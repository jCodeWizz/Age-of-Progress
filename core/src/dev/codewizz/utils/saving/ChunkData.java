package dev.codewizz.utils.saving;

import dev.codewizz.utils.Logger;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Chunk;
import dev.codewizz.world.Tile;

import java.lang.reflect.InvocationTargetException;

public class ChunkData extends DataSaveLoader {

    private final Chunk chunk;

    public ChunkData(Chunk chunk) {
        this.chunk = chunk;

        save();
    }

    public ChunkData(Chunk chunk, byte[] total) {
        super(total);

        this.chunk = chunk;
    }

    private void save() {
        byte b = 0;
        b = ByteUtils.toByte(b, chunk.isGenerated(), 0);
        b = ByteUtils.toByte(b, chunk.isInitialized(), 1);
        b = ByteUtils.toByte(b, chunk.isLoaded(), 2);

        this.addByte(b);
        this.end();

        if(chunk.isInitialized()) {
            Cell[][] grid = chunk.getGrid();

            for(int x = 0; x < grid.length; x++) {
                for(int y = 0; y < grid[x].length; y++) {
                    Cell cell = grid[x][y];
                    cell.tile.save(this);
                }
            }
        }
    }

    public void load() {
        byte[] main = take();
        chunk.setGenerated(ByteUtils.toBoolean(main[0], 0));
        chunk.setInitialized(ByteUtils.toBoolean(main[0], 1));
        chunk.setLoaded(ByteUtils.toBoolean(main[0], 2));

        if(!chunk.isInitialized()) {
            return;
        }


        Cell[][] grid = chunk.getGrid();
         for(int x = 0; x < grid.length; x++) {
             for(int y = 0; y < grid[x].length; y++) {
                 Cell cell = grid[x][y];

                 byte[] data = take();
                 try {
                     String typeString = ByteUtils.toString(data, 0);

                     @SuppressWarnings("unchecked")
                     Class<? extends Tile> type = (Class<? extends Tile>) Class.forName(typeString);
                     cell.tile = type.getConstructor().newInstance();
                     cell.tile.setCell(cell);


                     int length = data.length - typeString.length() - 1;
                     byte[] otherData = new byte[length];
                     System.arraycopy(data, typeString.length() + 1, otherData, 0, otherData.length);



                     if(otherData.length > 0) {
                         Logger.log(typeString);
                         Logger.log(typeString.length() + 1);
                         Logger.log(data.length);


                         cell.tile.load(otherData);
                     }
                 } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                     throw new RuntimeException(e);
                 }
             }
         }
    }
}
