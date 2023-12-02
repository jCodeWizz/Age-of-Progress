package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;

public class TiledTile8 extends PathTile {

	public TiledTile8(Cell cell) {
		super(cell);

		this.id = "aop:tiled-tile-8";
		this.texture = Assets.getSprite("tiled-tile-8");
		this.template = "aop:tiled-tile-8";
		this.templateGround = "aop:grass-tile";
		
		this.name = "Tiled Path Tile 8";
		
		this.cost = 1;
	}

}
