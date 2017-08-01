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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CHmodel;

public class MainView extends GridPane{
	
	private Scene scene;
	
	private RadioButton enumerate;
	private RadioButton random;
	
	private RadioButton autDis;
	private RadioButton manDis;
	
	private ToggleGroup radioGroup;
	private ToggleGroup distanceGroup;
	
	public Button createMap; 
	
	public TextField mapXField;
	public TextField mapYField;
	public TextField startPointX;
	public TextField startPointY;
	public TextField endPointX;
	public TextField endPointY;
	public TextField numberOfObstacle;
	public TextField percentOfContract;
	public TextField viaNodeDistance;
	
	public String mode;
	public String percentageCustom;
	
	
	/**
	 * @param stage
	 * The first GUI of this software, if it is started.
	 * The input value can be here stored.
	 */
	public MainView(Stage stage){
		
		
		stage.setTitle("Candidate Sets for Alternative Routes "
				+ "in Constrained Free Space Scenarios");
		stage.setHeight(700);
		stage.setWidth(700);
		
		
		GridPane gridPane = new GridPane();
		
		gridPane.setHgap(10);
		gridPane.setVgap(12);
		
		gridPane.setId("mainMenu");
		
		VBox vbox = new VBox();
		vbox.setLayoutX(100);
		vbox.setLayoutY(100);
		vbox.setSpacing(10);
		vbox.setId("menuVBox");
		
		createMap = new Button("createMap");
		
		/**
		 * all label are created.
		 */
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
		
		Label indoorSpaceLabel = new Label();
		indoorSpaceLabel.setText("indoor scenario:");
		
		Label viaNodeLabel = new Label();
		viaNodeLabel.setText("via node order:");
		
		Label viaNodeDistanceLabel = new Label();
		viaNodeDistanceLabel.setText("via node distance:");
		
		Label manualDistanceLabel = new Label();
		manualDistanceLabel.setText("manual distance:");
		
		/**
		 * all textfield are created.
		 */
		mapXField = new TextField();
		mapXField.setMinHeight(50);
		mapXField.setPrefWidth(100);
		mapXField.setId("mapXTextField");
		mapXField.setFocusTraversable(false);
		mapXField.setPromptText("X");
		//mapXField.setPromptText("600");
		
		mapYField = new TextField();
		mapYField.setMinHeight(50);
		mapYField.setPrefWidth(100);
		mapYField.setId("mapYTextField");
		mapYField.setFocusTraversable(false);
		mapYField.setPromptText("Y");
		//mapYField.setPromptText("600");
		
		
		HBox mapHbox = new HBox(5);
		mapHbox.setMaxWidth(600);
		mapHbox.setLayoutY(110);
		mapHbox.setId("mapHbox");
		
		/**
		 * the start point is initialized by input value.
		 */
		
		startPointX = new TextField();
		startPointX.setMinHeight(50);
		startPointX.setPrefWidth(100);
		startPointX.setId("startXTextField");
		startPointX.setFocusTraversable(false);
		startPointX.setPromptText("X");
		
		startPointY = new TextField();
		startPointY.setMinHeight(50);
		startPointY.setPrefWidth(100);
		startPointY.setId("startYTextField");
		startPointY.setFocusTraversable(false);
		startPointY.setPromptText("Y");
		
		HBox startHbox = new HBox(5);
		startHbox.setMaxWidth(600);
		startHbox.setLayoutY(480);
		startHbox.setId("startHbox");
		
		
		/**
		 * the goal point is initialized by input value.
		 */
		
		endPointX = new TextField();
		endPointX.setMinHeight(50);
		endPointX.setPrefWidth(100);
		endPointX.setId("endXTextField");
		endPointX.setFocusTraversable(false);
		endPointX.setPromptText("X");
		
		endPointY = new TextField();
		endPointY.setMinHeight(50);
		endPointY.setPrefWidth(100);
		endPointY.setId("endYTextField");
		endPointY.setFocusTraversable(false);
		endPointY.setPromptText("Y");
		
		HBox endHbox = new HBox(5);
		endHbox.setMaxWidth(600);
		endHbox.setLayoutY(480);
		endHbox.setId("endHbox");
		
		/**
		 * the number of the obstacle is initialized by input value.
		 */
		
		numberOfObstacle = new TextField();
		numberOfObstacle.setMinHeight(50);
		numberOfObstacle.setPrefWidth(100);
		numberOfObstacle.setId("numeberTextField");
		numberOfObstacle.setFocusTraversable(false);
		numberOfObstacle.setPromptText("numebr");
		
		HBox obstacleHbox = new HBox();
		obstacleHbox.setMaxWidth(600);
		obstacleHbox.setLayoutY(480);
		obstacleHbox.setId("obstacleHbox");
		
		/**
		 * the percentage of contraction hierarhes is initialized by input value.
		 */
		
		percentOfContract = new TextField();
		percentOfContract.setMinHeight(50);
		percentOfContract.setPrefWidth(100);
		percentOfContract.setId("");
		percentOfContract.setFocusTraversable(false);
		percentOfContract.setPromptText("percentage");
		
		HBox contractingPercentBox = new HBox();
		contractingPercentBox.setMaxWidth(600);
		contractingPercentBox.setLayoutY(200);
		
		
		viaNodeDistance = new TextField("");
		viaNodeDistance.setMinHeight(50);
		viaNodeDistance.setPrefWidth(50);
		viaNodeDistance.setId("");
		viaNodeDistance.setFocusTraversable(false);
		viaNodeDistance.setPromptText("2");
		
		
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "manual",
			        "fair",
			        "floor"
			    );
		final ComboBox comboBox = new ComboBox(options);
		
