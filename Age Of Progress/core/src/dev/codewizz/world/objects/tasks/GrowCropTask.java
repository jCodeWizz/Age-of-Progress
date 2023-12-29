package dev.codewizz.world.objects.tasks;

import dev.codewizz.gfx.Animation;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.utils.Utils;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.hermits.Jobs;
import dev.codewizz.world.settlement.Crop;
import dev.codewizz.world.settlement.CropType;

public class GrowCropTask extends Task{

	private Hermit hermit;
	private Crop crop;
	private Animation animation = new Animation(-10f, 0, 0.1f, Assets.getSprite("hermit-axe-1"), Assets.getSprite("hermit-axe-2"));
	private float counter = 0f;
	private float duration = 2f;
	private boolean reached = false;
	
	public GrowCropTask(Crop crop, Jobs... jobs) {
		super(jobs);
		
		this.crop = crop;
	}
	
	@Override
	public void finish() {

		if(crop.type.getNext() == CropType.empty) {
			
			for(int i = 0; i < Utils.getRandom(crop.type.getItem().getSize(), crop.type.getMax()); i++) {
				Item item = new Item(crop.cell.x + 25 + Utils.getRandom(-20, 20), crop.cell.y + 25 + Utils.getRandom(-10, 10), crop.type.getItem().getType());
				Main.inst.world.addItem(item);
			}
		}
		
		crop.type = crop.type.getNext();
		crop.cell.tile.setCurrentSprite(crop.type.getSprite());
		crop.tasked = false;
		
		if(crop.type == CropType.empty) {
			Main.inst.world.settlement.crops.remove(crop);
		} else {
			crop.counter = 0;
		}

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
		hermit.getAgent().setGoal(crop.cell);
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
		return "Taking care of crops";
	}
}
