package model;

import java.util.ArrayList;

public class ObstacleFactory {
	
	private ArrayList<Obstacle> factoryObstacles;
	
	private ArrayList<ObstacleFair> factoryObstacle1X5;
	private ArrayList<ObstacleFair> factoryObstacle1X6;
	private ArrayList<ObstacleFair> factoryObstacle1X28;
	private ArrayList<ObstacleFair> factoryObstacle2X8;
	private ArrayList<ObstacleFair> factoryObstacle40X1;
	
	private ArrayList<ObstacleFair> factoryObstacle2X2;
	private ArrayList<ObstacleFair> factoryObstacle2X7;
	private ArrayList<ObstacleFair> factoryObstacle2X6;
	private ArrayList<ObstacleFair> factoryObstacle3X4;
	private ArrayList<ObstacleFair> factoryObstacle9X2;
	private ArrayList<ObstacleFair> factoryObstacle3X2;
	private ArrayList<ObstacleFair> factoryObstacle3X7;
	private ArrayList<ObstacleFair> factoryObstacle6X2;
	private ArrayList<ObstacleFair> factoryObstacle4X1;
	
	
	private ArrayList<ObstacleFair> factoryObstacle4X4;
	private ArrayList<ObstacleFair> factoryObstacle4X3;
	private ArrayList<ObstacleFair> factoryObstacle6X4;
	private ArrayList<ObstacleFair> factoryObstacle3X5;
	private ArrayList<ObstacleFair> factoryObstacle4X2;
	private ArrayList<ObstacleFair> factoryObstacle2X4;
	
	private ArrayList<ObstacleFair> factoryObstacle3X3;
	private ArrayList<ObstacleFair> factoryObstacle6X1;
	
	private ArrayList<ObstacleFair> factoryObstacle10X100;
	private ArrayList<ObstacleFair> factoryObstacle10X60;
	private ArrayList<ObstacleFair> factoryObstacle40X10;
	private ArrayList<ObstacleFair> factoryObstacle10X80;
	private ArrayList<ObstacleFair> factoryObstacle10X50;
	private ArrayList<ObstacleFair> factoryObstacle20X140;
	private ArrayList<ObstacleFair> factoryObstacle10X40;
	private ArrayList<ObstacleFair> factoryObstacle20X10;
	private ArrayList<ObstacleFair> factoryObstacle50X10;
	private ArrayList<ObstacleFair> factoryObstacle90X10;
	private ArrayList<ObstacleFair> factoryObstacle60X10;
	private ArrayList<ObstacleFair> factoryObstacle80X10;
	private ArrayList<ObstacleFair> factoryObstacle100X20;
	private ArrayList<ObstacleFair> factoryObstacle10X20;
	private ArrayList<ObstacleFair> factoryObstacle30X10;
	private ArrayList<ObstacleFair> factoryObstacle10X30;
	
	public ObstacleFactory(ArrayList<Obstacle> obstacleArray){
		
		this.factoryObstacles = obstacleArray;
	
		
	}
	public void produce_10X30_obstacle(){
		
		
		factoryObstacle10X30 = new ArrayList<ObstacleFair>();
		
		factoryObstacle10X30.add(new ObstacleFair(710,150));
		
		
		
	
		for(int i =0; i < factoryObstacle10X30.size(); i++){
			
			factoryObstacle10X30.get(i).transform(10,30);
			
			factoryObstacles.add(factoryObstacle10X30.get(i));
			
		}
		
	}
	public void produce_30X10_obstacle(){
		
		
		factoryObstacle30X10 = new ArrayList<ObstacleFair>();
		
		factoryObstacle30X10.add(new ObstacleFair(600,230));
		factoryObstacle30X10.add(new ObstacleFair(600,260));
		
		
		
	
		for(int i =0; i < factoryObstacle30X10.size(); i++){
			
			factoryObstacle30X10.get(i).transform(30,10);
			
			factoryObstacles.add(factoryObstacle30X10.get(i));
			
		}
		
	}
	public void produce_10X20_obstacle(){
		
		
		factoryObstacle10X20 = new ArrayList<ObstacleFair>();
		
		factoryObstacle10X20.add(new ObstacleFair(500,510));
		
		
		
	
		for(int i =0; i < factoryObstacle10X20.size(); i++){
			
			factoryObstacle10X20.get(i).transform(10,20);
			
			factoryObstacles.add(factoryObstacle10X20.get(i));
			
		}
		
	}
	public void produce_100X20_obstacle(){
		
		
		factoryObstacle100X20 = new ArrayList<ObstacleFair>();
		
		factoryObstacle100X20.add(new ObstacleFair(560,420));
		
		
		
	
		for(int i =0; i < factoryObstacle100X20.size(); i++){
			
			factoryObstacle100X20.get(i).transform(100,20);
			
			factoryObstacles.add(factoryObstacle100X20.get(i));
			
		}
		
	}
	
