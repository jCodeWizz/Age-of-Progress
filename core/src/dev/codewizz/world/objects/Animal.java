package dev.codewizz.world.objects;

import com.dongbat.jbump.util.MathUtils;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Utils;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.Cell;
import dev.codewizz.world.World;
import dev.codewizz.world.objects.tasks.MoveTask;
import dev.codewizz.world.pathfinding.CellGraph;

public abstract class Animal extends TaskableObject {

	private Herd herd;
	private boolean inHerd = false, captured = false;
	protected int wanderDistance = 6;
	private float wanderTimer = Utils.getRandom(8, 12);

	
	public Animal() {
		super();
	}
	
	public Animal(float x, float y, Herd herd) {
		super(x, y);
		
		this.herd = herd;
		inHerd = true;
	}
	
	public Animal(float x, float y) {
		super(x, y);
		
		inHerd = false;
	}
	
	@Override
	public void update(float d) {
		super.update(d);

		if(wanderTimer > 0) {
			wanderTimer -= d;
		} else {
			wanderTimer = Utils.getRandom(8, 12);

			if(!agent.moving) {
				if(inHerd) {
					Cell cell = herd.newPath();
					CellGraph c = Main.inst.world.cellGraph;
					int s = c.getConnections(cell).size;
					if(s == 0) {
						return;
					}

					tree.addLast(new MoveTask(cell));
				} else {
					Cell currentCell = Main.inst.world.getCell(x, y);

					if(currentCell == null) return;


					int offX = Utils.getRandom(-wanderDistance, wanderDistance) + currentCell.getWorldIndexX();
					int offY = Utils.getRandom(-wanderDistance, wanderDistance) + currentCell.getWorldIndexY();

					Cell goalCell = Main.inst.world.getCellWorldIndex(offX, offY);
					if(goalCell == null) return;

					CellGraph c = Main.inst.world.cellGraph;
					int s = c.getConnections(goalCell).size;
					if(s == 0) {
						return;
					}

					tree.addLast(new MoveTask(goalCell));
				}
			}
		}
	}
	
	@Override
	public GameObjectData save(GameObjectData object) {
		super.save(object);
		
		object.addInteger(wanderDistance);
		object.addByte(ByteUtils.toByte((byte)0, inHerd, 0));
		object.end();
		
		return object;
	}
	
	@Override
	public boolean load(GameObjectDataLoader loader, GameObjectData object, boolean success) {
		super.load(loader, object, success);
		
		byte[] data = object.take();
		this.wanderDistance = ByteUtils.toInteger(data, 0);
		this.inHerd = ByteUtils.toBoolean(data[4], 0);

		/*
		 * Check to see if Herd.java is ready to load?
		 */

		return success;
	}

	public boolean isCaptured() {
		return captured;
	}

	public void setCaptured(boolean captured) {
		this.captured = captured;
	}

	public int getWanderDistance() {
		return wanderDistance;
	}
	
	public boolean isInHerd() {
		return inHerd;
	}
	
	public Herd getHerd() {
		return herd;
	}
	
	public void setIsInHerd(boolean herd) {
		this.inHerd = herd;
	}
}
