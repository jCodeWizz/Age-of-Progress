package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;
import dev.codewizz.world.TileType;

public class ClayTile extends Tile {

	public ClayTile(Cell cell) {
		super(cell);

		this.name = "Clay Tile";
		this.texture = Assets.getSprite("clay-tile");
		this.type = TileType.Clay;
		this.cost = 5;
	}

}
