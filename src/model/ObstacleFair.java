package model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ObstacleFair extends Obstacle{
	
	int xPos;
	int yPos;
	int width;
	int height;
	
	public ObstacleFair(int xProperty, int yProperty){
		
		this.xProperty().set(xProperty);
		this.yProperty().set(yProperty);
		
	}
	
	public void transform4X4(){
		
		this.setWidth(100);
		this.setHeight(100);
		this.setFill(Color.GAINSBORO);
	}
	
	
	public void transform4X3(){
		
		this.setWidth(100);
		this.setHeight(75);
		this.setFill(Color.CADETBLUE);
	}
}
