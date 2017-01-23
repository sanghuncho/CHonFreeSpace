package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CHmodel;
import model.DrawingEdge;
import model.Lane;
import model.Obstacle;
import model.Point;
import model.dijkstra.Edge;
import model.node.Node;
import model.node.NodeCircle;
import model.node.NodeMap;
import util.math.Vector;
import util.math.Vector2D;

public class Lobby extends BorderPane{
	
	public Button viaNodeButton = new Button("only via-node");
	public Button contractButton = new Button("via-node + CH");
	public Button searchButton = new Button("search");
	public Button refreshButton = new Button("refresh");
	
	public Obstacle obs;
	public ArrayList<Obstacle>  obstacles;
	public Point startPoint;
	public Point endPoint;
	public NodeMap nodemap;
	public Pane bottom = new Pane();
	public Pane center = new Pane();
	public Pane right = new Pane();
	public Pane left = new Pane();
	public Pane top = new Pane();
	public Text text;
	public int sizeOfEdges;
	public int diverseEdges;
	
	private Point viaNode;
	private Point contractedNode;
	private Random randX;
	private Random randY;
	private int randomNumX;	
	private int randomNumY;
	
	private int numberLane=0;

	
	private Scene scene;
	private int numberObs;
	private ArrayList<Point> viaNodes = new ArrayList<Point>();
	private ArrayList<Point> contractedPoints = new ArrayList<Point>();	
	ArrayList<Edge> variousEdges = new ArrayList<Edge>(); 
	private ArrayList<Lane> laneList = new ArrayList<Lane>();	

	
	private Polygon polygon;
	private LinkedList<Node> body;
	
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
		this.setTop(top);
		this.setBottom(bottom);
		
		BorderPane.setAlignment(left, Pos.TOP_LEFT);
		BorderPane.setAlignment(center, Pos.TOP_CENTER);
		BorderPane.setAlignment(right, Pos.TOP_RIGHT);
		BorderPane.setAlignment(top, Pos.TOP_CENTER);
		BorderPane.setAlignment(bottom, Pos.TOP_CENTER);
		
		
		
		center.setMaxSize(1000,1000);
		center.getChildren().addAll(startPoint,endPoint);
		
		/*VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.getChildren().addAll(viaNodeButton,contractButton,searchButton,refreshButton);*/
		
		
		final Pane leftSpacer = new Pane();
		leftSpacer.setMinWidth(150);
		HBox hBox = new HBox();
		hBox.setSpacing(10);
		hBox.getChildren().addAll(leftSpacer,viaNodeButton,contractButton,searchButton,refreshButton);
		
		
		final Pane leftSpacer_text = new Pane();
		leftSpacer_text.setMinWidth(150);
		HBox hBox_text = new HBox();
		hBox_text.setSpacing(10);
		text = new Text("");
		hBox_text.getChildren().addAll(leftSpacer_text,text);
		
		VBox vBox = new VBox();
		vBox.setSpacing(4);
		vBox.getChildren().addAll(hBox,hBox_text);
		
		
		//bottom.getChildren().addAll(hBox,hBox_text);
		bottom.getChildren().add(vBox);
		bottom.setMinWidth(50);
			
		left.setMinWidth(50);
		
		top.setMinHeight(25);
		
		bottom.setMinHeight(25);
		

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
			
