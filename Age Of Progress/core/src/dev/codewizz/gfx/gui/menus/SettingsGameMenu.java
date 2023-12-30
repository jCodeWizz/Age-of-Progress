package dev.codewizz.gfx.gui.menus;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;

import dev.codewizz.gfx.gui.UIButton;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.main.Main;

public class SettingsGameMenu extends UIMenu {

	private boolean[] states = null;
	
	
	public SettingsGameMenu(String id, int x, int y, int w, int h, UILayer layer) {
		super(id, x, y, w, h, layer);

	}

	@Override
	public void setup() {
		elements.add(new UIButton("back-button", Gdx.graphics.getWidth()-(66+5)*UILayer.SCALE, 5*UILayer.SCALE, 66, 24, "Back") {
			@Override
			protected void onDeClick() {
				layer.getElement("settingsMenu").disable();
				layer.getElement("pauseMenu").enable();
			}
		});
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(0, 0, 1920, 1080);
	}

	@Override
	public void onOpen() {
		Main.PAUSED = true;
		UILayer.FADE = true;
		
		setStates();
		
		layer.getElement("build-icon").setAvailable(false);
		layer.getElement("tool-icon").setAvailable(false);
		layer.getElement("people-icon").setAvailable(false);
		layer.getElement("manage-icon").setAvailable(false);
		layer.getElement("path-icon").setAvailable(false);
	}
	
	private void setStates() {
		
		if(states == null) {
			states = new boolean[5];
		}
		
		states[0] = layer.getElement("construction-icon").isAvailable();
		states[1] = layer.getElement("tool-icon").isAvailable();
		states[2] = layer.getElement("people-icon").isAvailable();
		states[3] = layer.getElement("manage-icon").isAvailable();
		states[4] = layer.getElement("path-icon").isAvailable();
	}

	@Override
	public void onClose() {
		Main.PAUSED = false;
		UILayer.FADE = false;
		
		if(states == null) {
			setStates();
		}
		
		layer.getElement("construction-icon").setAvailable(states[0]);
		layer.getElement("tool-icon").setAvailable(states[1]);
		layer.getElement("people-icon").setAvailable(states[2]);
		layer.getElement("manage-icon").setAvailable(states[3]);
		layer.getElement("path-icon").setAvailable(states[4]);
	}
}
