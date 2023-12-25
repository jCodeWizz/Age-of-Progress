package dev.codewizz.gfx.gui;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class UIElement {

	protected int x, y, w, h;
	protected boolean hovering = false, pressed = false, enabled = true, available = true, wantsClick = true, background = false;
	protected String id;
	
	public UIElement(String id, int x, int y, int w, int h) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	protected void onClick() {
		
	}
	
	protected void onDeClick() {
		
	}
	
	protected void onEnable() {
		
	}
	
	protected void onDisable() {
		
	}
	
	public UIElement click() {
		pressed = true;
		onClick();
		return this;
	}
	
	public UIElement deClick() {
		pressed = false;
		onDeClick();
		return this;
	}
	
	
	public abstract void render(SpriteBatch b);
	
	public Rectangle getBounds() {
		return new Rectangle(x, UILayer.HEIGHT - y - (h * UILayer.SCALE), w * UILayer.SCALE, h * UILayer.SCALE);
	}
	
	public UIElement enable() {
		this.enabled = true;
		onEnable();
		return this;
	}
	
	public UIElement disable() {
		this.enabled = false;
		onDisable();
		return this;
	}
	
	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}
	
	public void setWantsClick(boolean a) {
		this.wantsClick = a;
	}
	
	public boolean isHovering() {
		return hovering;
	}
	
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	public boolean isPressed() {
		return pressed;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public String getID() {
		return id;
	}
	
	
	public UIElement setBackground(boolean background) {
		this.background = background;
		return this;
	}
	
	public boolean isBackground() {
		return background;
	}
}
