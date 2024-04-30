package dev.codewizz.utils.saving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Pair;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Chunk;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.World;
import dev.codewizz.world.items.Inventory;
import dev.codewizz.world.objects.Flag;
import dev.codewizz.world.settlement.Settlement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class WorldDataLoader {

    public static HashMap<UUID, GameObject> objects;
    private final HashMap<Vector2, GameObjectDataLoader> loaders;
    private final HashMap<Vector2, Chunk> chunks;

    private final World world;

    public WorldDataLoader(World world) {
        this.world = world;

        loaders = new HashMap<>();
        objects = new HashMap<>();
        chunks = new HashMap<>();

        String name = "test_world";

        Assets.createFolder(Assets.pathFolderSaves + name + "/");
        Assets.createFolder(Assets.pathFolderSaves + name + "/objects/");
        Assets.createFolder(Assets.pathFolderSaves + name + "/tiles/");

        saveMainFile(Assets.pathFolderSaves + name + "/world.save");
        saveTiles(Assets.pathFolderSaves + name + "/tiles/");
        saveObjects(Assets.pathFolderSaves + name + "/objects/");
    }

    public WorldDataLoader(String name) {
        objects = new HashMap<>();
        chunks = new HashMap<>();
        loaders = new HashMap<>();

        File mainFile = Gdx.files.external(Assets.pathFolderSaves + name + "/world.save").file();
        File tileFolder = Gdx.files.external(Assets.pathFolderSaves + name + "/tiles/").file();
        File objectFolder = Gdx.files.external(Assets.pathFolderSaves + name + "/objects/").file();

        world = new World();
        Main.inst.world = world;

        loadObjects(objectFolder);
        loadTiles(tileFolder);
        loadMainFile(mainFile);

        world.chunkTree = chunks;
        world.chunks.addAll(chunks.values());
        Collections.sort(world.chunks);

        world.setup();
        world.showInfoStartMenu = false;
    }

    private void saveMainFile(String mainFilePath) {
        GeneralDataSaveLoader loader = new GeneralDataSaveLoader();

        loader.addDouble(world.noise.getSeed());
        loader.end();

        loader.addByte(ByteUtils.toByte((byte)0, world.settlement != null, 0));
        if(world.settlement != null) {
            loader.addFloat(world.settlement.getX());
            loader.addFloat(world.settlement.getY());

            loader.addInventory(world.settlement.getInventory());
        }
        loader.end();

        loader.addFloat(world.nature.spawnCounter);
        loader.addFloat(world.nature.timeCounter);
        loader.addFloat(world.nature.light);
        loader.addInteger(world.nature.dayCounter);

        byte b = 0;
        b = ByteUtils.toByte(b, world.nature.transition, 0);
        b = ByteUtils.toByte(b, world.nature.day, 1);
        loader.addByte(b);

        loader.end();


        File file = Gdx.files.external(mainFilePath).file();
        try {
            Files.write(file.toPath(), loader.getTotalBytes());
        } catch (IOException e) {
            Logger.error("Couldn't save to main file: " + mainFilePath);
        }
    }

    private void saveTiles(String tileFolderPath) {
        try {
            for(Chunk chunk : world.chunks) {
                ChunkData data = new ChunkData(chunk);

                String name = (int) chunk.getIndex().x + "," + (int)chunk.getIndex().y + ".chunk";

                File file = Gdx.files.external(tileFolderPath + name).file();
                Files.write(file.toPath(), data.getTotalBytes());
            }
        } catch (IOException ignored) {

        }
    }

    private void saveObjects(String objectFolderPath) {
        CopyOnWriteArrayList<GameObject> objects = new CopyOnWriteArrayList<>(world.getGameObjects());

        for(Chunk chunk : world.chunks) {
            if(!chunk.isGenerated()) continue;

            GameObjectDataLoader loader = new GameObjectDataLoader();
            loaders.put(chunk.getIndex(), loader);

            Cell[][] grid = chunk.getGrid();
            for(int i = 0; i < grid.length; i++) {
                for(int j = 0; j  < grid[i].length; j++) {
                    if(grid[i][j].getObject() != null) {
                        GameObject object = grid[i][j].getObject();
                        loader.save(object);
                        objects.remove(object);
                    }
                }
            }
        }

        for(Chunk chunk : world.chunks) {
            if(!chunk.isGenerated()) continue;

            GameObjectDataLoader loader = loaders.get(chunk.getIndex());

            for(GameObject object : objects) {
                if(chunk.getBounds().contains(object.getX(), object.getY())) {
                    loader.save(object);
                    objects.remove(object);
                }
            }

            String name = (int) chunk.getIndex().x + "," + (int)chunk.getIndex().y + ".chunk";

            File file = Gdx.files.external(objectFolderPath + name).file();
            try {
                Files.write(file.toPath(), loader.getTotalBytes());
            } catch (IOException ignored) {
                Logger.error("Error during saving of Chunk: " + chunk.getIndex());
            }
        }

        if(!objects.isEmpty()) {
            Logger.error("Not all objects were saved: " + objects.size());
        }
    }

    private void loadMainFile(File mainFile) {

        byte[] data = new byte[(int) mainFile.length()];
        try (FileInputStream inputStream = new FileInputStream(mainFile)) {
            inputStream.read(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GeneralDataSaveLoader loader = new GeneralDataSaveLoader(data);

        byte[] general = loader.take();
        world.noise.setSeed(ByteUtils.toDouble(general, 0));

        byte[] settlement = loader.take();
        if(ByteUtils.toBoolean(settlement[0], 0)) {
            float x = ByteUtils.toFloat(settlement, 1);
            float y = ByteUtils.toFloat(settlement, 5);
            world.settlement = new Settlement(x, y);

            Pair<Inventory, Integer> result = loader.readInventory(settlement, 9);

            world.settlement.inventory = result.getTypeA();

            world.settlement.getCell().setObject(new Flag());
        }

        byte[] nature = loader.take();
        world.nature.spawnCounter = ByteUtils.toFloat(nature, 0);
        world.nature.timeCounter = ByteUtils.toFloat(nature, 4);
        world.nature.light = ByteUtils.toFloat(nature, 8);
        world.nature.dayCounter = ByteUtils.toInteger(nature, 12);

        world.nature.transition = ByteUtils.toBoolean(nature[16], 0);
        world.nature.day = ByteUtils.toBoolean(nature[16], 1);

    }

    private void loadTiles(File tileFolder) {
        try {
            for(File file : tileFolder.listFiles()) {
                String[] nameLoc = file.getName().split("\\.")[0].split(",");
                Vector2 loc = new Vector2(Integer.parseInt(nameLoc[0]), Integer.parseInt(nameLoc[1]));

                Chunk chunk = new Chunk(world, (int)loc.x, (int)loc.y);
                chunk.init();

                byte[] data = new byte[(int) file.length()];
                try (FileInputStream inputStream = new FileInputStream(file)) {
                    inputStream.read(data);
                }
                ChunkData loader = new ChunkData(chunk, data);
                loader.load();

                chunks.put(loc, chunk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(GameObject object : objects.values()) {
            world.addObject(object);
            Logger.log(object.getUUID());
        }
    }

    private void loadObjects(File objectFolder) {
        try {
            for(File file : objectFolder.listFiles()) {
                GameObjectDataLoader loader = new GameObjectDataLoader();

                byte[] data = new byte[(int) file.length()];
                try (FileInputStream inputStream = new FileInputStream(file)) {
                    inputStream.read(data);
                }

                loader.loadFromData(data);
                loader.loadGameObjects();

                //String[] nameLoc = file.getName().split("\\.")[0].split(",");
                //Vector2 loc = new Vector2(Integer.parseInt(nameLoc[0]), Integer.parseInt(nameLoc[1]));
                //TODO: use for adding objects to correct chunk? :)

                objects.putAll(loader.getLoaded());
                Logger.log(objects.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public World getWorld() {
        return world;
    }
}
