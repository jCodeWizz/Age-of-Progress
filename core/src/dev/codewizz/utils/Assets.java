package dev.codewizz.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import dev.codewizz.modding.Registers;
import dev.codewizz.world.items.ItemType;
import dev.codewizz.world.settlement.Crop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import dev.codewizz.main.Main;
import dev.codewizz.networking.NetworkProtocol;
import java.util.List;
import java.util.logging.FileHandler;


public class Assets {

    public static File folderAgeOfProgress;
    public static File folderMods;
    public static File folderSaves;
    public static File folderData;
    public static File folderLogs;
    public static File folderScreenshots;

    public static String pathFolderAgeOfProgress = "ageofprogress/";
    public static String pathFolderMods = pathFolderAgeOfProgress + "mods/";
    public static String pathFolderSaves = pathFolderAgeOfProgress + "saves/";
    public static String pathFolderData = pathFolderAgeOfProgress + "data/";
    public static String pathFolderLogs = pathFolderData + "logs/";
    public static String pathFolderScreenshots = pathFolderAgeOfProgress + "screenshots/";

    public static HashMap<String, Sprite> sprites = new HashMap<>();
    public static HashMap<String, TextureAtlas> atlasses = new HashMap<>();
    public static HashMap<String, BufferedImage> images = new HashMap<>();
    public static HashMap<String, Texture> procuderal = new HashMap<>();
    public static HashMap<String, Sound> sounds = new HashMap<>();

    public static void setup() {
        try {
            folderAgeOfProgress = createFolder(pathFolderAgeOfProgress);
            folderMods = createFolder(pathFolderMods);
            folderSaves = createFolder(pathFolderSaves);
            folderData = createFolder(pathFolderData);
            folderLogs = createFolder(pathFolderLogs);
            folderScreenshots = createFolder(pathFolderScreenshots);
        } catch (Exception e) {
            Logger.error("Couldn't find main game files: ");
            Logger.error("Game will load, but saves, settings and mods might not be loaded.");
            e.printStackTrace();
        }
    }

    public static void checkGameFiles() {
        if(!Gdx.files.external(pathFolderData + "names.txt").exists()) {
            Logger.log("The 'names.txt' file wasn't found, attempting to download it...");
            Main.inst.client.sendFileRequest(pathFolderData + "names.txt");
        }
    }

    public static void create() {
        atlasses.put("tiles", new TextureAtlas(Gdx.files.internal("packs/tiles.atlas")));
        atlasses.put("entities", new TextureAtlas(Gdx.files.internal("packs/entities.atlas")));
        atlasses.put("particles", new TextureAtlas(Gdx.files.internal("packs/particles.atlas")));
        atlasses.put("items", new TextureAtlas(Gdx.files.internal("packs/items.atlas")));
        atlasses.put("objects", new TextureAtlas(Gdx.files.internal("packs/objects.atlas")));
        atlasses.put("ui", new TextureAtlas(Gdx.files.internal("packs/ui.atlas")));
        atlasses.put("paths", new TextureAtlas());

        for (TextureAtlas t : atlasses.values()) {
            for (AtlasRegion s : t.getRegions()) {
                sprites.put(s.name, t.createSprite(s.name));
            }
        }

        //TODO: Procedural tile generation needs fixing.
        procuderal.put("t", new Texture(Gdx.files.internal("textures/procuderal/path-tile.png")));
        procuderal.put("tTL",
                       new Texture(Gdx.files.internal("textures/procuderal/path-tile-TL.png")));
        procuderal.put("tTR",
                       new Texture(Gdx.files.internal("textures/procuderal/path-tile-TR.png")));
        procuderal.put("tBR",
                       new Texture(Gdx.files.internal("textures/procuderal/path-tile-BR.png")));
        procuderal.put("tBL",
                       new Texture(Gdx.files.internal("textures/procuderal/path-tile-BL.png")));

        procuderal.put("aop:grass-tile",
                       new Texture(Gdx.files.internal("textures/tiles/grass-tile.png")));
        procuderal.put("aop:dirt-tile",
                       new Texture(Gdx.files.internal("textures/tiles/dirt-tile.png")));
        procuderal.put("aop:tiled-tile-1",
                       new Texture(Gdx.files.internal("textures/tiles/tiled-tile-1.png")));
        procuderal.put("aop:tiled-tile-2",
                       new Texture(Gdx.files.internal("textures/tiles/tiled-tile-2.png")));
        procuderal.put("aop:tiled-tile-3",
                       new Texture(Gdx.files.internal("textures/tiles/tiled-tile-3.png")));
        procuderal.put("aop:tiled-tile-4",
                       new Texture(Gdx.files.internal("textures/tiles/tiled-tile-4.png")));
        procuderal.put("aop:tiled-tile-5",
                       new Texture(Gdx.files.internal("textures/tiles/tiled-tile-5.png")));
        procuderal.put("aop:tiled-tile-6",
                       new Texture(Gdx.files.internal("textures/tiles/tiled-tile-6.png")));
        procuderal.put("aop:tiled-tile-7",
                       new Texture(Gdx.files.internal("textures/tiles/tiled-tile-7.png")));
        procuderal.put("aop:tiled-tile-8",
                       new Texture(Gdx.files.internal("textures/tiles/tiled-tile-8.png")));
    }

    public static void load() {
        Crop.readCropFromJson(Gdx.files.internal("data/crops/carrot.json").readString());

        for (ItemType type : ItemType.types.values()) {
            String name = type.getId().split(":")[1];
            Logger.log(name);
            if (Gdx.files.internal("data/recipes/" + name + ".json").exists()) {
                String json = Gdx.files.internal("data/recipes/" + name + ".json").readString();
                Registers.registerRecipe(name, json);
                Logger.log("Registered");
            }
        }
    }

    public static BufferedImage getImage(String name) {
        return images.get(name);
    }

    public static File createFolder(String path) {
        File f = Gdx.files.external(path).file();

        if (!f.exists()) {
            f.mkdir();
        }

        return f;
    }

    public static TextureAtlas getAtlas(String s) {
        if (atlasses.containsKey(s)) {
            return atlasses.get(s);
        } else {
            return null;
        }
    }

    public static Sprite addSpriteToAtlas(String atlas, String s, Sprite sprite) {
        TextureAtlas textureAtlas = atlasses.get(atlas);

        textureAtlas.addRegion(s, sprite);

        return sprites.put(s, textureAtlas.createSprite(s));
    }

    public static Sprite getSprite(String s) {
        if (sprites.containsKey(s)) {
            return sprites.get(s);
        } else {
            Logger.error("Couldn't find requested texture in Assets Handler: ");
            Logger.error(s);
            return sprites.get("cow-idle");
        }
    }

    public static Sound getSound(String id) {
        if(sounds.containsKey(id)) {
            return sounds.get(id);
        } else {
            Logger.error("Couldn't find requested sound in Assets Handler: ");
            Logger.error(id);
            return null;
        }
    }

    public static boolean containsSprite(String s) {
        return sprites.containsKey(s);
    }

    public static void dispose() {
        for (TextureAtlas ta : atlasses.values()) {
            ta.dispose();
        }
    }
}
