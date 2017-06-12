package presenter;

import java.io.IOException;
import javafx.scene.Scene;
import main.Play;
import model.CHmodel;
import model.dijkstra.CostMap;
import model.node.NodeMap;
import model.node.NodeMapHandler;
import options.AvailableScenes;
import util.math.Vector;
import util.math.Vector2D;
import view.MainView;

public class MainPresenter {
	
	private Scene scene;
	private MainView mainView;
	private Vector2D size;	
	private NodeMap nodeMap;
	
	private static NodeMapHandler nodeMapHandler;
	
	public MainPresenter(MainView mainView) throws IOException, InterruptedException {
		
		this.scene = mainView.getScene();
		this.mainView = mainView;
		
		activate();


	}
	private void activate(){
		
		/*after click create map button*/
		mainView.createMap.setOnMousePressed(event -> {
			try {
				
				/*save the value of map size,position of start 
				 * & end point & number of obstacle*/
				readyForLobby();
	
				//Play.navigator.setGameModel(gameModel);
				Play.navigator.switchTo(AvailableScenes.LOBBY);
				

			} catch (Exception e) {
				e.printStackTrace();
			} 
			System.out.println("pushed \n");
		});
	}
	
	private void readyForLobby(){
		
		String modi = CHmodel.getMode();
		
		switch (modi){
		case "manual":
			readyForManualMode();
			break;
			
		case "fair":
			readyForFair();
			break;
			
		case "floor":
			readyForFloor();
			break;
			
		//case "extended for other scenarios"	
			
		}
	
	}
	private void readyForFloor(){
		/**
		 * The size of floor-mode is already fixed as 800x600.
		 * */
		CHmodel.setMapX(800);
		CHmodel.setMapY(600);
		
		// make not visible start and goal point
		/**
		 * The position of start point on the map.
		 * */
		CHmodel.setStartX(5);
		CHmodel.setStartY(5);
		
		/**
		 * The position of goal point on the map.
		 * */
		CHmodel.setGoalX(75);
		CHmodel.setGoalY(55);
		// make not visible number of obstacle
		//CHmodel.setNumerObstacle(0);
		
		CHmodel.setSizeVector2D(size = new Vector2D( CHmodel.getMapX(),CHmodel.getMapY(),10 ) );
		
		CHmodel.setContractingPercent(Integer.parseInt(mainView.getPercentageCustom().getText()));
		
		System.out.println("the percent of CH : " + Integer.parseInt(mainView.getPercentageCustom().getText()) + "%\n");
		
		new NodeMapHandler(size);
		
	}
	private void readyForFair(){
		
		/**
		 * The size of fair-mode is already fixed as 800x600.
		 * */
		CHmodel.setMapX(800);
		CHmodel.setMapY(600);
		
		// make not visible start and goal point
		/**
		 * The position of start point on the map.
		 * */
		CHmodel.setStartX(5);
		CHmodel.setStartY(5);
		
		/**
		 * The position of goal point on the map.
		 * */
		CHmodel.setGoalX(75);
		CHmodel.setGoalY(55);
		// make not visible number of obstacle
		//CHmodel.setNumerObstacle(0);
		
		CHmodel.setSizeVector2D(size = new Vector2D( CHmodel.getMapX(),CHmodel.getMapY(),10 ) );
		
		CHmodel.setContractingPercent(Integer.parseInt(mainView.getPercentageCustom().getText()));
		
		System.out.println("the percent of CH : " + Integer.parseInt(mainView.getPercentageCustom().getText()) + "%\n");
		
		new NodeMapHandler(size);
		
		
	}
	private  void readyForManualMode(){
		
		CHmodel.setMapX(Integer.parseInt(mainView.getMapX().getText()));
		CHmodel.setMapY(Integer.parseInt(mainView.getMapY().getText()));
		
		
		/**
		 * The position of start point on the map.
		 * */
		CHmodel.setStartX(Integer.parseInt(mainView.getStartPointX().getText()));
		CHmodel.setStartY(Integer.parseInt(mainView.getStartPointY().getText()));
		
		/**
		 * The position of start point on the map.
		 * */
		CHmodel.setGoalX(Integer.parseInt(mainView.getEndPointX().getText()));
		CHmodel.setGoalY(Integer.parseInt(mainView.getEndPointY().getText()));
		
		CHmodel.setNumberObstacle(Integer.parseInt(mainView.getNumberObstacle().getText()));
		
		CHmodel.setSizeVector2D(size = new Vector2D( CHmodel.getMapX(),CHmodel.getMapY(),10 ) );
		
		CHmodel.setContractingPercent(Integer.parseInt(mainView.getPercentageCustom().getText()));
		
		System.out.println("the percent of CH : " + Integer.parseInt(mainView.getPercentageCustom().getText()) + "%\n");
		
		new NodeMapHandler(size);
		
	}
		
}
