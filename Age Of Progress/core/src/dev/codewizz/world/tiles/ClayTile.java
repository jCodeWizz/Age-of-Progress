package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Tile;

public class ClayTile extends Tile {

	public ClayTile() {
		this.name = "Clay Tile";
		this.texture = Assets.getSprite("clay-tile");
		this.id = "aop:clay-tile";
		this.cost = 5;
	}
}
