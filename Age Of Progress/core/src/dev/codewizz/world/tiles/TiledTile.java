package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.TileType;

public class TiledTile extends PathTile {

	public TiledTile(Cell cell) {
		super(cell);

		this.type = TileType.Tiled_1;
		this.texture = Assets.getSprite("tiled-tile-1");
		this.template = TileType.Tiled_1;
		this.templateGround = TileType.Base;
		
		this.name = "Tiled Path Tile 1";
		
		this.cost = 1;
	}

}
