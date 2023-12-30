package dev.codewizz.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.main.Main;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.items.Inventory;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.tasks.BuildObjectTask;

public class ConstructionObject extends GameObject {

	private GameObject toPlace;
	private boolean placed;
	
	public Inventory costs;
	
	public ConstructionObject(float x, float y, GameObject toPlace) {
		super(x, y);
		
		this.id = "aop:construction";

		this.toPlace = toPlace;
		
		IBuy b = (IBuy) toPlace;
		costs = new Inventory(-1);
		for(Item i : b.costs()) {
			costs.addItem(i);
			Main.inst.world.settlement.addTask(new BuildObjectTask(this, i), true);
		}
	}
	
	private void placeObject() {
		toPlace.getCell().setObject(toPlace);
		((IBuy) toPlace).onPlace(toPlace.getCell());
		placed = true;
	}

	@Override
	public void update(float d) {
		if(costs.isEmpty() && !placed) {
			this.destroy();
			placeObject();
		} 
	}

	@Override
	public void render(SpriteBatch b) {
		b.setColor(1f, 1f, 1f, 0.5f);
		toPlace.render(b);
		b.setColor(1f, 1f, 1f, 1f);
	}
	
	public GameObject getToPlace() {
		return toPlace;
	}
}
