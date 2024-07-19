package dev.codewizz.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import dev.codewizz.gfx.Shaders;

public class Camera {

	public static final float speed = 550f, zoomSpeed = 0.05f, minZoom = 0.3f, maxZoom = 2f;
	
	public OrthographicCamera cam;
	
	public Camera() {
		cam = new OrthographicCamera(1920, 1080);
	}
	
	public void update(float d) {
		
		/*
		 *  camera movement 
		 */
		
		
		if(Main.PLAYING && !Main.PAUSED && Main.inst.renderer.uiStage.getKeyboardFocus() == null) {
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {
				move(d * -speed, 0);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D)) {
				move(d * speed, 0);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.S)) {
				move(0, d * -speed);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.W)) {
				move(0, d * speed);
			}
		}

		//cam.position.set(x, y, 0);
		cam.update();
	}
	
	public void move(float x, float y) {
		cam.translate((int)x, (int)y);
	}
	
	public void zoom(float amount, float toX, float toY) {
        float oldZ = cam.zoom;
        cam.zoom += amount;
        // If zooming still has effect then we need translate map towards cursor
        if (cam.zoom < minZoom) {
        	cam.zoom = minZoom;
        } else if (cam.zoom > maxZoom) {
        	cam.zoom = maxZoom;
        } else {
        	float a = (toX - (float) (Gdx.graphics.getWidth()/2f)) * (oldZ - cam.zoom);
        	float b = (-toY + (float) (Gdx.graphics.getHeight()/2f)) * (oldZ - cam.zoom);
        	cam.translate((int) a,(int) b, 0);
        }
	}
}
