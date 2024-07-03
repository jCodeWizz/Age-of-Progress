package dev.codewizz.modding.events;

import dev.codewizz.world.World;

public class SaveWorldEvent {

    private World world;

    public SaveWorldEvent(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
