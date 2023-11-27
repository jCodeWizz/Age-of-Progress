package dev.codewizz.world.objects.tasks;

import dev.codewizz.world.Cell;
import dev.codewizz.world.objects.TaskableObject;

public class MoveTask extends Task {

	private Cell cell;
	private TaskableObject object;
	
	public MoveTask(Cell cell) {
		this.cell = cell;
	}
	
	@Override
	public void reach() {
		finish();
	}
	
	@Override
	public void finish() {
		object.finishCurrentTask();
	}

	@Override
	public void stop() {
		object.getAgent().stop();
		object.finishCurrentTask();
	}

	@Override
	public void update(float d) {
		
	}

	@Override
	public void start(TaskableObject object) {
		this.object = object;
		boolean success = object.getAgent().setGoal(cell, object.getX(), object.getY());
		if(object.getAgent().path.isEmpty() || !success)
			reach();
		started = true;
		
	}
	
	@Override
	public String getName() {
		return "Moving";
	}

	@Override
	public void reset() {
		
	}
}
