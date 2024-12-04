package dev.codewizz.world.items;

import com.badlogic.gdx.graphics.g2d.Sprite;

import dev.codewizz.modding.Registers;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;

import java.util.HashMap;

public class ItemType {

	public final static HashMap<String, ItemType> types = new HashMap<>();

	public final static ItemType CARROT = new ItemType("Carrot", "aop:carrot", Assets.getSprite("item-carrot"));
	public final static ItemType MUSHROOMS = new ItemType("Mushrooms", "aop:mushrooms", Assets.getSprite("mushrooms"));
	public final static ItemType PLANKS = new ItemType("Planks", "aop:planks", Assets.getSprite("item-plank"));
	public final static ItemType STONE = new ItemType("Stone", "aop:stone", Assets.getSprite("item-stone"));
	public final static ItemType WOOD = new ItemType("Wood", "aop:wood", Assets.getSprite("item-wood"));
	public final static ItemType AXE = new ItemType("Crude Axe", "aop:crude_axe", Assets.getSprite("axe"));
	
	private final float w, h;
	private final String id, name;
	private final Sprite sprite;
	
	public ItemType(String name, String id) {
		this(name, id, Assets.getSprite(id));
	}
	
	public ItemType(String name, String id, Sprite sprite) {
		this.id = id;
		this.sprite = sprite;
		this.name = name;
		
		this.w = 12;
		this.h = 12;

		types.put(id, this);
	}
	
	public String getName() {
		return name;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public String getId() {
		return id;
	}
	
	public float getWidth() {
		return w;
	}
	
	public float getHeight() {
		return h;
	}

	@Override
	public String toString() {
		return "ItemType{" +
				"w=" + w +
				", h=" + h +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", sprite=" + sprite +
				'}';
	}
}
