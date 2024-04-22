package dev.codewizz.world.objects;

import com.badlogic.gdx.Gdx;

import dev.codewizz.gfx.gui.menus.SelectMenu;
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
	public void load(GameObjectData object) {
		super.load(object);
		
		byte[] data = object.take();
		
		this.health = ByteUtils.toFloat(data, 0);
		this.maxHealth = ByteUtils.toFloat(data, 4);
		this.damageCoolDown = 0;
	}
	
	@Override
	public GameObjectData save(GameObjectData object) {
		super.save(object);
		
		object.addFloat(health);
		object.addFloat(maxHealth);
		object.end();
		
		return object;
	}
	
	@Override
	public boolean loadCheck(GameObjectDataLoader loader, boolean ready) {
		return super.loadCheck(loader, ready);
	}
	
	@Override
	public void renderUICard(SelectMenu m) {

	}
	
	@Override
	public void updateUICard(SelectMenu m) {

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
