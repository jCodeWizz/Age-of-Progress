package dev.codewizz.world.objects.tasks;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.objects.Animal;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.hermits.Jobs;
import dev.codewizz.world.settlement.FarmArea;

public class CaptureAnimalTask extends Task {

	private final float REACH = 100f;
	
	private Hermit hermit;
	private Animal animal;
	private FarmArea area;
	
	private boolean reachedAnimal = false;
	
	public CaptureAnimalTask(Animal animal, FarmArea area) {
		this.animal = animal;
		this.area = area;
		
		this.jobs.add(Jobs.Farmer);
	}
	
	@Override
	public void finish() {
		hermit.setTaskAnimation(null);
		hermit.finishCurrentTask();
	}

	@Override
	public void stop() {
		hermit.setTaskAnimation(null);
		hermit.finishCurrentTask();
	}

	@Override
	public void start(TaskableObject object) {
		this.hermit = (Hermit) object;
		hermit.getAgent().setGoal(Main.inst.world.getCell(animal.getX(), animal.getY()), hermit.getX(), hermit.getY());
		if(hermit.getAgent().path.isEmpty())
			reach();
		
		started = true;
	}

	@Override
	public void reach() {
		/*
		 * force animal to follow hermit path finding.
		 *
		 */ 
		
		if(!reachedAnimal) {
			if(Vector2.dst2(animal.getX(), animal.getY(), hermit.getX(), hermit.getY()) < REACH) {
				
				hermit.getAgent().setGoal(area.getEntrances().get(0), hermit.getX(), hermit.getY());
				if(hermit.getAgent().path.isEmpty()) {
					reachedAnimal = true;
					reach();
				}
			} else {
				hermit.getAgent().setGoal(Main.inst.world.getCell(animal.getX(), animal.getY()), hermit.getX(), hermit.getY());
				if(hermit.getAgent().path.isEmpty()) {
					Logger.log("Couldn't path: " + Vector2.dst2(animal.getX(), animal.getY(), hermit.getX(), hermit.getY()) + "/"+ REACH);
					reachedAnimal = true;
					reach();
				}

			}
		} else {
			
			/*
			 * Add animal to some kind of list
			 * Force it inside of pen
			 * Reset it's path finding logic
			 */
			
			
			finish();
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
		return "capturing animal for pen";
	}
}