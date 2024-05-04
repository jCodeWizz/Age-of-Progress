package dev.codewizz.modding.events;

import dev.codewizz.world.GameObject;

public class AddObjectEvent extends Event {

	private GameObject object;
	private Reason reason;
	
	public AddObjectEvent(GameObject object, Reason reason) {
		this.reason = reason;
		this.object = object;
	}
	
	public GameObject getGameObject() {
		return object;
	}
	public Reason getReason() { return reason; }
}
