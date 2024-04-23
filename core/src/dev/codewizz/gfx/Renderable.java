package dev.codewizz.gfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Renderable implements Comparable<Renderable> {
	
	protected boolean tasked = false;
	
	public abstract void render(SpriteBatch b);
	public abstract float getY();
	public abstract float getX();
	public abstract float getSorthingHeight();
	public abstract void update(float dt);
	
	@Override
	public int compareTo(Renderable other) {
		if(other.getY() + other.getSorthingHeight() < this.getY() + this.getSorthingHeight()) {
			return -1;
		} else if(other.getY() + other.getSorthingHeight() > this.getY() + this.getSorthingHeight()){
			return 1;
		} else {
			if(other.getX() < this.getX()) {
				return 1;
			} else if(other.getX() > this.getX()) {
				return -1;
			} else {
				return 0;
			}
		}
	}
	public boolean isTasked() {
		return tasked;
	}
	public void setTasked(boolean tasked) {
		this.tasked = tasked;
	}
}
