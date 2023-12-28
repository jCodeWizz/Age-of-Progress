package dev.codewizz.gfx.gui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import dev.codewizz.input.MouseInput;

public abstract class UILayer implements InputProcessor {

	public static int SCALE = 3, WIDTH = 1920, HEIGHT = 1080;
	public static boolean FADE = false;

	private UIElement current;
	private UIElement hovering;
	public List<UIElement> elements = new CopyOnWriteArrayList<>();
	private Texture fadeTex;
	
	private Rectangle scissors;
	
	private int mx, my;

	public UILayer() {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		setup();

		Color c = new Color();
		c.a = 0.2f;
		c.r = 0f;
		c.b = 0f;
		c.g = 0f;

		Pixmap fadeMap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		fadeMap.setColor(c);
		fadeMap.drawPixel(0, 0);
		fadeTex = new Texture(fadeMap);
		
		scissors = new Rectangle((3) * UILayer.SCALE + 4, Gdx.graphics.getHeight() - (194 * UILayer.SCALE) - 2,
				160 * UILayer.SCALE, 157 * UILayer.SCALE);
	}

	public abstract void setup();

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for (UIElement e : elements) {
			if(e instanceof UIIcon || e instanceof UIButton || e instanceof UITabButton) {
				if (e.getBounds().contains(screenX, screenY) && e.isAvailable() && e.isEnabled() && e.wantsClick) {
					current = e;
					e.click();
					MouseInput.lastClickedUIElement = e;
					return true;
				}
			}
		}

		for (UIElement e : elements) {
			
			if (e.getBounds().contains(screenX, screenY) && e.isAvailable() && e.isEnabled() && e.wantsClick) {
				current = e;
				e.click();
				
				
				MouseInput.lastClickedUIElement = e;
				
				return true;
			}
		}
		return false;
	}

	public UIElement getElement(String id) {
		for (UIElement e : elements) {
			if (e.id.equalsIgnoreCase(id)) {
				return e;
			}
		}
		return null;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		MouseInput.dragging[button] = false;
		
		for (UIElement e : elements) {
			if(e instanceof UIIcon || e instanceof UIButton || e instanceof UITabButton) {
				if (e.getBounds().contains(screenX, screenY) && e.equals(current) && e.isAvailable() && e.isEnabled()
						&& !(e instanceof UIImage)) {
					current.deClick();
					return true;
				}
				e.pressed = false;
			}
		}
		
		for (UIElement e : elements) {
			if (e.getBounds().contains(screenX, screenY) && e.equals(current) && e.isAvailable() && e.isEnabled()
					&& !(e instanceof UIImage)) {
				current.deClick();
				return true;
			}
			e.pressed = false;
		}

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mx = screenX;
		my = screenY;
		return false;
	}

	public boolean closeMenus() {
		boolean closed = false;
		for (UIElement e : elements) {
			if (e instanceof UIMenu) {
				UIMenu menu = (UIMenu) e;
				if (menu.isEnabled() && !menu.id.equalsIgnoreCase("debugMenu") && !menu.id.equalsIgnoreCase("notification-menu")) {
					menu.disable();
					closed = true;
				}
			}
		}

		return closed;
	}
	
	public boolean menusClosed() {
		for (UIElement e : elements) {
			if (e instanceof UIMenu) {
				UIMenu menu = (UIMenu) e;
				if (menu.isEnabled() && !menu.id.equalsIgnoreCase("debugMenu") && !menu.id.equalsIgnoreCase("notification-menu") && !menu.id.equalsIgnoreCase("selectMenu")) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		for (UIElement e : elements) {
			if (e instanceof UIMenu) {
				UIMenu menu = (UIMenu) e;
				if (menu.getBounds().contains(Gdx.input.getX(), Gdx.input.getY()) && menu.isAvailable()
						&& menu.isEnabled()) {
					menu.scroll(amountY);
					return true;
				}
			}
		}

		return false;
	}

	public void render(SpriteBatch b) {
		if (FADE) {
			b.draw(fadeTex, 0, 0, WIDTH, HEIGHT);
		}
		
		this.hovering = null;
		for (int i = elements.size() - 1; i >= 0; i--) {
			UIElement e = elements.get(i);
			e.hovering = false;
			if(e.isBackground() && e.isEnabled()) {
				e.render(b);
			}
			
			if(e.getBounds().contains(mx, my)) {
				e.hovering = true;
			}
		}
		
		for (int i = elements.size() - 1; i >= 0; i--) {
			UIElement e = elements.get(i);
		
			if(e instanceof UIImage && e.isEnabled() && !e.isBackground()) {
				e.render(b);
			} else {
				if(e.getBounds().contains(mx, my) && e.getID().startsWith("slot-")) {
					e.hovering = true;
					if(this.hovering == null) {
						this.hovering = e;
					}
				}
			}
		}
		
		for (int i = elements.size() - 1; i >= 0; i--) {
			UIElement e = elements.get(i);
			
			if(!(e instanceof UIImage) && !e.isBackground()) {
				if (e.isEnabled()) { // CHECK IF UI COMPONENT SHOULD BE RENDERED AT ALL
					if (e instanceof UIBuyslotTile || e instanceof UIBuyslotObject || e.getID().startsWith("slot-")) { // CHECK IF UI COMPONENT IS A MOVEABLE SLOT
						b.flush();
						if (ScissorStack.pushScissors(scissors)) {
							e.render(b);
							b.flush();
							ScissorStack.popScissors();
						}
					} else {
						e.render(b);
					}
				}
			}
		}
	}
	
	public UIElement getHovering() {
		return this.hovering;
	}

	/*
	 * 
	 * 
	 * 
	 * USELESS METHODS
	 * 
	 * 
	 * 
	 */

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	
	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}
}
