package dev.codewizz.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.world.tiles.DirtTile;

public class Chunk implements Comparable<Chunk> {

	public static final int SIZE = 8;
	
	private Cell[][] grid;
	
	private World world;
	private float x, y;
	private Vector2 index;
	
	private boolean loaded;
	private boolean generated;
	
	public Chunk(World world, float x, float y, int indexX, int indexY) {
		grid = new Cell[SIZE][SIZE];
		this.index = new Vector2(indexX, indexY);

		this.world = world;	
		this.x = x;
		this.y = y;
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid.length; j++) {
				Cell cell = new Cell(x + i * 32 - j * 32, y + i * -16 - j * 16, i, j);
				
				grid[i][j] = cell;
				world.tree.set(cell.x, cell.y, cell);
				world.cellGraph.addCell(cell);
			}	
		}
		this.generated = true;
	
	}
	
	public void init() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid.length; j++) {
				grid[i][j].init(world.cellGraph, world, this);
				
				if(i == 0 || i == Chunk.SIZE - 1 || j == 0 || j == Chunk.SIZE - 1) {
					grid[i][j].setTile(new DirtTile());
				}
			}	
		}
	}
	
	public void generate() {
		this.generated = true;
	}
	
	public void render(SpriteBatch b) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j].render(b);
			}
		}
	}
	
	public void load() {
		this.loaded = true;
		
		
	}
	
	public void unload() {
		this.loaded = false;
	}
	
	public Cell[][] getGrid() {
		return grid;
	}

	public World getWorld() {
		return world;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public boolean isGenerated() {
		return generated;
	}
	
	public Vector2 getIndex() {
		return index;
	}

	@Override
	public int compareTo(Chunk o) {
		if(o.getY() > getY()) {
			return 1;
		} else if(o.getY() < getY()) {
			return -1;
		} else {
			return 0;
		}
	}
}
