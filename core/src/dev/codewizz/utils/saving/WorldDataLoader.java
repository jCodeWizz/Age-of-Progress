package dev.codewizz.utils.saving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.Chunk;
import dev.codewizz.world.World;
import dev.codewizz.world.objects.Flag;
import dev.codewizz.world.settlement.Settlement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;

public class WorldDataLoader {

    private final HashMap<Vector2, GameObjectDataLoader> objectLoaders;
    private final HashMap<Vector2, Chunk> chunks;

    private final World world;

    public WorldDataLoader(World world) {
        this.world = world;

        objectLoaders = new HashMap<>();
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
        objectLoaders = new HashMap<>();
        chunks = new HashMap<>();

        File mainFile = Gdx.files.external(Assets.pathFolderSaves + name + "/world.save").file();
        File tileFolder = Gdx.files.external(Assets.pathFolderSaves + name + "/tiles/").file();
        File objectFolder = Gdx.files.external(Assets.pathFolderSaves + name + "/objects/").file();

        world = new World();
        Main.inst.world = world;

        loadTiles(tileFolder);
        loadObjects(objectFolder);
        loadMainFile(mainFile);

        world.chunkTree = chunks;
        //world.chunks.addAll(chunks.values().stream().filter(chunk -> !chunk.isInitialized()).toList());
        world.chunks.addAll(chunks.values());
        Collections.sort(world.chunks);

        world.setup();
        world.showInfoSartMenu = false;
    }

    private void saveMainFile(String mainFilePath) {
        GeneralDataSaveLoader loader = new GeneralDataSaveLoader();

        loader.addDouble(world.noise.getSeed());
        loader.end();

        loader.addByte(ByteUtils.toByte((byte)0, world.settlement != null, 0));
        if(world.settlement != null) {
            loader.addFloat(world.settlement.getX());
            loader.addFloat(world.settlement.getY());

            //TODO: Serialize inventories...........


        }
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

            world.settlement.getCell().setObject(new Flag());
        }
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

                String[] nameLoc = file.getName().split("\\.")[0].split(",");

                Vector2 loc = new Vector2(Integer.parseInt(nameLoc[0]), Integer.parseInt(nameLoc[1]));
                objectLoaders.put(loc, loader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public World getWorld() {
        return world;
    }
}
