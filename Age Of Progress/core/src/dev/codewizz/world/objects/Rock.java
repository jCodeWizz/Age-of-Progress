package dev.codewizz.world.objects;

import java.awt.Polygon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.Particle;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Utils;
import dev.codewizz.utils.serialization.RCObject;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Serializable;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;

public class Rock extends GameObject implements Serializable, IGatherable {

	private static Sprite texture = Assets.getSprite("rock");
	private static Sprite texture2 = Assets.getSprite("rock-broken");

	private boolean broken = false;
	private boolean tasked = false;
	
	public Rock(float x, float y) {
		super(x, y);

		this.w = 64;
		this.h = 64;

		this.sortHeight = 25f;

		this.id = "aop:rock";
	}

	@Override
	public void update(float d) {
	}

	@Override
	public void render(SpriteBatch b) {
		if(broken) {
			b.draw(texture2, x + 8, y + 25);
		} else {
			b.draw(texture, x + 8, y + 25);
		}
	}

	@Override
	public Polygon getHitBox() {
		return new Polygon(new int[] { (int) x + 8, (int) x + 8, (int) x + 42, (int) x + 55, (int) x + 55 },
				new int[] { (int) y + 25, (int) y + 40, (int) y + 52, (int) y + 40, (int) y + 25 }, 5);
	}

	@Override
	public RCObject save(RCObject object) {
		return object;
	}

	@Override
	public void load(RCObject object) {
		Main.inst.world.addObject(this);
	}

	@Override
	public int duration() {
		return 10;
	}

	@Override
	public void gather() {
		
		int amount = Utils.getRandom(4, 8);
		for(int i = 0; i < amount; i++) {
			Item item = new Item(x + 25 + Utils.getRandom(-15, 15), y + 30 + Utils.getRandom(-5, 5), ItemType.Stone);
			Main.inst.world.addItem(item);
		}
		
		if(broken) {
			spawnParticles();
			destroy();
		} else {
			broken = true;
			tasked = false;
			spawnParticles();
		}
	}
	
	private void spawnParticles() {
		for(int i = 0; i < 10; i++) {
			int height = Utils.getRandom(5, 25);
			Particle p = new Particle(x + 8 + Utils.getRandom(0, 48), y + 25 + height, 4, 4)
					.color(Color.GRAY)
					.gravity(-1f, height);
			Main.inst.world.particles.add(p);
			
		}
	}

	@Override
	public boolean ready() {
		if(tasked) { return false; }
		else { tasked = true; return true; }
	}
}
