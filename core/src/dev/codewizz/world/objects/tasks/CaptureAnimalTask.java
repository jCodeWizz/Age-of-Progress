package dev.codewizz.world.objects.tasks;

import com.badlogic.gdx.math.Vector2;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Timer;
import dev.codewizz.world.objects.Animal;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.hermits.Jobs;
import dev.codewizz.world.settlement.FarmArea;

public class CaptureAnimalTask extends Task {

    private final float REACH = 1000f;

    private Hermit hermit;
    private final Animal animal;
    private FarmArea area;

    private boolean reachedAnimal = false;
    private final Timer timer;


    public CaptureAnimalTask(Animal animal, FarmArea area) {
        this.animal = animal;
        this.area = area;
        this.jobs.add(Jobs.Farmer);
        animal.setTasked(true);

        timer = new Timer(2f) {
            @Override
            public void timer() {
                hermit.setSpeed(animal.getSpeed());
            }
        };
    }

    @Override
    public void finish() {
        hermit.setTaskAnimation(null);
        hermit.finishCurrentTask();

        animal.setTasked(false);
        animal.setCaptured(true);
        animal.setArea(area);
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

        if (area == null) {
            stop();
        }

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

                hermit.getAgent().setGoal(area.getEntrances().get(0));
                if (hermit.getAgent().path.isEmpty()) {
                    reach();
                }

                animal.getAgent().stop();
                animal.getAgent().setGoal(area.getEntrances().get(0));
                if (animal.getAgent().path.isEmpty()) {
                    reach();
                }
            } else {

                hermit.getAgent().setGoal(Main.inst.world.getCell(animal.getX(), animal.getY()));
                if (hermit.getAgent().path.isEmpty()) {
                    Logger.log("Couldn't path: " + Vector2.dst2(animal.getX(), animal.getY(),
                            hermit.getX(),
                            hermit.getY()) + "/" + REACH);
                    reachedAnimal = true;
                    reach();
                }

            }
        } else {
            boolean s = area.join(animal);
            if (!s) {
                if (FarmArea.anyAvailable()) {
                    area = FarmArea.findArea(animal);
                    if (area != null) {
                        s = area.join(animal);
                    } else {
                        stop();
                    }
                }
            }

            if (!s) {
                stop();
            } else {
                animal.setX(area.getArea().get(0).getMiddlePoint().x);
                animal.setY(area.getArea().get(0).getMiddlePoint().y);

                if (animal.isInHerd()) {
                    animal.getHerd().removeMember(animal);
                    animal.setIsInHerd(false);
                }

                finish();
            }
            animal.getAgent().stop();
            hermit.setSpeed(20f);
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public void update(float d) {

        if (reachedAnimal) {
            timer.update(d);
        }
    }

    @Override
    public String getName() {
        return "capturing " + animal.getName() + " for pen";
    }
}