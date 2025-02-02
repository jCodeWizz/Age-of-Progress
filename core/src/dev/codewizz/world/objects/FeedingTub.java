package dev.codewizz.world.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.utils.serialization.SerializableObject;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FeedingTub extends GameObject implements SerializableObject, IBuy {

	private static final Sprite texture = Assets.getSprite("feeding-tub");
	private final List<Item> costs = new CopyOnWriteArrayList<>();

	public FeedingTub() {
		super();

		this.sortHeight = 25f;

		this.id = "aop:feeding-tub";

		costs.add(new Item(ItemType.WOOD, 2));
	}

	public FeedingTub(float x, float y) {
		super(x, y);

		this.w = 16;
		this.h = 16;

		this.sortHeight = 25f;

		this.id = "aop:feeding-tub";
		this.name = "Feeding Tub";
		
		costs.add(new Item(ItemType.WOOD, 2));
	}

	@Override
	public void onClick() {
		((GameLayer) Main.inst.renderer.uiLayer).farmMenu.open(this);
		selected = true;
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
		return "Feeding Tub";
	}

	@Override
	public String getMenuDescription() {
		return "Allows your farmer to feed their animals! How nice...";
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean continues() {
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
