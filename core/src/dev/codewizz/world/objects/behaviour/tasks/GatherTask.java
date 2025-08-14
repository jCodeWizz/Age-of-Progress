package dev.codewizz.world.objects.behaviour.tasks;

import dev.codewizz.gfx.Animation;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.objects.IGatherable;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.behaviour.State;
import dev.codewizz.world.objects.behaviour.Task;
import dev.codewizz.world.objects.behaviour.actions.ExecuteAction;
import dev.codewizz.world.objects.behaviour.actions.MoveAction;
import dev.codewizz.world.objects.behaviour.actions.WaitAction;

public class GatherTask extends Task {

    public GatherTask(GameObject toGather, TaskableObject object) {
        super(object);

        State a = new State(null);
        ExecuteAction executeAction = new ExecuteAction(object, a) {
            @Override
            public void execute() {
                ((IGatherable) toGather).gather();
            }
        };
        State b = new State(executeAction);
        Animation animation = new Animation(-10f, 0, 0.1f, Assets.getSprite("hermit-axe-1"), Assets.getSprite("hermit-axe-2"));
        WaitAction waitAction = new WaitAction(((IGatherable) toGather).duration(), animation, object, b);
        State c = new State(waitAction);
        MoveAction moveAction = new MoveAction(toGather.getCell() == null ? Main.inst.world.getCell(toGather.getX() + 30, toGather.getY() + 30) : toGather.getCell(), object, c);
        currentState = new State(moveAction);
    }
}
