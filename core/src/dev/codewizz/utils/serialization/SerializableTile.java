package dev.codewizz.utils.serialization;

import dev.codewizz.utils.saving.ChunkData;

public interface SerializableTile {

    void save(ChunkData data);
    void load(byte[] data);
}
