package dev.codewizz.world;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import box2dLight.PointLight;
import dev.codewizz.gfx.Renderable;
import dev.codewizz.main.Main;
import dev.codewizz.modding.events.Reason;
import dev.codewizz.utils.Utils;
import dev.codewizz.world.objects.Animal;
import dev.codewizz.world.objects.Cow;
import dev.codewizz.world.objects.Herd;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.tasks.SleepTask;

public class Nature {

	public static final float TRANSITION_TIME = 60f;
	public static final float DAY_TIME = 12f*60f - TRANSITION_TIME;	
	public static final float DAY_LIGHT = 1f;
	public static final float NIGHT_LIGHT = 0.2f;
	
	public World world;
	public static final int ANIMAL_CAP = 25;
	
	public List<Animal> animals = new CopyOnWriteArrayList<>();
	public List<Herd> herds = new CopyOnWriteArrayList<>();

	// spawnCounter is decreased by 1 every second.
	public float spawnCounter = Utils.getRandom(100, 200);
	public float timeCounter = 0;
	public boolean transition = false;
	public boolean day = true;
	public float light = DAY_LIGHT;
	public int dayCounter = 1;
	
	public Nature(World world) {
		this.world = world;
		
		for(Renderable object : world.getObjects()) {
			if(object instanceof Animal) {
				animals.add((Animal)object);
			}
		}
		
		Main.inst.renderer.setAmbientLight(light);
	}
	
	public void update(float dt) {
		if(spawnCounter < 0) {
			if(animals.size() < ANIMAL_CAP) {
				for(int i = 0; i < 5; i++) {
					if(spawnHerd()) { 
						break;
					}
				}
			}
			spawnCounter = Utils.getRandom(100, 200);
		} else {
			spawnCounter -= dt;
		} 
		
		dayCycle(dt);
	}
	
	public void onDay() {
		dayCounter++;
	}
	
	public void onNight() {
		if(Main.inst.world.settlement != null) {
			for(Hermit h : Main.inst.world.settlement.members) {
				//SleepTask t = new SleepTask();
				//h.addTask(t, true);
			}
		}
		
	}
	
	public boolean spawnHerd() {
		Herd herd = new Herd();
		
		int size = Utils.getRandom(3, 11);
		Cell cell = world.getRandomCell();
		
		for(int i = 0; i < size; i++) {
			
			Cow cow = new Cow(cell.x + Utils.getRandom(0, 256), cell.y + Utils.getRandom(0, 80));
			if(addAnimal(cow)) {
				herd.addMember(cow);
			}
		}
		
		if(herd.getMembers().size() > 1) {
			herd.setLeader();
			addHerd(herd);
			
			return true;
		} else {
			for(Animal a : herd.getMembers()) {
				this.removeAnimal(a);
			}
			herd.clear();
			
			return false;
		}
	}
	
	public boolean addAnimal(Animal a) {
		List<Cell> cells = world.findCell(a.getX(), a.getY(), 4, false, "aop:grass-tile", "aop:flower-tile");
		if(!cells.isEmpty()) {
			Cell cell = cells.get(cells.size()-1);
			
			if(cell == null) { return false; }
			
			a.setX(cell.x);
			a.setY(cell.y);
			
			world.addObject(a, Reason.ANIMAL_NATURE);
			animals.add(a);
			
			return true;
		}
		
		
		
		return false;
	}
	
	public Herd addHerd(Herd h) {
		herds.add(h);
		return h;
	}
	
	public void removeAnimal(Animal a) {
		animals.remove(a);
		world.removeObject(a);
	}
	
	private void dayCycle(float dt) {
		if(!transition) {
			if(timeCounter < DAY_TIME) {
				timeCounter += dt;
			} else {
				transition = true;
				
				for(PointLight l : Main.inst.renderer.lights) {
					l.setActive(day);
				}
				
				timeCounter = 0;
			}
		} else {
			if(timeCounter < TRANSITION_TIME) {
				timeCounter += dt;
				float lightStep = 0.8f/(TRANSITION_TIME/dt);
				if(day) {
					light = Utils.clamp(light -= lightStep, NIGHT_LIGHT, DAY_LIGHT);
				} else {
					light = Utils.clamp(light += lightStep, NIGHT_LIGHT, DAY_LIGHT);
				}
				Main.inst.renderer.setAmbientLight(light);
			} else {
				day = !day;
				if(day) {
					onDay();
					light = DAY_LIGHT;
					Main.inst.renderer.setAmbientLight(DAY_LIGHT);
				} else {
					light = NIGHT_LIGHT;
					Main.inst.renderer.setAmbientLight(NIGHT_LIGHT);
					onNight();
				}
				transition = false;
				timeCounter = 0;
			}
		}
	}
	
}