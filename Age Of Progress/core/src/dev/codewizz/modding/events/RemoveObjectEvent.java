package dev.codewizz.modding.events;

import dev.codewizz.world.GameObject;

public class RemoveObjectEvent extends Event {

	private GameObject object;
	
	public RemoveObjectEvent(GameObject object) {
		this.object = object;
	}
	
	public GameObject getGameObject() {
		return object;
	}
}
