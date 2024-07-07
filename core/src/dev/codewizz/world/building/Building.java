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

	private String name;
	private String style;

	public Building() {
		name = "Build " + (Main.inst.world.settlement.buildings.size() + 1);
		style = "default";
	}

	public void addRoom(Room room) {
		rooms.add(room);
		
		for(Cell cell : new ArrayList<>(room.getArea())) {
			if(cell.object == null) {
				cell.setObject(new BuildingObject(cell.x, cell.y, cell, room));
				cell.tile.setCurrentSprite(Assets.getSprite("tiled-tile-2"));
			}
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

	public String getName() {
		return name;
	}

	public String getStyle() {
		return style;
	}
}
