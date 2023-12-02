package dev.codewizz.modding;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

import dev.codewizz.modding.annotations.EventCall;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Pair;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Tile;

public class Registers {

	public static final HashMap<String, Pair<ModInfo, JavaMod>> mods = new HashMap<>();
	public static final HashMap<String, Class<? extends Tile>> tiles = new HashMap<>();
	public static final HashMap<String, Class<? extends GameObject>> objects = new HashMap<>();
	public static final HashMap<String, Pair<Object,Method>> events = new HashMap<>();

	public static Tile createTile(String id, Cell cell) {
		if(!tiles.containsKey(id)) {
			Logger.error("Trying to create a tile for: " + id + " but it doesn't exist!");
			return null;
		}
		
		Tile tile;
		try {
			tile = tiles.get(id).getConstructor().newInstance(cell);
			return tile;
		} catch (Exception e) {
			Logger.error("Exception while trying to create tile: " + id);
			e.printStackTrace();
			return null;
		}
	}
	
	public static GameObject createGameObject(String id, float x, float y) {
		if(!objects.containsKey(id)) {
			Logger.error("Trying to create a tile for: " + id + " but it doesn't exist!");
			return null;
		}
		
		try {
			GameObject object = objects.get(id).getConstructor().newInstance(x, y);
			return object;
		} catch (Exception e) {
			Logger.error("Exception while trying to create object: " + id);
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean registerMod(ModInfo info, JavaMod mod) {
		if(mods.containsKey(info.getId())) return false;
		mods.put(info.getId(), new Pair<ModInfo,JavaMod>(info, mod));
		return true;
	}
	
	public static boolean registerTile(String id, Class<? extends Tile> tile) {
		if(tiles.containsKey(id)) return false;
		tiles.put(id, tile);
		return true;
	}
	
	public static boolean registerGameObject(String id, Class<? extends GameObject> object) {
		if(objects.containsKey(id)) return false;
		objects.put(id, object);
		return true;
	}
	
	public static boolean registerEvent(ModInfo info, Object o) {
		boolean valid = false;
		for(Method b : o.getClass().getDeclaredMethods()) {
			for(Annotation a : b.getAnnotations()) {
				if(a.annotationType() == EventCall.class) {
					valid = true;
					events.put(info.getId() + ":" + o.getClass().toString() + ":" + b.getName(), new Pair<Object,Method>(o, b));
				}
			}
		}
		if(!valid) {
			Logger.error("Couldn't find any valid event methods in: " + o.getClass() + " | " + info.getId());
		}
		
		return valid;
	}
}