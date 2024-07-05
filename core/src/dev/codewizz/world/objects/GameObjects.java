package dev.codewizz.world.objects;

import dev.codewizz.modding.Registers;
import dev.codewizz.world.objects.buildings.Building;
import dev.codewizz.world.objects.hermits.Hermit;

public class GameObjects {

	public static void register() {

		Registers.registerGameObject("aop:building", Building.class);
		Registers.registerGameObject("aop:bush", Bush.class);
		Registers.registerGameObject("aop:cow", Cow.class);
		Registers.registerGameObject("aop:fence", Fence.class);
		Registers.registerGameObject("aop:fence-gate", FenceGate.class);
		Registers.registerGameObject("aop:fence-post", FencePost.class);
		Registers.registerGameObject("aop:flag", Flag.class);
		Registers.registerGameObject("aop:hermit", Hermit.class);
		Registers.registerGameObject("aop:mushrooms", Mushrooms.class);
		Registers.registerGameObject("aop:pine-tree", PineTree.class);
		Registers.registerGameObject("aop:rock", Rock.class);
		Registers.registerGameObject("aop:stump", Stump.class);
		Registers.registerGameObject("aop:tree", Tree.class);
		Registers.registerGameObject("aop:wolf", Wolf.class);
	}
	
}
