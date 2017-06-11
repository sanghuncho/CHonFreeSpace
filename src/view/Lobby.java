package view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CHmodel;
import model.DrawingEdge;
import model.Lane;
import model.Obstacle;
import model.ObstacleFactory;
import model.ObstacleFair;
import model.Point;
import model.dijkstra.Edge;
import model.node.Node;
import model.node.NodeCircle;
import model.node.NodeMap;
import util.math.Vector;
import util.math.Vector2D;

public class Lobby extends BorderPane{
	
	public Button viaNodeButton = new Button("only via node");
	public Button contractButton = new Button("via node + CH");
	public Button searchButton = new Button("search");
	public Button refreshButton = new Button("refresh");
	public Obstacle obs;
	public ObstacleFair obsFair;
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
	//public ArrayList<LinkedList<Node>> listOfPath = new ArrayList<LinkedList<Node>>();
	public ArrayList<LinkedList<Node>> listOfPathHead = new ArrayList<LinkedList<Node>>();
	public ArrayList<LinkedList<Node>> listOfPathTail = new ArrayList<LinkedList<Node>>();
	public int[] costOfHomotopy = new int[100];
	public ArrayList<String> listOfPathCategory = new ArrayList<String>();
	public ArrayList<LinkedList<Node>> allOfPathHead = new ArrayList<LinkedList<Node>>();
	public ArrayList<LinkedList<Node>> allOfPathTail = new ArrayList<LinkedList<Node>>();
	private Point viaNode;
	private Point contractedNode;
	private Random randX;
	private Random randY;
	private int randomNumX;	
	private int randomNumY;
		
	private Scene scene;
	private int numberObs;
	private ArrayList<Point> viaNodes = new ArrayList<Point>();
	private ArrayList<Point> contractedPoints = new ArrayList<Point>();	
	private ArrayList<Edge> variousEdges = new ArrayList<Edge>(); 
	private ArrayList<Edge> shortcuts = new ArrayList<Edge>(); 
	private ArrayList<Lane> laneList = new ArrayList<Lane>();	

	private Polygon polygon;
	private int colorNumber = 0;
	public TextField numberOfViaNode;
	
	private CheckBox cbAll;
	private CheckBox cbShort;
	private CheckBox cbTop3;
	
	private RadioButton radioAll = new RadioButton("All");
	private RadioButton radioCH = new RadioButton("Homotopy");
	private RadioButton radioTop3 = new RadioButton("Top3");
	
	final ToggleGroup radioGroup = new ToggleGroup();
	private boolean permissionChange = false;

	int k=0;
	private Color laneColor;
	
