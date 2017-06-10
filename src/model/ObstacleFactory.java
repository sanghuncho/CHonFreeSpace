package model;

import java.util.ArrayList;

public class ObstacleFactory {
	
	
	private ArrayList<Obstacle> factoryObstacles;
	private ArrayList<ObstacleFair> factoryObstacle2X8;
	private ArrayList<ObstacleFair> factoryObstacle40X1;
	private ArrayList<ObstacleFair> factoryObstacle1X28;
	private ArrayList<ObstacleFair> factoryObstacle2X2;
	private ArrayList<ObstacleFair> factoryObstacle2X7;
	private ArrayList<ObstacleFair> factoryObstacle2X6;
	private ArrayList<ObstacleFair> factoryObstacle3X4;
	private ArrayList<ObstacleFair> factoryObstacle9X2;
	private ArrayList<ObstacleFair> factoryObstacle3X2;
	private ArrayList<ObstacleFair> factoryObstacle3X7;
	private ArrayList<ObstacleFair> factoryObstacle6X2;
	private ArrayList<ObstacleFair> factoryObstacle4X1;
	private ArrayList<ObstacleFair> factoryObstacle1X5;
	
	private ArrayList<ObstacleFair> factoryObstacle4X4;
	private ArrayList<ObstacleFair> factoryObstacle4X3;
	private ArrayList<ObstacleFair> factoryObstacle6X4;
	private ArrayList<ObstacleFair> factoryObstacle3X5;
	private ArrayList<ObstacleFair> factoryObstacle4X2;
	private ArrayList<ObstacleFair> factoryObstacle2X4;
	
	private ArrayList<ObstacleFair> factoryObstacle3X3;
	
	public ObstacleFactory(ArrayList<Obstacle> obstacleArray){
		
		this.factoryObstacles = obstacleArray;
	
		
	}
	public void produce1X5Obstacle(){
		
		
		factoryObstacle1X5 = new ArrayList<ObstacleFair>();
		factoryObstacle1X5.add(new ObstacleFair(700,440));
		
		
		
		for(int i =0; i < factoryObstacle1X5.size(); i++){
			
			factoryObstacle1X5.get(i).transform1X5();
			factoryObstacles.add(factoryObstacle1X5.get(i));
			
		}
		
	}
	public void produce4X1Obstacle(){
		
		
		factoryObstacle4X1 = new ArrayList<ObstacleFair>();
		factoryObstacle4X1.add(new ObstacleFair(620,520));
		
		
		
		for(int i =0; i < factoryObstacle4X1.size(); i++){
			
			factoryObstacle4X1.get(i).transform4X1();
			factoryObstacles.add(factoryObstacle4X1.get(i));
			
		}
		
	}
	public void produce6X2Obstacle(){
		
		
		factoryObstacle6X2 = new ArrayList<ObstacleFair>();
		factoryObstacle6X2.add(new ObstacleFair(360,440));
		
		
		
		for(int i =0; i < factoryObstacle6X2.size(); i++){
			
			factoryObstacle6X2.get(i).transform6X2();
			factoryObstacles.add(factoryObstacle6X2.get(i));
			
		}
		
	}
	
	public void produce3X7Obstacle(){
		
		
		factoryObstacle3X7 = new ArrayList<ObstacleFair>();
		factoryObstacle3X7.add(new ObstacleFair(480,60));
		
		
		
		for(int i =0; i < factoryObstacle3X7.size(); i++){
			
			factoryObstacle3X7.get(i).transform3X7();
			factoryObstacles.add(factoryObstacle3X7.get(i));
			
		}
		
	}

	public void produce3X2Obstacle(){
		
		
		factoryObstacle3X2 = new ArrayList<ObstacleFair>();
		factoryObstacle3X2.add(new ObstacleFair(340,280));
		factoryObstacle3X2.add(new ObstacleFair(460,320));
		factoryObstacle3X2.add(new ObstacleFair(380,60));
		
		
		for(int i =0; i < factoryObstacle3X2.size(); i++){
			
			factoryObstacle3X2.get(i).transform3X2();
			factoryObstacles.add(factoryObstacle3X2.get(i));
			
		}
		
	}

