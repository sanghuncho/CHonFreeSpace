package model.node;

import java.util.ArrayList;
import java.util.ListIterator;

import util.enums.Directions;
import util.enums.Property;
import util.math.Vector;

public class Node implements Comparable<Node>{
	
	/** The previous node. */
	private Node north, northEast, east, southEast, south, southWest, west,
			northWest, previousNode;
	
	private int obstacleValue = 0;
	private int costFromStart;
	
	/** The start list. */
	private ArrayList<NodeProperty> startList = new ArrayList<NodeProperty>();
	
	/** The position. */
	private Vector position;
	private Property property;
	private NodeCircle nodeCircle;

	
	public Node(Vector vector) {

		this.position = vector;
		property = Property.DEFAULT;
		this.costFromStart = Integer.MAX_VALUE;
		
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
	
	public boolean isObstacle() {
		return property == Property.OBSTACLE;
	}
	
	public int getObstacleValue() {
		return obstacleValue;
	}
	
	public void setObstacleValue(int obstacleValue) {
		this.obstacleValue = obstacleValue;
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
	
	public synchronized int compareTo(Node otherNode) {
		int better = -1;
		int equal = 0;
		int worse = 1;

		/* if this node is cheaper, it is better */
		if (costFromStart < otherNode.costFromStart) {
			return better;

			/* if the other node is cheaper, this one is worse */
		} else if (costFromStart > otherNode.costFromStart) {
			return worse;

			/* if they are equally expensive, check the distance */
		} else {

			/* if this node is closer to the start, it is better 
			if (distanceFromStart < otherNode.distanceFromStart) {
				return better;
				 if the distance is greater, it is worse 
			} else if (distanceFromStart > otherNode.distanceFromStart) {
				return worse;
			 
				
				 * else the nodes are equal (concerning the criteria used here)
				 
			} else {
				return equal;
			}*/
			
			return equal;
		}
	}
	
	public int getCostFromStart() {
		return costFromStart;
	}

	/**
	 * Sets the cost from start.
	 *
	 * @param cost
	 *            the new cost from start
	 */
	public void setCostFromStart(int cost) throws ArithmeticException {
		costFromStart = cost;
		if (cost < -1) {
			throw new ArithmeticException("Node (" + this.getPosition().getX()
					+ "/" + this.getPosition().getY()
					+ ") cost is smaller than zero and not minus one! -> "
					+ cost);
		}
	}
	

}
