package dev.codewizz.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dev.codewizz.gfx.Renderer;
import dev.codewizz.modding.events.Event;
import dev.codewizz.modding.events.GenerateChunkEvent;
import dev.codewizz.utils.Utils;
import dev.codewizz.world.objects.Mushrooms;
import dev.codewizz.world.objects.PineTree;
import dev.codewizz.world.objects.Rock;
import dev.codewizz.world.objects.Tree;
import dev.codewizz.world.tiles.*;

import java.awt.*;
import java.util.List;

public class Chunk implements Comparable<Chunk> {

	public static final int SIZE = 8;
	
	private final Cell[][] grid;
	
	private final World world;
	private final float x, y;
	private final Vector2 index;
	private final Rectangle bounds;
	
	private boolean loaded;
	private boolean initialized;
	private boolean generated;
	
	public Chunk(World world, int indexX, int indexY) {
		grid = new Cell[SIZE][SIZE];
		this.index = new Vector2(indexX, indexY);

		this.x = indexX * Chunk.SIZE * 32 - indexY * Chunk.SIZE * 32;
		this.y = indexX * Chunk.SIZE * -16 - indexY * Chunk.SIZE * 16;

		this.bounds = new Rectangle((int)x - 28 * SIZE, (int)y - 28 * SIZE, 64 * SIZE, 34 * SIZE);
		
		this.world = world;	
	}
	
	public void init() {
		synchronized (world.tree) {
			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid.length; j++) {
					Cell cell = new Cell(world, this, x + i * 32 - j * 32, y + i * -16 - j * 16, i, j);

					grid[i][j] = cell;
					world.tree.set(cell.x, cell.y, cell);
					world.cellGraph.addCell(cell);
				}
			}

			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid.length; j++) {
					grid[i][j].init(world.cellGraph);
				}
			}

			initialized = true;
		}
	}
	
	public void generate() {
		this.generated = true;
		
		if(Event.dispatch(new GenerateChunkEvent(world, this))) {
			spawnRivers();
			spawnResources();
			spawnTree();
			spawnRock();
		}
	}
	
	public void render(SpriteBatch b) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j].render(b);
			}
		}
	}
	
	public void renderDebug() {
		if(initialized) {
			Vector2 a = new Vector2(grid[0][0].x + 32, grid[0][0].y + 48);
			Vector2 b = new Vector2(grid[0][SIZE-1].x, grid[0][SIZE-1].y + 32);
			Vector2 c = new Vector2(grid[SIZE-1][SIZE-1].x + 32, grid[SIZE-1][SIZE-1].y + 16);
			Vector2 d = new Vector2(grid[SIZE-1][0].x + 64, grid[SIZE-1][0].y + 32);
			
			Renderer.drawDebugLine(a, b);
			Renderer.drawDebugLine(b, c);
			Renderer.drawDebugLine(c, d);
			Renderer.drawDebugLine(d, a);
		}
	}
	
	public void load() {
		this.loaded = true;
	}
	
	private void spawnResources() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {

				Cell cell = grid[i][j];

				if (cell.tile.getId().equals("aop:sand-tile")) {
					float e = 1f;
					float n = (float) world.noise.noise((cell.indexX + this.index.x * SIZE) * e, (cell.indexY + this.index.y * SIZE) * e);

					if (n > 0.2f) {
						cell.setTile(new ClayTile());
					}
				} else if (cell.tile.getId().equals("aop:grass-tile")) {
					float e = 20f;
					float n = (float) world.noise.noise((cell.indexX + this.index.x * SIZE) * e, (cell.indexY + this.index.y * SIZE) * e);

					if (n > 0.65f) {
						cell.setTile(new FlowerTile());
					}
					
					e = 1f;
					n = (float) world.noise.noise((cell.indexX + this.index.x * SIZE) * e, (cell.indexY + this.index.y * SIZE) * e);
					
					if(n > 0.65f) {
						cell.setTile(new DirtTile());
					}
				}
			}
		}
	}

	private void spawnRock() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {

				Cell cell = grid[i][j];
				if (cell.tile.getId().equals("aop:grass-tile")) {
					float e = 21f;
					float n = (float) world.noise.noise((cell.indexX + this.index.x * SIZE) * e, (cell.indexY + this.index.y * SIZE) * e);

					if (n > 0.8f) {
						List<Cell> cells = world.findCell(cell.x, cell.y, 3, false, "aop:grass-tile");

						for (Cell c : cells) {
							
							if(Utils.getRandom(1, 4) < 3) {
								if(c.object == null)
									c.setObject(new Mushrooms(c.x, c.y));
							} 
						}
					} else if (n > 0.7f) {
						if(cell.object == null)
							cell.setObject(new Rock(cell.x, cell.y));
							
					}
				}
			}
		}
	}

	private void spawnRivers() {
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				
				Cell cell = grid[i][j];

				float n = (float) world.noise.noise((cell.indexX + this.index.x * SIZE) / 3f, (cell.indexY + this.index.y * SIZE) / 3f);
				
				n = Math.abs(n);
				
				if(n <= 0.03) {
					cell.setTile(new DeepWaterTile());
				} else if(n <= 0.075f) {
					cell.setTile(new WaterTile());
				} else if(n <= 0.15f) {
					cell.setTile(new SandTile());
				}
			}
		}
	}

	private void spawnTree() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {

				Cell cell = grid[i][j];

				if (cell.tile.getId().equals("aop:grass-tile")) {
					float e = 5f;
					float n = (float) world.noise.noise((cell.indexX + this.index.x * SIZE)* e, (cell.indexY + this.index.y * SIZE) * e);

					if (n > 0.4f && cell.object == null) {
						if(Utils.getRandom(1, 4) < 3) {
							cell.setObject(new Tree(cell.x, cell.y));
						} else {
							cell.setObject(new PineTree(cell.x, cell.y));
						}
					}
				}
			}
		}
	}
	
	public void markGenerated() {
		generated = true;
	}
	
	public Rectangle getBounds() {
		return bounds;
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

	public void setGenerated(boolean generated) {
		this.generated = generated;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public Vector2 getIndex() {
		return index;
	}

	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public int compareTo(Chunk o) {
        return Float.compare(o.getY(), getY());
	}
}
