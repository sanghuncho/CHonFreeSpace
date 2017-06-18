package presenter;

import java.awt.MouseInfo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import model.CHmodel;
import model.Obstacle;
import model.Point;
import model.dijkstra.ConstantMap;
import model.dijkstra.CostMap;
import model.dijkstra.DijkstraAlgorithm;
import model.dijkstra.Graph;
import model.node.Node;
import model.node.NodeMap;
import util.enums.Property;
import util.math.Vector;
import util.math.Vector2D;
import view.Lobby;

public class LobbyPresenter {
	
	Stage stage;
	Lobby lobbyView;
	Obstacle obs;
	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;
	
	private int numberObs;
	private ArrayList<Obstacle> obstacles;
	private Vector2D size;	
	private Vector startPointNode;
	private Vector goalPointNode;
	private int loop;
	private int loop_map;
	private int loop_viaNode;
	private ConstantMap constantMap;
	private NodeMap nodeMap;
	private boolean applyCH;
	private Thread thread;
	private CostMap costmap;
	private boolean isCheckAll;
	private boolean isCheckShort;
	private boolean isCheckTop3;
	
	private boolean isRadioAll;
	private boolean isRadioCH;
	private boolean isRadioTop3;
	
	private DijkstraAlgorithm dijkstra_head;
	private DijkstraAlgorithm dijkstra_tail;
	
	private Point startPoint;
	private Point endPoint;
	private int[] costOfHomotopy;
	private double[] valueOfTurning;
	private int nodeNumberObstacle;

	private boolean permitChange = false;
	
	public LobbyPresenter(Lobby lobbyView){
		this.lobbyView = lobbyView;
		this.obs=lobbyView.getObstacle();
		this.size = CHmodel.getSizeVector2D();
		/*this.startPointNode = CHmodel.getStartVector2D();
		this.goalPointNode = CHmodel.getGoalVector2D();*/
		this.nodeMap = CHmodel.getNodeMap();
		this.constantMap = new ConstantMap();
		
		this.startPoint = lobbyView.getStartPoint();
		this.endPoint = lobbyView.getEndPoint();
		this.costOfHomotopy = lobbyView.getCostOfHomotopy();
		this.valueOfTurning = lobbyView.getValueOfTurning();
		activate();
	}
	
