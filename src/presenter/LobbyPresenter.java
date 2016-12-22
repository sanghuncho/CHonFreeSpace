package presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javafx.animation.TranslateTransition;
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
						
			obstacles.get(i).setOnMousePressed(circleOnMousePressedEventHandler);
			obstacles.get(i).setOnMouseDragged(circleOnMouseDraggedEventHandler);
			
		}
		
		lobbyView.contractButton.setOnMouseClicked(event -> {
				/*try {
						Play.navigator.switchTo(AvailableScenes.GAME);
						clientPresenter.getGameModel().setGameStateStarted();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}*/

			});
		
		lobbyView.searchButton.setOnMouseClicked(event -> {
			
			//nodeMapHandler = new NodeMapHandler(size);
			
			
			
			CostMap costmap = new CostMap(size, CHmodel.getStartVector2D(),
					CHmodel.getGoalVector2D(), CHmodel.getNodeMap(), obstacles);
			
			//costmap.removeObstacleOnMap();
			
			//costmap.startDijkstra();
			
			costmap.getDijkstra().execute(costmap.getNodeMap()
					.get(CHmodel.getStartVector2D().getX(), CHmodel.getStartVector2D().getY()));
			
            LinkedList<Node> path = costmap.getDijkstra().getPath(costmap.getNodeMap()
					.get(CHmodel.getGoalVector2D().getX(),CHmodel.getGoalVector2D().getY()));
            
            
            
          /*  int k = path.size();
            System.out.println("path size : " + k + "\n"); 
            
            
            System.out.println("start first X : " + costmap.getNodeMap()
			.get( CHmodel.getStartX(), CHmodel.getStartY() ).getPosition().getX() + "\n");
            
            for(int i = 0 ; i < k ; i ++){
            	
            System.out.println("X : "+ path.get(i).getPosition().getX() + ", " + "Y : " +
           				path.get(i).getPosition().getY() + "\n");
                
            }*/
            
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

	 }
	EventHandler<MouseEvent> circleOnMousePressedEventHandler = 
	        new EventHandler<MouseEvent>() {
	 
	        @Override
	        public void handle(MouseEvent t) {
	            orgSceneX = t.getSceneX();
	            orgSceneY = t.getSceneY();
	            orgTranslateX = ((Obstacle)(t.getSource())).getTranslateX();
	            orgTranslateY = ((Obstacle)(t.getSource())).getTranslateY();
	        }
	    };
	     
	    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = 
	        new EventHandler<MouseEvent>() {
	 
	        @Override
	        public void handle(MouseEvent t) {
	            double offsetX = t.getSceneX() - orgSceneX;
	            double offsetY = t.getSceneY() - orgSceneY;
	            double newTranslateX = orgTranslateX + offsetX;
	            double newTranslateY = orgTranslateY + offsetY;
	             
	            ((Obstacle)(t.getSource())).setTranslateX(newTranslateX);
	            ((Obstacle)(t.getSource())).setTranslateY(newTranslateY);
	        }
	    };

}
