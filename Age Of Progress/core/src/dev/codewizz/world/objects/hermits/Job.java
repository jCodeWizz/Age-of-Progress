package dev.codewizz.world.objects.hermits;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.Animation;
import dev.codewizz.utils.Direction;

public abstract class Job {

	protected Hermit hermit;
	protected Jobs job;
	
	public HashMap<Direction, Animation> animations = new HashMap<>();
	public HashMap<Direction, Sprite> directions = new HashMap<>();
	
	public abstract void update(float dt);
	public abstract void render(SpriteBatch b);
	
	public Hermit getHermit() {
		return hermit;
	}
	
	public void setHermit(Hermit hermit) {
		this.hermit = hermit;
	}
	
	public Jobs getJob() {
		return job;
	}
	
	public abstract Sprite getIcon();
	
	
}
