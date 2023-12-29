package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import dev.codewizz.gfx.gui.UIElement;
import dev.codewizz.gfx.gui.UIIcon;
import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.gfx.gui.UIScrollList;
import dev.codewizz.gfx.gui.UITabButton;
import dev.codewizz.gfx.gui.UIText;
import dev.codewizz.input.MouseInput;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.Fence;
import dev.codewizz.world.objects.FenceGate;
import dev.codewizz.world.objects.FencePost;
import dev.codewizz.world.objects.IBuy;
import dev.codewizz.world.objects.Stump;
import dev.codewizz.world.objects.buildings.Building;

public class BuildingMenu extends UIMenu {

	private UIScrollList housingList;
	private UIScrollList settlementList;

	private UIText name;
	private UIText description;
	private UIText cost;
	
	private int rowSize = 5;
	private IBuy toRender;
	
	public BuildingMenu(String id, int x, int y, int w, int h, UILayer layer) {
		super(id, x, y, w, h, layer);
	}

	@Override
	public void setup() {
		/*
		 * 
		 * this was the problem (h-200) might fuck up later when more buyslots are
		 * added.
		 * 
		 */
		housingList = new UIScrollList("list-housing", x, y, w, h - 200);
		settlementList = new UIScrollList("list-settlement", x, y, w, h - 200);
		elements.add(housingList);
		housingList.disable();
		elements.add(settlementList);
		

		housingList.slotPort = new Rectangle((0) * UILayer.SCALE + 0, Gdx.graphics.getHeight() - (329 * UILayer.SCALE),
				160 * UILayer.SCALE, 291 * UILayer.SCALE);

		settlementList.slotPort = new Rectangle((0) * UILayer.SCALE + 0, Gdx.graphics.getHeight() - (329 * UILayer.SCALE),
				160 * UILayer.SCALE, 291 * UILayer.SCALE);

		elements.add(0, new UIIcon("close-button", (6 + 160 - 13 - 1) * UILayer.SCALE - 1,
				Gdx.graphics.getHeight() - (7 + 15) * UILayer.SCALE + 1, 14, 15, "close-icon") {
			@Override
			protected void onDeClick() {
				closeAllTabs();
				close();
			}
		});

		elements.add(new UITabButton("tab-button-1", 12 * UILayer.SCALE,
				Gdx.graphics.getHeight() - (34 * UILayer.SCALE), 43, 10, "Settlement") {
			@Override
			protected void onDeClick() {
				closeAllTabs();
				layer.elements.add(1, settlementList);
				layer.elements.addAll(2, settlementList.slots);
				housingList.disable();
				settlementList.enable();

			}
		});
		elements.add(new UITabButton("tab-button-2", 60 * UILayer.SCALE,
				Gdx.graphics.getHeight() - (34 * UILayer.SCALE), 43, 10, "Housing") {
			@Override
			protected void onDeClick() {
				closeAllTabs();
				layer.elements.add(1, housingList);
				layer.elements.addAll(2, housingList.slots);
				housingList.enable();
				settlementList.disable();
			}
		});

		elements.add(new UIText("text", (6 + 6) * UILayer.SCALE, Gdx.graphics.getHeight() - (6 + 5) * UILayer.SCALE + 1, "Building Menu", 8));
		name = new UIText("object-name-text", (6 + 6) * UILayer.SCALE, Gdx.graphics.getHeight() - 200 * UILayer.SCALE, "", 8);
		description = new UIText("object-description-text", (6 + 6) * UILayer.SCALE, Gdx.graphics.getHeight() - 210 * UILayer.SCALE, "", 6);
		cost = new UIText("object-cost-text", 0, 0, "x1", 6);
		
		elements.add(name);
		elements.add(description);

		settlementList.slots.add(new ObjectSlot(new Building(0, 0)));
		settlementList.slots.add(new ObjectSlot(new FencePost(0, 0)));
		settlementList.slots.add(new ObjectSlot(new Fence(0, 0)));
		settlementList.slots.add(new ObjectSlot(new FenceGate(0, 0)));
		settlementList.slots.add(new ObjectSlot(new Stump(0, 0)));

		for(int i = settlementList.slots.size(); i < 30; i++) {
			settlementList.slots.add(new ObjectSlot());
		}
		
		settlementList.maxScroll = (settlementList.slots.size()) * 52;

		housingList.maxScroll = (housingList.slots.size()) * 52;

		elements.addAll(settlementList.slots);
		
		elements.add(new UIImage("background", 6 * UILayer.SCALE, Gdx.graphics.getHeight() - (331 * UILayer.SCALE), 160,
				325, "path-menu"));
	}

	private void closeAllTabs() {
		layer.elements.removeAll(settlementList.slots);
		layer.elements.removeAll(housingList.slots);
		layer.elements.remove(settlementList);
		layer.elements.remove(housingList);
	}
	
