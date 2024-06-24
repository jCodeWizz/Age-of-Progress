package dev.codewizz.world.building;

import java.util.ArrayList;
import java.util.List;

import dev.codewizz.world.Cell;

public class Room {

	private Cell[] corners = new Cell[4];
	private List<Cell> area = new ArrayList<>();
	
	public Room(List<Cell> area, Cell c1, Cell c2, Cell c3, Cell c4) {
		this.area = area;
	
		corners[0] = c1;
		corners[1] = c2;
		corners[2] = c3;
		corners[3] = c4;
	}
	
	public Cell[] getCorners() {
		return corners;
	}
	
	public List<Cell> getArea() {
		return area;
	}

	public void removeCell(Cell cell) {
		area.remove(cell);
	}
}
