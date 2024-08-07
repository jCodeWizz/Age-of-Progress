package dev.codewizz.world.building;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.codewizz.gfx.Particle;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;

import java.awt.*;

public class Wall extends GameObject {
		
	private static final Sprite TEXTURE = Assets.getSprite("stone-wall");
	private static final Sprite TEXTURE_FLIP = Assets.getSprite("stone-wall-flipped");
		
	protected Direction facing;
		
	public Wall(float x, float y, Cell cell, Direction dir) {
		super(x, y);
		
		this.id = "aop:wall";
		
		if(dir == Direction.North || dir == Direction.West) {
			this.sortHeight = 2;
		} else {
			this.sortHeight = 7;
		}


		this.cell = cell;
		this.facing = dir;
	}

	public void onPlace() {
		cell.blockPath(facing);
		cell.blockPath(Direction.getFromIndex(facing.getIndex() - 1));
		cell.blockPath(Direction.getFromIndex(facing.getIndex() + 1));

		Cell neighbour = cell.getAllNeighbours()[facing.getIndex()];;
		neighbour.blockPath(facing.other());
		neighbour.blockPath(Direction.getFromIndex(facing.getIndex() - 1).other());
		neighbour.blockPath(Direction.getFromIndex(facing.getIndex() + 1).other());

	}

	@Override
	public void onDestroy() {
		cell.unblockPath(facing);
		cell.unblockPath(Direction.getFromIndex(facing.getIndex() - 1));
		cell.unblockPath(Direction.getFromIndex(facing.getIndex() + 1));

		Cell neighbour = cell.getAllNeighbours()[facing.getIndex()];;

		neighbour.unblockPath(facing.other());
		neighbour.unblockPath(Direction.getFromIndex(facing.getIndex() - 1).other());
		neighbour.unblockPath(Direction.getFromIndex(facing.getIndex() + 1).other());
	}

	@Override
	public Polygon getHitBox() {
		if(flip) {
			return new Polygon( new int[] {(int)x, (int)x, (int)x + 32, (int)x + 32}, new int[] {(int)y + 16, (int)y + 48, (int)y + 32, (int)y}, 4) ;
		} else {
			return new Polygon( new int[] {(int)x, (int)x, (int)x + 32, (int)x + 32}, new int[] {(int)y, (int)y + 32, (int)y + 48, (int)y + 16}, 4) ;
		}
	}

	public void makeDoor() {
		WallDoor door = new WallDoor(x, y, cell, facing);
		door.setFlip(flip);

		BuildingObject o = (BuildingObject) cell.getObject();
		Wall[] walls = o.getWalls();

		for(int i = 0; i < walls.length; i++) {
			if(walls[i] != null && walls[i].equals(this)) {
				o.setWall(i, door);
				break;
			}
		}

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