	public void produce9X2Obstacle(){
		
		
		factoryObstacle9X2 = new ArrayList<ObstacleFair>();
		factoryObstacle9X2.add(new ObstacleFair(340,240));
		factoryObstacle9X2.add(new ObstacleFair(340,360));
		
		
		for(int i =0; i < factoryObstacle9X2.size(); i++){
			
			factoryObstacle9X2.get(i).transform9X2();
			factoryObstacles.add(factoryObstacle9X2.get(i));
			
		}
		
	}
	public void produce2X6Obstacle(){
		
		
		factoryObstacle2X6 = new ArrayList<ObstacleFair>();
		factoryObstacle2X6.add(new ObstacleFair(100,470));
		factoryObstacle2X6.add(new ObstacleFair(520,470));
		factoryObstacle2X6.add(new ObstacleFair(320,440));
		
		
		for(int i =0; i < factoryObstacle2X6.size(); i++){
			
			factoryObstacle2X6.get(i).transform2X6();
			factoryObstacles.add(factoryObstacle2X6.get(i));
			
		}
		
	}
	public void produce2X7Obstacle(){
		
		
		factoryObstacle2X7 = new ArrayList<ObstacleFair>();
		factoryObstacle2X7.add(new ObstacleFair(240,60));
		factoryObstacle2X7.add(new ObstacleFair(340,10));
		
		
		for(int i =0; i < factoryObstacle2X7.size(); i++){
			
			factoryObstacle2X7.get(i).transform2X7();
			factoryObstacles.add(factoryObstacle2X7.get(i));
			
		}
		
	}
	public void produce2X2Obstacle(){
		
		
		factoryObstacle2X2 = new ArrayList<ObstacleFair>();
		factoryObstacle2X2.add(new ObstacleFair(200,60));
		factoryObstacle2X2.add(new ObstacleFair(580,120));
		factoryObstacle2X2.add(new ObstacleFair(640,60));
		factoryObstacle2X2.add(new ObstacleFair(680,140));
		
		for(int i =0; i < factoryObstacle2X2.size(); i++){
			
			factoryObstacle2X2.get(i).transform2X2();
			factoryObstacles.add(factoryObstacle2X2.get(i));
			
		}
		
	}
	
	public void produce1X28Obstacle(){
		
		
		factoryObstacle1X28 = new ArrayList<ObstacleFair>();
		factoryObstacle1X28.add(new ObstacleFair(0,10));
		factoryObstacle1X28.add(new ObstacleFair(790,10));
		
		
		for(int i =0; i < factoryObstacle1X28.size(); i++){
			
			factoryObstacle1X28.get(i).transform1X28();
			factoryObstacles.add(factoryObstacle1X28.get(i));
			
		}
		
	}
	
	public void produce2X8Obstacle(){
		
		
		factoryObstacle2X8 = new ArrayList<ObstacleFair>();
		factoryObstacle2X8.add(new ObstacleFair(100,10));
		factoryObstacle2X8.add(new ObstacleFair(600,220));
		factoryObstacle2X8.add(new ObstacleFair(700,220));
		
		
		for(int i =0; i < factoryObstacle2X8.size(); i++){
			
			factoryObstacle2X8.get(i).transform2X8();
			factoryObstacles.add(factoryObstacle2X8.get(i));
			
		}
		
	}
	public void produce40X1Obstacle(){
		
		
		factoryObstacle40X1 = new ArrayList<ObstacleFair>();
		factoryObstacle40X1.add(new ObstacleFair(0,0));
		factoryObstacle40X1.add(new ObstacleFair(0,590));
		
		
		for(int i =0; i < factoryObstacle40X1.size(); i++){
			
			factoryObstacle40X1.get(i).transform40X1();
			factoryObstacles.add(factoryObstacle40X1.get(i));
			
		}
		
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
		factoryObstacle4X3.add(new ObstacleFair(150,50));
		//factoryObstacle4X3.add(new ObstacleFair(150,75));
		//factoryObstacle4X3.add(new ObstacleFair(150,150));
		//factoryObstacle4X3.add(new ObstacleFair(150,225));
		
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
		
		factoryObstacle2X4.add(new ObstacleFair(420,150));
		factoryObstacle2X4.add(new ObstacleFair(420,250));
		factoryObstacle2X4.add(new ObstacleFair(420,350));
		factoryObstacle2X4.add(new ObstacleFair(550,500));
		
		
		for(int i =0; i < factoryObstacle2X4.size(); i++){
			
			factoryObstacle2X4.get(i).transform2X4();
			factoryObstacles.add(factoryObstacle2X4.get(i));
			
		}
		
	}
	public void produce3X4Obstacle(){
		
		
		factoryObstacle3X4 = new ArrayList<ObstacleFair>();
		
		factoryObstacle3X4.add(new ObstacleFair(200,440));
		
		
		
		for(int i =0; i < factoryObstacle3X4.size(); i++){
			
			factoryObstacle3X4.get(i).transform3X4();
			factoryObstacles.add(factoryObstacle3X4.get(i));
			
		}
		
	}
	public void produce3X3Obstacle(){
		
		
		factoryObstacle3X3 = new ArrayList<ObstacleFair>();
		
		factoryObstacle3X3.add(new ObstacleFair(100,240));
		factoryObstacle3X3.add(new ObstacleFair(200,240));
		
		factoryObstacle3X3.add(new ObstacleFair(100,340));
		factoryObstacle3X3.add(new ObstacleFair(200,340));
		
		factoryObstacle3X3.add(new ObstacleFair(600,420));
		
		
		
		for(int i =0; i < factoryObstacle3X3.size(); i++){
			
			factoryObstacle3X3.get(i).transform3X3();
			factoryObstacles.add(factoryObstacle3X3.get(i));
			
		}
		
	}
	
	public ArrayList<Obstacle> getFactoryObstacleArray(){
		return factoryObstacles;
	}
	
	

}
