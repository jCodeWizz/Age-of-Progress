package dev.codewizz.world.objects;

import java.awt.Polygon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.Particle;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Utils;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.world.GameObject;
import dev.codewizz.utils.serialization.SerializableObject;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;

public class Tree extends GameObject implements SerializableObject, IGatherable {

	private static final Sprite texture = Assets.getSprite("tree");
	
	public Tree() {
		super();
		
		this.sortHeight = 25;
		
		this.id = "aop:tree";
	}
	
	public Tree(float x, float y) {
		super(x, y);

		this.w = 64;
		this.h = 64;

		this.sortHeight = 25f;

		this.id = "aop:tree";
		this.name = "Tree";
	}

	@Override
	public void update(float d) {
	}

	@Override
	public void render(SpriteBatch b) {
		b.draw(texture, x - 32, y + 25);
	}

	@Override
	public Polygon getHitBox() {
		return new Polygon(new int[] { (int) x + 27, (int) x + 27, (int) x + 37, (int) x + 37 },
				new int[] { (int) y + 32, (int) y + 100, (int) y + 100, (int) y + 32 }, 4);
	}

	@Override
	public GameObjectData save(GameObjectData object) {
		return super.save(object);
	}

	@Override
	public boolean load(GameObjectDataLoader loader, GameObjectData object, boolean success) {
		return super.load(loader, object, success);
	}

	@Override
	public int duration() {
		return 10;
	}

	@Override
	public void gather() {
		int amount = Utils.getRandom(4, 8);
		for(int i = 0; i < amount; i++) {
			Item item = new Item(x + 25 + Utils.getRandom(-15, 15), y + 35 + Utils.getRandom(-5, 5), ItemType.WOOD);
			Main.inst.world.addItem(item);
		}
		destroy();
	}
	
	@Override
	public void onDestroy() {
		for (int i = 0; i < 20; i += 4) {
			Particle p = new Particle(x + 40 + 4 * i - 50, y + 100 + 10, 17, 13)
					.color(new Color(0.549f, 0.71f, 0.227f, 1f)).sprite(Particle.LEAVE).counter(4, 5)
					.velocity(0, -0.5f).gravity(-0.5f - Utils.RANDOM.nextFloat()*2, 100 + Utils.getRandom(-15, 15));
			Main.inst.world.particles.add(p);
		}
		
		for (int i = 5; i < 13; i += 4) {
			Particle p = new Particle(x + 40 + 4 * i - 50, y + 100, 17, 13)
					.color(new Color(0.549f, 0.71f, 0.227f, 1f)).sprite(Particle.LEAVE).counter(4, 5)
					.velocity(0, -0.5f).gravity(-0.5f - Utils.RANDOM.nextFloat()*2, 90 + Utils.getRandom(-15, 15));
			Main.inst.world.particles.add(p);
		}
		
		for (int i = 5; i < 13; i += 4) {
			Particle p = new Particle(x + 40 + 4 * i - 50, y + 100 + 20, 17, 13)
					.color(new Color(0.549f, 0.71f, 0.227f, 1f)).sprite(Particle.LEAVE).counter(4, 5)
					.velocity(0, -0.5f).gravity(-0.5f - Utils.RANDOM.nextFloat()*2, 100 + 10 + Utils.getRandom(-15, 15));
			Main.inst.world.particles.add(p);
		}

		for (int i = 0; i < 20; i+=4) {
			for (int j = 0; j < 4; j+=2) {
				Particle p = new Particle(x + 20 + Utils.getRandom(0, 15), y + 50 + i * 3, 4, 4)
						.color(new Color(0.463f, 0.345f, 0.227f, 1f))
						.velocity(0, -4f)
						.gravity(-2f, 20 + i * 3 + Utils.getRandom(-5, 5));
				Main.inst.world.particles.add(p);
			}
		}
	}

	@Override
	public boolean ready() {
		if(tasked) { return false; }
		else { tasked = true; return true; }
	}
}
