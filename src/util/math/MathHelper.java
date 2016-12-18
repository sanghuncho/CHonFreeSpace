package util.math;

import model.CHmodel;
import model.node.Node;

public abstract class MathHelper {
	
	public static Node shortestDistanceBetweenGoal(Node nodeFirst, Node nodeSecond) {

		double goalX = CHmodel.getGoalVector2D().getX();
		double goalY = CHmodel.getGoalVector2D().getY();
		
		
		double targetX = CHmodel.getStartVector2D().getX();
		double targetY = CHmodel.getStartVector2D().getX();
		
		double firstX = nodeFirst.getPosition().getX();
		double firstY = nodeFirst.getPosition().getY();
		
		double secondX = nodeSecond.getPosition().getX();
		double secondY = nodeSecond.getPosition().getY();
		
		
		
		double firstDistanceToGoalX = Math.pow(goalX - firstX, 2);
		double firstDistanceToGoalY = Math.pow(goalY - firstY, 2);
		
		double secondDistanceToGoalX = Math.pow(goalX - secondX, 2);
		double secondDistanceToGoalY = Math.pow(goalY - secondY, 2);
		
		
		double firstDistanceGoal = Math.sqrt(firstDistanceToGoalX + firstDistanceToGoalY);
		double secondDistanceGoal = Math.sqrt(secondDistanceToGoalX + secondDistanceToGoalY);
		
		/*
		double firstDistanceToStartX = Math.pow(startX - firstX, 2);
		double firstDistanceToStartY = Math.pow(startY - firstY, 2);
		
		double secondDistanceToStartX = Math.pow(startX - secondX, 2);
		double secondDistanceToStartY = Math.pow(startY - secondY, 2);*/
		
		
		/*double firstDistanceStart = Math.sqrt(firstDistanceToGoalX + firstDistanceToGoalY);
		double secondDistanceStart = Math.sqrt(secondDistanceToGoalX + secondDistanceToGoalY);*/
		
		
		if(firstDistanceGoal < secondDistanceGoal){
			
			return nodeFirst;
		}
		else{
			return nodeSecond;
		}
	}

}
