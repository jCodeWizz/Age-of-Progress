package dev.codewizz.gfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Utils;

public class Particle {

	public static final Sprite DEFAULT = Assets.getSprite("particle-default");
	public static final Sprite LEAF = Assets.getSprite("particle-leave");
	public static final Sprite PINE_LEAF1 = Assets.getSprite("particle-pine-leaf-1");
	public static final Sprite PINE_LEAF2 = Assets.getSprite("particle-pine-leaf-2");

	private float gravity = 1f;
	private float x, y, w, h, velX, velY, traveled, distance = 0f;
	private float counter = 3f;
	
	private Color color;
	private Sprite sprite;
	
	public Particle(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.color = Color.WHITE;
		this.sprite = DEFAULT;
		counter = 3f + Utils.RANDOM.nextFloat();
	}

	public Particle(float x, float y) {
		this(x, y, -1, -1);
	}
	 
	public void update(float dt) {
		
		if(distance != 0) {
			if(traveled < distance) {
				
				velY += gravity * dt;
				traveled += Math.abs(velY);
				
			} else {
				velY = 0;
			}
		}
		
		x+= velX;
		y+= velY;
		
		counter -= dt;
		if(counter <= 0f) {
			destroy();
		}
	}
	
	private void destroy() {
		Main.inst.world.particles.remove(this);
	}
	
	public void render(SpriteBatch b) {
		
		sprite.setColor(color);		
		sprite.setBounds(x, y, w, h);
		sprite.draw(b);
	}
	
	public Particle sprite(Sprite sprite) {
		if(this.w == -1) w = sprite.getWidth();
		if(this.h == -1) h = sprite.getHeight();

		this.sprite = sprite;
		return this;
	}
	
	public Particle color(Color color) {
		
		float r = Utils.clamp(color.r + (float)Utils.getRandom(-50 , 50)/1000f, 0f, 1f);
		float g = Utils.clamp(color.g + (float)Utils.getRandom(-50, 50)/1000f, 0f, 1f);
		float b = Utils.clamp(color.b + (float)Utils.getRandom(-50, 50)/1000f, 0f, 1f);

		this.color = new Color(r, g, b, 1f);
		
		return this;
	}
	
	public Particle velocity(float velX, float velY) {
		this.velX = velX == 0 ? 0 : Utils.getRandom(-velX, velX);
		this.velY = velY;
		return this;
	}
	
	public Particle gravity(float gravity, float distance) {
		this.gravity = gravity;
		this.distance = distance;
		return this;
	}
	
	public Particle counter(float a, float b) {
		this.counter = Utils.getRandom(a-1f, b);
		return this;
	}
}