	public void produce_80X10_obstacle(){
		
		
		factoryObstacle80X10 = new ArrayList<ObstacleFair>();
		
		factoryObstacle80X10.add(new ObstacleFair(420,350));
		factoryObstacle80X10.add(new ObstacleFair(560,350));
		factoryObstacle80X10.add(new ObstacleFair(560,140));
		factoryObstacle80X10.add(new ObstacleFair(710,140));
		factoryObstacle80X10.add(new ObstacleFair(370,140));
		
		
	
		for(int i =0; i < factoryObstacle80X10.size(); i++){
			
			factoryObstacle80X10.get(i).transform(80,10);
			
			factoryObstacles.add(factoryObstacle80X10.get(i));
			
		}
		
	}
	
	public void produce_60X10_obstacle(){
		
		
		factoryObstacle60X10 = new ArrayList<ObstacleFair>();
		
		factoryObstacle60X10.add(new ObstacleFair(240,540));
		factoryObstacle60X10.add(new ObstacleFair(340,540));
		factoryObstacle60X10.add(new ObstacleFair(450,530));
		
	
		for(int i =0; i < factoryObstacle60X10.size(); i++){
			
			factoryObstacle60X10.get(i).transform(60,10);
			
			factoryObstacles.add(factoryObstacle60X10.get(i));
			
		}
		
	}
	public void produce_90X10_obstacle(){
		
		
		factoryObstacle90X10 = new ArrayList<ObstacleFair>();
		
		factoryObstacle90X10.add(new ObstacleFair(270,140));
		factoryObstacle90X10.add(new ObstacleFair(610,500));
		factoryObstacle90X10.add(new ObstacleFair(270,350));
		
	
		for(int i =0; i < factoryObstacle90X10.size(); i++){
			
			factoryObstacle90X10.get(i).transform(90,10);
			
			factoryObstacles.add(factoryObstacle90X10.get(i));
			
		}
		
	}
	
	public void produce_50X10_obstacle(){
		
		
		factoryObstacle50X10 = new ArrayList<ObstacleFair>();
		
		factoryObstacle50X10.add(new ObstacleFair(220,500));
		factoryObstacle50X10.add(new ObstacleFair(310,500));
		factoryObstacle50X10.add(new ObstacleFair(430,400));
		factoryObstacle50X10.add(new ObstacleFair(450,450));
	
		for(int i =0; i < factoryObstacle50X10.size(); i++){
			
			factoryObstacle50X10.get(i).transform(50,10);
			
			factoryObstacles.add(factoryObstacle50X10.get(i));
			
		}
		
	}
	public void produce_20X10_obstacle(){
		
		
		factoryObstacle20X10 = new ArrayList<ObstacleFair>();	
		
		factoryObstacle20X10.add(new ObstacleFair(180,540));
		factoryObstacle20X10.add(new ObstacleFair(180,590));
		factoryObstacle20X10.add(new ObstacleFair(560,0));
	
		for(int i =0; i < factoryObstacle20X10.size(); i++){
			
			factoryObstacle20X10.get(i).transform(20,10);
			
			factoryObstacles.add(factoryObstacle20X10.get(i));
			
		}
		
	}
	public void produce_10X40_obstacle(){
		
		
		factoryObstacle10X40 = new ArrayList<ObstacleFair>();
		
		factoryObstacle10X40.add(new ObstacleFair(200,10));
		factoryObstacle10X40.add(new ObstacleFair(260,140));
	
		for(int i =0; i < factoryObstacle10X40.size(); i++){
			
			factoryObstacle10X40.get(i).transform(10,40);
			
			factoryObstacles.add(factoryObstacle10X40.get(i));
			
		}
		
	}
	public void produce_20X140_obstacle(){
		
		
		factoryObstacle20X140 = new ArrayList<ObstacleFair>();
		
		factoryObstacle20X140.add(new ObstacleFair(180,180));
	
		for(int i =0; i < factoryObstacle20X140.size(); i++){
			
			factoryObstacle20X140.get(i).transform(20,140);
			factoryObstacles.add(factoryObstacle20X140.get(i));
			
		}
		
	}
	
