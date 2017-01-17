package model.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.application.Platform;
import model.Obstacle;
import model.Point;
import model.node.Node;
import model.node.NodeMap;
import util.math.MathHelper;
import util.math.Vector;
import view.Lobby;

public class DijkstraAlgorithm  {
	
	private Thread thread;
	private List<Node> nodes;
    private List<Edge> edges;
    
    private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node,Integer> distance;
    private ArrayList<Obstacle> obstacles;
    private Node source;
    private Lobby lobbyView;
    private LinkedList<Node> path;
    private Map<Node,Double> turning;
    
    public DijkstraAlgorithm(Graph graph, ArrayList<Obstacle> obstacles,Node source,Lobby lobbyView){
    	
    	nodes = new ArrayList<Node>(graph.getNodes());
        edges = new ArrayList<Edge>(graph.getEdges());
        this.obstacles = obstacles;
        this.source = source;
        this.lobbyView = lobbyView;
        
    }
     
         
    public void execute() {
     	
        System.out.println("execute \n");

            settledNodes = new HashSet<Node>();
            unSettledNodes = new HashSet<Node>();
            distance = new HashMap<Node, Integer>();
            predecessors = new HashMap<Node, Node>();
            turning = new HashMap<Node,Double>();
            turning.put(source, 0.0);
            distance.put(source, 0);
            unSettledNodes.add(source);
            
            while (unSettledNodes.size() > 0) {
        
            	   Node node = getMinimumNode(unSettledNodes,source);
            		
                   settledNodes.add(node);

                   unSettledNodes.remove(node);
                    
                   findMinimalDistances(node,source);
                    
            }
	
    } 
 
     public void setPath(Node goal) {
  	   
         this.path = new LinkedList<Node>();
         
         if(goal == null){
         	
         	System.out.println("target is null");
         }
         
         Node step = goal;
         
         
         // check if a path exists
         if (predecessors.get(step) == null) {
        
         	System.out.println("target predecessor is null");
                 
         }
         path.add(step);
         while (predecessors.get(step) != null) {
                 step = predecessors.get(step);
                 path.add(step);
         }
         // Put it into the correct order
         Collections.reverse(path);
         
     }
     
    public LinkedList<Node> getPath(){
    	return path;
    }
    
  	private void findMinimalDistances(Node node,Node goal) {
  	 	
  	     List<Node> adjacentNodes = getNeighbors(node);
  	     
  	     for (Node target : adjacentNodes) {
  	    	
  	     	
  	           if (getShortestDistance(target) > getShortestDistance(node)
  	                             + getDistance(node, target)) { 
  	        	   
  	                     distance.put(target, getShortestDistance(node)+getDistance(node, target));
  	                     predecessors.put(target, node);
  	                     unSettledNodes.add(target);
  	                   
  	                     if(predecessors.get(node) == null){
  	                    
  	                    	turning.put(node, 0.0);
  	                    	turning.put(target, 0.0);
  	                    	
  	                     }else{
  	                    	 
  	                    	 Node pastNode = predecessors.get(node);
  	                     	 setNumberTurningPath(pastNode,node,target);
  	                    	 
  	                     }
  	                     
  	          }
  	          
  	          else if(getShortestDistance(target) == getShortestDistance(node)
  	                             + getDistance(node, target)) {

  	        	  double turningNumberTarget = turning.get(target);
  	        	 
  	        	  Node pastNode = predecessors.get(node);
  	        	  
                  double turningNodeToTarget = getNumberOfTurning(node,pastNode,target); 
                  
                  if(turningNumberTarget >= turningNodeToTarget){
                	  
                	  predecessors.put(target, node);                	  
                	  turning.put(target,turningNodeToTarget);
                	  distance.put(target, getShortestDistance(node)
      	                      + getDistance(node, target));
                  } 
  	         	   
  	           }
  	     	}
  	        
  	 }
  	
  	
  	private double getNumberOfTurning(Node node,Node pastNode, Node target){
  		
  		int angle_PastNode_Node = getAngle(node,pastNode);
  		int angle_Target_Node = getAngle(target,node);
  		
  		int angle = angle_Target_Node - angle_PastNode_Node  ;
  		
  		if (angle < 0) {
			angle += 360;
		}
  	
  		double turningNode = turning.get(node);
  		double turningToTarget = 0;
  		
	  		switch(angle){
	  		
		  		case 0 : 
		  			turningToTarget = (0.0 + turningNode);
		  			break;
		  			
		  		case 45 :
		  			turningToTarget = (0.5 + turningNode);
		  			break;
		  		case 90 :
		  			turningToTarget = (1.0 + turningNode);
		  			break;
		  		case 135 :
		  			turningToTarget = (0.5 + turningNode);
		  			break;
		  		case 180 :
		  			turningToTarget = (0.0 + turningNode);
		  			break;
		  		case 225 :
		  			turningToTarget = (0.5 + turningNode);
		  			break;
		  		case 270 :
		  			turningToTarget = (1.0 + turningNode);
		  			break;
		  		case 315 :
		  			turningToTarget = (0.5 + turningNode);
		  			break;
		  		case 360 :
		  			turningToTarget = (0.0 + turningNode);
		  			break;
		  			
	  	}

	  		return turningToTarget;
			
			
  	}
 
  	
  	private int getAngle(Node node, Node target){
  		
  		double nodeX = node.getPosition().getX();
  		double nodeY = node.getPosition().getY();
  		
  		double targetX = target.getPosition().getX();
  		double targetY = target.getPosition().getY();
  				
  		double theta =Math.atan2((targetY - nodeY),(targetX - nodeX));
  		
  		theta += Math.PI / 2.0;
  		
  		int angle = (int) Math.toDegrees(theta);
  		
  		if (angle < 0) {
			angle += 360;
		}
  		
  		return angle;
			
  	}

  	
  	
