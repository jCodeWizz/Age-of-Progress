package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.TileType;

public class DirtPathTile extends PathTile {

	public DirtPathTile(Cell cell) {
		super(cell);

		this.type = TileType.DirtPath;
		this.texture = Assets.getSprite("dirt-tile");
		this.template = TileType.Dirt;
		this.templateGround = TileType.Base;
		
		this.name = "Dirt Path Tile";
		
		this.cost = 1;
	}

}
