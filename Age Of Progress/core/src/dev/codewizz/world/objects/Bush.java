package dev.codewizz.world.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Utils;
import dev.codewizz.utils.serialization.RCObject;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Nature;
import dev.codewizz.world.Serializable;

public class Bush extends GameObject implements Serializable, IGatherable {

	private static Sprite texture = Assets.getSprite("bush");
	private static Sprite texture2 = Assets.getSprite("bush-berries");
	
	private boolean grown = true;
	private float counter = Utils.getRandom(Nature.DAY_TIME, Nature.DAY_TIME + Nature.DAY_TIME/2);
	
	public Bush(float x, float y) {
		super(x, y);

		this.w = 16;
		this.h = 16;

		this.sortHeight = 25f;

		this.id = ID.Bush;
		this.name = "Berry Bush";
	}

	@Override
	public void update(float d) {
		
		if(Main.inst.world.nature.day) {
			
			if(!grown) {
				counter -= d;
				if(counter <= 0) {
					counter = Utils.getRandom(Nature.DAY_TIME, Nature.DAY_TIME + Nature.DAY_TIME/2);
					grown = true;
				}
				
			}
		}
	}

	@Override
	public void render(SpriteBatch b) {
		if(grown) {
			b.draw(texture2, x - 32, y + 25);
		} else {
			b.draw(texture, x - 32, y + 25);
		}
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
	public int duration() {
		return 10;
	}

	@Override
	public void gather() {
		grown = false;
	}
	
	@Override
	public boolean ready() {
		return grown;
	}
}
