package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;

public class TiledTile2 extends PathTile {

	public TiledTile2(Cell cell) {
		super(cell);

		this.id = "aop:tiled-tile-2";
		this.texture = Assets.getSprite("tiled-tile-2");
		this.template = "aop:tiled-tile-2";
		this.templateGround = "aop:grass-tile";
		
		this.name = "Tiled Path Tile 2";
		
		this.cost = 1;
	}

}
