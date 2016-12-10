package model;

import model.node.NodeMap;
import util.math.Vector;
import util.math.Vector2D;

public class CHmodel {
	
	public static int mapX;
	public static int mapY;
	
	public static final int UNIT_MAP = 10;
	
	public static int startX;
	public static int startY;

	public static int goalX;
	public static int goalY;
	
	public static int obstacle;
	
	public static Vector2D sizeVector;
	
	public static NodeMap nodeMap;
	
	public static Vector getStartVector2D(){
		
		return new Vector2D(startX,startY);
	}
	
	public static Vector getGoalVector2D(){
		
		return new Vector2D(goalX,goalY);
	}
	
	
	public static Vector getSizeVector2D() {
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
		CHmodel.startX = startX;
	}

	public static int getStartY() {
		return startY;
	}

	public static void setStartY(int startY) {
		CHmodel.startY = startY;
	}

	public static int getGoalX() {
		return goalX;
	}

	public static void setGoalX(int goalX) {
		CHmodel.goalX = goalX;
	}

	public static int getGoalY() {
		return goalY;
	}

	public static void setGoalY(int goalY) {
		CHmodel.goalY = goalY;
	}

	public static int getObstacle() {
		return obstacle;
	}

	public static void setObstacle(int obstacle) {
		CHmodel.obstacle = obstacle;
	}
}
