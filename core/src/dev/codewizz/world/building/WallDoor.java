package dev.codewizz.world.building;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.world.Cell;

public class WallDoor extends Wall {

    private static final Sprite TEXTURE = Assets.getSprite("wall-door");
    private static final Sprite TEXTURE_FLIP = Assets.getSprite("wall-door-flipped");

    public WallDoor(float x, float y, Cell cell, Direction dir) {
        super(x, y, cell, dir);
    }

    @Override
    public void onPlace(Cell cell) {
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
        if (flip) {
            b.draw(TEXTURE_FLIP, (int) x, (int) y);
        } else {
            b.draw(TEXTURE, (int) x, (int) y);
        }
    }
}
