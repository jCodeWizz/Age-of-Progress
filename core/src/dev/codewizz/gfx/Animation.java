package dev.codewizz.gfx;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Animation {

	private float counter, speed;
	private int index;
	private Sprite[] frames;
	private boolean justRestarted;
	
	private float x, y;

	public Animation(float speed, Sprite... frames) {
		this.speed = speed;
		this.frames = frames;

		index = 0;
	}
	
	public Animation(float x, float y, float speed, Sprite... frames) {
		this.x = x;
		this.y = y;
		
		this.speed = speed;
		this.frames = frames;

		index = 0;
	}
	
	
	/*
	 * 
	 * Tick animation and check if it needs to move to the next frame.
	 * 
	 */

	public void tick(float d) {

		if (counter < speed)
			counter += 1f*d;
		else {
			if (index == frames.length - 1) {
				index = 0;
				justRestarted = true;
			} else
				index++;
			counter = 0;
		}

	}

	public int getIndex() {
		return index;
	}

	public int getLength() {
		return frames.length;
	}

	public boolean hasCycled() {
		return justRestarted;
	}

	public void reset() {
		index = 0;
		justRestarted = false;
		counter = 0;
	}

	public Sprite getFrame() {
		return frames[index];
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
