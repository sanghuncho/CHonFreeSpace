package model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * starting - and end point
 */
public class Point extends Circle{
	
	private DoubleProperty XPosProperty;
    private DoubleProperty yPosProperty;
	
	public Point(){	
		
		this.setRadius(4);		
	}
	/**
	 * set the property of start point*/
	public void setStart(){
		
		
		this.setRadius(6);
		this.setCenterX(CHmodel.getStartX());
		this.setCenterY(CHmodel.getStartY());
		this.setFill(Color.GREENYELLOW);
		
		
	}
	/**
	 * set the property of end point*/
	public void setEnd(){
		
		
		this.setRadius(6);
		this.setCenterX(CHmodel.getGoalX());
		this.setCenterY(CHmodel.getGoalY());
		this.setFill(Color.RED);
	}
	/**
	 * set the property of via node start point*/
	public void setStartViaNode(){
		this.setRadius(6);
		this.setCenterX(25);
		this.setCenterY(15);
		this.setFill(Color.MAGENTA);
	}
	


}
