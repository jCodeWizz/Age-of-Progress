package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.TileType;

public class TiledTile4 extends PathTile {

	public TiledTile4(Cell cell) {
		super(cell);

		this.type = TileType.Tiled_4;
		this.texture = Assets.getSprite("tiled-tile-4");
		this.template = TileType.Tiled_4;
		this.templateGround = TileType.Base;
		
		this.name = "Tiled Path Tile 4";
		
		this.cost = 1;
	}

}
