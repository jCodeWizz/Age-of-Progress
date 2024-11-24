package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import dev.codewizz.gfx.gui.elements.UILabel;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.input.MouseInput;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Nature;
import dev.codewizz.world.World;
import dev.codewizz.world.pathfinding.Link;


public class DebugMenu extends Menu implements IUpdateDataMenu {

    private UILabel rendering;
    private UILabel nature;
    private UILabel settlement;
    private UILabel time;

    private UILabel tile;
    private UILabel connections;
    private UILabel object;

    public DebugMenu(Stage stage, GameLayer layer) {
        super(stage, layer);
    }

    @Override
    protected void setup() {
        Table main = new Table();
        main.setBackground(createBackground(0.5f));
        base.add(main).expand().left().top().padLeft(10).padTop(10);

        UILabel title = UILabel.create("DEBUG SCREEN");
        main.add(title).top().left().pad(10, 10, 5, 10);
        main.row();

        rendering = UILabel.create("", UILabel.mediumStyle);
        main.add(rendering).top().left().pad(0, 10, 3, 10);
        main.row();

        nature = UILabel.create("", UILabel.mediumStyle);
        main.add(nature).top().left().pad(0, 10, 3, 10);
        main.row();

        settlement = UILabel.create("", UILabel.mediumStyle);
        main.add(settlement).top().left().pad(0, 10, 3, 10);
        main.row();

        time = UILabel.create("", UILabel.mediumStyle);
        main.add(time).top().left().pad(0, 10, 3, 10);
        main.row();

        UILabel cell = UILabel.create("Current cell >> ", UILabel.mediumStyle);
        main.add(cell).top().left().pad(20, 10, 3, 10);
        main.row();

        tile = UILabel.create("", UILabel.mediumStyle);
        main.add(tile).top().left().pad(0, 10, 3, 10);
        main.row();

        connections = UILabel.create("", UILabel.mediumStyle);
        main.add(connections).top().left().pad(0, 10, 3, 10);
        main.row();

        object = UILabel.create("", UILabel.mediumStyle);
        main.add(object).top().left().pad(0, 10, 10, 10);
        main.row();
    }

    @Override
    public void onOpen() {
        updateData();
        Main.DEBUG = true;
    }

    @Override
    public void onClose() {
        Main.DEBUG = false;
    }

    @Override
    public void updateData() {
        World world = Main.inst.world;

        rendering.setText("Rendering >> FPS: " + Gdx.graphics.getFramesPerSecond() + " | O: " + world.getObjects().size() + " P: " + world.particles.size());
        nature.setText("Nature >> Timer: " + (int)world.nature.spawnCounter + " | Cap: " + world.nature.animals.size() + "/" + Nature.ANIMAL_CAP);
        settlement.setText("Settlement >> Size: " + world.settlement.members.size() + " | Area: " + world.settlement.areas.size() + " | Structure: " + world.settlement.buildings.size());

        if (world.nature.transition) {
            time.setText("Time >> Day: " + world.nature.day + " C: " + world.nature.dayCounter + " | " + (int)world.nature.timeCounter + "/" + (int)Nature.TRANSITION_TIME + " T: " + world.nature.transition + " | Light: " + world.nature.light);
        } else {
            time.setText("Time >> Day: " + world.nature.day + " C: " + world.nature.dayCounter +  " | " + (int)world.nature.timeCounter + "/" + (int)Nature.DAY_TIME + " T: " + world.nature.transition + " | Light: " + world.nature.light);
        }

        if (MouseInput.hoveringOverCell != null) {
            Cell cell = MouseInput.hoveringOverCell;

            tile.setText(cell.tile.getId() + " | C: " + cell.tile.getCost() + " | X: " + (int)cell.x + " Y: " + (int)cell.y);
            connections.setText("C: " + world.cellGraph.getConnections(cell).size + " L: " + world.cellGraph.getLinks(cell).size + " | ");
            if(cell.object == null) {
                object.setText("Object: none");
            } else {
                object.setText("Object: " + cell.object.getName() + " | " + cell.object.getId() + " | flip: " + cell.object.isFlip());
            }
        }
    }

    @Override
    public void render(SpriteBatch b) {
        if (MouseInput.hoveringOverCell != null) {
            Array<Connection<Cell>> connections = Main.inst.world.cellGraph.getConnections(MouseInput.hoveringOverCell);

            b.setColor(new Color(Color.RED).sub(0, 0, 0, 0.6f));
            for (Connection<Cell> connection : connections) {
                b.draw(Assets.getSprite("tile-highlight"), connection.getToNode().x, connection.getToNode().y);
            }

            Array<Link> links = Main.inst.world.cellGraph.getLinks(MouseInput.hoveringOverCell);
            b.setColor(new Color(Color.BLUE).sub(0, 0, 0, 0.6f));
            for (Link link : links) {
                b.draw(Assets.getSprite("tile-highlight"), link.getToNode().x, link.getToNode().y);
            }

            b.setColor(Color.WHITE);
        }
    }
}
