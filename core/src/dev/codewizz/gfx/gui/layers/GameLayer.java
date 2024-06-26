package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.gui.UIElement;
import dev.codewizz.gfx.gui.UIIcon;
import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.menus.*;
import dev.codewizz.main.Main;
import dev.codewizz.world.GameObject;

public class GameLayer extends UILayer {

	private PathingMenu pathingMenu;
	private BuildingMenu buildingMenu;
	private StructureMenu structureMenu;
	private PauseMenu pauseMenu;
	private SettingsGameMenu settingsMenu;
	private SelectMenu selectMenu;
	private StartInfoMenu startInfoMenu;
	private DebugMenu debugMenu;
	private NotificationMenu notificationMenu;
	private SettlementMenu settlementMenu;
	private PeopleMenu peopleMenu;
	private ToolMenu toolMenu;
	private ConstructionMenu constructionMenu;

	public static GameObject selectedObject = null;

	@Override
	public void setup() {
		// MANAGE ICON
		elements.add(new UIIcon("manage-icon", (WIDTH / 2) - (134 * SCALE) / 2, 6 * SCALE, 22, 24, "manage-icon") {

			@Override
			protected void onDeClick() {
				if (!pauseMenu.isEnabled()) {
					UIElement e = settlementMenu;
					if (e.isEnabled())
						e.disable();
					else {
						closeMenus();
						e.enable();
					}
				}
			}

		});
		// PATH ICON
				elements.add(new UIIcon("path-icon", (WIDTH / 2) - (78 * SCALE) / 2, 6 * SCALE, 22, 24, "icon", "icon-pressed",
						"icon-unavailable", "path-icon") {
					@Override
					protected void onDeClick() {
						if (!pauseMenu.isEnabled()) {
							UIElement e = pathingMenu;
							if (e.isEnabled())
								e.disable();
							else {
								closeMenus();
								e.enable();
							}
						}
					}
				});
		// BUILD ICON
		UIIcon constructionIcon = new UIIcon("construction-icon", (WIDTH / 2) - (22 * SCALE) / 2, 6 * SCALE, 22, 24, "build-icon") {
			@Override
			protected void onDeClick() {
				if (!pauseMenu.isEnabled()) {
					UIElement e = constructionMenu;
					if (e.isEnabled())
						e.disable();
					else {
						closeMenus();
						e.enable();
					}
				}
			}
		};
		elements.add(constructionIcon);
		
		// PEOPLE ICON
		elements.add(new UIIcon("people-icon", (WIDTH / 2) - (-34 * SCALE) / 2, 6 * SCALE, 22, 24, "people-icon") {
			@Override
			protected void onDeClick() {
				if (!pauseMenu.isEnabled()) {
					UIElement e = peopleMenu;
					if (e.isEnabled())
						e.disable();
					else {
						closeMenus();
						e.enable();
					}
				}
			}
		});

		// TOOL ICON
		UIIcon toolIcon = new UIIcon("tool-icon", (WIDTH / 2) - (-90 * SCALE) / 2, 6 * SCALE, 22, 24, "icon",
				"icon-pressed", "icon-unavailable", "tool-icon") {
			@Override
			protected void onDeClick() {
				if (!pauseMenu.isEnabled()) {
					UIElement e = toolMenu;
					if (e.isEnabled())
						e.disable();
					else {
						closeMenus();
						e.enable();
					}
				}
			}
		};
		elements.add(toolIcon);

		toolMenu = new ToolMenu("tool-menu", (WIDTH / 2) - (-90 * SCALE) / 2 - 3 * SCALE, 3 * SCALE, this, toolIcon);
		toolMenu.disable();
		elements.add(toolMenu);
		
		constructionMenu = new ConstructionMenu("construction-menu", (WIDTH / 2) - (28 * SCALE) / 2, 3 * SCALE, this, constructionIcon);
		constructionMenu.disable();
		elements.add(constructionMenu);

		// PATH MENU
		pathingMenu = new PathingMenu("pathingMenu", 0, 0, 128, 260, this);
		pathingMenu.disable();
		elements.add(pathingMenu);

		// BUILDING MENU
		buildingMenu = new BuildingMenu("buildingMenu", 0, 0, 128, 260, this);
		buildingMenu.disable();
		elements.add(buildingMenu);

		// CONSTRUCTION MENU
		structureMenu = new StructureMenu("structureMenu", 0, 0, 128, 260, this);
		structureMenu.disable();
		elements.add(structureMenu);

		// SETTLEMENT MENU
		settlementMenu = new SettlementMenu("settlementMenu", UILayer.WIDTH / 2 - (531 / 2) * UILayer.SCALE,
				UILayer.HEIGHT / 2 - 298 / 2 * UILayer.SCALE, 531, 298, this);
		settlementMenu.disable();
		elements.add(settlementMenu);

		// SETTLEMENT MENU
		peopleMenu = new PeopleMenu("peopleMenu", UILayer.WIDTH / 2 - (531 / 2) * UILayer.SCALE,
				UILayer.HEIGHT / 2 - 298 / 2 * UILayer.SCALE, 531, 298, this);
		peopleMenu.disable();
		elements.add(peopleMenu);

		// PAUSE MENU
		pauseMenu = new PauseMenu("pauseMenu", 0, 0, 100, 100, this);
		pauseMenu.disable();
		elements.add(pauseMenu);

		// SETTINGS MENU
		settingsMenu = new SettingsGameMenu("settingsMenu", 0, 0, 100, 100, this);
		settingsMenu.disable();
		elements.add(settingsMenu);

		// SELECTMENU
		selectMenu = new SelectMenu("selectMenu", ((WIDTH / 2) - (146 * SCALE) / 2) / 2 - 75 * UILayer.SCALE, 0, 150,
				50, this);
		selectMenu.disable();
		elements.add(selectMenu);

		// INFO MENU START
		startInfoMenu = new StartInfoMenu("startInfoMenu", WIDTH / 2 - 160 * UILayer.SCALE,
				HEIGHT / 2 - 107 * UILayer.SCALE, 320, 214, this);
		if (Main.inst.world.showInfoStartMenu) {
			startInfoMenu.enable();
		} else {
			startInfoMenu.disable();
		}
		elements.add(startInfoMenu);

		debugMenu = new DebugMenu("debugMenu", 0, 0, 0, 0, this);
		elements.add(debugMenu);
		debugMenu.disable();

		// NOTIFICATION MENU
		notificationMenu = new NotificationMenu("notification-menu",
				UILayer.WIDTH - NotificationMenu.notificationWidth * UILayer.SCALE - 4 * UILayer.SCALE,
				UILayer.HEIGHT - NotificationMenu.notificationHeight * UILayer.SCALE - 4 * UILayer.SCALE, 200, 100,
				this);

		elements.add(notificationMenu);
		notificationMenu.enable();

		// BACKGROUND
		elements.add(new UIImage("icon-background", (WIDTH / 2) - (146 * SCALE) / 2, 0, 146, 30, "icon-board")
				.setBackground(true));
		elements.add(
				new UIImage("icon-background-extension", 0, 0, Gdx.graphics.getWidth() / 3, 30, "icon-board-extension")
						.setBackground(true));

	}

	@Override
	public void render(SpriteBatch b) {
		if (selectedObject != null)
			selectedObject.updateUICard(selectMenu);
		super.render(b);
	}
}
