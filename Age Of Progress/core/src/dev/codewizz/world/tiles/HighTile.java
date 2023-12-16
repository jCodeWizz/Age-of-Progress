package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;

public class HighTile extends Tile {

	public HighTile(Cell cell) {
		super(cell);

		this.name = "High Tile";
		this.texture = Assets.getSprite("high-tile");
		this.id = "aop:high-tile";
		this.cost = -1;
	}

}
