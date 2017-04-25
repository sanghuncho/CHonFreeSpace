package view;



import java.util.List;

import javafx.beans.binding.IntegerExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CHmodel;

public class MainView extends GridPane{
	
	private Scene scene;
	
	public Button createMap; 
	public TextField mapXField;
	public TextField mapYField;
	public TextField startPointX;
	public TextField startPointY;
	public TextField endPointX;
	public TextField endPointY;
	public TextField numberOfObstacle;
	public TextField percentOfContract;
	public String percentage;
	public String percentageCustom;
	
	public MainView(Stage stage){
		
		
		stage.setTitle("Candidate Sets for Alternative Routes "
				+ "in Constrained Free Space Scenarios");
		stage.setHeight(700);
		stage.setWidth(700);
		
		
		GridPane gridPane = new GridPane();
		//gridPane.setPrefSize(400, 400);
		
		gridPane.setHgap(10);
		gridPane.setVgap(12);
		
		//this.setId("mainMenu");
		gridPane.setId("mainMenu");
		
		VBox vbox = new VBox();
		vbox.setLayoutX(100);
		vbox.setLayoutY(100);
		vbox.setSpacing(10);
		vbox.setId("menuVBox");
		
		createMap = new Button("createMap");
		
		Label mapLabel = new Label();
		mapLabel.setText("size of map:");
		
		Label startLabel = new Label();
		startLabel.setText("starting point:");

		Label endLabel = new Label();
		endLabel.setText("goal point:");
		
		Label obstacleLabel = new Label();
		obstacleLabel.setText("size of obstacles:");
		
		Label contractingLabel = new Label();
		contractingLabel.setText("percent of contracting:");
		
		Label percentageLabel = new Label();
		percentageLabel.setText("%");
		
		/*textfield initialization map */
		
		mapXField = new TextField("1000");
		mapXField.setMinHeight(50);
		mapXField.setPrefWidth(100);
		mapXField.setId("mapXTextField");
		mapXField.setFocusTraversable(false);
		//mapXField.setPromptText("X");
		mapXField.setPromptText("600");
		
		mapYField = new TextField("600");
		mapYField.setMinHeight(50);
		mapYField.setPrefWidth(100);
		mapYField.setId("mapYTextField");
		mapYField.setFocusTraversable(false);
		//mapYField.setPromptText("Y");
		mapYField.setPromptText("600");
		
		HBox mapHbox = new HBox(5);
		mapHbox.setMaxWidth(600);
		mapHbox.setLayoutY(110);
		mapHbox.setId("mapHbox");
		
		/*textfield initialization starting point */
		
		startPointX = new TextField("2");
		startPointX.setMinHeight(50);
		startPointX.setPrefWidth(100);
		startPointX.setId("startXTextField");
		startPointX.setFocusTraversable(false);
		startPointX.setPromptText("X");
		
		startPointY = new TextField("2");
		startPointY.setMinHeight(50);
		startPointY.setPrefWidth(100);
		startPointY.setId("startYTextField");
		startPointY.setFocusTraversable(false);
		startPointY.setPromptText("Y");
		
		HBox startHbox = new HBox(5);
		startHbox.setMaxWidth(600);
		startHbox.setLayoutY(480);
		startHbox.setId("startHbox");
		
		
		/*textfield initialization end point */
		
		endPointX = new TextField("99");
		endPointX.setMinHeight(50);
		endPointX.setPrefWidth(100);
		endPointX.setId("endXTextField");
		endPointX.setFocusTraversable(false);
		endPointX.setPromptText("X");
		
		endPointY = new TextField("59");
		endPointY.setMinHeight(50);
		endPointY.setPrefWidth(100);
		endPointY.setId("endYTextField");
		endPointY.setFocusTraversable(false);
		endPointY.setPromptText("Y");
		
		HBox endHbox = new HBox(5);
		endHbox.setMaxWidth(600);
		endHbox.setLayoutY(480);
		endHbox.setId("endHbox");
		
		/*textfield initialization obstacle */
		
		numberOfObstacle = new TextField("4");
		numberOfObstacle.setMinHeight(50);
		numberOfObstacle.setPrefWidth(100);
		numberOfObstacle.setId("numeberTextField");
		numberOfObstacle.setFocusTraversable(false);
		numberOfObstacle.setPromptText("the numebr");
		
		HBox obstacleHbox = new HBox();
		obstacleHbox.setMaxWidth(600);
		obstacleHbox.setLayoutY(480);
		obstacleHbox.setId("obstacleHbox");
		
		
		percentOfContract = new TextField("10");
		percentOfContract.setMinHeight(50);
		percentOfContract.setPrefWidth(100);
		percentOfContract.setId("");
		percentOfContract.setFocusTraversable(false);
		percentOfContract.setPromptText("percentage");
		
		HBox contractingPercentBox = new HBox();
		contractingPercentBox.setMaxWidth(600);
		contractingPercentBox.setLayoutY(200);
		
		
		
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "10%",
			        "20%",
			        "30%"
			    );
		final ComboBox comboBox = new ComboBox(options);
		
