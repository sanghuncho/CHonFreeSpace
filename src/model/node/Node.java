package model.node;

import java.util.ArrayList;
import java.util.ListIterator;

import util.enums.Directions;
import util.enums.Property;
import util.math.Vector;
import util.math.Vector2D;

public class Node implements Comparable<Node>{
	
	/** The previous node. */
	private Node north, northEast, east, southEast, south, southWest, west,
			northWest, previousNode;
	
	private int obstacleValue = 0;
	private int costFromStart;
	
	/** The start list. */
	private ArrayList<NodeProperty> startList = new ArrayList<NodeProperty>();
	
	/** The position. */
	//private Vector vector;
	private Vector2D vector;
	private Property property;
	private NodeCircle nodeCircle;
	private Vector neighborOfNeighbor;

	
	public Node(Vector2D vector) {

		this.vector = vector;
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
		return vector;
	}
	
	public Vector2D getNodeVector() {
		return vector;
	}
	
	public boolean isObstacle() {
		return property == Property.OBSTACLE;
	}
	public boolean isStart() {
		return property == Property.START;
	}
	public boolean isGoal() {
		return property == Property.GOAL;
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
	
	
	public Directions getDirectionOfNeighbor(Node neighbor) {
		
		
		if(neighbor == null){
			
			System.out.print("neighbor is null \n");
		}
		
		Directions neighborOfNeighbor = null;
		
		
		if(north != null && north.equals(neighbor)){
			
			System.out.print("neighbor 1 \n");

			neighborOfNeighbor = Directions.NORTH;
		
		}
		else if(northEast!= null && northEast.equals(neighbor)){
			System.out.print("neighbor 2 \n");
			neighborOfNeighbor = Directions.NORTH_EAST;
		}
		else if(east != null && east.equals(neighbor)){
			System.out.print("neighbor 3 \n");
			neighborOfNeighbor = Directions.EAST;
		}
		else if(southEast != null && southEast.equals(neighbor)){
			System.out.print("neighbor 4 \n");
			neighborOfNeighbor = Directions.SOUTH_EAST;
		}
		else if(south != null && south.equals(neighbor)){
			System.out.print("neighbor 5 \n");
			neighborOfNeighbor = Directions.SOUTH;
		}
		else if(southWest != null && southWest.equals(neighbor)){
			System.out.print("neighbor 6 \n");
			neighborOfNeighbor = Directions.SOUTH_WEST;
		}
		else if(west!= null && west.equals(neighbor)){
			System.out.print("neighbor 7 \n");
			neighborOfNeighbor = Directions.WEST;
		}
		else if(northWest != null && northWest.equals(neighbor)){
			System.out.print("neighbor 8 \n");
			neighborOfNeighbor = Directions.NORTH_WEST;
		}
		else{}
		
		return neighborOfNeighbor;
	}
		
		/*if(north != null && north.equals(neighbor)){
			
			System.out.print("neighbor 1 \n");

			neighborOfNeighbor = new Vector2D(
					neighbor.getNorth().getPosition().getX(),neighbor.getNorth().getPosition().getY());
		
		}
		else if(northEast!= null && northEast.equals(neighbor)){
			System.out.print("neighbor 2 \n");
			neighborOfNeighbor = new Vector2D(
					neighbor.getNorthEast().getPosition().getX(),neighbor.getNorthEast().getPosition().getY());
		}
		else if(east != null && east.equals(neighbor)){
			System.out.print("neighbor 3 \n");
			neighborOfNeighbor = new Vector2D(
					neighbor.getEast().getPosition().getX(), neighbor.getEast().getPosition().getY());
		}
		else if(southEast != null && southEast.equals(neighbor)){
			System.out.print("neighbor 4 \n");
			neighborOfNeighbor = new Vector2D(
					neighbor.getSouthEast().getPosition().getX(), neighbor.getSouthEast().getPosition().getY());
		}
		else if(south != null && south.equals(neighbor)){
			System.out.print("neighbor 5 \n");
			neighborOfNeighbor = new Vector2D(
					neighbor.getSouth().getPosition().getX(), neighbor.getSouth().getPosition().getY());
		}
		else if(southWest != null && southWest.equals(neighbor)){
			System.out.print("neighbor 6 \n");
			neighborOfNeighbor = new Vector2D(
					neighbor.getSouthWest().getPosition().getX(), neighbor.getSouthWest().getPosition().getY());
		}
		else if(west!= null && west.equals(neighbor)){
			System.out.print("neighbor 7 \n");
			neighborOfNeighbor = new Vector2D(
					neighbor.getWest().getPosition().getX(), neighbor.getWest().getPosition().getY());
		}
		else if(northWest != null && northWest.equals(neighbor)){
			System.out.print("neighbor 8 \n");
			neighborOfNeighbor = new Vector2D(
					neighbor.getNorthEast().getPosition().getX(), neighbor.getNorthEast().getPosition().getY());
		}
		else{}
		
		return (Vector2D) neighborOfNeighbor;
		
	}*/
	
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
	
	public boolean equals(Object obj) {
        if (this == obj)
                return true;
        if (obj == null)
                return false;
        if (getClass() != obj.getClass())
                return false;
        Node other = (Node) obj;
        if (this.getPosition() == null) {
                if (other.getPosition() != null)
                        return false;
        } else if (!this.getPosition().equals(other.getPosition()))
                return false;
        return true;
	}
	
	public Node getNorth(){return north;}
	
	public Node getNorthEast(){return northEast;}
	
	public Node getEast(){return east;}
	
	public Node getSouthEast(){return southEast;}
	
	public Node getSouth(){return south;}
	
	public Node getSouthWest(){return southWest;}
	
	public Node getWest(){return west;}
	
	public Node getNorthWest(){return northWest;}
	
	
	
	

}
