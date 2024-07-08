package dev.codewizz.world.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.Renderable;
import dev.codewizz.utils.Utils;

public class Item extends Renderable {
	
	private float x, y, w, h;
	public ItemType item;
	public int size = 1;
	
	private float floating = 0;
	private long offset;
	
	public Item(float x, float y, ItemType item) {
		this.x = x;
		this.y = y;
		
		this.item = item;
		w = item.getWidth();
		h = item.getHeight();
		
		this.offset = Utils.getRandom(0, 1000);
	}
	
	public Item(ItemType item) {
		this(0, 0, item);
	}
	
	public Item(ItemType item, int size) {
		this(0, 0, item);
		this.size = size;
	}
	
	
	public Item size(int size) {
		this.size = size;
		return this;
	}
	
	@Override
	public void update(float dt) {
		floating = (float)Math.sin(((double)(System.currentTimeMillis() + offset) / 300)) * 1.5f;
	}	
	
	@Override
	public void render(SpriteBatch b) {
		b.draw(item.getSprite(), x, y + floating, w, h);
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getSortingHeight() {
		return 0f;
	}
	
	public ItemType getType() {
		return item;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
}
