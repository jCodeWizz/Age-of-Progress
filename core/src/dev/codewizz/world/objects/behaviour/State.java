package dev.codewizz.world.objects.behaviour;

public class State {

    private final Action action;

    public State(Action action) {
        this.action = action;
    }

    public boolean isFinal() {
        return action == null;
    }

    public Action getAction() {
        return action;
    }
}
