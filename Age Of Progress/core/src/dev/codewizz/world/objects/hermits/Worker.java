package dev.codewizz.world.objects.hermits;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.Animation;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;

public class Worker extends Job {

	private static Sprite icon = Assets.getSprite("worker-icon");
	
	public Worker() {
		this.job = Jobs.Worker;
		
		createAnimations();
	}
	
	@Override
	public void update(float dt) {
		
	}

	@Override
	public void render(SpriteBatch b) {
		
	}

	@Override
	public Sprite getIcon() {
		return icon;
	}
	
	private void createAnimations() {
		Sprite[] S = new Sprite[3];
		S[0] = Assets.getSprite("down-1-worker");
		S[1] = Assets.getSprite("down-2-worker");
		S[2] = Assets.getSprite("down-3-worker");
		
		Sprite[] SW = new Sprite[3];
		SW[0] = Assets.getSprite("down-left-1-worker");
		SW[1] = Assets.getSprite("down-left-2-worker");
		SW[2] = Assets.getSprite("down-left-3-worker");
		
		Sprite[] W = new Sprite[3];
		W[0] = Assets.getSprite("left-1-worker");
		W[1] = Assets.getSprite("left-2-worker");
		W[2] = Assets.getSprite("left-3-worker");
		
		Sprite[] NW = new Sprite[3];
		NW[0] = Assets.getSprite("up-left-1-worker");
		NW[1] = Assets.getSprite("up-left-2-worker");
		NW[2] = Assets.getSprite("up-left-3-worker");
		
		Sprite[] N = new Sprite[3];
		N[0] = Assets.getSprite("up-1-worker");
		N[1] = Assets.getSprite("up-2-worker");
		N[2] = Assets.getSprite("up-3-worker");
		
		Sprite[] NE = new Sprite[3];
		NE[0] = Assets.getSprite("up-right-1-worker");
		NE[1] = Assets.getSprite("up-right-2-worker");
		NE[2] = Assets.getSprite("up-right-3-worker");
		
		Sprite[] E = new Sprite[3];
		E[0] = Assets.getSprite("right-1-worker");
		E[1] = Assets.getSprite("right-2-worker");
		E[2] = Assets.getSprite("right-3-worker");
		
		Sprite[] SE = new Sprite[3];
		SE[0] = Assets.getSprite("down-right-1-worker");
		SE[1] = Assets.getSprite("down-right-2-worker");
		SE[2] = Assets.getSprite("down-right-3-worker");
		
		animations.put(Direction.South, new Animation(Hermit.walkAnimSpeed, S));
		animations.put(Direction.SouthWest, new Animation(Hermit.walkAnimSpeed, SW));
		animations.put(Direction.West, new Animation(Hermit.walkAnimSpeed, W));
		animations.put(Direction.NorthWest, new Animation(Hermit.walkAnimSpeed, NW));
		animations.put(Direction.North, new Animation(Hermit.walkAnimSpeed, N));
		animations.put(Direction.NorthEast, new Animation(Hermit.walkAnimSpeed, NE));
		animations.put(Direction.East, new Animation(Hermit.walkAnimSpeed, E));
		animations.put(Direction.SouthEast, new Animation(Hermit.walkAnimSpeed, SE));
		
		directions.put(Direction.South, Assets.getSprite("down-2-worker"));
		directions.put(Direction.SouthWest, Assets.getSprite("down-left-2-worker"));
		directions.put(Direction.West, Assets.getSprite("left-2-worker"));
		directions.put(Direction.NorthWest, Assets.getSprite("up-left-2-worker"));
		directions.put(Direction.North, Assets.getSprite("up-2-worker"));
		directions.put(Direction.NorthEast, Assets.getSprite("up-right-2-worker"));
		directions.put(Direction.East, Assets.getSprite("right-2-worker"));
		directions.put(Direction.SouthEast, Assets.getSprite("down-right-2-worker"));
	}
}
