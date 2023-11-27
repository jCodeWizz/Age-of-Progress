package dev.codewizz.gfx.gui.menus;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.gui.UIElement;
import dev.codewizz.gfx.gui.UIIcon;
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
		
		nameText = new UIText("name-text", (6 + 3) * UILayer.SCALE,  (6+98) * UILayer.SCALE, "", 8);
		
		elements.add(nameText);
		
		elements.add(new UIIcon("close-button", (6 + 142) * UILayer.SCALE - 1, (6+87) * UILayer.SCALE + 1, 14, 15,
				"close-icon") {
			@Override
			protected void onDeClick() {
				close();
			}
		});

		elements.add(new UIImage("selected-background", x, y, w, h, "selected-background"));
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
			if(!e.getID().equalsIgnoreCase("selected-background") && !e.getID().equalsIgnoreCase("close-button") && !e.getID().equalsIgnoreCase("name-text")) {
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
