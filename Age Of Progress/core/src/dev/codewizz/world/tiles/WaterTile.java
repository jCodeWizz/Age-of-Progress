package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;
import dev.codewizz.world.TileType;

public class WaterTile extends Tile {

	public WaterTile(Cell cell) {
		super(cell);

		this.type = TileType.Water;
		this.texture = Assets.getSprite("water-tile");
		this.name = "Water";
		this.cost = -1;
	}

}
