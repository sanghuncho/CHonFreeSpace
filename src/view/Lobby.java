package view;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
	
	public Button contractButton = new Button("Contracting");
	public Button searchButton = new Button("Searching");

	public Obstacle obs;
	public ArrayList<Obstacle>  obstacles;
	public Point startPoint;
	public Point endPoint;
	public NodeMap nodemap;
	public Pane bottom = new Pane();
	public Pane center = new Pane();
	
	private Scene scene;
	private int numberObs;
	
	
	public Lobby(Stage stage){
		
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
			center.getChildren().add(obs);
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
		
		/*create search button for CH algorithm*/
		contractButton.setId("contractButtonLabel");
		
		this.setBottom(bottom);
		this.setCenter(center);
		
		BorderPane.setAlignment(bottom, Pos.TOP_CENTER);
		BorderPane.setAlignment(center, Pos.TOP_CENTER);
		
		center.setMaxSize(600,600);
		center.getChildren().addAll(startPoint,endPoint);
		
		HBox hBox = new HBox();
		hBox.setSpacing(10);
		hBox.getChildren().addAll(contractButton,searchButton);
		bottom.setMaxSize(600,50);
		//bottom.getChildren().addAll(contractButton,searchButton);
		bottom.getChildren().add(hBox);

		scene = new Scene(this);
		stage.setHeight(CHmodel.getMapX()+100);
		stage.setWidth(CHmodel.getMapY()+100);
		
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
			
			//this.getChildren().add(nodeCircle);
			center.getChildren().add(nodeCircle);
			
//			System.out.println("X " + nodeCircle.getCenterX() + "\n");
//			System.out.println("Y " + nodeCircle.getCenterY() + "\n");
//			System.out.println("\n");
			
		}
		
		
	}
	
	public Obstacle getObstacle(){
		return this.obs;
	}
	
	public ArrayList<Obstacle>  getObstacleList(){
		return obstacles;
	}

}
