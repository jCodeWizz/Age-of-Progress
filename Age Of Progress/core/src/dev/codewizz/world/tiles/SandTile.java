package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;
import dev.codewizz.world.TileType;

public class SandTile extends Tile {

	public SandTile(Cell cell) {
		super(cell);

		this.name = "Sand Tile";
		this.texture = Assets.getSprite("sand-tile");
		this.type = TileType.Sand;
		this.cost = 5;
	}

}
