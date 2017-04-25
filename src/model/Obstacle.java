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
	int id;
	
	private DoubleProperty XPosProperty;
    private DoubleProperty yPosProperty;

    public Obstacle(int id){
		
    	
		/*this.xProperty().set(getRandomX());
		this.yProperty().set(getRandomY());*/
		
		/*this.xProperty().set(100);
		this.yProperty().set(100);*/
    	
    	/**
    	 * For evaluation the coordinates of four obstacles are set
    	 */ 
    	if(id == 0){
    		this.xProperty().set(100);
    		this.yProperty().set(100);
    	}
    	else if(id == 1){
    		this.xProperty().set(800);
    		this.yProperty().set(100);
    	}
    	else if(id == 2){
    		this.xProperty().set(100);
    		this.yProperty().set(450);
    	}
    	else if(id == 3){
    		this.xProperty().set(800);
    		this.yProperty().set(450);
    	}
		
		this.setWidth(50);
		this.setHeight(50);
		this.setFill(Color.BLUE);

		this.id = id;
	}	
    
	/*public Obstacle(){
		
	
		this.xProperty().set(getRandomX());
		this.yProperty().set(getRandomY());
		
		this.xProperty().set(100);
		this.yProperty().set(100);
		
		this.setWidth(50);
		this.setHeight(50);
		this.setFill(Color.BLUE);

		
	}*/
	
	private int getRandomX(){
		Random rand = new Random();
		int randomNumX = rand.nextInt( CHmodel.getMapX());
		return randomNumX;
	}
	private int getRandomY(){
		Random rand = new Random();
		int randomNumY = rand.nextInt( CHmodel.getMapY());
		return randomNumY;
	}
	
	public int getID(){
		
		return id;
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

}
