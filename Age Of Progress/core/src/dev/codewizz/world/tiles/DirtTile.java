package dev.codewizz.world.tiles;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;
import dev.codewizz.world.TileType;
import dev.codewizz.world.objects.hermits.Jobs;
import dev.codewizz.world.objects.tasks.PlantCropTask;

public class DirtTile extends Tile {

	public DirtTile(Cell cell) {
		super(cell);

		this.type = TileType.Dirt;
		this.texture = Assets.getSprite("dirt-tile");
	
		this.name = "Dirt Tile";
		
		this.cost = 5;
	}
	
	@Override
	public void onPlace() {
		Main.inst.world.settlement.addTask(new PlantCropTask(this.cell, Jobs.Farmer), false);
	}
}
