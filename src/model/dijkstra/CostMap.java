package model.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import model.CHmodel;
import model.Obstacle;
import model.Point;
import model.node.Node;
import model.node.NodeMap;
import util.enums.Directions;
import util.math.Vector;
import util.math.Vector2D;
import util.math.Vector3D;

public class CostMap {
	
	private Vector size; 
	private NodeMap nodeMap;
	private int edgeId = 0;
	private Edge lane;
	private List<Node> nodes;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private ArrayList<Node> visitedNodes = new ArrayList<Node>();
    private int[][] map;
	private long durationOfgeneratingAllShortcut;
	private long durationOfOneShortcut;
	
    //private Vector startPoint; 
	//private Vector goalPoint;	
	//private int nodeSize;
    //private final static int WEIGHT = 1;
    //private  ArrayList<Obstacle> obstacles;
	/*private ArrayList<Vector> visitedPoints = new ArrayList<Vector>(),
							  pointParkingHelper = new ArrayList<Vector>(),
							  onHoldList = new ArrayList<Vector>(),
							  pointParking = new ArrayList<Vector>();*/
	//private int lowestCost;
	//private DijkstraAlgorithm dijkstra;
	
	
	public CostMap(Vector size, Vector start, NodeMap nodeMap, 
				ArrayList<Obstacle> obstacles, int[][] map) {
		
		this.size = size;
		this.nodes = nodeMap.getNodes();
		this.nodeMap = nodeMap;
		this.map = map;
		this.durationOfgeneratingAllShortcut = 0;
		
		createSurroundingEdges(nodes);
		
		//this.startPoint = start;
		//this.nodeSize = nodeMap.getNodes().size();
		//this.obstacles = obstacles;
	}

	
	
/**
 * @param nodes
 * This method creates the edges around the node regarding contracted node, the obstacle.
 * If the neighbour node is already contracted, then find the next node until thisnode is not contracetd.
 * If the next node is not the contracted node, then the edge between two nodes is constructed.
 */
	private void createSurroundingEdges(List<Node> nodes) {
		
		for (Node node : nodes){
			
		 if( !isContractedNode(node.getPosition()) ){
			
			
			for (Node neighbor : node.getNeighborList()) {
	            
				int weight=1;
				
				Vector neighborVector = neighbor.getPosition();
				Directions neighborDirection = null;
				
				if(isContractedNode(neighborVector)){
					
					neighborDirection = node.getDirectionOfNeighbor(neighbor);

				}
				
				long startTime_shortcut = System.currentTimeMillis();
				
				while(isContractedNode(neighborVector)){
					
					Node freshNeighbor = getFreshNeighbor(node,neighborDirection,weight+1); 
					
					neighborVector = freshNeighbor.getPosition();
					
					weight++;
					
				}
				
				long endTime_shortcut = System.currentTimeMillis();
				
				durationOfOneShortcut = (endTime_shortcut - startTime_shortcut);
				durationOfgeneratingAllShortcut = durationOfgeneratingAllShortcut 
						+ durationOfOneShortcut;
				
				neighbor = nodeMap.get(neighborVector.getX(), neighborVector.getY());
				
				/**
				 * isObstacle-method checks the map-value
				 */
				if ( !isObstacle(node.getPosition()) && !isObstacle(neighbor.getPosition())){ 
					
					if (!(checkForNode(visitedNodes, node))) {
						 

						createEdge(node,neighbor,weight);
						
						
					}
					
				}
			}
			visitedNodes.add(node);
		}
	 }
		System.out.println("the duration of generating for all shortcuts : " 
	 + durationOfgeneratingAllShortcut + " miliseconds");
	}

	private Node getFreshNeighbor(Node node, Directions direction, int weight){
		
		int nodePosX = node.getPosition().getX();
		int nodePosY = node.getPosition().getY();
		
		Node freshNeighbor = null;
		
		if(direction.equals(Directions.NORTH)){
				freshNeighbor = nodeMap.get(nodePosX, nodePosY - weight);
		}
		else if(direction.equals(Directions.NORTH_EAST)){
				freshNeighbor = nodeMap.get(nodePosX + weight, nodePosY - weight);
		}
		else if(direction.equals(Directions.EAST)){
			freshNeighbor = nodeMap.get(nodePosX + weight, nodePosY);
		}
		else if(direction.equals(Directions.SOUTH_EAST)){
				freshNeighbor = nodeMap.get(nodePosX + weight, nodePosY + weight);
		}
		else if(direction.equals(Directions.SOUTH)){
			freshNeighbor = nodeMap.get(nodePosX, nodePosY + weight);
		}
		else if(direction.equals(Directions.SOUTH_WEST)){
			freshNeighbor = nodeMap.get(nodePosX - weight, nodePosY + weight);	
		}
		else if(direction.equals(Directions.WEST)){
			freshNeighbor = nodeMap.get(nodePosX-weight, nodePosY);	
		}
		else if(direction.equals(Directions.NORTH_WEST)){
			freshNeighbor = nodeMap.get(nodePosX-weight, nodePosY - weight);	
		}
		else{
			
		}
		return freshNeighbor;
		
	}
	
