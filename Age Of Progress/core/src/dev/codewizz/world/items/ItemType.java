package dev.codewizz.world.items;

import com.badlogic.gdx.graphics.g2d.Sprite;

import dev.codewizz.utils.Assets;

public class ItemType {
	
	public final static ItemType CARROT = new ItemType("Carrot", "aop:carrot", Assets.getSprite("item-carrot"));
	public final static ItemType CLAY = new ItemType("Clay", "aop:clay", Assets.getSprite("item-clay"));
	public final static ItemType MUSHROOMS = new ItemType("Mushrooms", "aop:mushrooms", Assets.getSprite("item-mushrooms"));
	public final static ItemType PLANKS = new ItemType("Planks", "aop:planks", Assets.getSprite("item-plank"));
	public final static ItemType STONE = new ItemType("Stone", "aop:stone", Assets.getSprite("item-stone"));
	public final static ItemType WHEAT = new ItemType("Wheat", "aop:wheat", Assets.getSprite("item-wheat"));
	public final static ItemType WOOD = new ItemType("Wood", "aop:wood", Assets.getSprite("item-wood"));
	
	private float w, h;
	private String id, name;
	private Sprite sprite;
	
	public ItemType(String name, String id) {
		this.id = id;
		this.sprite = Assets.getSprite(id);
		this.name = name;
		
		this.w = 12;
		this.h = 12;
	}
	
	public ItemType(String name, String id, Sprite sprite) {
		this.id = id;
		this.sprite = sprite;
		this.name = name;
		
		this.w = 12;
		this.h = 12;
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
}
