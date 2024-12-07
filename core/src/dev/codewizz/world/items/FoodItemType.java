package dev.codewizz.world.items;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class FoodItemType extends ItemType {

    private int foodPoints = 1;

    public FoodItemType(String name, String id) {
        super(name, id);
    }

    public FoodItemType(String name, String id, Sprite sprite) {
        super(name, id, sprite);
    }

    public FoodItemType(String name, String id, int foodPoints) {
        super(name, id);

        this.foodPoints = foodPoints;
    }

    public FoodItemType(String name, String id, Sprite sprite, int foodPoints) {
        super(name, id, sprite);

        this.foodPoints = foodPoints;
    }

    public int getFoodPoints() {
        return foodPoints;
    }
}
