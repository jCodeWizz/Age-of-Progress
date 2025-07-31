package dev.codewizz.world.objects.tasks;

import dev.codewizz.main.Main;
import dev.codewizz.modding.Registers;
import dev.codewizz.utils.Timer;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;
import dev.codewizz.world.items.Recipe;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Craftsman;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.hermits.Jobs;

public class CraftTask extends Task {

	private Hermit hermit;
	private final Recipe recipe;

	private boolean pickup = false;

	private final Timer pickupTimer;
	private boolean pickingup;

	private final Timer craftTimer;
	private boolean crafting;

	public CraftTask(Recipe recipe) {
		super(Jobs.Craftsman);
		this.recipe = recipe;

		craftTimer = new Timer(recipe.getTime()) {
			@Override
			public void timer() {
				finish();
			}
		};

		pickupTimer = new Timer(.5f) {
			@Override
			public void timer() {
				doPickup();
			}
		};

	}

	@Override
	public void finish() {
		for (int i = 0; i < recipe.getCosts().length; i++) {
			hermit.getInventory().removeItem(recipe.getCosts()[i]);
		}
		hermit.getInventory().addItem(new Item(recipe.getResult()[0].getType(), recipe.getResult()[0].getSize()));

		if (recipe.getResult()[0].getType().getId().equals("aop:planks") && Main.inst.world.settlement.inventory.getSizeOf(ItemType.PLANKS) + 2 < Craftsman.PLANKS_LIMIT && Craftsman.amountOfTasks == 0) {
			CraftTask t = new CraftTask(Registers.recipes.get("aop:planks"));
			Main.inst.world.settlement.addTask(t, true);
			Craftsman.PLANKS_LIMIT++;
		}

		hermit.finishCurrentTask();
		Craftsman.amountOfTasks--;
	}

	@Override
	public void stop() {
		Craftsman.amountOfTasks--;
		hermit.getAgent().stop();
		hermit.finishCurrentTask();

		if (pickup) {
			for (Item i : hermit.getInventory().getItems().values()) {
				Item item = new Item(hermit.getX(), hermit.getY(), i.getType()).size(i.getSize());
				Main.inst.world.addItem(item);
			}
		}

		craftTimer.cancel();
		pickupTimer.cancel();
	}

	@Override
	public void start(TaskableObject object) {
		this.hermit = (Hermit) object;

		hermit.getAgent().setGoal(hermit.getSettlement().getCell());
		if (hermit.getAgent().path.isEmpty())
			reach();

		started = true;
	}

	@Override
	public void reach() {
		if (pickup) {
			crafting = true;
		} else {
			for (int i = 0; i < recipe.getCosts().length; i++) {
				if (!hermit.getSettlement().getInventory().containsItem(recipe.getCosts()[i],
						recipe.getCosts()[i].getSize())) {
					hermit.getSettlement().addTask(new CraftTask(this.recipe), true);
					Craftsman.amountOfTasks++;
					stop();
				}
			}

			pickingup = true;
		}
	}

	@Override
	public boolean canTake(Hermit hermit) {
		
		if (!jobs.contains(hermit.getJob().getJob()) && !jobs.isEmpty()) {
			return false;
		}
		
		boolean found = false;

		for (GameObject object : hermit.getSettlement().objects) {
			if (object.getId().equals("aop:stump")) {
				found = true;
				break;
			}
		}

		if (!found) return false;

		for (int i = 0; i < recipe.getCosts().length; i++) {
			if (!hermit.getSettlement().getInventory().containsItem(recipe.getCosts()[i],
					recipe.getCosts()[i].getSize())) {
				return false;
			}
		}

		if (hermit.getInventory().getSizeAvailable() < recipe.getCosts().length) {
			return false;
		}

		return true;
	}

	@Override
	public void reset() {
		if (pickup) {
			for (Item i : hermit.getInventory().getItems().values()) {
				Item item = new Item(hermit.getX(), hermit.getY(), i.getType()).size(i.getSize());
				Main.inst.world.addItem(item);
			}
			pickup = false;
		}
	}

	@Override
	public void update(float d) {
		if (crafting) {
			craftTimer.update(d);
		}

		if (pickingup) {
			pickupTimer.update(d);
		}
	}

	public void doPickup() {
		for (int i = 0; i < recipe.getCosts().length; i++) {
			hermit.getSettlement().getInventory().removeItem(recipe.getCosts()[i]);
			hermit.getInventory().addItem(recipe.getCosts()[i]);
		}

		for (GameObject object : hermit.getSettlement().objects) {
			if (object.getId().equals("aop:stump")) {

				hermit.getAgent().setGoal(Main.inst.world.getCell(object.getX() + 24, object.getY() + 25));
				if (hermit.getAgent().path.isEmpty())
					reach();

				break;
			}
		}

		pickup = true;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	@Override
	public String getName() {
		return "Crafting " + recipe.getResult()[0].getType().getName();
	}
}
