package dev.codewizz.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dev.codewizz.gfx.Particle;
import dev.codewizz.gfx.Renderable;
import dev.codewizz.gfx.Shaders;
import dev.codewizz.input.MouseInput;
import dev.codewizz.main.Main;
import dev.codewizz.modding.events.Event;
import dev.codewizz.modding.events.*;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Utils;
import dev.codewizz.utils.WNoise;
import dev.codewizz.utils.quadtree.Point;
import dev.codewizz.utils.quadtree.QuadTree;
import dev.codewizz.utils.saving.WorldDataLoader;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.pathfinding.CellGraph;
import dev.codewizz.world.settlement.Settlement;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class World {

    public static final int WORLD_SIZE_W = 128;
    public static final int WORLD_SIZE_H = 128;
    public static final int WORLD_SIZE_WP = WORLD_SIZE_W * 64;
    public static final int WORLD_SIZE_HP = WORLD_SIZE_H * 64;

    public static int gameSpeed = 3;

    public QuadTree<Cell> tree;
    public HashMap<Vector2, Chunk> chunkTree = new HashMap<>();
    public List<Chunk> chunks = new CopyOnWriteArrayList<>();
    private List<Renderable> objects = new CopyOnWriteArrayList<>();
    public List<Particle> particles = new CopyOnWriteArrayList<>();

    public List<Chunk> generationQueue = new CopyOnWriteArrayList<>();
    private Thread generationThread;

    public Settlement settlement;
    public Nature nature;

    public CellGraph cellGraph;
    public WNoise noise = new WNoise();

    public boolean showInfoStartMenu = true;
    private long start;

    public float timer = 0;

    public World() {

        start = System.currentTimeMillis();

        tree = new QuadTree<>(-WORLD_SIZE_WP * 2, -WORLD_SIZE_HP * 2, WORLD_SIZE_WP * 2,
                              WORLD_SIZE_HP * 2);
        cellGraph = new CellGraph();
        Main.inst.world = this;

        nature = new Nature(this);
    }

    public static World openWorld(String name) {
        WorldDataLoader loader = new WorldDataLoader(name);
        return loader.getWorld();
    }

    public World(WorldDataLoader data) {
        Main.inst.world = this;
        Event.dispatch(new LoadWorldEvent(this));
    }

    public Chunk addChunk(int indexX, int indexY) {
        if (chunkTree.containsKey(new Vector2(indexX, indexY))) {
            return null;
        }

        Chunk c = new Chunk(this, indexX, indexY);

        chunkTree.put(new Vector2(indexX, indexY), c);
        chunks.add(c);

        Collections.sort(chunks);

        return c;
    }

    public void setup() {
        Thread initThread = new Thread("create-world") {
            @Override
            public void run() {
                init();
                Event.dispatch(new CreateWorldEvent(Main.inst.world));
                Logger.log(
                        "World creation time: " + (float) (System.currentTimeMillis() - start) / 1000.0f + " Seconds");

                generationThread = new Thread("chunk-generation") {
                    @Override
                    public void run() {
                        try {
                            generate();
                        } catch (InterruptedException e) {
                            Logger.log("Stopping world generation!");
                        }
                    }
                };

                generationThread.start();
            }
        };
        initThread.start();
    }

    private void init() {

        Chunk c = addChunk(0, 0);
        if (c != null) {
            c.init();
        }

        // nature.spawnHerd();
        // nature.spawnHerd();
        // nature.spawnHerd();
    }

    private void generate() throws InterruptedException {
        synchronized (this) {
            while (Main.RUNNING && Main.PLAYING) {
                wait();
                if (!generationQueue.isEmpty()) {
                    for (Chunk chunk : generationQueue) {
                        if (Main.RUNNING && Main.PLAYING) {
                            chunk.init();
                            chunk.generate();
                            generationQueue.remove(chunk);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    public void start(Settlement s) {
        this.settlement = s;
        this.showInfoStartMenu = false;

        for (int i = 0; i < 5; i++) {
            this.settlement.addHermit(Utils.getRandom(-30, 30) + s.getX(),
                                      Utils.getRandom(-30, 30) + s.getY());
        }
    }

    public void renderTiles(SpriteBatch b) {
        timer += Gdx.graphics.getDeltaTime();

        float border = Gdx.graphics.getHeight() / 8f;

        Vector3 p1 = Main.inst.camera.cam.unproject(new Vector3(-border, -border, 0));
        Vector3 p2 = Main.inst.camera.cam.unproject(
                new Vector3(Gdx.graphics.getWidth() + border, Gdx.graphics.getHeight() + border,
                            0));

        Rectangle r = new Rectangle((int) p1.x, (int) p2.y, (int) (p2.x - p1.x),
                                    (int) -(p2.y - p1.y));

        for (Chunk chunk : chunks) {

            if (r.intersects(chunk.getBounds())) {

                if (chunk.isLoaded() && chunk.isInitialized()) {
                    chunk.render(b);
                    continue;
                }

                if (!chunk.isGenerated()) {
                    chunk.markGenerated();
                    generationQueue.add(chunk);
                    synchronized (this) {
                        this.notifyAll();
                    }
                    if (!chunkTree.containsKey(new Vector2(chunk.getIndex()).add(1, 0))) {
                        addChunk((int) chunk.getIndex().x + 1, (int) chunk.getIndex().y);
                        // c.init();
                    }
                    if (!chunkTree.containsKey(new Vector2(chunk.getIndex()).add(0, 1))) {
                        addChunk((int) chunk.getIndex().x, (int) chunk.getIndex().y + 1);
                        // c.init();
                    }
                    if (!chunkTree.containsKey(new Vector2(chunk.getIndex()).add(-1, 0))) {
                        addChunk((int) chunk.getIndex().x - 1, (int) chunk.getIndex().y);
                        // c.init();
                    }
                    if (!chunkTree.containsKey(new Vector2(chunk.getIndex()).add(0, -1))) {
                        addChunk((int) chunk.getIndex().x, (int) chunk.getIndex().y - 1);
                        // c.init();
                    }
                } else {
                    chunk.load();
                }
            } else if (chunk.isLoaded()) {
                chunk.unload();
            }
        }
        b.setColor(Color.WHITE);
        b.setShader(Shaders.defaultShader);

        if (!Main.PAUSED) {
            if (MouseInput.hoveringOverCell != null && (MouseInput.tileArea == null || (MouseInput.tileArea.start == null || MouseInput.tileArea.cells.size() == 0))) {
                if (MouseInput.clear) {
                    b.draw( Assets.getSprite("tile-highlight"), MouseInput.hoveringOverCell.x,
                           MouseInput.hoveringOverCell.y);
                } else {
                    b.draw(Assets.getSprite("tile-highlight2"), MouseInput.hoveringOverCell.x,
                           MouseInput.hoveringOverCell.y);
                }
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
                    object.render(b);
                    b.setShader(Shaders.defaultShader);
                } else if (((GameObject) object).isHovering()) {
                    b.setShader(Shaders.hoveringShader);
                    object.render(b);
                    b.setShader(Shaders.defaultShader);
                } else {
                    object.render(b);
                }
            } else {
                object.render(b);
            }
        }

        for (Particle p : particles) {
            p.render(b);
        }
        b.setColor(Color.WHITE);

        if (MouseInput.currentlyDrawingObject != null && MouseInput.object && MouseInput.hoveringOverCell != null) {

            MouseInput.currentlyDrawingObject.setX(MouseInput.hoveringOverCell.x);
            MouseInput.currentlyDrawingObject.setY(MouseInput.hoveringOverCell.y);
            MouseInput.currentlyDrawingObject.setFlip(MouseInput.rotate);

            if (MouseInput.hoveringOverCell.object == null) {
                b.setColor(1f, 1f, 1f, 0.5f);
            } else {
                b.setColor(1f, 0.2f, 0.2f, 0.5f);
            }

            MouseInput.currentlyDrawingObject.render(b);
            b.setColor(1f, 1f, 1f, 1f);

        }

        MouseInput.renderTileArea(b);
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

        for (Chunk c : chunks) {
            c.renderDebug();
        }

    }

    public List<Cell> findCell(float x, float y, int r, boolean filter, String... ids) {
        ArrayList<Cell> cells = new ArrayList<>();
        ArrayList<String> t = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            t.add(ids[i]);
        }

        Cell cell = getCell(x, y);

        if (cell == null) {
            return cells;
        }

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

    public Cell getCell(Vector2 coords) {
        return getCell(coords.x, coords.y);
    }

    public Cell getCellWorldIndex(int worldIndexX, int worldIndexY) {
        int chunkX = 0;
        int chunkY = 0;

        int indexX = 0;
        int indexY = 0;

        if (worldIndexX >= 0) {
            chunkX = worldIndexX / 8;
            indexX = worldIndexX % 8;
        } else {
            chunkX = (int) Math.floor((float) worldIndexX / 8f);
            indexX = Math.abs(worldIndexX % 8);
            if (indexX != 0) {
                indexX = Chunk.SIZE - indexX;
            }
        }

        if (worldIndexY >= 0) {
            chunkY = worldIndexY / 8;
            indexY = worldIndexY % 8;
        } else {
            chunkY = (int) Math.floor((float) worldIndexY / 8f);
            indexY = Math.abs(worldIndexY % 8);
            if (indexY != 0) {
                indexY = Chunk.SIZE - indexY;
            }
        }

        Chunk c = chunkTree.get(new Vector2(chunkX, chunkY));

        if (c != null) {
            return c.getGrid()[indexX][indexY];
        } else {
            return null;
        }
    }

    public Cell getCellWorldIndex(Vector2 index) {
        return getCellWorldIndex((int) index.x, (int) index.y);
    }

    public Cell getCell(float x, float y) {
        x -= 32f;
        y -= 32f;

        Point<Cell>[] list = tree.searchIntersect(x - 100, y - 100, x + 100, y + 100);

        x += 32;
        y += 32;

        for (Point<Cell> cell : list) {
            if (cell.getValue().tile.getHitbox().contains(x, y)) {
                return cell.getValue();
            }
        }

        return null;
    }

    public void addItem(Item item) {
        objects.add(item);
    }

    public boolean addObject(GameObject object, Reason reason) {

        boolean proceed = Event.dispatch(new AddObjectEvent(object, reason));

        if (proceed) {
            objects.add(object);
        }

        return proceed;
    }

    public boolean removeObject(GameObject object) {

        boolean proceed = Event.dispatch(new RemoveObjectEvent(object));

        if (proceed) {
            objects.remove(object);
        }

        return proceed;
    }

    public void removeItem(Item item) {
        objects.remove(item);
    }

    public List<Renderable> getObjects() {
        return objects;
    }

    private boolean evaluateTile(Cell cell, boolean filter, List<String> t) {
        return (!filter && t.contains(cell.tile.getId())) || filter && !t.contains(
                cell.tile.getId());
    }

    public Nature getNature() {
        return this.nature;
    }

    public void stop() {
        generationThread.interrupt();
    }
}
