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

	private final float REACH = 1000f;
	
	private Hermit hermit;
	private Animal animal;
	private FarmArea area;
	
	private boolean reachedAnimal = false;
	
	private float counter = 0f;
	
	public CaptureAnimalTask(Animal animal, FarmArea area) {
		this.animal = animal;
		this.area = area;
		this.jobs.add(Jobs.Farmer);
		animal.setTasked(true);
	}
	
	@Override
	public void finish() {
		hermit.setTaskAnimation(null);
		hermit.finishCurrentTask();
	
		animal.setTasked(false);
	}

	@Override
	public void stop() {
		hermit.setTaskAnimation(null);
		hermit.finishCurrentTask();
		
		animal.setTasked(false);
	}

	@Override
	public void start(TaskableObject object) {
		this.hermit = (Hermit) object;
		
		if(area == null) {
			stop();
		}
		
		hermit.getAgent().setGoal(Main.inst.world.getCell(animal.getX(), animal.getY()), hermit.getX(), hermit.getY());
		if(hermit.getAgent().path.isEmpty())
			reach();
		
		started = true;
	}

	@Override
	public void reach() {
		if(!reachedAnimal) {
			if(Vector2.dst2(animal.getX(), animal.getY(), hermit.getX(), hermit.getY()) < REACH) {
				reachedAnimal = true;
				
				hermit.getAgent().setGoal(area.getEntrances().get(0), hermit.getX(), hermit.getY());
				if(hermit.getAgent().path.isEmpty()) {
					reach();
				}
				
				animal.getAgent().stop();
				animal.getAgent().setGoal(area.getEntrances().get(0), animal.getX(), animal.getY());
				if(animal.getAgent().path.isEmpty()) {
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
			
			boolean s = area.join(animal);
			if(!s) {
				if(FarmArea.anyAvailable()) {
					area = FarmArea.findArea(animal);
					if(area != null) {
						s = area.join(animal);
					} else {
						stop();
					}
				}
			}
			
			if(!s) {
				stop();
			} else {
				animal.setX(area.getArea().get(0).getMiddlePoint().x);
				animal.setY(area.getArea().get(0).getMiddlePoint().y);
				
				if(animal.isInHerd()) {
					animal.getHerd().removeMember(animal);
				}
				
				finish();
			}
			animal.getAgent().stop();
			hermit.setSpeed(20f);
		}
	}

	@Override
	public void reset() {
		
	}

	@Override
	public void update(float d) {
		
		if(reachedAnimal) {
			if(counter < 3f) counter += d;
			else {
				hermit.setSpeed(animal.getSpeed());
			}
		}
	}

	@Override
	public String getName() {
		return "capturing animal for pen";
	}
}