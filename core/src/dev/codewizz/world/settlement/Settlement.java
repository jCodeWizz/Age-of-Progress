package dev.codewizz.world.settlement;

import dev.codewizz.world.items.ItemType;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import dev.codewizz.gfx.Renderable;
import dev.codewizz.gfx.gui.UINotification;
import dev.codewizz.gfx.gui.menus.NotificationMenu;
import dev.codewizz.main.Main;
import dev.codewizz.modding.events.Event;
import dev.codewizz.modding.events.HermitJoinEvent;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.building.Building;
import dev.codewizz.world.items.Inventory;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.tasks.GrowCropTask;
import dev.codewizz.world.objects.tasks.HaulTask;
import dev.codewizz.world.objects.tasks.Task;

public class Settlement {

	private float x, y;
	private float timer = 0, max_timer = 5f;
	private Cell cell;
	
	public List<Hermit> members = new CopyOnWriteArrayList<>();
	public Queue<Task> taskTree = new Queue<>();
	public List<GameObject> objects = new CopyOnWriteArrayList<>();
	public List<Crop> crops = new CopyOnWriteArrayList<>();
	public List<FarmArea> areas = new CopyOnWriteArrayList<>();
	public List<Building> buildings = new CopyOnWriteArrayList<>();
	
	public Inventory inventory;

	private Settlement() {
		this.inventory = new Inventory(-1);


		inventory.addItem(new Item(ItemType.WOOD, 100));
		inventory.addItem(new Item(ItemType.PLANKS, 100));
	}

	public Settlement(Cell cell) {
		this();
		this.x = cell.x + 32;
		this.y = cell.y + 32;
		this.cell = cell;
	}

	public Settlement(float x, float y) {
		this();
		this.x = x;
		this.y = y;
	}

	public void update(float dt) {

		if (timer < max_timer)
			timer += dt;
		else {
			timer = 0f;
			checkHaulTask();
		}
		
		if(Main.inst.world.nature.day) {
			for(Crop crop : crops) {
				if(crop.isReady()) {
					if(!crop.tasked) {
						crop.task();
						addTask(new GrowCropTask(crop), true);
					}
				} else {
					crop.counter += dt;
				}
			}
		}
	}

	public Hermit addHermit(float x, float y) {

		
		Hermit hermit = new Hermit(x, y);

		hermit.setSettlement(this);
		members.add(hermit);
		Main.inst.world.addObject(hermit);

		((NotificationMenu) Main.inst.renderer.ui.getElement("notification-menu")).addNotification(new UINotification(
				"A new Hermit arrived!", "Give " + hermit.getName() + " a warm welcome! (And a meal!)", "people-icon"));
		;
		
		Event.dispatch(new HermitJoinEvent(hermit, this));

		return hermit;
	}

	private void checkHaulTask() {
		List<Item> items = new CopyOnWriteArrayList<>();

		for (Renderable r : Main.inst.world.getObjects()) {
			if (r instanceof Item) {
				Item i = (Item) r;
				if (!i.isTasked()) {
					items.add(i);
				}
			}
		}

		List<Item> t = new CopyOnWriteArrayList<>();
		for (int i = 0; i < items.size(); i++) {
			t.add(items.get(i));

			if (t.size() > 5) {
				HaulTask task = new HaulTask(t);
				taskTree.addLast(task);
				t = new CopyOnWriteArrayList<>();
			}
		}

		if (!t.isEmpty()) {
			HaulTask task = new HaulTask(t);
			taskTree.addLast(task);
		}
	}

	public Hermit addHermit(Hermit hermit) {

		hermit.setSettlement(this);
		members.add(hermit);

		return hermit;
	}

	public Queue<Task> getTasks() {
		return taskTree;
	}

	public void addTask(Task task, boolean prio) {

		if (prio) {
			taskTree.addFirst(task);
		} else {
			taskTree.addLast(task);
		}
	}

	public Cell getCell() {
		if(cell == null) this.cell = Main.inst.world.getCell(x, y);
		return cell;
	}

	public void removeTask(Task task) {
		taskTree.removeValue(task, false);
	}

	public Vector2 getLocation() {
		return new Vector2(x, y);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
}
