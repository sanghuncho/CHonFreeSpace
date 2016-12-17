package model.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.node.Node;
import model.node.NodeMap;
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

	private ArrayList<Node> visitedNodes = new ArrayList<Node>();

	private ArrayList<Vector> visitedPoints = new ArrayList<Vector>(),
							  pointParkingHelper = new ArrayList<Vector>(),
							  onHoldList = new ArrayList<Vector>(),
							  pointParking = new ArrayList<Vector>();
	
	private int lowestCost;
	
	private int[][] map;
		
	public CostMap(Vector size, Vector start,Vector goal, NodeMap nodeMap) {
		
		this.size = size;
		this.startPoint = start;
		this.goalPoint = goal;
		this.nodes = nodeMap.getNodes();
		this.nodeSize = nodeMap.getNodes().size();
		this.nodeMap = nodeMap;
		
		map = new int[size.getX()][size.getY()];
		
		for (Node node : nodeMap.getNodes()) {

			if (node.isObstacle()) {

				setCost(node.getPosition(), -1);
				
			} else {
				
				//setCost(node.getPosition(), node.getObstacleValue());
				setCost(node.getPosition(),0);

			}
		}
		
		map[startPoint.getX()][startPoint.getY()] = 0;
		visitedPoints.add(startPoint);
		
		//printMapToConsole();	
		
		//floodMap();
		
		createEdgeOnMap();
		
		Graph graph = new Graph(nodes,edges);
		
		dijkstra = new DijkstraAlgorithm(graph); 
		
		//printMapToConsole();
		
		//dijkstraAlgorithm();
		
		
	}
	
	public DijkstraAlgorithm getDijkstra(){
		return dijkstra;
	}
	
	public NodeMap getNodeMap(){
		return nodeMap;
	}
	
	public List<Node> getNodes(){
		
		return nodes;
	}
	
	/*private void floodMap(){
		
		createSurroundingCosts(startPoint);
		//createSurroundingEdges(startPoint);
		
		while (!goalReached()) {
						
			copyPoints();
			
			for (Vector newPoint : getPointParking()) {
				createSurroundingCosts(newPoint);
			}
		}
		copyPoints();
		for (Vector newPoint : getPointParking()) {
			createSurroundingCosts(newPoint);
		}
	}*/
	
	private void createEdgeOnMap() {
		
		createSurroundingEdges(nodes);
		
	}
	
	private void createSurroundingEdges(List<Node> nodes) {
		
		for (Node node : nodes){
			
			for (Node neighbor : node.getNeighborList()) {
	            
				if (!isObstacle(node.getPosition()) &&
						!(checkForNode(visitedNodes, neighbor))){
					
					if (!(checkForNode(visitedNodes, node))) {
						
						createEdge(node,neighbor);
						
					}
				}
			}
			visitedNodes.add(node);
		}

	}
	
	private void createEdge(Node node,Node neighbor){
				
		lane = new Edge(edgeId,node,neighbor,WEIGHT);
		edges.add(lane);
		edgeId++;	
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

	private boolean checkForPoint(ArrayList<Vector> list, Vector point) {
		for (Vector checkPoint : list) {
			if (checkPoint.getX() == point.getX()
					&& checkPoint.getY() == point.getY()) {
				return true;
			}
		}
		return false;
	}
	
	
	/*private void createSurroundingCosts(Vector middlePoint) {
		
		for (Vector point : sortTheList( getSurroundingPoints(middlePoint.getX(), middlePoint.getY()))) {
			if (!(point.getX() < 0 || point.getY() < 0
					|| point.getX() >= size.getX()
					|| point.getY() >= size.getY())) {
				if (!isObstacle(point)) {
					if (!(checkForPoint(visitedPoints, point))) {
						setCost(point, (getCost(middlePoint) + getCost(point)));
						visitedPoints.add(point);
						getPointParkingHelper().add(point);
					}
				}
			}
		}
	}*/
	
	private ArrayList<Node> sortTheList(ArrayList<Node> sortedList) {
		Collections.sort(sortedList);
		return sortedList;
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

	
	
	/*private void copyPoints() {
		
		lowestCost = Integer.MAX_VALUE;
		
		getPointParking().clear();

		for (Vector transferPoint : onHoldList) {
			getPointParkingHelper().add(transferPoint);
		}
		onHoldList.clear();
		 At first, check all stored points for the lowest cost. 
		for (Vector costPoint : getPointParkingHelper()) {
			checkLowestCost(costPoint);
		}
		for (Vector copyPoint : getPointParkingHelper()) {
			if (getCost(copyPoint) <= lowestCost) {
				getPointParking().add(copyPoint);
			} else {
				onHoldList.add(copyPoint);
			}
		}
		getPointParkingHelper().clear();
		sortTheList(getPointParking());
	}*/

	
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
	

	
	private boolean isObstacle(Vector point) {
		return map[point.getX()][point.getY()] == -1;
	}

	private boolean goalReached() {
		for (Vector point : getPointParking()) {
			if (point.getX() == goalPoint.getX()
					&& point.getY() == goalPoint.getY()) {
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<Vector> getPointParking() {
		return pointParking;
	}
	
	public void printMapToConsole() {
		for (int i = 0; i < size.getX(); i++) {
			for (int j = 0; j < size.getY(); j++) {
				if (map[i][j] == -1) {
					System.out.print("X\t");
				}
				else if (i == goalPoint.getX() && j == goalPoint.getY()) {
					System.out.print("G\t");
				 } else if (i == startPoint.getX() && j == startPoint.getY())
				 {
					System.out.print("S\t");
				 }
				else {
					System.out.print(getCostForCoordinates(i, j) + "\t");
				}
			}
			System.out.println();
		}
	}

	
	
}