	private void createEdge(Node node,Node neighbor,int weight){
		
		lane = new Edge(edgeId,node,neighbor,weight);
		edges.add(lane);
		edgeId++;	
	}
	
	private boolean isObstacle(Vector point) {
		return map[point.getX()][point.getY()] == -1;
	}
	
	private boolean isContractedNode(Vector point) {
		
		if(map[point.getX()][point.getY()] == 2){

			return true;
			
		}
		else{
			return false;
		}
		
	}

	private boolean checkForNode(ArrayList<Node> list, Node node) {
		
		for (Node checkPoint : list) {
			if (checkPoint.getPosition().getX() == node.getPosition().getX()
					&& checkPoint.getPosition().getY() == node.getPosition().getY()) {
				return true;
			}
		}
		return false;
		
	}
	
	private int getCostForCoordinates(int xCord, int yCord)
			throws ArrayIndexOutOfBoundsException {
		if (xCord >= 0 && yCord >= 0 && xCord < size.getX()
				&& yCord < size.getY()) {
			return map[xCord][yCord];
		} else {
			throw new ArrayIndexOutOfBoundsException(
					"xCord = " + xCord + ", yCord = " + yCord);
		}
	}
	
	public int getCost(Vector point) {

		if (map[point.getX()][point.getY()] < -1) {
			throw new ArithmeticException(
					"set cost < -1 : " + map[point.getX()][point.getY()]);
		}
		return map[point.getX()][point.getY()];
	}
	
	public ArrayList<Edge> getEdges(){
		return edges;
	}

	public ArrayList<Vector> getSurroundingPoints(int x, int y) {
		ArrayList<Vector> neighbors = new ArrayList<Vector>();

		if (y >= 1) {
			neighbors.add(
					new Vector3D(x, y - 1, getCostForCoordinates(x, y - 1)));
		}
		if ((y >= 1) && (x < size.getX() - 1)) {
			neighbors.add(new Vector3D(x + 1, y - 1,
					getCostForCoordinates(x + 1, y - 1)));
		}
		if (x < size.getX() - 1) {
			neighbors.add(
					new Vector3D(x + 1, y, getCostForCoordinates(x + 1, y)));
		}
		if ((y < size.getY() - 1) && (x < size.getX() - 1)) {
			neighbors.add(new Vector3D(x + 1, y + 1,
					getCostForCoordinates(x + 1, y + 1)));
		}
		if (y < size.getY() - 1) {
			neighbors.add(
					new Vector3D(x, y + 1, getCostForCoordinates(x, y + 1)));
		}
		if ((x >= 1) && (y < size.getY() - 1)) {
			neighbors.add(new Vector3D(x - 1, y + 1,
					getCostForCoordinates(x - 1, y + 1)));
		}
		if (x >= 1) {
			neighbors.add(
					new Vector3D(x - 1, y, getCostForCoordinates(x - 1, y)));
		}
		if ((y >= 1) && (x >= 1)) {
			neighbors.add(new Vector3D(x - 1, y - 1,
					getCostForCoordinates(x - 1, y - 1)));
		}

		return neighbors;
	}
	
	public int[][] getMap(){return map;}
	
	public NodeMap getNodeMap(){
		return nodeMap;
	}
	
	public List<Node> getNodes(){
		
		return nodes;
	}
	
	/*public DijkstraAlgorithm getDijkstra(){
		return dijkstra;
	}
*/	
	/*private ArrayList<Vector> getPointParkingHelper() {
		return pointParkingHelper;
	}
	
	private void checkLowestCost(Vector point) {
		if (getCost(point) != 0) {
			if (getCost(point) < lowestCost) {
				lowestCost = getCost(point);
			}
		}
	}
	private ArrayList<Vector> getPointParking() {
		return pointParking;
	}*/
	
	/*private void createSurroundingEdges(List<Node> nodes) {
	
	for (Node node : nodes){
		
		for (Node neighbor : node.getNeighborList()) {
            
			
			isObstacle mathode check the map-value
			if ( !isObstacle(node.getPosition()) && !isObstacle(neighbor.getPosition())){ 
				
				if (!(checkForNode(visitedNodes, node))) {
					 
					createEdge(node,neighbor);
					
				}
				
			}
		}
		visitedNodes.add(node);
	}

	}*/
	

	/*public void setCost(Vector position, int value) throws ArithmeticException {
		
		map[position.getX()][position.getY()] = value;

		if (value < -1) {
			throw new ArithmeticException("set cost < -1 : " + value);
		}
	}*/
	
}
