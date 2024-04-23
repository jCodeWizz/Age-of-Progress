package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;

public class TiledTile6 extends PathTile {

	public TiledTile6() {
		this.id = "aop:tiled-tile-6";
		this.texture = Assets.getSprite("tiled-tile-6");
		this.template = "aop:tiled-tile-6";
		this.templateGround = "aop:grass-tile";
		
		this.name = "Tiled Path Tile 6";
		
		this.cost = 1;
	}

}
