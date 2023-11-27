package dev.codewizz.world.objects;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.input.MouseInput;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.serialization.RCObject;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Serializable;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;
import dev.codewizz.world.objects.tasks.MoveTask;
import dev.codewizz.world.settlement.Settlement;

public class Flag extends GameObject implements IBuy, Serializable {

	private static Sprite texture = Assets.getSprite("flag");

	private List<Item> costs = new CopyOnWriteArrayList<>();
	
	public Flag(float x, float y) {
		super(x, y);

		this.id = ID.Flag;
		
		this.sortHeight = 26;
		
		costs.add(new Item(0, 0, ItemType.Wood, 5));
	}

	@Override
	public void update(float d) {
	}

	@Override
	public void render(SpriteBatch b) {
		if(Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
			
			Cell cell = MouseInput.hoveringOverCell;
			if(cell != null) {
				Main.inst.world.settlement.addTask(new MoveTask(cell), false);
			}
		}	

		b.draw(texture, x + 15, y + 26);
	}

	@Override
	public Sprite getMenuSprite() {
		return texture;
	}

	@Override
	public String getMenuName() {
		return "Flag";
	}

	@Override
	public GameObject getCopy(float x, float y) {
		return new Flag(x, y);
	}

	@Override
	public boolean conintues() {
		return false;
	}

	@Override
	public String getMenuDescription() {
		return "The beacon of your Settlement";
	}
	
	@Override
	public void onPlace(Cell cell) {
		Settlement s = new Settlement(cell);
		Main.inst.world.start(s);
		
		Main.inst.renderer.ui.getElement("manage-icon").setAvailable(true);
		Main.inst.renderer.ui.getElement("people-icon").setAvailable(true);
		Main.inst.renderer.ui.getElement("tool-icon").setAvailable(true);
		Main.inst.renderer.ui.getElement("path-icon").setAvailable(true);
		
	}
	
	@Override
	public RCObject save(RCObject object) {
		return object;	
	}

	@Override
	public void load(RCObject object) {
		Main.inst.world.objects.add(this);		
	}

	@Override
	public boolean available() {
		return false;
	}

	@Override
	public List<Item> costs() {
		return costs;
	}
}