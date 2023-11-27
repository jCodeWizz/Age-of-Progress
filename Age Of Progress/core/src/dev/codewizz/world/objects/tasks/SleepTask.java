package dev.codewizz.world.objects.tasks;

import dev.codewizz.main.Main;
import dev.codewizz.world.Cell;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.buildings.Building;
import dev.codewizz.world.objects.hermits.Hermit;

public class SleepTask extends Task {

	private Hermit hermit;
	private final float sleepConst = 0.5f;
	private boolean onGround = false;
	
	private float sleepNeedStart;
	
	@Override
	public void finish() {
		hermit.finishCurrentTask();
	}

	@Override
	public void stop() {
		hermit.getAgent().stop();
		hermit.finishCurrentTask();
	}

	@Override
	public void start(TaskableObject object) {
		hermit = (Hermit) object;
		
		Building b = hermit.getHome();
		
		if(b != null) {
			Cell cell = Main.inst.world.getCell(b.getX(), b.getY() + 32);
			object.getAgent().setGoal(cell, object.getX(), object.getY());
		} else {
			//TODO: maybe add different ways/spaces to sleep.
			onGround = true;
		}
		
		sleepNeedStart = hermit.getSleepNeed();
		started = true;
	}

	@Override
	public void reach() {
		hermit.getHome().enter(hermit);
	}

	@Override
	public void update(float d) {
		
		if(onGround && started) {
			if(hermit.getSleepNeed() <= 0f) {
				finish();
			}
			
			hermit.setSleepNeed(hermit.getSleepNeed() - sleepConst * d);
		}
	}

	@Override
	public String getName() {
		return "Getting sleep " + (int)((1f-(hermit.getSleepNeed()/sleepNeedStart))*100f) + "%";
	}

	@Override
	public void reset() {
		
	}
}
