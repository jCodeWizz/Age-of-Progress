package dev.codewizz.gfx.gui.menus;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.gui.UIElement;
import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.gfx.gui.UIText;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.world.GameObject;

public class SelectMenu extends UIMenu {

	public GameObject object;
	
	private UIText nameText;
	
	public SelectMenu(String id, int x, int y, int w, int h, UILayer layer) {
		super(id, x, y, w, h, layer);
	}

	@Override
	public void setup() {
		nameText = new UIText("name-text", x + 6 * UILayer.SCALE,  y + (h-5) * UILayer.SCALE, "", 8);
		elements.add(nameText);
		elements.add(new UIImage("selected-background", x, y, 150, 50, "select-menu-background"));
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x * UILayer.SCALE, y * UILayer.SCALE, w * UILayer.SCALE, h * UILayer.SCALE);
	}
	
	public void updateData() {
		nameText.setText("" + object.getName());
	}

	@Override
	public void onOpen() {
		object = GameLayer.selectedObject;
		updateData();
	}

	@Override
	public void onClose() {
		
		for(UIElement e : elements) {
			if(!e.getID().equalsIgnoreCase("selected-background") && !e.getID().equalsIgnoreCase("name-text")) {
				elements.remove(e);
				layer.elements.remove(e);
			}
		}
		
		if(GameLayer.selectedObject != null) GameLayer.selectedObject.deselect();
		object = null;
	}
	
	@Override
	public void render(SpriteBatch b) {
		updateData();
	}
}