	public void activate(){
		
		
		/**
		 * if only manual mode is selected,
		 * then the obstacle can be moved by dragging it.
		 * */
		if(CHmodel.getMode().equals("manual")){
			
			numberObs = CHmodel.getNumberObstacle();
			
		}else if(CHmodel.getMode().equals("fair")){
			
			numberObs = 0;
		}else if(CHmodel.getMode().equals("floor")){
			
			numberObs = 0;
		}
		
		
		obstacles = lobbyView.getObstacleList();
			
		for(int i = 0; i < numberObs; i++){
							
			obstacles.get(i).setOnMousePressed(obstacleOnMousePressedEventHandler);
			obstacles.get(i).setOnMouseDragged(obstacleOnMouseDraggedEventHandler);
				//obstacles.get(i).setOnMouseReleased(obstacleOnMouseRelesedEventHandler);			
		}
		
		
		
		
		startPoint.setOnMousePressed(startOnMousePressedEventHandler);
		startPoint.setOnMouseDragged(startOnMouseDraggedEventHandler);
		startPoint.setOnMouseReleased(startOnMouseRelesedEventHandler);
		
		endPoint.setOnMousePressed(endOnMousePressedEventHandler);
		endPoint.setOnMouseDragged(endOnMouseDraggedEventHandler);
		endPoint.setOnMouseReleased(endOnMouseRelesedEventHandler);
		
		
		/**
		 * this runs, if only the via-node button is clicked
		 */
		lobbyView.viaNodeButton.setOnMouseClicked(event -> {
			
			
			this.startPointNode = CHmodel.getStartVector2D();
			this.goalPointNode = CHmodel.getGoalVector2D();
			
			setNodeObstacleProperty(nodeMap);
			setNodeStartProperty(nodeMap);
			setNodeGoalProperty(nodeMap);
			
			this.applyCH = false;
						
			
			/**
			 * set the cost of obstacle, start point and goal point on the constantMap
			 */
			constantMap.setCostOnConstantMap(nodeMap);
			
			loop_viaNode=0;
			while(loop_viaNode < lobbyView.getnumberOfViaNode() ){
			
				lobbyView.createViaNodePoint(size,constantMap.getMap(),loop_viaNode);
				loop_viaNode++;
			}
			
			
            costmap = new CostMap(size, CHmodel.getStartVector2D(),
            		nodeMap, obstacles , constantMap.getMap());
			
			Graph graph = new Graph(nodeMap.getNodes(),costmap.getEdges());
			
			dijkstra_head = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(startPointNode.getX(),startPointNode.getY()),lobbyView); 
			
			dijkstra_tail = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(goalPointNode.getX(),goalPointNode.getY()),lobbyView); 
			
			System.out.println("node size : " + nodeMap.getNodes().size());
            System.out.println("edge size : " + costmap.getEdges().size()/2);
			

		});
		
		/**
		 * this works, if (via-node + CH) button is clicked.
		 */
		lobbyView.contractButton.setOnMouseClicked(event -> {
			
			
			this.startPointNode = CHmodel.getStartVector2D();
			this.goalPointNode = CHmodel.getGoalVector2D();
			
			
			setNodeObstacleProperty(nodeMap);
			setNodeStartProperty(nodeMap);
			setNodeGoalProperty(nodeMap);
			
			
			this.applyCH = true;
			
			/**
			 * set the cost of obstacle, start point and goal point on the constantMap
			 */
			constantMap.setCostOnConstantMap(nodeMap);
			
			loop_viaNode=0;	
			while(loop_viaNode < lobbyView.getnumberOfViaNode() ){
				
				lobbyView.createViaNodePoint(size,constantMap.getMap(),loop_viaNode);
				loop_viaNode++;
			}
			CHmodel.setNumberOfViaNode(lobbyView.getnumberOfViaNode());
			
			loop_map=0;
			long  startTime_contracted = System.currentTimeMillis();
			System.out.println("contracting : " + CHmodel.getNumberContractedCustom());
			while(loop_map < CHmodel.getNumberContractedCustom()){
		
				lobbyView.generateContractedPoint(size,constantMap.getMap());
				loop_map++;
			}
			long endTime_contracted = System.currentTimeMillis();
			long duration_allContracted = (endTime_contracted - startTime_contracted);
			System.out.println("the duration of the contracting : " + duration_allContracted + " miliseconds");
			
			
			
            costmap = new CostMap(size, CHmodel.getStartVector2D(),
        					nodeMap, obstacles , constantMap.getMap());
            
            System.out.println("node size : " + (nodeMap.getNodes().size() - CHmodel.getNumberContractedCustom()));
            System.out.println("edge size : " + (costmap.getEdges().size()/2));
			
			Graph graph = new Graph(nodeMap.getNodes(),costmap.getEdges());
			
			dijkstra_head = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(startPointNode.getX(),startPointNode.getY()),lobbyView); 
			
			dijkstra_tail = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(goalPointNode.getX(),goalPointNode.getY()),lobbyView); 
			
			    
			

		});
		
		
		/**
		 * It starts the searching,if search button is clicked.
		 */
		lobbyView.searchButton.setOnMouseClicked(event -> {
			
			lobbyView.setNumberShortcutEdges(costmap.getEdges());
			this.permitChange = true;
			
			long  startTime_searching = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss.SSS");    
			Date resultdate = new Date(startTime_searching);
			System.out.println("start search time : " + sdf.format(resultdate));
			
			dijkstraForward();
			
			dijkstraBackward();
			
		 /**
		  * here through the checkBox decided whether 
		  * all paths are showed or the representative paths showed.
		  */ 
		 isCheckAll = lobbyView.getCheckBoxAll().isSelected();
		 isCheckShort = lobbyView.getCheckBoxShort().isSelected();
		 isCheckTop3 = lobbyView.getCheckBoxTop3().isSelected();
		 
		 /**
		  * here through the checkBox decided whether 
		  * all paths are showed or the representative paths showed.
		  */ 
		 isRadioAll = lobbyView.getRadioButtonAll().isSelected();
		 isRadioCH = lobbyView.getRadioButtonCH().isSelected();
		 isRadioTop3 = lobbyView.getRadioButtonTop3().isSelected();
		 /**
		  * The representative paths of homotopy class as top3 paths or all paths of homotopy classesare displayed.'
		  * */
		 //if(!isCheckAll){ 
		 if(!isRadioAll){ 
			 
			int firstViaNodeX = lobbyView.getViaNode2D(0).getX();
			int firstViaNodeY = lobbyView.getViaNode2D(0).getY();
			
			int distance_first_x = dijkstra_head.getShortestDistance(nodeMap.get(firstViaNodeX,firstViaNodeY));
			int distance_first_y = dijkstra_tail.getShortestDistance(nodeMap.get(firstViaNodeX,firstViaNodeY));
			
			double turningValueFromHead_f = dijkstra_head.getTurningValue(nodeMap.get(firstViaNodeX,firstViaNodeY));
			double turningValueFromTail_f = dijkstra_tail.getTurningValue(nodeMap.get(firstViaNodeX,firstViaNodeY));
			
			
			costOfHomotopy[0] = (distance_first_x + distance_first_y); 
			valueOfTurning[0] = (turningValueFromHead_f + turningValueFromTail_f);
					
			dijkstra_head.setPath(nodeMap.get(firstViaNodeX,firstViaNodeY));
			
			lobbyView.getListOfPathHead().add(0,dijkstra_head.getPath());
			
			//here should implement the whole distance of path and the turn value of path of the head
			long  firstHomotopy = System.currentTimeMillis();
			SimpleDateFormat sdf_1 = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss.SSS");    
			Date resultdate_1 = new Date(firstHomotopy);
			System.out.println("first homotopy time : " + sdf_1.format(resultdate_1));
			
			dijkstra_tail.setPath(nodeMap.get(firstViaNodeX,firstViaNodeY));
			
			lobbyView.getListOfPathTail().add(0,dijkstra_tail.getPath());
			lobbyView.getPathCategory().add("start");
			
			//here should implement the whole distance of path and the turn value of path of the tail
			
			
			loop=1;
			int totalLoop = lobbyView.getViaNodeSize();			
			while( loop < totalLoop ){
				
				int numberViaNode = loop+1;
				int nextViaNodeX = lobbyView.getViaNode2D(loop).getX();
				int nextViaNodeY = lobbyView.getViaNode2D(loop).getY();
				
				//int distance_next = dijkstra_head.getShortestDistance(nodeMap.get(nextViaNodeX,nextViaNodeY));		
				//System.out.println("distance_ " +numberViaNode + " : "+ distance_next);
				
				int distance_next_x = dijkstra_head.getShortestDistance(nodeMap.get(nextViaNodeX,nextViaNodeY));
				int distance_next_y = dijkstra_tail.getShortestDistance(nodeMap.get(nextViaNodeX,nextViaNodeY));
				int distance_next = (distance_next_x + distance_next_y);  
				
				
				double turningValueFromHead_n = dijkstra_head.getTurningValue(nodeMap.get(nextViaNodeX,nextViaNodeY));
				double turningValueFromTail_n = dijkstra_tail.getTurningValue(nodeMap.get(nextViaNodeX,nextViaNodeY));
				double turningOfValue_next = (turningValueFromHead_n + turningValueFromTail_n);
				
				dijkstra_head.setPath(nodeMap.get(nextViaNodeX,nextViaNodeY));
				
				dijkstra_tail.setPath(nodeMap.get(nextViaNodeX,nextViaNodeY));
				
				Polygon polygon = lobbyView.generatePolygon(lobbyView.getListOfPathHead().get(0),
						lobbyView.getListOfPathTail().get(0),
						dijkstra_head.getPath(),dijkstra_tail.getPath());
				
				/*Polygon polygon = new Polygon();
				
				polygon.getPoints().add((double) 305);
				polygon.getPoints().add((double) 305);
				
				polygon.getPoints().add((double) 325);
				polygon.getPoints().add((double) 275);
				
				polygon.getPoints().add((double) 355);
				polygon.getPoints().add((double) 275);
				
				
				polygon.getPoints().add((double) 355);
				polygon.getPoints().add((double) 255);
				
				polygon.getPoints().add((double) 335);
				polygon.getPoints().add((double) 255);
				
				polygon.getPoints().add((double) 335);
				polygon.getPoints().add((double) 305);
				
				polygon.getPoints().add((double) 305);
				polygon.getPoints().add((double) 305);
				polygon.getPoints().add((double) 355);
				polygon.getPoints().add((double) 275);*/
				
				/**
				 * pathidList contains the list of obstacle-id, which are in the polygon covered.
				 *  */
				ArrayList<Integer> pathIdList = lobbyView.getListPathID(polygon);
				
				//lobbyView.drawPolygon(polygon);
					
				String pathIdString;
				if(pathIdList.size() == 0){
					
					/**
					 * first homotopy class is named as "start" and 
					 * if the path is homotopic with fist via node path, 
					 * then id is defined as "start".*/
					pathIdString = "start";
					
				}
				else{
					/**
					 * the other homotopy class after start homotopy class 
					 * is named as "start123..."
					 */ 
					pathIdString = pathIdToString(pathIdList);
				}
		
				
				/**
				 * The path is classified into the homotopy class.
				 * the evaluation_1st is here implemented.
				 */
				lobbyView.setPathCategory(pathIdString, dijkstra_head.getPath(),
						dijkstra_tail.getPath(),numberViaNode,nextViaNodeX,nextViaNodeY,distance_next,turningOfValue_next);
				
				loop++;
				
			}
			
			/**
			 *the evaluation_3st is implemented.
			 *this shows the duration of from the running of two dijkstra's algorithm to the homotopy test. 
			 */
			long endTime_searching = System.currentTimeMillis();
			long duration_searching = (endTime_searching - startTime_searching);
			long duration_classifying = (endTime_searching - firstHomotopy);
			System.out.println("the duration of the searching and the homotopy test : " + duration_searching + " miliseconds");
			System.out.println("the duration of homotopy test : " + duration_classifying + " miliseconds");
			
		    createHomotopy(lobbyView, costmap);
		    lobbyView.printCostOfHomotopyClass();
		    lobbyView.printValueOfTurning();
		   
		    
		 }
		 
		 /**
		  * All the paths are displayed.
		  * */
		 //if((!isCheckShort) && (isCheckAll)){
		 if((!isRadioCH) && (isRadioAll)){ 
			 
				loop=0;
				int totalLoop = lobbyView.getViaNodeSize();
				
				while( loop < totalLoop ){
									
					int viaNodeX = lobbyView.getViaNode2D(loop).getX();
					int viaNodeY = lobbyView.getViaNode2D(loop).getY();
					
					dijkstra_head.setPath(nodeMap
							.get(viaNodeX,viaNodeY));
					
					dijkstra_tail.setPath(nodeMap
							.get(viaNodeX,viaNodeY));
					
					lobbyView.setAllPaths(dijkstra_head.getPath(),
							dijkstra_tail.getPath());
					
					loop++;
					
				}
				createAllPaths(lobbyView, costmap);
				lobbyView.printCostOfHomotopyClass();
				lobbyView.printValueOfTurning();
			
		 }
		 else{
			 	//error 
		 }
		 
		 
		 
		 lobbyView.getRadioGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			    public void changed(ObservableValue<? extends Toggle> ov,
				        Toggle old_toggle, Toggle new_toggle) {
			    	
				            if(lobbyView.getRadioGroup().getSelectedToggle().equals(lobbyView.getRadioButtonAll())) {
				            	
				            	System.out.println("Remove!!");
				    		    
				    		    lobbyView.removeLane();
				    		    
				    		    createAllPath();
				            }                
				        }
				});
		
		lobbyView.getRadioGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
			        Toggle old_toggle, Toggle new_toggle) {
		    	
			            if(lobbyView.getRadioGroup().getSelectedToggle().equals(lobbyView.getRadioButtonTop3())) {
			            	
			            	System.out.println("Remove!!");
			    		    
			    		    lobbyView.removeLane();
			    		    
			    		    createTop3Path();
			            }                
			        }
			});
		
		lobbyView.getRadioGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
			        Toggle old_toggle, Toggle new_toggle) {
		    	
			            if(lobbyView.getRadioGroup().getSelectedToggle().equals(lobbyView.getRadioButtonCH())) {
			            	
			            	System.out.println("Remove!!");
			    		    
			    		    lobbyView.removeLane();
			    		    
			    		    createHomotopyPath();
			            }                
			        }
			});
		
		
		          
		});
		
		
		/*lobbyView.getRadioGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			    public void changed(ObservableValue<? extends Toggle> ov,
				        Toggle old_toggle, Toggle new_toggle) {
			    	
				            if(lobbyView.getRadioGroup().getSelectedToggle().equals(lobbyView.getRadioButtonAll())) {
				            	
				            	System.out.println("Remove!!");
				    		    
				    		    lobbyView.removeLane();
				    		    
				    		    createAllPath();
				            }                
				        }
				});
		
		lobbyView.getRadioGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
			        Toggle old_toggle, Toggle new_toggle) {
		    	
			            if(lobbyView.getRadioGroup().getSelectedToggle().equals(lobbyView.getRadioButtonTop3())) {
			            	
			            	System.out.println("Remove!!");
			    		    
			    		    lobbyView.removeLane();
			    		    
			    		    createTop3Path();
			            }                
			        }
			});
		
		lobbyView.getRadioGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
			        Toggle old_toggle, Toggle new_toggle) {
		    	
			            if(lobbyView.getRadioGroup().getSelectedToggle().equals(lobbyView.getRadioButtonCH())) {
			            	
			            	System.out.println("Remove!!");
			    		    
			    		    lobbyView.removeLane();
			    		    
			    		    createHomotopyPath();
			            }                
			        }
			});
		*/
		
		
		
	}
	
	
	private void createTop3Path(){
		lobbyView.initiateColorNumber();
		createHomotopy(lobbyView, costmap);
	}
	private void createHomotopyPath(){
		
		lobbyView.initiateColorNumber();
		createHomotopy(lobbyView, costmap);
	}
	private void createAllPath(){
		
		lobbyView.initiateColorNumber();
		loop=0;
		int totalLoop = lobbyView.getViaNodeSize();
		
		while( loop < totalLoop ){
							
			int viaNodeX = lobbyView.getViaNode2D(loop).getX();
			int viaNodeY = lobbyView.getViaNode2D(loop).getY();
			
			dijkstra_head.setPath(nodeMap
					.get(viaNodeX,viaNodeY));
			
			dijkstra_tail.setPath(nodeMap
					.get(viaNodeX,viaNodeY));
			
			lobbyView.setAllPaths(dijkstra_head.getPath(),
					dijkstra_tail.getPath());
			
			loop++;
			
		}
		createAllPaths(lobbyView, costmap);
		
		
	}
	private void dijkstraForward(){
		
		 Task<Void> task1 = new Task<Void>() {
            @Override
            public Void call() throws InterruptedException {
            	
            	dijkstra_head.execute();
            	
    			System.out.println("the execution_1 is the end \n");
    			
    			
    			
                return null ;
            }
		 };
		
         Thread thread1 = new Thread(task1);
         thread1.setDaemon(true);
         thread1.start();
         
         try {
				thread1.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	private void dijkstraBackward(){
		
		 Task<Void> task2 = new Task<Void>() {
            @Override
            public Void call() throws InterruptedException {
            	dijkstra_tail.execute();
    			System.out.println("the execution_2 is the end  \n");

                return null ;
            }
		 };

	    Thread thread2 = new Thread(task2);
	    thread2.setDaemon(true);
	    thread2.start();
		
		try {
			thread2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void createHomotopy(Lobby lobbyView,CostMap costmap){
		        
			lobbyView.createHomotopyLane();
				
			lobbyView.setText(costmap.getEdges().size(),applyCH);
	
	}
	private void createAllPaths(Lobby lobbyView,CostMap costmap){
        
		lobbyView.createAllLane();

		lobbyView.setText(costmap.getEdges().size(),applyCH);

	}
		
	private String pathIdToString(ArrayList<Integer> idList){
		
		
		String idString="";
		for(int i=0; i < idList.size(); i++){
			
			idString = idString + idList.get(i).toString();
				
		}
		
		return idString;
	}

	private void setNodeObstacleProperty(NodeMap nodeMap){
		
		this.nodeNumberObstacle = 0;
		for(Node node : nodeMap.getNodes()){
			
			for (Obstacle obstacle : obstacles) {
		
					if(isNodeInsideObs(node,obstacle)){
						
						node.setProperty(Property.OBSTACLE);
						this.nodeNumberObstacle++;
					}	
			}
			
		}
		CHmodel.setNodeNumberObstacle(this.nodeNumberObstacle);
		
	}
	
	private void setNodeStartProperty(NodeMap nodeMap){
		
			for(Node node : nodeMap.getNodes()){

				if(node.getNodeVector().equals(CHmodel.getStartVector2D())){
				
							node.setProperty(Property.START);
						}	
				}
				
	}
			
	

	private void setNodeGoalProperty(NodeMap nodeMap){
		
		for(Node node : nodeMap.getNodes()){
			
			if(node.getNodeVector().equals(CHmodel.getGoalVector2D())){
				
				
						node.setProperty(Property.GOAL);
					}	
			}
		
	}

	
	private boolean isNodeInsideObs(Node node, Obstacle obstacle){
		
		int nodeGoalX = node.getPosition().getX()*10;
		int nodeGoalY = node.getPosition().getY()*10;
		
		int obsXpos = (int)obstacle.xProperty().get();
		//System.out.println("obsXpos" + obsXpos);
		int obsXposWidth = (int)(obsXpos + (obstacle.getWidth()));
		
		int obsYpos = (int)obstacle.yProperty().get();
		int obsYposWidth = (int)(obsYpos + (obstacle.getHeight()));
		//obsXpos-10
		if( (obsXpos-10 <= nodeGoalX ) && ( nodeGoalX <= obsXposWidth)){ 
			
			if(( obsYpos-10  <= nodeGoalY) && ( nodeGoalY <= obsYposWidth)){
				
				return true;
				
			}
			else{
				return false;
			}
		
		}
		else{
			return false;
		}
		
	}

	
	EventHandler<MouseEvent> obstacleOnMousePressedEventHandler = 
	        new EventHandler<MouseEvent>() {
	 
	        @Override
	        public void handle(MouseEvent t) {
	        	
	            orgSceneX = t.getSceneX()-50;//50
	            orgSceneY = t.getSceneY()-25;//25
	            
		        /*System.out.println("orgSceneX : " + orgSceneX +"\n");	           
		        System.out.println("orgSceneY : " + orgSceneY +"\n");*/
	            
	            orgTranslateX = ((Obstacle)(t.getSource())).getTranslateX();
	            orgTranslateY = ((Obstacle)(t.getSource())).getTranslateY();
	        	
	        }       
	    };
	    
	   
	     
	EventHandler<MouseEvent> obstacleOnMouseDraggedEventHandler = 
	        new EventHandler<MouseEvent>() {
	 
	        @Override
	        public void handle(MouseEvent t) {
	        	
	        	double offsetX = t.getSceneX() - orgSceneX;
	            double offsetY = t.getSceneY() - orgSceneY;
	            double newTranslateX = orgTranslateX;// + offsetX;
	            double newTranslateY = orgTranslateY;// + offsetY;

	            ((Obstacle)(t.getSource())).setTranslateX(newTranslateX);
	            ((Obstacle)(t.getSource())).setTranslateY(newTranslateY);
	            
	            DoubleProperty xPosProperty = new SimpleDoubleProperty(t.getSceneX()-50-(CHmodel.VALUE_OBSTACLE_WIDTH/2));
	            DoubleProperty yPosProperty = new SimpleDoubleProperty(t.getSceneY()-25-(CHmodel.VALUE_OBSTACLE_HEIGHT/2));
	            
	    
		        ((Obstacle)(t.getSource())).xProperty().bind(xPosProperty );
		        ((Obstacle)(t.getSource())).yProperty().bind(yPosProperty );
		        		  
	        }
	        
	    };
	    
	   EventHandler<MouseEvent> obstacleOnMouseRelesedEventHandler = 
		        new EventHandler<MouseEvent>() {
		 
		        @Override
		        public void handle(MouseEvent t) {
		        	
		        DoubleProperty xPosProperty = new SimpleDoubleProperty(t.getSceneX());
		        DoubleProperty yPosProperty = new SimpleDoubleProperty(t.getSceneY());
		        
		        ((Obstacle)(t.getSource())).xProperty().bind(xPosProperty );
		        ((Obstacle)(t.getSource())).yProperty().bind(yPosProperty );
		          double xposition =  ((Obstacle)(t.getSource())).getXPoperty().get();
		          double yposition =  ((Obstacle)(t.getSource())).getYPoperty().get();
		        
		        }
		        
		    };
	  
	    /*EventHandler<MouseEvent> obstacleOnMouseRelesedEventHandler = 
		        new EventHandler<MouseEvent>() {
		 
		        @Override
		        public void handle(MouseEvent t) {
		             
		          double xposition =  ((Obstacle)(t.getSource())).getXPoperty().get();
		          double yposition =  ((Obstacle)(t.getSource())).getYPoperty().get();
		        
		        }
		        
		    };*/
		    
		    EventHandler<MouseEvent> startOnMousePressedEventHandler = 
			        new EventHandler<MouseEvent>() {
			 
			        @Override
			        public void handle(MouseEvent t) {
			        	
			            orgSceneX = t.getSceneX()-50;//50
			            orgSceneY = t.getSceneY()-25;//25
			            
			            orgTranslateX = ((Point)(t.getSource())).getTranslateX();
			            orgTranslateY = ((Point)(t.getSource())).getTranslateY();
			        	
			        }       
			    };
     
			EventHandler<MouseEvent> startOnMouseDraggedEventHandler = 
			        new EventHandler<MouseEvent>() {
			 
			        @Override
			        public void handle(MouseEvent t) {
			        	
			        	double offsetX = t.getSceneX() - orgSceneX;
			            double offsetY = t.getSceneY() - orgSceneY;
			            double newTranslateX = orgTranslateX;// + offsetX;
			            double newTranslateY = orgTranslateY;// + offsetY;

			            ((Point)(t.getSource())).setTranslateX(newTranslateX);
			            ((Point)(t.getSource())).setTranslateY(newTranslateY);
			            
			            DoubleProperty xPosProperty = new SimpleDoubleProperty(t.getSceneX()-50-3);
			            DoubleProperty yPosProperty = new SimpleDoubleProperty(t.getSceneY()-25-3);
			           
				        CHmodel.setStartX( (int)(startPoint.getCenterX()/10)+1 );
				        CHmodel.setStartY( (int)(startPoint.getCenterY()/10)+1 );
				        
				        ((Point)(t.getSource())).centerXProperty().bind(xPosProperty);
				        ((Point)(t.getSource())).centerYProperty().bind(yPosProperty);
				        		  
			        }
			        
			   };
			    
			EventHandler<MouseEvent> startOnMouseRelesedEventHandler = 
				        new EventHandler<MouseEvent>() {
				 
				        @Override
				        public void handle(MouseEvent t) {
				             
				        DoubleProperty xPosProperty = new SimpleDoubleProperty(CHmodel.getStartX());
					    DoubleProperty yPosProperty = new SimpleDoubleProperty(CHmodel.getStartY());
				        ((Point)(t.getSource())).centerXProperty().bind(xPosProperty);
					    ((Point)(t.getSource())).centerYProperty().bind(yPosProperty);
				          			        
				        }
				        
				        
				    };
				    
				    
			    EventHandler<MouseEvent> endOnMousePressedEventHandler = 
				        new EventHandler<MouseEvent>() {
				        @Override
				        public void handle(MouseEvent t) {
				        	
				            orgSceneX = t.getSceneX()-50;//50
				            orgSceneY = t.getSceneY()-25;//25
				            
				            orgTranslateX = ((Point)(t.getSource())).getTranslateX();
				            orgTranslateY = ((Point)(t.getSource())).getTranslateY();
				        	
				        }       
				    };
	     
				EventHandler<MouseEvent> endOnMouseDraggedEventHandler = 
				        new EventHandler<MouseEvent>() {
				 
				        @Override
				        public void handle(MouseEvent t) {
				        	
				        	double offsetX = t.getSceneX() - orgSceneX;
				            double offsetY = t.getSceneY() - orgSceneY;
				            double newTranslateX = orgTranslateX;// + offsetX;
				            double newTranslateY = orgTranslateY;// + offsetY;

				            ((Point)(t.getSource())).setTranslateX(newTranslateX);
				            ((Point)(t.getSource())).setTranslateY(newTranslateY);
				            
				            DoubleProperty xPosProperty = new SimpleDoubleProperty(t.getSceneX()-50-3);
				            DoubleProperty yPosProperty = new SimpleDoubleProperty(t.getSceneY()-25-3);
				            
				            
					        CHmodel.setGoalX( (int)(endPoint.getCenterX()/10)+1);
					        CHmodel.setGoalY( (int)(endPoint.getCenterY()/10)+1);
					        
					        ((Point)(t.getSource())).centerXProperty().bind(xPosProperty);
					        ((Point)(t.getSource())).centerYProperty().bind(yPosProperty);
					        
					        		  
				        }
				        
				    };
				EventHandler<MouseEvent> endOnMouseRelesedEventHandler = 
					        new EventHandler<MouseEvent>() {
					 
					        @Override
					        public void handle(MouseEvent t) {
					             
					        DoubleProperty xPosProperty = new SimpleDoubleProperty(CHmodel.getGoalX());
						    DoubleProperty yPosProperty = new SimpleDoubleProperty(CHmodel.getGoalY());
					        ((Point)(t.getSource())).centerXProperty().bind(xPosProperty);
						    ((Point)(t.getSource())).centerYProperty().bind(yPosProperty);
					          			        
					        }
					        
					    };
			
			

}

