package dev.codewizz.input;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.main.Main;
import dev.codewizz.world.Cell;

public class TileSelector {

	public Cell start;

	public void start(Cell start) {
		this.start = start;
	}

	public ArrayList<Cell> end(Cell end) {

		ArrayList<Cell> cells = new ArrayList<>();

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
					handle(cell);
				}
			}
		}

		return cells;
	}

	public void handle(Cell cell) {

	}

}
