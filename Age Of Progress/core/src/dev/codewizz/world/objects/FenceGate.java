package dev.codewizz.world.objects;

import java.awt.Polygon;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.utils.serialization.RCObject;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Serializable;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;
import dev.codewizz.world.pathfinding.CellGraph;
import dev.codewizz.world.settlement.FarmArea;

public class FenceGate extends GameObject implements Serializable, IBuy {

	private static Sprite texture = Assets.getSprite("fence-gate");
	private static Sprite texture2 = Assets.getSprite("fence-gate-flipped");

	private List<Item> costs = new CopyOnWriteArrayList<>();
	
	private int previousCost = 5;
	
	public FenceGate(float x, float y) {
		super(x, y);

		this.w = 64;
		this.h = 46;

		this.sortHeight = 25f;

		this.id = "aop:fence-gate";
		this.name = "Fence Gate";
		
		costs.add(new Item(ItemType.WOOD, 4));
		costs.add(new Item(ItemType.PLANKS, 8));
		
	}

	@Override
	public void update(float d) {
	}

	@Override
	public void render(SpriteBatch b) {
		if(flip) {
			b.draw(texture2, x + 2, y + 17);
		} else {
			b.draw(texture, x, y + 19);
		}
	}

	@Override
	public Polygon getHitBox() {
		if(flip) {
			return new Polygon(new int[] { (int) x + 2, (int) x + 2,(int) x + 16,(int) x + 16,(int) x + 52, (int)x + 52, (int) x + 64, (int) x + 64, (int) x + 52, (int) x + 52, (int) x + 16, (int) x + 16 },
					new int[] {        (int) y + 26, (int) y + 32, (int) y + 39, (int) y + 46, (int) y + 63, (int) y + 57, (int) y + 62, (int) y + 56, (int)y + 51, (int) y + 40, (int)y + 24, (int) y + 32}, 12);
		} else {
			return new Polygon(new int[] { (int) x + 2, (int) x + 2,(int) x + 14,(int) x + 14,(int) x + 50, (int)x + 50, (int) x + 64, (int) x + 64, (int) x + 50, (int) x + 50, (int) x + 14, (int) x + 14 },
					new int[] {        (int) y + 55, (int) y + 61, (int) y + 56, (int) y + 62, (int) y + 45, (int) y + 38, (int) y + 31, (int) y + 25, (int) y + 31, (int)y + 23, (int) y + 39, (int)y + 50}, 12);
		}
	}

	@Override
	public RCObject save(RCObject object) {
		return object;
	}

	@Override
	public void load(RCObject object) {
	}

	@Override
	public Sprite getMenuSprite() {
		return texture;
	}

	@Override
	public String getMenuName() {
		return "Fence Gate";
	}

	@Override
	public String getMenuDescription() {
		return "Will magically sort Hermits and Animals";
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public boolean conintues() {
		return false;
	}

	@Override
	public boolean available() {
		return true;
	}

	@Override
	public void onPlace(Cell cell) {
		previousCost = cell.tile.getCost();
		cell.tile.setCost(500);
		
		CellGraph c = Main.inst.world.cellGraph;
		
		Cell a = flip ? cell.getNeighbour(Direction.South) : cell.getNeighbour(Direction.South);
		Cell b = flip ? cell.getNeighbour(Direction.North) : cell.getNeighbour(Direction.North);
			
		c.removeConnections(cell);
		
		Cell[] n = cell.getAllNeighbours();
		for(int i = 0; i < n.length; i++) {
			c.removeConnection(n[i], cell);
		}
		
		c.connectCells(cell, a, cell.tile.getCost());
		c.connectCells(cell, b, cell.tile.getCost());
		c.connectCells(a, cell, cell.tile.getCost());
		c.connectCells(b, cell, cell.tile.getCost());
		
		FarmArea area = new FarmArea();
		boolean v = area.checkArea(cell);
		
		if(v) {
			Main.inst.world.settlement.areas.add(area);
		}
	}
	
	@Override
	public void onDestroy() {
		cell.tile.setCost(previousCost);
		
		CellGraph c = Main.inst.world.cellGraph;

		Cell[] n = cell.getAllNeighbours();
		for(int i = 0; i < n.length; i++) {
			c.connectCells(cell, n[i], cell.tile.getCost());
			c.connectCells(n[i], cell, n[i].tile.getCost());
		}
	}

	@Override
	public List<Item> costs() {
		return costs;
	}
}
