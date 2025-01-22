package dev.codewizz.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UIToggle;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.world.World;
import dev.codewizz.world.building.BuildingObject;
import dev.codewizz.world.building.Wall;
import dev.codewizz.world.building.WallDoor;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.tasks.MoveTask;
import dev.codewizz.world.objects.tasks.Task;
import dev.codewizz.world.settlement.Settlement;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.Deflater;

public class KeyInput implements InputProcessor {

	/*
	 * 
	 * Key Input handling class
	 * 
	 */
	@Override
	public boolean keyDown(int key) {
		
		if(key == Input.Keys.ESCAPE) {
			// if shift is also pressed just force-close the program
			if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
				Main.exit();
			}
			
			if(Main.PLAYING) {
				if(((GameLayer)Main.inst.renderer.uiLayer).menusClosed()) {
					((GameLayer)Main.inst.renderer.uiLayer).openMenu(((GameLayer)Main.inst.renderer.uiLayer).pauseMenu);
				} else {
					((GameLayer)Main.inst.renderer.uiLayer).closeMenus();
				}
			}
			
			
			return true;
		}
		
		if(key == Input.Keys.R) {
			MouseInput.rotate = !MouseInput.rotate;
		}
		
		if(key == Input.Keys.NUM_3) {
			UIToggle t = ((GameLayer)Main.inst.renderer.uiLayer).speed3;
			t.setChecked(true);
			World.gameSpeed = 5;
		}
		if(key == Input.Keys.NUM_2) {
			UIToggle t = ((GameLayer)Main.inst.renderer.uiLayer).speed2;
			t.setChecked(true);
			World.gameSpeed = 3;
		}
		if(key == Input.Keys.NUM_1) {
			UIToggle t = ((GameLayer)Main.inst.renderer.uiLayer).speed1;
			t.setChecked(true);
			World.gameSpeed = 1;
		}
		if(key == Input.Keys.NUM_0) {
			UIToggle t = ((GameLayer)Main.inst.renderer.uiLayer).speed0;
			t.setChecked(true);
			World.gameSpeed = 0;
		}
		if(key == Input.Keys.NUM_9) {
			World.gameSpeed = 40;
		}

		if(key == Input.Keys.F1) {
			GameLayer layer = (GameLayer) Main.inst.renderer.uiLayer;
			layer.openMenu(layer.consoleMenu);
		}

		if(key == Input.Keys.H) {
			MouseInput.area = AreaSelector.harvest();
		}

		if(key == Input.Keys.T) {
			GameLayer layer = (GameLayer) Main.inst.renderer.uiLayer;
			layer.openMenu(layer.tileMenu);
		}

		if(key == Input.Keys.Y) {
			GameLayer layer = (GameLayer) Main.inst.renderer.uiLayer;
			layer.openMenu(layer.objectMenu);
		}

		if(key == Input.Keys.B) {
			GameLayer layer = (GameLayer) Main.inst.renderer.uiLayer;
			layer.openMenu(layer.structureMenu);
		}

		if(key == Input.Keys.F2) {
			Pixmap pixmap = Pixmap.createFromFrameBuffer(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
			ByteBuffer pixels = pixmap.getPixels();

			// This loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
			int size = Gdx.graphics.getBackBufferWidth() * Gdx.graphics.getBackBufferHeight() * 4;
			for (int i = 3; i < size; i += 4) {
				pixels.put(i, (byte) 255);
			}

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");  
			LocalDateTime now = LocalDateTime.now();  
			
			PixmapIO.writePNG(Gdx.files.external(Assets.pathFolderScreenshots + "sc_" + dtf.format(now) + ".png"), pixmap, Deflater.DEFAULT_COMPRESSION, true);
			pixmap.dispose();

			return true;
		}

		if(key == Input.Keys.F3) {
			((GameLayer) Main.inst.renderer.uiLayer).debugMenu.toggle();
		}
		return false;
	}

	@Override
	public boolean keyUp(int key) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	
	/*
	 * 
	 *  MOUSE INPUT NOT IN THIS CLASS
	 * 
	 */

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}
}
