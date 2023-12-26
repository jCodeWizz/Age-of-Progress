package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Gdx;
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
import dev.codewizz.utils.Assets;
import dev.codewizz.world.Tile;
import dev.codewizz.world.tiles.ClayTile;
import dev.codewizz.world.tiles.DeepWaterTile;
import dev.codewizz.world.tiles.DirtPathTile;
import dev.codewizz.world.tiles.DirtTile;
import dev.codewizz.world.tiles.EmptyTile;
import dev.codewizz.world.tiles.FarmTile;
import dev.codewizz.world.tiles.FlowerTile;
import dev.codewizz.world.tiles.GrassTile;
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
	
	private int rowSize = 5;

	public PathingMenu(String id, int x, int y, int w, int h, UILayer layer) {
		super(id, x, y, w, h, layer);
	}

	@Override
	public void setup() {
		elements.add(0, new UIIcon("close-button", (6 + 160 - 13 - 1) * UILayer.SCALE - 1,
				Gdx.graphics.getHeight() - (7 + 15) * UILayer.SCALE + 1, 14, 15, "close-icon") {
			@Override
			protected void onDeClick() {
				closeAllTabs();
				close();
			}
		});
		
		
		
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

		terrainList.slots.add(new Slot(new GrassTile()));
		terrainList.slots.add(new Slot(new FlowerTile()));
		terrainList.slots.add(new Slot(new DirtTile()));
		terrainList.slots.add(new Slot(new SandTile()));
		terrainList.slots.add(new Slot(new ClayTile()));
		terrainList.slots.add(new Slot(new WaterTile()));
		terrainList.slots.add(new Slot(new EmptyTile()));
		terrainList.slots.add(new Slot(new FarmTile()));
		terrainList.slots.add(new Slot(new DeepWaterTile()));
		terrainList.slots.add(new Slot(new FarmTile()));
		
		stonesList.slots.add(new Slot(new DirtPathTile()));
		stonesList.slots.add(new Slot(new TiledTile()));
		stonesList.slots.add(new Slot(new TiledTile2()));
		stonesList.slots.add(new Slot(new TiledTile3()));
		stonesList.slots.add(new Slot(new TiledTile4()));
		stonesList.slots.add(new Slot(new TiledTile5()));
		stonesList.slots.add(new Slot(new TiledTile6()));
		stonesList.slots.add(new Slot(new TiledTile7()));
		stonesList.slots.add(new Slot(new TiledTile8()));
		

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
		
		for(int i = 0; i < terrainList.slots.size(); i++) {
			UIElement e = terrainList.slots.get(i);

			int indexX = i % rowSize;
			int indexY = (int) (i / rowSize);
			
			e.setX(indexX * 94 + x + 8 * UILayer.SCALE);
			e.setY(indexY * - 94 + y + 291 * UILayer.SCALE);
		}
		
		for(int i = 0; i < stonesList.slots.size(); i++) {
			UIElement e = stonesList.slots.get(i);

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
class Slot extends UIElement {
	
	private static Sprite background = Assets.getSprite("slot-background");
	private static Sprite backgroundPressed = Assets.getSprite("slot-background-pressed");
	private static Sprite backgroundHovering = Assets.getSprite("slot-background-hovering");
	
	public Tile tile;
	
	private int width, height;
	
	public Slot(Tile tile) {
		super("slot-" + tile.getId(), 0, 0, 94, 94);
		
		this.tile = tile;
		
		width = w - 6;
		height = (int)(((float)(width) / 64f) * 48f);;
	}
	
	@Override
	protected void onDeClick() {
		MouseInput.object = false;
		MouseInput.currentlyDrawingTileId = tile.getId();
	}

	@Override
	public void render(SpriteBatch b) {
		if(pressed) {	
			b.draw(backgroundPressed, x, y, w, h);	
		} else if(hovering){
			b.draw(backgroundHovering, x, y, w, h);	
		} else {
			b.draw(background, x, y, w, h);	
		}
		
		b.draw(tile.getCurrentSprite(), x, y + 6, width, height);
	}
	
	@Override
	public java.awt.Rectangle getBounds() {
		return new java.awt.Rectangle(x, UILayer.HEIGHT - y - h, w, h);
	}
}
