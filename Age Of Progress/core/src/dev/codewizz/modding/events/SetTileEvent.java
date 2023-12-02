package dev.codewizz.modding.events;

import dev.codewizz.world.Cell;
import dev.codewizz.world.Tile;

public class SetTileEvent extends Event {

	private Cell cell;
	private Tile newTile;
	
	public Cell getCell() {
		return cell;
	}

	public Tile getNewTile() {
		return newTile;
	}

	public SetTileEvent(Cell cell, Tile newTile) {
		this.cell = cell;
		this.newTile = newTile;
	}
	
}
