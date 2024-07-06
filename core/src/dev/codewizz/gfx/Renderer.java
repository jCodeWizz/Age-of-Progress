package dev.codewizz.gfx;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.layers.MainMenuLayer;
import dev.codewizz.input.MouseInput;
import dev.codewizz.main.Main;
import dev.codewizz.world.Nature;
import dev.codewizz.world.World;

public class Renderer {

	private static ShapeRenderer debugRenderer = new ShapeRenderer();
	public static ShapeRenderer shapeRenderer = new ShapeRenderer();
	
	public SpriteBatch tileBatch;
	public SpriteBatch objectBatch;
	public SpriteBatch uiBatch;

	public UILayer ui;

	private com.badlogic.gdx.physics.box2d.World world = new com.badlogic.gdx.physics.box2d.World(new Vector2(0,0),false);
	private RayHandler rayHandler = new RayHandler(world);
	public List<PointLight> lights = new CopyOnWriteArrayList<>();
	
	public Renderer() {
		Shaders.init();
		tileBatch = new SpriteBatch();
		objectBatch = new SpriteBatch();
		uiBatch = new SpriteBatch();

		ui = new MainMenuLayer();
		
		rayHandler.setAmbientLight(0.2f);
	}

	public void render(World world, OrthographicCamera cam) {   
		tileBatch.begin();
		world.renderTiles(tileBatch);
		tileBatch.setProjectionMatrix(cam.combined);
		tileBatch.end();
		/*
		 * 
		 * TILE PART DONE CONTINUE TO OBJECT
		 * 
		 */
		objectBatch.begin();
		world.renderObjects(objectBatch);
		
		Main.inst.handler.update(Gdx.graphics.getDeltaTime());
		Main.inst.handler.render(objectBatch);
		objectBatch.setProjectionMatrix(cam.combined);
		objectBatch.end();
		/*
		 * 
		 * OBJECT PART DONE CONTINUE TO UI
		 * 
		 */
		if(!Main.inst.world.nature.day || Main.inst.world.nature.transition) {
			rayHandler.setCombinedMatrix(Main.inst.camera.cam);
			rayHandler.updateAndRender();
		}
	}
	
	public void setAmbientLight(float l) {
		rayHandler.setAmbientLight(l);
	}

	public void renderUI() {
		
		Gdx.gl.glLineWidth(5);
		
		shapeRenderer.setProjectionMatrix(Main.inst.camera.cam.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.ORANGE);
		
		MouseInput.renderArea();
		
		shapeRenderer.end();
		
		Gdx.gl.glLineWidth(5);

		
		uiBatch.begin();
		ui.render(uiBatch);
		uiBatch.end();
	}
	
	public PointLight addLight(float x, float y, float radius) {
		PointLight light = new PointLight(this.rayHandler, 16, new Color(0.8f, 0.8f, 0.5f, 0.75f), radius, x, y);
		light.setStaticLight(true);
		light.setXray(true);
		lights.add(light);
		
		if(Main.inst.world != null) {
			Nature n = Main.inst.world.nature;
			light.setActive(!n.day || n.day && n.transition);
		} else {
			light.setActive(false);
		}
		
		return light;
	}
	
	public void removeLight(PointLight light) {
		lights.remove(light);
		light.remove();
	}

	public void renderDebug(World world, OrthographicCamera cam) {
		Gdx.gl.glLineWidth(1);

		debugRenderer.setProjectionMatrix(Main.inst.camera.cam.combined);
		debugRenderer.begin(ShapeRenderer.ShapeType.Line);
		debugRenderer.setColor(Color.WHITE);

		world.renderDebug();

		debugRenderer.end();
		Gdx.gl.glLineWidth(1);

	}

	public void dispose() {
		tileBatch.dispose();
		objectBatch.dispose();
	}

	public static void drawDebugLine(Vector2 start, Vector2 end) {
		debugRenderer.line(start, end);
	}
}
