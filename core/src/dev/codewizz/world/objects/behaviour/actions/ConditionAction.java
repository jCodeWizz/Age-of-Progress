package dev.codewizz.world.objects.behaviour.actions;

import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.behaviour.Action;
import dev.codewizz.world.objects.behaviour.State;

public class ConditionAction extends Action {

    public ConditionAction(TaskableObject object, State... next) {
        super(object, next);
    }

    @Override
    public void start() {
        if (condition()) {

        } else {

        }
        finish();
    }

    @Override
    public void update(float dt) {

    }

    public boolean condition() {
        return true;
    }
}
