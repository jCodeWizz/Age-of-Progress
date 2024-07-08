package dev.codewizz.gfx.gui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;

public abstract class UIMenu extends UIElement {

	public List<UIElement> elements = new CopyOnWriteArrayList<>();
	public UILayer layer;
	
	public UIMenu(String id, int x, int y, int w, int h, UILayer layer) {
		super(id, x, y, w, h);
		
		this.layer = layer;
		this.wantsClick = false;
		
		setup();
	}
	
	public abstract void setup();
	
	@Override
	public abstract java.awt.Rectangle getBounds();
	
	public void close() {
		disable();
	}
	
	@Override
	protected void onEnable() {
		onOpen();
        layer.elements.addAll(elements);
	}		
			
	@Override
	protected void onDisable() {
		onClose();
		for(UIElement e : elements) {
			layer.elements.remove(e);
		}	
	}

	public void clicked(GameObject object) {}
	public void clicked(Cell cell) {}

	public void scroll(float amount) {
		for(UIElement e : elements) {
			if(e.id.contains("list")) {
				UIScrollList list = (UIScrollList) e;
				if(list.isEnabled()) {
					list.scroll(amount);
				}
			}
		}	
	}
	
	public abstract void onOpen();
	public abstract void onClose();
			
	@Override
	public void render(SpriteBatch b) {
	}		
}				
	