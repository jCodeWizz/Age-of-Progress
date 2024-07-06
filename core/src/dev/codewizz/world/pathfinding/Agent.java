package dev.codewizz.world.pathfinding;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import dev.codewizz.main.Main;
import dev.codewizz.world.Cell;
import dev.codewizz.world.objects.Animal;
import dev.codewizz.world.objects.TaskableObject;

public class Agent {

	private Cell previousCell;
	public Queue<Cell> path = new Queue<Cell>();
	public boolean moving = false;
	private CellGraph graph;
	private Vector2 dir;

	public Cell goal;

	private TaskableObject object;

	public Agent(TaskableObject object) {
		dir = new Vector2();
		this.object = object;

		graph = Main.inst.world.cellGraph;
	}

	public void update(float d, Vector2 loc) {

		if (graph == null)
			graph = Main.inst.world.cellGraph;

		checkDistance(loc);
	}

	private void checkDistance(Vector2 loc) {
		if (path.size > 0) {
			Vector2 target = path.first().getMiddlePoint();
			if (Vector2.dst(loc.x, loc.y, target.x, target.y) < 2.5f) {
				reachGoal(loc);
			}
		}
	}

	public Vector2 getMiddlePoint() {
		return new Vector2();
	}

	private void reachGoal(Vector2 loc) {
		Cell nextCell = path.first();

		this.previousCell = nextCell;
		path.removeFirst();

		if (path.size == 0) {
			reach();
		} else {
			setSpeedToNextCell(loc);
		}
	}

	public Vector2 getDir() {
		return dir;
	}

	private void reach() {
		path.clear();
		dir.x = 0;
		dir.y = 0;
		moving = false;
		onReach();
	}

	public void stop() {
		path.clear();
		dir.x = 0;
		dir.y = 0;
		moving = false;
	}

	public void onReach() {

	}

	public boolean setGoal(Cell goal) {
		this.goal = goal;

		previousCell = Main.inst.world.getCell(object.getX(), object.getY());
		GraphPath<Cell> graphPath = graph.findPath(previousCell, goal);
		for (int i = 1; i < graphPath.getCount(); i++) {
			
			if(object instanceof Animal) {
				if(graphPath.get(i).getObject() != null) {
					
					if(graphPath.get(i).getObject().getId().equals("aop:fence-gate") && !object.isTasked()) {
						stop();
						return false;
					}
				}
			}
			
			path.addLast(graphPath.get(i));
		}

		int s = graph.getConnections(goal).size;
		
		if (!path.isEmpty() && s > 0) {
			setSpeedToNextCell(object.getCenter());
			moving = true;
			
			return true;
		}
		
		return false;
	}

	private void setSpeedToNextCell(Vector2 loc) {
		Cell nextCell = path.first();
		float angle = MathUtils.atan2(nextCell.getMiddlePoint().y - loc.y, nextCell.getMiddlePoint().x - loc.x);
		dir.x = MathUtils.cos(angle);
		dir.y = MathUtils.sin(angle);
	}
}
