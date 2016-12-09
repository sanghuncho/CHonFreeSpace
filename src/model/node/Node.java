package model.node;

import java.util.ArrayList;
import java.util.ListIterator;

import util.enums.Directions;
import util.enums.Property;
import util.math.Vector;

public class Node {
	
	/** The previous node. */
	private Node north, northEast, east, southEast, south, southWest, west,
			northWest, previousNode;
	
	/** The start list. */
	private ArrayList<NodeProperty> startList = new ArrayList<NodeProperty>();
	
	/** The position. */
	private Vector position;
	private Property property;
	private NodeCircle nodeCircle;
	
	public Node(Vector vector) {

		this.position = vector;
		property = Property.DEFAULT;
		
	}

	public void setNodeCircle(NodeCircle circle){
		this.nodeCircle = circle;
	}
	
	public NodeCircle getNodeCircle(){
		return nodeCircle;
	}
	
	public ArrayList<NodeProperty> getStartList() {
		return startList;
	}
	
	public void setStartList(ArrayList<NodeProperty> startList) {
		this.startList = startList;
	}
	
	public synchronized Property getProperty() {
		return property;
	}
	
	public synchronized void setProperty(Property property) {
		this.property = property;
	}
	
	public Vector getPosition() {
		return position;
	}
	
	public ArrayList<Node> getNeighborList() {

		ArrayList<Node> neighbors = new ArrayList<>();

		neighbors.add(north);
		neighbors.add(northEast);
		neighbors.add(east);
		neighbors.add(southEast);
		neighbors.add(south);
		neighbors.add(southWest);
		neighbors.add(west);
		neighbors.add(northWest);

		ListIterator<Node> iterator = neighbors.listIterator();
		while (iterator.hasNext()) {
			if (iterator.next() == null) {
				iterator.remove();
			}
		}

		return neighbors;
	}
	
	
	public void addNeighbor(Node node, Directions direction) {

		switch (direction) {
		case NORTH:
			this.north = node;
			break;

		case NORTH_EAST:
			this.northEast = node;
			break;

		case EAST:
			this.east = node;
			break;

		case SOUTH_EAST:
			this.southEast = node;
			break;

		case SOUTH:
			this.south = node;
			break;

		case SOUTH_WEST:
			this.southWest = node;
			break;

		case WEST:
			this.west = node;
			break;

		case NORTH_WEST:
			this.northWest = node;
			break;
		}
	}

	

}