	@Override
	public void render(SpriteBatch b) {
		
		if(Main.inst.renderer.ui.getHovering() != null) {

			if(Main.inst.renderer.ui.getHovering() instanceof ObjectSlot) {
				ObjectSlot s = (ObjectSlot) Main.inst.renderer.ui.getHovering();
				if(s.iBuy != null) {
					toRender = s.iBuy;
				}
			}
		}
		
		if(toRender != null) {
			name.setText(toRender.getMenuName());
			description.setText(toRender.getMenuDescription());

			
			
			float maxWidth = 154 * UILayer.SCALE;
			
			float width = 154 * UILayer.SCALE;
			float height = 90 * UILayer.SCALE;
			
			width = (int) (((float) (height) / toRender.getMenuSprite().getHeight()) * toRender.getMenuSprite().getWidth());
			
			if(width > maxWidth) {
				width = maxWidth;
				height = (int) (((float) (width) / toRender.getMenuSprite().getWidth()) * toRender.getMenuSprite().getHeight());
			}
			
			b.draw(toRender.getMenuSprite(), 6 * UILayer.SCALE + maxWidth / 2 - width / 2, Gdx.graphics.getHeight() - (328 * UILayer.SCALE), width, height);
		
			for(int i = 0; i < toRender.costs().size(); i++) {
				Item item = toRender.costs().get(i);

				if(Main.inst.world.settlement.inventory.containsItem(item, item.getSize())) {
					cost.setColor(Color.GREEN);
				} else {
					cost.setColor(Color.RED);
				}
				
				
				
				b.draw(item.getType().getSprite(), x + (152) * UILayer.SCALE - 48, Gdx.graphics.getHeight() - (215 + i * 2) * UILayer.SCALE - 48 * i, 48, 48);
				cost.setText("x" + item.getSize());
				cost.setX(x + (154) * UILayer.SCALE);
				cost.setY(Gdx.graphics.getHeight() - (203 + i * 2) * UILayer.SCALE - 48 * i);
				cost.render(b);
			}
		}
	}

	@Override
	public java.awt.Rectangle getBounds() {
		return new java.awt.Rectangle(6 * UILayer.SCALE, Gdx.graphics.getHeight() - (331 * UILayer.SCALE),
				160 * UILayer.SCALE, 325 * UILayer.SCALE);
	}

	@Override
	public void onOpen() {
		for(int i = 0; i < settlementList.slots.size(); i++) {
			UIElement e = settlementList.slots.get(i);

			int indexX = i % rowSize;
			int indexY = (int) (i / rowSize);
			
			e.setX(indexX * 94 + x + 8 * UILayer.SCALE);
			e.setY(indexY * - 94 + y + 291 * UILayer.SCALE);
		}
	}

	@Override
	public void onClose() {
		MouseInput.object = true;
		MouseInput.currentlyDrawingObject = null;
		closeAllTabs();
	}
}
class ObjectSlot extends UIElement {
	
	private static Sprite background = Assets.getSprite("slot-background");
	private static Sprite backgroundPressed = Assets.getSprite("slot-background-pressed");
	private static Sprite backgroundHovering = Assets.getSprite("slot-background-hovering");
	
	public GameObject object;
	public IBuy iBuy;
	
	private int width, height;
	
	public ObjectSlot() {
		super("slot-empty", 0, 0, 94, 94);
		
		this.object = null;
	}
	
	public ObjectSlot(GameObject object) {
		super("slot-" + object.getId(), 0, 0, 94, 94);
		
		this.object = object;
		this.iBuy = (IBuy) object;

		if (iBuy.getMenuSprite().getWidth() > iBuy.getMenuSprite().getHeight()) {

			width = w - 6;
			height = (int) (((float) (width) / iBuy.getMenuSprite().getWidth()) * iBuy.getMenuSprite().getHeight());

		} else {
			height = h - 6;
			width = (int) (((float) (height) / iBuy.getMenuSprite().getHeight()) * iBuy.getMenuSprite().getWidth());
		}

		width = w - 6;
		height = (int)(((float)(width) / 64f) * 48f);
	}
	
	@Override
	protected void onDeClick() {
		if(object != null) {
			MouseInput.object = true;
			MouseInput.currentlyDrawingObject = object;
		}
	}

	@Override
	public void render(SpriteBatch b) {
		if(object != null) {
			if(pressed) {	
				b.draw(backgroundPressed, x, y, w, h);	
			} else if(hovering){
				b.draw(backgroundHovering, x, y, w, h);	
			} else {
				b.draw(background, x, y, w, h);	
			}
		
			b.draw(iBuy.getMenuSprite(), x, y + 6, width, height);
		} else {
			if(hovering){
				b.draw(backgroundHovering, x, y, w, h);	
			} else {
				b.draw(background, x, y, w, h);	
			}	
		}
	}
	
	@Override
	public java.awt.Rectangle getBounds() {
		return new java.awt.Rectangle(x, UILayer.HEIGHT - y - h, w, h);
	}
}

