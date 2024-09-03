package dev.codewizz.world.objects.tasks;

import dev.codewizz.main.Main;
import dev.codewizz.modding.events.Reason;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Timer;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.building.Wall;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.ConstructionObject;
import dev.codewizz.world.objects.IBuy;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;

public class ChangeObjectTask extends Task {
    
    private Hermit hermit;
    
    private final GameObject newObject;
    private final GameObject oldObject;

    private final Timer timer;
    private boolean reached;

    public ChangeObjectTask(GameObject newObject, GameObject oldObject) {
        this.newObject = newObject;
        this.oldObject = oldObject;
        
        timer = new Timer(1f) {
            @Override
            public void timer() {
                finish();
            }
        };
    }

    @Override
    public void finish() {
        if (oldObject instanceof IBuy) {
            for (Item i : ((IBuy) oldObject).costs()) {
                Item item = new Item(oldObject.getFootPoint().x, oldObject.getFootPoint().y, i.item);
                Main.inst.world.addItem(item.size(Math.round(i.getSize()/2f)));
            }
        }
        oldObject.update(1);

        if(oldObject instanceof Wall) {
            oldObject.onDestroy();
            Main.inst.world.removeObject(oldObject);
            oldObject.setCell(null);
            if(oldObject.isSelected()) oldObject.deselect();
        } else {
            oldObject.destroy();
        }

        ConstructionObject c = new ConstructionObject((Wall) newObject);
        Main.inst.world.addObject(c, Reason.FORCED);
    }

    @Override
    public void stop() {

    }

    @Override
    public void start(TaskableObject object) {
        this.hermit = (Hermit) object;

        Cell cell;
        if (oldObject.getCell() != null) {
            cell = oldObject.getCell();
        } else {
            cell = Main.inst.world.getCell(oldObject.getX(), oldObject.getY());
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
            timer.update(d);
        }
    }

    @Override
    public String getName() {
        return "Changing object to " + newObject.getName();
    }
}
