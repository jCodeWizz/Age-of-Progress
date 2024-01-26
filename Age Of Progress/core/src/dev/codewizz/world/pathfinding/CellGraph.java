package dev.codewizz.world.pathfinding;

import java.util.HashMap;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;

import dev.codewizz.world.Cell;

public class CellGraph implements IndexedGraph<Cell> {

	private CellHeuristic heuristic = new CellHeuristic();
	private Array<Cell> cells = new Array<>();
	
	private HashMap<Cell, Array<Link>> links = new HashMap<>();
	private HashMap<Cell, Array<Connection<Cell>>> linkMap = new HashMap<>();
	
	private int lastIndex = 0;
	
	public void addCell(Cell cell) {
		cell.index = lastIndex++;
		cells.add(cell);
	}
	
	public void connectCells(Cell fromCell, Cell toCell, int cost) {
		Link link = new Link(fromCell, toCell);
		link.setCost(cost);
		if(!linkMap.containsKey(fromCell)) {
			Array<Connection<Cell>> l = new Array<>();
			linkMap.put(fromCell, l);
		}
		
		Array<Connection<Cell>> b = linkMap.get(fromCell);
		if(b != null) {
			b.add(link);
		} else {
			Array<Connection<Cell>> l = new Array<>();
			l.add(link);
			linkMap.put(fromCell, l);
		}
		
		if(!links.containsKey(fromCell)) {
			Array<Link> l = new Array<>();
			links.put(fromCell, l);
		}
		
		Array<Link> c = links.get(fromCell);
		if(c != null) {
			c.add(link);
		} else {
			Array<Link> l = new Array<>();
			l.add(link);
			links.put(fromCell, l);
		}
	}
	
	public boolean containsCell(Cell cell) {
		return links.containsKey(cell);
	}
	
	public GraphPath<Cell> findPath(Cell startCell, Cell goalCell) {
		GraphPath<Cell> cellPath = new DefaultGraphPath<>();
		new IndexedAStarPathFinder<>(this).searchNodePath(startCell, goalCell, heuristic, cellPath);
		return cellPath;
	}
	
	public Array<Link> getLinks(Cell fromCell) {
		return links.get(fromCell);
	}
	
	public void removeConnections(Cell fromNode) {
		
		for(int i = 0; i < fromNode.connectedTo.length; i++) {
			fromNode.connectedTo[i] = false;
			fromNode.acceptConnections[i] = false;
		}
		
		
		linkMap.remove(fromNode);
		links.remove(fromNode);
	}
	
	public void removeConnection(Cell from, Cell to) {
		Array<Connection<Cell>> a = linkMap.get(from);
		Array<Connection<Cell>> b = linkMap.get(to);
		
		Array<Link> d = links.get(from);
		Array<Link> e = links.get(to);
		
		if(a != null) {
			for(Connection<Cell> c : a) {
				if(c.getFromNode().index == from.index && c.getToNode().index == to.index) {
					a.removeValue(c, false);
				}
			}
		}
		
		if(d != null) {
			for(Link c : d) {
				if(c.getFromNode().index == from.index && c.getToNode().index == to.index) {
					d.removeValue(c, false);
				}
			}
		}
		
		if(b != null) {
			for(Connection<Cell> c : b) {
				if(c.getFromNode().index == from.index && c.getToNode().index == to.index) {
					b.removeValue(c, false);
				}
			}
		}
		
		if(e != null) {
			for(Link c : e) {
				if(c.getFromNode().index == from.index && c.getToNode().index == to.index) {
					e.removeValue(c, false);
				}
			}
		}
	}
	
	@Override
	public Array<Connection<Cell>> getConnections(Cell fromNode) {
		if (linkMap.containsKey(fromNode)) {
			return linkMap.get(fromNode);
		}

		return new Array<>(0);
	}

	@Override
	public int getIndex(Cell node) {
		return node.index;
	}

	@Override
	public int getNodeCount() {
		return lastIndex;
	}
}
