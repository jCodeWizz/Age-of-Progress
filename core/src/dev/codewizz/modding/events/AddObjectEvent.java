package dev.codewizz.modding.events;

import dev.codewizz.world.GameObject;

public class AddObjectEvent extends Event {

	private GameObject object;
	
	public AddObjectEvent(GameObject object) {
		this.object = object;
	}
	
	public GameObject getGameObject() {
		return object;
	}
}
