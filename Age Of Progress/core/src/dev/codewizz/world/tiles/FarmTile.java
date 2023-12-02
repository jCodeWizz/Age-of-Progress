package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;

public class FarmTile extends Tile {

	public FarmTile(Cell cell) {
		super(cell);

		this.id = "aop:farm-tile";
		this.texture = Assets.getSprite("farm-tile");
		this.name = "Farm Tile";
		this.cost = 5;
	}
}
