package dev.codewizz.world.objects.behaviour.actions;

import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.behaviour.Action;
import dev.codewizz.world.objects.behaviour.State;

public class ExecuteAction extends Action {

    public ExecuteAction(TaskableObject object, State... next) {
        super(object, next);
    }

    @Override
    public void start() {
        execute();
        //TODO: transition
        finish();
    }

    @Override
    public void update(float dt) {

    }

    public void execute() {}
}
