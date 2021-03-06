package model;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle{
	
	int xPos;
	int yPos;
	int width;
	int height;

    public Obstacle(){
		
    	
    	/**
    	 * create randomly obstacles on the map.
    	 */
		createObstacleRandom();
    	
    	/**
    	 * For evaluation the coordinates of four obstacles are set
    	 */
    	//createObstacleFour(id);
    	
    	
    	
    	/**
    	 * 600*600 with 12 obstacles.
    	 */
    	//createObstacle_12_600_600(id);
    	
    	
    	/**
    	 * 1000*600 with 12 obstacles.
    	 */
    	//createObstacle_12_1000_600(id);
    		
    	
		this.setWidth(CHmodel.VALUE_OBSTACLE_WIDTH);
		this.setHeight(CHmodel.VALUE_OBSTACLE_HEIGHT);
		this.setFill(Color.BLUE);

	}
    
    private void createObstacleRandom(){
    	this.xProperty().set(getRandomX());
		this.yProperty().set(getRandomY());
    }

	private int getRandomX(){
		Random rand = new Random();
		int randomNumX = ThreadLocalRandom.current().nextInt(CHmodel.VALUE_OBSTACLE_CREATE_DISTANCE 
				,CHmodel.getMapX()-CHmodel.VALUE_OBSTACLE_CREATE_DISTANCE);
		return randomNumX;
	}
	private int getRandomY(){
		Random rand = new Random();
		int randomNumY = ThreadLocalRandom.current().nextInt(CHmodel.VALUE_OBSTACLE_CREATE_DISTANCE 
				,CHmodel.getMapY()-CHmodel.VALUE_OBSTACLE_CREATE_DISTANCE);
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
	private void createObstacleFour(int id){
		
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
	}
	private void createObstacle_12_600_600(int id){
		
		if(id == 0){
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
		}
		
	}
	
	private void createObstacle_12_1000_600(int id){
		if(id == 0){
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
	}

	
}