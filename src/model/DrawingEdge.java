package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class DrawingEdge extends Line{
	
	public DrawingEdge(Double startX, Double startY, Double goalX, Double goalY){
		
		this.setStartX(startX);
		this.setStartY(startY);
		this.setEndX(goalX);
		this.setEndY(goalY);
		this.setStroke(Color.OLIVEDRAB);
	}

}
