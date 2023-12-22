package dev.codewizz.modding.events;

import dev.codewizz.world.World;

public class CreateWorldEvent extends Event {
	
	private World world;
	
	public CreateWorldEvent(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}
}
