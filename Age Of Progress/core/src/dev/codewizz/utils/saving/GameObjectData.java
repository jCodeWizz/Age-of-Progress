package dev.codewizz.utils.saving;

import dev.codewizz.main.Main;
import dev.codewizz.modding.Registers;
import dev.codewizz.utils.serialization.RCDatabase;
import dev.codewizz.utils.serialization.RCObject;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Serializable;

public class GameObjectData {

	public GameObject object;
	public boolean tileObject = false;
	public int indexX;
	public int indexY;
	
	public static void load(RCObject ro) {
		String t = ro.getName().substring(1);
		
		float x = ro.findField("x").getFloat();
		float y = ro.findField("y").getFloat();
		
		GameObject object = Registers.createGameObject(t, x, y);
		((Serializable) object).load(ro);
		Main.inst.world.addObject(object);
	}
	
	public static void load(RCDatabase db) {
		for(RCObject object : db.objects) {
			if(object.getName().charAt(0) == '$') {
				load(object);
			}
		}
	}
}
