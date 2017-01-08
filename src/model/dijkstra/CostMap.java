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
	
	private Vector size, startPoint, goalPoint;
	private NodeMap nodeMap;
	private int edgeId = 0;
	private Edge lane;
	private int nodeSize;
	private List<Node> nodes;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private final static int WEIGHT = 1;
    private DijkstraAlgorithm dijkstra;
    private  ArrayList<Obstacle> obstacles;
	private ArrayList<Node> visitedNodes = new ArrayList<Node>();
	private ArrayList<Vector> visitedPoints = new ArrayList<Vector>(),
							  pointParkingHelper = new ArrayList<Vector>(),
							  onHoldList = new ArrayList<Vector>(),
							  pointParking = new ArrayList<Vector>();
	
	private int lowestCost;
	private int[][] map;
	
	public CostMap(Vector size, Vector start, NodeMap nodeMap, ArrayList<Obstacle> obstacles,int[][] map) {
		
		this.size = size;
		this.startPoint = start;
		//this.goalPoint = goal;
		this.nodes = nodeMap.getNodes();
		this.nodeSize = nodeMap.getNodes().size();
		this.nodeMap = nodeMap;
		this.obstacles = obstacles;
		this.map = map;
		
		createSurroundingEdges(nodes);
	}

	public int[][] getMap(){return map;}
	
		
	public DijkstraAlgorithm getDijkstra(){
		return dijkstra;
	}
	
	public NodeMap getNodeMap(){
		return nodeMap;
	}
	
	public List<Node> getNodes(){
		
		return nodes;
	}
	
	private void createSurroundingEdges(List<Node> nodes) {
		
		for (Node node : nodes){
			
			
			
			for (Node neighbor : node.getNeighborList()) {
	            
				int weight=1;
				
				Vector neighborVector = neighbor.getPosition();
				Directions neighborDirection = null;
				
				if(isContractedNode(neighborVector)){
					
					neighborDirection = node.getDirectionOfNeighbor(neighbor);
					//System.out.println("direction : "+ neighborDirection +"\n");

				}
				
				while(isContractedNode(neighborVector)){
					 	
					/*System.out.println("node positionX : "+ node.getPosition().getX() +"\n");
					System.out.println("node positionY : "+ node.getPosition().getY() +"\n");
					
					System.out.println("neighbor positionX : "+ neighbor.getPosition().getX() +"\n");
					System.out.println("neighbor positionY : "+ neighbor.getPosition().getY() +"\n");*/
					
					/*node.getNotContractedNeighbor methode becomes only the direction and per position of the node
					get the neighbor*/
					
					Node freshNeighbor = getFreshNeighbor(node,neighborDirection,weight+1); 
					
					
					/*System.out.println("fresh positionX : "+ freshNeighbor.getPosition().getX() +"\n");
					System.out.println("fresh positionY : "+ freshNeighbor.getPosition().getY() +"\n");*/
					
					neighborVector = freshNeighbor.getPosition();
					weight++;
					
				}
				
				neighbor = nodeMap.get(neighborVector.getX(), neighborVector.getY());
				
				/*isObstacle mathode check the map-value*/
				if ( !isObstacle(node.getPosition()) && !isObstacle(neighbor.getPosition())){ 
					
					if (!(checkForNode(visitedNodes, node))) {
						 
						

						createEdge(node,neighbor,weight);//added to cost for node
						
						/*System.out.println("edgeNode positionX : "+ node.getPosition().getX() +"\n");
						System.out.println("edgeNode positionY : "+ node.getPosition().getY() +"\n");
						System.out.println("edgeGoal positionX : "+ neighbor.getPosition().getX() +"\n");
						System.out.println("edgeGoal positionY : "+ neighbor.getPosition().getY() +"\n");
						System.out.println("weight: "+ weight +"\n");*/
						
					}
					
				}
			}
			visitedNodes.add(node);
		}

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
	
	private void createEdge(Node node,Node neighbor,int weight){
		
		lane = new Edge(edgeId,node,neighbor,weight);

		/*System.out.println("create edge nodeX : "+ node.getPosition().getX() +"\n");
		System.out.println("create edge nodeY : "+ node.getPosition().getY() +"\n");
		
		System.out.println("create edge neighborX : "+ neighbor.getPosition().getX() +"\n");
		System.out.println("create edge neighborY : "+ neighbor.getPosition().getY() +"\n");
		System.out.println("weight : "+ weight +"\n");
		System.out.println("\n");*/
		
		edges.add(lane);
		edgeId++;	
	}
	
	public ArrayList<Edge> getEdges(){return edges;}
	
	private boolean isObstacle(Vector point) {
		return map[point.getX()][point.getY()] == -1;
	}
	
	private boolean isContractedNode(Vector point) {
		
		if(map[point.getX()][point.getY()] == 2){
			System.out.println("neighbor is contracted \n");
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
	
	public void setCost(Vector position, int value) throws ArithmeticException {
		
		map[position.getX()][position.getY()] = value;

		if (value < -1) {
			throw new ArithmeticException("set cost < -1 : " + value);
		}
	}
	
	public int getCost(Vector point) {

		if (map[point.getX()][point.getY()] < -1) {
			throw new ArithmeticException(
					"set cost < -1 : " + map[point.getX()][point.getY()]);
		}
		return map[point.getX()][point.getY()];
	}

	private ArrayList<Vector> getPointParkingHelper() {
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
	}
	
	
	
}
