package dev.codewizz.world.objects.hermits;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.Animation;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;

public class Farmer extends Job {

	//private static Sprite texture = Assets.getSprite("farmer-hermit");
	private static Sprite icon = Assets.getSprite("farmer-icon");
	
	public Farmer() {
		this.job = Jobs.Farmer;
		
		createAnimations();
	}

	@Override
	public void update(float dt) {
		
		/*
		if(this.hermit.getCurrentTask() == null && this.hermit.getTree().isEmpty()) {
			if(FarmArea.anyAvailable()) {
				for(Renderable r : Main.inst.world.getObjects()) {
					if(r instanceof Animal && !r.isTasked()) {
						Animal a = (Animal) r;
						
						FarmArea area = FarmArea.findArea(a);
						CaptureAnimalTask task = new CaptureAnimalTask(a, area);
						hermit.addTask(task, true);
					}
				}
			}
		}
		*/
	}

	@Override
	public void render(SpriteBatch b) {
		//b.draw(texture, hermit.getX(), hermit.getY(), hermit.getW(), hermit.getH());
	}
	
	@Override
	public Sprite getIcon() {
		return icon;
	}
	
	private void createAnimations() {
		Sprite[] S = new Sprite[3];
		S[0] = Assets.getSprite("down-1-farmer");
		S[1] = Assets.getSprite("down-2-farmer");
		S[2] = Assets.getSprite("down-3-farmer");
		
		Sprite[] SW = new Sprite[3];
		SW[0] = Assets.getSprite("down-left-1-farmer");
		SW[1] = Assets.getSprite("down-left-2-farmer");
		SW[2] = Assets.getSprite("down-left-3-farmer");
		
		Sprite[] W = new Sprite[3];
		W[0] = Assets.getSprite("left-1-farmer");
		W[1] = Assets.getSprite("left-2-farmer");
		W[2] = Assets.getSprite("left-3-farmer");
		
		Sprite[] NW = new Sprite[3];
		NW[0] = Assets.getSprite("up-left-1-farmer");
		NW[1] = Assets.getSprite("up-left-2-farmer");
		NW[2] = Assets.getSprite("up-left-3-farmer");
		
		Sprite[] N = new Sprite[3];
		N[0] = Assets.getSprite("up-1-farmer");
		N[1] = Assets.getSprite("up-2-farmer");
		N[2] = Assets.getSprite("up-3-farmer");
		
		Sprite[] NE = new Sprite[3];
		NE[0] = Assets.getSprite("up-right-1-farmer");
		NE[1] = Assets.getSprite("up-right-2-farmer");
		NE[2] = Assets.getSprite("up-right-3-farmer");
		
		Sprite[] E = new Sprite[3];
		E[0] = Assets.getSprite("right-1-farmer");
		E[1] = Assets.getSprite("right-2-farmer");
		E[2] = Assets.getSprite("right-3-farmer");
		
		Sprite[] SE = new Sprite[3];
		SE[0] = Assets.getSprite("down-right-1-farmer");
		SE[1] = Assets.getSprite("down-right-2-farmer");
		SE[2] = Assets.getSprite("down-right-3-farmer");
		
		animations.put(Direction.South, new Animation(Hermit.walkAnimSpeed, S));
		animations.put(Direction.SouthWest, new Animation(Hermit.walkAnimSpeed, SW));
		animations.put(Direction.West, new Animation(Hermit.walkAnimSpeed, W));
		animations.put(Direction.NorthWest, new Animation(Hermit.walkAnimSpeed, NW));
		animations.put(Direction.North, new Animation(Hermit.walkAnimSpeed, N));
		animations.put(Direction.NorthEast, new Animation(Hermit.walkAnimSpeed, NE));
		animations.put(Direction.East, new Animation(Hermit.walkAnimSpeed, E));
		animations.put(Direction.SouthEast, new Animation(Hermit.walkAnimSpeed, SE));
		
		directions.put(Direction.South, Assets.getSprite("down-2-farmer"));
		directions.put(Direction.SouthWest, Assets.getSprite("down-left-2-farmer"));
		directions.put(Direction.West, Assets.getSprite("left-2-farmer"));
		directions.put(Direction.NorthWest, Assets.getSprite("up-left-2-farmer"));
		directions.put(Direction.North, Assets.getSprite("up-2-farmer"));
		directions.put(Direction.NorthEast, Assets.getSprite("up-right-2-farmer"));
		directions.put(Direction.East, Assets.getSprite("right-2-farmer"));
		directions.put(Direction.SouthEast, Assets.getSprite("down-right-2-farmer"));
	}
}