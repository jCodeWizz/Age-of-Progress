package dev.codewizz.world.building;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.IBuy;

public class Wall extends GameObject implements IBuy {

	private static final Sprite TEXTURE = Assets.getSprite("wall");
	private static final Sprite TEXTURE_FLIP = Assets.getSprite("wall-flipped");
	
	private List<Item> costs = new ArrayList<>();
	private Cell cell;
	private Direction facing;
	
	public Wall(float x, float y, Cell cell, Direction dir) {
		super(x, y);

		this.id = "aop:wall";
		
		this.sortHeight = 10;
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
		if(flip) {
			b.draw(TEXTURE_FLIP, x, y);
		} else {
			b.draw(TEXTURE, x, y);
		}
	}

	@Override
	public Sprite getMenuSprite() {
		return TEXTURE;
	}

	@Override
	public String getMenuName() {
		return "Wall";
	}

	@Override
	public String getMenuDescription() {
		return "Wall :)";
	}

	@Override
	public boolean conintues() {
		return false;
	}

	@Override
	public boolean available() {
		return false;
	}

	@Override
	public void onPlace(Cell cell) {
		
	}

	@Override
	public List<Item> costs() {
		return costs;
	}
}
