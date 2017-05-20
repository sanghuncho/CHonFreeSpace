/*package model.dijkstra;

import java.util.ArrayList;
import java.util.List;

import model.node.Node;
import util.enums.Directions;
import util.math.Vector;

public class Edges {
	
	
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	
	
	public Edges(){
		
		
	}
	
private void createSurroundingEdges(List<Node> nodes) {
		
		for (Node node : nodes){
			
		 if( !isContractedNode(node.getPosition()) ){
			
			
			for (Node neighbor : node.getNeighborList()) {
	            
				int weight=1;
				
				Vector neighborVector = neighbor.getPosition();
				Directions neighborDirection = null;
				
				if(isContractedNode(neighborVector)){
					
					neighborDirection = node.getDirectionOfNeighbor(neighbor);

				}
				long startTime_shortcut = System.currentTimeMillis();
				
				while(isContractedNode(neighborVector)){
					
					Node freshNeighbor = getFreshNeighbor(node,neighborDirection,weight+1); 
					
					neighborVector = freshNeighbor.getPosition();
					weight++;
					
				}
				long endTime_shortcut = System.currentTimeMillis();
				durationOfOneShortcut = (endTime_shortcut - startTime_shortcut);
				durationOfgeneratingAllShortcut = durationOfgeneratingAllShortcut 
						+ durationOfOneShortcut;
				
				neighbor = nodeMap.get(neighborVector.getX(), neighborVector.getY());
				
				*//**
				 * isObstacle-method checks the map-value
				 *//*
				if ( !isObstacle(node.getPosition()) && !isObstacle(neighbor.getPosition())){ 
					
					if (!(checkForNode(visitedNodes, node))) {
						 

						createEdge(node,neighbor,weight);
						
						
					}
					
				}
			}
			visitedNodes.add(node);
		}
	 }
		System.out.println("the duration of generating for all shortcuts : " 
	 + durationOfgeneratingAllShortcut + " miliseconds");
	}

}
*/