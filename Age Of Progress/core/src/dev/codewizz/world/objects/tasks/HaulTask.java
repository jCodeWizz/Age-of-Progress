package dev.codewizz.world.objects.tasks;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.codewizz.main.Main;
import dev.codewizz.world.Cell;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;

public class HaulTask extends Task {

	private Hermit hermit;
	public List<Item> items;
	
	public HaulTask(List<Item> items) {
		this.items = new CopyOnWriteArrayList<>();
		
		for(Item i : items) {
			i.setHauled(true);
			this.items.add(i);
		}
		
		this.shouldRestart = false;
	}
	
	@Override
	public void finish() {
		hermit.finishCurrentTask();
	}

	@Override
	public void stop() {
		for(Item i : items) {
			i.setHauled(false);
		}
		
		for(Item i : hermit.getInventory().getItems()) {
			i.setX(hermit.getX());
			i.setY(hermit.getY());
			i.setHauled(false);
			Main.inst.world.objects.add(i);
			hermit.getInventory().removeItem(i);
		}
		
		hermit.getAgent().stop();
		hermit.finishCurrentTask();
	}

	@Override
	public void start(TaskableObject object) {
		this.hermit = (Hermit) object;
		
		Cell cell = Main.inst.world.getCell(items.get(0).getX(), items.get(0).getY());
		
		object.getAgent().setGoal(cell, object.getX(), object.getY());
		if(object.getAgent().path.isEmpty())
			reach();
		started = true;
	}

	@Override
	public void reach() {
		if(items.isEmpty()) {
			
			for(Item item : hermit.getInventory().getItems()) {
				Main.inst.world.settlement.getInventory().addItem(item);
				hermit.getInventory().removeItem(item);
			}
			
			finish();
		} else {
			Main.inst.world.objects.remove(items.get(0));
			hermit.getInventory().addItem(items.get(0));
			items.remove(0);
			
			if(items.isEmpty()) {
				Cell cell = hermit.getSettlement().getCell();
				
				hermit.getAgent().setGoal(cell, hermit.getX(), hermit.getY());
				if(hermit.getAgent().path.isEmpty())
					reach();
				
			} else {
				Cell cell = Main.inst.world.getCell(items.get(0).getX(), items.get(0).getY());
				
				hermit.getAgent().setGoal(cell, hermit.getX(), hermit.getY());
				if(hermit.getAgent().path.isEmpty())
					reach();
			}
		}
		
	}

	@Override
	public void reset() {
		
	}

	@Override
	public void update(float d) {
		
	}

	@Override
	public String getName() {
		return "Moving Items";
	}
}