package dev.codewizz.world.objects;

import com.badlogic.gdx.Gdx;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.GameObject;

public abstract class Entity extends GameObject {

	protected float maxHealth = 10f, health = maxHealth, damageCoolDown = 0.0f;
	
	public Entity() {
		super();
	}
	
	public Entity(float x, float y) {
		super(x, y);
	}
	
	@Override
	public void update(float d) {
		if(damageCoolDown >= 0f) {
			damageCoolDown -= 1 * Gdx.graphics.getDeltaTime();
		}
	}
	
	public void damage(float damage) {
		if(damageCoolDown <= 0f) {
			health -= damage;
			damageCoolDown = 0.2f;
			
			if(health <= 0f)
				destroy();
		}
	}
	
	@Override
	public boolean load(GameObjectDataLoader loader, GameObjectData object, boolean success) {
		super.load(loader, object, success);
		
		byte[] data = object.take();
		
		this.health = ByteUtils.toFloat(data, 0);
		this.maxHealth = ByteUtils.toFloat(data, 4);
		this.damageCoolDown = 0;

		return success;
	}
	
	@Override
	public GameObjectData save(GameObjectData object) {
		super.save(object);
		
		object.addFloat(health);
		object.addFloat(maxHealth);
		object.end();
		
		return object;
	}
	
	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}
}
