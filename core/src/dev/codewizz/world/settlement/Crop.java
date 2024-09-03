package dev.codewizz.world.settlement;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Utils;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Nature;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemDrop;
import dev.codewizz.world.items.ItemType;
import java.util.ArrayList;
import java.util.HashMap;

public class Crop {

    public static HashMap<String, CropType> cropTypes = new HashMap<>();

    CropStage[] stages;
    private int currentStage = 0;

    ArrayList<ItemDrop> drops = new ArrayList<>();

    private Cell cell;
    float counter = 0f;
    boolean tasked = false;

    String id;

    public Crop(Cell cell) {
        this.cell = cell;
    }

    public void onHarvest() { }

    public void grow() {
        currentStage++;
        this.counter = 0f;
        this.cell.tile.setCurrentSprite(getCurrentSprite());
    }

    public void harvest() {

        for (ItemDrop d : drops) {
            Item i = d.get();

            i.setX(cell.x + Utils.getRandom(16, 48));
            i.setY(cell.y + Utils.getRandom(20, 40));

            Main.inst.world.addItem(i);
        }

        onHarvest();
        Main.inst.world.settlement.crops.remove(this);
        cell.tile.setCurrentSprite(getStage(0).sprite);
    }

    public boolean isReady() {
        return this.counter >= stages[currentStage].time;
    }

    public boolean onLastStage() {
        return currentStage == stages.length - 1;
    }

    public boolean fullyGrown() {
        return isReady() && onLastStage();
    }

    public Sprite getCurrentSprite() {
        return stages[currentStage].sprite;
    }

    public void task() {
        this.tasked = true;
    }

    public void untask() {
        this.tasked = false;
    }

    public Cell getCell() {
        return cell;
    }

    public CropStage getStage(int index) {
        return stages[index];
    }

    public static void readCropFromJson(String json) {

        JsonValue root = new JsonReader().parse(json);

        String id = root.getString("id");
        int stageCount = root.getInt("stageCount");
        JsonValue stages = root.get("stages");
        int dropCount = root.getInt("resultCount");
        CropStage[] st = new CropStage[stageCount];

        for (int i = 0; i < stageCount; i++) {
            JsonValue stage = stages.get(i);
            CropStage s = new CropStage(
                    stage.getInt("time") * (Nature.DAY_TIME + Nature.TRANSITION_TIME),
                    Assets.getSprite(stage.getString("texture")));
            st[i] = s;
        }

        ArrayList<ItemDrop> d = new ArrayList<>();
        JsonValue drops = root.get("result");
        for (int i = 0; i < dropCount; i++) {
            JsonValue drop = drops.get(i);
            d.add(new ItemDrop(ItemType.types.get(drop.getString("id")), drop.getInt("min"), drop.getInt("max")));
        }

        CropType crop = new CropType(id, st, d);
        cropTypes.put(id, crop);
    }
}
