package dev.codewizz.world.building;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;

public class Pole extends GameObject {

	private final static Sprite TEXTURE = Assets.getSprite("pole");
	private final static int OFFSET = (int) (TEXTURE.getWidth()/2);
	
	private Cell cell;
	private Direction facing;
	
	public Pole(float x, float y, Cell cell, Direction dir) {
		super(x, y);

		this.id = "aop:pole";
		
		this.cell = cell;
		this.facing = dir;
		
		cell.blockPath(facing);
		cell.blockPath(Direction.getFromIndex(facing.getIndex() - 1));
		cell.blockPath(Direction.getFromIndex(facing.getIndex() + 1));
	}
	
	@Override
	public void onDestroy() {
		cell.unblockPath(facing);
		cell.unblockPath(Direction.getFromIndex(facing.getIndex() - 1));
		cell.unblockPath(Direction.getFromIndex(facing.getIndex() + 1));
	}

	@Override
	public void update(float d) {
		
	}

	@Override
	public void render(SpriteBatch b) {
		b.draw(TEXTURE, x - OFFSET, y);
	}
}
