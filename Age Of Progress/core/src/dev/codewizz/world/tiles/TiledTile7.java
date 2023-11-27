package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.TileType;

public class TiledTile7 extends PathTile {

	public TiledTile7(Cell cell) {
		super(cell);

		this.type = TileType.Tiled_7;
		this.texture = Assets.getSprite("tiled-tile-7");
		this.template = TileType.Tiled_7;
		this.templateGround = TileType.Base;
		
		this.name = "Tiled Path Tile 7";
		
		this.cost = 1;
	}

}
