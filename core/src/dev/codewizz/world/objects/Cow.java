package dev.codewizz.world.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.Animation;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.world.Serializable;
import dev.codewizz.world.objects.tasks.CaptureAnimalTask;
import dev.codewizz.world.settlement.FarmArea;

public class Cow extends Animal implements Serializable {
	
	/*
	 * pTR1600:
	 * 
	 * jELMER cOw
	 * 
	 *  * HAppy FacE *
	 * 
	 */
	
	private Animation walkAnim;
	private boolean moving = false;
	
	public Cow() {
		super();
		
		this.id = "aop:cow";
		createAnim();
	}
	
	public Cow(float x, float y) {
		super(x, y);
		
		this.id = "aop:cow";
		this.name = "Cow";
		
		this.w = 32;
		this.h = 32;
		this.wanderDistance = 6;
		
		this.speed = 10f;
		this.health = 10f;
		
		createAnim();
	}
	
	public Cow(float x, float y, Herd herd) {
		super(x, y, herd);
		
		this.id = "aop:cow";
		this.name = "Cow";
		
		this.w = 32;
		this.h = 32;
		this.wanderDistance = 6;
		
		this.speed = 10f;
		this.health = 10f;
		
		createAnim();
	}
	
	private void createAnim() {
		Sprite[] frames = new Sprite[4];
		frames[0] = Assets.getSprite("cow-move-1");
		frames[1] = Assets.getSprite("cow-move-3");
		frames[2] = Assets.getSprite("cow-move-2");
		frames[3] = Assets.getSprite("cow-move-3");

		walkAnim = new Animation(0.15f, frames);
	}
	
	@Override
	public void update(float d) {
		super.update(d);
		
		moving = vel.x != 0 || vel.y != 0;
		
		if(moving)
			walkAnim.tick(d);
		
		
		if(this.isSelected()) {
			this.deselect();
			
			if(FarmArea.anyAvailable()) {
				FarmArea a = FarmArea.findArea(this);
				if(a != null) {
					Main.inst.world.settlement.addTask(new CaptureAnimalTask(this, a), moving);
				}
			}
			
		}
	}

	@Override
	public void render(SpriteBatch b) {
		if(this.damageCoolDown >= 0f)
			b.setColor(1f, 0f, 0f, 1f);
		
		if(facingRight) {
			if(moving) {
				b.draw(walkAnim.getFrame(), x, y, w, h);
			} else {
				b.draw(Assets.getSprite("cow-idle"), x, y, w, h);
			}
		} else {
			if(moving) {
				b.draw(walkAnim.getFrame(), x + 32, y, -w, h);
			} else {
				b.draw(Assets.getSprite("cow-idle"), x + 32, y, -w, h);
			}
		}
		
		if(this.damageCoolDown >= 0f)
			b.setColor(1f, 1f, 1f, 1f);
	}

	@Override
	public GameObjectData save(GameObjectData object) {
		return super.save(object);	
	}

	@Override
	public void load(GameObjectData object) {
		super.load(object);
	}
	
	@Override
	public boolean loadCheck(GameObjectDataLoader loader, boolean ready) {
		return super.loadCheck(loader, ready);
	}
}