	public Lobby(Stage stage){
		
		/**
		 * The node map is created.
		 * */
		createNodeMap();
				
		/**
		 * The multiple obstacles are generated by the input value.
		 */
		//numberObs = CHmodel.getObstacle();
		
		obstacles = new ArrayList<Obstacle>();
		
		
		/**
		 * obstacles are generated by regarding manual mode.
		 * */
		if(CHmodel.getMode().equals("manual")){
			
			numberObs = CHmodel.getNumberObstacle();
			
			for(int i = 0; i < numberObs; i++){
				
				//int id =i;
				obs = new Obstacle();
				
				/**
				 *test 6 obstacles
				 * */
				/*if(i == 0){
		    		obs.xProperty().set(150);
		    		obs.yProperty().set(200);
		    	}
		    	else if(i == 1){
		    		obs.xProperty().set(300);
		    		obs.yProperty().set(200);
		    	}
		    	else if(i == 2){
		    		obs.xProperty().set(450);
		    		obs.yProperty().set(200);
		    	}
		    	else if(i == 3){
		    		obs.xProperty().set(150);
		    		obs.yProperty().set(400);
		    	}
		    	else if(i == 4){
		    		obs.xProperty().set(300);
		    		obs.yProperty().set(400);
		    	}
		    	else if(i == 5){
		    		obs.xProperty().set(450);
		    		obs.yProperty().set(400);
		    	}*/
				
				if(i == 0){
		    		obs.xProperty().set(150);
		    		obs.yProperty().set(150);
		    	}
		    	else if(i == 1){
		    		obs.xProperty().set(300);
		    		obs.yProperty().set(150);
		    	}
		    	else if(i == 2){
		    		obs.xProperty().set(450);
		    		obs.yProperty().set(150);
		    	}
				
		    	else if(i == 3){
		    		obs.xProperty().set(150);
		    		obs.yProperty().set(300);
		    	}
		    	else if(i == 4){
		    		obs.xProperty().set(300);
		    		obs.yProperty().set(300);
		    	}
		    	else if(i == 5){
		    		obs.xProperty().set(450);
		    		obs.yProperty().set(300);
		    	}
				
		    	else if(i == 6){
		    		obs.xProperty().set(150);
		    		obs.yProperty().set(450);
		    	}
		    	else if(i == 7){
		    		obs.xProperty().set(300);
		    		obs.yProperty().set(450);
		    	}
		    	else if(i == 8){
		    		obs.xProperty().set(450);
		    		obs.yProperty().set(450);
		    	}
		    	
				
				obstacles.add(obs);
				/*Iterator itr = obstacles.iterator();
				
				while(itr.hasNext()){
					Obstacle obs = (Obstacle)itr.next();
					center.getChildren().add(obs);
				}*/
			}
		}
		
		/**
		 * obstacles are generated by regarding fair mode.
		 * */
		if(CHmodel.getMode().equals("fair")){
			
			ObstacleFactory factoryFair = new ObstacleFactory(obstacles);
			
			factoryFair.produce2X8Obstacle();
			factoryFair.produce40X1Obstacle();
			factoryFair.produce1X28Obstacle();
			factoryFair.produce2X2Obstacle();
			factoryFair.produce3X3Obstacle();
			factoryFair.produce2X7Obstacle();
			factoryFair.produce2X6Obstacle();
			factoryFair.produce3X4Obstacle();
			factoryFair.produce9X2Obstacle();
			factoryFair.produce3X2Obstacle();
			factoryFair.produce3X7Obstacle();
			factoryFair.produce6X2Obstacle();
			factoryFair.produce4X1Obstacle();
			factoryFair.produce1X5Obstacle();
		
			
			obstacles = factoryFair.getFactoryObstacleArray();
			numberObs = obstacles.size();
			
			/*Iterator itr = obstacles.iterator();
			
			while(itr.hasNext()){
				Obstacle obs = (Obstacle)itr.next();
				center.getChildren().add(obs);
			}*/
			
		}
			
		Iterator itr = obstacles.iterator();
		
		while(itr.hasNext()){
			Obstacle obs = (Obstacle)itr.next();
			center.getChildren().add(obs);
		}
		
		
		
		/**
		 * The staring - and end point object are created.
		 */
		startPoint= new Point();
		startPoint.setStart();
		
		endPoint= new Point();
		endPoint.setEnd();
		
		/*startPoint.setCenterX(CHmodel.getStartX());
		startPoint.setCenterY(CHmodel.getStartY());
		startPoint.setFill(Color.GREENYELLOW);*/
		
		/*endPoint.setCenterX(CHmodel.getGoalX());
		endPoint.setCenterY(CHmodel.getGoalY());
		endPoint.setFill(Color.RED);*/
		
		contractButton.setId("contractButtonLabel");
		
		
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
		
		
		final Pane leftSpacer = new Pane();
		leftSpacer.setMinWidth(40);
		
		numberOfViaNode = new TextField();
		numberOfViaNode.setMaxWidth(80);
		numberOfViaNode.setPromptText("Via Node");
		
		cbAll = new CheckBox("All");
		cbShort = new CheckBox("Homotopy");
		cbTop3 = new CheckBox("Top3");
		
		radioCH.setToggleGroup(radioGroup);
		radioCH.setSelected(true);
		
		radioTop3.setToggleGroup(radioGroup);
		radioAll.setToggleGroup(radioGroup);
		
		
		
		
		HBox hBox = new HBox();
		hBox.setSpacing(10);
		//hBox.getChildren().addAll(leftSpacer,cbShort,cbTop3,cbAll,numberOfViaNode,viaNodeButton,contractButton,searchButton);
		hBox.getChildren().addAll(leftSpacer,radioCH,radioTop3,radioAll,numberOfViaNode,viaNodeButton,contractButton,searchButton);
		
		final Pane leftSpacer_text = new Pane();
		leftSpacer_text.setMinWidth(40);
		HBox hBox_text = new HBox();
		hBox_text.setSpacing(10);
		text = new Text("");
		hBox_text.getChildren().addAll(leftSpacer_text,text);
		
		VBox vBox = new VBox();
		vBox.setSpacing(4);
		vBox.getChildren().addAll(hBox,hBox_text);
		
		bottom.getChildren().add(vBox);
		bottom.setMinWidth(50);
			
		left.setMinWidth(50);
		left.setMaxWidth(50);
		right.setMinWidth(50);
		right.setMaxWidth(50);
		
		top.setMinHeight(25);
		
		bottom.setMinHeight(25);

		scene = new Scene(this);
		stage.setHeight(CHmodel.getMapY()+100);
		stage.setWidth(CHmodel.getMapX()+100);
		stage.setTitle("Candidate Sets For Alternative Routes in Constrained Free Space Scenarios");
		 
		stage.setScene(scene);
			
	}
	
