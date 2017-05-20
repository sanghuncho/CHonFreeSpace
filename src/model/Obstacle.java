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
		
    	
    	/**
    	 * For evaluation the coordinates of four obstacles are set
    	 */
    	
    	if(id == 0){
    		this.xProperty().set(100);
    		this.yProperty().set(100);
    	}
    	else if(id == 1){
    		this.xProperty().set(400);
    		this.yProperty().set(100);
    	}
    	else if(id == 2){
    		this.xProperty().set(100);
    		this.yProperty().set(400);
    	}
    	else if(id == 3){
    		this.xProperty().set(400);
    		this.yProperty().set(400);
    	}
    	
    	
    	/**
    	 * 600*600 with 12 obstacles.
    	 */
    	/*if(id == 0){
    		this.xProperty().set(130);
    		this.yProperty().set(120);
    	}
    	else if(id == 1){
    		this.xProperty().set(280);
    		this.yProperty().set(120);
    	}
    	else if(id == 2){
    		this.xProperty().set(430);
    		this.yProperty().set(120);
    	}
    	else if(id == 3){
    		this.xProperty().set(130);
    		this.yProperty().set(240);
    	}
    	else if(id == 4){
    		this.xProperty().set(280);
    		this.yProperty().set(240);
    	}
    	else if(id == 5){
    		this.xProperty().set(430);
    		this.yProperty().set(240);
    	}
    	else if(id == 6){
    		this.xProperty().set(130);
    		this.yProperty().set(360);
    	}
    	else if(id == 7){
    		this.xProperty().set(280);
    		this.yProperty().set(360);
    	}
    	else if(id == 8){
    		this.xProperty().set(430);
    		this.yProperty().set(360);
    	}
    	else if(id == 9){
    		this.xProperty().set(130);
    		this.yProperty().set(480);
    	}
    	else if(id == 10){
    		this.xProperty().set(280);
    		this.yProperty().set(480);
    	}
    	else if(id == 11){
    		this.xProperty().set(430);
    		this.yProperty().set(480);
    	}*/
    	
    	/**
    	 * 1000*600 with 12 obstacles.
    	 */
    	/*if(id == 0){
    		this.xProperty().set(250);
    		this.yProperty().set(150);
    	}
    	else if(id == 1){
    		this.xProperty().set(500);
    		this.yProperty().set(150);
    	}
    	else if(id == 2){
    		this.xProperty().set(750);
    		this.yProperty().set(150);
    	}
    	else if(id == 3){
    		this.xProperty().set(250);
    		this.yProperty().set(300);
    	}
    	else if(id == 4){
    		this.xProperty().set(500);
    		this.yProperty().set(300);
    	}
    	else if(id == 5){
    		this.xProperty().set(750);
    		this.yProperty().set(300);
    	}
    	else if(id == 6){
    		this.xProperty().set(250);
    		this.yProperty().set(450);
    	}
    	else if(id == 7){
    		this.xProperty().set(500);
    		this.yProperty().set(450);
    	}
    	else if(id == 8){
    		this.xProperty().set(750);
    		this.yProperty().set(450);
    	}
*/    	
    	
    	
		this.setWidth(CHmodel.VALUE_OBSTACLE_WIDTH);
		this.setHeight(CHmodel.VALUE_OBSTACLE_HEIGHT);
		this.setFill(Color.BLUE);

		this.id = id;
	}

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