		comboBox.setPromptText("mode");
		
		comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
                mode = t1;
                CHmodel.setMode(t1);
            }    
        });
		
		enumerate = new RadioButton("enumerate");
		CHmodel.setEnumRadio(enumerate);
		random = new RadioButton("random");
		CHmodel.setRandomRadio(random);
		radioGroup =  new ToggleGroup();
		
		enumerate.setToggleGroup(radioGroup);
		enumerate.setSelected(true);
		
		random.setToggleGroup(radioGroup);
		
		
		autDis = new RadioButton("auto distance");
		CHmodel.setAutRadio(autDis);
		manDis = new RadioButton("manual distance");
		CHmodel.setManRadio(manDis);
		distanceGroup =  new ToggleGroup();
		
		autDis.setToggleGroup(distanceGroup);
		autDis.setSelected(true);
		manDis.setToggleGroup(distanceGroup);
		
		gridPane.add(mapLabel,0,0);
		gridPane.add(mapXField,1,0);
		gridPane.add(mapYField,2,0);
		
		
		gridPane.add(obstacleLabel,0,1);
		gridPane.add(numberOfObstacle,1,1);
		
		gridPane.add(contractingLabel,0,2);
		gridPane.add(percentOfContract,1,2);
		gridPane.add(percentageLabel,2,2);
		
		gridPane.add(indoorSpaceLabel, 0, 3);
		gridPane.add(comboBox, 1, 3);
		
		gridPane.add(viaNodeLabel, 0, 4);
		gridPane.add(enumerate, 1, 4);
		gridPane.add(random, 2, 4);
		
		gridPane.add(viaNodeDistanceLabel, 0, 5);
		gridPane.add(autDis, 1, 5);
		gridPane.add(manDis, 2, 5);
		gridPane.add(viaNodeDistance, 3, 5);
		
		gridPane.add(createMap,0,6);
		gridPane.setAlignment(Pos.CENTER);
		
		scene = new Scene(gridPane);
	
		stage.setScene(scene);
	
	}
		
	public String getMode(){ return mode;}
	
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
	
	public TextField getViaNodeDistance(){
		
		return viaNodeDistance;
	}
	
	public RadioButton getRadioButtonEnum(){
		return enumerate;
	}
	public RadioButton getRadioButtonRandom(){
		return random;
	}
	public RadioButton getRadioButtonMan(){
		return manDis;
	}
	public RadioButton getRadioButtonAut(){
		return autDis;
	}

}