	public void setPermissionChange(boolean change){
		
		this.permissionChange = change;
	}
	
	/**
	 * @param number
	 * @return
	 * This function return each value of vertices of the obstacles.
	 */
	private ArrayList<Vector> getVertexOfObstalce(int number){
		
		Vector2D leftOver,leftBelow,rightOver,rightBelow;
		Obstacle obstacle = obstacles.get(number);
		int width = (int)obstacle.getWidth();
		int height = (int)obstacle.getHeight();
		
		ArrayList<Vector> vertexOfObstacle = new ArrayList<Vector>();
		
		leftOver = new Vector2D(obstacle.getXPoperty().get(),
				obstacle.getYPoperty().get());
		
		vertexOfObstacle.add(leftOver);
		
		rightOver = new Vector2D(obstacle.getXPoperty().get() + width,
				obstacle.getYPoperty().get());
		
		vertexOfObstacle.add(rightOver);
		
		rightBelow = new Vector2D(obstacle.getXPoperty().get() + width,
				obstacle.getYPoperty().get()+height);
		
		vertexOfObstacle.add(rightBelow);
		
		leftBelow = new Vector2D(obstacle.getXPoperty().get(),
				obstacle.getYPoperty().get()+height);
		
		vertexOfObstacle.add(leftBelow);
		
		
		return vertexOfObstacle;	
	}
	
	
	/**
	 * @param polygon
	 * @return
	 * In order to determine the homotopy class, list of paths which are different one another is called. 
	 */
	public ArrayList<Integer> getListPathID(Polygon polygon){
		
		int fourVertex = 4;
		ArrayList<Integer> listOfPathID = new ArrayList<Integer>();
		
		
		/**
		 * id is considered as obstacle.
		 */
		for(int id = 0; id < numberObs; id++){

			/**
			 * vertexOfobstacle includes the four position of obstacle.
			 */
			ArrayList<Vector> vertexOfObstacle = getVertexOfObstalce(id);
			
			int coveredPoint = 0;
			/*check whether the polygon include the four verteces of obstacle*/
			for(int k = 0; k < fourVertex; k++){
				
				int xPosVertex = vertexOfObstacle.get(k).getX();
				int yPosVertex = vertexOfObstacle.get(k).getY();
				/*System.out.println("obstacle X" + xPosVertex);
				System.out.println("obstacle Y" + yPosVertex);*/
				if(polygon.contains(xPosVertex,yPosVertex)){
					coveredPoint++;
				}
			}
			
			if(coveredPoint >= 3){
				listOfPathID.add(id);
			}
			
		}
			
		return listOfPathID;
					
	}
	
