package dev.codewizz.world.objects.behaviour;

import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;

public abstract class Action {

    protected TaskableObject object;
    protected State[] next;

    public Action(TaskableObject object, State... next) {
        this.object = object;
        this.next = next;
    }

    public abstract void start();
    public abstract void update(float dt);

    public void interrupt() {
        stop();
    }

    public void finish() {
        stop();
    }

    private void stop() {
        if (object instanceof Hermit) {
            ((Hermit) object).setTaskAnimation(null);
        }
        object.getAgent().stop();
    }


    public State[] getNext() {
        return next;
    }

    public void setNext(State[] next) {
        this.next = next;
    }

    public TaskableObject getObject() {
        return object;
    }

    public void setObject(TaskableObject object) {
        this.object = object;
    }
}