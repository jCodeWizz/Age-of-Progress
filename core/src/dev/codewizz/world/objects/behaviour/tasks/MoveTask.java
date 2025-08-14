package dev.codewizz.world.objects.behaviour.tasks;

import dev.codewizz.world.Cell;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.behaviour.State;
import dev.codewizz.world.objects.behaviour.Task;
import dev.codewizz.world.objects.behaviour.actions.MoveAction;

public class MoveTask extends Task {

    public MoveTask(TaskableObject object, Cell cell) {
        super(object);

        State a = new State(null);
        MoveAction action = new MoveAction(cell, object, a);
        currentState = new State(action);
    }
}
