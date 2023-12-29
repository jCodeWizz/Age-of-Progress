package dev.codewizz.world.objects.tasks;

import dev.codewizz.gfx.Renderable;
import dev.codewizz.main.Main;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.Recipe;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;

public class CraftTask extends Task {

	private Hermit hermit;
	private Recipe recipe;
	
	private boolean pickup = false;
	
	public CraftTask(Recipe recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void finish() {
		hermit.finishCurrentTask();
	}

	@Override
	public void stop() {
		hermit.getAgent().stop();
		hermit.finishCurrentTask();
		
		if(pickup) {
			for(Item i : hermit.getInventory().getItems()) {
				Item item = new Item(hermit.getX(), hermit.getY(), i.getType(), i.getSize());
				Main.inst.world.addItem(item);
			}
		}
	}

	@Override
	public void start(TaskableObject object) {
		this.hermit = (Hermit) object;
		
		hermit.getAgent().setGoal(hermit.getSettlement().getCell());
		if(hermit.getAgent().path.isEmpty())
			reach();
		
		started = true;
	}

	@Override
	public void reach() {
		if(pickup) {
			
			for(int i = 0; i < recipe.getCosts().length; i++) {
				hermit.getInventory().removeItem(recipe.getCosts()[i]);
			}
			hermit.getInventory().addItem(new Item(0, 0, recipe.getResult().getType(), recipe.getResult().getSize()));
			
			finish();
		} else {
			pickup = true;
			
			for(int i = 0; i < recipe.getCosts().length; i++) {
				if(!hermit.getSettlement().getInventory().containsItem(recipe.getCosts()[i], recipe.getCosts()[i].getSize())) {
					hermit.getSettlement().addTask(new CraftTask(this.recipe), true);
					stop();
				}
			}
			
			for(int i = 0; i < recipe.getCosts().length; i++) {
				hermit.getSettlement().getInventory().removeItem(recipe.getCosts()[i]);
				hermit.getInventory().addItem(recipe.getCosts()[i]);
			}
			
			for(Renderable r : Main.inst.world.getObjects()) {
				if(r instanceof GameObject) {
					GameObject object = (GameObject) r;
					if(object.getId().equals("aop:stump")) {
						
						hermit.getAgent().setGoal(Main.inst.world.getCell(object.getX() + 24, object.getY() + 25));
						if(hermit.getAgent().path.isEmpty())
							reach();
					}
				}
			}
		}
	}
	
	@Override
	public boolean canTake(Hermit hermit) {
		
		
		if(!jobs.contains(hermit.getJob().getJob()) && !jobs.isEmpty()) {
			return false;
		}
		
		for(int i = 0; i < recipe.getCosts().length; i++) {
			if(!hermit.getSettlement().getInventory().containsItem(recipe.getCosts()[i], recipe.getCosts()[i].getSize())) {
				return false;
			}
		}
		
		if(hermit.getInventory().getSizeAvailable() < recipe.getCosts().length) {
			return false;
		}
		
		return true;
	}

	@Override
	public void reset() {
		if(pickup) {
			for(Item i : hermit.getInventory().getItems()) {
				Item item = new Item(hermit.getX(), hermit.getY(), i.getType(), i.getSize());
				Main.inst.world.addItem(item);
			}
			pickup = false;
		}
	}

	@Override
	public void update(float d) {
		
	}

	@Override
	public String getName() {
		return "Crafting " + recipe.getResult().getType().toString();
	}
}
