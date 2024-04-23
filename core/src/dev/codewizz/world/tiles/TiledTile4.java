package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;

public class TiledTile4 extends PathTile {

	public TiledTile4() {
		this.id = "aop:tiled-tile-4";
		this.texture = Assets.getSprite("tiled-tile-4");
		this.template = "aop:tiled-tile-4";
		this.templateGround = "aop:grass-tile";
		
		this.name = "Tiled Path Tile 4";
		
		this.cost = 1;
	}

}
