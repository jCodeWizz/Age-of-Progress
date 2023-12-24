package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.Gdx;

import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.menus.MainMenu;
import dev.codewizz.gfx.gui.menus.SettingsMainMenu;

public class MainMenuLayer extends UILayer {

	private MainMenu mainMenu;
	private SettingsMainMenu settingsMenu;
	
	@Override
	public void setup() {
		mainMenu = new MainMenu("main-menu-menu", 0, 0, 1920, 1080, this);
		elements.add(mainMenu);
		mainMenu.enable();
		
		settingsMenu = new SettingsMainMenu("settings-menu-menu", 0, 0, 1920, 1080, this);
		settingsMenu.disable();
		elements.add(settingsMenu);

		elements.add(new UIImage("main-menu-background", 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), "main-menu-background", 1));
	}
}
