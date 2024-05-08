package dev.codewizz.modding;

import dev.codewizz.modding.annotations.EventCall;
import dev.codewizz.modding.annotations.Priorities;
import dev.codewizz.modding.annotations.Priority;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Pair;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Tile;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Registers {

    public static final HashMap<String, Pair<ModInfo, JavaMod>> mods = new HashMap<>();
    public static final HashMap<String, Class<? extends Tile>> tiles = new HashMap<>();
    public static final HashMap<String, Class<? extends GameObject>> objects = new HashMap<>();
    public static HashMap<String, EventMethod> events = new HashMap<>();

    public static Tile createTile(String id) {
        if (!tiles.containsKey(id)) {
            Logger.error("Trying to create a tile for: " + id + " but it doesn't exist!");
            return null;
        }

        Tile tile;
        try {
            tile = tiles.get(id).getConstructor().newInstance();
            return tile;
        } catch (Exception e) {
            Logger.error("Exception while trying to create tile: " + id);
            e.printStackTrace();
            return null;
        }
    }

    public static GameObject createGameObject(String id, float x, float y) {
        if (!objects.containsKey(id)) {
            Logger.error("Trying to create a tile for: " + id + " but it doesn't exist!");
            return null;
        }

        try {
            GameObject object = objects.get(id).getConstructor(float.class, float.class)
                    .newInstance(x, y);
            return object;
        } catch (Exception e) {
            Logger.error("Exception while trying to create object: " + id);
            e.printStackTrace();
            return null;
        }
    }

    public static boolean registerMod(ModInfo info, JavaMod mod) {
        if (mods.containsKey(info.getId())) { return false; }
        mods.put(info.getId(), new Pair<>(info, mod));
        return true;
    }

    public static void registerTile(String id, Class<? extends Tile> tile) {
        if (tiles.containsKey(id)) {
            Logger.error("Trying to register tile for: " + id);
            Logger.error("But it already exists...");
            return;
        }
        tiles.put(id, tile);
    }

    public static void registerGameObject(String id, Class<? extends GameObject> object) {
        if (objects.containsKey(id)) {
            Logger.error("Trying to register object for: " + id);
            Logger.error("But it already exists...");
            return;
        }
        objects.put(id, object);
    }

    public static boolean registerEvent(ModInfo info, Object o) {
        boolean valid = false;
        for (Method b : o.getClass().getDeclaredMethods()) {
            boolean found = false;
            Priorities p = Priorities.NORMAL;

            for (Annotation a : b.getAnnotations()) {
                if (a.annotationType() == Priority.class) {
                    p = ((Priority) a).priority();
                }
                if (a.annotationType() == EventCall.class) {
                    valid = true;
                    found = true;
                }
            }

            if (found) {
                events.put(info.getId() + ":" + o.getClass().toString() + ":" + b.getName(),
                           new EventMethod(o, b, p));
            }
        }
        if (!valid) {
            Logger.error("Couldn't find any valid event methods in: " + o.getClass() + " | "+ info.getId());
        }

        return valid;
    }
}