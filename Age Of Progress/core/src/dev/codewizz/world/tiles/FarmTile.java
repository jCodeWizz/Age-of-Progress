package dev.codewizz.world.tiles;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.Tile;
import dev.codewizz.world.objects.hermits.Jobs;
import dev.codewizz.world.objects.tasks.PlantCropTask;

public class FarmTile extends Tile {

	public FarmTile() {
		this.id = "aop:farm-tile";
		this.texture = Assets.getSprite("farm-tile");
		this.name = "Farm Tile";
		this.cost = 5;
	}
	
	@Override
	public void onPlace() {
		PlantCropTask task = new PlantCropTask(cell, Jobs.Farmer);
		Main.inst.world.settlement.addTask(task, true);
	}
}
