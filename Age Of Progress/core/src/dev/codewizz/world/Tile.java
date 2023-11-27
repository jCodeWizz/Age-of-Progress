package dev.codewizz.world;

import java.awt.Polygon;

import com.badlogic.gdx.graphics.g2d.Sprite;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.pathfinding.CellGraph;
import dev.codewizz.world.pathfinding.Link;
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

public abstract class Tile {
	
	protected Cell cell;
	protected Sprite texture;
	protected TileType type;
	protected String name;
	protected int cost = 1;
	
	public Tile(Cell cell) {
		this.cell = cell;
		this.type = TileType.Base;
		this.texture = Assets.getSprite("base-tile");
		this.name = "Grass Tile";
	}
	
	public void onPlace() {};
	public void onDestroy() {};
	public void update() {};
	
	public void place() {
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
				data[i] = neighbours[i].getTile().getType() == this.type;
			}
		}
		return data;
	}
	
	public boolean[] checkNeighbours(TileType type) {
		boolean[] data = new boolean[] { false, false, false, false };
		Cell[] neighbours = this.cell.getNeighbours();

		for (int i = 0; i < neighbours.length; i++) {
			if (neighbours[i] == null) {
				data[i] = false;
			} else {
				data[i] = neighbours[i].getTile().getType() == type;
			}
		}
		return data;
	}
	
	public static Tile getTileFromType(TileType type, Cell cell) {
		if(type == TileType.Empty) {
			return new EmptyTile(cell);
		} else if(type == TileType.Base) {
			return new BaseTile(cell);
		} else if(type == TileType.Dirt) {
			return new DirtTile(cell);
		} else if(type == TileType.DirtPath) {
			return new DirtPathTile(cell);
		} else if(type == TileType.Tiled_1) {
			return new TiledTile(cell);
		} else if(type == TileType.Tiled_2) {
			return new TiledTile2(cell);
		} else if(type == TileType.Tiled_3) {
			return new TiledTile3(cell);
		} else if(type == TileType.Tiled_4) {
			return new TiledTile4(cell);
		} else if(type == TileType.Tiled_5) {
			return new TiledTile5(cell);
		} else if(type == TileType.Tiled_6) {
			return new TiledTile6(cell);
		} else if(type == TileType.Tiled_7) {
			return new TiledTile7(cell);
		} else if(type == TileType.Tiled_8) {
			return new TiledTile8(cell);
		} else if(type == TileType.Water) {
			return new WaterTile(cell);
		} else if(type == TileType.Sand) {
			return new SandTile(cell);
		} else if(type == TileType.Flower) {
			return new FlowerTile(cell);
		} else if(type == TileType.Clay) {
			return new ClayTile(cell);
		} else if(type == TileType.Farm) {
			return new FarmTile(cell);
		}

		Logger.error("TileType not found: " + type.toString());
		return null;
	}
	
	public TileType getType() {
		return type;
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
