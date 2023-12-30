package dev.codewizz.input;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.Cell;

public class TileSelector {
	
	public Cell start;
	
	public void start(Cell start) {
		this.start = start;
	}
	
	public void end(Cell end) {
		Vector2 startIndex = new Vector2(start.getWorldIndexX(), start.getWorldIndexY());
		Vector2 endIndex = new Vector2(end.getWorldIndexX(), end.getWorldIndexY());
		
		if(startIndex.x > endIndex.x) {
			
			
			Logger.log("A");
			
			float a = startIndex.x;
			startIndex.x = endIndex.x;
			endIndex.x = a;
		}
		
		if(startIndex.y > endIndex.y) {
			
			Logger.log("B");
			
			float a = startIndex.y;
			startIndex.y = endIndex.y;
			endIndex.y = a;
		}
		
		for(int i = (int)startIndex.x; i < (int)endIndex.x; i++) {
			for(int j = (int)startIndex.y; j < (int)endIndex.y; j++) {
				Vector2 coords = Cell.getCoordsFromIndex(i, j);
				Cell c = Main.inst.world.getCell(coords.x, coords.y);
				handle(c);
			}
		}
	}
	
	public void handle(Cell cell) {
		
	}

}
