package dev.codewizz.world.objects.tasks;

import dev.codewizz.main.Main;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;

public class ClearInventoryTask extends Task {

	private Hermit hermit;
	
	@Override
	public void finish() {
		hermit.finishCurrentTask();
	}

	@Override
	public void stop() {
		for(Item i : hermit.getInventory().getItems()) {
			i.setX(hermit.getX());
			i.setY(hermit.getY());
			i.setTasked(false);
			Main.inst.world.addItem(i);
			hermit.getInventory().removeItem(i);
		}
	}

	@Override
	public void start(TaskableObject object) {
		this.hermit = (Hermit) object;
		
		object.getAgent().setGoal(hermit.getSettlement().getCell());
		if(object.getAgent().path.isEmpty())
			reach();
		started = true;
	}

	@Override
	public void reach() {
		for(Item item : hermit.getInventory().getItems()) {
			Main.inst.world.settlement.getInventory().addItem(item);
			hermit.getInventory().removeItem(item);
		}
		finish();
	}

	@Override
	public void reset() {
		
	}

	@Override
	public void update(float d) {
		
	}

	@Override
	public String getName() {
		return "Moving Items";
	}
}
