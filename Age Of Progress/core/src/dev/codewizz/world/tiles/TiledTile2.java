package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.TileType;

public class TiledTile2 extends PathTile {

	public TiledTile2(Cell cell) {
		super(cell);

		this.type = TileType.Tiled_2;
		this.texture = Assets.getSprite("tiled-tile-2");
		this.template = TileType.Tiled_2;
		this.templateGround = TileType.Base;
		
		this.name = "Tiled Path Tile 2";
		
		this.cost = 1;
	}

}
