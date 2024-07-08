package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.codewizz.gfx.gui.*;
import dev.codewizz.input.MouseInput;
import dev.codewizz.input.TileSelector;
import dev.codewizz.main.Main;
import dev.codewizz.modding.events.CreateBuildingEvent;
import dev.codewizz.modding.events.Event;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.building.Building;
import dev.codewizz.world.building.BuildingObject;
import dev.codewizz.world.building.Wall;

import java.awt.*;

public class StructureMenu extends UIMenu {

    private UIImage fade1;
    private UIImage fade2;
    private UIImage fade3;

    private UIIcon finish;
    private UIIcon add;
    private UIIconToggle door;
    private UIIconToggle remove;
    private UIText info;

    private Building current;

    public StructureMenu(String id, int x, int y, int w, int h, UILayer layer) {
        super(id, x, y, w, h, layer);
    }

    @Override
    public void setup() {
        this.x = 100;
        this.y = 100;
        this.h = (8 + 27 * (4)) * UILayer.SCALE;

        fade1 = new UIImage("back-1-" + id, x, y, 28 * UILayer.SCALE, h, new Sprite(UILayer.fadeTex), 1);
        fade2 = new UIImage("back-2-" + id, x, y, 28 * UILayer.SCALE, h, new Sprite(UILayer.fadeTex), 1);
        fade3 = new UIImage("back-3-" + id, x, y, 28 * UILayer.SCALE, h, new Sprite(UILayer.fadeTex), 1);

        elements.add(fade1);
        elements.add(fade2);
        elements.add(fade3);

        finish = new UIIcon("finish-icon", x + 3 * UILayer.SCALE, y, 22, 24, "done-icon") {
            @Override
            protected void onDeClick() {
                if (!current.getRooms().isEmpty() && Event.dispatch(new CreateBuildingEvent(current))) {
                    if(remove.isToggled()) { remove.setToggled(false); }
                    if(door.isToggled()) { door.setToggled(false); }

                    Main.inst.world.settlement.buildings.add(current);
                    current = null;
                    close();
                }
            }
        };
        elements.add(finish);
        add = new UIIcon("add-icon", x + 3 * UILayer.SCALE, y, 22, 24, "plus-icon") {
            @Override
            protected void onDeClick() {
                if(remove.isToggled()) { remove.setToggled(false); }
                if(door.isToggled()) { door.setToggled(false); }

                MouseInput.tileArea = TileSelector.room(current);
            }
        };
        elements.add(add);

        door = new UIIconToggle("door-icon", x + 3 * UILayer.SCALE, y, 22, 24, "door-icon") {
            @Override
            protected void onDeClick() {
                if(remove.isToggled()) { remove.setToggled(false); }
            }
        };
        elements.add(door);

        remove = new UIIconToggle("remove-icon", x + 3 * UILayer.SCALE, y, 22, 24, "close-icon") {

            @Override
            protected void onDeClick() {
                if(door.isToggled()) { door.setToggled(false); }
            }
        };
        elements.add(remove);

        info = new UIText("info-text", x + 3 * UILayer.SCALE, y, "", 2 * UILayer.SCALE);
        elements.add(info);

        update();
    }

    @Override
    public void clicked(Cell cell) {
        if (cell.getObject() != null && cell.getObject() instanceof BuildingObject) {
           BuildingObject o = (BuildingObject) cell.getObject();
           if(!o.getRoom().getBuilding().equals(current)) {
                current = o.getRoom().getBuilding();
           }
        }
    }

    @Override
    public void clicked(GameObject object) {
        if (object instanceof Wall) {
            if (door.isToggled()) {
                Wall wall = (Wall) object;

                if (wall.getId().equals("aop:wall")) {
                    wall.makeDoor();
                }
            } else if (remove.isToggled()) {
                Wall wall = (Wall) object;

                Wall[] walls = ((BuildingObject)wall.getCell().getObject()).getWalls();

                for(int i = 0; i < walls.length; i++) {
                    if(walls[i] != null && walls[i].equals(wall)) {
                        ((BuildingObject)wall.getCell().getObject()).setWall(i, null);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch b) {
        super.render(b);

        if(current != null) {
            info.setText(current.getName() + ", " + current.getRooms().size());
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    @Override
    public void onOpen() {

        if (current == null) {
            current = new Building();
        }
    }

    @Override
    public void onClose() {

    }

    public void update() {
        int y = UILayer.HEIGHT / 2 - h / 2;

        finish.setX(3 * UILayer.SCALE);
        add.setX(3 * UILayer.SCALE);
        door.setX(3 * UILayer.SCALE);
        remove.setX(3 * UILayer.SCALE);
        info.setX(3 * UILayer.SCALE);

        finish.setY(y + 3 * UILayer.SCALE);
        add.setY(y + 30 * UILayer.SCALE);
        door.setY(y + 57 * UILayer.SCALE);
        remove.setY(y + 84 * UILayer.SCALE);
        info.setY(y + 114 * UILayer.SCALE);

        fade1.setX(0);
        fade2.setX(0);
        fade3.setX(0);

        fade1.setY(y);
        fade2.setY(y);
        fade3.setY(y);
    }
}
