package dev.codewizz.world.objects.tasks;

import dev.codewizz.gfx.Animation;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.world.Cell;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.hermits.Jobs;
import dev.codewizz.world.settlement.Crop;
import dev.codewizz.world.settlement.CropType;
import dev.codewizz.world.tiles.FarmTile;

public class PlantCropTask extends Task{

	private Hermit hermit;
	private Cell cell;
	private Animation animation = new Animation(-10f, 0, 0.1f, Assets.getSprite("hermit-axe-1"), Assets.getSprite("hermit-axe-2"));
	private float counter = 0f;
	private float duration = 2f;
	private boolean reached = false;
	
	public PlantCropTask(Cell cell, Jobs... jobs) {
		super(jobs);
		
		this.cell = cell;
	}
	
	@Override
	public void finish() {
		Main.inst.world.settlement.crops.add(new Crop(cell, CropType.carrots1));
		
		cell.setTile(new FarmTile(cell));
		cell.tile.setCurrentSprite(CropType.carrots1.getSprite());
		
		hermit.setTaskAnimation(null);
		hermit.finishCurrentTask();
	}

	@Override
	public void stop() {
		hermit.getAgent().stop();
		hermit.setTaskAnimation(null);
		hermit.finishCurrentTask();
	}

	@Override
	public void start(TaskableObject object) {
		this.hermit = (Hermit) object;
		
		hermit.getAgent().setGoal(cell, hermit.getX(), hermit.getY());
		if(hermit.getAgent().path.isEmpty())
			reach();
		
		started = true;
		
	}

	@Override
	public void reach() {
		hermit.setFacing(Direction.West);
		hermit.setTaskAnimation(animation);
		reached = true;
	}

	@Override
	public void reset() {
		
	}

	@Override
	public void update(float d) {
		if(reached) {
			if(counter < duration) {
				counter += d;
			} else {
				finish();
			}
		}
	}

	@Override
	public String getName() {
		return "Planting Crop";
	}
}
