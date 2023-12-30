package dev.codewizz.gfx.gui.menus;

import dev.codewizz.gfx.gui.UIIcon;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.input.AreaSelector;
import dev.codewizz.input.MouseInput;
import dev.codewizz.input.TileSelector;
import dev.codewizz.main.Main;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.objects.IGatherable;
import dev.codewizz.world.objects.tasks.GatherTask;
import dev.codewizz.world.tiles.EmptyTile;

public class ToolMenu extends UIIconMenu {
	
	public ToolMenu(String id, int x, int y, UILayer layer, UIIcon parent) {
		super(id, x, y, layer, parent);
		
	}
	
	@Override
	public void setup() {
		super.setup();
		
		UIIcon gatherIcon = new UIIcon("gather-icon", x + 3 * UILayer.SCALE, y + currentHeight - 27 * UILayer.SCALE, 22, 24, "tool-icon") {
			@Override
			protected void onDeClick() {
				MouseInput.area = new AreaSelector() {
					@Override
					public void handle(GameObject obj) {
						if(obj instanceof IGatherable) {
							if(((IGatherable) obj).ready()) {
								obj.setSelected(true);
								Main.inst.world.settlement.addTask(new GatherTask(obj), false);
							}
						}
					}
				};
			}
		};
		addIcon(gatherIcon);
		
		UIIcon removeIcon = new UIIcon("remove-icon", x + 3 * UILayer.SCALE, y + currentHeight - 54 * UILayer.SCALE, 22, 24, "close-icon") {
			@Override
			protected void onDeClick() {
				MouseInput.tileArea = new TileSelector() {
					@Override
					public void handle(Cell cell) {
						cell.setTile(new EmptyTile());
					}
				};
			}
		};
		addIcon(removeIcon);
	}
}
