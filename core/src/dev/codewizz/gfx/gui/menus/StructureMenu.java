package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Gdx;
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
import dev.codewizz.modding.events.Reason;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.building.*;
import dev.codewizz.world.objects.ConstructionObject;
import dev.codewizz.world.objects.tasks.ChangeObjectTask;
import dev.codewizz.world.objects.tasks.DestroyObjectTask;
import dev.codewizz.world.objects.tasks.Task;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class StructureMenu extends Menu implements IUpdateDataMenu {

    private Building current;

    private UIIconToggle doorIcon;
    private UIIconToggle removeIcon;
    private UILabel info;

    public CopyOnWriteArrayList<GameObject> objects = new CopyOnWriteArrayList<>();
    private HashMap<GameObject, Task> changes = new HashMap<>();

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


                if (!Main.inst.world.settlement.buildings.contains(current) && !current.getRooms().isEmpty() && Event.dispatch(new CreateBuildingEvent(current))) {
                    Main.inst.world.settlement.buildings.add(current);

                    for (Task t : changes.values()) {
                        Main.inst.world.settlement.addTask(t, false);
                    }

                    for (GameObject object : objects) {
                        ConstructionObject c = new ConstructionObject((Wall) object);
                        Main.inst.world.addObject(c, Reason.FORCED);
                    }

                    changes.clear();
                    objects.clear();

                    current = null;
                    close();
                } else if (Main.inst.world.settlement.buildings.contains(current)) {
                    for (GameObject object : objects) {
                        ConstructionObject c = new ConstructionObject((Wall) object);
                        Main.inst.world.addObject(c, Reason.FORCED);
                    }

                    for (Task t : changes.values()) {
                        Main.inst.world.settlement.addTask(t, false);
                    }

                    changes.clear();
                    objects.clear();

                    current = null;
                    close();
                }

                //TODO: add error message when not closing menu.

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
        if (current != null) {
            if (Gdx.input.isButtonJustPressed(0)) {
                for (GameObject object : objects) {
                    if (object.getHitBox().contains(MouseInput.coords.x, MouseInput.coords.y) && object instanceof Wall) {
                        clickedOn(object);
                        break;
                    }
                }
            }

            for (Building building : Main.inst.world.settlement.buildings) {
                if(!building.equals(current)) {
                    b.setColor(building.getColor());
                    for (Room room : building.getRooms()) {
                        for (Cell cell : room.getArea()) {
                            b.draw(Assets.getSprite("tile-highlight"), cell.x, cell.y);
                        }
                    }
                }
            }

            b.setColor(new Color(Color.WHITE).sub(0, 0, 0, 0.4f));
            for (GameObject object : objects) {
                object.render(b);
            }

            b.setColor(Color.WHITE);
        }
    }

    @Override
    public void onOpen() {
        if (current == null) {
            current = new Building();
        }
    }

    @Override
    public void clickedOn(Cell cell) {
        if (cell.getObject() != null && cell.getObject() instanceof BuildingObject) {
            BuildingObject o = (BuildingObject) cell.getObject();
            if (!o.getRoom().getBuilding().equals(current)) {
                current = o.getRoom().getBuilding();
                changes.clear();
                Logger.log("Cleared changes");
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
                    WallDoor door = new WallDoor(wall.getX(), wall.getY(), wall.getCell(), wall.getFacing());
                    door.setFlip(wall.isFlip());

                    BuildingObject o = (BuildingObject) wall.getCell().getObject();

                    Wall[] walls = o.getWalls();

                    for (int i = 0; i < walls.length; i++) {
                        if (walls[i] != null && walls[i].equals(wall)) {
                            walls[i].onDestroy();
                            walls[i] = door;


                            if (objects.contains(object)) {
                                objects.remove(object);
                                objects.add(door);
                            } else {
                                changes.put(object, new ChangeObjectTask(door, object));
                            }
                            break;
                        }
                    }
                }
            } else if (removeIcon.isPressed()) {
                Wall wall = (Wall) object;

                Wall[] walls = ((BuildingObject) wall.getCell().getObject()).getWalls();

                for (int i = 0; i < walls.length; i++) {
                    if (walls[i] != null && walls[i].equals(wall)) {
                        ((BuildingObject) wall.getCell().getObject()).getWalls()[i] = null;
                        if (objects.contains(object)) {
                            objects.remove(object);
                        } else {
                            changes.put(object, new DestroyObjectTask(object));
                        }
                        break;
                    }
                }
            }
        }

        updateData();
    }

    @Override
    public void updateData() {
        if (current == null) {
            info.setText("");
        } else {
            info.setText(current.getName() + ", " + current.getRooms().size());
        }
    }
}
