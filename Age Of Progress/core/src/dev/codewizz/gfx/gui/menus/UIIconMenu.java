package dev.codewizz.gfx.gui.menus;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.gui.UIElement;
import dev.codewizz.gfx.gui.UIIcon;
import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;

public abstract class UIIconMenu extends UIMenu {

	private ArrayList<UIIcon> icons;
	private UIIcon parent;
	
	protected int currentHeight;
	
	private UIImage fade;
	private UIImage fade1;
	private UIImage fade2;
	
	private boolean closing = false;
	
	public UIIconMenu(String id, int x, int y, UILayer layer, UIIcon parent, UIIcon... icons) {
		super(id, x, y, 0, 0, layer);
		
		this.w = 28 * UILayer.SCALE;
		this.parent = parent;
	}

	@Override
	public void setup() {
		this.icons = new ArrayList<>();
		
		fade = new UIImage("fade-1-" + id, x, y, 28 * UILayer.SCALE, currentHeight, new Sprite(UILayer.fadeTex), 1);
		fade1 = new UIImage("fade-2-" + id, x, y, 28 * UILayer.SCALE, currentHeight, new Sprite(UILayer.fadeTex), 1);
		fade2 = new UIImage("fade-3-" + id, x, y, 28 * UILayer.SCALE, currentHeight, new Sprite(UILayer.fadeTex), 1);
		
		elements.add(fade);
		elements.add(fade1);
		elements.add(fade2);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}

	@Override
	public void onOpen() {
		parent.setPressed(true);
		currentHeight = 0;
		update();
		
		closing = false;
	}

	@Override
	public void onClose() {
		
		closing = true;
		
		for(UIElement e : elements) {
			layer.elements.add(e);
		}
		
		this.enabled = true;
		parent.setPressed(false);
		update();
	}
	
	private void update() {
		
		if(closing) {
			fade.setH(currentHeight);
			fade1.setH(currentHeight);
			fade2.setH(currentHeight);
			
			for(int i = 0; i < icons.size(); i++) {
				float newY = y + currentHeight - 27 * (i+1) * UILayer.SCALE;
				if(newY > parent.getY()) {
					icons.get(i).setY(y + currentHeight - 27 * (i+1) * UILayer.SCALE);
				} else {
					icons.get(i).setY(parent.getY());
				}
			}
		} else {
			fade.setH(currentHeight);
			fade1.setH(currentHeight);
			fade2.setH(currentHeight);
			
			for(int i = 0; i < icons.size(); i++) {
				float newY = y + currentHeight - 27 * (i+1) * UILayer.SCALE;
				if(newY > parent.getY()) {
					icons.get(i).setY(y + currentHeight - 27 * (i+1) * UILayer.SCALE);
				} else {
					icons.get(i).setY(parent.getY());
				}
			}
		}
	}
	
	@Override
	public void render(SpriteBatch b) {
		parent.setPressed(true);
		
		if(closing) {
			if(currentHeight > 0) {
				currentHeight -= (int)(Gdx.graphics.getDeltaTime() * 4000f);
				update();
			}
			
			if(currentHeight <= 0) {
				currentHeight = 0;
				update();
				
				for(UIElement e : elements) {
					layer.elements.remove(e);
				}
				
				this.enabled = false;
				parent.setPressed(false);
			}
		} else {
			if(currentHeight < h) {
				currentHeight += (int)(Gdx.graphics.getDeltaTime() * 2000f);
				update();
			}
			
			if(currentHeight > h) {
				currentHeight = h;
				update();
			}
		}
	}
	
	public void addIcon(UIIcon icon) {
		icons.add(icon);
		elements.add(icon);
		
		this.h = (12+24*(icons.size()+1)) * UILayer.SCALE;
		
		icon.setX(x + 3 * UILayer.SCALE);
	}
}
