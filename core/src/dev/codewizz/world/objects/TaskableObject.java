package dev.codewizz.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIText;
import dev.codewizz.gfx.gui.menus.SelectMenu;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.world.objects.tasks.Task;
import dev.codewizz.world.pathfinding.Agent;

public abstract class TaskableObject extends Entity {

	protected Queue<Task> tree = new Queue<>();
	protected Task currentTask;
	protected boolean facingRight = true;
	protected Vector2 vel = new Vector2();
	
	
	protected float speed = 400f;
	protected Agent agent;
	
	public TaskableObject() {
		super();
		
		agent = new Agent(this) {
			@Override
			public void onReach() {
				reachCell();
			}
			
			@Override
			public Vector2 getMiddlePoint() {
				return getCenter();
			}
		};
	}
	
	public TaskableObject(float x, float y) {
		super(x, y);
		
		agent = new Agent(this) {
			@Override
			public void onReach() {
				reachCell();
			}
			
			@Override
			public Vector2 getMiddlePoint() {
				return getCenter();
			}
		};
	}

	@Override
	public boolean load(GameObjectDataLoader loader, GameObjectData object, boolean success) {
		super.load(loader, object, success);

		/*
		 * Check to see if tasks are ready to load?
		 */

		return success;
	}
	
	@Override
	public GameObjectData save(GameObjectData object) {
		return super.save(object);
	}
	
	public void finishCurrentTask() {
		agent.stop();
		tree.removeFirst();
		currentTask = null;
	}
	
	public void reachCell() {
		if(currentTask != null) {
			currentTask.reach();
		}
	}
	
	public void addTask(Task task, boolean prio) {
		if(prio) {
			tree.addFirst(task);
		} else {
			tree.addLast(task);
		}
	}
	
	public void addPrioTask(Task t) {
		if(this.getCurrentTask() != null) {
			Task task = this.getCurrentTask();
			task.stop();
			if(task.shouldRestart()) {
				task.reset();
				task.setStarted(false);
				addTask(task, true);
			}
		}
		
		this.getTree().addFirst(t);
		this.setCurrentTask(t);
		this.getCurrentTask().start(this);
	}

	@Override
	public void update(float d) {
		super.update(d);
		
		if(currentTask == null) {
			if(!tree.isEmpty()) {
				currentTask = tree.first();
			}
		} else {
			// TODO: maybe add a check to see if Hermit can take a task
			// TODO: on second note, I don't care about that.
			if(currentTask.isStarted()) {
				currentTask.update(d);
			} else {
				currentTask.start(this);
			}
		}
		agent.update(d, x, y);
		
		vel.x = agent.getDir().x * d * speed;
		vel.y = agent.getDir().y * d * speed;
		
		x += vel.x;
		y += vel.y;
		
		if(vel.x > 0) {
			facingRight = true;
		} else if(vel.x < 0){
			facingRight = false;
		}
	}
	
	private UIText taskText = new UIText("task-text", ((UILayer.WIDTH / 2) - (146 * UILayer.SCALE) / 2)/2 - 69 * UILayer.SCALE, (6+20) * UILayer.SCALE, "", 8);
	
	@Override
	public void renderUICard(SelectMenu m) {
		super.renderUICard(m);
		
		m.elements.add(taskText); 
	}
	
	@Override
	public void updateUICard(SelectMenu m) {
		super.updateUICard(m);

		taskText.setText(this.getCurrentTaskText());
	}
	
	public String getCurrentTaskText() {
		if(this.getCurrentTask() == null) {
			return "Idle";
		} else {
			return this.getCurrentTask().getName();
		}
	}

	public Vector2 getCenter() {
		return new Vector2(x+w/2, y+h/2);
	}

	public Task getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(Task currentTask) {
		this.currentTask = currentTask;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Queue<Task> getTree() {
		return tree;
	}

	@Override
	public abstract void render(SpriteBatch b);
}
