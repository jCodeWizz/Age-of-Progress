package dev.codewizz.world.building;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.objects.IGatherable;
import dev.codewizz.world.objects.tasks.GatherTask;

public class Building {

	private final List<Room> rooms = new CopyOnWriteArrayList<>();
	
	public void addRoom(Room room) {
		rooms.add(room);
		
		ArrayList<BuildingObject> edges = new ArrayList<>(); 
		
		for(Cell cell : new ArrayList<>(room.getArea())) {
			if(cell.object == null) {
				cell.setObject(new BuildingObject(cell.x, cell.y, cell, room));
				cell.tile.setCurrentSprite(Assets.getSprite("tiled-tile-2"));
			} else {
				
				if(cell.object.getId().equals("aop:buildingobject")) {
					BuildingObject o = (BuildingObject) cell.object;
					if(o.isEdge()) {
						edges.add(o);
					}
					room.getArea().remove(cell);
				} else {
					cell.object.destroy();
					cell.setObject(new BuildingObject(cell.x, cell.y, cell, room));
					cell.tile.setCurrentSprite(Assets.getSprite("tiled-tile-2"));
				}
			}
		}
		
		for(BuildingObject o : edges) {
			o.init();
		}
		
		for(Cell cell : room.getArea()) {
			if(cell.getObject() != null && cell.getObject() instanceof IGatherable) {
				Main.inst.world.settlement.addTask(new GatherTask(cell.getObject()), true);
			}
			
			((BuildingObject) cell.getObject()).init();
		}
	}
	
	public List<Room> getRooms() {
		return rooms;
	}
}
