package view;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.CHmodel;
import model.Obstacle;
import model.Point;
import model.node.Node;
import model.node.NodeCircle;
import model.node.NodeMap;

public class Lobby extends BorderPane{
	
	public Button searchButton = new Button("Search");
	public Obstacle obs;
	public ArrayList<Obstacle>  obstacles;
	public Point startPoint;
	public Point endPoint;
	public NodeMap nodemap;
	
	private Scene scene;
	private int numberObs;
	
	
	public Lobby(Stage stage){
		
		searchButton.setId("searchButtonLabel");
		searchButton.setLayoutX(700);
		searchButton.setLayoutY(700);
		
		
		/*create the node map*/
		createNodeMap();
		
		
		/*TODO : abstraction of obstacle & create the methode for obstacle*/
		
		/*create the multiple obstacles*/
		numberObs = CHmodel.getObstacle();
		obstacles = new ArrayList<Obstacle>();
					
		for(int i = 0; i < numberObs; i++){
			obs = new Obstacle();	
			obstacles.add(obs);
		}
		
		Iterator itr = obstacles.iterator();
		
		while(itr.hasNext()){
			Obstacle obs = (Obstacle)itr.next();
			this.getChildren().add(obs);
		}
		
		/*create the staring - and end points*/
		startPoint= new Point();
		endPoint= new Point();
		
		startPoint.setCenterX(CHmodel.getStartX());
		startPoint.setCenterY(CHmodel.getStartY());
		startPoint.setFill(Color.GREENYELLOW);
		
		endPoint.setCenterX(CHmodel.getGoalX());
		endPoint.setCenterY(CHmodel.getGoalY());
		endPoint.setFill(Color.RED);
		
		this.getChildren().addAll(searchButton,startPoint,endPoint);
		
		scene = new Scene(this);
		stage.setHeight(CHmodel.getMapX());
		stage.setWidth(CHmodel.getMapY());
		//this.scene.getStylesheets().add("/view/style.css");
		stage.setScene(scene);
			
	}
	
	private void createNodeMap(){
		
		nodemap = CHmodel.getNodeMap();
		
		for (Node node : nodemap.getNodes()) {
			
			NodeCircle nodeCircle = new NodeCircle();
			
			nodeCircle.setCenterX(node.getPosition().getX()*CHmodel.UNIT_MAP
					+ (CHmodel.UNIT_MAP / 2) );
			
			nodeCircle.setCenterY(node.getPosition().getY()*CHmodel.UNIT_MAP 
					+ (CHmodel.UNIT_MAP / 2));
			
			node.setNodeCircle(nodeCircle);
			
			this.getChildren().add(nodeCircle);
			
			System.out.println("X " + nodeCircle.getCenterX() + "\n");
			System.out.println("Y " + nodeCircle.getCenterY() + "\n");
			System.out.println("\n");
			
		}
		
		
	}
	
	public Obstacle getObstacle(){
		return this.obs;
	}
	
	public ArrayList<Obstacle>  getObstacleList(){
		return obstacles;
	}

}
