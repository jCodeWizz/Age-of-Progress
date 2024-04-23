package dev.codewizz.world.settlement;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;

import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Utils;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Nature;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;

public class Carrot extends Crop {

	private static Sprite stage0 = Assets.getSprite("farm-tile");
	private static Sprite stage1 = Assets.getSprite("carrot-farm-tile");
	private static Sprite stage2 = Assets.getSprite("carrot2-farm-tile");
	private static Sprite stage3 = Assets.getSprite("carrot3-farm-tile");
	
	public Carrot(Cell cell) {
		super(cell);
		this.stages = new CropStage[4];
		
		this.stages[0] = new CropStage(2*(Nature.TRANSITION_TIME + Nature.DAY_TIME), stage0);
		this.stages[1] = new CropStage(2*(Nature.TRANSITION_TIME + Nature.DAY_TIME), stage1);
		this.stages[2] = new CropStage(2*(Nature.TRANSITION_TIME + Nature.DAY_TIME), stage2);
		this.stages[3] = new CropStage(2*(Nature.TRANSITION_TIME + Nature.DAY_TIME), stage3);
		
		this.id = "aop:carrot";
	}

	@Override
	public ArrayList<Item> getItems() {
		ArrayList<Item> items = new ArrayList<>();
		
		for(int i = 0; i < Utils.getRandom(3, 5); i++) {
			items.add(new Item(ItemType.CARROT, 3));
		}
		
		return items;
	}
}
