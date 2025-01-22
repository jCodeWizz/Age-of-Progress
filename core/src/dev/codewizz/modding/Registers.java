package dev.codewizz.modding;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.modding.annotations.EventCall;
import dev.codewizz.modding.annotations.Priorities;
import dev.codewizz.modding.annotations.Priority;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Pair;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Tile;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;
import dev.codewizz.world.items.Recipe;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Registers {

    public static final HashMap<String, Pair<ModInfo, JavaMod>> mods = new HashMap<>();
    public static final HashMap<String, Class<? extends Tile>> tiles = new HashMap<>();
    public static final HashMap<String, Class<? extends GameObject>> objects = new HashMap<>();
    public static final HashMap<String, CommandExecutor> commands = new HashMap<>();
    public static HashMap<String, EventMethod> events = new HashMap<>();
    public static HashMap<String, Recipe> recipes = new HashMap<>();

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

    public static boolean registerCommand(String name, CommandExecutor e) {
        if(commands.containsKey(name)) {
            Logger.error("Tried to register new command: " + name + " but it already exists!");
            return false;
        }

        commands.put(name, e);
        return true;
    }

    public static boolean registerRecipe(String name, String json) {
        JsonValue root = new JsonReader().parse(json);
        String type = root.getString("type");
        float time = root.getFloat("time");

        JsonValue costs = root.get("cost");
        JsonValue results = root.get("result");

        Item[] itemCosts = new Item[costs.size];
        Item[] itemResults = new Item[results.size];

        for (int i = 0; i < costs.size; i++) {
            JsonValue cost = costs.get(i);

            ItemType costType = ItemType.types.get(cost.getString("id"));

            if (costType == null) {
                Logger.error("Couldn't find cost type: " + cost.getString("id"));
            }

            Item item = new Item(costType , cost.getInt("count", 1));
            itemCosts[i] = item;
        }

        for (int i = 0; i < results.size; i++) {
            JsonValue result = results.get(i);

            ItemType resultType = ItemType.types.get(result.getString("id"));

            if (resultType == null) {
                Logger.error("Couldn't find result type: " + result.getString("id"));
            }

            Item item = new Item(resultType , result.getInt("count", 1));
            itemResults[i] = item;
        }

        Recipe recipe = new Recipe(type, time, itemCosts, itemResults);
        recipes.put(name, recipe);
        return true;
    }
}