			center.getChildren().add(nodeCircle);
			
		}	
	}
	
	public Obstacle getObstacle(){
		return this.obs;
	}
	
	public ArrayList<Obstacle>  getObstacleList(){
		return obstacles;
	}
	
	/*public void createLane(LinkedList<Node> path){
		
		int k = path.size();
		
		for(int i = 0 ; i < k-1 ; i ++){
			
			Vector start = path.get(i).getPosition();
			Vector goal = path.get(i+1).getPosition();
			Lane lane = new Lane(10*(double)start.getX()+5,10*(double)start.getY() +5,
					10*(double)goal.getX()+5,10*(double)goal.getY()+5);
			center.getChildren().add(lane);
			
		}
		
	}*/
	
	/*for removeLane*/
	public void createLane(LinkedList<Node> path){
		
		int k = path.size();
		
		for(int i = 0 ; i < k-1 ; i ++){
			
			Vector start = path.get(i).getPosition();
			Vector goal = path.get(i+1).getPosition();
			Lane lane = new Lane(10*(double)start.getX()+5,10*(double)start.getY() +5,
					10*(double)goal.getX()+5,10*(double)goal.getY()+5);
			center.getChildren().add(lane);
			laneList.add(lane);
			
		}
		
	}
	
	private int getSizeOfCenterObj(){
		
		return center.getChildren().size();
	}
	
	public void removeLane(){
		
		int sizeObj = getSizeOfCenterObj();
		System.out.println("sizeObj : " + sizeObj + "\n" );
		int sizeLane = laneList.size();
		System.out.println("laneSize : " + sizeLane + "\n" );
		int k=0;
		
		//for(int i=0; i< sizeObj; i++){
			
			for(int j=0; j< sizeLane; j++){
			
			
				//if(center.getChildren().get(i).equals(laneList.get(j))){
				
					center.getChildren().remove(laneList.get(j));
			
				//}
			}
			//k++;
		//}
		
		//System.out.println("K : " + k + "\n" );

		
	}
	
	public void drawingEdges(ArrayList<Edge> edges){
		
		int size = edges.size();
				
		for(int i=0; i < size; i++){
			
			Vector start = edges.get(i).getSource().getPosition();
			Vector goal = edges.get(i).getDestination().getPosition();
			
			if(checkLengthEdge(edges.get(i))){
				
				if( !checkHomoEdge(edges.get(i)) ){
				
				DrawingEdge drawEdge = new DrawingEdge(10*(double)start.getX()+5,10*(double)start.getY() +5,
						10*(double)goal.getX()+5,10*(double)goal.getY()+5);
				
				variousEdges.add(edges.get(i));
				
				center.getChildren().add(drawEdge);
				}
			}
			
		}
		
		
		
	}
	private boolean checkLengthEdge(Edge edge){
		
		int startX = edge.getSource().getPosition().getX();
		int startY = edge.getSource().getPosition().getY();
		int goalX = edge.getDestination().getPosition().getX();
		int goalY = edge.getDestination().getPosition().getY();
		double lengthX = (startX - goalX)*(startX - goalX);
		double lengthY = (startY - goalY)*(startY - goalY);
		
		double length = Math.sqrt(lengthX + lengthY);
		
		if(length > Math.sqrt(2.0)){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	private boolean checkHomoEdge(Edge edge){
		
		sizeOfEdges = variousEdges.size();
		
		if(variousEdges == null){
			return false;
		}
		else{
			for(int i = 0; i < sizeOfEdges; i++){
				
				if( (variousEdges.get(i).getSource().equals(edge.getSource()) 
						&& variousEdges.get(i).getDestination().equals(edge.getDestination()))
							|| (variousEdges.get(i).getSource().equals(edge.getDestination())
								&& variousEdges.get(i).getDestination().equals(edge.getSource()))){
					return true;
				}
				else{
					
				}
			
			}
			return false;
	
		}
		
		
		
		
	}
	
	/*public void checkHomoEdge(ArrayList<Edge> edges){
		
		int sizeOfEdges = edges.size();
		
		variousEdges.add(edges.get(0));
		
		diverseEdges = 1;
		
		for(int i = 1; i < edges.size(); i++){
				
			for(int j = 0; j< variousEdges.size(); j++ ){
				
				if( (edges.get(i).getSource().equals(variousEdges.get(j).getSource()) 
						&& edges.get(i).getDestination().equals(variousEdges.get(j).getDestination()))
							|| (edges.get(i).getSource().equals(variousEdges.get(j).getDestination())
								&& edges.get(i).getDestination().equals(variousEdges.get(j).getSource()))){
					
				}
				else{
					
				}
			
			}
	
		}
	}*/
	
	
	
	public void generateContractedPoint(Vector2D size, int[][] map){
		
		randX = new Random();
		randomNumX = randX.nextInt( size.getX());
		randY = new Random();
		randomNumY = randY.nextInt( size.getY());
		/*
		System.out.println("size x " + size.getX() + "\n");
		System.out.println("size y " + size.getY() + "\n");
		*/
		/*System.out.println("random x " + randomNumX + "\n");
		System.out.println("random y " + randomNumY + "\n");*/
	
		while(insideObstacle(randomNumX,randomNumY,map) || isEdgeOfMap(randomNumX,randomNumY,map)){
			
				randX = new Random();
				randomNumX = randX.nextInt( size.getX());
				randY = new Random();
				randomNumY = randY.nextInt( size.getY());
				
				/*System.out.println("new random x " + randomNumX + "\n");
				System.out.println("new random y " + randomNumY + "\n");
				System.out.println("\n");*/
			
			}
		
		if( !insideObstacle(randomNumX,randomNumY,map)){
			

			contractedNode = new Point();
			contractedNode.setCenterX(randomNumX*10+5);
			contractedNode.setCenterY(randomNumY*10+5);
			contractedNode.setFill(Color.WHITE);
			center.getChildren().add(contractedNode);
			contractedPoints.add(contractedNode);
			map[randomNumX][randomNumY] = CHmodel.VALUE_MAP_CONTRACTING;
		}
		
	}
	
	public boolean isEdgeOfMap (int randomNumX, int randomNumY, int[][] map){
		
		if(randomNumX == 0 || randomNumY == 0 || 
				randomNumX == (CHmodel.getNodeMapSizeX()-1) || randomNumY == (CHmodel.getNodeMapSizeY()-1)){
			
			return true;
			
		}
		else{
			return false;
		}
		
		
	}
	
	public void createViaNodePoint(Vector2D size, int[][] map){
		
		randX = new Random();
		randomNumX = randX.nextInt( size.getX());
		randY = new Random();
		randomNumY = randY.nextInt( size.getY());

	
		while(insideObstacle(randomNumX,randomNumY,map)){
			
				randX = new Random();
				randomNumX = randX.nextInt( size.getX());
				randY = new Random();
				randomNumY = randY.nextInt( size.getY());
				
				/*System.out.println("new random x " + randomNumX + "\n");
				System.out.println("new random y " + randomNumY + "\n");
				System.out.println("\n");*/
			
			}
		
		if( !insideObstacle(randomNumX,randomNumY,map) && !isContractedNode(randomNumX,randomNumY,map)){
			
			viaNode = new Point();
			viaNode.setCenterX(randomNumX*10+5);
			viaNode.setCenterY(randomNumY*10+5);
			viaNode.setFill(Color.DARKGOLDENROD);
			center.getChildren().add(viaNode);
			viaNodes.add(viaNode);
		}
		
	}
	
	private boolean insideObstacle(int randomX, int randomY, int[][] map){
		
		if( (map[randomX][randomY]) == -1 || (map[randomX][randomY]) == 0){
			
			if((map[randomX][randomY]) == 0){
				
				System.out.println("map = 0 \n");
			}
			return true;
			
		}
		else{
			return false;
		}
		
	}
	private boolean isContractedNode(int randomX, int randomY, int[][] map){		
		
		if( (map[randomX][randomY]) == 2){
		
			return true;
			
		}
		else{
			return false;
		}
		
	}
	
	public Vector2D getViaNode2D(int i){

		return new Vector2D((viaNodes.get(i).getCenterX()-5)/10,(viaNodes.get(i).getCenterY()-5)/10);
	}
	
	public ArrayList<Point> getViaNodes(){
		
		return viaNodes;
	}
	
	public int getViaNodeSize(){
		return viaNodes.size();
	}

	/*public Vector2D getViaNode2D(){
		return new Vector2D(( viaNode.getCenterX()-5 )/10,( viaNode.getCenterY()-5 )/10);
	}*/
	
	/*public Vector2D getViaNodeScreen(){
		return new Vector2D( viaNode.getCenterX(),viaNode.getCenterY());
	}*/
	
	public void generatePolygon(LinkedList<Node> head, LinkedList<Node> tail){
		
		
		head.addAll(tail);
		int k = head.size();
		polygon = new Polygon();
		
		for(int i = 0 ; i < k-1 ; i ++){
			
			double pointX = head.get(i).getPosition().getX();
			double pointY = head.get(i).getPosition().getY();
			
			polygon.getPoints().add(pointX*10 +5);
			polygon.getPoints().add(pointY*10 +5);
				
		}
		
	    
	    polygon.setFill(Color.CADETBLUE);
	    
	    center.getChildren().add(polygon);
	    
	    
//	    int k = path.size();
//		
//		for(int i = 0 ; i < k-1 ; i ++){
//			
//			Vector start = path.get(i).getPosition();
//			Vector goal = path.get(i+1).getPosition();
//			Lane lane = new Lane(10*(double)start.getX()+5,10*(double)start.getY() +5,
//					10*(double)goal.getX()+5,10*(double)goal.getY()+5);
//			center.getChildren().add(lane);
//			
//		}
		
	    
	    
	}
	public Polygon getPolygon(){
		return polygon;
	}
	
	public void setText(int sizeOfEdges, boolean applyCH){
		
		int numberOfNode = nodemap.getSizeNode();
		int numberContracted = 0;
		
		if(applyCH == true){
			numberContracted = CHmodel.getNumberContracted();
		}
		else{
		}
		String liveNumberNode = Integer.toString(numberOfNode-numberContracted);
		
		//int variousEdge = variousEdges.size();
		int variousEdges = sizeOfEdges/2;
				
		text.setText("the number of nodes : " + liveNumberNode
				+ ",  the number of edges : " + variousEdges);
		
	}
	
	

}
