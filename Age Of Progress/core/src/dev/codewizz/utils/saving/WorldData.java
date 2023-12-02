package dev.codewizz.utils.saving;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;

import dev.codewizz.gfx.Renderable;
import dev.codewizz.utils.quadtree.QuadTree;
import dev.codewizz.utils.serialization.RCDatabase;
import dev.codewizz.utils.serialization.RCField;
import dev.codewizz.utils.serialization.RCObject;
import dev.codewizz.utils.serialization.RCString;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Serializable;
import dev.codewizz.world.World;
import dev.codewizz.world.pathfinding.CellGraph;
import dev.codewizz.world.settlement.Settlement;

public class WorldData {

	public Cell[][] grid;
	public QuadTree<Cell> tree;
	public CellGraph cellGraph;
	public Settlement settlement;
	public List<Renderable> objects;
	public boolean showStartInfo;
	public String[] tiles;

	public static WorldData load(RCDatabase db) {
		
		WorldData data = new WorldData();

		RCObject generalData = db.findObject("generalData");
		data.showStartInfo = generalData.findField("showStartInfo").getBoolean();

		RCObject settlementData = db.findObject("settlementData");
		RCObject tiles = db.findObject("tiles");
		int size = World.WORLD_SIZE_H * World.WORLD_SIZE_W;
		String[] tileIds = new String[size];

		for (int i = 0; i < tileIds.length; i++) {
			tileIds[i] = tiles.findString("" + i).getString();
		}

		data.grid = new Cell[World.WORLD_SIZE_W][World.WORLD_SIZE_H];
		data.tree = new QuadTree<Cell>(-World.WORLD_SIZE_WP / 2, -World.WORLD_SIZE_HP / 2, World.WORLD_SIZE_WP / 2, World.WORLD_SIZE_HP / 2);
		data.cellGraph = new CellGraph();
		data.tiles = tileIds;
		data.objects = new CopyOnWriteArrayList<Renderable>();

		for (int i = 0; i < World.WORLD_SIZE_W; i++) {
			for (int j = 0; j < World.WORLD_SIZE_H; j++) {
				if (j % 2 == 0) {
					Cell cell = new Cell((i - (World.WORLD_SIZE_W / 2)) * 64, (j - (World.WORLD_SIZE_H / 2)) * 16, i, j, false);
					data.tree.set(cell.x, cell.y, cell);
					data.grid[i][j] = cell;
					data.cellGraph.addCell(cell);
				} else {
					Cell cell = new Cell((i - (World.WORLD_SIZE_W / 2)) * 64 + 32, (j - (World.WORLD_SIZE_H / 2)) * 16, i, j, true);
					data.tree.set(cell.x, cell.y, cell);
					data.grid[i][j] = cell;
					data.cellGraph.addCell(cell);
				}
			}
		}
		
		float sx = settlementData.findField("x").getFloat();
		float sy = settlementData.findField("y").getFloat();

		loop:
		for (int i = 0; i < data.grid.length; i++) {
			for (int j = 0; j < data.grid[i].length; j++) {
				if (data.grid[i][j].tile.getHitbox().contains(sx, sy)) {
					data.settlement = new Settlement(data.grid[i][j]);
					break loop;
				}
			}
		}
		
		return data;
	}

	public static void save(World world, String path) {
		File file = Gdx.files.internal(path).file();
		RCDatabase db = new RCDatabase("worldname");

		RCObject generalData = new RCObject("generalData");
		generalData.addField(RCField.Boolean("showStartInfo", world.showInfoSartMenu));
		db.addObject(generalData);

		RCObject settlementData = new RCObject("settlementData");
		settlementData.addField(RCField.Float("x", world.settlement.getX()));
		settlementData.addField(RCField.Float("y", world.settlement.getY()));
		db.addObject(settlementData);

		RCObject tiles = new RCObject("tiles");
		for (int i = 0; i < World.WORLD_SIZE_W; i++) {
			for (int j = 0; j < World.WORLD_SIZE_H; j++) {
				tiles.addString(RCString.Create("" + (int) (world.grid[i][j].indexX + (world.grid[i][j].indexY * World.WORLD_SIZE_W)), world.grid[i][j].tile.getId()));
			}
		}
		
		db.addObject(tiles);

		for (Renderable object : world.getObjects()) {
			if (object instanceof Serializable) {
				GameObject obj = (GameObject) object;
				RCObject ro = new RCObject("$" + obj.getId());

				ro.addField(RCField.Float("x", obj.getX()));
				ro.addField(RCField.Float("y", obj.getY()));
				
				ro = ((Serializable) obj).save(ro);

				db.addObject(ro);
			}
		}

		db.serializeToFile(file);
	}
}
