package dev.codewizz.world.objects.tasks;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.items.FoodItemType;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;
import java.util.List;

public class ConsumeTask extends Task {

    private final static int NUTRITION_MULTIPLIER = 50;

    private Hermit hermit;

    @Override
    public void finish() {
        hermit.finishCurrentTask();
    }

    @Override
    public void stop() {
        hermit.getAgent().stop();
        hermit.finishCurrentTask();
    }

    @Override
    public void start(TaskableObject object) {
        this.hermit = (Hermit) object;
        boolean success = object.getAgent().setGoal(Main.inst.world.settlement.getCell());
        if(object.getAgent().path.isEmpty() || !success)
            reach();
        started = true;
    }

    @Override
    public void reach() {
        while (hermit.getFoodNeed() > 0 || hermit.getDrinkNeed() > 0) {
            List<Item> items = Main.inst.world.settlement.inventory.getItems().values().stream().filter(item -> item.getType() instanceof FoodItemType).toList();
            if (!items.isEmpty()) {
                boolean food = hermit.getFoodNeed() > hermit.getDrinkNeed();
                Item best = items.get(0);
                for (Item i : items) {
                    if (food) {
                        if (((FoodItemType)i.getType()).getFoodNutrition() > ((FoodItemType)best.getType()).getFoodNutrition())  {
                            best = i;
                        }
                    } else {
                        if (((FoodItemType)i.getType()).getDrinkNutrition() > ((FoodItemType)best.getType()).getDrinkNutrition()) {
                            best = i;
                        }
                    }
                }
                Main.inst.world.settlement.inventory.removeItem(new Item(best.getType()));
                hermit.setFoodNeed(hermit.getFoodNeed() - ((FoodItemType) best.getType()).getFoodNutrition() * NUTRITION_MULTIPLIER);
                hermit.setDrinkNeed(hermit.getDrinkNeed() - ((FoodItemType) best.getType()).getDrinkNutrition() * NUTRITION_MULTIPLIER);
            } else {
                break;
            }
        }
        if (hermit.getFoodNeed() > 0 || hermit.getDrinkNeed() > 0) {
            hermit.setDaysWithoutFood(hermit.getDaysWithoutFood() + 1);

            if (hermit.getDaysWithoutFood() > 3) {
                hermit.damage(50f);
            }
        } else {
            hermit.setDaysWithoutFood(0);
        }

        finish();
    }

    @Override
    public void reset() {

    }

    @Override
    public void update(float d) {

    }

    @Override
    public String getName() {
        return "Eating";
    }
}