	/**
	 * the grid cells are created as the black circle and attached on the display that shows the alternative paths.
	 */
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
	
	
	
	
	/*this methode is made for removing the lane*/
	/*public void createLane(LinkedList<Node> path){
		
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
	
	public void removeLane(){
		
		int sizeLane = laneList.size();
			
			for(int j=0; j< sizeLane; j++){
				
					center.getChildren().remove(laneList.get(j));
			}
		
	}*/
	
	
	
	
	/**
	 * @param edges
	 * It shows the shortcut edges on the display.
	 */
	public void drawingShortcutEdges(ArrayList<Edge> edges){
		
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
	
	/**
	 * @param edges
	 *  It shows the all edges created on the display.
	 */
	public void drawingEdges(ArrayList<Edge> edges){
			
			int size = edges.size();
					
			for(int i=0; i < size; i++){
				
				Vector start = edges.get(i).getSource().getPosition();
				Vector goal = edges.get(i).getDestination().getPosition();
									
					if( !checkHomoEdge(edges.get(i)) ){
					
					DrawingEdge drawEdge = new DrawingEdge(10*(double)start.getX()+5,10*(double)start.getY() +5,
							10*(double)goal.getX()+5,10*(double)goal.getY()+5);
					
					variousEdges.add(edges.get(i));
					
					center.getChildren().add(drawEdge);
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
		
		
		while(insideObstacle(randomNumX,randomNumY,map) || isEdgeOfMap(randomNumX,randomNumY,map) ||
				isContractedNode(randomNumX,randomNumY,map) || isViaNode(randomNumX,randomNumY,map)){
			
				randX = new Random();
				randomNumX = randX.nextInt( size.getX());
				randY = new Random();
				randomNumY = randY.nextInt( size.getY());
				
				/*System.out.println("new random x " + randomNumX + "\n");
				System.out.println("new random y " + randomNumY + "\n");
				System.out.println("\n");*/
			
		}
		
		if( !insideObstacle(randomNumX,randomNumY,map) || !isContractedNode(randomNumX,randomNumY,map) 
				|| !isViaNode(randomNumX,randomNumY,map)){
			

			contractedNode = new Point();
			contractedNode.setCenterX(randomNumX*10+5);
			contractedNode.setCenterY(randomNumY*10+5);
			contractedNode.setFill(Color.WHITE);
			center.getChildren().add(contractedNode);
			contractedPoints.add(contractedNode);
			map[randomNumX][randomNumY] = CHmodel.VALUE_MAP_CONTRACTING;
			//System.out.println("contracted" + k);
			//k++;
			
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
	
	public void createViaNodePoint(Vector2D size, int[][] map,int loop_viaNode){
		
		randX = new Random();
		randomNumX = randX.nextInt( size.getX());
		randY = new Random();
		randomNumY = randY.nextInt( size.getY());

		
		//while( insideObstacle(randomNumX,randomNumY,map) || isContractedNode(randomNumX,randomNumY,map) ){
		while( insideObstacle(randomNumX,randomNumY,map) || isViaNode(randomNumX,randomNumY,map) ){
			randX = new Random();
			randomNumX = randX.nextInt(size.getX());
			randY = new Random();
			randomNumY = randY.nextInt(size.getY());
		
		}
		
		//if( !insideObstacle(randomNumX,randomNumY,map) && !isContractedNode(randomNumX,randomNumY,map)){
		if( !insideObstacle(randomNumX,randomNumY,map) && !isViaNode(randomNumX,randomNumY,map)){
			viaNode = new Point();
			viaNode.setCenterX(randomNumX*10+5);
			viaNode.setCenterY(randomNumY*10+5);
			viaNode.setFill(Color.YELLOW);
			center.getChildren().add(viaNode);
			map[randomNumX][randomNumY] = CHmodel.VALUE_MAP_VIA_NODE;
			viaNodes.add(viaNode);
		}
		
	}
	
	private boolean insideObstacle(int randomX, int randomY, int[][] map){
		
		if( (map[randomX][randomY]) == -1 || (map[randomX][randomY]) == 0){
			
			if((map[randomX][randomY]) == 0){
				
				//System.out.println("map = 0 \n");
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
	
	private boolean isViaNode(int randomX, int randomY, int[][] map){		
		
		if( (map[randomX][randomY]) == 3){
		
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
	
	public Point getStartPoint(){
		return startPoint;
	}
	public Point getEndPoint(){
		return endPoint;
	}

	/*public Vector2D getViaNode2D(){
		return new Vector2D(( viaNode.getCenterX()-5 )/10,( viaNode.getCenterY()-5 )/10);
	}*/
	
	/*public Vector2D getViaNodeScreen(){
		return new Vector2D( viaNode.getCenterX(),viaNode.getCenterY());
	}*/
	
	
	public Polygon generatePolygon(LinkedList<Node> head_first, LinkedList<Node> tail_first
			,LinkedList<Node> head_second,LinkedList<Node> tail_second){
	
		polygon = new Polygon();
		
		int k = head_first.size();
		int l = tail_first.size();
		int m = head_second.size();
		int n = tail_second.size();
		
		double finalX=head_first.get(k-1).getPosition().getX();
		double finalY=head_first.get(k-1).getPosition().getY();
		
		for(int i = 0 ; i < k ; i ++){
			
			double pointX = head_first.get(i).getPosition().getX();
			
			double pointY = head_first.get(i).getPosition().getY();
			
			polygon.getPoints().add(pointX*10 +5);
			polygon.getPoints().add(pointY*10 +5);
				
		}
		
		
		double finalX_tail=tail_first.get(l-1).getPosition().getX();
		double finalY_tail=tail_first.get(l-1).getPosition().getY();
		for(int i = 1 ; i <= l ; i ++){
				
				double pointX = tail_first.get(l-i).getPosition().getX();
				double pointY = tail_first.get(l-i).getPosition().getY();
				polygon.getPoints().add(pointX*10 +5);
				polygon.getPoints().add(pointY*10 +5);
					
			}
		
		for(int i = 0 ; i < n ; i++){
			
			double pointX = tail_second.get(i).getPosition().getX();
			double pointY = tail_second.get(i).getPosition().getY();
			polygon.getPoints().add(pointX*10 +5);
			polygon.getPoints().add(pointY*10 +5);
				
		}
		
		polygon.getPoints().add(finalX_tail*10 +5);
		polygon.getPoints().add(finalY_tail*10 +5);
		
		
		
		for(int i = 1 ; i <= m ; i ++){
			
			double pointX = head_second.get(m-i).getPosition().getX();
			double pointY = head_second.get(m-i).getPosition().getY();
			polygon.getPoints().add(pointX*10 +5);
			polygon.getPoints().add(pointY*10 +5);
				
		}
		polygon.getPoints().add(finalX*10 +5);
		polygon.getPoints().add(finalY*10 +5);
		
		
		
		

		
		
		
		
		return polygon;
	}

	
	/*for test*/
	/*public Polygon generatePolygon(LinkedList<Node> head_first, LinkedList<Node> tail_first
			,LinkedList<Node> head_second,LinkedList<Node> tail_second){
	
		polygon = new Polygon();
		
		int k = head_first.size();
		int l = tail_first.size();
		int m = head_second.size();
		int n = tail_second.size();
		
		double finalX=head_first.get(k-1).getPosition().getX();
		double finalY=head_first.get(k-1).getPosition().getY();
		
		for(int i = 0 ; i < k ; i ++){
			
			double pointX = head_first.get(i).getPosition().getX();
			System.out.println("fisrst head  pointX: "+ pointX);
			
			double pointY = head_first.get(i).getPosition().getY();
			System.out.println("first head  pointY: "+ pointY);
			
			polygon.getPoints().add(pointX*10 +5);
			polygon.getPoints().add(pointY*10 +5);
				
		}
		
		
		for(int i = 0 ; i < m ; i ++){
			
			double pointX = head_second.get(i).getPosition().getX();
			System.out.println("secons head pointX: "+ pointX);
			double pointY = head_second.get(i).getPosition().getY();
			System.out.println("second head pointY: "+ pointY);
			polygon.getPoints().add(pointX*10 +5);
			polygon.getPoints().add(pointY*10 +5);
				
		}
		polygon.getPoints().add(finalX*10 +5);
		polygon.getPoints().add(finalY*10 +5);
		
		
		
		double finalX_tail=tail_first.get(l-1).getPosition().getX();
		double finalY_tail=tail_first.get(l-1).getPosition().getY();
		for(int i = 0 ; i < l ; i ++){
				
				double pointX = tail_first.get(i).getPosition().getX();
				System.out.println("first tail pointX: "+ pointX);
				double pointY = tail_first.get(i).getPosition().getY();
				System.out.println("first tail pointY: "+ pointY);
				polygon.getPoints().add(pointX*10 +5);
				polygon.getPoints().add(pointY*10 +5);
					
			}

		for(int i = 0 ; i < n ; i++){
			
			double pointX = tail_second.get(i).getPosition().getX();
			System.out.println("second tail pointX: "+ pointX);
			double pointY = tail_second.get(i).getPosition().getY();
			System.out.println("second tail pointY: "+ pointY);
			polygon.getPoints().add(pointX*10 +5);
			polygon.getPoints().add(pointY*10 +5);
				
		}
		
		polygon.getPoints().add(finalX_tail*10 +5);
		polygon.getPoints().add(finalY_tail*10 +5);
		
		
		
		return polygon;
	}
*/	
	public Polygon getPolygon(){
		return polygon;
	}
	
	public void setText(int sizeOfEdges, boolean applyCH){
		
		int numberOfNode = nodemap.getSizeNode();
		int numberContracted = 0;
		
		if(applyCH == true){
			numberContracted = CHmodel.getNumberContractedCustom();
		}
		else{
		}
		String liveNumberNode = Integer.toString(numberOfNode-numberContracted);
		
		int variousEdges = sizeOfEdges/2;
				
		text.setText("the number of nodes : " + liveNumberNode
				+ ",  the number of edges : " + variousEdges);
		
	}
	
	public ArrayList<LinkedList<Node>> getListOfPathHead(){
		
		return listOfPathHead;
		
	}
	
	public ArrayList<LinkedList<Node>> getListOfPathTail(){
		
		return listOfPathTail;
		
	}
	/*public ArrayList<LinkedList<Node>> getListPath(){
		return listOfPath;
	}*/
	
	public ArrayList<String> getPathCategory(){
		
		return listOfPathCategory;
	}
	/**
	 * @param pathId
	 * @param head_path
	 * @param tail_path
	 * @param numberViaNode
	 */
	public void setPathCategory(String pathId, LinkedList<Node> head_path,LinkedList<Node> tail_path,
			int numberViaNode,int nextViaNodeX,int nextViaNodeY,int distance_next){
		
		
		int length = listOfPathCategory.size();
		boolean homotopy = false;
		
		/**
		 * find the homotopy class
		 */
		for(int i = 0; i < length; i++){
			
			/**
			 * classified homotopy classes and check the obstacles of homotopy class.
			 */
			String stringIdPath = listOfPathCategory.get(i);
			
			
			/**
			 * there is already suitable homotpy class in the list.
			 * */ 
			if(stringIdPath.equals(pathId)){
				
				//here implement the method of compare with cost and turn value between two homotopy path
				homotopy = true;
				
				if(costOfHomotopy[i] >= distance_next){
					
					listOfPathHead.remove(i);
					listOfPathHead.add(i,head_path);
					listOfPathTail.remove(i);
					listOfPathTail.add(i,tail_path);
					costOfHomotopy[i] = distance_next;
					
				}
				
			}					
		}
		
		if(!homotopy){
			
			listOfPathCategory.add(pathId);
			listOfPathHead.add(length, head_path);
			listOfPathTail.add(length, tail_path);
			costOfHomotopy[length] = distance_next;
		}
		
		evaluation_1(listOfPathCategory.size(), numberViaNode,nextViaNodeX,nextViaNodeY);
		
	  
	}
	
	/**
	 * @param lengthOfCategory
	 * @param numberViaNode
	 * test for the relation between the homotopy clss and the number of the via-node. 
	 */
	private void evaluation_1(int lengthOfCategory,int numberViaNode,int nextViaNodeX,int nextViaNodeY){
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss.SSS");    
		Date resultdate = new Date(System.currentTimeMillis());
		System.out.println("the number of homotopy class : " + lengthOfCategory 
				+ " the numebr of via-node : " + numberViaNode + ", time : " 
				+ sdf.format(resultdate));
		
		
	}
	public void setAllPaths(LinkedList<Node> head_path,LinkedList<Node> tail_path){
			
		allOfPathHead.add(head_path);
		allOfPathTail.add(tail_path);
		
	}
	
	public void createHomotopyLane(){
		
		
		/**
		 * if the top3-checkbox is selected on lobby, 
		 * then only three shortest paths of homotopy classes are displayed.
		 * */  
		//if(cbTop3.isSelected()){
		if(radioTop3.isSelected()){	
			createTop3HomotopyLane();
			
						
		}
		/**
		 * if the representative-checkbox is selected on lobby, 
		 * then all shortest paths of homotopy classes are displayed.
		 * */ 
		//if(cbShort.isSelected()){
		if(radioCH.isSelected()){
			createAllHomotopyLane();
			
		}
		
	}
	public void createAllLane(){
		
		int head_size = allOfPathHead.size();
				
		for(int i=0; i< head_size; i++){

			createLane(allOfPathHead.get(i));	
			createLane(allOfPathTail.get(i));
				
			}
		
	}	
	private void createAllHomotopyLane(){
		
		int head_size = listOfPathHead.size();
		
		System.out.println("head size : " + head_size);
		
		for(int i=0; i< head_size; i++){

			createLane(listOfPathHead.get(i));	
			createLane(listOfPathTail.get(i));
				
		}
	}
	private void createTop3HomotopyLane(){
		int length = listOfPathCategory.size();
		int[] costArray = new int[length];
		
		int minimum,se_minimum,th_minimum;
		
		
		for (int i = 0; i< length; i++) {
			
			   costArray[i] = costOfHomotopy[i];
		}
		
		Arrays.sort(costArray);
		
		minimum = costArray[0];
		se_minimum = costArray[1];
		th_minimum = costArray[2];
		
		int min_index = 0,se_min_index = 0, th_min_index = 0;
		
		for(int i = 0; i < costOfHomotopy.length; i++){
			
			int value = costOfHomotopy[i];
			
			if(value == minimum){
				
				min_index = i;
				
			}
		}
			
		for(int i = 0; i < costOfHomotopy.length; i++){
			
			int value = costOfHomotopy[i];
			
			if((value == se_minimum) && (min_index != i)){
				
				se_min_index = i;
				
			}
		}
		
		for(int i = 0; i < costOfHomotopy.length; i++){
			
			int value = costOfHomotopy[i];
			
			if((value == th_minimum) && (min_index != i) && (se_min_index != i)){
				
				th_min_index = i;
				
			}
		}
		
		
				
			createLane(listOfPathHead.get(min_index));	
			createLane(listOfPathTail.get(min_index));
			
			createLane(listOfPathHead.get(se_min_index));	
			createLane(listOfPathTail.get(se_min_index));
			
			createLane(listOfPathHead.get(th_min_index));	
			createLane(listOfPathTail.get(th_min_index));
	
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
	
	public void createLane(LinkedList<Node> path){
	
		int k = path.size();
		
		for(int i = 0 ; i < k-1 ; i ++){
			
			Vector start = path.get(i).getPosition();	
			Vector goal = path.get(i+1).getPosition();
			
			Lane lane = new Lane(10*(double)start.getX()+5,10*(double)start.getY() +5,
					10*(double)goal.getX()+5,10*(double)goal.getY()+5);

			lane.setStroke(getLaneColor(colorNumber));
			
			if(center == null){
				System.out.println("center is null");
			}
			if(center.getChildren() == null){
				System.out.println("centerchildren is null");
			}
			center.getChildren().add(lane);
			laneList.add(lane);
			
			
		}
		/*if either homotpy or top3 on lobby is checked,
		 * then various color is applied*/
		//if(cbShort.isSelected() || cbTop3.isSelected()){
		if(radioCH.isSelected() || radioTop3.isSelected()){
			colorNumber++;
		}
		
	}
	
	public void removeLane(){
		
		int sizeLane = laneList.size();
			
			for(int j=0; j< sizeLane; j++){
				
					center.getChildren().remove(laneList.get(j));
			}
		
	}
	
	public void drawPolygon(Polygon polygon){
		
		
		Color color=new Color(0f,1f,0f,.5f );
		polygon.setFill(color);
		center.getChildren().add(polygon);
		
		
		
	}
	private Color getLaneColor(int numberLane){
		
		
		int number = numberLane;
		Color laneColor = null;
		
		switch(number) {
			
			case 0 : laneColor = Color.PINK;
			break;
			
			case 1 : laneColor = Color.PINK;
			break;
			
			case 2 : laneColor = Color.BROWN;
			break;
			
			case 3 : laneColor = Color.BROWN;
			break;
			
			case 4 : laneColor = Color.DARKGREEN;
			break;
			
			case 5 : laneColor = Color.DARKGREEN;
			break;
			
			case 6 : laneColor = Color.RED;
			break;
			
			case 7 : laneColor = Color.RED;
			break;
			
			case 8 : laneColor = Color.CHARTREUSE;
			break;
			
			case 9 : laneColor = Color.CHARTREUSE;
			break;
			
			case 10 : laneColor = Color.PURPLE;
			break;
			
			case 11 : laneColor = Color.PURPLE;
			break;
			
			case 12 : laneColor = Color.GREY;
			break;
			
			case 13 : laneColor = Color.GREY;
			break;
			
			case 14 : laneColor = Color.AQUAMARINE;
			break;
			
			case 15 : laneColor = Color.AQUAMARINE;
			break;
			
			case 16 : laneColor = Color.GOLDENROD;
			break;
			
			case 17 : laneColor = Color.GOLDENROD;
			break;
			
			case 18 : laneColor = Color.CORNFLOWERBLUE;
			break;
			
			case 19 : laneColor = Color.CORNFLOWERBLUE;
			break;
			
			case 20 : laneColor = Color.CHOCOLATE;
			break;
			
			case 21 : laneColor = Color.CHOCOLATE;
			break;
			
			case 22 : laneColor = Color.CRIMSON;
			break;
			
			case 23 : laneColor = Color.CRIMSON;
			break;
			
			case 24 : laneColor = Color.CADETBLUE;
			break;
			
			case 25 : laneColor = Color.CADETBLUE;
			break;
			
			case 26 : laneColor = Color.DARKGOLDENROD;
			break;
			
			case 27 : laneColor = Color.DARKGOLDENROD;
			break;
			
			case 28 : laneColor = Color.DARKKHAKI;
			break;
			
			case 29 : laneColor = Color.DARKKHAKI;
			break;
			
			case 30 : laneColor = Color.FUCHSIA;
			break;
			
			case 31 : laneColor = Color.FUCHSIA;
			break;
		}
		
		return laneColor;
	}
	
	public int getnumberOfViaNode(){
		
		return Integer.parseInt(numberOfViaNode.getText());
		
	}
	
	public void setProgessBar(double value){
		
		/*final ProgressBar bar = new ProgressBar();
		bar.setProgress(value);
		.getChildren().add(bar);*/
	}
	public CheckBox getCheckBoxAll(){
		
		return cbAll;
	}
	public CheckBox getCheckBoxShort(){
		
		return cbShort;
	}
	public CheckBox getCheckBoxTop3(){
		
		return cbTop3;
	}
	
	public RadioButton getRadioButtonAll(){
		
		return radioAll;
	}
	public RadioButton getRadioButtonCH(){
		
		return radioCH;
	}
	public RadioButton getRadioButtonTop3(){
		
		return radioTop3;
	}
	public ToggleGroup getRadioGroup(){
		return radioGroup;
	}
	
	public int[] getCostOfHomotopy(){
		return costOfHomotopy;
	}
	public void initiateColorNumber(){
		colorNumber = 0;
	}
	
}
