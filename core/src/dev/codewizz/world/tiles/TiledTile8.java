package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;

public class TiledTile8 extends PathTile {

	public TiledTile8() {
		this.id = "aop:tiled-tile-8";
		this.texture = Assets.getSprite("tiled-tile-8");
		this.template = "aop:tiled-tile-8";
		this.templateGround = "aop:grass-tile";
		
		this.name = "Tiled Path Tile 8";
		
		this.cost = 1;
	}

}
