package dev.codewizz.world.settlement;

import com.badlogic.gdx.graphics.g2d.Sprite;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Nature;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;

public enum CropType {

	empty("farm-tile", 0f, new Item(0, 0, ItemType.Carrot, 3), 1, null),
	carrots3("carrot3-farm-tile", 2*(Nature.TRANSITION_TIME + Nature.DAY_TIME), new Item(0, 0, ItemType.Carrot, 3), 8, empty),
	carrots2("carrot2-farm-tile", 2*(Nature.TRANSITION_TIME + Nature.DAY_TIME), new Item(0, 0, ItemType.Carrot, 3), 8, carrots3),
	carrots1("carrot-farm-tile", 2*(Nature.TRANSITION_TIME + Nature.DAY_TIME), new Item(0, 0, ItemType.Carrot, 3), 8, carrots2),
	wheat3("carrot3-farm-tile", 2*(Nature.TRANSITION_TIME + Nature.DAY_TIME), new Item(0, 0, ItemType.Carrot, 3), 8, empty),
	wheat2("carrot2-farm-tile", 2*(Nature.TRANSITION_TIME + Nature.DAY_TIME), new Item(0, 0, ItemType.Carrot, 3), 8, wheat3),
	wheat1("carrot-farm-tile", 2*(Nature.TRANSITION_TIME + Nature.DAY_TIME), new Item(0, 0, ItemType.Carrot, 3), 8, wheat2);
	
	
	
	
	
	private Sprite sprite;
	private float time = 0f;
	private CropType next;
	private Item item;
	private int max;
	CropType(String texture, float time, Item item, int max, CropType next) {
		this.time = time;
		this.sprite = Assets.getSprite(texture);
		this.item = item;
		this.max = max;
		this.next = next;
	}
	
	public CropType getNext() {
		return this.next;
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public float getTime() {
		return this.time;
	}
	
	public int getMax() {
		return max;
	}
	
	public Item getItem() {
		return item;
	}
}
