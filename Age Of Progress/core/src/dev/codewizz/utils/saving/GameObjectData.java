package dev.codewizz.utils.saving;

import dev.codewizz.utils.serialization.RCDatabase;
import dev.codewizz.utils.serialization.RCObject;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.objects.Cow;
import dev.codewizz.world.objects.Flag;
import dev.codewizz.world.objects.Tree;
import dev.codewizz.world.objects.Wolf;
import dev.codewizz.world.objects.buildings.Building;
import dev.codewizz.world.objects.hermits.Hermit;

public class GameObjectData {

	public GameObject object;
	public boolean tileObject = false;
	public int indexX;
	public int indexY;
	
	public static void load(RCObject ro) {
		String t = ro.getName().substring(1);
		
		float x = ro.findField("x").getFloat();
		float y = ro.findField("y").getFloat();
		
		if(t.equalsIgnoreCase("tree")) {
			Tree tree = new Tree(x, y);
			tree.load(ro);
		} else if(t.equalsIgnoreCase("cow")) {
			Cow cow = new Cow(x, y);
			cow.load(ro);
		} else if(t.equalsIgnoreCase("wolf")) {
			Wolf wolf = new Wolf(x, y);
			wolf.load(ro);
		} else if(t.equalsIgnoreCase("hermit")) {
			Hermit hermit = new Hermit(x, y);
			hermit.load(ro);
		} else if(t.equalsIgnoreCase("flag")) {
			Flag flag = new Flag(x, y);
			flag.load(ro);
		} else if(t.equalsIgnoreCase("building")) {
			Building building = new Building(x, y);
			building.load(ro);
		}
	}
	
	public static void load(RCDatabase db) {
		for(RCObject object : db.objects) {
			if(object.getName().charAt(0) == '$') {
				load(object);
			}
		}
	}
}
