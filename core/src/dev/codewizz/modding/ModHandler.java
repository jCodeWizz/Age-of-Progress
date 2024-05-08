package dev.codewizz.modding;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Pair;
import dev.codewizz.utils.Utils;

public class ModHandler {

    public void register() {
        File file = Assets.folderMods;

        File[] mods = file.listFiles();

        if (mods != null) {
            for (int i = 0; i < mods.length; i++) {
                if (Utils.getFileType(mods[i].getName()).equalsIgnoreCase("jar")) {
                    loadMod(mods[i]);
                } else {
                    Logger.error("Could not load mod: " + mods[i].getName()
                            .toUpperCase() + " because it wasn't detected as a jar file!");
                }
            }
        } else {
            Logger.error("Mods folder wasn't loaded correctly, check logs!");
        }
    }

    private void loadMod(File file) {
        try {
            URLClassLoader child = new URLClassLoader(new URL[]{file.toURI().toURL()},
                                                      this.getClass().getClassLoader());

            ZipFile zip = new ZipFile(file);
            InputStream stream = null;
            try {
                stream = zip.getInputStream(zip.getEntry("info.mod"));
            } catch (Exception e) {
                Logger.error("Mod: " + file.getName()
                        .toUpperCase() + " is missing or has a corruped info.mod file.");
                child.close();
                zip.close();
                return;
            }

            ModInfo info = new ModInfo(stream);

            if (Registers.mods.keySet().contains(info.getId()) || info.getId().equals("aop")) {
                Logger.error("Cannot load mod: " + file.getName()
                        .toUpperCase() + " because mod-id is already used: " + info.getId());

                child.close();
                zip.close();
                stream.close();

                return;
            }


            stream.close();


            for (String s : getClassNames(file)) {
                Class<?> testClass = Class.forName(s, false, child);

                if (s.equals(info.getMain())) {
                    if (testClass.getSuperclass() == JavaMod.class) {
                        JavaMod mod = (JavaMod) testClass.getConstructor().newInstance();
                        if (!Registers.registerMod(info, mod)) {
                            Logger.error("2 mods trying to use the same ID: " + info.getId());
                        }
                    } else {
                        Logger.error(
                                "Main class for mod: " + file.getName() + " is not a child of JavaMod");
                        Logger.error("or your Main class is not in a unique package");
                    }
                }
            }


            loadModAssets(info, file);

            zip.close();
            child.close();
        } catch (Exception e) {
            Logger.error("Exception while loading mod: " + file.getName());
            e.printStackTrace();
        }
    }

    public void loadModAssets(ModInfo info, File file) {

        int total = 0;

        ZipInputStream zip;
        try {
            ZipFile zipFile = new ZipFile(file);
            zip = new ZipInputStream(new FileInputStream(file.getPath()));
            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                String e = entry.getName();
                int end = entry.getName().indexOf('/');
                if (end != -1) {
                    if (e.substring(0, end).equalsIgnoreCase(info.getId())) {

                        int end2 = e.lastIndexOf('/') + 1;
                        String folder = e.substring(end, end2);
                        String name = e.substring(end2, e.lastIndexOf('.'));

                        InputStream stream = zipFile.getInputStream(entry);
                        byte[] bytes = stream.readAllBytes();

                        Pixmap m = new Pixmap(bytes, 0, bytes.length);
                        Sprite s = new Sprite(new Texture(m));


                        if (folder.equalsIgnoreCase("/textures/tiles/")) {
                            Assets.addSpriteToAtlas("tiles", info.getId() + ":" + name, s);
                        } else if (folder.equalsIgnoreCase("/textures/objects/")) {
                            Assets.addSpriteToAtlas("objects", info.getId() + ":" + name, s);
                        } else if (folder.equalsIgnoreCase("/textures/entities/")) {
                            Assets.addSpriteToAtlas("entities", info.getId() + ":" + name, s);
                        } else if (folder.equalsIgnoreCase("/textures/particles/")) {
                            Assets.addSpriteToAtlas("particles", info.getId() + ":" + name, s);
                        } else if (folder.equalsIgnoreCase("/textures/items/")) {
                            Assets.addSpriteToAtlas("items", info.getId() + ":" + name, s);
                        } else if (folder.equalsIgnoreCase("/textures/ui/icons/")) {
                            Assets.addSpriteToAtlas("ui", info.getId() + ":" + name, s);
                        } else {
                            Logger.error(
                                    "Trying to load: " + entry.getName() + " from mod: " + info.getId()
                                            .toUpperCase() + " but it isn't in the correct folder!");
                        }

                        stream.close();
                        total++;
                    }
                }
            }

            zipFile.close();
        } catch (Exception e) {
            Logger.error(
                    "Exception occured while loading resources for: " + info.getId().toUpperCase());
            e.printStackTrace();
        }

