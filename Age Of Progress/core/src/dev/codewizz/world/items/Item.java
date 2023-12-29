package dev.codewizz.world.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.Renderable;
import dev.codewizz.utils.Utils;

public class Item extends Renderable {

	private float x, y, w, h;
	private float floating = 0;
	private long offset;
	private int size = 1;
	private ItemType type;
	private boolean hauled;
	
	public Item(float x, float y, ItemType type) {
		this.x = x;
		this.y = y;
		this.w = 12;
		this.h = 12;
		this.size = 1;
		
		this.offset = Utils.getRandom(0, 1000);
		this.type = type;
	}
	
	public Item(float x, float y, ItemType type, int size) {
		this(x, y, type);
		
		this.size = size;
	}
	
	public Item(ItemType type, int size) {
		this(0, 0, type, size);
	}
	
	@Override
	public void render(SpriteBatch b) {
		b.draw(type.getSprite(), x, y + floating, w, h);
	}
	
	public Item setSize(int size) {
		this.size = size;
		return this;
	}
	
	public int getSize() {
		return size;
	}
	
	@Override
	public float getX() {
		return x;
	}
	
	@Override
	public float getY() {
		return y;
	}
	
	public ItemType getType() {
		return type;
	}

	@Override
	public void update(float dt) {
		floating = (float)Math.sin(((double)(System.currentTimeMillis() + offset) / 300)) * 1.5f;
	}

	@Override
	public float getSorthingHeight() {
		return 0f;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public boolean isHauled() {
		return hauled;
	}
	
	public void setHauled(boolean hauled) {
		this.hauled = hauled;
	}
	
	@Override
	public String toString() {
		return "X: " + x + " Y: " + y + " " + type.toString() + ": " + size;
	}
}
