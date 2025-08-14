package dev.codewizz.world.objects.behaviour.tasks;

import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.behaviour.State;
import dev.codewizz.world.objects.behaviour.Task;

public class ClearInventoryTask extends Task {

    public ClearInventoryTask(TaskableObject object) {
        super(object);

        State a = new State(null);
    }
}
