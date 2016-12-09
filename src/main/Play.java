package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import view.MainView;
import options.AvailableScenes;

public class Play extends Application {
	
	public static void main(String[] args) {
        launch(args);
    }
	
	public static SceneNavigator navigator;
	
	public void start(Stage primaryStage)throws IOException,InterruptedException{
		
		navigator = new SceneNavigator(primaryStage);

		primaryStage.setHeight(800);
		primaryStage.setWidth(800);
		primaryStage.show();
		primaryStage.centerOnScreen();
		navigator.switchTo(AvailableScenes.MAINMENU);
	}

}
