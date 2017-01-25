package presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.CHmodel;
import model.Obstacle;
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
			
			loop_map=0;
			
			while(loop_map < 0){//CHmodel.getNumberContracted() , numberObs
		
				//lobbyView.createViaNodePoint(size,map);
				lobbyView.generateContractedPoint(size,map);
				loop_map++;
			}
			
			loop_viaNode=0;
			while(loop_viaNode < 4 ){//CHmodel.getNumberContracted() , numberObs
				
				lobbyView.createViaNodePoint(size,map);
				loop_viaNode++;
			}
			

		});
		
		
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
			
			loop_map=0;
			
			while(loop_map < CHmodel.getNumberContracted()){//CHmodel.getNumberContracted() , numberObs
		
				lobbyView.generateContractedPoint(size,map);
				loop_map++;
			}
			
			loop_viaNode=0;
			while(loop_viaNode < 4 ){//CHmodel.getNumberContracted() , numberObs

				lobbyView.createViaNodePoint(size,map);
				loop_viaNode++;
			}
			

		});
		
		
		lobbyView.searchButton.setOnMouseClicked(event -> {
		
			//loop=0;
			loop=1;
			
			CostMap costmap = new CostMap(size, CHmodel.getStartVector2D(),
					nodeMap, obstacles , map);
			
			Graph graph = new Graph(nodeMap.getNodes(),costmap.getEdges());
			
			DijkstraAlgorithm dijkstra_head = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(startPointNode.getX(),startPointNode.getY()),lobbyView); 
			
			DijkstraAlgorithm dijkstra_tail = new DijkstraAlgorithm(graph,obstacles,
					nodeMap.get(goalPointNode.getX(),goalPointNode.getY()),lobbyView); 
			
	
	
			dijkstra_head.execute();
			
			dijkstra_tail.execute();
			
			int firstViaNodeX = lobbyView.getViaNode2D(0).getX();
			int firstViaNodeY = lobbyView.getViaNode2D(0).getY();
			
			dijkstra_head.setPath(nodeMap
					.get(firstViaNodeX,firstViaNodeY));
			
			lobbyView.getListOfPathHead().add(dijkstra_head.getPath());
			
			
			
			dijkstra_tail.setPath(nodeMap
					.get(firstViaNodeX,firstViaNodeY));
			
			lobbyView.getListOfPathTail().add(dijkstra_tail.getPath());
			
			lobbyView.getListOfPathHead().get(0).addAll(lobbyView.getListOfPathTail().get(0));
			
			lobbyView.getListPath().add(lobbyView.getListOfPathHead().get(0));
			
			lobbyView.getPathCategory().add("null");
			
			
			/*polygons are made by this methode*/
			
			while( loop < lobbyView.getViaNodeSize() ){
				
				int nextViaNodeX = lobbyView.getViaNode2D(loop).getX();
				int nextViaNodeY = lobbyView.getViaNode2D(loop).getY();
				
				dijkstra_head.setPath(nodeMap
						.get(nextViaNodeX,nextViaNodeY));
				
				dijkstra_tail.setPath(nodeMap
						.get(nextViaNodeX,nextViaNodeY));
				
				Polygon polygon = lobbyView.generatePolygon(lobbyView.getListOfPathHead().get(0),
						lobbyView.getListOfPathTail().get(0),
						dijkstra_head.getPath(),dijkstra_tail.getPath());
				
				dijkstra_head.getPath().addAll(dijkstra_tail.getPath());
				
				ArrayList<Integer> pathIdList = lobbyView.getListPathID(polygon);
				
				String pathIdString = pathIdToString(pathIdList);
				
				lobbyView.setPathCategory(pathIdString,dijkstra_head.getPath());
				
			}
			
			
			
			
			
			
			/*while( loop < lobbyView.getViaNodeSize() ){  //CHmodel.getNumberContracted()
			
			
					dijkstra_head.setPath(nodeMap
							.get(lobbyView.getViaNode2D(loop).getX(),lobbyView.getViaNode2D(loop).getY()));
					
					lobbyView.createLane(dijkstra_head.getPath());//LinkedList<Node>
			            
					dijkstra_tail.setPath(nodeMap
							.get(lobbyView.getViaNode2D(loop).getX(),lobbyView.getViaNode2D(loop).getY()));
					
					lobbyView.createLane(dijkstra_tail.getPath());
				
				
		        loop++;
				
			}*/
			
			/*it is determined according to mode,
			whether only the shortcut are exhibited or all edges are exhibited*/  
			if(applyCH){
				
				lobbyView.drawingShortcutEdges(costmap.getEdges());
			}
			else{
			
				lobbyView.drawingEdges(costmap.getEdges());
			}
			
			
			
			
			
			
			
			/*it shows the number of edges and nodes*/
			lobbyView.setText(costmap.getEdges().size(),applyCH);
			
			
			
			
			System.out.println("Algo is end \n");
	
			
		});
		
		
		/*lobbyView.refreshButton.setOnMouseClicked(event -> {
			
			
			lobbyView.removeLane();
			
			
		});*/
		
	}
	
	private String pathIdToString(ArrayList<Integer> idList){
		
		if(idList.size() == 0){
			
			return "null";
			
		}else{
			
			return idList.toString();
			
		}
		
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
		int obsXposWidth = (int)(obsXpos + (obstacle.getWidth()));
		
		int obsYpos = (int)obstacle.yProperty().get();
		int obsYposWidth = (int)(obsYpos + (obstacle.getHeight()));
		
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
	        	
	            orgSceneX = t.getSceneX()-60;//50
	            orgSceneY = t.getSceneY()-35;//25
	            
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
	            
	            DoubleProperty xPosProperty = new SimpleDoubleProperty(t.getSceneX()-75);
	            DoubleProperty yPosProperty = new SimpleDoubleProperty(t.getSceneY()-50);
	            
	    
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

