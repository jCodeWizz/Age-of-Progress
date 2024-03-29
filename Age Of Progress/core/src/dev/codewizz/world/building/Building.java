package dev.codewizz.world.building;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.Cell;
import dev.codewizz.world.objects.IGatherable;
import dev.codewizz.world.objects.tasks.GatherTask;

public class Building {

	private List<Room> rooms = new CopyOnWriteArrayList<>();
	
	public void addRoom(Room room) {
		rooms.add(room);
		
		for(Cell cell : room.getArea()) {
			cell.setObject(new BuildingObject(cell.x, cell.y, cell, room));
			cell.tile.setCurrentSprite(Assets.getSprite("tiled-tile-2"));
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
