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
import model.node.Node;
import model.node.NodeMapHandler;
import util.math.Vector2D;
import view.Lobby;

public class LobbyPresenter {
	
	Stage stage;
	Lobby lobbyView;
	Obstacle obs;
	private int numberObs;
	private ArrayList<Obstacle> obstacles;
	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;
	private Vector2D size;	
	private static NodeMapHandler nodeMapHandler;
	
	public LobbyPresenter(Lobby lobbyView){
		this.lobbyView = lobbyView;
		obs=lobbyView.getObstacle();
		size = CHmodel.getSizeVector2D();
		activate();
	}
	
	public void activate(){
		
		
		numberObs = CHmodel.getObstacle();
		obstacles = lobbyView.getObstacleList();
		
					
		for(int i = 0; i < numberObs; i++){
						
			obstacles.get(i).setOnMousePressed(obstacleOnMousePressedEventHandler);
			obstacles.get(i).setOnMouseDragged(obstacleOnMouseDraggedEventHandler);
			obstacles.get(i).setOnMouseReleased(obstacleOnMouseRelesedEventHandler);
			System.out.println("numer obstacle " + i +"\n");
			
		}
		
		lobbyView.contractButton.setOnMouseClicked(event -> {

			});
		
		lobbyView.searchButton.setOnMouseClicked(event -> {
			
			CostMap costmap = new CostMap(size, CHmodel.getStartVector2D(),
					CHmodel.getGoalVector2D(), CHmodel.getNodeMap(), obstacles);
			
			
			costmap.getDijkstra().execute(costmap.getNodeMap()
					.get(CHmodel.getStartVector2D().getX(), CHmodel.getStartVector2D().getY()));
			
            LinkedList<Node> path = costmap.getDijkstra().getPath(costmap.getNodeMap()
					.get(CHmodel.getGoalVector2D().getX(),CHmodel.getGoalVector2D().getY()));
            
            
            lobbyView.createLane(path);
            
            
            System.out.println("algorithm is the end \n");
            
            

			/*try {
					Play.navigator.switchTo(AvailableScenes.GAME);
					clientPresenter.getGameModel().setGameStateStarted();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/

		});

	 }//activate end
	
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
	            /*
	            System.out.println("offsetX :" + offsetX + "\n");
	            System.out.println("newTranslateX :" + newTranslateX + "\n");
	            */
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
		        	System.out.println("relese Xposition :" + xposition + "\n");
		        	System.out.println("relese Yposition :" + yposition + "\n");
		            
		        }
		        
		    };


}
