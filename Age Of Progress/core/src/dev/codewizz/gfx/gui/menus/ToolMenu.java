package dev.codewizz.gfx.gui.menus;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.gui.UIIcon;
import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.input.AreaSelector;
import dev.codewizz.input.MouseInput;
import dev.codewizz.main.Main;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.objects.IGatherable;
import dev.codewizz.world.objects.tasks.GatherTask;

public class ToolMenu extends UIMenu {
	
	private UIIcon gatherIcon;
	private UIIcon removeIcon;
	
	private UIIcon toolIcon;
	private int currentHeight = 0;
	
	private UIImage fade;
	private UIImage fade1;
	private UIImage fade2;
	
	public ToolMenu(String id, int x, int y, int w, int h, UILayer layer) {
		super(id, x, y, w, h, layer);
		
	}
	
	@Override
	public void setup() {
		toolIcon = (UIIcon) layer.getElement("tool-icon");
		
		gatherIcon = new UIIcon("gather-icon", x + 3 * UILayer.SCALE, y + currentHeight - 27 * UILayer.SCALE, 22, 24, "tool-icon") {
			@Override
			protected void onDeClick() {
				MouseInput.area = new AreaSelector() {
					@Override
					public void handle(GameObject obj) {
						if(obj instanceof IGatherable) {
							if(((IGatherable) obj).ready()) {
								obj.setSelected(true);
								Main.inst.world.settlement.addTask(new GatherTask(obj), false);
							}
						}
					}
				};
			}
		};
		elements.add(gatherIcon);
		
		removeIcon = new UIIcon("remove-icon", x + 3 * UILayer.SCALE, y + currentHeight - 54 * UILayer.SCALE, 22, 24, "close-icon") {
			@Override
			protected void onDeClick() {
				MouseInput.area = new AreaSelector() {
					@Override
					public void handle(GameObject obj) {
						obj.destroy();
					}
				};
			}
		};
		elements.add(removeIcon);
		
		fade = new UIImage("fade-1", x, y, w, currentHeight, new Sprite(UILayer.fadeTex), 1);
		fade1 = new UIImage("fade-2", x, y, w, currentHeight, new Sprite(UILayer.fadeTex), 1);
		fade2 = new UIImage("fade-3", x, y, w, currentHeight, new Sprite(UILayer.fadeTex), 1);
		
		elements.add(fade);
		elements.add(fade1);
		elements.add(fade2);
	}
	
	@Override
	public void onOpen() {
		toolIcon.setPressed(true);
		
		currentHeight = 0;
		
		update();
	}

	@Override
	public void onClose() {
		toolIcon.setPressed(false);
		
		currentHeight = 0;
		
		update();
	}
	
	private void update() {
		fade.setH(currentHeight);
		fade1.setH(currentHeight);
		fade2.setH(currentHeight);
		
		float newY = y + currentHeight - 27 * UILayer.SCALE;
		if(newY > toolIcon.getY()) {
			gatherIcon.setY(y + currentHeight - 27 * UILayer.SCALE);
		} else {
			gatherIcon.setY(toolIcon.getY());
		}
		
		newY =  y + currentHeight - 54 * UILayer.SCALE;
		if(newY > toolIcon.getY()) {
			removeIcon.setY(y + currentHeight - 54 * UILayer.SCALE);
		} else {
			removeIcon.setY(toolIcon.getY());
		}
	}

	@Override
	public void render(SpriteBatch b) {
		
		toolIcon.setPressed(true);
		
		if(currentHeight < h) {
			currentHeight += (int)(Gdx.graphics.getDeltaTime() * 2000f);
		}
		
		if(currentHeight >= h) {
			currentHeight = h;
		}
		
		update();
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}
}
