package presenter;

import java.awt.Insets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.CHmodel;
import model.Obstacle;
import model.ProgressForm;
import model.dijkstra.CostMap;
import model.dijkstra.DijkstraAlgorithm;
import model.dijkstra.Edge;
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
	private int[][] map;
	private NodeMap nodeMap;
	private boolean applyCH;
	private Thread thread;
	private CostMap costmap;
	private boolean isCheckAll;
	private boolean isCheckShort;
	private DijkstraAlgorithm dijkstra_head;
	private DijkstraAlgorithm dijkstra_tail;

	
	public LobbyPresenter(Lobby lobbyView){
		this.lobbyView = lobbyView;
		obs=lobbyView.getObstacle();
		size = CHmodel.getSizeVector2D();
		startPointNode = CHmodel.getStartVector2D();
		goalPointNode = CHmodel.getGoalVector2D();
		nodeMap = CHmodel.getNodeMap();
		activate();
	}
	
	public void activate(){
		
		
		numberObs = CHmodel.getObstacle();
		obstacles = lobbyView.getObstacleList();
		
		for(int i = 0; i < numberObs; i++){
						
			obstacles.get(i).setOnMousePressed(obstacleOnMousePressedEventHandler);
			obstacles.get(i).setOnMouseDragged(obstacleOnMouseDraggedEventHandler);
			//obstacles.get(i).setOnMouseReleased(obstacleOnMouseRelesedEventHandler);			
		}
		
		
		
		/**
		 * this runs, if only the via-node butten is clicked
		 */
		lobbyView.viaNodeButton.setOnMouseClicked(event -> {
			
			setNodeObstacleProperty(nodeMap);
			setNodeStartProperty(nodeMap);
			setNodeGoalProperty(nodeMap);
			
			this.applyCH = false;
						
			
			this.map = new int[size.getX()][size.getY()];
						
			for (Node node : nodeMap.getNodes()) {

				if (node.isObstacle()) {
				
					setCost(node.getPosition(), CHmodel.VALUE_MAP_OBSTACLE);
					
				}else if(node.isStart() || node.isGoal()){
			
					setCost(node.getPosition(), CHmodel.VALUE_MAP_START_GAOL);
				} 
					
				else {
					
					setCost(node.getPosition(),CHmodel.VALUE_MAP_POINT);

				}
			}
			
			/*loop_map=0;
			
			while(loop_map < 0){
		
				//lobbyView.createViaNodePoint(size,map);
				lobbyView.generateContractedPoint(size,map);
				loop_map++;
			}*/
			
			loop_viaNode=0;
			while(loop_viaNode < lobbyView.getnumberOfViaNode() ){
			
				lobbyView.createViaNodePoint(size,map,loop_viaNode);
				loop_viaNode++;
			}
			
			/*move to preprocessing*/
            costmap = new CostMap(size, CHmodel.getStartVector2D(),
        					nodeMap, obstacles , map);
			
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
			
			
			setNodeObstacleProperty(nodeMap);
			setNodeStartProperty(nodeMap);
			setNodeGoalProperty(nodeMap);
			
			
			this.applyCH = true;
						
			
			this.map = new int[size.getX()][size.getY()];
						
			for (Node node : nodeMap.getNodes()) {

				if (node.isObstacle()) {
				
					setCost(node.getPosition(), CHmodel.VALUE_MAP_OBSTACLE);
					
				}else if(node.isStart() || node.isGoal()){
								
					setCost(node.getPosition(), CHmodel.VALUE_MAP_START_GAOL);
				} 
				
				
				else {
					
					setCost(node.getPosition(),CHmodel.VALUE_MAP_POINT);

				}
			}
			
			
			loop_viaNode=0;	
			while(loop_viaNode < lobbyView.getnumberOfViaNode() ){
				
				lobbyView.createViaNodePoint(size,map,loop_viaNode);
				loop_viaNode++;
			}
			
			loop_map=0;
			long  startTime_contracted = System.currentTimeMillis();
			System.out.println("contracting : " + CHmodel.getNumberContractedCustom());
			while(loop_map < CHmodel.getNumberContractedCustom()){
		
				lobbyView.generateContractedPoint(size,map);
				loop_map++;
			}
			long endTime_contracted = System.currentTimeMillis();
			long duration_allContracted = (endTime_contracted - startTime_contracted);
			System.out.println("the duration of the contracting : " + duration_allContracted + " miliseconds");
			
			
			
			
			
			/*move to preprocessing*/
            costmap = new CostMap(size, CHmodel.getStartVector2D(),
        					nodeMap, obstacles , map);
            
            System.out.println("edge size : " + costmap.getEdges().size()/2);
			
			Graph graph = new Graph(nodeMap.getNodes(),costmap.getEdges());
			
			dijkstra_head = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(startPointNode.getX(),startPointNode.getY()),lobbyView); 
			
			dijkstra_tail = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(goalPointNode.getX(),goalPointNode.getY()),lobbyView); 
			
			    
			

		});
	
		/*try to implement the threads*/
		lobbyView.searchButton.setOnMouseClicked(event -> {
		
			long  startTime_searching = System.currentTimeMillis();
        
			
			/*ProgressForm pForm = new ProgressForm();
			
			Task<Void> task3 = new Task<Void>() {
                @Override
                public Void call() throws InterruptedException {
                	for (int i = 0; i < 10; i++) {
                        updateProgress(i, 10);
                        pForm.getDialogStage().show();
                        Thread.sleep(2000);
                    }
                    updateProgress(10, 10);
                    return null ;
                }
			 };
			 
			 pForm.activateProgressBar(task3);
	         pForm.getDialogStage().show();
	        

			 Thread thread3 = new Thread(task3);
			 thread3.setDaemon(true);
	         thread3.start();*/
	         
	        /* try {
				thread3.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			
		/*	
			costamp inclusives createSurroundingEdges(nodes),moved to preprocessing
            costmap = new CostMap(size, CHmodel.getStartVector2D(),
        					nodeMap, obstacles , map);
            
			CostMap costmap = new CostMap(size, CHmodel.getStartVector2D(),
					nodeMap, obstacles , map);
			
			Graph graph = new Graph(nodeMap.getNodes(),costmap.getEdges());
			
			DijkstraAlgorithm dijkstra_head = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(startPointNode.getX(),startPointNode.getY()),lobbyView); 
			
			DijkstraAlgorithm dijkstra_tail = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(goalPointNode.getX(),goalPointNode.getY()),lobbyView); 
			
			    */
			/*dijkstra_head.execute();
			System.out.println("Execute1 end \n");
			dijkstra_tail.execute();			
			System.out.println("Execute2 end \n");*/
			

	        
			
			
			Task<Void> task1 = new Task<Void>() {
                @Override
                public Void call() throws InterruptedException {
                	dijkstra_head.execute();
        			System.out.println("Execute1 end \n");

                    return null ;
                }
			 };
			
	         Thread thread1 = new Thread(task1);
	         thread1.setDaemon(true);
	         thread1.start();
	         
	        Task<Void> task2 = new Task<Void>() {
	                @Override
	                public Void call() throws InterruptedException {
	                	dijkstra_tail.execute();
	        			System.out.println("Execute2 end \n");

	                    return null ;
	                }
				 };
				

		    Thread thread2 = new Thread(task2);
		    thread2.setDaemon(true);
		    thread2.start();
			
		    
				try {
					thread1.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				try {
					thread2.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		 /*here through the checkBox decided whether 
		     * all paths are showed or the representative paths showed*/ 
		 isCheckAll = lobbyView.getCheckBoxAll().isSelected();
		 isCheckShort = lobbyView.getCheckBoxShort().isSelected();
			
		 /*the representative paths of homotopy class are displayed */
		 if((isCheckShort) && (!isCheckAll)){ 
			 
			int firstViaNodeX = lobbyView.getViaNode2D(0).getX();
			int firstViaNodeY = lobbyView.getViaNode2D(0).getY();
			
			dijkstra_head.setPath(nodeMap
					.get(firstViaNodeX,firstViaNodeY));
			
			lobbyView.getListOfPathHead().add(0,dijkstra_head.getPath());
			
			//here should implement the whole distance of path and the turn value of path of the head
			
			dijkstra_tail.setPath(nodeMap
					.get(firstViaNodeX,firstViaNodeY));
			
			lobbyView.getListOfPathTail().add(0,dijkstra_tail.getPath());
			lobbyView.getPathCategory().add("start");
			
			//here should implement the whole distance of path and the turn value of path of the tail
			
			/*polygons are made by this methode*/
			loop=1;
			int totalLoop = lobbyView.getViaNodeSize();			
			while( loop < totalLoop ){
				
				int numberViaNode = loop+1;
				int nextViaNodeX = lobbyView.getViaNode2D(loop).getX();
				int nextViaNodeY = lobbyView.getViaNode2D(loop).getY();
				
				
				dijkstra_head.setPath(nodeMap
						.get(nextViaNodeX,nextViaNodeY));
				
				dijkstra_tail.setPath(nodeMap
						.get(nextViaNodeX,nextViaNodeY));
				
				Polygon polygon = lobbyView.generatePolygon(lobbyView.getListOfPathHead().get(0),
						lobbyView.getListOfPathTail().get(0),
						dijkstra_head.getPath(),dijkstra_tail.getPath());
		
				
				/*pathidList contains the list of obstacle-id,
				which are in the polygon covered */
				ArrayList<Integer> pathIdList = lobbyView.getListPathID(polygon);
				
				//lobbyView.drawPolygon(polygon);
					
				String pathIdString;
				if(pathIdList.size() == 0){
					
					/*first homotopy class is named as "start"*/
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
						dijkstra_tail.getPath(),numberViaNode,nextViaNodeX,nextViaNodeY);
				
				loop++;
				
			}
			
			/**
			 *the evaluation_3st is implemented.
			 *this shows the durationn of from the running of two dijkstra's algorithm to the homotopy test. 
			 */
			long endTime_searching = System.currentTimeMillis();
			long duration_searching = (endTime_searching - startTime_searching);
			System.out.println("the duration of the searching and the homotopy test : " + duration_searching + " miliseconds");
			
			
		    createHomotopy(lobbyView, costmap);
		 }//checkBoxShort is selected
		 
		 /*all the paths class are displayed */
		 if((!isCheckShort) && (isCheckAll)){
			 
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
		 else{
			 	//error 
		 }
		 
		
		          
		});
		
		
	}
	private void createHomotopy(Lobby lobbyView,CostMap costmap){
		        
			lobbyView.createHomotopyLane();
			 
			
			/*drawing shortcut edge on lobby*/
			/*if(applyCH){
				
				lobbyView.drawingShortcutEdges(costmap.getEdges());
			}
			else{
				
			}*/
				
			lobbyView.setText(costmap.getEdges().size(),applyCH);
	
	}
	private void createAllPaths(Lobby lobbyView,CostMap costmap){
        
		lobbyView.createAllLane();

		lobbyView.setText(costmap.getEdges().size(),applyCH);

	}

		
		
		/*original version*/ 
		/*lobbyView.searchButton.setOnMouseClicked(event -> {
		
			//loop=0;
			
			
			ProgressForm pForm = new ProgressForm();
			
			Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws InterruptedException {
                    for (int i = 0; i < 10; i++) {
                        updateProgress(i, 10);
                        Thread.sleep(200);
                    }
                    updateProgress(10, 10);
                    return null ;
                }
            };
            
            pForm.activateProgressBar(task);
  
            pForm.getDialogStage().show();

            Thread thread = new Thread(task);
            thread.start();
            
            
			
			CostMap costmap = new CostMap(size, CHmodel.getStartVector2D(),
					nodeMap, obstacles , map);
			
			Graph graph = new Graph(nodeMap.getNodes(),costmap.getEdges());
			
			DijkstraAlgorithm dijkstra_head = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(startPointNode.getX(),startPointNode.getY()),lobbyView); 
			
			DijkstraAlgorithm dijkstra_tail = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(goalPointNode.getX(),goalPointNode.getY()),lobbyView); 
			
	
	
			dijkstra_head.execute();
			System.out.println("Execute1 end \n");
			dijkstra_tail.execute();			
			System.out.println("Execute2 end \n");
			
			int firstViaNodeX = lobbyView.getViaNode2D(0).getX();
			int firstViaNodeY = lobbyView.getViaNode2D(0).getY();
			
			dijkstra_head.setPath(nodeMap
					.get(firstViaNodeX,firstViaNodeY));
			
			lobbyView.getListOfPathHead().add(0,dijkstra_head.getPath());
			
			dijkstra_tail.setPath(nodeMap
					.get(firstViaNodeX,firstViaNodeY));
			
			lobbyView.getListOfPathTail().add(0,dijkstra_tail.getPath());
			
			
			the head path is concatenated by the tail path
			lobbyView.getListOfPathHead().get(0).addAll(lobbyView.getListOfPathTail().get(0));
			first complete path is added at the listpath
			lobbyView.getListPath().add(lobbyView.getListOfPathHead().get(0));
		
			
			lobbyView.getPathCategory().add("start");
			
			
			polygons are made by this methode
			loop=1;
			int totalLoop = lobbyView.getViaNodeSize();
			while( loop < totalLoop ){
				
				//bar.setProgress(loop / totalLoop);
				
				int nextViaNodeX = lobbyView.getViaNode2D(loop).getX();
				int nextViaNodeY = lobbyView.getViaNode2D(loop).getY();
				
				
				dijkstra_head.setPath(nodeMap
						.get(nextViaNodeX,nextViaNodeY));
				
				dijkstra_tail.setPath(nodeMap
						.get(nextViaNodeX,nextViaNodeY));
				
				lobbyView.getListOfPathHead().add(1,dijkstra_head.getPath());
				lobbyView.getListOfPathTail().add(1,dijkstra_tail.getPath());
				
				
				Polygon polygon = lobbyView.generatePolygon(lobbyView.getListOfPathHead().get(0),
						lobbyView.getListOfPathTail().get(0),
						dijkstra_head.getPath(),dijkstra_tail.getPath());
		
				
				pathidList contain the list of obstacle-id,
				which are in the polygon covered 
				ArrayList<Integer> pathIdList = lobbyView.getListPathID(polygon);
				
				//lobbyView.drawPolygon(polygon);
				
				
				String pathIdString;
				if(pathIdList.size() == 0){
					
					pathIdString = "start";
					
				}
				else{
					pathIdString = pathIdToString(pathIdList);
				}
		
				
				set the homotopy categoriy
				lobbyView.setPathCategory(pathIdString, dijkstra_head.getPath(), dijkstra_tail.getPath());
				
				loop++;
				
			}
			
			
			drawing at once all the path,which the homotopy relation is applied
			lobbyView.createHomotopyLane();
			
			public void createHomotopyLane(){
				
				int head_size = listOfPathHead.size();
						
				for(int i=0; i< head_size; i++){
					
					createLane(listOfPathHead.get(i));	
					createLane(listOfPathTail.get(i));
						
					}
			}
			
			
			while( loop < lobbyView.getViaNodeSize() ){  //CHmodel.getNumberContracted()
			
			
					dijkstra_head.setPath(nodeMap
							.get(lobbyView.getViaNode2D(loop).getX(),lobbyView.getViaNode2D(loop).getY()));
					
					lobbyView.createLane(dijkstra_head.getPath());//LinkedList<Node>
			            
					dijkstra_tail.setPath(nodeMap
							.get(lobbyView.getViaNode2D(loop).getX(),lobbyView.getViaNode2D(loop).getY()));
					
					lobbyView.createLane(dijkstra_tail.getPath());
				
				
		        loop++;
				
			}
			
			
			it is determined according to mode,
			whether only the shortcut are exhibited or all edges are exhibited  
			if(applyCH){
				
				if the mode has selected by via node + CH button,
				 * then show the only the shortcut
				lobbyView.drawingShortcutEdges(costmap.getEdges());
			}
			else{
				if the mode is selected by via node button,
				 * then show the all the edges 
				//lobbyView.drawingEdges(costmap.getEdges());
			}
			
			
			it shows the number of edges and nodes on the lobby
			lobbyView.setText(costmap.getEdges().size(),applyCH);
			
			System.out.println("Algo is end \n");
			
			
			
			
		});
		
		
		lobbyView.refreshButton.setOnMouseClicked(event -> {
			
			
			lobbyView.removeLane();
			
			
		});
		
		
		
	}
*/	
	private String pathIdToString(ArrayList<Integer> idList){
		
		
		String idString="";
		for(int i=0; i < idList.size(); i++){
			
			idString = idString + idList.get(i).toString();
				
		}
		
		return idString;
	}
	
	private boolean isObstacle(Vector point) {
		return map[point.getX()][point.getY()] == -1;
	}
	
	public void setCost(Vector position, int value) throws ArithmeticException {
		
		map[position.getX()][position.getY()] = value;

		if (value < -1) {
			throw new ArithmeticException("set cost < -1 : " + value);
		}
	}
	
	private void setNodeObstacleProperty(NodeMap nodeMap){
		
		for(Node node : nodeMap.getNodes()){
			
			for (Obstacle obstacle : obstacles) {
		
					if(isNodeInsideObs(node,obstacle)){
						
						node.setProperty(Property.OBSTACLE);
					}	
			}
			
		}
		
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
			
			if(( obsYpos-10 <= nodeGoalY) && ( nodeGoalY <= obsYposWidth)){
				
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
		    
		   


}

