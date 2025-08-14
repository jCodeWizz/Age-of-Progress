package dev.codewizz.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.saving.ChunkData;
import dev.codewizz.utils.saving.WorldDataLoader;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.utils.serialization.SerializableTile;
import dev.codewizz.world.objects.behaviour.pathfinding.CellGraph;
import dev.codewizz.world.objects.behaviour.pathfinding.Link;

import java.awt.*;
import java.util.UUID;

public abstract class Tile implements SerializableTile {

    protected Cell cell;
    protected Sprite texture;
    protected String name;
    protected String id;
    protected int cost = 1;

    public Tile() {
        this.id = "no-id";
        this.texture = Assets.getSprite("grass-tile");
        this.name = "Grass Tile";
    }

    public void onPlace() {}

    public void onDestroy() {}

    public void update() {}

    public void place() {
        Cell[] cells = this.cell.getAllNeighbours();
        for(int i = 0; i < cells.length; i++) {
            if(cells[i] != null) {
                cells[i].tile.update();
			}
        }

        CellGraph c = Main.inst.world.cellGraph;
        if(cost != -1) {
            if(c.containsCell(cell)) {
                if(c.getLinks(cell) != null) {
                    for(Link link : c.getLinks(cell)) {
                        link.setCost(cost);
                    }
                }
            } else {
                this.cell.connectAll();
            }
        } else {
            c.removeConnections(cell);

            for(int i = 0; i < cells.length; i++) {
                if(cells[i] != null) {
                    cell.acceptConnections[i] = false;
                    cells[i].connectedTo[(i + 4) % 8] = false;

                    c.removeConnection(cells[i], cell);

                    if(cells[i] != null) {
                        if(cells[i].acceptConnections[(i + 4) % 8]) {
                            c.connectCells(cell, cells[i], cells[i].tile.getCost());
                        }
                    }
                }
            }
        }
        onPlace();
    }

    public void render(SpriteBatch b) {
		b.draw(texture, cell.x, cell.y);
    }

    public Sprite getCurrentSprite() {
        return texture;
    }

    public String getName() {
        return name;
    }

    public void setCurrentSprite(Sprite texture) {
        this.texture = texture;
    }

    public Polygon getHitbox() {
        return new Polygon(cell.getXPoints(), cell.getYPoints(), 4);
    }

    public boolean[] checkNeighbours() {
        boolean[] data = new boolean[] { false, false, false, false };
        Cell[] neighbours = this.cell.getNeighbours();

        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] == null) {
                data[i] = false;
            } else {
                data[i] = neighbours[i].getTile().getId().equals(this.id);
            }
        }
        return data;
    }

    public boolean[] checkNeighbours(String id) {
        boolean[] data = new boolean[] { false, false, false, false };
        Cell[] neighbours = this.cell.getNeighbours();

        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] == null) {
                data[i] = false;
            } else {
                data[i] = neighbours[i].getTile().getId().equals(id);
            }
        }
        return data;
    }

    public String getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {

        CellGraph c = Main.inst.world.cellGraph;
        if(cost != -1) {
            if(c.containsCell(cell)) {
                for(Link link : c.getLinks(cell)) {
                    link.setCost(cost);
                }
            } else {
                Cell[] neighBours = cell.getAllNeighbours();
                for(int i = 0; i < neighBours.length; i++) {
                    if(neighBours[i] != null) {
                        c.connectCells(cell, neighBours[i], this.cost);
                    }
                }
            }
        } else {
            c.removeConnections(cell);
        }
        this.cost = cost;
    }

    public float getShaderId() {
        return 0;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }

    @Override
    public void save(ChunkData data) {

        String type = getClass().toString();
        data.addString(type.substring(6));

        if (this.cell.getObject() != null) {
            byte[] uuid = ByteUtils.toBytes(this.cell.getObject().getUUID());
            data.addArray(uuid);
        }

        data.end();
    }

    @Override
    public void load(byte[] data) {
        if (data.length >= 16) {
            UUID uuid = ByteUtils.toUUID(data, 0);
            GameObject object = WorldDataLoader.objects.remove(uuid);
            cell.setObject(object);
        }
    }
}
