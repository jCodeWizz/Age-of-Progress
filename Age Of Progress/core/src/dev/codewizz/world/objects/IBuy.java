 package dev.codewizz.world.objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

import dev.codewizz.world.Cell;
import dev.codewizz.world.items.Item;

public interface IBuy {

	public Sprite getMenuSprite();
	public String getMenuName();
	public String getMenuDescription();
	public String getId();
	public boolean conintues();
	public boolean available();
	public void onPlace(Cell cell);
	public List<Item> costs();
	
}
