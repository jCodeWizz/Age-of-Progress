package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;

public class GrassTile extends Tile {

	public GrassTile(Cell cell) {
		super(cell);

		this.name = "Grass Tile";
		this.texture = Assets.getSprite("grass-tile");
		this.id = "aop:grass-tile";
		this.cost = 10;
	}

}
