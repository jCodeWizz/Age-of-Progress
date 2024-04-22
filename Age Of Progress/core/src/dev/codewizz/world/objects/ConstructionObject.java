package dev.codewizz.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.main.Main;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.items.Inventory;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.tasks.BuildObjectTask;

public class ConstructionObject extends GameObject {

	public static boolean FREE = true;
	
	private GameObject toPlace;
	private boolean placed;
	
	public Inventory costs;
	
	public ConstructionObject(float x, float y, GameObject toPlace) {
		super(x, y);
		
		this.id = "aop:construction";

		this.toPlace = toPlace;
		this.costs = new Inventory(-1);
		
		if(!FREE) {
			IBuy b = (IBuy) toPlace;
			for(Item i : b.costs()) {
				this.costs.addItem(i);
				Main.inst.world.settlement.addTask(new BuildObjectTask(this, i), true);
			}
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
	
	@Override
	public GameObjectData save(GameObjectData object) {
		return super.save(object);
	}
	
	@Override
	public void load(GameObjectData object) {
		super.load(object);
	}
	
	@Override
	public boolean loadCheck(GameObjectDataLoader loader, boolean ready) {
		
		
		
		
		return super.loadCheck(loader, ready);
	}
}
