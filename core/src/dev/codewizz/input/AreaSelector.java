package dev.codewizz.input;

import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.gfx.Renderable;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.objects.IGatherable;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.tasks.GatherTask;
import dev.codewizz.world.objects.tasks.Task;

public class AreaSelector {

	public Vector2 start;

	public void start(Vector2 start) {
		this.start = start;
	}

	public void end(Vector2 end) {
		
		if(start.x > end.x) {
			float a = start.x;
			start.x = end.x;
			end.x = a;
		}
		
		if(start.y > end.y) {
			float a = start.y;
			start.y = end.y;
			end.y = a;
		}
		
		int w = (int) (end.x - start.x);
		int h = (int) (end.y - start.y);

		Rectangle rec = new Rectangle((int) start.x, (int) start.y, w, h);

		Collections.reverse(Main.inst.world.getObjects());
		
		for (Renderable obj : Main.inst.world.getObjects()) {
			if(obj instanceof GameObject) {
				if (((GameObject)obj).getHitBox().intersects(rec) || rec.contains(obj.getX(), obj.getY())) {
					handle((GameObject)obj);
				}
			}
		}
	}

	public void handle(GameObject obj) {

	}
	
	public static AreaSelector delete() {
		return new AreaSelector() {
			@Override
			public void handle(GameObject obj) {
				if(!obj.getId().equals("aop:hermit")) {
					obj.destroy();
				}
			}
		};
	}
	
	public static AreaSelector harvest() {
		return new AreaSelector() {
			@Override
			public void handle(GameObject obj) {
				if(Main.inst.world.settlement != null) {
					if(obj instanceof IGatherable) {
						if(((IGatherable) obj).ready()) {
							obj.setSelected(true);
							Main.inst.world.settlement.addTask(new GatherTask(obj), false);
						}
					}
				}
			}
		};
	}
	
	/**
	 * Task any hermit in the selected area with this task. Only tasks with an empty constructor are allowed.
	 * @param t Task to task with
	 * @return the AreaSlector to use.
	 */
	public static AreaSelector taskHermit(final Class<? extends Task> t) {
		return new AreaSelector() {
			@Override
			public void handle(GameObject obj) {
				if(obj.getId().equals("aop:hermit")) {
					Hermit h = (Hermit) obj;
					
					try {
						h.addTask(t.getConstructor().newInstance(), true);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException e) {
						Logger.log("Couldn't create task: " + t.getName());
					}
				}
			}
		};
	}
}
