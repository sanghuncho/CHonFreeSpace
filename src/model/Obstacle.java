package model;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle {
	
	public Obstacle(){
		
		/*this.setX(300);
		this.setY(300);*/
		this.setX(getRandomX());
		this.setY(getRandomY());
		this.setWidth(50);
		this.setHeight(50);
		this.setFill(Color.BLUE);
		
	}
	
	private int getRandomX(){
		Random rand = new Random();
		int randomNumX = rand.nextInt( CHmodel.getMapX() + 1);
		return randomNumX;
	}
	private int getRandomY(){
		Random rand = new Random();
		int randomNumY = rand.nextInt( CHmodel.getMapY() + 1);
		return randomNumY;
	}

}
