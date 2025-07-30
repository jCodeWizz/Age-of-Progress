package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UIIconButton;
import dev.codewizz.gfx.gui.elements.UIIconMenu;
import dev.codewizz.gfx.gui.elements.UITextTooltip;
import dev.codewizz.gfx.gui.layers.GameLayer;

public class ConstructionMenu extends UIIconMenu {

    public ConstructionMenu(Stage stage, GameLayer layer, UIIconButton parent) {
        super(stage, layer, parent);

        UIIconButton tileMenu = UIIconButton.create("path-icon");
        tileMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                layer.openMenu(layer.tileMenu);
            }
        });
        tileMenu.addListener(UITextTooltip.create("Tiles"));

        UIIconButton objectMenu = UIIconButton.create("construction-icon");
        objectMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                layer.openMenu(layer.objectMenu);
            }
        });
        objectMenu.addListener(UITextTooltip.create("Objects (G)"));

        UIIconButton structureMenu = UIIconButton.create("new-build-icon");
        structureMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                layer.openMenu(layer.structureMenu);
            }
        });
        structureMenu.addListener(UITextTooltip.create("Structures (B)"));

        UIIconButton manageStructureMenu = UIIconButton.create("build-icon");
        manageStructureMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                layer.openMenu(layer.manageStructureMenu);
            }
        });
        manageStructureMenu.addListener(UITextTooltip.create("Manage Structures"));

        addIcon(tileMenu);
        addIcon(objectMenu);
        addIcon(structureMenu);
        addIcon(manageStructureMenu);
    }
}
