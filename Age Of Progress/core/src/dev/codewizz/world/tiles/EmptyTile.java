package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Tile;

public class EmptyTile extends Tile {

	public EmptyTile() {
		this.id = "aop:empty-tile";
		this.texture = Assets.getSprite("empty-tile");
		this.name = "Empty Tile";
		
		this.cost = -1;
	}

}
