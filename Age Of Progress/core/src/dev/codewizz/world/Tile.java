package dev.codewizz.world;

import java.awt.Polygon;

import com.badlogic.gdx.graphics.g2d.Sprite;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.pathfinding.CellGraph;
import dev.codewizz.world.pathfinding.Link;

public abstract class Tile {
	
	protected Cell cell;
	protected Sprite texture;
	protected String name;
	protected String id;
	protected int cost = 1;
	
	public Tile(Cell cell) {
		this.cell = cell;
		this.id = "no-id";
		this.texture = Assets.getSprite("grass-tile");
		this.name = "Grass Tile";
	}

	public void onPlace() {};
	public void onDestroy() {};
	public void update() {};
	
	public void place() {/*
		Cell[] cells = this.cell.getCrossedNeighbours();
		for(int i = 0; i < 4; i++) {
			if(cells[i] != null) {
				cells[i].tile.update();				
			}
		}
		
		CellGraph c = Main.inst.world.cellGraph;
		if(cost != -1) {
			if(c.containsCell(cell)) {
				for(Link link : c.getLinks(cell)) {
					link.setCost(cost);
				}
			} else {
				Cell[] neighBours = cell.getCrossedNeighbours();
				for(int i = 0; i < neighBours.length; i++) {
					if(neighBours[i] != null) {
						c.connectCells(cell, neighBours[i], this.cost);
					}
				}
			}
		} else {
			c.removeConnections(cell);
		}
		*/
		onPlace();
	}
	
	public Sprite getCurrentSprite() {
		return texture;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCurrentSprite(Sprite texture) {
		this.texture = texture;
	}
 	
	public Polygon getHitbox() {
		return new Polygon(cell.getXPoints(), cell.getYPoints(), 4);
	}
	
	public boolean[] checkNeighbours() {
		boolean[] data = new boolean[] { false, false, false, false };
		Cell[] neighbours = this.cell.getNeighbours();

		for (int i = 0; i < neighbours.length; i++) {
			if (neighbours[i] == null) {
				data[i] = false;
			} else {
				data[i] = neighbours[i].getTile().getId().equals(this.id);
			}
		}
		return data;
	}
	
	public boolean[] checkNeighbours(String id) {
		boolean[] data = new boolean[] { false, false, false, false };
		Cell[] neighbours = this.cell.getNeighbours();

		for (int i = 0; i < neighbours.length; i++) {
			if (neighbours[i] == null) {
				data[i] = false;
			} else {
				data[i] = neighbours[i].getTile().getId().equals(id);
			}
		}
		return data;
	}
	
	public String getId() {
		return id;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		
		CellGraph c = Main.inst.world.cellGraph;
		if(cost != -1) {
			if(c.containsCell(cell)) {
				for(Link link : c.getLinks(cell)) {
					link.setCost(cost);
				}
			} else {
				Cell[] neighBours = cell.getCrossedNeighbours();
				for(int i = 0; i < neighBours.length; i++) {
					if(neighBours[i] != null) {
						c.connectCells(cell, neighBours[i], this.cost);
					}
				}
			}
		} else {
			c.removeConnections(cell);
		}
		this.cost = cost;
	}
}
