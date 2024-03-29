package dev.codewizz.utils;

public enum Direction {

	North(0, 1, 1), East(1, 0, 3), South(0, -1, 5), West(-1, 0, 7),

	NorthWest(-1, 1, 0), NorthEast(1, 1, 2), SouthWest(-1, -1, 6), SouthEast(1, -1, 4);

	private int dx, dy;
	private int index;

	Direction(int dx, int dy, int index) {
		this.dx = dx;
		this.dy = dy;
		this.index = index;
	}

	public int getDX() {
		return this.dx;
	}

	public int getDY() {
		return this.dy;
	}

	public int getIndex() {
		return index;
	}

	/*
	 * 
	 * Returns @Dirtion from @Integer in range of [-360,360] but only on intervals
	 * of 45 degrees Old: When a non 45 degree angle is used, North will be
	 * returned. New:
	 * 
	 */

	public Direction other() {

		switch (this) {
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
	 * Utility method to query a Direction from corresponding index.
	 * @param index of Direction
	 * @return Direction
	 */
	public static Direction getFromIndex(int index) {
		
		
		int i = index;
		if(i < 0) i = 7 + index + 1;
		if(i > 7) i = index - 8;
		
		switch (i) {
		case (0):
			return NorthWest;
		case (1):
			return North;
		case (2):
			return NorthEast;
		case (3):
			return East;
		case (4):
			return SouthEast;
		case (5):
			return South;
		case (6):
			return SouthWest;
		case (7):
			return West;
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

		if (deg == 0 || deg == 360 || deg == -360)
			return North;
		else if (deg == 180 || deg == -180)
			return South;
		else if (deg == 90 || deg == -270)
			return East;
		else if (deg == -90 || deg == 270)
			return West;
		else if (deg == 45 || deg == -315)
			return NorthEast;
		else if (deg == 135 || deg == -225)
			return SouthEast;
		else if (deg == 225 || deg == -135)
			return SouthWest;
		else if (deg == -45 || deg == 315)
			return NorthWest;

		return North;
	}
}
