package dev.codewizz.gfx.gui.menus;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.gfx.gui.UIText;
import dev.codewizz.main.Main;
import dev.codewizz.world.Nature;

public class DebugMenu extends UIMenu {

	UIText nature;
	UIText settlement;
	UIText rendering;
	UIText time;
	
	public DebugMenu(String id, int x, int y, int w, int h, UILayer layer) {
		super(id, x, y, w, h, layer);

	}

	@Override
	public void setup() {
		
		elements.add(new UIText("debug-text", 20, UILayer.HEIGHT - 20, "DEBUG MODE", 10));
		
		rendering = new UIText("debug-text-rendering", 20, UILayer.HEIGHT - 60, "", 8);
		elements.add(rendering);
		
		nature = new UIText("debug-text-nature", 20, UILayer.HEIGHT - 85, "", 8);
		elements.add(nature);
		
		settlement = new UIText("debug-text-settlement", 20, UILayer.HEIGHT - 110, "", 8);
		elements.add(settlement);
		
		time = new UIText("debug-text-time", 20, UILayer.HEIGHT - 135, "", 8);
		elements.add(time);
	}
	
	public void updateData() {
		
		rendering.setText("Rendering>> FPS: " + Gdx.graphics.getFramesPerSecond());
		
		nature.setText("Nature>> Timer: " + (int)Main.inst.world.nature.spawnCounter + " | Cap: " + Main.inst.world.nature.animals.size() + " / " + Nature.ANIMAL_CAP);
		
		if(Main.inst.world.settlement != null) {
			settlement.setText("Settlement>> Loc: { " + (int)Main.inst.world.settlement.getX() + " ; " + (int)Main.inst.world.settlement.getY() + " } | Size: " + Main.inst.world.settlement.members.size());
		} else {
			settlement.setText("Settlement>> No Settlement");
		}
		
		Nature n = Main.inst.world.nature;
		
		float l = ((int)(n.light*100))/100f;
		
		if(n.transition) {
			time.setText("Time>> Day: " + n.day + " | " + (int)n.timeCounter + " / " + Nature.TRANSITION_TIME + " | Light: " + l);
		} else {
			time.setText("Time>> Day: " + n.day + " | " + (int)n.timeCounter + " / " + Nature.DAY_TIME + " | Light: " + l);
		}
	}
	
	@Override
	public void render(SpriteBatch b) {
		updateData();
	}
	
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(0, 0, 0, 0);
	}

	@Override
	public void onOpen() {
		
	}

	@Override
	public void onClose() {
		this.enable();
	}
	

}
