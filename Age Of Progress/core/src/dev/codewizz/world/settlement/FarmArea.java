package dev.codewizz.world.settlement;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.codewizz.world.Cell;
import dev.codewizz.world.tiles.EmptyTile;

public class FarmArea {

	public static final int MAX_SIZE = 60;
	
	private List<Cell> area;
	private List<Cell> fencing;
	private List<Cell> entrances;
	
	private boolean valid = false;

	public FarmArea() {
		area = new CopyOnWriteArrayList<>();
		fencing = new CopyOnWriteArrayList<>();
		entrances = new CopyOnWriteArrayList<>();
	}
	
	
	public boolean checkArea(Cell cell) {
		Cell[] cells = cell.getNeighbours();
		for(int i = 0; i < cells.length; i++) {
			if(cells[i].getObject() == null || (cells[i].getObject().getId().equals("aop:fence") && cells[i].getObject().getId().equals("aop:fence-post"))) {
				checkCell(cells[i]);
				
				if(checkValid()) {
					
					for(Cell c : area) {
						c.setTile(new EmptyTile(c));
					}
					
					return true;
				}
				
				area.clear();
				fencing.clear();
				entrances.clear();
			}
		}
		
		return false;
	}
	
	public boolean checkValid() {
		if(area.size() > MAX_SIZE || area.size() == 0) return false;
		if(entrances.isEmpty()) return false;
		
		for(Cell cell : area) {
			
			Cell[] cells = cell.getCrossedNeighbours();
			
			for(int i = 0; i < cells.length; i++) {
				if(cells[i] != null) {
					if(!contains(cells[i])) {
						this.valid = false;
						return false;
					}
				} else {
					this.valid = false;
					return false;
				}
			}
		}
		this.valid = true;
		return true;
	}
	
	private void checkCell(Cell cell) {
		if(contains(cell) || area.size() > MAX_SIZE) {
			return;
		}
		
		boolean check = this.addCell(cell);
		
		if(check) {
			Cell[] cells = cell.getCrossedNeighbours();
			
			for(int i = 0; i < cells.length; i++) {
				if(cells[i] != null) {
					checkCell(cells[i]);
				}
			}
		}
	}
	
	public boolean contains(Cell cell) {
		return area.contains(cell) || entrances.contains(cell) || fencing.contains(cell);
	}
	
	/**
	 * This will both add and check a cell to add to the correct list of components of a farm area.
	 * @param cell the cell to be added to the correct list of components.
	 * @return a boolean: true if the search should continue from this cell, false if it is an edge.
	 */
	private boolean addCell(Cell cell) {
		if(cell.getObject() == null) {
			area.add(cell);
			return true;
		} else {
			if(cell.getObject().getId().equals("aop:fence-gate")) {
				entrances.add(cell);
				return false;
			} else if(cell.getObject().getId().equals("aop:fence") || cell.getObject().getId().equals("aop:fence-post")) {
				fencing.add(cell);
				return false;
			} else {
				area.add(cell);
				return true;
			}
		}
	}

	public List<Cell> getArea() {
		return area;
	}


	public List<Cell> getFencing() {
		return fencing;
	}


	public List<Cell> getEntrances() {
		return entrances;
	}
	
	public boolean isValid() {
		return valid;
	}
}
