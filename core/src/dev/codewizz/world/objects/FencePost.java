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
import dev.codewizz.utils.serialization.SerializableObject;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;
import dev.codewizz.world.settlement.FarmArea;

public class FencePost extends GameObject implements SerializableObject, IBuy {

	private static final Sprite texture = Assets.getSprite("fence-post");
	private final List<Item> costs = new CopyOnWriteArrayList<>();
	
	public FencePost() {
		super();
		
		this.sortHeight = 25f;
		this.id = "aop:fence-post";
	
		this.costs.add(new Item(ItemType.WOOD, 4));
		this.costs.add(new Item(ItemType.PLANKS, 2));
	}
	
	public FencePost(float x, float y) {
		super(x, y);

		this.w = 16;
		this.h = 16;

		this.sortHeight = 25f;

		this.id = "aop:fence-post";
		this.name = "Fence Post";
		
		this.costs.add(new Item(ItemType.WOOD, 4));
		this.costs.add(new Item(ItemType.PLANKS, 2));
	}

	@Override
	public void update(float d) {
	}

	@Override
	public void render(SpriteBatch b) {
		b.draw(texture, x + 16, y + 31);
	}

	@Override
	public Polygon getHitBox() {
		return new Polygon(new int[] { (int) x + 30, (int) x + 30, (int) x + 34, (int) x + 34 },
				new int[] { (int) y + 31, (int) y + 55, (int) y + 55, (int) y + 31 }, 4);
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
		return "Fence Post";
	}

	@Override
	public String getMenuDescription() {
		return "A sturdy wooden post";
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
		FarmArea a = new FarmArea();
		boolean v = a.checkArea(cell);
		if(v) {
			Main.inst.world.settlement.areas.add(a);
		}

		cell.disconnect();
	}
	
	@Override
	public void onDestroy() {
		cell.reconnect();
	}

	@Override
	public List<Item> costs() {
		return costs;
	}
}
