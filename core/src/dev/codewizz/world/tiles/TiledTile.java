package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;

public class TiledTile extends PathTile {

	public TiledTile() {
		this.id = "aop:tiled-tile-1";
		this.texture = Assets.getSprite("tiled-tile-1");
		this.template = "aop:tiled-tile-1";
		this.templateGround = "aop:grass-tile";
		
		this.name = "Tiled Path Tile 1";
		
		this.cost = 1;
	}

}
