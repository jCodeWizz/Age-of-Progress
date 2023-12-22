package dev.codewizz.modding.events;

import dev.codewizz.world.World;

public class LoadWorldEvent extends Event {
	
	private World world;
	
	public LoadWorldEvent(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}
	
	
	
}
