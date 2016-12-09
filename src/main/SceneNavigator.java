package main;

import java.io.IOException;

import javafx.stage.Stage;
import options.AvailableScenes;
import presenter.LobbyPresenter;
import presenter.MainPresenter;
import view.Lobby;
import view.MainView;

public class SceneNavigator {
	
	private LobbyPresenter lobbyPresenter;
	private MainPresenter mainPresenter;
	public Lobby lobby;
	public Stage primaryStage;
	
	
	
	public SceneNavigator(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
public void switchTo(AvailableScenes newScene) throws IOException, InterruptedException {
		
		switch(newScene) {
		
		case MAINMENU : 
			
			mainPresenter = new MainPresenter(new MainView(primaryStage)); 
			
			break;
			
		case LOBBY :
			
			lobbyPresenter = new LobbyPresenter(lobby = new Lobby(primaryStage)); 
			break;

			/*	case GAME :

				model = new MapModel(level);
				gameView = new GameView(model, primaryStage);
				presenter = new GamePresenter(model, gameView,gameModel);
				presenter.play();
				break;*/


		}
	
	}
}
