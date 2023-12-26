package dev.codewizz.gfx.gui.menus;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;

import dev.codewizz.gfx.gui.UIElement;
import dev.codewizz.gfx.gui.UIIcon;
import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.gfx.gui.UIText;
import dev.codewizz.main.Main;
import dev.codewizz.world.items.Item;

public class SettlementMenu extends UIMenu {

	private final static int x_padding = 6 * UILayer.SCALE;
	private final static int y_padding = 6 * UILayer.SCALE;
	private final static int height = 16 * UILayer.SCALE;
	private final static int width = 90 * UILayer.SCALE;
	
	private UIImage image;
	
	public SettlementMenu(String id, int x, int y, int w, int h, UILayer layer) {
		super(id, x, y, w, h, layer);

	}
	
	@Override
	public void setup() {
		
		elements.add(0, new UIIcon("close-button", x + w*UILayer.SCALE - 2 - 14*UILayer.SCALE,
				y + h*UILayer.SCALE - 15*UILayer.SCALE - 2, 14, 15, "close-icon") {
			@Override
			protected void onDeClick() {
				close();
			}
		});
		
		elements.add(new UIText("settlementMenu-title", x + 4 * UILayer.SCALE, y + h*UILayer.SCALE - 4 * UILayer.SCALE, "Settlement Menu", 10));
		image = new UIImage("settlement-menu", x, y, w, h, "settlement-menu");
	}
	
	private void addItemCard(Item item, int x, int y) {
		elements.add(new UIImage("settlementMenu-item", x, y, 16, 16, item.getType().getSprite()));
		elements.add(new UIText("settlementMenu-item", x + 6 + (int)item.getType().getSprite().getWidth() * UILayer.SCALE, y + 15, "" + item.getSize(), 8));
		elements.add(new UIText("settlementMenu-item", x + 6 + (int)item.getType().getSprite().getWidth() * UILayer.SCALE, y + 45, "" + item.getType().toString(), 10));
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(6 * UILayer.SCALE, Gdx.graphics.getHeight() - (331 * UILayer.SCALE),
				160 * UILayer.SCALE, 325 * UILayer.SCALE);
	}

	@Override
	public void onOpen() {
		for(int i = 0; i < Main.inst.world.settlement.getInventory().getItems().size(); i++) {
			addItemCard(Main.inst.world.settlement.getInventory().getItems().get(i), x + x_padding + ((i % 4) * (width + x_padding)), y + h*UILayer.SCALE - 150 - (((int)(i / 4)) * (height + y_padding)));
		}
		
		elements.add(image);
	}

	@Override
	public void onClose() {
		for(UIElement e : elements) {
			if(e.getID().equalsIgnoreCase("settlementMenu-item")) {
				Main.inst.renderer.ui.elements.remove(e);
				elements.remove(e);
			}
		}
		
		Main.inst.renderer.ui.elements.remove(image);
		elements.remove(image);
	}
}