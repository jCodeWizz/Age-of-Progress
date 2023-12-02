package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;

public class SandTile extends Tile {

	public SandTile(Cell cell) {
		super(cell);

		this.name = "Sand Tile";
		this.texture = Assets.getSprite("sand-tile");
		this.id = "aop:sand-tile";
		this.cost = 5;
	}

}
