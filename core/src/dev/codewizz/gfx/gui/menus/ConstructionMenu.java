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

		// BUILDING ICON
        addIcon(new UIIcon("structure-icon", "build-icon") {
            @Override
            protected void onDeClick() {
                if (!layer.getElement("pauseMenu").isEnabled()) {
                    UIElement e = layer.getElement("structureMenu");
                    if (e.isEnabled())
                        e.disable();
                    else {
                        Main.inst.renderer.ui.closeMenus();
                        e.enable();
                    }
                }
            }
        });

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

        // BUILDING ICON
        addIcon(new UIIcon("construction-icon", "construction-icon") {
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
