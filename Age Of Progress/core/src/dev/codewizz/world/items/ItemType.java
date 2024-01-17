package dev.codewizz.world.items;

import com.badlogic.gdx.graphics.g2d.Sprite;

import dev.codewizz.utils.Assets;

public class ItemType {
	
	public static ItemType CARROT = new ItemType("Carrot", "aop:carrot", Assets.getSprite("item-carrot"));
	public static ItemType CLAY = new ItemType("Clay", "aop:clay", Assets.getSprite("item-clay"));
	public static ItemType MUSHROOMS = new ItemType("Mushrooms", "aop:mushrooms", Assets.getSprite("item-mushrooms"));
	public static ItemType PLANKS = new ItemType("Planks", "aop:planks", Assets.getSprite("item-plank"));
	public static ItemType STONE = new ItemType("Stone", "aop:stone", Assets.getSprite("item-stone"));
	public static ItemType WHEAT = new ItemType("Wheat", "aop:wheat", Assets.getSprite("item-wheat"));
	public static ItemType WOOD = new ItemType("Wood", "aop:wood", Assets.getSprite("item-wood"));
	
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
