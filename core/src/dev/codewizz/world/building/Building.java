package dev.codewizz.world.building;

import com.badlogic.gdx.graphics.Color;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Utils;
import dev.codewizz.world.Cell;
import dev.codewizz.world.objects.IGatherable;
import dev.codewizz.world.objects.tasks.GatherTask;
import dev.codewizz.world.tiles.MudTile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Building {

    private final static ArrayList<Color> BUILDING_COLOURS = new ArrayList<>();

    static {
        BUILDING_COLOURS.add(new Color(Color.BLUE).sub(0f, 0f, 0f, 0.6f));
        BUILDING_COLOURS.add(new Color(Color.CYAN).sub(0f, 0f, 0f, 0.6f));
        BUILDING_COLOURS.add(new Color(Color.MAGENTA).sub(0f, 0f, 0f, 0.6f));
        BUILDING_COLOURS.add(new Color(Color.PINK).sub(0f, 0f, 0f, 0.6f));
        BUILDING_COLOURS.add(new Color(Color.ORANGE).sub(0f, 0f, 0f, 0.6f));
        BUILDING_COLOURS.add(new Color(Color.YELLOW).sub(0f, 0f, 0f, 0.6f));
    }

    private final List<Room> rooms = new CopyOnWriteArrayList<>();

    private String name;
    private String style;
    private Color color;

    public Building() {
        name = "Build " + (Main.inst.world.settlement.buildings.size() + 1);
        style = "default";
        color = BUILDING_COLOURS.get(Utils.RANDOM.nextInt(BUILDING_COLOURS.size()));
    }

    public void addRoom(Room room) {
        rooms.add(room);

        for (Cell cell : new ArrayList<>(room.getArea())) {
            if (cell.object == null) {
                cell.setObject(new BuildingObject(cell.x, cell.y, cell, room));
                cell.setTile(new MudTile());
            }
        }

        for (Cell cell : room.getArea()) {
            if (cell.getObject() != null && cell.getObject() instanceof IGatherable) {
                Main.inst.world.settlement.addTask(new GatherTask(cell.getObject()), true);
            }

            ((BuildingObject) cell.getObject()).init();
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public String getName() {
        return name;
    }

    public String getStyle() {
        return style;
    }

    public Color getColor() {
        return color;
    }
}
