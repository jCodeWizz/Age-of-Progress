package dev.codewizz.world.objects;

import java.awt.Polygon;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.utils.Assets;
import dev.codewizz.utils.serialization.RCObject;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Serializable;
import dev.codewizz.world.items.Item;

public class Stump extends GameObject implements Serializable, IBuy {

	private static Sprite texture = Assets.getSprite("stump");
	private List<Item> costs = new CopyOnWriteArrayList<>();
	
	public Stump(float x, float y) {
		super(x, y);

		this.w = 16;
		this.h = 16;

		this.sortHeight = 25f;

		this.id = "aop:stump";
		this.name = "Stump";
	}

	@Override
	public void update(float d) {
	}

	@Override
	public void render(SpriteBatch b) {
		b.draw(texture, x + 24, y + 25);
	}

	@Override
	public Polygon getHitBox() {
		return new Polygon(new int[] { (int) x + 24, (int) x + 24, (int) x + 40, (int) x + 40 },
				new int[] { (int) y + 25, (int) y + 41, (int) y + 41, (int) y + 25 }, 4);
	}

	@Override
	public RCObject save(RCObject object) {
		return object;
	}

	@Override
	public void load(RCObject object) {
	}

	@Override
	public Sprite getMenuSprite() {
		return texture;
	}

	@Override
	public String getMenuName() {
		return "Tree Stump";
	}

	@Override
	public String getMenuDescription() {
		return "The first workshop for your craftsman";
	}

	@Override
	public GameObject getCopy(float x, float y) {
		return new Stump(x, y);
	}

	@Override
	public boolean conintues() {
		return false;
	}

	@Override
	public boolean available() {
		return true;
	}

	@Override
	public void onPlace(Cell cell) {
		
	}

	@Override
	public List<Item> costs() {
		return costs;
	}
}
