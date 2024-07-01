package dev.codewizz.world.building;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.main.Main;
import dev.codewizz.modding.events.Reason;
import dev.codewizz.utils.Direction;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.tiles.DirtTile;

public class BuildingObject extends GameObject {
	
	private boolean edge = false;
	private Wall[] walls = new Wall[4];
	
	public BuildingObject(float x, float y, Cell cell, Room room) {
		super(x, y);
		
		this.id = "aop:buildingobject";
		this.w = 32;
		this.h = 32;
		
		this.sortHeight = 16;
	}
	
	public void init() {
		edge = false;
		
		Cell[] n = this.cell.getNeighbours();
		
		for(int i = 0; i < n.length; i++) {
			if(n[i] != null) {
				if(n[i].getObject() == null || !n[i].getObject().getId().equals("aop:buildingobject")) {
					edge = true;
					
					if(i == 0) {
						setWall(i, new Wall(x + 32, y + 32, this.cell, Direction.North).flip());
					} else if(i == 1) {
						setWall(i, new Wall(x + 32, y + 16, this.cell, Direction.East));
					} else if(i == 2) {
						setWall(i, new Wall(x, y + 16, this.cell, Direction.South).flip());
					} else if(i == 3) {
						setWall(i, new Wall(x, y + 32, this.cell, Direction.West));
					}
				} else {
					if(walls[i] != null) {
						walls[i].destroy();
						walls[i] = null;
					}
				}
			}
		}
	}
	
	public void setWall(int i, GameObject wall) {
		if(walls[i] != null) {
			walls[i].destroy();
		}
		
		if(wall != null) {
			walls[i] = (Wall) wall;
			Main.inst.world.addObject(walls[i], Reason.FORCED);
		}
	}
	
	@Override
	public void update(float d) {
	}

	@Override
	public void render(SpriteBatch b) {
	}
	
	@Override
	public void onDestroy() {
		Cell[] n = this.cell.getNeighbours();
		for(int i = 0; i < n.length; i++) {
			if(n[i] != null && n[i].getObject() != null && n[i].getObject().getId().equals("aop:buildingobject")) {
				((BuildingObject) n[i].getObject()).init();
			}
		}
		
		for(int i = 0; i < walls.length; i++) {
			if(walls[i] != null) walls[i].destroy();
		}
		this.cell.setTile(new DirtTile());
	}
	
	public boolean isEdge() {
		return edge;
	}

	public Wall[] getWalls() {
		return walls;
	}
}
