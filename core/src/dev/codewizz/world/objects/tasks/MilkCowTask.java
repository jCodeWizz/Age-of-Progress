package dev.codewizz.world.objects.tasks;

import com.badlogic.gdx.math.Vector2;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Timer;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;
import dev.codewizz.world.objects.Animal;
import dev.codewizz.world.objects.Cow;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.hermits.Jobs;
import dev.codewizz.world.settlement.FarmArea;

public class MilkCowTask extends Task {

    private static final float REACH = 1000f;

    private Hermit hermit;
    private final Cow animal;

    private boolean reachedAnimal = false;
    private final Timer timer;


    public MilkCowTask(Cow animal) {
        this.animal = animal;
        this.jobs.add(Jobs.Farmer);
        animal.setTasked(true);

        timer = new Timer(10f) {
            @Override
            public void timer() {
                finish();
            }
        };
    }

    @Override
    public void finish() {
        hermit.setTaskAnimation(null);
        hermit.finishCurrentTask();

        Item item = new Item(animal.getX() + animal.getW()/2f, animal.getY(), ItemType.MILK).size(4);

        if (hermit.getInventory().roomFor(item)) {
            hermit.getInventory().addItem(item);
        } else {
            Main.inst.world.addItem(item);
        }

        animal.milk();
        animal.setTasked(false);
    }

    @Override
    public void stop() {
        hermit.setTaskAnimation(null);
        hermit.finishCurrentTask();

        animal.setTasked(false);
    }

    @Override
    public void start(TaskableObject object) {
        this.hermit = (Hermit) object;
        hermit.getAgent().setGoal(Main.inst.world.getCell(animal.getX(), animal.getY()));
        if (hermit.getAgent().path.isEmpty()) {
            reach();
        }
        started = true;
    }

    @Override
    public void reach() {
        if (!reachedAnimal) {
            if (Vector2.dst2(animal.getX(), animal.getY(), hermit.getX(), hermit.getY()) < REACH) {
                reachedAnimal = true;
                if (animal.getCurrentTask() != null) {
                    animal.getCurrentTask().stop();
                }
                animal.getAgent().stop();
            } else {
                hermit.getAgent().setGoal(Main.inst.world.getCell(animal.getX(), animal.getY()));
                if (hermit.getAgent().path.isEmpty()) {
                    reachedAnimal = true;
                }
            }
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public void update(float d) {
        if (reachedAnimal) {
            animal.preventWandering();
            timer.update(d);
        }
    }

    @Override
    public String getName() {
        return "Milking " + animal.getName();
    }
}