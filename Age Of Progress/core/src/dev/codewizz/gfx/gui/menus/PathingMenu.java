package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import dev.codewizz.gfx.gui.UIBuyslotTile;
import dev.codewizz.gfx.gui.UIIcon;
import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.gfx.gui.UIScrollList;
import dev.codewizz.gfx.gui.UITabButton;
import dev.codewizz.gfx.gui.UIText;
import dev.codewizz.input.MouseInput;
import dev.codewizz.world.tiles.BaseTile;
import dev.codewizz.world.tiles.ClayTile;
import dev.codewizz.world.tiles.DirtPathTile;
import dev.codewizz.world.tiles.DirtTile;
import dev.codewizz.world.tiles.EmptyTile;
import dev.codewizz.world.tiles.FarmTile;
import dev.codewizz.world.tiles.FlowerTile;
import dev.codewizz.world.tiles.SandTile;
import dev.codewizz.world.tiles.TiledTile;
import dev.codewizz.world.tiles.TiledTile2;
import dev.codewizz.world.tiles.TiledTile3;
import dev.codewizz.world.tiles.TiledTile4;
import dev.codewizz.world.tiles.TiledTile5;
import dev.codewizz.world.tiles.TiledTile6;
import dev.codewizz.world.tiles.TiledTile7;
import dev.codewizz.world.tiles.TiledTile8;
import dev.codewizz.world.tiles.WaterTile;

public class PathingMenu extends UIMenu {

	private UIScrollList stonesList;
	private UIScrollList terrainList;

	public PathingMenu(String id, int x, int y, int w, int h, UILayer layer) {
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
		stonesList = new UIScrollList("list-stones", x, y, w, h - 200);
		terrainList = new UIScrollList("list-terrain", x, y, w, h - 200);
		elements.add(stonesList);
		stonesList.disable();
		elements.add(terrainList);
		

		stonesList.slotPort = new Rectangle((8) * UILayer.SCALE + 4, Gdx.graphics.getHeight() - (329 * UILayer.SCALE),
				160 * UILayer.SCALE, 291 * UILayer.SCALE);

		terrainList.slotPort = new Rectangle((8) * UILayer.SCALE + 4, Gdx.graphics.getHeight() - (329 * UILayer.SCALE),
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
				Gdx.graphics.getHeight() - (34 * UILayer.SCALE), 43, 10, "Terrain") {
			@Override
			protected void onDeClick() {
				closeAllTabs();
				layer.elements.add(1, terrainList);
				layer.elements.addAll(2, terrainList.slots);
				stonesList.disable();
				terrainList.enable();

			}
		});
		elements.add(new UITabButton("tab-button-2", 60 * UILayer.SCALE,
				Gdx.graphics.getHeight() - (34 * UILayer.SCALE), 43, 10, "Stones") {
			@Override
			protected void onDeClick() {
				closeAllTabs();
				layer.elements.add(1, stonesList);
				layer.elements.addAll(2, stonesList.slots);
				stonesList.enable();
				terrainList.disable();
			}
		});

		elements.add(new UIText("text", (6 + 6) * UILayer.SCALE, Gdx.graphics.getHeight() - (6 + 5) * UILayer.SCALE + 1, "Pathing Menu", 8));

		terrainList.slots.add(new UIBuyslotTile("slot-1", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (90 * UILayer.SCALE), 160 - 8, 52, new BaseTile(null)));
		terrainList.slots.add(new UIBuyslotTile("slot-2", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (140 * UILayer.SCALE), 160 - 8, 52, new FlowerTile(null)));
		terrainList.slots.add(new UIBuyslotTile("slot-3", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (190 * UILayer.SCALE), 160 - 8, 52, new DirtTile(null)));
		terrainList.slots.add(new UIBuyslotTile("slot-4", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (240 * UILayer.SCALE), 160 - 8, 52, new SandTile(null)));
		terrainList.slots.add(new UIBuyslotTile("slot-5", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (290 * UILayer.SCALE), 160 - 8, 52, new ClayTile(null)));
		terrainList.slots.add(new UIBuyslotTile("slot-6", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (340 * UILayer.SCALE), 160 - 8, 52, new WaterTile(null)));
		terrainList.slots.add(new UIBuyslotTile("slot-7", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (390 * UILayer.SCALE), 160 - 8, 52, new EmptyTile(null)));
		terrainList.slots.add(new UIBuyslotTile("slot-8", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (440 * UILayer.SCALE), 160 - 8, 52, new FarmTile(null)));

		stonesList.slots.add(new UIBuyslotTile("slot-1", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (90 * UILayer.SCALE), 160 - 8, 52, new DirtPathTile(null)));
		stonesList.slots.add(new UIBuyslotTile("slot-2", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (140 * UILayer.SCALE), 160 - 8, 52, new TiledTile(null)));
		stonesList.slots.add(new UIBuyslotTile("slot-3", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (190 * UILayer.SCALE), 160 - 8, 52, new TiledTile2(null)));
		stonesList.slots.add(new UIBuyslotTile("slot-4", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (240 * UILayer.SCALE), 160 - 8, 52, new TiledTile3(null)));
		stonesList.slots.add(new UIBuyslotTile("slot-5", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (290 * UILayer.SCALE), 160 - 8, 52, new TiledTile4(null)));
		stonesList.slots.add(new UIBuyslotTile("slot-6", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (340 * UILayer.SCALE), 160 - 8, 52, new TiledTile5(null)));
		stonesList.slots.add(new UIBuyslotTile("slot-7", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (390 * UILayer.SCALE), 160 - 8, 52, new TiledTile6(null)));
		stonesList.slots.add(new UIBuyslotTile("slot-8", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (440 * UILayer.SCALE), 160 - 8, 52, new TiledTile7(null)));
		stonesList.slots.add(new UIBuyslotTile("slot-9", (8) * UILayer.SCALE + 4,
				Gdx.graphics.getHeight() - (490 * UILayer.SCALE), 160 - 8, 52, new TiledTile8(null)));

		terrainList.maxScroll = (terrainList.slots.size()) * 52;

		stonesList.maxScroll = (stonesList.slots.size()) * 52;

		elements.addAll(terrainList.slots);

		elements.add(new UIImage("background", 6 * UILayer.SCALE, Gdx.graphics.getHeight() - (331 * UILayer.SCALE), 160,
				325, "path-menu"));
	}

	private void closeAllTabs() {
		layer.elements.removeAll(terrainList.slots);
		layer.elements.removeAll(stonesList.slots);
		layer.elements.remove(terrainList);
		layer.elements.remove(stonesList);
		
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
