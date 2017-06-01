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
	
	public void transform6X4(){
		
		this.setWidth(150);
		this.setHeight(100);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform3X5(){
		
		this.setWidth(75);
		this.setHeight(125);
		this.setFill(Color.DARKOLIVEGREEN);
	}
	public void transform4X2(){
		
		this.setWidth(100);
		this.setHeight(50);
		this.setFill(Color.KHAKI);
	}
	public void transform2X4(){
		
		this.setWidth(50);
		this.setHeight(100);
		this.setFill(Color.LIGHTSKYBLUE);
	}
	public void transform3X4(){
		
		this.setWidth(75);
		this.setHeight(100);
		this.setFill(Color.LIGHTSLATEGRAY);
	}
	public void transform3X3(){
		
		this.setWidth(75);
		this.setHeight(75);
		this.setFill(Color.MEDIUMTURQUOISE);
	}



}
