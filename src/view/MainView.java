package view;



import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends Pane{
	
	private Scene scene;
	
	public Button createMap; 
	public TextField mapXField;
	public TextField mapYField;
	public TextField startPointX;
	public TextField startPointY;
	public TextField endPointX;
	public TextField endPointY;
	public TextField numberOfObstacle;
	
	public MainView(Stage stage){
		
		this.setId("mainMenu");
		
		VBox vbox = new VBox();
		vbox.setLayoutX(100);
		vbox.setLayoutY(100);
		vbox.setSpacing(10);
		vbox.setId("menuVBox");
		
		createMap = new Button("createMap");
		
		Label mapLabel = new Label();
		mapLabel.setText("size Map:");
		
		Label startLabel = new Label();
		startLabel.setText("starting:");

		Label endLabel = new Label();
		endLabel.setText("Goal:");
		
		Label obstacleLabel = new Label();
		obstacleLabel.setText("Obstacles :");
		
		/*textfield initialization map */
		
		mapXField = new TextField("600");
		mapXField.setMinHeight(70);
		mapXField.setId("mapXTextField");
		mapXField.setFocusTraversable(false);
		//mapXField.setPromptText("X");
		mapXField.setPromptText("600");
		
		mapYField = new TextField("600");
		mapYField.setMinHeight(70);
		mapYField.setId("mapYTextField");
		mapYField.setFocusTraversable(false);
		//mapYField.setPromptText("Y");
		mapYField.setPromptText("600");
		
		HBox mapHbox = new HBox(5);
		mapHbox.setMaxWidth(600);
		mapHbox.setLayoutY(110);
		mapHbox.setId("mapHbox");
		
		/*textfield initialization starting point */
		
		startPointX = new TextField("55");
		startPointX.setMinHeight(70);
		startPointX.setId("startXTextField");
		startPointX.setFocusTraversable(false);
		startPointX.setPromptText("X");
		
		startPointY = new TextField("55");
		startPointY.setMinHeight(70);
		startPointY.setId("startYTextField");
		startPointY.setFocusTraversable(false);
		startPointY.setPromptText("Y");
		
		HBox startHbox = new HBox(5);
		startHbox.setMaxWidth(600);
		startHbox.setLayoutY(480);
		startHbox.setId("startHbox");
		
		
		/*textfield initialization end point */
		
		endPointX = new TextField("555");
		endPointX.setMinHeight(70);
		endPointX.setId("endXTextField");
		endPointX.setFocusTraversable(false);
		endPointX.setPromptText("X");
		
		endPointY = new TextField("555");
		endPointY.setMinHeight(70);
		endPointY.setId("endYTextField");
		endPointY.setFocusTraversable(false);
		endPointY.setPromptText("Y");
		
		HBox endHbox = new HBox(5);
		endHbox.setMaxWidth(600);
		endHbox.setLayoutY(480);
		endHbox.setId("endHbox");
		
		/*textfield initialization obstacle */
		
		numberOfObstacle = new TextField("5");
		numberOfObstacle.setMinHeight(70);
		numberOfObstacle.setId("numeberTextField");
		numberOfObstacle.setFocusTraversable(false);
		numberOfObstacle.setPromptText("the numebr");
		
		HBox obstacleHbox = new HBox();
		obstacleHbox.setMaxWidth(600);
		obstacleHbox.setLayoutY(480);
		obstacleHbox.setId("obstacleHbox");
		
		
		mapHbox.getChildren().addAll(mapLabel,mapXField,mapYField);
		
		startHbox.getChildren().addAll(startLabel,startPointX,startPointY);
		
		endHbox.getChildren().addAll(endLabel,endPointX,endPointY);

		obstacleHbox.getChildren().addAll(obstacleLabel,numberOfObstacle);
		
		
		vbox.getChildren().addAll(mapHbox,startHbox,endHbox,obstacleHbox,createMap);
		
		//vbox.getChildren().addAll(mapHbox,createMap);
		this.getChildren().addAll(vbox);
		
		scene = new Scene(this);
		 
		/*TODO : add css file in package*/
		//this.scene.getStylesheets().add("/view/style.css");
		
		stage.setScene(scene);
	
	}
	
	public TextField getMapX(){
		
		return mapXField;
	}
	public TextField getMapY(){
		
		return mapYField;
	}
	
	public TextField getStartPointX(){
		
		return startPointX;
	}
	public TextField getStartPointY(){
		
		return startPointY;
	}
	
	public TextField getEndPointX(){
		
		return endPointX;
	}
	public TextField getEndPointY(){
		
		return endPointY;
	}
	
	public TextField getNumberObstacle(){
		
		return numberOfObstacle;
	}

}
