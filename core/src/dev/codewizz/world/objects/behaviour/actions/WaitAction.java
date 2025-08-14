package dev.codewizz.world.objects.behaviour.actions;

import dev.codewizz.gfx.Animation;
import dev.codewizz.utils.Timer;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.behaviour.Action;
import dev.codewizz.world.objects.behaviour.State;
import dev.codewizz.world.objects.hermits.Hermit;

public class WaitAction extends Action {

    private final Timer timer;
    private final Animation animation;

    public WaitAction(float time, Animation animation, TaskableObject object, State... next) {
        super(object, next);

        this.animation = animation;
        timer = new Timer(time) {
            @Override
            public void timer() {
                //TODO: transition
                finish();
            }
        };
    }

    @Override
    public void start() {
        if (animation != null && object instanceof Hermit) {
            ((Hermit) object).setTaskAnimation(animation);
        }
    }

    @Override
    public void update(float dt) {
        timer.update(dt);
    }
}
