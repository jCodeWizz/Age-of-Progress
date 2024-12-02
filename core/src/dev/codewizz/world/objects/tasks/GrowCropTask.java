package dev.codewizz.world.objects.tasks;

import dev.codewizz.gfx.Animation;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.utils.Timer;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.hermits.Jobs;
import dev.codewizz.world.settlement.Crop;

public class GrowCropTask extends Task {

    private Hermit hermit;
    private final Crop crop;
    private final Animation animation = new Animation(-10f, 0, 0.1f, Assets.getSprite("hermit-axe-1"), Assets.getSprite("hermit-axe-2"));
    private boolean reached = false;
    private final Timer timer;

    public GrowCropTask(Crop crop) {
        super(Jobs.Farmer);

        this.crop = crop;

        timer = new Timer(2f) {
            @Override
            public void timer() {
                finish();
            }
        };
    }

    @Override
    public void finish() {
        crop.untask();
        hermit.setTaskAnimation(null);
        hermit.finishCurrentTask();

        if (crop.fullyGrown()) {
            crop.harvest();
            return;
        }

        crop.grow();
    }


    @Override
    public void stop() {
        hermit.getAgent().stop();
        hermit.setTaskAnimation(null);
        hermit.finishCurrentTask();
    }

    @Override
    public void start(TaskableObject object) {
        this.hermit = (Hermit) object;
        hermit.getAgent().setGoal(crop.getCell());
        if (hermit.getAgent().path.isEmpty())
            reach();

        started = true;

    }

    @Override
    public void reach() {
        hermit.setFacing(Direction.West);
        hermit.setTaskAnimation(animation);
        reached = true;
    }

    @Override
    public void reset() {

    }

    @Override
    public void update(float d) {
        if (reached) {
            timer.update(d);
        }
    }

    @Override
    public String getName() {
        return "Taking care of crops";
    }
}
