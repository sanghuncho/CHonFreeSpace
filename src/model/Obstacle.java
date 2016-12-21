package model;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle {
	
	int xPos;
	int yPos;
	int width;
	int height;
	
	public Obstacle(){
		
		
		this.setX(xPos = getRandomX());
		this.setY(yPos = getRandomY());
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
	
	/*public int getXpos(){
		
		return xPos;
	}
	
	public int intgetYpos(){
		
		return yPos;
	}*/

}
