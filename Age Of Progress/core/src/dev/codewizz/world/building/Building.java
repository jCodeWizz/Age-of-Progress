package dev.codewizz.world.building;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.world.Cell;
import dev.codewizz.world.objects.IGatherable;
import dev.codewizz.world.objects.tasks.GatherTask;

public class Building {

	private List<Room> rooms = new CopyOnWriteArrayList<>();
	
	public void addRoom(Room room) {
		rooms.add(room);
		
		Main.inst.world.addObject(new Pole(room.getCorners()[0].x + 32, room.getCorners()[0].y + 16, room.getCorners()[0], Direction.SouthEast));
		Main.inst.world.addObject(new Pole(room.getCorners()[1].x, room.getCorners()[1].y + 32, room.getCorners()[1], Direction.SouthWest));
		Main.inst.world.addObject(new Pole(room.getCorners()[2].x + 32, room.getCorners()[2].y + 48, room.getCorners()[2], Direction.NorthWest));
		Main.inst.world.addObject(new Pole(room.getCorners()[3].x + 64, room.getCorners()[3].y + 32, room.getCorners()[3], Direction.NorthEast));
		
		for(Cell cell : room.getArea()) {
			if(cell.getObject() != null && cell.getObject() instanceof IGatherable) {
				Main.inst.world.settlement.addTask(new GatherTask(cell.getObject()), true);
			}
			
			cell.tile.setCurrentSprite(Assets.getSprite("tiled-tile-2"));
		}
		
		Cell top = room.getCorners()[2];
		Cell bottom = room.getCorners()[0];
		Cell left = room.getCorners()[1];
		Cell right = room.getCorners()[3];
		
		for(int i = left.getWorldIndexX(); i <= bottom.getWorldIndexX(); i++) {
			Cell cell = Main.inst.world.getCellWorldIndex(i, left.getWorldIndexY());
		
			Main.inst.world.addObject(new Wall(cell.x, cell.y + 16, cell, Direction.South).flip());
		}
		
		for(int i = top.getWorldIndexX(); i <= right.getWorldIndexX(); i++) {
			Cell cell = Main.inst.world.getCellWorldIndex(i, top.getWorldIndexY());
		
			Main.inst.world.addObject(new Wall(cell.x + 32, cell.y + 32, cell, Direction.North).flip());
		}
		
		for(int i = top.getWorldIndexY(); i <= left.getWorldIndexY(); i++) {
			Cell cell = Main.inst.world.getCellWorldIndex(top.getWorldIndexX(), i);

			Main.inst.world.addObject(new Wall(cell.x, cell.y + 32, cell, Direction.West));
		}
		
		for(int i = right.getWorldIndexY(); i <= bottom.getWorldIndexY(); i++) {
			Cell cell = Main.inst.world.getCellWorldIndex(right.getWorldIndexX(), i);

			Main.inst.world.addObject(new Wall(cell.x + 32, cell.y + 16, cell, Direction.East));
		}
	}
	
	public List<Room> getRooms() {
		return rooms;
	}
}
