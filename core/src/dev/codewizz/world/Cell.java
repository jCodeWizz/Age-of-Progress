package dev.codewizz.world;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dev.codewizz.gfx.Renderer;
import dev.codewizz.gfx.Shaders;
import dev.codewizz.main.Main;
import dev.codewizz.modding.events.Event;
import dev.codewizz.modding.events.Reason;
import dev.codewizz.modding.events.SetTileEvent;
import dev.codewizz.utils.Direction;
import dev.codewizz.utils.quadtree.Point;
import dev.codewizz.world.pathfinding.CellGraph;
import dev.codewizz.world.tiles.GrassTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Cell {

	public Tile tile;
	public float x, y;
	public int indexX, indexY;
	public World world;
	public Chunk chunk;
	public int index;
	public GameObject object;

	public boolean[] acceptConnections = { true, true, true, true, true, true, true, true };
	public boolean[] connectedTo = { false, false, false, false, false, false, false, false };

	public Cell(World world, Chunk chunk, float x, float y, int indexX, int indexY) {
		this.world = world;
		this.chunk = chunk;

		this.x = x;
		this.y = y;
		this.indexX = indexX;
		this.indexY = indexY;
		this.tile = new GrassTile();
		this.tile.setCell(this);

	}

	public void init(CellGraph graph) {
		connectAll();
	}
	
	public void disconnect() {
		CellGraph c = Main.inst.world.cellGraph;
		Cell[] n = getAllNeighbours();
		for(int i = 0; i < n.length; i++) {
			if(n[i] != null) {
				n[i].connectedTo[(i + 4) % 8] = false;
				c.removeConnection(n[i], this);
			}
			
			acceptConnections[i] = false;
		}
	} 
	
	public void reconnect() {
		CellGraph c = Main.inst.world.cellGraph;

		Cell[] neighBours = getAllNeighbours();
		for(int i = 0; i < neighBours.length; i++) {
			if(neighBours[i] != null && !neighBours[i].connectedTo[(i + 4) % 8]) {
				c.connectCells(neighBours[i], this, neighBours[i].tile.getCost());
				neighBours[i].connectedTo[(i + 4) % 8] = true;
			}
			
			acceptConnections[i] = true;
		}
	}

	public void connectAll() {

		if (this.tile.cost == -1) {
			return;
		}

		synchronized (Main.inst.world) {
			CellGraph graph = Main.inst.world.cellGraph;

			Cell[] neighBours = getAllNeighbours();

			for (int i = 0; i < neighBours.length; i++) {
				if (neighBours[i] != null) {
					if (neighBours[i].acceptConnections[(i + 4) % 8] && !connectedTo[i]) {
						graph.connectCells(this, neighBours[i], tile.cost);
						connectedTo[i] = true;
					}

					if (acceptConnections[i] && !neighBours[i].connectedTo[(i + 4) % 8]) {
						graph.connectCells(neighBours[i], this, neighBours[i].tile.cost);
						neighBours[i].connectedTo[(i + 4) % 8] = true;
					}
				}
			}	
		}
	}
	
	public void blockPath(Direction dir) {
		Cell other = getAllNeighbours()[dir.getIndex()];
		
		this.acceptConnections[dir.getIndex()] = false;
		
		if(other != null && other.connectedTo[(dir.getIndex() + 4) % 8]) {
			other.connectedTo[(dir.getIndex() + 4) % 8] = false;
			Main.inst.world.cellGraph.removeConnection(other, this);

			if(this.connectedTo[dir.getIndex()]) {
				this.connectedTo[dir.getIndex()] = false;
				Main.inst.world.cellGraph.removeConnection(this, other);
			}
		}
	}
	
	public void unblockPath(Direction dir) {
		Cell other = getAllNeighbours()[dir.getIndex()];
		
		this.acceptConnections[dir.getIndex()] = true;

		if(other != null && other.acceptConnections[(dir.getIndex() + 4) % 8] && !other.connectedTo[(dir.getIndex() + 4) % 8]) {
			other.connectedTo[dir.other().getIndex()] = true;
			Main.inst.world.cellGraph.connectCells(other, this, other.tile.getCost());

			if(this.acceptConnections[dir.getIndex()] && !this.connectedTo[dir.getIndex()]) {
				this.connectedTo[dir.getIndex()] = true;
				Main.inst.world.cellGraph.connectCells(this, other, this.tile.getCost());
			}
		}
	}

	public boolean setTile(Tile tile) {
		if (!this.tile.getId().equals(tile.getId())) {

			boolean proceed = Event.dispatch(new SetTileEvent(this, tile));

			if (!proceed)
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
		b.setColor(1f, 1f, 1f, tile.getShaderId());
		tile.render(b);
	}

	public Cell getCrossedNeighbour(Direction dir) {
		if (dir == Direction.North) {
			if (indexY > 0 && indexX > 0) {
				return chunk.getGrid()[indexX - 1][indexY - 1];
			} else if (indexY > 0 && indexX == 0) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(1, 0));

				if (n != null && n.isGenerated()) {
					return n.getGrid()[Chunk.SIZE - 1][indexY - 1];
				}
			} else if (indexY == 0 && indexX > 0) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(0, 1));

				if (n != null && n.isGenerated()) {
					return n.getGrid()[indexX - 1][Chunk.SIZE - 1];
				}
			} else if (indexY == 0 && indexX == 0) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(1, 1));

				if (n != null && n.isGenerated()) {
					return n.getGrid()[Chunk.SIZE - 1][Chunk.SIZE - 1];
				}
			}
		} else if (dir == Direction.East) {
			if (indexX < Chunk.SIZE - 1 && indexY > 0) {
				return chunk.getGrid()[indexX + 1][indexY - 1];
			} else if (indexX < Chunk.SIZE - 1 && indexY == 0) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(0, 1));

				if (n != null && n.isGenerated()) {
					return n.getGrid()[indexX + 1][Chunk.SIZE - 1];
				}
			} else if (indexX == Chunk.SIZE - 1 && indexY > 0) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(1, 0));

				if (n != null && n.isGenerated()) {
					return n.getGrid()[0][indexY - 1];
				}
			} else if (indexX == Chunk.SIZE - 1 && indexY == 0) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(-1, 1));

				if (n != null && n.isGenerated()) {
					return n.getGrid()[0][Chunk.SIZE - 1];
				}
			}
		} else if (dir == Direction.South) {
			if (indexY < Chunk.SIZE - 1 && indexX < Chunk.SIZE - 1) {
				return chunk.getGrid()[indexX + 1][indexY + 1];
			} else if (indexX == Chunk.SIZE - 1 && indexY < Chunk.SIZE - 1) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(1, 0));

				if (n != null && n.isGenerated()) {
					return n.getGrid()[0][indexY + 1];
				}
			} else if (indexX < Chunk.SIZE - 1 && indexY == Chunk.SIZE - 1) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(0, 1));

				if (n != null && n.isGenerated()) {
					return n.getGrid()[indexX + 1][0];
				}
			} else if (indexX == Chunk.SIZE - 1 && indexY == Chunk.SIZE - 1) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(1, 1));

				if (n != null && n.isGenerated()) {
					return n.getGrid()[0][0];
				}
			}
		} else if (dir == Direction.West) {
			if (indexX > 0 && indexY < Chunk.SIZE - 1) {
				return chunk.getGrid()[indexX - 1][indexY + 1];
			} else if (indexX == 0 && indexY < Chunk.SIZE - 1) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(-1, 0));

				if (n != null && n.isGenerated()) {
					return n.getGrid()[Chunk.SIZE - 1][indexY + 1];
				}
			} else if (indexX > 0 && indexY == Chunk.SIZE - 1) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(0, 1));

				if (n != null && n.isGenerated()) {
					return n.getGrid()[indexX - 1][0];
				}
			} else if (indexX == 0 && indexY == Chunk.SIZE - 1) {
				Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(-1, 1));

				if (n != null && n.isGenerated()) {
					return n.getGrid()[Chunk.SIZE - 1][0];
				}
			}
		}
		return null;
	}

	public Cell getNeighbour(Direction dir) {
		if (dir == Direction.North) {
			if (indexY > 0) {
				return chunk.getGrid()[indexX][indexY - 1];
			}
			Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(0, 1));

			if (n != null && n.isGenerated()) {
				return n.getGrid()[indexX][Chunk.SIZE - 1];
			}
		} else if (dir == Direction.East) {
			if (indexX < Chunk.SIZE - 1) {
				return chunk.getGrid()[indexX + 1][indexY];
			}

			Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(1, 0));

			if (n != null && n.isGenerated()) {
				return n.getGrid()[0][indexY];
			}
		} else if (dir == Direction.South) {
			if (indexY < Chunk.SIZE - 1) {
				return chunk.getGrid()[indexX][indexY + 1];
			}

			Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).add(0, 1));

			if (n != null && n.isGenerated()) {
				return n.getGrid()[indexX][0];
			}
		} else if (dir == Direction.West) {
			if (indexX > 0) {
				return chunk.getGrid()[indexX - 1][indexY];
			}

			Chunk n = world.chunkTree.get(new Vector2(chunk.getIndex()).sub(1, 0));

			if (n != null && n.isGenerated()) {
				return n.getGrid()[Chunk.SIZE - 1][indexY];
			}
		}
		return null;
	}

	public Cell[] getAllNeighbours() {
		Cell[] data = new Cell[] { null, null, null, null, null, null, null, null };

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

		for (int i = 0; i < cells.length; i++) {
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
		Renderer.drawDebugLine(new Vector2(getXPoints()[0], getYPoints()[0]),
				new Vector2(getXPoints()[1], getYPoints()[1]));
		Renderer.drawDebugLine(new Vector2(getXPoints()[1], getYPoints()[1]),
				new Vector2(getXPoints()[2], getYPoints()[2]));
		Renderer.drawDebugLine(new Vector2(getXPoints()[2], getYPoints()[2]),
				new Vector2(getXPoints()[3], getYPoints()[3]));
		Renderer.drawDebugLine(new Vector2(getXPoints()[3], getYPoints()[3]),
				new Vector2(getXPoints()[0], getYPoints()[0]));
	}

	public Tile getTile() {
		return tile;
	}

	public int[] getXPoints() {
		return new int[] { (int) x, (int) x + 32, (int) x + 64, (int) x + 32 };
	}

	public int[] getYPoints() {
		return new int[] { (int) y + 32, (int) y + 48, (int) y + 32, (int) y + 16 };
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x - 32, (int) y - 24, 64, 48);
	}

	public Vector2 getMiddlePoint() {
		return new Vector2(x + 32, y + 32);
	}

	public void setObject(GameObject object) {
		if (object != null) {
			if(this.object != null) {
				Main.inst.world.removeObject(this.object);
				this.object.setCell(null);
			}
			object.setCell(this);
			Main.inst.world.addObject(object, Reason.FORCED);
		}
		
		this.object = object;
	}

	public GameObject getObject() {
		return object;
	}

	public int getWorldIndexX() {
		int indexX = (int) chunk.getIndex().x * Chunk.SIZE;

		indexX += this.indexX;

		return indexX;
	}

	public int getWorldIndexY() {
		int indexY = (int) chunk.getIndex().y * Chunk.SIZE;

		indexY += this.indexY;

		return indexY;
	}

	@Override
	public String toString() {
		return "X: " + x + " Y: " + y + " IX: " + indexX + " IY: " + indexY;
	}
}