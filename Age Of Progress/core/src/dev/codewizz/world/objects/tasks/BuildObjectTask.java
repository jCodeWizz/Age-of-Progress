package dev.codewizz.world.objects.tasks;

import dev.codewizz.main.Main;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.ConstructionObject;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;

public class BuildObjectTask extends Task {

	private ConstructionObject object;
	private Hermit hermit;
	private Item i;
	
	public BuildObjectTask(ConstructionObject object, Item i) {
		this.object = object;
		this.i = i;
	}
	
	@Override
	public boolean canTake(Hermit hermit) {
		return Main.inst.world.settlement.inventory.containsItem(i, i.getSize()) && hermit.getInventory().roomFor(i);
	}
	
	@Override
	public void finish() {
		object.costs.removeItem(i);
		hermit.getInventory().removeItem(i);
		
		hermit.setTaskAnimation(null);
		hermit.finishCurrentTask();
	}

	@Override
	public void stop() {
		hermit.setTaskAnimation(null);
		hermit.finishCurrentTask();
		hermit.getAgent().stop();
		
		Main.inst.world.settlement.addTask(new BuildObjectTask(object, i), true);
	}

	@Override
	public void start(TaskableObject object) {
		this.hermit = (Hermit) object;
		boolean success = object.getAgent().setGoal(Main.inst.world.settlement.getCell());
		if(object.getAgent().path.isEmpty() || !success)
			reach();
		started = true;
	}

	@Override
	public void reach() {
		if(hermit.getInventory().containsItem(i)) {
			finish();
		} else {
			if(Main.inst.world.settlement.inventory.containsItem(i)) {
				if(!hermit.getInventory().addItem(i)) {
					stop();
				}
			} else {
				stop();
			}

			Main.inst.world.settlement.inventory.removeItem(i);
			hermit.getAgent().setGoal(object.getCell());
		}
	}

	@Override
	public void reset() {
	}

	@Override
	public void update(float d) {
	}

	@Override
	public String getName() {
		return "Building with " + i.getType().toString() + " on " + object.getToPlace().getName();
	}
}
