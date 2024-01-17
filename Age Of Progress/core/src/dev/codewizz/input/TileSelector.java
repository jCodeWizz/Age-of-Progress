package dev.codewizz.input;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.main.Main;
import dev.codewizz.world.Cell;

public class TileSelector {

	public Cell start;
	public Cell end;
	public ArrayList<Cell> cells;

	public void start(Cell start) {
		this.start = start;
		cells = new ArrayList<Cell>();
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
				if(!cells.contains(cell)) {
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
		
		return;
	}

	public void handle(Cell cell) {

	}

}
