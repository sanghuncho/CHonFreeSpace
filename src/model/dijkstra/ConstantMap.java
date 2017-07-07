package model.dijkstra;

import model.CHmodel;
import model.node.Node;
import model.node.NodeMap;
import util.math.Vector;

public class ConstantMap {
	
	private int sizeOfObstacleNode;
	
	private static int[][] constantMap = 
			new int[CHmodel.getSizeVector2D().getX()][CHmodel.getSizeVector2D().getY()];
	
	public ConstantMap(){ }
	
	public int[][] getMap(){
		return constantMap;
	}
	
	public int getColumn(){
		
		return constantMap[0].length;
	}
	
	public int getRow(){
		
		return constantMap.length;
	}
	public boolean isObstacle(Vector point) {
		return constantMap[point.getX()][point.getY()] == -1;
	}
	
	public boolean isContractedNode(Vector point) {
		
		if(constantMap[point.getX()][point.getY()] == 2){

			return true;
			
		}
		else{
			return false;
		}
		
	}
	public void setCostOnConstantMap(NodeMap nodeMap){
		
		sizeOfObstacleNode = 0;
		
		for (Node node : nodeMap.getNodes()) {

			if (node.isObstacle()) {
			
				setCost(node.getPosition(), CHmodel.VALUE_MAP_OBSTACLE);
				sizeOfObstacleNode++;
				 
			}else if(node.isStart() || node.isGoal()){
		
				setCost(node.getPosition(), CHmodel.VALUE_MAP_START_GAOL);
			} 
				
			else {
				
				setCost(node.getPosition(),CHmodel.VALUE_MAP_POINT);

			}
		}
		
		
	}
	private void setCost(Vector position, int value) throws ArithmeticException {
		
		constantMap[position.getX()][position.getY()] = value;

		if (value < -1) {
			throw new ArithmeticException("set cost < -1 : " + value);
		}
	}
	public int getSizeOfObstacleNode(){
		
		return sizeOfObstacleNode;
	}
	

}
