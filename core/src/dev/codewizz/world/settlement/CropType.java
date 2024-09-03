package dev.codewizz.world.settlement;

import dev.codewizz.world.Cell;
import dev.codewizz.world.items.ItemDrop;
import java.util.ArrayList;

public class CropType {

    private final String id;
    private final CropStage[] stages;
    private final ArrayList<ItemDrop> drops;

    public CropType(String id, CropStage[] stages, ArrayList<ItemDrop> drops) {
        this.id = id;
        this.stages = stages;
        this.drops = drops;
    }

    public Crop get(Cell cell) {
        Crop crop = new Crop(cell);

        crop.id = id;
        crop.stages = stages;
        crop.drops = drops;


        return crop;
    }
}
