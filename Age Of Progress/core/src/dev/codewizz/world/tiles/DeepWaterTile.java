package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;

public class DeepWaterTile extends Tile {

	public DeepWaterTile(Cell cell) {
		super(cell);

		this.id = "aop:deep-water-tile";
		this.texture = Assets.getSprite("deep-water-tile");
		this.name = "Deep Water";
		this.cost = -1;
	}

}
