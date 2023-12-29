package dev.codewizz.input;

import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import box2dLight.PointLight;
import dev.codewizz.gfx.Renderable;
import dev.codewizz.gfx.Renderer;
import dev.codewizz.gfx.gui.UIElement;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.main.Camera;
import dev.codewizz.main.Main;
import dev.codewizz.modding.Registers;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Tile;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.IBuy;

public class MouseInput implements InputProcessor {

	public static boolean object = true;

	public static boolean[] dragging = new boolean[5];
	public static Vector3 coords = new Vector3();
	public static Cell hoveringOverCell;
	public static String currentlyDrawingTileId = "aop:base-tile";
	public static GameObject currentlyDrawingObject = null;
	public static AreaSelector area = null;
	public static boolean clear = false;
	public static UIElement lastClickedUIElement;
	public static PointLight light;
	public static boolean rotate = false;

	public MouseInput() {
		light = Main.inst.renderer.addLight(0, 0, 300);
	}

	public void update(float d) {

		coords = Main.inst.camera.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		light.setPosition(coords.x, coords.y);

		if (Main.PLAYING && !Main.PAUSED) {

			/*
			 * 
			 * SELECTING CURRENT TILE AND CHECKING IF IT'S CLEAR OR NOT.
			 * 
			 */

			hoveringOverCell = Main.inst.world.getCell(coords.x, coords.y);
			if(hoveringOverCell != null) {
				clear = true;

				if(object && Main.inst.world.settlement != null && currentlyDrawingObject != null) {

					for(Item c : ((IBuy) currentlyDrawingObject).costs()) {
						if(!Main.inst.world.settlement.inventory.containsItem(c, c.getSize())) {
							clear = false;
						}
					}
				} else {
					Tile tile = hoveringOverCell.tile;

					if (tile.getId().equals(currentlyDrawingTileId) || tile.getId().equals("aop:empty-tile")) {
						clear = false;
					}
				}
			}

			/*
			 * 
			 * WHILE BUTTING IS DOWN, PLACE CURRENTLYDRAWINGTYPE TILE
			 * 
			 */

			if (dragging[0]) {

				if (hoveringOverCell != null) {

					if (object) {
						if (currentlyDrawingObject != null && hoveringOverCell.getObject() == null) {
							if(clear) {
								IBuy object = (IBuy) currentlyDrawingObject;
								
								GameObject toPlace = Registers.createGameObject(object.getId(), hoveringOverCell.x, hoveringOverCell.y);
								
								toPlace.setFlip(rotate);
								hoveringOverCell.setObject(toPlace);
								toPlace.setCell(hoveringOverCell);
								((IBuy) toPlace).onPlace(hoveringOverCell);

								dragging[0] = object.conintues() && object.available();
								lastClickedUIElement.setAvailable(object.available());

								if (!object.available()) {
									currentlyDrawingObject = null;
								}
							}
						}
					} else {
						Tile t;
						try {
							t = Registers.createTile(currentlyDrawingTileId, hoveringOverCell);
							hoveringOverCell.setTile(t);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					if (Main.DEBUG) {
						Cell.printDebugInfo(hoveringOverCell);
					}
				}
			}

			/*
			 * SCROLL BUTTON CAMERA MOVEMENT (DOESNT FULLY WORK, BUT GOOD ENOUGH)
			 */

			if (dragging[2]) {
				Main.inst.camera.move(-Gdx.input.getDeltaX(), 0);
				Main.inst.camera.move(0, Gdx.input.getDeltaY());
			}
		}
	}

	public static void renderArea() {
		if (area != null) {
			if (area.start != null) {
				Renderer.shapeRenderer.line(area.start, new Vector2(area.start.x, coords.y));
				Renderer.shapeRenderer.line(new Vector2(area.start.x, coords.y), new Vector2(coords.x, coords.y));
				Renderer.shapeRenderer.line(new Vector2(coords.x, coords.y), new Vector2(coords.x, area.start.y));
				Renderer.shapeRenderer.line(new Vector2(coords.x, area.start.y), area.start);
			}
		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		dragging[button] = true;

		/*
		 * 
		 * Object selector
		 * 
		 */

		if (Main.inst.world != null) {

			Collections.sort(Main.inst.world.getObjects());
			Collections.reverse(Main.inst.world.getObjects());

			if (area != null) {
				area.start(new Vector2(coords.x, coords.y));
				return false;
			}			
			
			if(button == 0 && Main.inst.renderer.ui.menusClosed()) {
				if (GameLayer.selectedObject != null)
					GameLayer.selectedObject.deselect();
				
				for (Renderable o : Main.inst.world.getObjects()) {
					if (o instanceof GameObject) {
						GameObject obj = (GameObject) o;
						obj.setSelected(false);
						if (obj.getHitBox().contains(coords.x, coords.y) && !obj.isSelected()) {
							obj.select();
							dragging[button] = false;
							break;
						}
					}
				}
			}
		}

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		dragging[button] = false;

		if (area != null) {
			area.end(new Vector2(coords.x, coords.y));
			area = null;
		}

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
		if (Main.PLAYING && !Main.PAUSED) {
			if (Main.inst.camera != null) {
				Main.inst.camera.zoom(Camera.zoomSpeed * amountY, Gdx.input.getX(), Gdx.input.getY());
				return true;
			}
		}
		return false;
	}

	/*
	 * 
	 * KEY INPUTS NOT IN THIS CLASS
	 * 
	 */

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}
}
