package dev.codewizz.world.objects.tasks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.main.Main;
import dev.codewizz.world.objects.Entity;
import dev.codewizz.world.objects.TaskableObject;

public class HuntTask extends Task {

	private Entity huntable;
	private TaskableObject hunter;
	private float damage, speed, counter;
	
	public HuntTask(Entity huntable, float damage, float speed) {
		this.damage = damage;
		this.speed = speed;
		this.counter = speed;
		this.huntable = huntable;
	}
	
	@Override
	public void finish() {
		hunter.finishCurrentTask();
	}

	@Override
	public void stop() {
		
	}

	@Override
	public void start(TaskableObject object) {
		hunter = object;
		started = true;
	}

	@Override
	public void reach() {
		if(inRange()) {
			hunter.getAgent().stop();
		} else {
			hunter.getAgent().setGoal(Main.inst.world.getCell(huntable.getX(), huntable.getY()));
		}
	}
	
	private boolean inRange() {
		if(hunter.getAgent().moving) {
			return (Vector2.dst(huntable.getX(), huntable.getY(), hunter.getX(), hunter.getY()) < 20);
		} else {
			return (Vector2.dst(huntable.getX(), huntable.getY(), hunter.getX(), hunter.getY()) < 50);
		}
	}
	
	private void attack() {
		huntable.damage(damage);
	}
	
	@Override
	public void update(float d) {
		if(huntable.getHealth() <= 0f)
			finish();
		
		if(inRange()) {
			hunter.getAgent().stop();
			if(counter >= speed) {
				counter = 0;
				attack();
			} else {
				counter += 1 * Gdx.graphics.getDeltaTime();
			}
		} else {
			if(!hunter.getAgent().moving) {
				hunter.getAgent().setGoal(Main.inst.world.getCell(huntable.getX(), huntable.getY()));
			}
		}
	}
	
	@Override
	public String getName() {
		return "Hunting";
	}

	@Override
	public void reset() {
		
	}
}
