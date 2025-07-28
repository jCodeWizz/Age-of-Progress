package dev.codewizz.world.items;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class FoodItemType extends ItemType {

    private int drinkNutrition = 0;
    private int foodNutrition = 0;

    public FoodItemType(String name, String id) {
        super(name, id);
    }

    public FoodItemType(String name, String id, Sprite sprite) {
        super(name, id, sprite);
    }

    public FoodItemType(String name, String id, int foodNutrition) {
        super(name, id);

        this.foodNutrition = foodNutrition;
    }

    public FoodItemType(String name, String id, Sprite sprite, int foodNutrition) {
        super(name, id, sprite);

        this.foodNutrition = foodNutrition;
    }

    public FoodItemType(String name, String id, int foodNutrition, int drinkNutrition) {
        super(name, id);

        this.foodNutrition = foodNutrition;
        this.drinkNutrition = drinkNutrition;
    }

    public FoodItemType(String name, String id, Sprite sprite, int foodNutrition, int drinkNutrition) {
        super(name, id, sprite);

        this.foodNutrition = foodNutrition;
        this.drinkNutrition = drinkNutrition;
    }

    public int getFoodNutrition() {
        return foodNutrition;
    }

    public int getDrinkNutrition() {
        return drinkNutrition;
    }
}
