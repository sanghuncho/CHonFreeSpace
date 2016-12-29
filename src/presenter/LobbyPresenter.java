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
import javafx.stage.Stage;
import javafx.util.Duration;
import model.CHmodel;
import model.Obstacle;
import model.dijkstra.CostMap;
import model.dijkstra.Edge;
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
	private static NodeMapHandler nodeMapHandler;
	private Vector startPointNode;
	private Vector goalPointNode;
	private int loop;
	private int loop_map;
	private int[][] map;
	private NodeMap nodeMap;
	
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
		
		setNodeObstacleProperty(nodeMap);
		
		for(int i = 0; i < numberObs; i++){
						
			obstacles.get(i).setOnMousePressed(obstacleOnMousePressedEventHandler);
			obstacles.get(i).setOnMouseDragged(obstacleOnMouseDraggedEventHandler);
			obstacles.get(i).setOnMouseReleased(obstacleOnMouseRelesedEventHandler);			
		}
		
		lobbyView.contractButton.setOnMouseClicked(event -> {
			
			/*this.map = new int[size.getX()][size.getY()];
			
			for (Node node : nodeMap.getNodes()) {

				if (node.isObstacle()) {
				
					setCost(node.getPosition(), -1);
					
				} else {
					
					setCost(node.getPosition(),0);

				}
			}*/
			
			map[startPointNode.getX()][startPointNode.getY()] = 0;
			
			
			loop_map=0;
			
			while(loop_map < numberObs){
				
				lobbyView.createViaNodePoint(size,map);
				
				loop_map++;
			}
			
			

		});
		
		
		lobbyView.searchButton.setOnMouseClicked(event -> {
		
		loop=0;
			
		
		
		this.map = new int[size.getX()][size.getY()];
		
		for (Node node : nodeMap.getNodes()) {

			if (node.isObstacle()) {
			
				setCost(node.getPosition(), -1);
				
			} else {
				
				setCost(node.getPosition(),0);

			}
		}
		
		loop_map=0;
		
		/*while(loop_map < numberObs){
			
			lobbyView.createViaNodePoint(size,map);
			
			loop_map++;
		}*/
		
		lobbyView.createViaNodePoint(size,map);
		
		
		//while(loop < numberObs ){
			
			
			
			CostMap costmap1 = new CostMap(size, CHmodel.getStartVector2D(),
					nodeMap, obstacles , map);
			
			costmap1.createEdgeOnMap();
			costmap1.startDijkstra();
			
			costmap1.getDijkstra().execute(nodeMap
					.get(startPointNode.getX(),startPointNode.getY()),nodeMap
					.get(lobbyView.getViaNode2D().getX(),lobbyView.getViaNode2D().getY()));
			
			System.out.println("execute end \n");
			
            LinkedList<Node> path1 = costmap1.getDijkstra().getPath(nodeMap
					.get(lobbyView.getViaNode2D().getX(),lobbyView.getViaNode2D().getY()));
            
            lobbyView.createLane(path1);
            			
            CostMap costmap2 = new CostMap(size, lobbyView.getViaNode2D(),
					  nodeMap, obstacles, map);
			
			/*CostMap costmap2 = new CostMap(size,CHmodel.getStartVector2D(),
					  nodeMap, obstacles, map);*/
				
			costmap2.createEdgeOnMap();
			costmap2.startDijkstra();
            
            costmap2.getDijkstra().execute(nodeMap
					.get(lobbyView.getViaNode2D().getX(),lobbyView.getViaNode2D().getY())
							,nodeMap.get(goalPointNode.getX(),goalPointNode.getY()) );
            
			
			/*costmap2.getDijkstra().execute(nodeMap
					.get(startPointNode.getX(),startPointNode.getY()),
							nodeMap.get(goalPointNode.getX(),goalPointNode.getY()) );
            */
     
            
          LinkedList<Node> path2 = costmap2.getDijkstra().getPath(nodeMap
					.get(goalPointNode.getX(),goalPointNode.getY()));
            
          lobbyView.createLane(path2);
            
           // loop++;
           
		// }
            
            
            System.out.println("algorithm is the end \n");
            
		});
		
		
	}//activate end
	
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
		
		boolean insideObs = false;
		
		
		for(Node node : nodeMap.getNodes()){
			
			for (Obstacle obstacle : obstacles) {
				
				
				insideObs = (insideObs || isNodeInsideObs(node,obstacle));
						
			}
			
			if(insideObs){
				
				node.setProperty(Property.OBSTACLE);
				
	        }
			insideObs = false;
		}
		
	}

	
	private boolean isNodeInsideObs(Node node, Obstacle obstacle){
		
		int nodeGoalX = node.getPosition().getX();
		int nodeGoalY = node.getPosition().getY();
		
		int obsXpos = (int)obstacle.xProperty().get();
		int obsXposWidth = (int)(obsXpos + (obstacle.getWidth()));
		
		int obsYpos = (int)obstacle.yProperty().get();
		int obsYposWidth = (int)(obsYpos + (obstacle.getHeight()));
		
		if( (obsXpos <= nodeGoalX ) && ( nodeGoalX <= obsXposWidth)){ 
			
			if(( obsYpos <= nodeGoalY) && ( nodeGoalY <= obsYposWidth)){
				
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
	            orgSceneX = t.getSceneX();
	            orgSceneY = t.getSceneY();
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
	            
	            DoubleProperty xPosProperty = new SimpleDoubleProperty(t.getSceneX());
	            DoubleProperty yPosProperty = new SimpleDoubleProperty(t.getSceneY());
	            
		        ((Obstacle)(t.getSource())).xProperty().bind(xPosProperty );
		        ((Obstacle)(t.getSource())).yProperty().bind(yPosProperty );
		        		  
	        }
	        
	    };
	  
	    EventHandler<MouseEvent> obstacleOnMouseRelesedEventHandler = 
		        new EventHandler<MouseEvent>() {
		 
		        @Override
		        public void handle(MouseEvent t) {
		             
		          double xposition =  ((Obstacle)(t.getSource())).getXPoperty().get();
		          double yposition =  ((Obstacle)(t.getSource())).getYPoperty().get();
		        
		        }
		        
		    };
		    
		   


}

