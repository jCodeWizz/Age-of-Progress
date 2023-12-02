package dev.codewizz.world.objects;

import java.awt.Polygon;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.serialization.RCObject;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Serializable;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.pathfinding.CellGraph;

public class Fence extends GameObject implements Serializable, IBuy {

	private static Sprite texture = Assets.getSprite("fence");
	private static Sprite texture2 = Assets.getSprite("fence-flipped");

	private List<Item> costs = new CopyOnWriteArrayList<>();
	
	public Fence(float x, float y) {
		super(x, y);

		this.w = 64;
		this.h = 46;

		this.sortHeight = 25f;

		this.id = "aop:fence";
		this.name = "Fence";
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
			return new Polygon(new int[] { (int) x + 2, (int) x + 2,(int) x + 32,(int) x + 32,(int) x + 36, (int)x + 36, (int) x + 64, (int) x + 64, (int) x + 36, (int) x + 36, (int) x + 32, (int) x + 32 },
					new int[] {         (int) y + 25, (int) y + 31, (int) y + 45, (int) y + 55, (int) y + 55, (int) y + 48,(int) y + 61, (int) y + 55, (int) y + 42, (int)y + 31, (int) y + 31, (int)y + 40}, 12);
		} else {
			return new Polygon(new int[] { (int) x + 2, (int) x + 2,(int) x + 30,(int) x + 30,(int) x + 34, (int)x + 34, (int) x + 64, (int) x + 64, (int) x + 34, (int) x + 34, (int) x + 30, (int) x + 30 },
					new int[] {        (int) y + 55, (int) y + 61, (int) y + 48, (int) y + 55, (int) y + 55, (int) y + 45, (int) y + 31, (int) y + 25, (int) y + 40, (int)y + 31, (int) y + 31, (int)y + 42}, 12);
		}
	}

	@Override
	public RCObject save(RCObject object) {
		return object;
	}

	@Override
	public void load(RCObject object) {
		Main.inst.world.addObject(this);
	}

	@Override
	public Sprite getMenuSprite() {
		return texture;
	}

	@Override
	public String getMenuName() {
		return "Fence";
	}

	@Override
	public String getMenuDescription() {
		return "The first workshop for your craftsman";
	}

	@Override
	public GameObject getCopy(float x, float y) {
		return new Fence(x, y);
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
		CellGraph c = Main.inst.world.cellGraph;
		c.removeConnections(cell);
	}
	
	@Override
	public void onDestroy() {
		CellGraph c = Main.inst.world.cellGraph;
		
		Cell[] neighBours = cell.getCrossedNeighbours();
		for(int i = 0; i < neighBours.length; i++) {
			if(neighBours[i] != null) {
				c.connectCells(cell, neighBours[i], cell.tile.getCost());
			}
		}
	}

	@Override
	public List<Item> costs() {
		return costs;
	}
}
