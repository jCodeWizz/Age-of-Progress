package dev.codewizz.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import dev.codewizz.gfx.Renderer;
import dev.codewizz.main.Main;
import dev.codewizz.modding.events.Event;
import dev.codewizz.modding.events.SetTileEvent;
import dev.codewizz.utils.Direction;
import dev.codewizz.utils.quadtree.Point;
import dev.codewizz.world.pathfinding.CellGraph;
import dev.codewizz.world.pathfinding.Link;
import dev.codewizz.world.tiles.GrassTile;

public class Cell {

	public Tile tile;
	public float x, y;
	public int indexX, indexY;
	public World world;
	public Chunk chunk;
	public int index;
	public GameObject object; 
	
	public Cell(float x, float y, int indexX, int indexY) {
		this.x = x;
		this.y = y;
		this.indexX = indexX;
		this.indexY = indexY;
		this.tile = new GrassTile();
		this.tile.setCell(this);
	}
	
	public void init(CellGraph graph, World world, Chunk chunk) {
		this.world = world;
		this.chunk = chunk;
		
		Cell[] neighBours = getAllNeighbours();
		for(int i = 0; i < neighBours.length; i++) {
			if(neighBours[i] != null) {
				graph.connectCells(this, neighBours[i], tile.cost);
			}
		}
	}

	public boolean setTile(Tile tile) {
		if(!this.tile.getId().equals(tile.getId())) {
			
			
			boolean proceed = Event.dispatch(new SetTileEvent(this, tile));
			
			if(!proceed)
				return false;
			
			this.tile.onDestroy();
			this.tile.setCell(null);
			this.tile = tile;
			this.tile.setCell(this);
			this.tile.place();
			
			return true;
		} else {
			return true;
		}
	}

	public void render(SpriteBatch b) {
		b.draw(tile.getCurrentSprite(), x, y);
	}
	
