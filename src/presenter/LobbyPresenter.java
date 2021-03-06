package presenter;

import java.awt.MouseInfo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

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
import model.node.NodeMapHandler;
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
	private Point startViaNode;
	private int[] costOfHomotopy;
	private double[] valueOfTurning;
	private int nodeNumberObstacle;

	private boolean permitChange = false;
	
	Thread thread1;
	Thread thread2;
	Thread thread3;
	Thread thread4;
	
	public LobbyPresenter(Lobby lobbyView){
		this.lobbyView = lobbyView;
		this.obs=lobbyView.getObstacle();
		this.size = CHmodel.getSizeVector2D();
		this.nodeMap = CHmodel.getNodeMap();
		this.constantMap = new ConstantMap();
		this.startPoint = lobbyView.getStartPoint();
		this.endPoint = lobbyView.getEndPoint();
		this.startViaNode = lobbyView.getStartViaNode();
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
		
		/**
		 * get the list of obstacles.*/
		obstacles = lobbyView.getObstacleList();
		
		/**
		 * the obstacle can be moved by dragging.*/	
		for(int i = 0; i < numberObs; i++){
							
			obstacles.get(i).setOnMousePressed(obstacleOnMousePressedEventHandler);
			obstacles.get(i).setOnMouseDragged(obstacleOnMouseDraggedEventHandler);
							
		}
		
		
		
		/**
		 * the start, goal, vianode-stat points can be moved by dragging.*/	
		startPoint.setOnMousePressed(startOnMousePressedEventHandler);
		startPoint.setOnMouseDragged(startOnMouseDraggedEventHandler);
		startPoint.setOnMouseReleased(startOnMouseRelesedEventHandler);
		
		endPoint.setOnMousePressed(endOnMousePressedEventHandler);
		endPoint.setOnMouseDragged(endOnMouseDraggedEventHandler);
		endPoint.setOnMouseReleased(endOnMouseRelesedEventHandler);
		
		startViaNode.setOnMousePressed(startViaNodeOnMousePressedEventHandler);
		startViaNode.setOnMouseDragged(startViaNodeOnMouseDraggedEventHandler);
		startViaNode.setOnMouseReleased(startViaNodeOnMouseRelesedEventHandler);
		
		
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
			
			
			/**
			 * the order of via node is random order
			 * */
			if(CHmodel.getRadioButtonRandom().isSelected()){
				
				createViaNodeRandom();
				
			}
			/**
			 * the order of generating via node is enumeration order
			 * */
			else if(CHmodel.getRadioButtonEnum().isSelected()){
				
				createViaNodeEnumerate();
				
			}
			
            costmap = new CostMap(size, CHmodel.getStartVector2D(),
            		nodeMap, obstacles , constantMap.getMap());
			
			Graph graph = new Graph(nodeMap.getNodes(),costmap.getEdges());
			
			dijkstra_head = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(startPointNode.getX(),startPointNode.getY()),lobbyView); 
			
			dijkstra_tail = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(goalPointNode.getX(),goalPointNode.getY()),lobbyView); 
			

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
			 * contraction hierarchies randomly -> via node enumeration
			 * */
			
		/**************************************************************************/
			constantMap.setCostOnConstantMap(nodeMap);
			
			generateContractedNodeRandom();
			
			/**
			 * the order of via node is random order
			 * */
			if(CHmodel.getRadioButtonRandom().isSelected()){
				
				createViaNodeRandom();
				
			}
			/**
			 * the order of generating via node is enumeration order
			 * */
			else if(CHmodel.getRadioButtonEnum().isSelected()){
				
				createViaNodeEnumerate();
				
			}
			
		
			/**
			 * the order of generating contracted node is enumeration order
			 * */
			//generateContractedNodeEnum();

			
           costmap = new CostMap(size, CHmodel.getStartVector2D(),
        					nodeMap, obstacles , constantMap.getMap());
            
            /*System.out.println("node size : " + (nodeMap.getNodes().size() - CHmodel.getNumberContractedCustom()));
            System.out.println("edge size : " + (costmap.getEdges().size()/2));*/
			
			Graph graph = new Graph(nodeMap.getNodes(),costmap.getEdges());
			
			dijkstra_head = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(startPointNode.getX(),startPointNode.getY()),lobbyView); 
			
			dijkstra_tail = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(goalPointNode.getX(),goalPointNode.getY()),lobbyView);
			
			    
			

		});
		
		//*******************  SEARCH BUTTON  *******************************************************
		/**
		 * It starts the searching,if search button is clicked.
		 */
		
		lobbyView.searchButton.setOnMouseClicked(event -> {
			
			lobbyView.setNumberShortcutEdges(costmap.getEdges());
			this.permitChange = true;
		
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
		 
		 if(!isRadioAll){ 
			 
			int firstViaNodeX = lobbyView.getViaNode2D(0).getX();
			int firstViaNodeY = lobbyView.getViaNode2D(0).getY();
			
			int distance_first_x = dijkstra_head.getShortestDistance(nodeMap.get(firstViaNodeX,firstViaNodeY));
			int distance_first_y = dijkstra_tail.getShortestDistance(nodeMap.get(firstViaNodeX,firstViaNodeY));
			
			double turningValueFromHead_f = dijkstra_head.getTurningValue(nodeMap.get(firstViaNodeX,firstViaNodeY));
			double turningValueFromTail_f = dijkstra_tail.getTurningValue(nodeMap.get(firstViaNodeX,firstViaNodeY));
			Node prod_node_head_f = dijkstra_head.getPredecessors(nodeMap.get(firstViaNodeX,firstViaNodeY));
			Node prod_node_tail_f = dijkstra_tail.getPredecessors(nodeMap.get(firstViaNodeX,firstViaNodeY));
			double turningValue_ViaNode_f = getTurningValue_ViaNode(prod_node_head_f, 
												nodeMap.get(firstViaNodeX,firstViaNodeY), prod_node_tail_f); 
			
			
			costOfHomotopy[0] = (distance_first_x + distance_first_y); 
			valueOfTurning[0] = (turningValueFromHead_f + turningValueFromTail_f + turningValue_ViaNode_f );
					
			dijkstra_head.setPath(nodeMap.get(firstViaNodeX,firstViaNodeY));
			
			lobbyView.getListOfPathHead().add(0,dijkstra_head.getPath());
			
			dijkstra_tail.setPath(nodeMap.get(firstViaNodeX,firstViaNodeY));
			
			lobbyView.getListOfPathTail().add(0,dijkstra_tail.getPath());
			lobbyView.getPathCategory().add("start");
			
			
			loop=1;
			int totalLoop = lobbyView.getViaNodeSize();			
			while( loop < totalLoop ){
				
				int numberViaNode = loop+1;
				int nextViaNodeX = lobbyView.getViaNode2D(loop).getX();
				int nextViaNodeY = lobbyView.getViaNode2D(loop).getY();
				
				int distance_next_x = dijkstra_head.getShortestDistance(nodeMap.get(nextViaNodeX,nextViaNodeY));
				int distance_next_y = dijkstra_tail.getShortestDistance(nodeMap.get(nextViaNodeX,nextViaNodeY));
				int distance_next = (distance_next_x + distance_next_y);  
				
				Node prod_node_head_n = dijkstra_head.getPredecessors(nodeMap.get(nextViaNodeX,nextViaNodeY));
				Node prod_node_tail_n = dijkstra_tail.getPredecessors(nodeMap.get(nextViaNodeX,nextViaNodeY));
				double turningValue_ViaNode_n = getTurningValue_ViaNode(prod_node_head_n,
													nodeMap.get(nextViaNodeX,nextViaNodeY), prod_node_tail_n); 
				
				double turningValueFromHead_n = dijkstra_head.getTurningValue(nodeMap.get(nextViaNodeX,nextViaNodeY));
				double turningValueFromTail_n = dijkstra_tail.getTurningValue(nodeMap.get(nextViaNodeX,nextViaNodeY));
				double turningOfValue_next = (turningValueFromHead_n + turningValueFromTail_n + turningValue_ViaNode_n);
				
				dijkstra_head.setPath(nodeMap.get(nextViaNodeX,nextViaNodeY));
				
				dijkstra_tail.setPath(nodeMap.get(nextViaNodeX,nextViaNodeY));
				
				Polygon polygon = lobbyView.generatePolygon(lobbyView.getListOfPathHead().get(0),
						lobbyView.getListOfPathTail().get(0),
						dijkstra_head.getPath(),dijkstra_tail.getPath());
				
			
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
				 * 
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
		
		    createHomotopy(lobbyView, costmap);
		   // lobbyView.printCostOfHomotopyClass();
		    //lobbyView.printValueOfTurning();
		    
		 }
		 
		 /**
		  * All the paths are displayed.
		  * */
		
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
	
	}
	private double getTurningValue_ViaNode(Node pastNode, Node node ,Node target){
  		
  		int angle_PastNode_Node = getAngle(pastNode,node);
  		
  		int angle_Target_Node = getAngle(target,node);
  		
  		int angle = angle_PastNode_Node - angle_Target_Node;
  		
  		double turning = 100;
  		
  		if (angle < 0) {
			angle += 360;
		}
  		
	  		switch(angle){
	  		
		  		case 0 : 
		  			turning =  0;
		  			break;
		  		case 45 :
		  			turning =  0.5;
		  			break;
		  		case 90 :
		  			turning =  1;
		  			break;
		  		case 135 :
		  			turning =  0.5;
		  			break;
		  		case 180 :
		  			turning =  0;
		  			break;
		  		case 225 :
		  			turning =  0.5;
		  			break;
		  		case 270 :
		  			turning = 1.0;
		  			break;
		  		case 315 :
		  			turning = 0.5;
		  			break;
		  		case 360 :
		  			turning = 0;
		  			break;
			
  		}
	  	return turning;
  	
  		
  	}
	private int getAngle(Node node, Node target){
  		
  		double nodeX = node.getPosition().getX();
  		double nodeY = node.getPosition().getY();
  		
  		double targetX = target.getPosition().getX();
  		double targetY = target.getPosition().getY();
  				
  		double theta =Math.atan2((targetY - nodeY),(targetX - nodeX));
  		
  		theta += Math.PI / 2.0;
  		
  		int angle = (int) Math.toDegrees(theta);
  		
  		if (angle < 0) {
			angle += 360;
		}
  		
  		return angle;
			
  	}
	
	private void generateContractedNodeRandom(){
		
		loop_map=0;
		while(loop_map < CHmodel.getNumberContractedCustom()){
	
			lobbyView.generateContractedPoint_randomization(size,constantMap.getMap());
			loop_map++;
			
		}
		
	}
	private void generateContractedNodeEnum(){
		
		constantMap.setCostOnConstantMap(nodeMap);
		int row = constantMap.getRow();
		int column = constantMap.getColumn();
		int actual_default_node = row*column - constantMap.getSizeOfObstacleNode() - 2*(row+column) - 4;
		int distance_contractedNode =  (int) Math.ceil( (actual_default_node / CHmodel.getNumberContractedCustom()+0.5));
		
		
		loop_map=0;
		int act_row = 0;
		int act_column = 0;
		int hop = distance_contractedNode;
		
		while((loop_map < CHmodel.getNumberContractedCustom()) && (act_row < row)){
			
			if(!lobbyView.insideObstacle(act_column, act_row, constantMap.getMap()) 
					&& !lobbyView.isEdgeOfMap(act_column, act_row, constantMap.getMap()) 
						&& hop == (distance_contractedNode) ){
				
				lobbyView.generateContractedPoint_enumeration(act_column, act_row, constantMap.getMap());
				loop_map++;
				System.out.println("contracted node : " + act_column + ", " + act_row);
				act_column = act_column + 1;
				
				if(act_column == column-1){
					act_column = 0;
					act_row++;
				}
				hop = 0;
			
			}
			else if(!lobbyView.insideObstacle(act_column, act_row, constantMap.getMap()) 
					&& !lobbyView.isEdgeOfMap(act_column, act_row, constantMap.getMap()) 
						&& hop != (distance_contractedNode) ){
				
				act_column = act_column + 1;
				
				if(act_column == column-1){
					act_column = 0;
					act_row++;
				}
				hop++;
				
			}
			else if(lobbyView.insideObstacle(act_column, act_row, constantMap.getMap())
					|| lobbyView.isEdgeOfMap(act_column, act_row, constantMap.getMap())){
				
				act_column = act_column + 1;
				
				if(act_column == column-1){
					act_column = 0;
					act_row++;
				}
				
				
			}
		}
		
		
	}
	private void createViaNodeRandom(){
		
		loop_viaNode=0;
		while(loop_viaNode < lobbyView.getnumberOfViaNode() ){
		
			lobbyView.createViaNodePoint_randomization(size,constantMap.getMap(),loop_viaNode);
			loop_viaNode++;
		}
		CHmodel.setNumberOfViaNode(lobbyView.getnumberOfViaNode());
	}
	private void createViaNodeEnumerate(){
		
		int row = constantMap.getRow();
		int column = constantMap.getColumn();
		int actual_default_node = (int) (row*column - constantMap.getSizeOfObstacleNode()  
										- ((row-1)*(column-1)-constantMap.getSizeOfObstacleNode()-2)*0.9);
		
		
		int act_row = (CHmodel.getStartViaNodeY()-5)/10;
		int act_column = (CHmodel.getStartViaNodeX()-5)/10;
		
		
		int distance_viaNode = 0;
		int hop = 0;
		/*if(CHmodel.getRadioButtonAut().isSelected()){
			
			distance_viaNode = (int) Math.ceil( (actual_default_node / lobbyView.getnumberOfViaNode()));
			hop = distance_viaNode;
			
		}else if(CHmodel.getRadioButtonMan().isSelected()){
			distance_viaNode = CHmodel.getViaNodeDistanceManual();
			hop = distance_viaNode;
		}*/
		distance_viaNode = CHmodel.getViaNodeDistanceManual();
		hop = distance_viaNode;
		
		loop_viaNode=0;
		while((loop_viaNode < lobbyView.getnumberOfViaNode()) && (act_row < row) ){ 
			
			/**
			  * insideObstacle method check whether the point is located in the obstacle or in start-, goal point.
			  * */  		 
			if(!lobbyView.insideObstacle(act_column, act_row, constantMap.getMap()) 
					&& !lobbyView.isContractedNode(act_column, act_row, constantMap.getMap())
						&& hop == (distance_viaNode) ){
				
				lobbyView.createViaNodePoint_enumeration(act_column, act_row, constantMap.getMap());
				
				loop_viaNode++;
				
				act_column = act_column + 1;
				
				if(act_column >= column){
					act_column = 0;
					act_row++;
				}
				hop = 0;
				
			}
			else if(!lobbyView.insideObstacle(act_column, act_row, constantMap.getMap()) 
					&& !lobbyView.isContractedNode(act_column, act_row, constantMap.getMap())
						&& hop != (distance_viaNode) ){
				
				act_column = act_column + 1;
				
				if(act_column >= column){
					act_column = 0;
					act_row++;
				}
				hop++;
				
			}
			else if(lobbyView.insideObstacle(act_column, act_row, constantMap.getMap())
					||  lobbyView.isContractedNode(act_column, act_row, constantMap.getMap())){
				
				act_column = act_column + 1;
				
				if(act_column >= column){
					act_column = 0;
					act_row++;
				}
				
				
			}
			
		}
		
		CHmodel.setNumberOfViaNode(lobbyView.getnumberOfViaNode());
		
	}
	private void autoEvaluation(int loopForTest){
		
		
		getThreadForward().interrupt();
		getThreadBackward().interrupt();
		int times = 0;
		
		while(times < loopForTest){
			
			lobbyView.removeLane();
			
			if(CHmodel.getMode().equals("manual")){
				
				numberObs = CHmodel.getNumberObstacle();
				
			}else if(CHmodel.getMode().equals("fair")){
				
				numberObs = 0;
				
			}else if(CHmodel.getMode().equals("floor")){
				
				numberObs = 0;
			}
			
			
			obstacles = lobbyView.getObstacleList();
					
			
			this.startPointNode = CHmodel.getStartVector2D();
			this.goalPointNode = CHmodel.getGoalVector2D();
			
			NodeMapHandler handler = new NodeMapHandler(CHmodel.getSizeVector2D());
			NodeMap nodeMapT = handler.getNodeMap();
			setNodeObstacleProperty(nodeMapT);
			setNodeStartProperty(nodeMapT);
			setNodeGoalProperty(nodeMapT);
			
			//this.applyCH = false;
			this.applyCH = true;
			
			ConstantMap constantMapT = new ConstantMap();
			constantMapT.setCostOnConstantMap(nodeMapT);
			
			if(applyCH){
				
				int loop_map_T = 0;
				
				
				while(loop_map_T < CHmodel.getNumberContractedCustom()){
			
					lobbyView.generateContractedPoint_randomization(size, constantMapT.getMap());
					loop_map_T++;
				}
			}
			
			int row = constantMap.getRow();
			int column = constantMap.getColumn();
			int actual_default_node = (int) (row*column - constantMap.getSizeOfObstacleNode() 
					- ((row-1)*(column-1)-constantMap.getSizeOfObstacleNode()-2)*0.9);
			int distance_viaNode =  (int) Math.ceil( (actual_default_node / lobbyView.getnumberOfViaNode())-0.5);
			
			
			loop_viaNode=0;
			int act_row = 0;
			int act_column = 0;
			int hop = distance_viaNode;
			ArrayList<Point> viaNodes_T = new ArrayList<Point>();
			while((loop_viaNode < lobbyView.getnumberOfViaNode()) && (act_row < row) ){
				
				
				if(!lobbyView.insideObstacle(act_column, act_row, constantMap.getMap()) 
							&&!lobbyView.isContractedNode(act_column, act_row, constantMap.getMap())
								&& hop == (distance_viaNode) ){
					
					lobbyView.createViaNodePoint_enumeration_T(act_column, act_row, constantMap.getMap(), viaNodes_T);
					
					loop_viaNode++;
					
					act_column = act_column + 1;
					
					if(act_column >= column-1){
						act_column = 0;
						act_row++;
					}
					hop = 0;
					
				}
				else if(!lobbyView.insideObstacle(act_column, act_row, constantMap.getMap()) 
						&&!lobbyView.isContractedNode(act_column, act_row, constantMap.getMap())
							&& hop != (distance_viaNode) ){
					
					act_column = act_column + 1;
					
					if(act_column >= column-1){
						act_column = 0;
						act_row++;
					}
					hop++;
					
				}
				else if(lobbyView.insideObstacle(act_column, act_row, constantMap.getMap())
						 || lobbyView.isContractedNode(act_column, act_row, constantMap.getMap())){
					
					act_column = act_column + 1;
					
					if(act_column >= column-1){
						act_column = 0;
						act_row++;
					}
					
					
				}
				
			}
			CHmodel.setNumberOfViaNode(lobbyView.getnumberOfViaNode());
			
			
			
			/**
			 * random via node
			 * */
			/*loop_viaNode=0;
			ArrayList<Point> viaNodes_T = new ArrayList<Point>();
			while(loop_viaNode < lobbyView.getnumberOfViaNode() ){
			
				lobbyView.createViaNodePoint_T(size,constantMapT.getMap(),loop_viaNode,viaNodes_T);
				loop_viaNode++;
				
			}*/
			
			
			
			CostMap costMapT  = new CostMap(size, CHmodel.getStartVector2D(),
            		nodeMapT, obstacles , constantMapT.getMap());
			
			Graph graphT = new Graph(nodeMapT.getNodes(),costMapT.getEdges());
			
			DijkstraAlgorithm dijkstra_head_T = new DijkstraAlgorithm(graphT, obstacles,
					nodeMapT.get(startPointNode.getX(),startPointNode.getY()),lobbyView); 
			
			DijkstraAlgorithm dijkstra_tail_T = new DijkstraAlgorithm(graphT, obstacles,
					nodeMapT.get(goalPointNode.getX(),goalPointNode.getY()),lobbyView); 
			
			this.permitChange = true;
			
			long  startTime_searching = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss.SSS");    
			Date resultdate = new Date(startTime_searching);
			System.out.println("start search time : " + sdf.format(resultdate));
			
			dijkstraForward_T(dijkstra_head_T);
			
			dijkstraBackward_T(dijkstra_tail_T);
			
			
			int firstViaNodeX = getViaNode2D_T(0,viaNodes_T).getX();
			int firstViaNodeY = getViaNode2D_T(0,viaNodes_T).getY();
			
			int distance_first_x = dijkstra_head_T.getShortestDistance(nodeMapT.get(firstViaNodeX,firstViaNodeY));		
			int distance_first_y = dijkstra_tail_T.getShortestDistance(nodeMapT.get(firstViaNodeX,firstViaNodeY));
					
			double turningValueFromHead_f = dijkstra_head_T.getTurningValue(nodeMapT.get(firstViaNodeX,firstViaNodeY));
			double turningValueFromTail_f = dijkstra_tail_T.getTurningValue(nodeMapT.get(firstViaNodeX,firstViaNodeY));
			
					
			int[] costOfHomotopy_T = new int[50];
			double[] valueOfTurning_T = new double[50];
					
			costOfHomotopy_T[0] = (distance_first_x + distance_first_y); 
			valueOfTurning_T[0] = (turningValueFromHead_f + turningValueFromTail_f);
							
			dijkstra_head_T.setPath(nodeMapT.get(firstViaNodeX,firstViaNodeY));
					
			ArrayList<LinkedList<Node>> listOfPathHead_T = new ArrayList<LinkedList<Node>>();
			listOfPathHead_T.add(0,dijkstra_head_T.getPath());
					
			long  firstHomotopy = System.currentTimeMillis();
			SimpleDateFormat sdf_1 = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss.SSS");    
			Date resultdate_1 = new Date(firstHomotopy);
			System.out.println("first homotopy time : " + sdf_1.format(resultdate_1));
					
			dijkstra_tail_T.setPath(nodeMapT.get(firstViaNodeX, firstViaNodeY));
			ArrayList<LinkedList<Node>> listOfPathTail_T = new ArrayList<LinkedList<Node>>();
			listOfPathTail_T.add(0,dijkstra_tail_T.getPath());
			
			ArrayList<String> listOfPathCategory_T = new ArrayList<String>();
			listOfPathCategory_T.add("start");
			int totalLoop_T = viaNodes_T.size();
			
			
			loop=1;
			while( loop < totalLoop_T ){
				
				int numberViaNode = loop+1;
				
				int nextViaNodeX = getViaNode2D_T(loop,viaNodes_T).getX();
				int nextViaNodeY = getViaNode2D_T(loop,viaNodes_T).getY();
				
			
				int distance_next_x = dijkstra_head_T.getShortestDistance(nodeMapT.get(nextViaNodeX,nextViaNodeY));
				int distance_next_y = dijkstra_tail_T.getShortestDistance(nodeMapT.get(nextViaNodeX,nextViaNodeY));
				int distance_next = (distance_next_x + distance_next_y);  
				
				
				double turningValueFromHead_n = dijkstra_head_T.getTurningValue(nodeMapT.get(nextViaNodeX,nextViaNodeY));
				double turningValueFromTail_n = dijkstra_tail_T.getTurningValue(nodeMapT.get(nextViaNodeX,nextViaNodeY));
				double turningOfValue_next = (turningValueFromHead_n + turningValueFromTail_n);
				
				dijkstra_head_T.setPath(nodeMapT.get(nextViaNodeX,nextViaNodeY));
				
				dijkstra_tail_T.setPath(nodeMapT.get(nextViaNodeX,nextViaNodeY));
				
				Polygon polygon = lobbyView.generatePolygon(listOfPathHead_T.get(0),
						listOfPathTail_T.get(0),
						dijkstra_head_T.getPath(),dijkstra_tail_T.getPath());
				
				
				ArrayList<Integer> pathIdList = lobbyView.getListPathID(polygon);
		
					
				String pathIdString;
				if(pathIdList.size() == 0){
					
					pathIdString = "start";
					
				}
				else{
					
					pathIdString = pathIdToString(pathIdList);
				}
		
				
				/**
				 * Evaluation_1 is in this method implemented.
				 * */
				lobbyView.setPathCategory_T(pathIdString, dijkstra_head_T.getPath(), 
						dijkstra_tail_T.getPath(), numberViaNode,nextViaNodeX, nextViaNodeY, 
							distance_next,turningOfValue_next, listOfPathCategory_T, costOfHomotopy_T, valueOfTurning_T,
							listOfPathHead_T, listOfPathTail_T );
				
				loop++;
				
			}
			
			
			long endTime_searching = System.currentTimeMillis();
			long duration_searching = (endTime_searching - startTime_searching);
			long duration_classifying = (endTime_searching - firstHomotopy);
			System.out.println("the duration of the searching and the homotopy test : " + duration_searching + " miliseconds");
			System.out.println("the duration of homotopy test : " + duration_classifying + " miliseconds");
			
		    createHomotopy(lobbyView, costMapT);
			
			
		    eval_whole_iteration(listOfPathHead_T,times);
		    //eval_path_quality(costOfHomotopy_T, valueOfTurning_T);
		    
			times++;
			//getThreadForward_T().interrupt();
			//getThreadBackward_T().interrupt();
		}
		
		
	}
	private void eval_path_quality(int[] costOfHomotopy_T, double[] valueOfTurning_T){
		
		lobbyView.printCostOfHomotopyClass_T(costOfHomotopy_T);
	    lobbyView.printValueOfTurning_T(valueOfTurning_T);
		
	}
	private void eval_whole_iteration(ArrayList<LinkedList<Node>> listOfPathHead_T, int times){
		
		System.out.println("founded homotopy classes : " + listOfPathHead_T.size() + " , " + times);
		
	}
	private Vector2D getViaNode2D_T(int i,ArrayList<Point> viaNodes_T){

			return new Vector2D((viaNodes_T.get(i).getCenterX()-5)/10,(viaNodes_T.get(i).getCenterY()-5)/10);

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
		
         //Thread thread1 = new Thread(task1);
		 thread1 = new Thread(task1);
         thread1.setDaemon(true);
         thread1.start();
         
         try {
				thread1.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	private Thread getThreadForward(){
		return thread1;
		
	}
	private Thread getThreadBackward(){
		return thread2;	
	}
	private Thread getThreadForward_T(){
		return thread3;
		
	}
	private Thread getThreadBackward_T(){
		return thread4;	
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

	    //Thread thread2 = new Thread(task2);
		thread2 = new Thread(task2);
	    thread2.setDaemon(true);
	    thread2.start();
		
		try {
			thread2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void dijkstraForward_T(DijkstraAlgorithm dijkstra_head_T){
		
		 Task<Void> task3 = new Task<Void>() {
           @Override
           public Void call() throws InterruptedException {
           	
           	dijkstra_head_T.execute();
           	
   			System.out.println("the execution_1_T is the end \n");
   			
   			
   			
               return null ;
           }
		 };
		
        thread3 = new Thread(task3);
        thread3.setDaemon(true);
        thread3.start();
        
        try {
				thread3.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	private void dijkstraBackward_T(DijkstraAlgorithm dijkstra_tail_T){
		
		 Task<Void> task4 = new Task<Void>() {
           @Override
           public Void call() throws InterruptedException {
           	dijkstra_tail_T.execute();
   			System.out.println("the execution_2_T is the end  \n");

               return null ;
           }
		 };

	    thread4 = new Thread(task4);
	    thread4.setDaemon(true);
	    thread4.start();
		
		try {
			thread4.join();
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
			
					    EventHandler<MouseEvent> startViaNodeOnMousePressedEventHandler = 
						        new EventHandler<MouseEvent>() {
						 
						        @Override
						        public void handle(MouseEvent t) {
						        	
						            orgSceneX = t.getSceneX()-50;//50
						            orgSceneY = t.getSceneY()-25;//25
						            
						            orgTranslateX = ((Point)(t.getSource())).getTranslateX();
						            orgTranslateY = ((Point)(t.getSource())).getTranslateY();
						        	
						        }       
						    };
			     
						EventHandler<MouseEvent> startViaNodeOnMouseDraggedEventHandler = 
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
						           
							        CHmodel.setStartViaNodeX( (int)(startViaNode.getCenterX()/10)+1 );
							        CHmodel.setStartViaNodeY( (int)(startViaNode.getCenterY()/10)+1 );
							        
							        ((Point)(t.getSource())).centerXProperty().bind(xPosProperty);
							        ((Point)(t.getSource())).centerYProperty().bind(yPosProperty);
							        		  
						        }
						        
						   };
						    
						EventHandler<MouseEvent> startViaNodeOnMouseRelesedEventHandler = 
							        new EventHandler<MouseEvent>() {
							 
							        @Override
							        public void handle(MouseEvent t) {
							             
							        DoubleProperty xPosProperty = new SimpleDoubleProperty(CHmodel.getStartViaNodeX());
								    DoubleProperty yPosProperty = new SimpleDoubleProperty(CHmodel.getStartViaNodeY());
							        ((Point)(t.getSource())).centerXProperty().bind(xPosProperty);
								    ((Point)(t.getSource())).centerYProperty().bind(yPosProperty);
							          			        
							        }
							        
							        
						};
							    

}

