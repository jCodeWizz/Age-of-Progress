package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.graphics.g2d.Sprite;
import dev.codewizz.gfx.gui.UIIcon;
import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.input.MouseInput;
import dev.codewizz.input.TileSelector;
import dev.codewizz.main.Main;
import dev.codewizz.world.building.Building;

import java.awt.*;

public class StructureMenu extends UIMenu {

    private UIImage fade1;
    private UIImage fade2;
    private UIImage fade3;

    private UIIcon finish;
    private UIIcon add;

    private Building current;

    public StructureMenu(String id, int x, int y, int w, int h, UILayer layer) {
        super(id, x, y, w, h, layer);
    }

    @Override
    public void setup() {
        this.x = 100;
        this.y = 100;
        this.h = (9+24*(2)) * UILayer.SCALE;

        fade1 = new UIImage("back-1-" + id, x, y, 28 * UILayer.SCALE, h, new Sprite(UILayer.fadeTex), 1);
        fade2 = new UIImage("back-2-" + id, x, y, 28 * UILayer.SCALE, h, new Sprite(UILayer.fadeTex), 1);
        fade3 = new UIImage("back-3-" + id, x, y, 28 * UILayer.SCALE, h, new Sprite(UILayer.fadeTex), 1);

        elements.add(fade1);
        elements.add(fade2);
        elements.add(fade3);

        finish = new UIIcon("finish-icon", x + 3 * UILayer.SCALE, y, 22, 24, "done-icon") {
            @Override
            protected void onDeClick() {
                Main.inst.world.settlement.buildings.add(current);
                current = null;
                close();
            }
        };
        elements.add(finish);
        add = new UIIcon("add-icon", x + 3 * UILayer.SCALE, y, 22, 24, "plus-icon") {
            @Override
            protected void onDeClick() {
                MouseInput.tileArea = TileSelector.room(current);
            }
        };
        elements.add(add);

        update();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    @Override
    public void onOpen() {

        if(current == null) {
            current = new Building();
        }
    }

    @Override
    public void onClose() {

    }

    public void update() {
        int y = UILayer.HEIGHT/2 - h/2;

        finish.setX(3 * UILayer.SCALE);
        add.setX(3 * UILayer.SCALE);

        finish.setY(y + 3 * UILayer.SCALE);
        add.setY(y + 30 * UILayer.SCALE);

        fade1.setX(0);
        fade2.setX(0);
        fade3.setX(0);

        fade1.setY(y);
        fade2.setY(y);
        fade3.setY(y);
    }
}
