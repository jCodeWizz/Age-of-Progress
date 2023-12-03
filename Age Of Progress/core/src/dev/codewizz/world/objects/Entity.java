package dev.codewizz.world.objects;

import com.badlogic.gdx.Gdx;

import dev.codewizz.utils.serialization.RCField;
import dev.codewizz.utils.serialization.RCObject;
import dev.codewizz.world.GameObject;

public abstract class Entity extends GameObject {

	protected float maxHealth = 10f, health = maxHealth, damageCoolDown = 0.0f;
	
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
	public void load(RCObject object) {
		this.health = object.findField("health").getFloat();
		this.maxHealth = object.findField("maxHealth").getFloat();
		this.damageCoolDown = object.findField("damageCoolDown").getFloat();
		
		super.load(object);
	}
	
	@Override
	public RCObject save(RCObject object) {
		object.addField(RCField.Float("health", health));
		object.addField(RCField.Float("maxHealth", maxHealth));
		object.addField(RCField.Float("damageCoolDown", damageCoolDown));
		
		return super.save(object);
	}
	
	
	@Override
	public void onDestroy() {
		cell.setObject(null);
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
