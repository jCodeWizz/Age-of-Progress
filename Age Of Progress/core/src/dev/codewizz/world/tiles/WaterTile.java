package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;

public class WaterTile extends Tile {

	public WaterTile(Cell cell) {
		super(cell);

		this.id = "aop:water-tile";
		this.texture = Assets.getSprite("water-tile");
		this.name = "Water";
		this.cost = -1;
	}

}
