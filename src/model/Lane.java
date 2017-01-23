package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Lane extends Line{
	
	public Lane(Double startX, Double startY, Double goalX, Double goalY){
		
		this.setStartX(startX);
		this.setStartY(startY);
		this.setEndX(goalX);
		this.setEndY(goalY);
		this.setStrokeWidth(3.0f);
		this.setStroke(Color.RED);
		
	}

	
}
