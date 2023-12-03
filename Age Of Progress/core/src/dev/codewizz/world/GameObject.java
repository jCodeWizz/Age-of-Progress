package dev.codewizz.world;

import java.awt.Polygon;
import java.awt.Rectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.gfx.Renderable;
import dev.codewizz.gfx.Renderer;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.gfx.gui.menus.SelectMenu;
import dev.codewizz.main.Main;

public abstract class GameObject extends Renderable implements Serializable {

	
	protected String id;
	
	protected float x, y, sortHeight;
	protected int w, h;
	protected boolean flip = false;
	protected Cell cell;
	
	protected boolean selected = false;
	protected String name = "Object";
	
	public GameObject(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void update(float d);
	public abstract void render(SpriteBatch b);
	public void renderUICard(SelectMenu m) {}
	public void updateUICard() {}
	

	public void renderDebug() {
		Polygon g = this.getHitBox();
		
		for(int i = 0; i < g.npoints; i++) {
			Renderer.drawDebugLine(new Vector2(g.xpoints[i % g.npoints], g.ypoints[i % g.npoints]), new Vector2(g.xpoints[(i+1) % g.npoints], g.ypoints[(i+1) % g.npoints]));
		}
	}
	
	public void onDestroy() {};
	
	public void destroy() {
		onDestroy();
		Main.inst.world.removeObject(this);
		if(cell != null) cell.setObject(null);
	}
	
	public void select() {
		Main.inst.renderer.ui.closeMenus();
		GameLayer.selectedObject = this;
		this.renderUICard((SelectMenu)Main.inst.renderer.ui.getElement("selectMenu"));
		Main.inst.renderer.ui.getElement("selectMenu").enable();
		selected = true;
	}
	
	public void deselect() {
		GameLayer.selectedObject = null;
		Main.inst.renderer.ui.getElement("selectMenu").disable();
		selected = false;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, w, h);
	}
	
	public Polygon getHitBox() {
		return new Polygon( new int[] {(int)x + w/2, (int)x, (int)x + w/2, (int)x + w}, new int[] {(int)y, (int)y + h/2, (int)y + h, (int)y + h/2}, 4) ;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	
	public String getId() {
		return id;
	}
	
	public int compareTo(GameObject other) {
		if(other.y + other.sortHeight < this.y + this.sortHeight) {
			return -1;
		} else if(other.y + other.sortHeight > this.y + this.sortHeight){
			return 1;
		} else {
			return 0;
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public float getSorthingHeight() {
		return sortHeight;
	}

	public boolean isFlip() {
		return flip;
	}

	public void setFlip(boolean flip) {
		this.flip = flip;
	}
	
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	
	public Cell getCell() {
		return this.cell;
	}
	
	
}
