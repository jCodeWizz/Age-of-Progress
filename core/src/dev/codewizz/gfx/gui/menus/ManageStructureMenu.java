package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.building.Building;
import dev.codewizz.world.building.BuildingObject;
import dev.codewizz.world.building.Room;

public class ManageStructureMenu extends Menu {

    private Building current;

    public ManageStructureMenu(Stage stage, GameLayer layer) {
        super(stage, layer);
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
    public void clickedOn(Cell cell) {
        if (cell.getObject() != null && cell.getObject() instanceof BuildingObject) {
            BuildingObject o = (BuildingObject) cell.getObject();
            if(!o.getRoom().getBuilding().equals(current)) {
                current = o.getRoom().getBuilding();
            }
        }
    }

    @Override
    protected void setup() {

    }
}
