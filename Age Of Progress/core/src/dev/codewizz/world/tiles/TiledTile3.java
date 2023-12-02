package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;

public class TiledTile3 extends PathTile {

	public TiledTile3(Cell cell) {
		super(cell);

		this.id = "aop:tiled-tile-3";
		this.texture = Assets.getSprite("tiled-tile-3");
		this.template = "aop:tiled-tile-3";
		this.templateGround = "aop:grass-tile";
		
		this.name = "Tiled Path Tile 3";
		
		this.cost = 1;
	}

}
