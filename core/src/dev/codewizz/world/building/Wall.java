package dev.codewizz.world.building;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
		
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.tiles.EmptyTile;

public class Wall extends GameObject {
		
	private static final Sprite TEXTURE = Assets.getSprite("wall");
	private static final Sprite TEXTURE_FLIP = Assets.getSprite("wall-flipped");
		
	protected Cell cell;
	protected Direction facing;
		
	public Wall(float x, float y, Cell cell, Direction dir) {
		super(x, y);
		
		this.id = "aop:wall";
		
		this.sortHeight = 4;
		this.cell = cell;
		this.facing = dir;

		onPlace();
	}

	public void onPlace() {
		cell.blockPath(facing);
		cell.blockPath(Direction.getFromIndex(facing.getIndex() - 1));
		cell.blockPath(Direction.getFromIndex(facing.getIndex() + 1));

		Cell neighbour = cell.getNeighbour(facing);
		neighbour.blockPath(facing.other());
		neighbour.blockPath(Direction.getFromIndex(facing.getIndex() - 1).other());
		neighbour.blockPath(Direction.getFromIndex(facing.getIndex() + 1).other());

	}

	@Override
	public void onDestroy() {
		cell.unblockPath(facing);
		cell.unblockPath(Direction.getFromIndex(facing.getIndex() - 1));
		cell.unblockPath(Direction.getFromIndex(facing.getIndex() + 1));

		Cell neighbour = cell.getNeighbour(facing);
		neighbour.unblockPath(facing.other());
		neighbour.unblockPath(Direction.getFromIndex(facing.getIndex() - 1).other());
		neighbour.unblockPath(Direction.getFromIndex(facing.getIndex() + 1).other());
	}	
		
	@Override
	public void update(float d) {
		
	}	
		
	@Override
	public void render(SpriteBatch b) {
		if(flip) {
			b.draw(TEXTURE_FLIP, (int)x, (int)y);
		} else {
			b.draw(TEXTURE, (int)x, (int)y);
		}
	}	
}		