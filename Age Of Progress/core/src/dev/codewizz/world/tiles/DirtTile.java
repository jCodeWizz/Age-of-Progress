package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Tile;

public class DirtTile extends Tile {

	public DirtTile() {
		this.id = "aop:dirt-tile";
		this.texture = Assets.getSprite("dirt-tile");
	
		this.name = "Dirt Tile";
		
		this.cost = 5;
	}
}
