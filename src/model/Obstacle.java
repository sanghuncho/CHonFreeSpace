package model;

import java.util.Random;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle {
	
	int xPos;
	int yPos;
	int width;
	int height;
	
	private DoubleProperty XPosProperty;
    private DoubleProperty yPosProperty;

	
	public Obstacle(){
		
		/*XPosProperty = new SimpleDoubleProperty(xPos = getRandomX());
		yPosProperty = new SimpleDoubleProperty(yPos = getRandomY());
		*/
		/*this.setX(xPos = getRandomX());
		this.setY(yPos = getRandomY());*/
		
		/*this.setX(xPos = 255);
		this.setY(yPos = 255);
		*/
		
		this.xProperty().set(getRandomX());
		this.yProperty().set(getRandomY());
		
		/*this.xProperty().set(85);
		this.yProperty().set(85);*/
		
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
	
	public DoubleProperty getXPoperty(){
		
		return this.xProperty();
	}
	
	public DoubleProperty getYPoperty(){
		
		return this.yProperty();
	}
	
	public void setXPoperty(double x){
		
		this.xProperty().set(x);
	}
	
	public void setYPoperty(double y){
		
		this.yProperty().set(y);
	}
	/*public int getXpos(){
		
		return xPos;
	}
	
	public int intgetYpos(){
		
		return yPos;
	}*/

}
