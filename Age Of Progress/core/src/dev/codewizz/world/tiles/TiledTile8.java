package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.TileType;

public class TiledTile8 extends PathTile {

	public TiledTile8(Cell cell) {
		super(cell);

		this.type = TileType.Tiled_8;
		this.texture = Assets.getSprite("tiled-tile-8");
		this.template = TileType.Tiled_8;
		this.templateGround = TileType.Base;
		
		this.name = "Tiled Path Tile 8";
		
		this.cost = 1;
	}

}
