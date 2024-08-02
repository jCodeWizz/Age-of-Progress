package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UIIconButton;
import dev.codewizz.gfx.gui.elements.UIIconToggle;
import dev.codewizz.gfx.gui.elements.UILabel;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.input.MouseInput;
import dev.codewizz.input.TileSelector;
import dev.codewizz.main.Main;
import dev.codewizz.modding.events.CreateBuildingEvent;
import dev.codewizz.modding.events.Event;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.building.Building;
import dev.codewizz.world.building.BuildingObject;
import dev.codewizz.world.building.Room;
import dev.codewizz.world.building.Wall;

public class StructureMenu extends Menu implements IUpdateDataMenu {

    private Building current;

    private UIIconToggle doorIcon;
    private UIIconToggle removeIcon;
    private UILabel info;

    public StructureMenu(Stage stage, GameLayer layer) {
        super(stage, layer);
    }

    @Override
    protected void setup() {
        Table main = new Table();
        base.add(main).expand().left();
        main.setBackground(createBackground(0.7f));

        info = UILabel.create("", UILabel.smallStyle);

        removeIcon = UIIconToggle.create("close-icon");
        doorIcon = UIIconToggle.create("door-icon");
        doorIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                doorIcon.clicked();
                removeIcon.setPressed(false);
            }
        });
        removeIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removeIcon.clicked();
                doorIcon.setPressed(false);
            }
        });

        Button addIcon = UIIconButton.create("plus-icon");
        addIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                doorIcon.setPressed(false);
                removeIcon.setPressed(false);

                MouseInput.tileArea = TileSelector.room(current);
            }
        });

        Button finishIcon = UIIconButton.create("done-icon");
        finishIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                doorIcon.setPressed(false);
                removeIcon.setPressed(false);

                if (!current.getRooms().isEmpty() && !Main.inst.world.settlement.buildings.contains(current) && Event.dispatch(new CreateBuildingEvent(current))) {
                    Main.inst.world.settlement.buildings.add(current);
                }

                current = null;
                close();
            }
        });

        main.add(info).expand().left().pad(5, 10, 0, 10);
        main.row();
        main.add(removeIcon).expand().left().size(22 * Layer.scale, 24 * Layer.scale).pad(5, 10, 5, 10);
        main.row();
        main.add(doorIcon).expand().left().size(22 * Layer.scale, 24 * Layer.scale).pad(5, 10, 5, 10);
        main.row();
        main.add(addIcon).expand().left().size(22 * Layer.scale, 24 * Layer.scale).pad(5, 10, 5, 10);
        main.row();
        main.add(finishIcon).expand().left().size(22 * Layer.scale, 24 * Layer.scale).pad(5, 10, 10, 10);

        updateData();
    }

    @Override
    public void render(SpriteBatch b) {
        if(current != null) {
            b.setColor(current.getColor());
            for(Room room : current.getRooms()) {
                for(Cell cell : room.getArea()) {
                    b.draw(Assets.getSprite("tile-highlight"), cell.x, cell.y);
                }
            }
            b.setColor(Color.WHITE);
        }
    }

    @Override
    public void onOpen() {
        if(current == null) {
            current = new Building();
        }
    }

    @Override
    public void clickedOn(Cell cell) {
        if (cell.getObject() != null && cell.getObject() instanceof BuildingObject) {
            BuildingObject o = (BuildingObject) cell.getObject();
            if(!o.getRoom().getBuilding().equals(current)) {
                current = o.getRoom().getBuilding();
            }
        }

        updateData();
    }

    @Override
    public void clickedOn(GameObject object) {
        if (object instanceof Wall) {
            if (doorIcon.isPressed()) {
                Wall wall = (Wall) object;

                if (wall.getId().equals("aop:wall")) {
                    wall.makeDoor();
                }
            } else if (removeIcon.isPressed()) {
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

        updateData();
    }

    @Override
    public void updateData() {
        if(current == null) {
            info.setText("");
        } else {
            info.setText(current.getName() + ", " + current.getRooms().size());
        }
    }
}