	public void produce_10X50_obstacle(){
		
		
		factoryObstacle10X50 = new ArrayList<ObstacleFair>();		
		factoryObstacle10X50.add(new ObstacleFair(110,540));
	
		for(int i =0; i < factoryObstacle10X50.size(); i++){
			
			factoryObstacle10X50.get(i).transform(10,50);
			factoryObstacles.add(factoryObstacle10X50.get(i));
			
		}
		
	}
	
	public void produce_10X80_obstacle(){
		
		factoryObstacle10X80 = new ArrayList<ObstacleFair>();

		
		factoryObstacle10X80.add(new ObstacleFair(0,510));
		factoryObstacle10X80.add(new ObstacleFair(180,380));
		factoryObstacle10X80.add(new ObstacleFair(190,380));
		
		factoryObstacle10X80.add(new ObstacleFair(500,140));
		
		factoryObstacle10X80.add(new ObstacleFair(710,220));
		factoryObstacle10X80.add(new ObstacleFair(710,340));
		
		factoryObstacle10X80.add(new ObstacleFair(360,140));
		factoryObstacle10X80.add(new ObstacleFair(360,220));
		
		factoryObstacle10X80.add(new ObstacleFair(360,350));
		factoryObstacle10X80.add(new ObstacleFair(360,430));
		
		
		
		for(int i =0; i < factoryObstacle10X80.size(); i++){
			
			factoryObstacle10X80.get(i).transform(10,80);
			factoryObstacles.add(factoryObstacle10X80.get(i));
			
		}
		
	}

