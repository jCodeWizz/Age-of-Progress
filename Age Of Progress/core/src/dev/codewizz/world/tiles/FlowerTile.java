package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;
import dev.codewizz.world.TileType;

public class FlowerTile extends Tile {

	public FlowerTile(Cell cell) {
		super(cell);

		this.name = "Flower Tile";
		this.texture = Assets.getSprite("flower-tile-1");
		this.type = TileType.Flower;
		this.cost = 5;
	}

}
