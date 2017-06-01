package model;

import java.util.ArrayList;

public class ObstacleFactory {
	
	
	private ArrayList<Obstacle> factoryObstacles;
	private ArrayList<ObstacleFair> factoryObstacle4X4;
	private ArrayList<ObstacleFair> factoryObstacle4X3;
	private ArrayList<ObstacleFair> factoryObstacle6X4;
	private ArrayList<ObstacleFair> factoryObstacle3X5;
	private ArrayList<ObstacleFair> factoryObstacle4X2;
	private ArrayList<ObstacleFair> factoryObstacle2X4;
	private ArrayList<ObstacleFair> factoryObstacle3X4;
	private ArrayList<ObstacleFair> factoryObstacle3X3;
	
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
	
	public void produce6X4Obstacle(){
		
		
		factoryObstacle6X4 = new ArrayList<ObstacleFair>();
		
		factoryObstacle6X4.add(new ObstacleFair(350,500));
		factoryObstacle6X4.add(new ObstacleFair(650,500));
		
		for(int i =0; i < factoryObstacle6X4.size(); i++){
			
			factoryObstacle6X4.get(i).transform6X4();
			factoryObstacles.add(factoryObstacle6X4.get(i));
			
		}
		
	}

	public void produce3X5Obstacle(){
		
		
		factoryObstacle3X5 = new ArrayList<ObstacleFair>();
		
		factoryObstacle3X5.add(new ObstacleFair(300,150));
		factoryObstacle3X5.add(new ObstacleFair(300,325));
		
		for(int i =0; i < factoryObstacle3X5.size(); i++){
			
			factoryObstacle3X5.get(i).transform3X5();
			factoryObstacles.add(factoryObstacle3X5.get(i));
			
		}
		
	}
	public void produce4X2Obstacle(){
		
		
		factoryObstacle4X2 = new ArrayList<ObstacleFair>();
		
		factoryObstacle4X2.add(new ObstacleFair(300,0));
		
		
		for(int i =0; i < factoryObstacle4X2.size(); i++){
			
			factoryObstacle4X2.get(i).transform4X2();
			factoryObstacles.add(factoryObstacle4X2.get(i));
			
		}
		
	}
	public void produce2X4Obstacle(){
		
		
		factoryObstacle2X4 = new ArrayList<ObstacleFair>();
		
		factoryObstacle2X4.add(new ObstacleFair(425,150));
		factoryObstacle2X4.add(new ObstacleFair(425,250));
		factoryObstacle2X4.add(new ObstacleFair(425,350));
		factoryObstacle2X4.add(new ObstacleFair(550,500));
		
		
		for(int i =0; i < factoryObstacle2X4.size(); i++){
			
			factoryObstacle2X4.get(i).transform2X4();
			factoryObstacles.add(factoryObstacle2X4.get(i));
			
		}
		
	}
	public void produce3X4Obstacle(){
		
		
		factoryObstacle3X4 = new ArrayList<ObstacleFair>();
		
		factoryObstacle3X4.add(new ObstacleFair(400,0));
		
		
		
		for(int i =0; i < factoryObstacle3X4.size(); i++){
			
			factoryObstacle3X4.get(i).transform3X4();
			factoryObstacles.add(factoryObstacle3X4.get(i));
			
		}
		
	}
	public void produce3X3Obstacle(){
		
		
		factoryObstacle3X3 = new ArrayList<ObstacleFair>();
		
		factoryObstacle3X3.add(new ObstacleFair(550,150));
		factoryObstacle3X3.add(new ObstacleFair(550,275));
		factoryObstacle3X3.add(new ObstacleFair(550,350));
		
		
		
		for(int i =0; i < factoryObstacle3X3.size(); i++){
			
			factoryObstacle3X3.get(i).transform3X3();
			factoryObstacles.add(factoryObstacle3X3.get(i));
			
		}
		
	}
	
	public ArrayList<Obstacle> getFactoryObstacleArray(){
		return factoryObstacles;
	}
	
	

}