  	private void setNumberTurningPath(Node pastNode,Node node ,Node target){
  		
  		int angle_PastNode_Node = getAngle(pastNode,node);
  		int angle_Target_Node = getAngle(target,node);
  		
  		int angle = angle_PastNode_Node - angle_Target_Node;
  		
  		if (angle < 0) {
			angle += 360;
		}
  		
  		double turningNode = turning.get(node);
  		
  		
	  		switch(angle){
	  		
		  		case 0 : 
		  			turning.put(target,0.0 + turningNode);
		  			break;
		  		case 45 :
		  			turning.put(target,0.5 + turningNode);
		  			break;
		  		case 90 :
		  			turning.put(target,1.0 + turningNode);
		  			break;
		  		case 135 :
		  			turning.put(target,0.5 + turningNode);
		  			break;
		  		case 180 :
		  			turning.put(target,0.0 + turningNode);
		  			break;
		  		case 225 :
		  			turning.put(target,0.5 + turningNode);
		  			break;
		  		case 270 :
		  			turning.put(target,1.0 + turningNode);
		  			break;
		  		case 315 :
		  			turning.put(target,0.5 + turningNode);
		  			break;
		  		case 360 :
		  			turning.put(target,0.0 + turningNode);
		  			break;
			
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
	        	
	        
	                if (edge.getSource().equals(node) ){//&& !isSettled(edge.getDestination()) 
	                	
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
		
		int edgeGoalX = edge.getDestination().getPosition().getX()*10;
		int edgeGoalY = edge.getDestination().getPosition().getY()*10;
		
		int obsXpos = (int)obstacle.xProperty().get();
		int obsXposWidth = (int)(obsXpos + (obstacle.getWidth()));
		
		int obsYpos = (int)obstacle.yProperty().get();
		int obsYposWidth = (int)(obsYpos + (obstacle.getHeight()));
		
		if( (obsXpos <= edgeGoalX ) && ( edgeGoalX <= obsXposWidth)){ 
			
			if(( obsYpos <= edgeGoalY) && ( edgeGoalY <= obsYposWidth)){
				
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

	private Node getMinimumNode(Set<Node> vertexes,Node goal) {
    	Node minimum = null;
        for (Node vertex : vertexes) {
                if (minimum == null) {
                        minimum = vertex;
                } else {
                        if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                                minimum = vertex;
                        }
                        else if(getShortestDistance(vertex) == getShortestDistance(minimum)){
                        	
                        	if(turning.get(vertex) <= turning.get(minimum)){
                        		
                        		minimum =  vertex;
                        	}
                        	else{
                        	
                        		}
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

    public Thread getThread() {
		return thread;
	}

    public void setThread(Thread thread) {
		this.thread = thread;
	}
   

}
