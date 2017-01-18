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
	
	public static int startX;
	public static int startY;

	public static int goalX;
	public static int goalY;
	
	public static int obstacle;
	
	public static Vector2D sizeVector;
	
	public static NodeMap nodeMap;
	
	public static String percentage;
	
	public static String getPercentage() {
		return percentage;
	}

	public static void setPercentage(String percentage) {
		CHmodel.percentage = percentage;
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
	
	public static int getNumberContracted() {
				
		int result = 0;
		
		switch (CHmodel.percentage){
			case "10%" : result = 10;
						 break;
			case "20%" : result = 20;
			 			 break;
			case "30%" : result = 20;
			 			 break;

		}
		
		int nodes = (mapX*mapY)/100;
	
		return  (nodes*result)/100;
	}
}
