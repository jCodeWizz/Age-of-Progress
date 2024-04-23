package dev.codewizz.world.objects.tasks;

import dev.codewizz.gfx.Animation;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.objects.IGatherable;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;

public class GatherTask extends Task {

	private GameObject object;
	private Cell cell;
	
	private Hermit hermit;
	private Animation animation = new Animation(-10f, 0, 0.1f, Assets.getSprite("hermit-axe-1"), Assets.getSprite("hermit-axe-2"));
	
	private float counter = 0;
	private boolean reached = false;
	
	public GatherTask(GameObject object) {
		this.object = object;
		
		counter = (float)((IGatherable) object).duration();
		
		cell = Main.inst.world.getCell(object.getX() + 30, object.getY() + 30);
		
		if(cell == null) stop();
	}
	
	@Override
	public void finish() {
		((IGatherable) object).gather();
		hermit.setTaskAnimation(null);
		hermit.finishCurrentTask();
	}

	@Override
	public void stop() {
		hermit.getAgent().stop();
		hermit.setTaskAnimation(null);
		hermit.finishCurrentTask();
	}

	@Override
	public void start(TaskableObject object) {
		this.hermit = (Hermit) object;
		this.hermit.getAgent().setGoal(cell);
		if(this.hermit.getAgent().path.isEmpty())
			stop();
		started = true;
	}

	@Override
	public void reach() {
		reached = true;
		hermit.setFacing(Direction.West);
		hermit.setTaskAnimation(animation);
	}

	@Override
	public void update(float d) {
		if(reached) {
			counter -= 1f * d;
			
			if(counter <= 0) {
				finish();
			}
		}
	}

	@Override
	public String getName() {
		return "Gathering " + object.getName();
	}

	@Override
	public void reset() {
		counter = (float)((IGatherable) object).duration();
		reached = false;
	}
}
