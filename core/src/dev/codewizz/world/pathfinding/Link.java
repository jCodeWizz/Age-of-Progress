package dev.codewizz.world.pathfinding;
import com.badlogic.gdx.ai.pfa.Connection;

import dev.codewizz.world.Cell;

public class Link implements Connection<Cell> {

	private float cost = 1f;
	private Cell fromCell;
	private Cell toCell;
	
	public Link(Cell fromCell, Cell toCell) {
		this.fromCell = fromCell;
		this.toCell = toCell;
	}
	
	@Override
	public float getCost() {
		return cost;
	}

	@Override
	public Cell getFromNode() {
		return fromCell;
	}

	@Override
	public Cell getToNode() {
		return toCell;
	}
	
	public void setCost(float cost) {
		this.cost = cost;
	}
}
