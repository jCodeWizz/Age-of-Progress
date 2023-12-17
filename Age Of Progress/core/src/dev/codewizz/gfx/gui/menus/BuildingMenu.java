package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import dev.codewizz.gfx.gui.UIBuyslotObject;
import dev.codewizz.gfx.gui.UIBuyslotTile;
import dev.codewizz.gfx.gui.UIIcon;
import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.gfx.gui.UIScrollList;
import dev.codewizz.gfx.gui.UITabButton;
import dev.codewizz.gfx.gui.UIText;
import dev.codewizz.input.MouseInput;
import dev.codewizz.world.objects.Fence;
import dev.codewizz.world.objects.FenceGate;
import dev.codewizz.world.objects.FencePost;
import dev.codewizz.world.objects.Flag;
import dev.codewizz.world.objects.Stump;
import dev.codewizz.world.objects.buildings.Building;
import dev.codewizz.world.tiles.DirtPathTile;
import dev.codewizz.world.tiles.TiledTile;

public class BuildingMenu extends UIMenu {

	private UIScrollList housingList;
	private UIScrollList settlementList;

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
		

		housingList.slotPort = new Rectangle((8) * UILayer.SCALE + 4, Gdx.graphics.getHeight() - (329 * UILayer.SCALE),
				160 * UILayer.SCALE, 291 * UILayer.SCALE);

		settlementList.slotPort = new Rectangle((8) * UILayer.SCALE + 4, Gdx.graphics.getHeight() - (329 * UILayer.SCALE),
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

		settlementList.slots.add(new UIBuyslotObject("slot-1", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (90 * UILayer.SCALE), 160 - 8, 52, new Flag(0, 0)));
		settlementList.slots.add(new UIBuyslotObject("slot-2", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (140 * UILayer.SCALE), 160 - 8, 52, new Building(0, 0)));
		settlementList.slots.add(new UIBuyslotObject("slot-3", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (190 * UILayer.SCALE), 160 - 8, 52, new Stump(0, 0)));
		settlementList.slots.add(new UIBuyslotObject("slot-3", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (240 * UILayer.SCALE), 160 - 8, 52, new FencePost(0, 0)));
		settlementList.slots.add(new UIBuyslotObject("slot-3", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (290 * UILayer.SCALE), 160 - 8, 52, new Fence(0, 0)));
		settlementList.slots.add(new UIBuyslotObject("slot-3", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (340 * UILayer.SCALE), 160 - 8, 52, new FenceGate(0, 0)));
		
		
		housingList.slots.add(new UIBuyslotTile("slot-1", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (90 * UILayer.SCALE), 160 - 8, 52, new DirtPathTile()));
		housingList.slots.add(new UIBuyslotTile("slot-2", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (140 * UILayer.SCALE), 160 - 8, 52, new TiledTile()));

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
	public java.awt.Rectangle getBounds() {
		return new java.awt.Rectangle(6 * UILayer.SCALE, Gdx.graphics.getHeight() - (331 * UILayer.SCALE),
				160 * UILayer.SCALE, 325 * UILayer.SCALE);
	}

	@Override
	public void onOpen() {
	}

	@Override
	public void onClose() {
		MouseInput.object = true;
		MouseInput.currentlyDrawingObject = null;
		closeAllTabs();
	}
}
