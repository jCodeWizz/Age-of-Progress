package dev.codewizz.world.settlement;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Utils;
import dev.codewizz.world.Cell;
import dev.codewizz.world.items.Item;

public abstract class Crop {

    protected CropStage[] stages;
    protected int currentStage = 0;

    protected Cell cell;
    protected float counter = 0f;
    protected boolean tasked = false;

    protected String id;

    public Crop(Cell cell) {
        this.cell = cell;
    }

    public abstract ArrayList<Item> getItems();

    public void onHarvest() { }

    public void grow() {
        currentStage++;
        this.counter = 0f;
        this.cell.tile.setCurrentSprite(getCurrentSprite());
    }

    public void harvest() {

        for (Item i : getItems()) {

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


}
