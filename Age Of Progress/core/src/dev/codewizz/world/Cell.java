package dev.codewizz.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import dev.codewizz.gfx.Renderer;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Direction;
import dev.codewizz.world.pathfinding.CellGraph;
import dev.codewizz.world.pathfinding.Link;
import dev.codewizz.world.tiles.BaseTile;

public class Cell {

	public Tile tile;
	public float x, y;
	public int indexX, indexY;
	public boolean odd = false;
	public World world;
	public int index;
	public GameObject object; 
	
	public Cell(float x, float y, int indexX, int indexY, boolean odd) {
		this.x = x;
		this.y = y;
		this.indexX = indexX;
		this.indexY = indexY;
		this.tile = new BaseTile(this);
		this.odd = odd;
	}
	
	public void init(CellGraph graph, World world) {
		this.world = world;
		
		Cell[] neighBours = getCrossedNeighbours();
		for(int i = 0; i < neighBours.length; i++) {
			if(neighBours[i] != null) {
				graph.connectCells(this, neighBours[i], tile.cost);
			}
		}
	}

	public void setTile(Tile tile) {
		if(this.tile.type != tile.type) {
			this.tile.onDestroy();
			this.tile = tile;
			this.tile.cell = this;
			this.tile.place();
		} else {
		}
	}

	public void render(SpriteBatch b) {
		b.draw(tile.getCurrentSprite(), x, y);
	}
	
	public Cell getNeighbourCrossed(Direction dir) {
		if (dir == Direction.West) {
			if(indexX > 0) {
				return world.grid[indexX-1][indexY];
			}
		}
		
		if (dir == Direction.East) {
			if(indexX < World.WORLD_SIZE_W-1) {
				return world.grid[indexX+1][indexY];
			}
		}
		if (dir == Direction.North) {
			if(indexY > 1) {
				return world.grid[indexX][indexY-2];
			}
		}

		if (dir == Direction.South) {
			if(indexY < World.WORLD_SIZE_H-2) {
				return world.grid[indexX][indexY+2];
			}
		}
		
		if (odd) {
			if (dir == Direction.NorthWest) {
				if (indexY > 0) {
					return (world.grid[indexX][indexY - 1]);
				}
			} else if (dir == Direction.NorthEast) {
				if (indexY > 0 && indexX < World.WORLD_SIZE_W - 1) {
					return (world.grid[indexX + 1][indexY - 1]);
				}
			} else if (dir == Direction.SouthWest) {
				if (indexY < World.WORLD_SIZE_H - 1) {
					return (world.grid[indexX][indexY + 1]);
				}
			} else if (dir == Direction.SouthEast) {
				if (indexY < World.WORLD_SIZE_H - 1 && indexX < World.WORLD_SIZE_W - 1) {
					return (world.grid[indexX + 1][indexY + 1]);
				}
			}
		} else {
			if (dir == Direction.NorthWest) {
				if (indexY > 0 && indexX > 0) {
					return (world.grid[indexX - 1][indexY - 1]);
				}
			} else if (dir == Direction.NorthEast) {
				if (indexY > 0) {
					return (world.grid[indexX][indexY - 1]);
				}
			} else if (dir == Direction.SouthWest) {
				if (indexY < World.WORLD_SIZE_H && indexX > 0) {
					return (world.grid[indexX - 1][indexY + 1]);
				}
			} else if (dir == Direction.SouthEast) {
				if (indexY < World.WORLD_SIZE_H) {
					return (world.grid[indexX][indexY + 1]);
				}
			}
		}
		
		return null;
	}
	
	public Cell getNeighbour(Direction dir, Direction dir2) {

		if (odd) {
			if (dir == Direction.North && dir2 == Direction.West) {
				if (indexY > 0) {
					return (world.grid[indexX][indexY - 1]);
				}
			} else if (dir == Direction.North && dir2 == Direction.East) {
				if (indexY > 0 && indexX < World.WORLD_SIZE_W - 1) {
					return (world.grid[indexX + 1][indexY - 1]);
				}
			} else if (dir == Direction.South && dir2 == Direction.West) {
				if (indexY < World.WORLD_SIZE_H - 1) {
					return (world.grid[indexX][indexY + 1]);
				}
			} else if (dir == Direction.South && dir2 == Direction.East) {
				if (indexY < World.WORLD_SIZE_H - 1 && indexX < World.WORLD_SIZE_W - 1) {
					return (world.grid[indexX + 1][indexY + 1]);
				}
			}
		} else {
			if (dir == Direction.North && dir2 == Direction.West) {
				if (indexY > 0 && indexX > 0) {
					return (world.grid[indexX - 1][indexY - 1]);
				}
			} else if (dir == Direction.North && dir2 == Direction.East) {
				if (indexY > 0) {
					return (world.grid[indexX][indexY - 1]);
				}
			} else if (dir == Direction.South && dir2 == Direction.West) {
				if (indexY < World.WORLD_SIZE_H && indexX > 0) {
					return (world.grid[indexX - 1][indexY + 1]);
				}
			} else if (dir == Direction.South && dir2 == Direction.East) {
				if (indexY < World.WORLD_SIZE_H) {
					return (world.grid[indexX][indexY + 1]);
				}
			}
		}

		return null;
	}
	
	public Cell[] getCrossedNeighbours() {
		Cell[] data = new Cell[] { null, null, null, null , null, null, null, null };
		
		data[0] = getNeighbour(Direction.South, Direction.West);
		data[1] = getNeighbour(Direction.South, Direction.East);
		data[2] = getNeighbour(Direction.North, Direction.East);
		data[3] = getNeighbour(Direction.North, Direction.West);
		if(indexX > 0) {
			data[4] = world.grid[indexX-1][indexY];
		}
		if(indexX < World.WORLD_SIZE_W-1) {
			data[5] = world.grid[indexX+1][indexY];
		}
		if(indexY > 1) {
			data[6] = world.grid[indexX][indexY-2];
		}
		if(indexY < World.WORLD_SIZE_H-2) {
			data[7] = world.grid[indexX][indexY+2];
		}
		
		return data;
	}
	
	public List<Cell> getCellsInRadius(int r) {
		ArrayList<Cell> cells = new ArrayList<>();
		
		Cell[][] grid = Main.inst.world.grid;
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				Cell cell = grid[i][j];
				if(Vector2.dst(cell.x, cell.y, x, y) < r * 32) {
					cells.add(cell);
				}
			}
		}
		
		return cells;
	}
	
	public List<Cell> getCellsInSquare(int r) {
		ArrayList<Cell> cells = new ArrayList<>();
		
		Cell[][] grid = Main.inst.world.grid;
		
		for(int i = -r; i < r; i++) {
			for(int j = -r; j < r; j++) {
				int iX = i + indexX;
				int jY = j + indexY;
				
				if(iX >= 0 && iX < World.WORLD_SIZE_W && jY >= 0 && jY < World.WORLD_SIZE_H) {
					cells.add(grid[iX][jY]);
				}
			}
		}
		
		return cells;
	}

	public Cell[] getNeighbours() {
		Cell[] data = new Cell[] { null, null, null, null };

		data[0] = getNeighbour(Direction.South, Direction.West);
		data[1] = getNeighbour(Direction.South, Direction.East);
		data[2] = getNeighbour(Direction.North, Direction.East);
		data[3] = getNeighbour(Direction.North, Direction.West);

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
		System.out.println(" - ODD: " + cell.odd + " INDEX: " + cell.index);
		
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
			Main.inst.world.objects.add(object);
		}
		
		this.object = object;
	}
	
	public GameObject getObject() {
		return object;
	}
}