	public void produce_40X10_obstacle(){
		
		factoryObstacle40X10 = new ArrayList<ObstacleFair>();

		
		factoryObstacle40X10.add(new ObstacleFair(0,500));
		factoryObstacle40X10.add(new ObstacleFair(80,500));
		factoryObstacle40X10.add(new ObstacleFair(120,500));
		factoryObstacle40X10.add(new ObstacleFair(540,50));
		
		factoryObstacle40X10.add(new ObstacleFair(640,40));
		factoryObstacle40X10.add(new ObstacleFair(640,50));
		factoryObstacle40X10.add(new ObstacleFair(640,60));
		factoryObstacle40X10.add(new ObstacleFair(640,70));
		
		factoryObstacle40X10.add(new ObstacleFair(680,20));
		factoryObstacle40X10.add(new ObstacleFair(680,30));
		factoryObstacle40X10.add(new ObstacleFair(680,40));
		factoryObstacle40X10.add(new ObstacleFair(680,50));
		
		factoryObstacle40X10.add(new ObstacleFair(720,50));
		
		factoryObstacle40X10.add(new ObstacleFair(710,450));
		factoryObstacle40X10.add(new ObstacleFair(750,450));
		
		factoryObstacle40X10.add(new ObstacleFair(420,50));
		factoryObstacle40X10.add(new ObstacleFair(460,50));
		
		factoryObstacle40X10.add(new ObstacleFair(280,50));
		factoryObstacle40X10.add(new ObstacleFair(320,50));
		
		factoryObstacle40X10.add(new ObstacleFair(200,50));
		factoryObstacle40X10.add(new ObstacleFair(500,500));
		
		for(int i =0; i < factoryObstacle40X10.size(); i++){
			
			factoryObstacle40X10.get(i).transform(40,10);
			factoryObstacles.add(factoryObstacle40X10.get(i));
			
		}
		
	}
	public void produce_10X60_obstacle(){
		
		factoryObstacle10X60 = new ArrayList<ObstacleFair>();

		
		factoryObstacle10X60.add(new ObstacleFair(110,40));
		factoryObstacle10X60.add(new ObstacleFair(110,170));
		factoryObstacle10X60.add(new ObstacleFair(110,310));
		factoryObstacle10X60.add(new ObstacleFair(110,410));
		factoryObstacle10X60.add(new ObstacleFair(170,540));
		factoryObstacle10X60.add(new ObstacleFair(580,0));
		factoryObstacle10X60.add(new ObstacleFair(630,180));
		factoryObstacle10X60.add(new ObstacleFair(630,260));
		factoryObstacle10X60.add(new ObstacleFair(600,500));
		factoryObstacle10X60.add(new ObstacleFair(420,400));
		factoryObstacle10X60.add(new ObstacleFair(500,400));
		factoryObstacle10X60.add(new ObstacleFair(440,530));
		
		for(int i =0; i < factoryObstacle10X60.size(); i++){
			
			factoryObstacle10X60.get(i).transform(10,60);
			factoryObstacles.add(factoryObstacle10X60.get(i));
			
		}
		
	}
	public void produce_10X100_obstacle(){
		
		factoryObstacle10X100 = new ArrayList<ObstacleFair>();

		
		factoryObstacle10X100.add(new ObstacleFair(0,270));
		factoryObstacle10X100.add(new ObstacleFair(500,260));
		factoryObstacle10X100.add(new ObstacleFair(260,400));
		factoryObstacle10X100.add(new ObstacleFair(550,260));
		
		for(int i =0; i < factoryObstacle10X100.size(); i++){
			
			factoryObstacle10X100.get(i).transform(10,100);
			factoryObstacles.add(factoryObstacle10X100.get(i));
			
		}
		
	}
	public void produce1X5Obstacle(){
		
		
		factoryObstacle1X5 = new ArrayList<ObstacleFair>();
			
		factoryObstacle1X5.add(new ObstacleFair(700,440));
	
		
		
		for(int i =0; i < factoryObstacle1X5.size(); i++){
			
			factoryObstacle1X5.get(i).transform1X5();
			factoryObstacles.add(factoryObstacle1X5.get(i));
			
		}
		
	}
	public void produce1X6Obstacle(){
		
		
		factoryObstacle1X6 = new ArrayList<ObstacleFair>();
		
		factoryObstacle1X6.add(new ObstacleFair(0,0));
		factoryObstacle1X6.add(new ObstacleFair(0,10));
		factoryObstacle1X6.add(new ObstacleFair(0,140));
		factoryObstacle1X6.add(new ObstacleFair(0,380));
		
		factoryObstacle1X6.add(new ObstacleFair(790,0));
		factoryObstacle1X6.add(new ObstacleFair(790,120));
		factoryObstacle1X6.add(new ObstacleFair(790,240));
		factoryObstacle1X6.add(new ObstacleFair(790,360));
		factoryObstacle1X6.add(new ObstacleFair(790,480));
		
		factoryObstacle1X6.add(new ObstacleFair(260,240));
		factoryObstacle1X6.add(new ObstacleFair(550,140));
		
		
		for(int i =0; i < factoryObstacle1X6.size(); i++){
			
			factoryObstacle1X6.get(i).transform1X6();
			factoryObstacles.add(factoryObstacle1X6.get(i));
			
		}
		
	}
	
	
	
	public void produce6X1Obstacle(){
		
		
		factoryObstacle6X1 = new ArrayList<ObstacleFair>();
		
		factoryObstacle6X1.add(new ObstacleFair(0,0));
		factoryObstacle6X1.add(new ObstacleFair(0,130));
		factoryObstacle6X1.add(new ObstacleFair(0,260));
		factoryObstacle6X1.add(new ObstacleFair(0,370));
		factoryObstacle6X1.add(new ObstacleFair(0,590));
		
		factoryObstacle6X1.add(new ObstacleFair(200,0));
		factoryObstacle6X1.add(new ObstacleFair(320,0));
		factoryObstacle6X1.add(new ObstacleFair(440,0));
		
		factoryObstacle6X1.add(new ObstacleFair(200,590));
		factoryObstacle6X1.add(new ObstacleFair(320,590));
		factoryObstacle6X1.add(new ObstacleFair(440,590));
		factoryObstacle6X1.add(new ObstacleFair(560,590));
		
		
		for(int i =0; i < factoryObstacle6X1.size(); i++){
			
			factoryObstacle6X1.get(i).transform6X1();
			factoryObstacles.add(factoryObstacle6X1.get(i));
			
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