	public Cell getCrossedNeighbour(Direction dir) {
		if(dir == Direction.North) {
			if(indexY > 0 && indexX > 0) {
				return chunk.getGrid()[indexX - 1][indexY - 1];
			} else if(indexY > 0 && indexX == 0) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(1, 0).toString());
				
				if(n != null && n.isGenerated()) {
					return n.getGrid()[Chunk.SIZE - 1][indexY - 1];
				}
			} else if(indexY == 0 && indexX > 0) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(0, 1).toString());

				if(n != null && n.isGenerated()) {
					return n.getGrid()[indexX - 1][Chunk.SIZE - 1];
				}
			} else if(indexY == 0 && indexX == 0) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(1, 1).toString());

				if(n != null && n.isGenerated()) {
					return n.getGrid()[Chunk.SIZE - 1][Chunk.SIZE - 1];
				}
			}
		} else if(dir == Direction.East) {
			if(indexX < Chunk.SIZE - 1 && indexY > 0) {
				return chunk.getGrid()[indexX + 1][indexY - 1];
			} else if(indexX < Chunk.SIZE - 1 && indexY == 0) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(0, 1).toString());

				if(n != null && n.isGenerated()) {
					return n.getGrid()[indexX + 1][Chunk.SIZE - 1];
				}
			} else if(indexX == Chunk.SIZE - 1 && indexY > 0) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(1, 0).toString());

				if(n != null && n.isGenerated()) {
					return n.getGrid()[0][indexY - 1];
				}
			} else if(indexX == Chunk.SIZE - 1 && indexY == 0) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(-1, 1).toString());

				if(n != null && n.isGenerated()) {
					return n.getGrid()[0][Chunk.SIZE - 1];
				}
			}
		} else if(dir == Direction.South) {
			if(indexY < Chunk.SIZE - 1 && indexX < Chunk.SIZE - 1) {
				return chunk.getGrid()[indexX + 1][indexY + 1];
			} else if(indexX == Chunk.SIZE - 1 && indexY < Chunk.SIZE - 1) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(1, 0).toString());

				if(n != null && n.isGenerated()) {
					return n.getGrid()[0][indexY + 1];
				}
			} else if(indexX < Chunk.SIZE - 1 && indexY == Chunk.SIZE - 1) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(0, 1).toString());

				if(n != null && n.isGenerated()) {
					return n.getGrid()[indexX + 1][0];
				}
			} else if(indexX == Chunk.SIZE - 1 && indexY == Chunk.SIZE - 1) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(1, 1).toString());

				if(n != null && n.isGenerated()) {
					return n.getGrid()[0][0];
				}
			}
		} else if(dir == Direction.West) {
			if(indexX > 0 && indexY < Chunk.SIZE - 1) {
				return chunk.getGrid()[indexX - 1][indexY + 1];
			} else if(indexX == 0 && indexY < Chunk.SIZE - 1) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(-1, 0).toString());

				if(n != null && n.isGenerated()) {
					return n.getGrid()[Chunk.SIZE - 1][indexY + 1];
				}
			} else if(indexX > 0 && indexY == Chunk.SIZE - 1) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(0, 1).toString());

				if(n != null && n.isGenerated()) {
					return n.getGrid()[indexX - 1][0];
				}
			} else if(indexX == 0 && indexY == Chunk.SIZE - 1) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(-1, 1).toString());

				if(n != null && n.isGenerated()) {
					return n.getGrid()[Chunk.SIZE - 1][0];
				}
			}
		}
		return null;
	}
	
	public Cell getNeighbour(Direction dir) {
		if(dir == Direction.North) {
			if(indexY > 0) {
				return chunk.getGrid()[indexX][indexY - 1];
			}
			Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(0, 1).toString());
			
			if(n != null && n.isGenerated()) {
				return n.getGrid()[indexX][Chunk.SIZE-1];
			}
		} else if(dir == Direction.East) {
			if(indexX < Chunk.SIZE - 1) {
				return chunk.getGrid()[indexX + 1][indexY];
			}
			
			Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(1, 0).toString());
			
			if(n != null && n.isGenerated()) {
				return n.getGrid()[0][indexY];
			}
		} else if(dir == Direction.South) {
			if(indexY < Chunk.SIZE - 1) {
				return chunk.getGrid()[indexX][indexY + 1];
			}
			
			Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(0, 1).toString());
			
			if(n != null && n.isGenerated()) {
				return n.getGrid()[indexX][0];
			}
		} else if(dir == Direction.West) {
			if(indexX > 0) {
				return chunk.getGrid()[indexX - 1][indexY];
			}
			
			Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(1, 0).toString());
			
			if(n != null && n.isGenerated()) {
				return n.getGrid()[Chunk.SIZE-1][indexY];
			}
		}
		return null;
	}
	
	public Cell[] getAllNeighbours() {
		Cell[] data = new Cell[] { null, null, null, null , null, null, null, null };
		
		data[0] = getCrossedNeighbour(Direction.North);
		data[1] = getNeighbour(Direction.North);
		data[2] = getCrossedNeighbour(Direction.East);
		data[3] = getNeighbour(Direction.East);
		data[4] = getCrossedNeighbour(Direction.South);
		data[5] = getNeighbour(Direction.South);
		data[6] = getCrossedNeighbour(Direction.West);
		data[7] = getNeighbour(Direction.West);
		
		return data;
	}
	
	public List<Cell> getCellsInSquare(int r) {
		Point<Cell>[] cells = world.tree.searchIntersect(this.x - r, this.y - r, this.x + r, this.y + r);
		
		ArrayList<Cell> a = new ArrayList<>();
		
		for(int i = 0; i < cells.length; i++) {		
			a.add(cells[i].getValue());
		}
		return a;
	}
	
	public Cell[] getNeighbours() {
		Cell[] data = new Cell[] { null, null, null, null };

		data[0] = getNeighbour(Direction.North);
		data[1] = getNeighbour(Direction.East);
		data[2] = getNeighbour(Direction.South);
		data[3] = getNeighbour(Direction.West);

		return data;
	}
	
	public Cell[] getCrossedNeighbours() {
		Cell[] data = new Cell[] { null, null, null, null };

		data[0] = getCrossedNeighbour(Direction.North);
		data[1] = getCrossedNeighbour(Direction.East);
		data[2] = getCrossedNeighbour(Direction.South);
		data[3] = getCrossedNeighbour(Direction.West);

		return data;
	}
	
	public void drawBorders() {
		Renderer.drawDebugLine(new Vector2(getXPoints()[0], getYPoints()[0]), new Vector2(getXPoints()[1], getYPoints()[1]));
		Renderer.drawDebugLine(new Vector2(getXPoints()[1], getYPoints()[1]), new Vector2(getXPoints()[2], getYPoints()[2]));
		Renderer.drawDebugLine(new Vector2(getXPoints()[2], getYPoints()[2]), new Vector2(getXPoints()[3], getYPoints()[3]));
		Renderer.drawDebugLine(new Vector2(getXPoints()[3], getYPoints()[3]), new Vector2(getXPoints()[0], getYPoints()[0]));
	}
	
	public static void printDebugInfo(Cell cell) {
		System.out.println("CELL: [" + cell.indexX + "][" + cell.indexY + "]");
		System.out.println(" - X: " + cell.x + " Y: " + cell.y);
		System.out.println(" INDEX: " + cell.index);
		
		Array<Link> links = Main.inst.world.cellGraph.getLinks(cell);
		
		for(int i = 0; i < links.size; i++) {
			System.out.println(" - Link[" + i + "] " + links.get(i).getCost());
		}
		
		System.out.println("TILE: [" + cell.tile.getName() + "]");
		System.out.println(" - COST: " + cell.tile.cost);
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public int[] getXPoints() {
		return new int[] { (int)x, (int)x + 32, (int)x + 64, (int)x + 32};
	}
	
	public int[] getYPoints() {
		return new int[] { (int)y + 32, (int)y + 48, (int)y + 32, (int)y + 16};
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x-32, (int)y-24, 64, 48);
	}
	
	public Vector2 getMiddlePoint() {
		return new Vector2(x+32, y+32);
	}
	
	public void setObject(GameObject object) {
		if(object != null) {
			object.setCell(this);
			Main.inst.world.addObject(object);
		}
		
		this.object = object;
	}
	
	public GameObject getObject() {
		return object;
	}
}
