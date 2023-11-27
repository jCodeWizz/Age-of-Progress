package dev.codewizz.world.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.Animation;
import dev.codewizz.gfx.gui.menus.SelectMenu;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.serialization.RCField;
import dev.codewizz.utils.serialization.RCObject;
import dev.codewizz.world.Serializable;

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
	
	public Cow(float x, float y) {
		super(x, y);
		
		this.id = ID.Cow;
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
		
		this.id = ID.Cow;
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
		
		
		
		if(vel.x > 0) {
			facingRight = true;
			moving = true;
		} else if(vel.x < 0){
			facingRight = false;
			moving = true;
		} else {
			moving = false;
		}
		
		if(moving)
			walkAnim.tick(d);
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
	public void renderUICard(SelectMenu m) {
		
	}
	
	@Override
	public void updateUICard() {

	}

	@Override
	public RCObject save(RCObject object) {
		object.addField(RCField.Float("health", health));
		
		return object;	
	}

	@Override
	public void load(RCObject object) {
		this.health = object.findField("health").getFloat();
		
		Main.inst.world.objects.add(this);		
	}
}
