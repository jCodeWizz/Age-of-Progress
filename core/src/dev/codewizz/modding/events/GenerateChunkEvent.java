package dev.codewizz.modding.events;

import dev.codewizz.world.Chunk;
import dev.codewizz.world.World;

public class GenerateChunkEvent extends Event {

    private final World world;
    private final Chunk chunk;

    public GenerateChunkEvent(World world, Chunk chunk) {
        this.world = world;
        this.chunk = chunk;
    }

    public World getWorld() {
        return world;
    }

    public Chunk getChunk() {
        return chunk;
    }
}
