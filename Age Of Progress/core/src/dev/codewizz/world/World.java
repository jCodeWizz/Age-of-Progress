package dev.codewizz.world;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.gfx.Particle;
import dev.codewizz.gfx.Renderable;
import dev.codewizz.gfx.Shaders;
import dev.codewizz.input.MouseInput;
import dev.codewizz.main.Main;
import dev.codewizz.modding.events.AddObjectEvent;
import dev.codewizz.modding.events.CreateWorldEvent;
import dev.codewizz.modding.events.Event;
import dev.codewizz.modding.events.LoadWorldEvent;
import dev.codewizz.modding.events.RemoveObjectEvent;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Utils;
import dev.codewizz.utils.WNoise;
import dev.codewizz.utils.quadtree.Point;
import dev.codewizz.utils.quadtree.QuadTree;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.WorldData;
import dev.codewizz.utils.serialization.RCDatabase;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.pathfinding.CellGraph;
import dev.codewizz.world.settlement.Settlement;

public class World {

	public static final int WORLD_SIZE_W = 128;
	public static final int WORLD_SIZE_H = 128;
	public static final int WORLD_SIZE_WP = WORLD_SIZE_W * 64;
	public static final int WORLD_SIZE_HP = WORLD_SIZE_H * 64;
	public static final int RADIUS = 2;
	public static final int MAX_RIVER_LENGTH = 1000;
	public static final float E = 0.5f;

	public static int gameSpeed = 3;

	public QuadTree<Cell> tree;
	public HashMap<String, Chunk> chunkTree = new HashMap<>();
	public List<Chunk> chunks = new CopyOnWriteArrayList<>();
	private List<Renderable> objects = new CopyOnWriteArrayList<>();
	public List<Particle> particles = new CopyOnWriteArrayList<>();

	public Settlement settlement;
	public Nature nature;

	public CellGraph cellGraph;

	public WNoise noise = new WNoise();
	public WNoise terrainNoise = new WNoise();

	public boolean showInfoSartMenu = true;

	public World() {
		long start = System.currentTimeMillis();
		
		tree = new QuadTree<Cell>(-WORLD_SIZE_WP * 2, -WORLD_SIZE_HP * 2, WORLD_SIZE_WP * 2, WORLD_SIZE_HP * 2);
		cellGraph = new CellGraph();
		Main.inst.world = this;

		nature = new Nature(this);
		
		Thread initThread = new Thread("create-world-thread" ) {
			@Override
			public void run() {
				init();
			}
		};
		
		initThread.start();
		
		Event.dispatch(new CreateWorldEvent());
		
		Logger.log("World creation time: " + (float)(System.currentTimeMillis() - start) / 1000.0f + " Seconds");
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
		this.settlement = data.settlement;
		this.objects = data.objects;
		this.showInfoSartMenu = data.showStartInfo;
		this.cellGraph = data.cellGraph;

		for (int i = 0; i < WORLD_SIZE_W; i++) {
			for (int j = 0; j < WORLD_SIZE_H; j++) {
				//grid[i][j].init(cellGraph, this);
			}
		}

		for (int i = 0; i < WORLD_SIZE_W; i++) {
			for (int j = 0; j < WORLD_SIZE_H; j++) {
				try {
					//grid[i][j].setTile(Registers.createTile(data.tiles[i + (j * World.WORLD_SIZE_W)], grid[i][j]));
				} catch (Exception e) {
					//grid[i][j].setTile(new EmptyTile());
					e.printStackTrace();
				}
			}
		}
		
		Event.dispatch(new LoadWorldEvent());
	}
	
	public Chunk addChunk(int indexX, int indexY) {
		
		float x = indexX * Chunk.SIZE * 32 - indexY * Chunk.SIZE * 32;
		float y = indexX * Chunk.SIZE * - 16 - indexY * Chunk.SIZE * 16;
		
		
		Chunk c = new Chunk(this, x, y, indexX, indexY);
		
		chunkTree.put(new Vector2(indexX, indexY).toString(), c);
		chunks.add(c);
		
		Collections.sort(chunks);

		return c;
	}

