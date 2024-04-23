package dev.codewizz.world.objects;

import java.awt.Polygon;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.utils.serialization.Serializable;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;

public class Stump extends GameObject implements Serializable, IBuy {

	private static final Sprite texture = Assets.getSprite("stump");
	private final List<Item> costs = new CopyOnWriteArrayList<>();
	
	public Stump() {
		super();

		this.sortHeight = 25f;

		this.id = "aop:stump";
		
		costs.add(new Item(ItemType.WOOD, 3));
	}
	
	public Stump(float x, float y) {
		super(x, y);

		this.w = 16;
		this.h = 16;

		this.sortHeight = 25f;

		this.id = "aop:stump";
		this.name = "Stump";
		
		costs.add(new Item(ItemType.WOOD, 3));
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
	public GameObjectData save(GameObjectData object) {
		return super.save(object);
	}

	@Override
	public boolean load(GameObjectDataLoader loader, GameObjectData object, boolean success) {
		return super.load(loader, object, success);
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
	public String getId() {
		return id;
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
		Main.inst.world.settlement.objects.add(this);
	}

	@Override
	public List<Item> costs() {
		return costs;
	}
}
