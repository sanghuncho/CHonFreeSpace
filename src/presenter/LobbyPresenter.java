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
					CHmodel.getGoalVector2D(), CHmodel.getNodeMap());
			
			costmap.getDijkstra().execute(costmap.getNodeMap()
					.get(CHmodel.getStartX(),CHmodel.getStartY()));
            LinkedList<Node> path = costmap.getDijkstra().getPath(costmap.getNodeMap()
					.get(CHmodel.getGoalX(),CHmodel.getGoalY()));
            
            System.out.println("algorithm is end \n");
            
            

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
