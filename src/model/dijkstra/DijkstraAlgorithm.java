package model.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Obstacle;
import model.node.Node;
import model.node.NodeMap;
import util.math.MathHelper;
import util.math.Vector;

public class DijkstraAlgorithm {
	
	private List<Node> nodes;
    private List<Edge> edges;
    
    private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distance;
    private ArrayList<Obstacle> obstacles;
    
    public DijkstraAlgorithm(Graph graph, ArrayList<Obstacle> obstacles){
    	
    	nodes = new ArrayList<Node>(graph.getNodes());
        edges = new ArrayList<Edge>(graph.getEdges());
        this.obstacles = obstacles;
        
    }
        
    public void execute(Node source) {
    	
    	
        System.out.println("execute \n");

            settledNodes = new HashSet<Node>();
            unSettledNodes = new HashSet<Node>();
            distance = new HashMap<Node, Integer>();
            predecessors = new HashMap<Node, Node>();
            distance.put(source, 0);
            unSettledNodes.add(source);
            
            while (unSettledNodes.size() > 0) {
            	

            		Node node = getMinimumCost(unSettledNodes);
            		
                    settledNodes.add(node);

                    unSettledNodes.remove(node);
                    
                    findMinimalDistances(node);
            }
            
    }  

    private void findMinimalDistances(Node node) {
    	
        List<Node> adjacentNodes = getNeighbors(node);
        
        for (Node target : adjacentNodes) {
        	
                if (getShortestDistance(target) > getShortestDistance(node)
                                + 1) { 
                        distance.put(target, getShortestDistance(node)+1);
                        predecessors.put(target, node);
                        unSettledNodes.add(target);
                }
               else if(getShortestDistance(target) == getShortestDistance(node)
                                + getDistance(node, target)) {
            	   
            	   Node prevNode = predecessors.get(target);
            	   Node minimum = MathHelper.shortestDistanceBetweenGoal(node,prevNode);
            	   predecessors.remove(target,prevNode);
            	   predecessors.put(target, minimum);
                   
            	   
            	   distance.put(target, getShortestDistance(node)
                                        + getDistance(node, target));
               }
        }
           
    }
  
    private int getShortestDistance(Node destination) {
    	
        Integer d = distance.get(destination);
        
        if (d == null) {
                return Integer.MAX_VALUE;
        	
        	
        } else {
                return d;
        }
    }
    
    
    
    private List<Node> getNeighbors(Node node) {
    	
    	List<Node> neighbors = new ArrayList<Node>();
    	
    	boolean insideObs = false;
	    	
	        for (Edge edge : edges) {
	        	
	        
	                if (edge.getSource().equals(node)
	                                && !isSettled(edge.getDestination())) {
	                	
	                	for (Obstacle obstacle : obstacles) {
	    	    		
	                	insideObs = (insideObs || isNodeInsideObs(edge,obstacle));
	                
	                	
	        		 } 
	                if(!insideObs){
	                	neighbors.add(edge.getDestination());
	                }
	                	
	               }
	                
	        }
	        
         return neighbors;
    }

	private boolean isNodeInsideObs(Edge edge, Obstacle obstacle){
		
		int edgeGoalX = edge.getDestination().getPosition().getX();
		int edgeGoalY = edge.getDestination().getPosition().getY();
		
		int obsXpos = (int)obstacle.xProperty().get()/10;
		int obsXposWidth = (int)(obsXpos + (obstacle.getWidth()/10));
		
		int obsYpos = (int)obstacle.yProperty().get()/10;
		int obsYposWidth = (int)(obsYpos + (obstacle.getHeight()/10));
		
		System.out.println("obsXpos :  "+ obsXpos + "\n");
		System.out.println("obsXposWidth :  "+ obsXposWidth + "\n");
		
		System.out.println("obsYpos :  "+ obsYpos + "\n");
		System.out.println("obsYposWidth :  "+ obsYposWidth + "\n");
		
		
		
		if( (obsXpos <= edgeGoalX ) && ( edgeGoalX < obsXposWidth+1)){ 
			
			if(( obsYpos <= edgeGoalY) && ( edgeGoalY < obsYposWidth+1)){
				
				System.out.println("edgeGoalX :  "+ edgeGoalX + "\n");
				System.out.println("edgeGoalY :  "+ edgeGoalY + "\n");
    			
				return true;
				
			}
			else{
				return false;
			}
		
		}
		else{
			return false;
		}
		
	}
	
	private Node getMinimumCost(Set<Node> vertexes) {
    	Node minimum = null;
        for (Node vertex : vertexes) {
                if (minimum == null) {
                        minimum = vertex;
                } else {
                        if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                                minimum = vertex;
                        }
                        else if(getShortestDistance(vertex) < getShortestDistance(minimum)){
                        	
                        	minimum = MathHelper.shortestDistanceBetweenGoal(vertex,minimum);
                        }
                }
        }
        return minimum;
    }
    
    
    
    private int getDistance(Node node, Node target) {
        for (Edge edge : edges) {
                if (edge.getSource().equals(node)
                                && edge.getDestination().equals(target)) {
                        return edge.getWeight();
                }
        }
        throw new RuntimeException("Should not happen");
    }
    
    
    private boolean isSettled(Node vertex) {
        return settledNodes.contains(vertex);
    }

    
    
    public LinkedList<Node> getPath(Node target) {
    	
        LinkedList<Node> path = new LinkedList<Node>();
        Node step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
        
        	return null;
                
                
        }
        path.add(step);
        while (predecessors.get(step) != null) {
                step = predecessors.get(step);
                path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        
        return path;
    }
        
   

}
