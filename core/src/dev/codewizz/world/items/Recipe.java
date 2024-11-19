package dev.codewizz.world.items;

import java.util.ArrayList;

public class Recipe {

    private String type;
    private float time;

    private Item[] costs;
    private Item[] result;

    public Recipe(String type, float time, Item[] costs, Item[] result) {
        this.type = type;
        this.time = time;
        this.costs = costs;
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public float getTime() {
        return time;
    }

    public Item[] getCosts() {
        return costs;
    }

    public Item[] getResult() {
        return result;
    }

}