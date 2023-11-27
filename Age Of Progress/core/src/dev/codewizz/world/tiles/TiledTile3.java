package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.TileType;

public class TiledTile3 extends PathTile {

	public TiledTile3(Cell cell) {
		super(cell);

		this.type = TileType.Tiled_3;
		this.texture = Assets.getSprite("tiled-tile-3");
		this.template = TileType.Tiled_3;
		this.templateGround = TileType.Base;
		
		this.name = "Tiled Path Tile 3";
		
		this.cost = 1;
	}

}
