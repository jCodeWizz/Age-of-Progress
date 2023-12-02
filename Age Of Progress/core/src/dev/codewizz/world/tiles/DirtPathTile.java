package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;

public class DirtPathTile extends PathTile {

	public DirtPathTile(Cell cell) {
		super(cell);

		this.id = "aop:dirt-path-tile";
		this.texture = Assets.getSprite("dirt-tile");
		this.template = "aop:dirt-tile";
		this.templateGround = "aop:grass-tile";
		
		this.name = "Dirt Path Tile";
		
		this.cost = 1;
	}

}
