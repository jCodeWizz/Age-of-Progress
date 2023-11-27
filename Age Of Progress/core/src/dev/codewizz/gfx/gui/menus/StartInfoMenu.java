package dev.codewizz.gfx.gui.menus;

import java.awt.Rectangle;

import dev.codewizz.gfx.gui.UIIcon;
import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.gfx.gui.UIText;
import dev.codewizz.main.Main;

public class StartInfoMenu extends UIMenu {
private boolean[] states = null;
	
	public StartInfoMenu(String id, int x, int y, int w, int h, UILayer layer) {
		super(id, x, y, w, h, layer);
	}

	@Override
	public void setup() {
		
		
		
		elements.add(0, new UIIcon("close-button", x + w*UILayer.SCALE - 1 - 14*UILayer.SCALE,
				y + h*UILayer.SCALE - 15*UILayer.SCALE - 2, 14, 15, "close-icon") {
			@Override
			protected void onDeClick() {
				close();
			}
		});
		
		elements.add(new UIText("startinginfomenu-title", x + 13, y + h * UILayer.SCALE - 13, "Start Information", UILayer.SCALE * 3));
		elements.add(new UIText("startinginfomenu-title", x + 13, y + h * UILayer.SCALE - 65, w*UILayer.SCALE - 25,
				"Greetings Traveller, (n) (n) " +
				"I hope your journey thru time and space went well. Welcome to your lands, your people have been waiting for your guidance. (n) (n) " +
				"Your goal is to grow your settlement both in size and thru time. Use research to unlock new technologies and make your settlement a better place to attract new settlers. (n) (n) " +
				"To get started: (n) (n) " +
				" - First you will want to mark your settlement in these wild lands. Go to the ‘buildings’ menu and place down the flag on the spot where you want to start your settlement. (n) (n) " +
				" "
				, (int)(UILayer.SCALE * 3.5f), 8f));
		
		elements.add(new UIImage("background", x, y, w, h, "info-menu"));
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
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
		
		states[0] = true;
		states[1] = false;
		states[2] = false;
		states[3] = false;
		states[4] = false;
	}

	@Override
	public void onClose() {
		Main.PAUSED = false;
		UILayer.FADE = false;
		
		if(states == null) {
			setStates();
		}
		
		layer.getElement("build-icon").setAvailable(states[0]);
		layer.getElement("tool-icon").setAvailable(states[1]);
		layer.getElement("people-icon").setAvailable(states[2]);
		layer.getElement("manage-icon").setAvailable(states[3]);
		layer.getElement("path-icon").setAvailable(states[4]);
	}
}