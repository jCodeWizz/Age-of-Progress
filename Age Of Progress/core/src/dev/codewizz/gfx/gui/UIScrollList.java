package dev.codewizz.gfx.gui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class UIScrollList extends UIElement {

	public List<UIElement> slots = new CopyOnWriteArrayList<>();
	public Rectangle slotPort;
	
	public static float SCROLL_SPEED = 20f;
	public int scroll = 0;
	public int maxScroll = 0;
	
	public UIScrollList(String id, int x, int y, int w, int h) {
		super(id, x, y, w, h);

	}

	@Override
	public void render(SpriteBatch b) {
		
	}
	
	public Rectangle getSlotArea() {
		return slotPort;
	}
	
	public void scroll(float a) {
		
		int amount = (int)(a * SCROLL_SPEED);
		
		if(scroll + amount > maxScroll) {
			amount = maxScroll - scroll;
		} else if (scroll + amount < 0){
			amount = -scroll;
		}
		
		for(UIElement b : slots) {
			b.setY(b.getY() + amount);
		}
		
		scroll += amount;
	}		
}
