package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Tile;

public class FlowerTile extends Tile {

	public FlowerTile() {
		this.name = "Flower Tile";
		this.texture = Assets.getSprite("flower-tile-1");
		this.id = "aop:flower-tile";
		this.cost = 5;
	}

}