		comboBox.setPromptText("the percent of contracting");
		
		comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
                percentage = t1;
                CHmodel.setPercentage(t1);
            }    
        });
			
		/*mapHbox.getChildren().addAll(mapLabel,mapXField,mapYField);
		
		startHbox.getChildren().addAll(startLabel,startPointX,startPointY);
		
		endHbox.getChildren().addAll(endLabel,endPointX,endPointY);

		obstacleHbox.getChildren().addAll(obstacleLabel,numberOfObstacle);
		
		contractingPercentBox.getChildren().addAll(contractingLabel,percentOfContract,percentageLabel);*/
		
		
		gridPane.add(mapLabel,0,0);
		gridPane.add(mapXField,1,0);
		gridPane.add(mapYField,2,0);
		
		gridPane.add(startLabel,0,1);
		gridPane.add(startPointX,1,1);
		gridPane.add(startPointY,2,1);
		
		gridPane.add(endLabel,0,2);
		gridPane.add(endPointX,1,2);
		gridPane.add(endPointY,2,2);
		
		gridPane.add(obstacleLabel,0,3);
		gridPane.add(numberOfObstacle,1,3);
		
		gridPane.add(contractingLabel,0,4);
		gridPane.add(percentOfContract,1,4);
		gridPane.add(percentageLabel,2,4);
		
		gridPane.add(createMap,0,5);
		gridPane.setAlignment(Pos.CENTER);
		
		/*vbox.getChildren().addAll(mapHbox,startHbox,endHbox,obstacleHbox,
				contractingPercentBox,createMap);//comboBox,
*/		
		//vbox.getChildren().addAll(mapHbox,createMap);
		//this.getChildren().addAll(vbox);
		
		scene = new Scene(gridPane);
		 
		/*TODO : add css file in package*/
		//this.scene.getStylesheets().add("/view/style.css");
		
		
		
		stage.setScene(scene);
	
	}
		
	public String getPercentage(){ return percentage;}
	
	public TextField getPercentageCustom(){ return percentOfContract;}
	
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










