package dev.codewizz.input;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Chunk;
import dev.codewizz.world.tiles.FarmTile;

public class TileSelector {
	
	public Cell start;
	
	public void start(Cell start) {
		this.start = start;
	}
	
	public void end(Cell end) {
		Vector2 startIndex = new Vector2(start.getWorldIndexX(), start.getWorldIndexY());
		Vector2 endIndex = new Vector2(end.getWorldIndexX(), end.getWorldIndexY());
		
		Logger.log("Start: {" + start.indexX + " ; " + start.indexY);
		Logger.log("End: {" + end.indexX + " ; " + end.indexY);

		
		for(int i = (int)startIndex.x; i < (int)endIndex.x; i++) {
			for(int j = (int)startIndex.y; j < (int)endIndex.y; j++) {
				
				
				int chunkX = i / 8;
				int chunkY = j / 8;
				
				Chunk chunk = Main.inst.world.chunkTree.get(new Vector2(chunkX, chunkY).toString());
				
				if(chunk != null) {
					Cell cell = chunk.getGrid()[Math.abs(i % 8)][Math.abs(j % 8)];
					
					Logger.log("Handeling: {" + cell.indexX + " ; " + cell.indexY);
					
					handle(cell);
				}
			}
		}
		
		Cell startCell = Main.inst.world.getCellWorldIndex(startIndex);
		Cell endCell = Main.inst.world.chunkTree.get(new Vector2((int)endIndex.x / 8, (int)endIndex.y / 8).toString()).getGrid()[(int) Math.abs(endIndex.x % 8)][(int) Math.abs(endIndex.y % 8)];
	
		startCell.setTile(new FarmTile());
		endCell.setTile(new FarmTile());
	}
	
	public void handle(Cell cell) {
		
	}

}
