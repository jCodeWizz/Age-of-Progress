package dev.codewizz.world.objects.behaviour.actions;

import dev.codewizz.world.Cell;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.behaviour.Action;
import dev.codewizz.world.objects.behaviour.State;

public class MoveAction extends Action {

    private final Cell goal;

    public MoveAction(Cell goal, TaskableObject object, State... next) {
        super(object, next);

        this.goal = goal;
    }

    @Override
    public void start() {
        boolean success = object.getAgent().setGoal(goal);
        if (!success) {
            interrupt();
        }
    }

    @Override
    public void update(float dt) {
        if (object.getAgent().path.isEmpty()) {
            //TODO: transition
            finish();
        }
    }
}
