package model;

import java.util.ArrayList;

public class ObstacleFactory {
	
	
	private ArrayList<Obstacle> factoryObstacles;
	private ArrayList<ObstacleFair> factoryObstacle4X4;
	private ArrayList<ObstacleFair> factoryObstacle4X3;
	
	
	public ObstacleFactory(ArrayList<Obstacle> obstacleArray){
		
		this.factoryObstacles = obstacleArray;
		
		
		
	}
	
	public void produce4X4Obstacle(){
		
		
		factoryObstacle4X4 = new ArrayList<ObstacleFair>();
		factoryObstacle4X4.add(new ObstacleFair(0,200));
		factoryObstacle4X4.add(new ObstacleFair(0,300));
		factoryObstacle4X4.add(new ObstacleFair(50,500));
		factoryObstacle4X4.add(new ObstacleFair(150,500));
		factoryObstacle4X4.add(new ObstacleFair(250,500));
		factoryObstacle4X4.add(new ObstacleFair(150,350));
		factoryObstacle4X4.add(new ObstacleFair(700,275));
		
		for(int i =0; i < factoryObstacle4X4.size(); i++){
			
			factoryObstacle4X4.get(i).transform4X4();
			factoryObstacles.add(factoryObstacle4X4.get(i));
			
		}
		
	}
	
	public void produce4X3Obstacle(){
		
		
		factoryObstacle4X3 = new ArrayList<ObstacleFair>();
		factoryObstacle4X3.add(new ObstacleFair(150,0));
		//factoryObstacle4X3.add(new ObstacleFair(150,75));
		factoryObstacle4X3.add(new ObstacleFair(150,150));
		factoryObstacle4X3.add(new ObstacleFair(150,225));
		
		factoryObstacle4X3.add(new ObstacleFair(525,0));
		
		factoryObstacle4X3.add(new ObstacleFair(700,0));
		factoryObstacle4X3.add(new ObstacleFair(700,125));
		factoryObstacle4X3.add(new ObstacleFair(700,200));
		factoryObstacle4X3.add(new ObstacleFair(700,375));
		
		
		for(int i =0; i < factoryObstacle4X3.size(); i++){
			
			factoryObstacle4X3.get(i).transform4X3();
			factoryObstacles.add(factoryObstacle4X3.get(i));
			
		}
		
	}
	
	public ArrayList<Obstacle> getFactoryObstacleArray(){
		return factoryObstacles;
	}
	
	

}
