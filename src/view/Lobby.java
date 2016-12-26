package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

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
import model.Lane;
import model.Obstacle;
import model.Point;
import model.node.Node;
import model.node.NodeCircle;
import model.node.NodeMap;
import util.math.Vector;
import util.math.Vector2D;

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
	public Pane right = new Pane();
	public Pane left = new Pane();
	
	private Point viaNode;
	private Random randX;
	private Random randY;
	private int randomNumX;	
	private int randomNumY;

	
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
		
		//this.setBottom(bottom);
		this.setCenter(center);
		this.setRight(right);
		this.setLeft(left);
		
		//BorderPane.setAlignment(bottom, Pos.TOP_CENTER);
		BorderPane.setAlignment(center, Pos.TOP_CENTER);
		BorderPane.setAlignment(right, Pos.TOP_LEFT);
		
		
		
		center.setMaxSize(1000,600);
		center.getChildren().addAll(startPoint,endPoint);
		
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.getChildren().addAll(contractButton,searchButton);
		right.setMaxSize(100, 200);
		right.getChildren().add(vBox);
		
		left.setMaxSize(100,200);
		
		//bottom.setMaxSize(600,50);
		//bottom.getChildren().addAll(contractButton,searchButton);
		//bottom.getChildren().add(hBox);

		scene = new Scene(this);
		stage.setHeight(CHmodel.getMapX()+50);
		stage.setWidth(CHmodel.getMapY()+50);
		
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
			
			center.getChildren().add(nodeCircle);
			
		}	
	}
	
	public Obstacle getObstacle(){
		return this.obs;
	}
	
	public ArrayList<Obstacle>  getObstacleList(){
		return obstacles;
	}
	
	public void createLane(LinkedList<Node> path){
		
		int k = path.size();
		
		for(int i = 0 ; i < k-1 ; i ++){
			
			Vector start = path.get(i).getPosition();
			Vector goal = path.get(i+1).getPosition();
			Lane lane = new Lane(10*(double)start.getX()+5,10*(double)start.getY() +5,
					10*(double)goal.getX()+5,10*(double)goal.getY()+5);
			center.getChildren().add(lane);
		}
		
	}
	
	public void createViaNodePoint(Vector2D size, int[][] map){
		
		
		
		randX = new Random();
		int randomNumX = randX.nextInt( size.getX());
		randY = new Random();
		int randomNumY = randY.nextInt( size.getY());
		
		System.out.println("size x " + size.getX() + "\n");
		System.out.println("size y " + size.getY() + "\n");
		
		System.out.println("random x " + randomNumX + "\n");
		System.out.println("random y " + randomNumY + "\n");
		
		/*while(!insideObstacle(randomNumX,randomNumY,map)){
			
				randX = new Random();
				randomNumX = randX.nextInt( size.getX());
				randY = new Random();
				randomNumY = randY.nextInt( size.getY());		
			}*/
		
		viaNode = new Point();
		viaNode.setCenterX(randomNumX*10+5);
		viaNode.setCenterY(randomNumY*10+5);
		viaNode.setFill(Color.DARKGOLDENROD);
		center.getChildren().add(viaNode);	
				
	}
	
	public Vector2D getViaNode2D(){
		return new Vector2D(( viaNode.getCenterX()-5 )/10,( viaNode.getCenterY()-5 )/10);
	}
	
	private boolean insideObstacle(int randomX, int randomY,int[][] map){
		
		if( (map[randomX][randomY]) == -1){
			return false;
			
		}
		else{
			return true;
		}
		
	}

}
