package dev.codewizz.world.building;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.codewizz.utils.Direction;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.Cell;
import dev.codewizz.world.tiles.EmptyTile;
import dev.codewizz.world.tiles.FlowerTile;

public class WallDoor extends Wall {

    public WallDoor(float x, float y, Cell cell, Direction dir) {
        super(x, y, cell, dir);
    }

    @Override
    public void onPlace() {
        cell.blockPath(Direction.getFromIndex(facing.getIndex() - 1));
        cell.blockPath(Direction.getFromIndex(facing.getIndex() + 1));
    }

    @Override
    public void onDestroy() {
        cell.unblockPath(Direction.getFromIndex(facing.getIndex() - 1));
        cell.unblockPath(Direction.getFromIndex(facing.getIndex() + 1));
    }

    @Override
    public void render(SpriteBatch b) {
    }
}
