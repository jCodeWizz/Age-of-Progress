package dev.codewizz.world.settlement;

import dev.codewizz.world.Cell;

public class Crop {

	public Cell cell;
	public CropType type;
	public float counter = 0f;
	public boolean tasked = false;
	
	public Crop(Cell cell, CropType type) {
		this.cell = cell;
		this.type = type;
		
	}
	
	public boolean isReady() {
		return this.counter > type.getTime();
	}
}
