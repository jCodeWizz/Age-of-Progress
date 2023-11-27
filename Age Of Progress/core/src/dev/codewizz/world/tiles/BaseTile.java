package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;
import dev.codewizz.world.TileType;

public class BaseTile extends Tile {

	public BaseTile(Cell cell) {
		super(cell);

		this.name = "Base Tile";
		this.texture = Assets.getSprite("base-tile");
		this.type = TileType.Base;
		this.cost = 10;
	}

}
