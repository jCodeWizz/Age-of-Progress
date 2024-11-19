package dev.codewizz.world.items;

import java.util.ArrayList;

public class Recipe {

    private String type;
    private float time;

    private ArrayList<Item> costs;
    private ArrayList<Item> result;

    public Recipe(String type, float time, ArrayList<Item> costs, ArrayList<Item> result) {
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

    public ArrayList<Item> getCosts() {
        return costs;
    }

    public ArrayList<Item> getResult() {
        return result;
    }

}