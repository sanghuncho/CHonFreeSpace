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
			System.out.println("pushed");
		});
	}
	
	private void readyForLobby(){
		
		CHmodel.setMapX(Integer.parseInt(mainView.getMapX().getText()));
		CHmodel.setMapY(Integer.parseInt(mainView.getMapY().getText()));
		
		CHmodel.setStartX(Integer.parseInt(mainView.getStartPointX().getText()));
		CHmodel.setStartY(Integer.parseInt(mainView.getStartPointY().getText()));
		
		CHmodel.setGoalX(Integer.parseInt(mainView.getEndPointX().getText()));
		CHmodel.setGoalY(Integer.parseInt(mainView.getEndPointY().getText()));
		
		CHmodel.setObstacle(Integer.parseInt(mainView.getNumberObstacle().getText()));
		
		CHmodel.setSizeVector2D(size = new Vector2D( CHmodel.getMapX(),CHmodel.getMapY(),10 ) );
		
		System.out.println("X " + size.getX() + "\n");
		System.out.println("Y " + size.getY() + "\n");
		
		/*CHmodel.setNodeMap(nodeMap = new NodeMap(size));
		
		new NodeMapHandler(nodeMap);*/
		
		nodeMapHandler = new NodeMapHandler(size);
		
		CostMap costmap = new CostMap(size, CHmodel.getStartVector2D(),
				CHmodel.getGoalVector2D(), nodeMapHandler.getNodeMap());
		
			
	}
		
}
