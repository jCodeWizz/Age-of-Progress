package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.TileType;

public class TiledTile6 extends PathTile {

	public TiledTile6(Cell cell) {
		super(cell);

		this.type = TileType.Tiled_6;
		this.texture = Assets.getSprite("tiled-tile-6");
		this.template = TileType.Tiled_6;
		this.templateGround = TileType.Base;
		
		this.name = "Tiled Path Tile 6";
		
		this.cost = 1;
	}

}
