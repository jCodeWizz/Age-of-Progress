package dev.codewizz.world;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.dongbat.jbump.util.MathUtils;

import dev.codewizz.gfx.Particle;
import dev.codewizz.gfx.Renderable;
import dev.codewizz.gfx.Shaders;
import dev.codewizz.input.MouseInput;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.utils.Utils;
import dev.codewizz.utils.WNoise;
import dev.codewizz.utils.quadtree.Point;
import dev.codewizz.utils.quadtree.QuadTree;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.WorldData;
import dev.codewizz.utils.serialization.RCDatabase;
import dev.codewizz.world.objects.Mushrooms;
import dev.codewizz.world.objects.Rock;
import dev.codewizz.world.objects.Tree;
import dev.codewizz.world.pathfinding.CellGraph;
import dev.codewizz.world.settlement.Settlement;
import dev.codewizz.world.tiles.ClayTile;
import dev.codewizz.world.tiles.FlowerTile;
import dev.codewizz.world.tiles.SandTile;
import dev.codewizz.world.tiles.WaterTile;

public class World {

	public static final int WORLD_SIZE_W = 48;
	public static final int WORLD_SIZE_H = 96;
	public static final int WORLD_SIZE_WP = WORLD_SIZE_W * 64;
	public static final int WORLD_SIZE_HP = WORLD_SIZE_H * 16;
	public static final int RADIUS = 2;
	public static final int MAX_RIVER_LENGTH = 1000;
	public static final float E = 0.5f;

	public static int gameSpeed = 3;

	public QuadTree<Cell> tree;
	public Cell[][] grid;
	public List<Renderable> objects = new CopyOnWriteArrayList<>();
	public List<Particle> particles = new CopyOnWriteArrayList<>();

	public Settlement settlement;
	public Nature nature;

	public CellGraph cellGraph;

	public WNoise noise = new WNoise();
	public WNoise terrainNoise = new WNoise();

	public boolean showInfoSartMenu = true;

	public World() {
		grid = new Cell[WORLD_SIZE_W][WORLD_SIZE_H];
		tree = new QuadTree<Cell>(-WORLD_SIZE_WP / 2, -WORLD_SIZE_HP / 2, WORLD_SIZE_WP / 2, WORLD_SIZE_HP / 2);
		cellGraph = new CellGraph();
		Main.inst.world = this;

		for (int i = 0; i < WORLD_SIZE_W; i++) {
			for (int j = 0; j < WORLD_SIZE_H; j++) {
				if (j % 2 == 0) {
					Cell cell = new Cell((i - (WORLD_SIZE_W / 2)) * 64, (j - (WORLD_SIZE_H / 2)) * 16, i, j, false);
					grid[i][j] = cell;
					tree.set(cell.x, cell.y, cell);
					cellGraph.addCell(cell);
				} else {
					Cell cell = new Cell((i - (WORLD_SIZE_W / 2)) * 64 + 32, (j - (WORLD_SIZE_H / 2)) * 16, i, j, true);
					grid[i][j] = cell;
					tree.set(cell.x, cell.y, cell);
					cellGraph.addCell(cell);
				}
			}
		}

		for (int i = 0; i < WORLD_SIZE_W; i++) {
			for (int j = 0; j < WORLD_SIZE_H; j++) {
				grid[i][j].init(cellGraph, this);
			}
		}

		nature = new Nature(this);
		init();
	}

	public static World openWorld(String path) {
		File file = Gdx.files.internal(path).file();
		RCDatabase db = RCDatabase.DeserializeFromFile(file);

		World world = new World(WorldData.load(db));
		GameObjectData.load(db);

		world.nature = new Nature(world);

		return world;
	}