	public void init() {

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				addChunk(i, j);
			}
		}
		
		for(Chunk c : chunks) {
			c.init();
		}
		
		//nature.spawnHerd();
		//nature.spawnHerd();
		//nature.spawnHerd();
	}

	public void start(Settlement s) {
		this.settlement = s;
		this.showInfoSartMenu = false;

		for (int i = 0; i < 5; i++) {
			this.settlement.addHermit(Utils.getRandom(-75, 75) + s.getX(), Utils.getRandom(-75, 75) + s.getY());
		}
	}

	/*
	private void spawnResources() {
		for (int i = 0; i < WORLD_SIZE_W; i++) {
			for (int j = 0; j < WORLD_SIZE_H; j++) {

				Cell cell = grid[i][j];

				if (cell.tile.getId().equals("aop:sand-tile")) {
					float e = 1f;
					float n = (float) noise.noise(cell.indexX * e, cell.indexY * e);

					if (n > 0.2f) {
						cell.setTile(new ClayTile());
					}
				} else if (cell.tile.getId().equals("aop:grass-tile")) {
					float e = 20f;
					float n = (float) noise.noise(cell.indexX * e, cell.indexY * e);

					if (n > 0.65f) {
						cell.setTile(new FlowerTile());
					}
					
					e = 1f;
					n = (float) noise.noise(cell.indexX * e, cell.indexY * e);
					
					if(n > 0.65f) {
						cell.setTile(new DirtTile());
					}
				}
			}
		}
	}

	private void spawnRock() {
		for (int i = 0; i < WORLD_SIZE_W; i++) {
			for (int j = 0; j < WORLD_SIZE_H; j++) {

				Cell cell = grid[i][j];
				if (cell.tile.getId().equals("aop:grass-tile")) {
					float e = 21f;
					float n = (float) noise.noise(cell.indexX * e, cell.indexY * e);

					if (n > 0.8f) {
						List<Cell> cells = this.findCell(cell.x, cell.y, 3, false, "aop:grass-tile");

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
		
		for (int i = 0; i < WORLD_SIZE_W; i++) {
			for (int j = 0; j < WORLD_SIZE_H; j++) {
				
				Cell cell = grid[i][j];

				float n = (float) noise.noise((float)cell.indexX / 3f, (float)cell.indexY / 3f);
				
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
		for (int i = 0; i < WORLD_SIZE_W; i++) {
			for (int j = 0; j < WORLD_SIZE_H; j++) {

				Cell cell = grid[i][j];

				if (cell.tile.getId().equals("aop:grass-tile")) {
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
	*/
	
	public void renderTiles(SpriteBatch b) {

		
		for(Chunk chunk : chunks) {
			chunk.render(b);
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

	public List<GameObject> getGameObjects() {
		List<GameObject> list = new CopyOnWriteArrayList<>();

		for (Renderable r : objects) {
			if (r instanceof GameObject) {
				list.add((GameObject) r);
			}
		}

		return list;
	}

	public void renderDebug() {

		for (GameObject object : this.getGameObjects()) {
			object.renderDebug();
		}

	}

	public List<Cell> findCell(float x, float y, int r, boolean filter, String... ids) {
		ArrayList<Cell> cells = new ArrayList<>();
		ArrayList<String> t = new ArrayList<>();

		for (int i = 0; i < ids.length; i++) {
			t.add(ids[i]);
		}

		Cell cell = getCell(x, y);

		if (cell == null)
			return cells;

		// if filter is on, tiletype should not be in list. if filer is off, tiletype
		// should be in list if (!evaluateTile(cell, filter, t)) {

		for (Cell c : cell.getCellsInSquare(r)) {

			if (evaluateTile(c, filter, t)) {
				cells.add(c);
			}
		}
		return cells;

	}

	public Cell getRandomCell() {
		List<Cell> all = tree.getValues();
		return all.get(Utils.getRandom(0, all.size()));
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
	
	public void addItem(Item item) {
		objects.add(item);
	}
	
	public boolean addObject(GameObject object) {
		
		boolean proceed = Event.dispatch(new AddObjectEvent(object));
		
		if(proceed)
			objects.add(object);
		
		return proceed;
	}
	
	public boolean removeObject(GameObject object) {
		
		boolean proceed = Event.dispatch(new RemoveObjectEvent(object));
		
		if(proceed)
			objects.remove(object);
		
		return proceed;
	}
	
	public void removeItem(Item item) {
		objects.remove(item);
	}
	
	public List<Renderable> getObjects() {
		return objects;
	}

	private boolean evaluateTile(Cell cell, boolean filter, List<String> t) {
		return (!filter && t.contains(cell.tile.getId())) || filter && !t.contains(cell.tile.getId());
	}

	public Nature getNature() {
		return this.nature;
	}
}
