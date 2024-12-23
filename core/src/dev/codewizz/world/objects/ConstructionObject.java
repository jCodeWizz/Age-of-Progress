package dev.codewizz.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.codewizz.main.Main;
import dev.codewizz.modding.events.Reason;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.building.Wall;
import dev.codewizz.world.items.Inventory;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.tasks.BuildObjectTask;
import java.util.UUID;

public class ConstructionObject extends GameObject {

    public static boolean FREE = false;

    private GameObject toPlace;
    private boolean placed;
    private boolean inBuilding = false;

    public Inventory costs;

    public ConstructionObject(float x, float y, GameObject toPlace) {
        super(x, y);

        this.id = "aop:constructionobject";

        this.toPlace = toPlace;
        this.costs = new Inventory(-1);

        if (!FREE) {
            IBuy b = (IBuy) toPlace;
            for (Item i : b.costs()) {
                this.costs.addItem(i);
                Main.inst.world.settlement.addTask(new BuildObjectTask(this, i), true);
            }
        }
    }

    public ConstructionObject(Wall wall) {
        this(wall.getX(), wall.getY(), wall);

        this.cell = wall.getCell();
        inBuilding = true;
    }

    private void placeObject() {
        if(inBuilding) {
            Main.inst.world.addObject(toPlace, Reason.FORCED);
            Main.inst.world.removeObject(this);
        } else {
            toPlace.getCell().setObject(toPlace);
        }
        ((IBuy) toPlace).onPlace(toPlace.getCell());
        placed = true;
    }

    @Override
    public void update(float d) {
        if (costs.isEmpty() && !placed) {
            if(inBuilding) {
                Main.inst.world.removeObject(toPlace);
            } else {
                this.destroy();
            }
            placeObject();
        }
    }

    @Override
    public void render(SpriteBatch b) {
        b.setColor(1f, 1f, 1f, 0.5f);
        toPlace.render(b);
        b.setColor(1f, 1f, 1f, 1f);
    }

    public GameObject getToPlace() {
        return toPlace;
    }

    @Override
    public GameObjectData save(GameObjectData object) {
        super.save(object);

        object.addByte(ByteUtils.toByte((byte) 0, placed, 0));
        object.addArray(ByteUtils.toBytes(toPlace.getUUID()));
        object.end();

        return object;
    }

    @Override
    public boolean load(GameObjectDataLoader loader, GameObjectData object, boolean success) {
        super.load(loader, object, success);

        byte[] data = object.take();

        placed = ByteUtils.toBoolean(data[0], 0);

        UUID toPlaceUUID = ByteUtils.toUUID(data, 1);
        if (loader.isLoaded(toPlaceUUID)) {
            this.toPlace = loader.getLoadedObject(toPlaceUUID);
        } else {
            success = false;
        }

        return success;
    }
}
