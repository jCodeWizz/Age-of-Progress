package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.gui.UIElement;
import dev.codewizz.gfx.gui.UIIcon;
import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.menus.BuildingMenu;
import dev.codewizz.gfx.gui.menus.DebugMenu;
import dev.codewizz.gfx.gui.menus.NotificationMenu;
import dev.codewizz.gfx.gui.menus.PathingMenu;
import dev.codewizz.gfx.gui.menus.PauseMenu;
import dev.codewizz.gfx.gui.menus.PeopleMenu;
import dev.codewizz.gfx.gui.menus.SelectMenu;
import dev.codewizz.gfx.gui.menus.SettingsGameMenu;
import dev.codewizz.gfx.gui.menus.SettlementMenu;
import dev.codewizz.gfx.gui.menus.StartInfoMenu;
import dev.codewizz.input.AreaSelector;
import dev.codewizz.input.MouseInput;
import dev.codewizz.main.Main;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.objects.IGatherable;
import dev.codewizz.world.objects.tasks.GatherTask;

public class GameLayer extends UILayer {

	private PathingMenu pathingMenu;
	private BuildingMenu buildingMenu;
	private PauseMenu pauseMenu;
	private SettingsGameMenu settingsMenu;
	private SelectMenu selectMenu;
	private StartInfoMenu startInfoMenu;
	private DebugMenu debugMenu;
	private NotificationMenu notificationMenu;
	private SettlementMenu settlementMenu;
	private PeopleMenu peopleMenu;

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
		elements.add(new UIIcon("build-icon", (WIDTH / 2) - (22 * SCALE) / 2, 6 * SCALE, 22, 24, "build-icon") {
			@Override
			protected void onDeClick() {
				if (!pauseMenu.isEnabled()) {
					UIElement e = buildingMenu;
					if (e.isEnabled())
						e.disable();
					else {
						closeMenus();
						e.enable();
					}
				}
			}
		});

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
		elements.add(new UIIcon("tool-icon", (WIDTH / 2) - (-90 * SCALE) / 2, 6 * SCALE, 22, 24, "icon", "icon-pressed",
				"icon-unavailable", "tool-icon") {
			@Override
			protected void onDeClick() {
				MouseInput.area = new AreaSelector() {
					@Override
					public void handle(GameObject obj) {
						if(obj instanceof IGatherable) {
							if(((IGatherable) obj).ready()) {
								obj.setSelected(true);
								Main.inst.world.settlement.addTask(new GatherTask(obj), false);
							}
						}
					}
				};
			}
		});

		// BACKGROUND
		elements.add(new UIImage("icon-background", (WIDTH / 2) - (146 * SCALE) / 2, 0, 146, 30, "icon-board"));
		elements.add(new UIImage("icon-background-extension", 0, 0, Gdx.graphics.getWidth() / 3, 30, "icon-board-extension"));

		// PATH MENU
		pathingMenu = new PathingMenu("pathingMenu", 0, 0, 128, 260, this);
		pathingMenu.disable();
		elements.add(pathingMenu);

		// BUILDING MENU
		buildingMenu = new BuildingMenu("buildingMenu", 0, 0, 128, 260, this);
		buildingMenu.disable();
		elements.add(buildingMenu);
		
		// SETTLEMENT MENU
		settlementMenu = new SettlementMenu("settlementMenu", UILayer.WIDTH/2 - (531/2) * UILayer.SCALE, UILayer.HEIGHT/2 - 298/2 * UILayer.SCALE, 531, 298, this);
		settlementMenu.disable();
		elements.add(settlementMenu);
		
		// SETTLEMENT MENU
		peopleMenu = new PeopleMenu("peopleMenu", UILayer.WIDTH / 2 - (531 / 2) * UILayer.SCALE, UILayer.HEIGHT / 2 - 298 / 2 * UILayer.SCALE, 531, 298, this);
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
		selectMenu = new SelectMenu("selectMenu", 6, 6, 160, 107, this);
		selectMenu.disable();
		elements.add(selectMenu);
		
		// INFO MENU START
		startInfoMenu = new StartInfoMenu("startInfoMenu", WIDTH / 2 - 160 * UILayer.SCALE, HEIGHT / 2 - 107 * UILayer.SCALE, 320, 214, this);
		if(Main.inst.world.showInfoSartMenu) {
			startInfoMenu.enable();
		} else {
			startInfoMenu.disable();
		}
		elements.add(startInfoMenu);
		
		debugMenu = new DebugMenu("debugMenu", 0, 0, 0, 0, this);
		elements.add(debugMenu);
		debugMenu.enable();
		
		// NOTIFICATION MENU
		notificationMenu = new NotificationMenu("notification-menu", UILayer.WIDTH - NotificationMenu.notificationWidth * UILayer.SCALE - 4 * UILayer.SCALE, UILayer.HEIGHT - NotificationMenu.notificationHeight * UILayer.SCALE - 4 * UILayer.SCALE, 200, 100, this);
		
		elements.add(notificationMenu);
		notificationMenu.enable();
		
	}

	@Override
	public void render(SpriteBatch b) {
		if(selectedObject != null) selectedObject.updateUICard();
		super.render(b);
	}
}
