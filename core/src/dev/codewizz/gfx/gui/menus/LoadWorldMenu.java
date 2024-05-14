package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Gdx;
import dev.codewizz.gfx.gui.UIButton;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.World;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadWorldMenu extends UIMenu {

    private List<UIButton> worldButtons = new ArrayList<>();

    public LoadWorldMenu(String id, int x, int y, int w, int h, UILayer layer) {
        super(id, x, y, w, h, layer);
    }

    @Override
    public void setup() {
        elements.add(new UIButton("back-button", Gdx.graphics.getWidth()-(66+5)*UILayer.SCALE, 5*UILayer.SCALE, 66, 24, "Back") {
            @Override
            protected void onDeClick() {
                layer.getElement("load-world-menu").disable();
                layer.getElement("main-menu-menu").enable();
            }
        });
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    @Override
    public void onOpen() {



        //TODO: fix this int buttonX = Gdx.graphics.getHeight() + w/2 - (99*UILayer.SCALE)/2;

        int index = 0;
        for(File file : Assets.folderSaves.listFiles()) {
            final String name = file.getName();

            UIButton b = new UIButton("world-"+name, 1352, UILayer.HEIGHT - 300 - 200 * index, 99, 36, name) {
                @Override
                protected void onDeClick() {
                    Main.inst.openWorld(World.openWorld(name));

                    boolean hideButtons = !Main.inst.world.showInfoStartMenu;

                    Main.inst.renderer.ui.getElement("manage-icon").setAvailable(hideButtons);
                    Main.inst.renderer.ui.getElement("path-icon").setAvailable(hideButtons);
                    Main.inst.renderer.ui.getElement("people-icon").setAvailable(hideButtons);
                    Main.inst.renderer.ui.getElement("tool-icon").setAvailable(hideButtons);
                    Main.inst.renderer.ui.getElement("construction-icon").setAvailable(hideButtons);
                }
            };

            worldButtons.add(b);
            Main.inst.renderer.ui.elements.add(b);
            index++;
        }
    }

    @Override
    public void onClose() {
        if(Main.inst.renderer != null) {
            Main.inst.renderer.ui.elements.removeAll(worldButtons);
            worldButtons.clear();
        }
    }
}