/*package view;



import java.util.List;

import javafx.beans.binding.IntegerExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CHmodel;

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
	public TextField percentOfContract;
	public String percentage;
	public String percentageCustom;
	
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
		
		Label contractingLabel = new Label();
		contractingLabel.setText("percent of contracting :");
		
		Label percentageLabel = new Label();
		percentageLabel.setText("%");
		
		textfield initialization map 
		
		mapXField = new TextField("600");
		mapXField.setMinHeight(50);
		mapXField.setPrefWidth(100);
		mapXField.setId("mapXTextField");
		mapXField.setFocusTraversable(false);
		//mapXField.setPromptText("X");
		mapXField.setPromptText("600");
		
		mapYField = new TextField("600");
		mapYField.setMinHeight(50);
		mapYField.setPrefWidth(100);
		mapYField.setId("mapYTextField");
		mapYField.setFocusTraversable(false);
		//mapYField.setPromptText("Y");
		mapYField.setPromptText("600");
		
		HBox mapHbox = new HBox(5);
		mapHbox.setMaxWidth(600);
		mapHbox.setLayoutY(110);
		mapHbox.setId("mapHbox");
		
		textfield initialization starting point 
		
		startPointX = new TextField("55");
		startPointX.setMinHeight(50);
		startPointX.setPrefWidth(100);
		startPointX.setId("startXTextField");
		startPointX.setFocusTraversable(false);
		startPointX.setPromptText("X");
		
		startPointY = new TextField("55");
		startPointY.setMinHeight(50);
		startPointY.setPrefWidth(100);
		startPointY.setId("startYTextField");
		startPointY.setFocusTraversable(false);
		startPointY.setPromptText("Y");
		
		HBox startHbox = new HBox(5);
		startHbox.setMaxWidth(600);
		startHbox.setLayoutY(480);
		startHbox.setId("startHbox");
		
		
		textfield initialization end point 
		
		endPointX = new TextField("555");
		endPointX.setMinHeight(50);
		endPointX.setPrefWidth(100);
		endPointX.setId("endXTextField");
		endPointX.setFocusTraversable(false);
		endPointX.setPromptText("X");
		
		endPointY = new TextField("555");
		endPointY.setMinHeight(50);
		endPointY.setPrefWidth(100);
		endPointY.setId("endYTextField");
		endPointY.setFocusTraversable(false);
		endPointY.setPromptText("Y");
		
		HBox endHbox = new HBox(5);
		endHbox.setMaxWidth(600);
		endHbox.setLayoutY(480);
		endHbox.setId("endHbox");
		
		textfield initialization obstacle 
		
		numberOfObstacle = new TextField("1");
		numberOfObstacle.setMinHeight(50);
		numberOfObstacle.setPrefWidth(100);
		numberOfObstacle.setId("numeberTextField");
		numberOfObstacle.setFocusTraversable(false);
		numberOfObstacle.setPromptText("the numebr");
		
		HBox obstacleHbox = new HBox();
		obstacleHbox.setMaxWidth(600);
		obstacleHbox.setLayoutY(480);
		obstacleHbox.setId("obstacleHbox");
		
		
		percentOfContract = new TextField();
		percentOfContract.setMinHeight(50);
		percentOfContract.setPrefWidth(150);
		percentOfContract.setId("");
		percentOfContract.setFocusTraversable(false);
		percentOfContract.setPromptText("the percentage");
		
		HBox contractingPercentBox = new HBox();
		contractingPercentBox.setMaxWidth(600);
		contractingPercentBox.setLayoutY(200);
		
		
		
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "10%",
			        "20%",
			        "30%"
			    );
		final ComboBox comboBox = new ComboBox(options);
		
		comboBox.setPromptText("the percent of contracting");
		
		comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
                percentage = t1;
                CHmodel.setPercentage(t1);
            }    
        });
			
		mapHbox.getChildren().addAll(mapLabel,mapXField,mapYField);
		
		startHbox.getChildren().addAll(startLabel,startPointX,startPointY);
		
		endHbox.getChildren().addAll(endLabel,endPointX,endPointY);

		obstacleHbox.getChildren().addAll(obstacleLabel,numberOfObstacle);
		
		contractingPercentBox.getChildren().addAll(contractingLabel,percentOfContract,percentageLabel);
		
		
		vbox.getChildren().addAll(mapHbox,startHbox,endHbox,obstacleHbox,
				contractingPercentBox,createMap);//comboBox,
		
		//vbox.getChildren().addAll(mapHbox,createMap);
		this.getChildren().addAll(vbox);
		
		scene = new Scene(this);
		 
		TODO : add css file in package
		//this.scene.getStylesheets().add("/view/style.css");
		
		stage.setScene(scene);
	
	}
		
	public String getPercentage(){ return percentage;}
	
	public TextField getPercentageCustom(){ return percentOfContract;}
	
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
*/