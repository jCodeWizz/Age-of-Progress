package dev.codewizz.world.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.gfx.Animation;
import dev.codewizz.gfx.Renderable;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.world.objects.tasks.HuntTask;

public class Wolf extends Animal {

	private Animation walkAnim;
	private boolean moving = false;
	private float attackSpeed = 1.5f, damage = 4f;
	
	public Wolf() {
		super();

		this.id = "aop:wolf";

		this.wanderDistance = 20;

		speed = 40f;

		createAnim();
	}

	public Wolf(float x, float y) {
		super(x, y);

		this.id = "aop:wolf";
		this.name = "Wolf";

		this.w = 40;
		this.h = 40;
		this.wanderDistance = 20;

		speed = 40f;

		createAnim();
	}

	public Wolf(float x, float y, Herd herd) {
		super(x, y, herd);

		this.id = "aop:wolf";
		this.name = "Wolf";

		this.w = 40;
		this.h = 40;
		this.wanderDistance = 20;

		speed = 40f;

		createAnim();
	}

	private void createAnim() {
		Sprite[] frames = new Sprite[6];
		frames[0] = Assets.getSprite("wolf-move-1");
		frames[1] = Assets.getSprite("wolf-move-2");
		frames[2] = Assets.getSprite("wolf-move-3");
		frames[3] = Assets.getSprite("wolf-move-4");
		frames[4] = Assets.getSprite("wolf-move-5");
		frames[5] = Assets.getSprite("wolf-move-6");

		walkAnim = new Animation(0.05f, frames);
	}

	@Override
	public void update(float d) {
		super.update(d);

		moving = vel.x != 0 || vel.y != 0;

		if (moving)
			walkAnim.tick(d);

		for (Renderable object : Main.inst.world.getObjects()) {
			if (object instanceof Entity) {
				if (((Entity) object).getId().equals("aop:cow")) {
					if (Vector2.dst(object.getX(), object.getY(), getX(), getY()) < 400) {

						this.addTask(new HuntTask((Entity) object, damage, attackSpeed), true);

						break;
					}
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch b) {
		if (facingRight) {
			if (moving) {
				b.draw(walkAnim.getFrame(), x, y, w, h);
			} else {
				b.draw(Assets.getSprite("wolf-idle"), x, y, w, h);
			}
		} else {
			if (moving) {
				b.draw(walkAnim.getFrame(), x + 32, y, -w, h);
			} else {
				b.draw(Assets.getSprite("wolf-idle"), x + 32, y, -w, h);
			}
		}
	}
	
	@Override
	public GameObjectData save(GameObjectData object) {
		return super.save(object);	
	}

	@Override
	public boolean load(GameObjectDataLoader loader, GameObjectData object, boolean success) {
		return super.load(loader, object, success);
	}
}
