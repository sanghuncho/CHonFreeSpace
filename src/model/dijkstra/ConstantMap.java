package model.dijkstra;

import model.CHmodel;

public class ConstantMap {
	
	private static int[][] constantMap = 
			new int[CHmodel.getSizeVector2D().getX()][CHmodel.getSizeVector2D().getY()];
	
	private static ConstantMap singletonMap = new ConstantMap( );
	
	private ConstantMap(){ }
	
	public static int[][] getConstantMap(){
		return constantMap;
	}
	

}
