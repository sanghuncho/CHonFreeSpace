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
	
	public void transform(int width, int height){
		
		this.setWidth(width);
		this.setHeight(height);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform1X6(){
		
		this.setWidth(10);
		this.setHeight(120);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform6X1(){
		
		this.setWidth(120);
		this.setHeight(10);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform1X5(){
		
		this.setWidth(20);
		this.setHeight(100);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform4X1(){
		
		this.setWidth(80);
		this.setHeight(20);
		this.setFill(Color.PALEGOLDENROD);
	}	
	public void transform6X2(){
		
		this.setWidth(120);
		this.setHeight(40);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform3X7(){
		
		this.setWidth(60);
		this.setHeight(140);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform3X2(){
		
		this.setWidth(60);
		this.setHeight(40);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform9X2(){
		
		this.setWidth(180);
		this.setHeight(40);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform2X6(){
		
		this.setWidth(40);
		this.setHeight(120);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform2X7(){
		
		this.setWidth(40);
		this.setHeight(140);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform2X2(){
		
		this.setWidth(40);
		this.setHeight(40);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform2X8(){
		
		this.setWidth(40);
		this.setHeight(160);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform40X1(){
		
		this.setWidth(800);
		this.setHeight(10);
		this.setFill(Color.GRAY);
	}
	public void transform1X28(){
		
		this.setWidth(10);
		this.setHeight(580);
		this.setFill(Color.GRAY);
	}
	
	public void transform4X4(){
		
		this.setWidth(100);
		this.setHeight(100);
		this.setFill(Color.PALEGOLDENROD);
	}
	
	
	public void transform4X3(){
		
		this.setWidth(100);
		this.setHeight(75);
		this.setFill(Color.PALEGOLDENROD);
	}
	
	public void transform6X4(){
		
		this.setWidth(150);
		this.setHeight(100);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform3X5(){
		
		this.setWidth(80);
		this.setHeight(125);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform4X2(){
		
		this.setWidth(100);
		this.setHeight(50);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform2X4(){
		
		this.setWidth(50);
		this.setHeight(100);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform3X4(){
		
		this.setWidth(60);
		this.setHeight(80);
		this.setFill(Color.PALEGOLDENROD);
	}
	public void transform3X3(){
		
		this.setWidth(60);
		this.setHeight(60);
		this.setFill(Color.PALEGOLDENROD);
	}



}
