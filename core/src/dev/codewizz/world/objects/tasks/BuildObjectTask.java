package dev.codewizz.world.objects.tasks;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Timer;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.ConstructionObject;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;

public class BuildObjectTask extends Task {

	private ConstructionObject object;
	private Hermit hermit;
	private Item i;

	private final Timer timerFinish;
	private boolean finishing;

	private final Timer timerGrab;
	private boolean grabbing;
	
	public BuildObjectTask(ConstructionObject object, Item i) {
		this.object = object;
		this.i = i;

		timerFinish = new Timer(2f) {
			@Override
			public void timer() {
				finish();
			}
		};

		timerGrab = new Timer(.5f) {
			@Override
			public void timer() {
				grab();
			}
		};
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

		timerFinish.cancel();
		timerGrab.cancel();

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
			finishing = true;
		} else {
			if(Main.inst.world.settlement.inventory.containsItem(i)) {
				if(!hermit.getInventory().addItem(i)) {
					stop();
				}
			} else {
				stop();
			}

			grabbing = true;
		}
	}

	public void grab() {
		Main.inst.world.settlement.inventory.removeItem(i);
		hermit.getAgent().setGoal(object.getCell());
	}

	@Override
	public void reset() {
	}

	@Override
	public void update(float d) {
		if (finishing) {
			timerFinish.update(d);
		}

		if (grabbing) {
			timerGrab.update(d);
		}
	}

	@Override
	public String getName() {
		return "Building with " + i.getType().getName() + " on " + object.getToPlace().getName();
	}
}
