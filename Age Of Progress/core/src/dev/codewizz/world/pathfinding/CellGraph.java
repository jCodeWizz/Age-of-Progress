package dev.codewizz.world.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import dev.codewizz.world.Cell;

public class CellGraph implements IndexedGraph<Cell> {

	private CellHeuristic heuristic = new CellHeuristic();
	private Array<Cell> cells = new Array<>();
	private Array<Link> links = new Array<>();
	
	private ObjectMap<Cell, Array<Connection<Cell>>> linkMap = new ObjectMap<>();
	
	private int lastIndex = 0;
	
	public void addCell(Cell cell) {
		cell.index = lastIndex++;
		cells.add(cell);
	}
	
	public void connectCells(Cell fromCell, Cell toCell, int cost) {
		Link link = new Link(fromCell, toCell);
		link.setCost(cost);
		if(!linkMap.containsKey(fromCell)) {
			linkMap.put(fromCell, new Array<Connection<Cell>>());
		}
		if(!linkMap.get(fromCell).contains(link, false)) {
			linkMap.get(fromCell).add(link);
		}
		links.add(link);
	}
	
	public boolean containsCell(Cell cell) {
		return linkMap.containsKey(cell);
	}
	
	public GraphPath<Cell> findPath(Cell startCell, Cell goalCell) {
		GraphPath<Cell> cellPath = new DefaultGraphPath<>();
		new IndexedAStarPathFinder<>(this).searchNodePath(startCell, goalCell, heuristic, cellPath);
		return cellPath;
	}
	
	public Array<Link> getLinks(Cell fromCell) {
		Array<Link> a = new Array<>();
		for(int i = 0; i < links.size; i++) {
			Link link = links.get(i);
			if(link.getFromNode().index == fromCell.index) {
				a.add(link);
			}
		}
		return a;
	}
	
	public void removeConnections(Cell fromNode) {
		linkMap.remove(fromNode);
	}
	
	public void removeConnection(Cell from, Cell to) {
		Array<Connection<Cell>> a = linkMap.get(from);
		Array<Connection<Cell>> b = linkMap.get(to);
		
		if(a != null) {
			for(Connection<Cell> c : a) {
				if(c.getFromNode().index == from.index && c.getToNode().index == to.index) {
					linkMap.get(from).removeValue(c, false);
				}
			}
		}
		
		if(b != null) {
			for(Connection<Cell> c : b) {
				if(c.getFromNode().index == from.index && c.getToNode().index == to.index) {
					linkMap.get(from).removeValue(c, false);
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