	public World(WorldData data) {
		Main.inst.world = this;

		this.tree = data.tree;
		this.grid = data.grid;
		this.settlement = data.settlement;
		this.objects = data.objects;
		this.showInfoSartMenu = data.showStartInfo;
		this.cellGraph = data.cellGraph;

		for (int i = 0; i < WORLD_SIZE_W; i++) {
			for (int j = 0; j < WORLD_SIZE_H; j++) {
				grid[i][j].init(cellGraph, this);
			}
		}

		for (int i = 0; i < WORLD_SIZE_W; i++) {
			for (int j = 0; j < WORLD_SIZE_H; j++) {
				try {
					grid[i][j].setTile(Tile.getTileFromType(data.tileTypes[i + (j * World.WORLD_SIZE_W)], grid[i][j]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void init() {
		spawnRivers();
		spawnTree();
		spawnRock();
		spawnResources();

		nature.spawnHerd();
		nature.spawnHerd();
		nature.spawnHerd();
	}

	public void start(Settlement s) {
		this.settlement = s;
		this.showInfoSartMenu = false;

		for (int i = 0; i < 5; i++) {
			this.settlement.addHermit(Utils.getRandom(-75, 75) + s.getX(), Utils.getRandom(-75, 75) + s.getY());
		}
	}

	private void spawnResources() {
		for (int i = 0; i < WORLD_SIZE_W; i++) {
			for (int j = 0; j < WORLD_SIZE_H; j++) {

				Cell cell = grid[i][j];

				if (cell.tile.getType() == TileType.Sand) {
					float e = 1f;
					float n = (float) noise.noise(cell.indexX * e, cell.indexY * e);

					if (n > 0.2f) {
						cell.setTile(new ClayTile(cell));
					}
				} else if (cell.tile.getType() == TileType.Base) {
					float e = 20f;
					float n = (float) noise.noise(cell.indexX * e, cell.indexY * e);

					if (n > 0.65f) {
						cell.setTile(new FlowerTile(cell));
					}
				}
			}
		}
	}

	private void spawnRock() {
		for (int i = 0; i < WORLD_SIZE_W; i++) {
			for (int j = 0; j < WORLD_SIZE_H; j++) {

				Cell cell = grid[i][j];
				if (cell.tile.getType() == TileType.Base) {
					float e = 21f;
					float n = (float) noise.noise(cell.indexX * e, cell.indexY * e);

					if (n > 0.8f) {
						List<Cell> cells = this.findCell(cell.x, cell.y, 3, false, TileType.Base);

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
		Cell currentCell = grid[0][WORLD_SIZE_H / 2];
		int length = 0;
		boolean done = false;

		List<Cell> cells = new CopyOnWriteArrayList<>();
		List<Cell> cellsSand = new CopyOnWriteArrayList<>();

		do {
			float sizeNoise = (float) terrainNoise.noise(currentCell.indexX, currentCell.indexY);
			int size = (int) (3 * ((sizeNoise + 1) / 2)) + 2;
			currentCell.setTile(new WaterTile(currentCell));

			cells.addAll(currentCell.getCellsInRadius((int) (size)));
			cellsSand.addAll(currentCell.getCellsInRadius((int) (size * 2)));

			float noise = (float) terrainNoise.noise(currentCell.indexX * E, currentCell.indexY * E);
			int degrees = 90 + (int) (90 * noise);

			Direction dir = Direction.getDirFromDeg(degrees);
			Cell newCell = currentCell.getNeighbourCrossed(dir);
			if (newCell != null) {
				currentCell = newCell;
				length++;
			} else {
				if (length > 5) {
					done = true;
				}
			}
		} while (length < MAX_RIVER_LENGTH && done == false);

		for (int i = 0; i < cellsSand.size(); i++) {
			cellsSand.get(i).setTile(new SandTile(cellsSand.get(i)));
		}

		for (int i = 0; i < cells.size(); i++) {
			cells.get(i).setTile(new WaterTile(cells.get(i)));
		}
	}

	private void spawnTree() {
		for (int i = 0; i < WORLD_SIZE_W; i++) {
			for (int j = 0; j < WORLD_SIZE_H; j++) {

				Cell cell = grid[i][j];

				if (cell.tile.getType() == TileType.Base) {
					float e = 5f;
					float n = (float) noise.noise(cell.indexX * e, cell.indexY * e);

					if (n > 0.4f) {
						if(cell.object == null)
							cell.setObject(new Tree(cell.x, cell.y + Utils.RANDOM.nextFloat()));
					}
				}
			}
		}
	}

	public void renderTiles(SpriteBatch b) {
		Vector3 p1 = Main.inst.camera.cam.unproject(new Vector3(-64*4, -64*4, 0));
		Vector3 p2 = Main.inst.camera.cam.unproject(new Vector3(Gdx.graphics.getWidth() + 64*4, Gdx.graphics.getHeight() + 64*4, 0));

		Cell c1 = this.getCell(p1.x, p2.y);
		Cell c2 = this.getCell(p2.x, p1.y);

		int iX1 = 0;
		int iX2 = WORLD_SIZE_W;
		int iY1 = 0;
		int iY2 = WORLD_SIZE_H;

		if(c1 != null) {
			iX1 = c1.indexX;
			iY1 = c1.indexY;
		}
		
		if(c2 != null) {
			iX2 = c2.indexX;
			iY2 = c2.indexY;
		}
		
		for (int i = iY2 - 1; i >= iY1; i--) {
			for (int j = iX2 - 1; j >= iX1; j--) {
				grid[j][i].render(b);
			}
		}
		
		if (!Main.PAUSED) {
			if (MouseInput.hoveringOverCell != null) {
				if (MouseInput.clear) {
					b.draw(Assets.getSprite("tile-highlight"), MouseInput.hoveringOverCell.x,
							MouseInput.hoveringOverCell.y);
				} else {
					b.draw(Assets.getSprite("tile-highlight2"), MouseInput.hoveringOverCell.x,
							MouseInput.hoveringOverCell.y);
				}

				/*
				 * 
				 * TODO: Need to add a good way to select a tile and place it. This will be
				 * great when starting to work on menus.
				 * 
				 */
			}
		}

	}

	public void renderObjects(SpriteBatch b) {

		if (settlement != null) {
			for (int i = 0; i < gameSpeed; i++) {
				settlement.update(Gdx.graphics.getDeltaTime());
			}
		}
		if (nature != null) {
			for (int i = 0; i < gameSpeed; i++) {
				nature.update(Gdx.graphics.getDeltaTime());
			}
		}

		if (!Main.PAUSED) {
			for (Renderable object : objects) {
				for (int i = 0; i < gameSpeed; i++) {
					object.update(Gdx.graphics.getDeltaTime());
				}
			}

			for (Particle p : particles) {
				for (int i = 0; i < gameSpeed; i++) {
					p.update(Gdx.graphics.getDeltaTime());
				}
			}
		}

		Collections.sort(objects);

		for (Renderable object : objects) {

			if (object instanceof GameObject) {
				if (((GameObject) object).isSelected()) {
					b.setShader(Shaders.outlineShader);
					((GameObject) object).render(b);
					b.setShader(Shaders.defaultShader);
				}
			}

			object.render(b);

		}

		for (Particle p : particles) {
			p.render(b);
		}
		b.setColor(Color.WHITE);
		
		if(MouseInput.currentlyDrawingObject != null && MouseInput.object && MouseInput.hoveringOverCell != null) {
			
			MouseInput.currentlyDrawingObject.setX(MouseInput.hoveringOverCell.x);
			MouseInput.currentlyDrawingObject.setY(MouseInput.hoveringOverCell.y);
			MouseInput.currentlyDrawingObject.setFlip(MouseInput.rotate);
			
			if(MouseInput.hoveringOverCell.object == null)
				b.setColor(1f, 1f, 1f, 0.5f);
			else
				b.setColor(1f, 0.2f, 0.2f, 0.5f);
			
			MouseInput.currentlyDrawingObject.render(b);
			
			b.setColor(1f, 1f, 1f, 1f);

		}
	}

	public Cell getCellIndex(int x, int y) {
		x = MathUtils.clamp(x, 0, WORLD_SIZE_W - 1);
		y = MathUtils.clamp(y, 0, WORLD_SIZE_H - 1);

		return grid[x][y];
	}

	public List<GameObject> getObjects() {
		List<GameObject> list = new CopyOnWriteArrayList<>();

		for (Renderable r : objects) {
			if (r instanceof GameObject) {
				list.add((GameObject) r);
			}
		}

		return list;
	}

	public void renderDebug() {

		for (GameObject object : this.getObjects()) {
			object.renderDebug();
		}

	}

	public List<Cell> findCell(float x, float y, int r, boolean filter, TileType... types) {
		ArrayList<Cell> cells = new ArrayList<>();
		ArrayList<TileType> t = new ArrayList<>();

		for (int i = 0; i < types.length; i++) {
			t.add(types[i]);
		}

		Cell cell = getCell(x, y);

		if (cell == null)
			return cells;

		// if filter is on, tiletype should not be in list. if filer is off, tiletype
		// should be in list if (!evaluateTile(cell, filter, t)) {

		int m = r;
		int n = (int) ((float) r * 1.5f);

		int xx = cell.indexX - m / 2;
		int yy = cell.indexY - n / 2;

		int i, k = 0, l = 0;

		while (k < m && l < n) {

			for (i = l; i < n; ++i) {
				Cell c = getCellIndex(k + xx, i + yy);
				if (evaluateTile(c, filter, t)) {
					cells.add(c);
				}
			}
			k++;

			for (i = k; i < m; ++i) {

				Cell c = getCellIndex(i + xx, n - 1 + yy);
				if (evaluateTile(c, filter, t)) {
					cells.add(c);
				}

			}
			n--;

			if (k < m) {
				for (i = n - 1; i >= l; --i) {

					Cell c = getCellIndex(m - 1 + xx, i + yy);
					if (evaluateTile(c, filter, t)) {
						cells.add(c);
					}
				}
				m--;
			}

			if (l < n) {
				for (i = m - 1; i >= k; --i) {

					Cell c = getCellIndex(i + xx, l + yy);
					if (evaluateTile(c, filter, t)) {
						cells.add(c);
					}
				}
				l++;
			}
		}

		return cells;

	}

	public Cell getRandomCell() {
		return grid[Utils.getRandom(0, WORLD_SIZE_W)][Utils.getRandom(0, WORLD_SIZE_H)];
	}

	public Cell getCell(float x, float y) {
		x -= 32f;
		y -= 32f;

		Point<Cell>[] list = tree.searchIntersect(x-100, y-100, x+100, y+100);
		
		x+=32;
		y+=32;
		
		for(Point<Cell> cell : list) {
			if(cell.getValue().tile.getHitbox().contains(x, y)) {
				return cell.getValue();
			}
		}
		
		return null;
	}

	private boolean evaluateTile(Cell cell, boolean filter, List<TileType> t) {
		return (!filter && t.contains(cell.tile.type)) || filter && !t.contains(cell.tile.getType());
	}

	public Nature getNature() {
		return this.nature;
	}
}
