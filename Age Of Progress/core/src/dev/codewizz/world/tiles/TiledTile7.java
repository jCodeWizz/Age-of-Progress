package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;

public class TiledTile7 extends PathTile {

	public TiledTile7() {
		this.id = "aop:tiled-tile-7";
		this.texture = Assets.getSprite("tiled-tile-7");
		this.template = "aop:tiled-tile-7";
		this.templateGround = "aop:grass-tile";
		
		this.name = "Tiled Path Tile 7";
		
		this.cost = 1;
	}

}
