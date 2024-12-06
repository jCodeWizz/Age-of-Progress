package dev.codewizz.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import dev.codewizz.gfx.Renderable;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Utils;
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
        if (damageCoolDown >= 0f) {
            damageCoolDown -= 1 * Gdx.graphics.getDeltaTime();
        }

        Vector2 separationForce = calculateSeparationForce();
        x += separationForce.x * d;
        y += separationForce.y * d;
    }

    private Vector2 calculateSeparationForce() {
        Vector2 force = new Vector2(0, 0);
        float desiredSeparation = 100; // Desired separation distance

        int count = 0;
        for (Renderable other : Main.inst.world.getObjects()) {
            if (other == this || !(other instanceof TaskableObject)) { continue; }

            Vector2 a = new Vector2(getFootPoint());
            Vector2 b = new Vector2(((TaskableObject)other).getFootPoint());

            float distance = Vector2.dst2(a.x, a.y, b.x, b.y);
            if (distance > 0 && distance < desiredSeparation) {
                Vector2 diff = new Vector2(a.sub(b));
                diff.nor();
                diff.scl(200 / distance);
                force.add(diff);
                count++;
            }
        }

        if (count > 0) {
            force.scl(1.0f / count);
            force.add(Utils.RANDOM.nextFloat(), Utils.RANDOM.nextFloat());
        }
        return force;
    }

    public void damage(float damage) {
        if (damageCoolDown <= 0f) {
            health -= damage;
            damageCoolDown = 0.2f;

            if (health <= 0f) { destroy(); }
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
