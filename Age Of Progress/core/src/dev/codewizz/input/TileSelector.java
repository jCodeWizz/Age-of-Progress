package dev.codewizz.input;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.Cell;
import dev.codewizz.world.building.Building;
import dev.codewizz.world.building.BuildingObject;
import dev.codewizz.world.building.Room;
import dev.codewizz.world.objects.tasks.Task;

public class TileSelector {

	public Cell start;
	public Cell end;
	public ArrayList<Cell> cells;

	public void start(Cell start) {
		this.start = start;
		cells = new ArrayList<Cell>();
	}
	
	public boolean isClear() {
		if(start == null || end == null || cells == null) return false;
		
		return checkClear();
	}
	
	public boolean checkClear() {
		return true;
	}
	
	public boolean checkCellClear(Cell cell) {
		return true;
	}
	
	public void step(Cell end) {
		cells.clear();
		this.end = end;
		Vector2 startIndex = new Vector2(start.getWorldIndexX(), start.getWorldIndexY());
		Vector2 endIndex = new Vector2(end.getWorldIndexX(), end.getWorldIndexY());

		if (endIndex.x < startIndex.x && endIndex.y < startIndex.y) {
			Vector2 a = new Vector2(startIndex);
			startIndex = new Vector2(endIndex);
			endIndex = new Vector2(a);
		} else if (endIndex.y < startIndex.y && endIndex.x > startIndex.x) {
			Vector2 a = new Vector2(startIndex);
			Vector2 b = new Vector2(endIndex);

			startIndex.y = b.y;
			endIndex.y = a.y;
		} else if (endIndex.x < startIndex.x) {
			Vector2 a = new Vector2(startIndex);
			Vector2 b = new Vector2(endIndex);

			startIndex.x = b.x;
			endIndex.x = a.x;
		}

		for (int i = (int) startIndex.x; i <= (int) endIndex.x; i++) {
			for (int j = (int) startIndex.y; j <= (int) endIndex.y; j++) {
				Cell cell = Main.inst.world.getCellWorldIndex(i, j);
				if(!cells.contains(cell) && cell != null) {
					cells.add(cell);
				}
			}
		}
	}

	public void end(Cell end) {
		if(start == null) return;
		
		this.end = end;
		
		step(end);

		for(Cell cell : cells) {
			handle(cell);
		}
		
		onEnd();
	}

	public void handle(Cell cell) {
 
	}
	
	public void onEnd() {
		
	}
	
	public static TileSelector room(final Building building) {
		return new TileSelector() {
			
			@Override
			public void onEnd() {
				if(!checkClear()) {
					return;
				}
				
				Cell c1 = this.end;
				Cell c2 = Main.inst.world.getCellWorldIndex(start.getWorldIndexX(), end.getWorldIndexY());
				Cell c3 = this.start;
				Cell c4 = Main.inst.world.getCellWorldIndex(end.getWorldIndexX(), start.getWorldIndexY());

				if(c3.y < c1.y && c2.x > c4.x) {
					Cell t = c1;
					c1 = c3;
					c3 = t;
					
					t = c2;
					c2 = c4;
					c4 = t;
				}
				
				if(c2.y > c3.y && c1.y > c4.y) {
					Cell t = c2;
					c2 = c3;
					c3 = t;
					
					t = c1;
					c1 = c4;
					c4 = t;
				}

				if(c3.x > c4.x && c2.x > c1.x) {
					Cell t = c4;
					c4 = c3;
					c3 = t;
					
					t = c2;
					c2 = c1;
					c1 = t;
				}

				Room room = new Room(this.cells, c1, c2, c3, c4);
				building.addRoom(room);
			}
			
			@Override
			public boolean checkCellClear(Cell cell) {
				return cell.getObject() == null || (cell.object.getId().equals("aop:buildingobject") && ((BuildingObject)cell.object).isEdge());
			}
			
			
			@Override
			public boolean checkClear() {
				for(Cell cell : this.cells) {
					if(cell.getObject() != null && cell.object.getId().equals("aop:buildingobject") && !((BuildingObject)cell.object).isEdge()) {
						return false;
					}
				}
				
				return true;
			}
		};
	}
	
	public static TileSelector task(final Class<? extends Task> task) {
		return new TileSelector() {
			@Override
			public void handle(Cell cell) {
				try {
					Main.inst.world.settlement.addTask(task.getConstructor(Cell.class).newInstance(cell), true);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					Logger.error("Couldn't create task: " + task.getName());
				}
			}
		};
	}

}
