package model;

import java.util.ArrayList;

public class ObstacleFactory {
	
	
	private ArrayList<Obstacle> factoryObstacles;
	private ArrayList<ObstacleFair> factoryObstacle4X4;
	
	public ObstacleFactory(ArrayList<Obstacle> obstacleArray){
		
		this.factoryObstacles = obstacleArray;
		
		
		
	}
	
	public void produce4X4Obstacle(){
		
		int size4X4 = 6;
		
		factoryObstacle4X4 = new ArrayList<ObstacleFair>();
		factoryObstacle4X4.add(new ObstacleFair(0,200));
		factoryObstacle4X4.add(new ObstacleFair(0,400));
		factoryObstacle4X4.add(new ObstacleFair(150,450));
		
		for(int i =0; i < factoryObstacle4X4.size(); i++){
			
			factoryObstacle4X4.get(i).transform4X4();
			factoryObstacles.add(factoryObstacle4X4.get(i));
			
		}
		
	}
	
	public ArrayList<Obstacle> getFactoryObstacleArray(){
		return factoryObstacles;
	}
	
	

}
