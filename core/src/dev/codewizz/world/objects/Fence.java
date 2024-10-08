package dev.codewizz.world.objects;

import java.awt.Polygon;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.utils.serialization.SerializableObject;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;
import dev.codewizz.world.settlement.FarmArea;

public class Fence extends GameObject implements SerializableObject, IBuy {

    private static final Sprite texture = Assets.getSprite("fence");
    private static final Sprite texture2 = Assets.getSprite("fence-flipped");

    private final List<Item> costs = new CopyOnWriteArrayList<>();

    public Fence() {
        super();

        this.sortHeight = 25f;
        this.id = "aop:fence";

        this.costs.add(new Item(ItemType.WOOD, 4));
        this.costs.add(new Item(ItemType.PLANKS, 2));
    }


    public Fence(float x, float y) {
        super(x, y);

        this.w = 64;
        this.h = 46;

        this.sortHeight = 25f;

        this.id = "aop:fence";
        this.name = "Fence";

        this.costs.add(new Item(ItemType.WOOD, 4));
        this.costs.add(new Item(ItemType.PLANKS, 2));
    }

    @Override
    public void update(float d) {
    }

    @Override
    public void render(SpriteBatch b) {
        if (flip) {
            b.draw(texture2, x + 2, y + 17);
        } else {
            b.draw(texture, x, y + 19);
        }
    }

    @Override
    public Polygon getHitBox() {
        if (flip) {
            return new Polygon(
                    new int[]{(int) x + 2, (int) x + 2, (int) x + 32, (int) x + 32, (int) x + 36,
                            (int) x + 36, (int) x + 64, (int) x + 64, (int) x + 36, (int) x + 36,
                            (int) x + 32, (int) x + 32},
                    new int[]{(int) y + 25, (int) y + 31, (int) y + 45, (int) y + 55, (int) y + 55,
                            (int) y + 48, (int) y + 61, (int) y + 55, (int) y + 42, (int) y + 31,
                            (int) y + 31, (int) y + 40}, 12);
        } else {
            return new Polygon(
                    new int[]{(int) x + 2, (int) x + 2, (int) x + 30, (int) x + 30, (int) x + 34,
                            (int) x + 34, (int) x + 64, (int) x + 64, (int) x + 34, (int) x + 34,
                            (int) x + 30, (int) x + 30},
                    new int[]{(int) y + 55, (int) y + 61, (int) y + 48, (int) y + 55, (int) y + 55,
                            (int) y + 45, (int) y + 31, (int) y + 25, (int) y + 40, (int) y + 31,
                            (int) y + 31, (int) y + 42}, 12);
        }
    }

    @Override
    public GameObjectData save(GameObjectData object) {
        return super.save(object);
    }

    @Override
    public boolean load(GameObjectDataLoader loader, GameObjectData object, boolean success) {
        return super.load(loader, object, success);
    }

    @Override
    public Sprite getMenuSprite() {
        return texture;
    }

    @Override
    public String getMenuName() {
        return "Fence";
    }

    @Override
    public String getMenuDescription() {
        return "A very primitive wall";
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean continues() {
        return false;
    }

    @Override
    public boolean available() {
        return true;
    }

    @Override
    public void onPlace(Cell cell) {
        FarmArea a = new FarmArea();
        a.checkArea(cell);

        cell.disconnect();
    }

    @Override
    public void onDestroy() {
        cell.reconnect();
    }

    @Override
    public List<Item> costs() {
        return costs;
    }
}
