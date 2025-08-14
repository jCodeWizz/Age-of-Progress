package dev.codewizz.world.objects.behaviour.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.world.Cell;

public class CellHeuristic implements Heuristic<Cell> {

	@Override
	public float estimate(Cell node, Cell endNode) {
		return Vector2.dst(node.getMiddlePoint().x, node.getMiddlePoint().y, endNode.getMiddlePoint().x, endNode.getMiddlePoint().y);
	}
}
