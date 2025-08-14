package dev.codewizz.world.objects.behaviour;

import dev.codewizz.world.objects.TaskableObject;

public abstract class Task {

    protected TaskableObject object;
    protected State currentState;

    public Task(TaskableObject object) {
        this.object = object;
    }

    public void transition(State toState) {
        if (toState.isFinal()) {
            finish();
        } else {
            currentState = toState;
            currentState.getAction().start();
        }
    }

    public void update(float dt) {
        currentState.getAction().update(dt);
    }

    public void interrupt() {
        currentState.getAction().interrupt();
    }

    public void finish() {
        object.finishCurrentTask();
    }
}
