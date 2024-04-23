package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;

public class TiledTile5 extends PathTile {

	public TiledTile5() {
		this.id = "aop:tiled-tile-5";
		this.texture = Assets.getSprite("tiled-tile-5");
		this.template = "aop:tiled-tile-5";
		this.templateGround = "aop:grass-tile";
		
		this.name = "Tiled Path Tile 5";
		
		this.cost = 1;
	}

}
