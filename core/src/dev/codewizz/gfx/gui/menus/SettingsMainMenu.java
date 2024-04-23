package dev.codewizz.gfx.gui.menus;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;

import dev.codewizz.gfx.gui.UIButton;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;

public class SettingsMainMenu extends UIMenu {

	public SettingsMainMenu(String id, int x, int y, int w, int h, UILayer layer) {
		super(id, x, y, w, h, layer);

	}

	@Override
	public void setup() {
		elements.add(new UIButton("back-button", Gdx.graphics.getWidth()-(66+5)*UILayer.SCALE, 5*UILayer.SCALE, 66, 24, "Back") {
			@Override
			protected void onDeClick() {
				layer.getElement("settings-menu-menu").disable();
				layer.getElement("main-menu-menu").enable();
			}
		});
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(0, 0, 1920, 1080);
	}

	@Override
	public void onOpen() {
		
	}

	@Override
	public void onClose() {
		
	}
}
