package dev.codewizz.world.items;

import dev.codewizz.utils.Utils;

public class ItemDrop {

    private ItemType type;
    private int min, max;

    public ItemDrop(ItemType type, int min, int max) {
        this.type = type;
        this.min = min;
        this.max = max;
    }

    public Item get() {
        return new Item(type, Utils.getRandom(min, max));
    }
}
