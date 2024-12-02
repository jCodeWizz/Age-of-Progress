package dev.codewizz.world.objects.tasks;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Timer;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;

public class ClearInventoryTask extends Task {

	private Hermit hermit;
	private Timer timer;
	private boolean reached;

	public ClearInventoryTask() {
		timer = new Timer(0.5f) {
			@Override
			public void timer() {
				dropItem();
			}
		};
	}
	
	@Override
	public void finish() {
		hermit.finishCurrentTask();
		timer.cancel();
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
		reached = true;
	}

	@Override
	public void reset() {
		
	}

	@Override
	public void update(float d) {
		if (reached) {
			timer.update(d);
		}

		if (hermit.getInventory().isEmpty()) {
			finish();
		}
	}

	public void dropItem() {
		Item i = hermit.getInventory().getItems().get(0);
		Main.inst.world.settlement.getInventory().addItem(i);
		hermit.getInventory().removeItem(i);

		if (!hermit.getInventory().isEmpty()) {
			timer.reset();
		}
	}

	@Override
	public String getName() {
		return "Moving Items";
	}
}
