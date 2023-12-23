package dev.codewizz.utils;

public enum Direction {

	North(0, 1),
	East(1, 0),
	South(0, -1),
	West(-1, 0),
	
	NorthWest(-1, 1),
	NorthEast(1, 1),
	SouthWest(-1, -1),
	SouthEast(1, -1);
	
	
	private int dx, dy;
	
	Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public int getDX() {
		return this.dx;
	}
	
	public int getDY() {
		return this.dy;
	}
	
	/*
	 * 
	 * Returns @Dirtion from @Integer in range of [-360,360] but only on intervals of 45 degrees
	 * Old: When a non 45 degree angle is used, North will be returned.
	 * New: 
	 * 
	 */
	
	public Direction other() {
		
		switch(this) {
			case North:
				return South;
			case South:
				return North;
			case West:
				return East;
			case East:
				return West;
			case NorthWest:
				return SouthEast;
			case NorthEast:
				return SouthWest;
			case SouthEast:
				return NorthWest;
			case SouthWest:
				return NorthEast;
			default:
				return North;
		}
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static Direction getDirFromDeg(float d) {
		
		int deg = 0;
		
		float e = d / 45f;
		deg = Math.round(e) * 45;
		
		if(deg == 0 || deg == 360 || deg == -360)
			return North;
		else if(deg == 180 || deg == -180)
			return South;
		else if(deg == 90 || deg == -270)
			return East;
		else if(deg == -90 || deg == 270) 
			return West;
		else if(deg == 45 || deg == -315)
			return NorthEast;
		else if(deg == 135 || deg == -225) 
			return SouthEast;
		else if(deg == 225 || deg == -135)
			return SouthWest;
		else if(deg == -45 || deg == 315)
			return NorthWest;
		
		
		return North;
	}
}
