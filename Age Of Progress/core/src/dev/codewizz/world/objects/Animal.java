package dev.codewizz.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.jbump.util.MathUtils;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Utils;
import dev.codewizz.world.Cell;
import dev.codewizz.world.World;
import dev.codewizz.world.objects.tasks.MoveTask;
import dev.codewizz.world.pathfinding.CellGraph;

public abstract class Animal extends TaskableObject {

	private Herd herd;
	private boolean inHerd = false;
	protected int wanderDistance = 6;
	
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
		
		if(!agent.moving) {
			if(Utils.RANDOM.nextInt(1000) == 0) {
				if(inHerd) {
					if(herd.getLeader().equals(this)) {
						Cell currentCell = Main.inst.world.getCell(x, y);
						
						if(currentCell == null) return;
						
						int offX = Utils.getRandom(-wanderDistance, wanderDistance) + currentCell.indexX;
						int offY = Utils.getRandom(-wanderDistance, wanderDistance) + currentCell.indexY;
						
						offX = MathUtils.clamp(offX, 0, World.WORLD_SIZE_W-1);
						offY = MathUtils.clamp(offY, 0, World.WORLD_SIZE_H-1);
						
						Cell goalCell = Main.inst.world.grid[offX][offY];
						CellGraph c = Main.inst.world.cellGraph;
						int s = c.getConnections(goalCell).size;
						if(s == 0) {
							return;
						}
					
						tree.addLast(new MoveTask(goalCell));
						herd.leaderMoved();
					} else {
						Cell cell = herd.newPath();
						
						CellGraph c = Main.inst.world.cellGraph;
						int s = c.getConnections(cell).size;
						if(s == 0) {
							return;
						}
						
						tree.addLast(new MoveTask(cell));
					}
				} else {
					Cell currentCell = Main.inst.world.getCell(x, y);
					
					if(currentCell == null) return;
					
					int offX = Utils.getRandom(-wanderDistance, wanderDistance) + currentCell.indexX;
					int offY = Utils.getRandom(-wanderDistance, wanderDistance) + currentCell.indexY;
					
					offX = MathUtils.clamp(offX, 0, World.WORLD_SIZE_W-1);
					offY = MathUtils.clamp(offY, 0, World.WORLD_SIZE_H-1);
					
					Cell goalCell = Main.inst.world.grid[offX][offY];
					
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
	
	public abstract void render(SpriteBatch b);

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