        String message =
                total != 1 ? "Loaded " + total + " resources for: " + info.getId().toUpperCase() :
                        "Loaded " + total + " resource for: " + info.getId().toUpperCase();
        Logger.log(message);
    }

    public ArrayList<String> getClassNames(File file) {
        ArrayList<String> classNames = new ArrayList<String>();
        try {
            ZipInputStream zip = new ZipInputStream(new FileInputStream(file.getPath()));
            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace('/', '.');
                    classNames.add(className.substring(0, className.length() - ".class".length()));
                }
            }
        } catch (Exception ignored) { }
        return classNames;
    }


    public void start() {
        List<Pair<ModInfo, JavaMod>> mods = new CopyOnWriteArrayList<>();

        mods.addAll(Registers.mods.values());


        ArrayList<EventMethod> e = new ArrayList<>(Registers.events.values());

        HashMap<String, EventMethod> r = new HashMap<>();

        Collections.sort(e);

        for (EventMethod m : e) {
            for (String s : Registers.events.keySet()) {

                if (Registers.events.get(s).equals(m)) {
                    r.put(s, m);
                }
            }
        }

        Registers.events = r;


        for (EventMethod method : Registers.events.values()) {
            System.out.println(method.getPriority());
        }


        for (Pair<ModInfo, JavaMod> pair : mods) {
            try {
                pair.getTypeB().register(pair.getTypeA());
                Logger.log("Registered mod: " + pair.getTypeA().getId().toUpperCase());
            } catch (Exception exc) {
                Logger.error("Exception when mod: " + pair.getTypeA().getId() + " was registering");
                exc.printStackTrace();
            }
        }

        while (!mods.isEmpty()) {
            for (Pair<ModInfo, JavaMod> pair : mods) {
                if (dependenciesClear(pair)) {
                    try {
                        pair.getTypeB().start();
                        Logger.log("Started mod: " + pair.getTypeA().getId().toUpperCase());
                        pair.getTypeA().initialized = true;
                        mods.remove(pair);

                    } catch (Exception exc) {
                        Logger.error(
                                "Exception when mod: " + pair.getTypeA().getId() + " was started");
                        exc.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean dependenciesClear(Pair<ModInfo, JavaMod> m) {
        String[] d = m.getTypeA().getDependencies();

		if (d == null) { return true; }

        for (int i = 0; i < d.length; i++) {
            if (!(d[i].isBlank() || d[i].isEmpty())) {
                ModInfo mod = Registers.mods.get(d[i]).getTypeA();
                if (!mod.initialized) {
                    return false;
                }
            }
        }

        return true;
    }

    public void update(float dt) {
        for (Pair<ModInfo, JavaMod> mod : Registers.mods.values()) {
            mod.getTypeB().update(dt);
        }
    }

    public void render(SpriteBatch b) {
        for (Pair<ModInfo, JavaMod> mod : Registers.mods.values()) {

            mod.getTypeB().render(b);
        }
    }

    public void stop() {
        for (Pair<ModInfo, JavaMod> mod : Registers.mods.values()) {
            try {
                mod.getTypeB().stop();
            } catch (Exception e) {
                Logger.error("Exception when mod: " + mod.getTypeA().getId() + " was stopped");
                e.printStackTrace();
            }
        }
    }
}
