package dev.codewizz.world.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Utils;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Nature;
import dev.codewizz.utils.serialization.SerializableObject;

public class Bush extends GameObject implements SerializableObject, IGatherable {

	private static Sprite texture = Assets.getSprite("bush");
	private static Sprite texture2 = Assets.getSprite("bush-berries");
	
	private boolean grown = true;
	private float counter = Utils.getRandom(Nature.DAY_TIME, Nature.DAY_TIME + Nature.DAY_TIME/2);
	
	public Bush() {
		super();
		
		this.sortHeight = 25f;
		this.id = "aop:berry-bush";
	}
	
	public Bush(float x, float y) {
		super(x, y);

		this.w = 16;
		this.h = 16;

		this.sortHeight = 25f;

		this.id = "aop:berry-bush";
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
	public GameObjectData save(GameObjectData object) {
		super.save(object);
		
		object.addFloat(counter);
		object.addByte(ByteUtils.toByte((byte)0, grown, 0));
		object.end();
		
		return object;
	}

	@Override
	public boolean load(GameObjectDataLoader loader, GameObjectData object, boolean success) {
		super.load(loader, object, success);
		
		byte[] data = object.take();
		this.counter = ByteUtils.toFloat(data, 0);
		this.grown = ByteUtils.toBoolean(data[4], 0);

		return success;
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
