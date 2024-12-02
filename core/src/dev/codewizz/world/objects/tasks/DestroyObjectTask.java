package dev.codewizz.world.objects.tasks;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Timer;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.building.Wall;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.IBuy;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;

public class DestroyObjectTask extends Task {

    private final GameObject toDestroy;
    private Hermit hermit;
    private Timer dTime;
    private boolean reached;

    public DestroyObjectTask(GameObject object) {
        this.toDestroy = object;

        dTime = new Timer(5f) {
            @Override
            public void timer() {
                finish();
            }
        };
    }


    @Override
    public void finish() {
        if (toDestroy instanceof IBuy) {
            for (Item i : ((IBuy) toDestroy).costs()) {
                Item item = new Item(toDestroy.getFootPoint().x, toDestroy.getFootPoint().y, i.item);
                Main.inst.world.addItem(item.size(Math.round(i.getSize()/2f)));
            }
        }
        toDestroy.update(1);

        if(toDestroy instanceof Wall) {
            toDestroy.onDestroy();
            Main.inst.world.removeObject(toDestroy);
            toDestroy.setCell(null);
            if(toDestroy.isSelected()) toDestroy.deselect();
        } else {
            toDestroy.destroy();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void start(TaskableObject object) {
        this.hermit = (Hermit) object;

        Cell cell;
        if (toDestroy.getCell() != null) {
            cell = toDestroy.getCell();
        } else {
            cell = Main.inst.world.getCell(toDestroy.getX(), toDestroy.getY());
        }

        boolean success = object.getAgent().setGoal(cell);
        if(object.getAgent().path.isEmpty() || !success)
            reach();
        started = true;
    }

    @Override
    public void reach() {
        reached = true;
    }

    @Override
    public void reset() {

    }

    @Override
    public void update(float d) {
        if(reached) {
            dTime.update(d);
        }
    }

    @Override
    public String getName() {
        return "Destroying " + toDestroy.getName();
    }
}
