package model;

import model.node.NodeMap;
import util.math.Vector;
import util.math.Vector2D;

public class CHmodel {
	
	public static int mapX;
	public static int mapY;
	
	public static final int UNIT_MAP = 10;
	public static final int VALUE_MAP_OBSTACLE = -1;
	public static final int VALUE_MAP_START_GAOL = 0;
	public static final int VALUE_MAP_POINT = 1;
	public static final int VALUE_MAP_CONTRACTING = 2;
	public static final int VALUE_MAP_VIA_NODE = 3;
	public static final int VALUE_OBSTACLE_WIDTH = 25;
	public static final int VALUE_OBSTACLE_HEIGHT = 25;
	public static final int VALUE_OBSTACLE_CREATE_DISTANCE = 15;
	public static int startX;
	public static int startY;

	public static int goalX;
	public static int goalY;
	
	public static int obstacleNumber;
	
	public static int percentageCustom;
	
	public static Vector2D sizeVector;
	
	public static NodeMap nodeMap;
		
	public static String mode;
	public static int nodeNumberObstacle;
	public static int numberOfViaNode;
	
	public static String getMode() {
		return mode;
	}

	/*There are two mode, manual or fair*/
	public static void setMode(String mode){
		CHmodel.mode = mode;
	}

	public static Vector2D getStartVector2D(){
		
		return new Vector2D((startX-5)/10,(startY-5)/10);
	}
	
	public static Vector2D getGoalVector2D(){
		
		return new Vector2D((goalX-5)/10,(goalY-5)/10);
	}
	
	
	public static Vector2D getSizeVector2D() {
		return sizeVector;
	}

	public static void setSizeVector2D(Vector2D sizeVector) {
		CHmodel.sizeVector = sizeVector;
	}

	public static NodeMap getNodeMap() {
		return nodeMap;
	}

	public static void setNodeMap(NodeMap nodeMap) {
		CHmodel.nodeMap = nodeMap;
	}

	
	public static int getMapX() {
		return mapX;
	}
	
	public static int getNodeMapSizeX(){
		return mapX/10;
	}
	public static int getNodeMapSizeY(){
		return mapY/10;
	}

	public static void setMapX(int mapX) {
		CHmodel.mapX = mapX;
	}

	public static int getMapY() {
		return mapY;
	}

	public static void setMapY(int mapY) {
		CHmodel.mapY = mapY;
	}

	public static int getStartX() {
		return startX;
	}

	public static void setStartX(int startX) {
		CHmodel.startX = (startX-1)*10+5;
	}

	public static int getStartY() {
		return startY;
	}

	public static void setStartY(int startY) {
		CHmodel.startY = (startY-1)*10+5;
	}

	public static int getGoalX() {
		return goalX;
	}

	public static void setGoalX(int goalX) {
		CHmodel.goalX = (goalX-1)*10+5;
	}

	public static int getGoalY() {
		return goalY;
	}

	public static void setGoalY(int goalY) {
		CHmodel.goalY = (goalY-1)*10+5;
	}

	public static int getNumberObstacle() {
		return obstacleNumber;
	}

	public static void setNumberObstacle(int obstacleNumber) {
		CHmodel.obstacleNumber = obstacleNumber;
	}
	
	public static void setContractingPercent(int percentageCustom) {
		CHmodel.percentageCustom = percentageCustom;
	}
	
	public static int getNumberContractedCustom() {
		
		
		int result = CHmodel.percentageCustom;
		
		int nodes = (mapX*mapY)/100;
		
		/**
		 * Here 2 is regarded as number of start point and goal point
		 * */
		return  ((nodes-2-nodeNumberObstacle)*result)/100; 
	}
	public static void setNodeNumberObstacle(int nodeNumberObs){
		
		CHmodel.nodeNumberObstacle = nodeNumberObs;
	}
	public static void setNumberOfViaNode(int numberViaNode){
		
		CHmodel.numberOfViaNode = numberViaNode;
	}

}
