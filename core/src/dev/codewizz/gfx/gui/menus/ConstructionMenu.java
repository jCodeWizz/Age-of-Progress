package dev.codewizz.gfx.gui.menus;

import dev.codewizz.gfx.gui.UIElement;
import dev.codewizz.gfx.gui.UIIcon;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.main.Main;

public class ConstructionMenu extends UIIconMenu {

	public ConstructionMenu(String id, int x, int y, UILayer layer, UIIcon parent) {
		super(id, x, y, layer, parent);

	}

	@Override
	public void setup() {
		super.setup();

		// PATH ICON
		addIcon(new UIIcon("path-icon", "path-icon") {
			@Override
			protected void onDeClick() {
				if (!layer.getElement("pauseMenu").isEnabled()) {
					UIElement e = layer.getElement("pathingMenu");
					if (e.isEnabled())
						e.disable();
					else {
						Main.inst.renderer.ui.closeMenus();
						e.enable();
					}
				}
			}
		});

		// BUILD ICON
		addIcon(new UIIcon("build-icon", "build-icon") {
			@Override
			protected void onDeClick() {
				if (!layer.getElement("pauseMenu").isEnabled()) {
					UIElement e = layer.getElement("buildingMenu");
					if (e.isEnabled())
						e.disable();
					else {
						Main.inst.renderer.ui.closeMenus();
						e.enable();
					}
				}
			}
		});

	}
}
