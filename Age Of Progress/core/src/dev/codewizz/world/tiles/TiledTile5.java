package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.TileType;

public class TiledTile5 extends PathTile {

	public TiledTile5(Cell cell) {
		super(cell);

		this.type = TileType.Tiled_5;
		this.texture = Assets.getSprite("tiled-tile-5");
		this.template = TileType.Tiled_5;
		this.templateGround = TileType.Base;
		
		this.name = "Tiled Path Tile 5";
		
		this.cost = 1;
	}

}
