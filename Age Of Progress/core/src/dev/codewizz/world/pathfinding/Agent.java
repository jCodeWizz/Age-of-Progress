package dev.codewizz.world.pathfinding;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import dev.codewizz.main.Main;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.objects.Animal;

public class Agent {

	private Cell previousCell;
	public Queue<Cell> path = new Queue<Cell>();
	public boolean moving = false;
	private CellGraph graph;
	private Vector2 dir;

	public Cell goal;

	private GameObject object;

	public Agent(GameObject object) {
		dir = new Vector2();
		this.object = object;

		graph = Main.inst.world.cellGraph;
	}

	public void update(float d, float x, float y) {

		if (graph == null)
			graph = Main.inst.world.cellGraph;

		checkDistance(x, y);
	}

	private void checkDistance(float x, float y) {
		if (path.size > 0) {
			Vector2 target = path.first().getMiddlePoint();
			if (Vector2.dst(x, y, target.x, target.y) < 5) {
				reachGoal(x, y);
			}
		}
	}

	public Vector2 getMiddlePoint() {
		return new Vector2();
	}

	private void reachGoal(float x, float y) {
		Cell nextCell = path.first();

		this.previousCell = nextCell;
		path.removeFirst();

		if (path.size == 0) {
			reach();
		} else {
			setSpeedToNextCell(x, y);
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

	public boolean setGoal(Cell goal, float x, float y) {
		this.goal = goal;

		previousCell = Main.inst.world.getCell(x, y);
		GraphPath<Cell> graphPath = graph.findPath(previousCell, goal);
		for (int i = 1; i < graphPath.getCount(); i++) {
			
			
			if(graphPath.get(i).tile.getId().equals("aop:water-tile") || graphPath.get(i).tile.getId().equals("aop:deep-water-tile")) {
				stop();
				return false;
			}
			
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
			setSpeedToNextCell(x, y);
			moving = true;
			
			return true;
		}
		
		return false;
	}

	private void setSpeedToNextCell(float x, float y) {
		Cell nextCell = path.first();
		float angle = MathUtils.atan2(nextCell.getMiddlePoint().y - y, nextCell.getMiddlePoint().x - x);
		dir.x = MathUtils.cos(angle);
		dir.y = MathUtils.sin(angle);
	}
}
