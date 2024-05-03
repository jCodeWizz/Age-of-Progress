package dev.codewizz.gfx.gui.menus;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.gfx.gui.UIText;
import dev.codewizz.input.MouseInput;
import dev.codewizz.main.Main;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Nature;

public class DebugMenu extends UIMenu {

    UIText nature;
    UIText settlement;
    UIText rendering;
    UIText time;

    UIText currentCell;
    UIText currentCellInfo;
    UIText currentCellConnections;
    UIText currentCellObject;

    private UIImage fade1;
    private UIImage fade2;

    private boolean close = false;

    public DebugMenu(String id, int x, int y, int w, int h, UILayer layer) {
        super(id, x, y, w, h, layer);

    }

    @Override
    public void setup() {

        fade1 = new UIImage("fade", 5, UILayer.HEIGHT - 295, 710, 290, new Sprite(UILayer.fadeTex),
                            1);
        fade2 = new UIImage("fade", 5, UILayer.HEIGHT - 295, 710, 290, new Sprite(UILayer.fadeTex),
                            1);

        elements.add(new UIText("debug-text", 20, UILayer.HEIGHT - 20, "DEBUG MODE", 10));

        rendering = new UIText("debug-text-rendering", 20, UILayer.HEIGHT - 60, "", 8);
        elements.add(rendering);

        nature = new UIText("debug-text-nature", 20, UILayer.HEIGHT - 85, "", 8);
        elements.add(nature);

        settlement = new UIText("debug-text-settlement", 20, UILayer.HEIGHT - 110, "", 8);
        elements.add(settlement);

        time = new UIText("debug-text-time", 20, UILayer.HEIGHT - 135, "", 8);
        elements.add(time);

        currentCell = new UIText("debug-text-current-cell", 20, UILayer.HEIGHT - 185,
                                 "Current Cell>>", 8);
        elements.add(currentCell);

        currentCellInfo = new UIText("debug-text-current-cell", 20, UILayer.HEIGHT - 210, "", 8);
        elements.add(currentCellInfo);

        currentCellConnections = new UIText("debug-text-current-cell", 20, UILayer.HEIGHT - 235, "",
                                            8);
        elements.add(currentCellConnections);

        currentCellObject = new UIText("debug-text-current-cell", 20, UILayer.HEIGHT - 260, "", 8);
        elements.add(currentCellObject);

        fade1.setBackground(true);
        elements.add(fade1);

        fade2.setBackground(true);
        elements.add(fade2);
    }

    public void updateData() {

        rendering.setText(
                "Rendering>> FPS: " + Gdx.graphics.getFramesPerSecond() + " O: " + Main.inst.world.getObjects()
                        .size() + " P: " + Main.inst.world.particles.size());

        nature.setText(
                "Nature>> Timer: " + (int) Main.inst.world.nature.spawnCounter + " | Cap: " + Main.inst.world.nature.animals.size() + " / " + Nature.ANIMAL_CAP);

        if (Main.inst.world.settlement != null) {
            settlement.setText(
                    "Settlement>> Loc: { " + (int) Main.inst.world.settlement.getX() + " ; " + (int) Main.inst.world.settlement.getY() + " } | Size: " + Main.inst.world.settlement.members.size() + " | Areas: " + Main.inst.world.settlement.areas.size());
        } else {
            settlement.setText("Settlement>> No Settlement");
        }

        Nature n = Main.inst.world.nature;

        float l = ((int) (n.light * 100)) / 100f;

        if (n.transition) {
            time.setText(
                    "Time>> Day: " + n.day + " | " + (int) n.timeCounter + " / " + Nature.TRANSITION_TIME + " | Light: " + l);
        } else {
            time.setText(
                    "Time>> Day: " + n.day + " | " + (int) n.timeCounter + " / " + Nature.DAY_TIME + " | Light: " + l);
        }

        if (MouseInput.hoveringOverCell != null) {
            currentCellInfo.setText(MouseInput.hoveringOverCell.getTile()
                                            .getName() + " | Cost: " + MouseInput.hoveringOverCell.getTile()
                    .getCost() + " | X: " + (int) MouseInput.hoveringOverCell.x + " Y: " + (int) MouseInput.hoveringOverCell.y);

            int c = 0;
            int link = 0;

            if (Main.inst.world.cellGraph.getConnections(MouseInput.hoveringOverCell) != null) {
                c = Main.inst.world.cellGraph.getConnections(MouseInput.hoveringOverCell).size;
            }
            if (Main.inst.world.cellGraph.getLinks(MouseInput.hoveringOverCell) != null) {
                link = Main.inst.world.cellGraph.getLinks(MouseInput.hoveringOverCell).size;
            }

            String connections = "C: " + c + " L: " + link + " B:";

            Cell[] neighbours = MouseInput.hoveringOverCell.getAllNeighbours();

            for (int i = 0; i < neighbours.length; i++) {
                if (neighbours[i] != null) {
                    connections += " " + neighbours[i].acceptConnections[(i + 4) % 8];
                } else {
                    connections += " NULL";
                }
            }
            currentCellConnections.setText(connections);

            if (MouseInput.hoveringOverCell.getObject() != null) {
                currentCellObject.setText("Object: " + MouseInput.hoveringOverCell.getObject()
                        .getName() + " | " + MouseInput.hoveringOverCell.getObject()
                        .getId() + " | Flipped: " + MouseInput.hoveringOverCell.getObject()
                        .isFlip());
            } else {
                currentCellObject.setText("Object: none");
            }
        } else {
            currentCellInfo.setText("");
            currentCellConnections.setText("");
            currentCellObject.setText("");
        }
    }

    @Override
    public void render(SpriteBatch b) {
        updateData();
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(0, 0, 0, 0);
    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {
        if(!close) {
			this.enable();
		}
    }

	public void closeDebugUI() {
		close = !close;
		if(close) {
			this.disable();
		} else {
			this.enable();
		}
	}

}
