/*package model;

import java.util.LinkedList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.node.Node;
import util.math.Vector;

public class HomotopyLane implements Runnable {
	
	LinkedList<Node> path;
	private int colorNumber = 0;
	public Pane center;
	
	public HomotopyLane(LinkedList<Node> path,Pane center){
		
		this.path = path;
		this.center = center;
		
	}
	
	
	public void run(){
		
		int k = path.size();
		
		
		for(int i = 0 ; i < k-1 ; i ++){
			
			Vector start = path.get(i).getPosition();
			Vector goal = path.get(i+1).getPosition();
			Lane lane = new Lane(10*(double)start.getX()+5,10*(double)start.getY() +5,
					10*(double)goal.getX()+5,10*(double)goal.getY()+5);
			lane.setStroke(getLaneColor(colorNumber));
			center.getChildren().add(lane);
			
		}
		colorNumber++;
	}
	
	private Color getLaneColor(int numberLane){
		
		
		int number = numberLane;
		Color laneColor = null;
		
		switch(number) {
			
			case 0 : laneColor = Color.AQUA;
			break;
			
			case 1 : laneColor = Color.AQUA;
			break;
			
			case 2 : laneColor = Color.BROWN;
			break;
			
			case 3 : laneColor = Color.BROWN;
			break;
			
			case 4 : laneColor = Color.DARKGREEN;
			break;
			
			case 5 : laneColor = Color.DARKGREEN;
			break;
			
			case 6 : laneColor = Color.GREY;
			break;
			
			case 7 : laneColor = Color.GREY;
			break;
			
			case 8 : laneColor = Color.CHARTREUSE;
			break;
			
			case 9 : laneColor = Color.CHARTREUSE;
			break;
			
			case 10 : laneColor = Color.PURPLE;
			break;
			
			case 11 : laneColor = Color.PURPLE;
			break;
			
			case 12 : laneColor = Color.PINK;
			break;
			
			case 13 : laneColor = Color.PINK;
			break;
			
		}
		
		return laneColor;
	}

	public void createLane(LinkedList<Node> path){
		
		int k = path.size();
		
		
		for(int i = 0 ; i < k-1 ; i ++){
			
			Vector start = path.get(i).getPosition();
			Vector goal = path.get(i+1).getPosition();
			Lane lane = new Lane(10*(double)start.getX()+5,10*(double)start.getY() +5,
					10*(double)goal.getX()+5,10*(double)goal.getY()+5);
			lane.setStroke(getLaneColor(colorNumber));
			center.getChildren().add(lane);
			
		}
		colorNumber++;
		
	}
}
*/