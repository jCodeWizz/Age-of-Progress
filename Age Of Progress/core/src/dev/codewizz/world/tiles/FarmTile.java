package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;
import dev.codewizz.world.TileType;

public class FarmTile extends Tile {

	public FarmTile(Cell cell) {
		super(cell);

		this.type = TileType.Farm;
		this.texture = Assets.getSprite("farm-tile");
		this.name = "Farm Tile";
		this.cost = 5;
	}
}
