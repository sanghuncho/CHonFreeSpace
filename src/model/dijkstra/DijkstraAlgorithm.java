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

public class DijkstraAlgorithm  {//implements Runnable
	
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
     
     /*public void execute() {
     	
         System.out.println("execute \n");

             settledNodes = new HashSet<Node>();
             unSettledNodes = new HashSet<Node>();
             distance = new HashMap<Node, Integer>();
             predecessors = new HashMap<Node, Node>();
             turning = new HashMap<Node,Integer>();
             turning.put(source, 0);
             distance.put(source, 0);
             unSettledNodes.add(source);
             
             while (unSettledNodes.size() > 0) {
         
         		
             		Node node = getMinimumNode(unSettledNodes,source);
             		
                    settledNodes.add(node);

                    unSettledNodes.remove(node);
                     
                    findMinimalDistances(node,source);
                     
             }
 	
     }*/
    
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
         
         System.out.println("number of Path turning : " +turning.get(goal)+ "\n");
         System.out.println("goal pointX : " +goal.getPosition().getX() + "\n");
         System.out.println("goal pointY : " +goal.getPosition().getY() + "\n");
         
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
  	                    	 
  	                    	 //System.out.println("turning : "+ turning.get(node) +"\n");
  	                    	 
  	                     	 //setNumberTurningPath(node, target);
  	                     	 turning.put(target, 0.0);
  	                     }else{
  	                    	 Node pastNode = predecessors.get(node);
  	                     	 setNumberTurningPath(pastNode,node,target);
  	                    	 
  	                    	//setNumberTurningPath(node, target);
  	                     }
  	                     
  	          }
  	          
  	          else if(getShortestDistance(target) == getShortestDistance(node)
  	                             + getDistance(node, target)) {

  	        	  double turningNumberTarget = turning.get(target);
  	        	 
  	        	  Node pastNode = predecessors.get(node);
  	        	  
                  double turningNodeToTarget = getNumberOfTurning(node,pastNode,target); 
                  
                  if(turningNumberTarget >= turningNodeToTarget){
                	  
                	  //predecessors.remove(target);
                	  predecessors.put(target, node);
                	 // turning.remove(target);
                	  System.out.println("turningNodeToTarget : " + turningNodeToTarget + "\n");
                	  turning.put(target,turningNodeToTarget);
                	  distance.put(target, getShortestDistance(node)
      	                      + getDistance(node, target));
                	  System.out.println("turning value of target : " + turning.get(target) + "\n");
                  } 
  	         	   
  	           }
  	     	}
  	        
  	 }
  	private Node getMinimumTurningPathNode(Node node, Node prevNode){
  		
  		if(turning.get(node) < turning.get(prevNode)){
  			
  			return node;
  			
  		}
  		else{
  			
  			return prevNode;
  		}
  		
  	}
  	
  	private double getNumberOfTurning(Node node,Node pastNode, Node target){
  		
  	/*	System.out.println("pastX : "+ pastNode.getPosition().getX() +"\n");
  		System.out.println("pastY : "+ pastNode.getPosition().getY() +"\n");
  		
  		System.out.println("nodeX : "+ node.getPosition().getX() +"\n");
  		System.out.println("nodeY : "+ node.getPosition().getY() +"\n");
  		
  		System.out.println("targetX : "+ target.getPosition().getX() +"\n");
  		System.out.println("targetY : "+ target.getPosition().getY() +"\n");*/
  		
  		int angle_PastNode_Node = getAngle(node,pastNode);
  		int angle_Target_Node = getAngle(target,node);
  		
  		int angle = angle_Target_Node - angle_PastNode_Node  ;
  		
  		if (angle < 0) {
			angle += 360;
		}
  		/*
  		System.out.println("turning target : "+ turning.get(target) +"\n");
  		System.out.println("turning node : "+ turning.get(node) +"\n");
  		
  		
  		System.out.println("angle : "+ angle +"\n");
  		
  		System.out.println("\n");*/

  		double turningNode = turning.get(node);
  		double turningToTarget = 0;
  		
	  		switch(angle){
	  		
		  		case 0 : 
		  			turningToTarget = 0.0 + turningNode;
		  			
		  		case 45 :
		  			turningToTarget = 0.5 + turningNode;
		  		
		  		case 90 :
		  			turningToTarget =  1.0 + turningNode;
		  			
		  		case 135 :
		  			turningToTarget = 0.5 + turningNode;
		  			
		  		case 180 :
		  			turningToTarget = 0.0 + turningNode;
		  		case 225 :
		  			turningToTarget = 0.5 + turningNode;
		  			
		  		case 270 :
		  			turningToTarget = 1.0 + turningNode;
		  			
		  		case 315 :
		  			turningToTarget = 0.5 + turningNode;
		  			
		  		case 360 :
		  			turningToTarget = 0.0 + turningNode;
		  			
		  			
	  	}
	  		return turningToTarget;
			
			
  	}
    

/*  	private void findMinimalDistances(Node node,Node goal) {
 	
     List<Node> adjacentNodes = getNeighbors(node);
     
     for (Node target : adjacentNodes) {
    	 
    	
    	 
     	
             if (getShortestDistance(target) > getShortestDistance(node)
                             + getDistance(node, target)) { 
                     distance.put(target, getShortestDistance(node)+getDistance(node, target));
                     predecessors.put(target, node);
                     unSettledNodes.add(target);
                   
                     if(predecessors.get(node).equals(null)){
                    	 Node past = node;
                    	 turning.put(past, 0.0);
                     	 setNumberTurningPath(past, target);
                     }
                     
             }
          
          else if(getShortestDistance(target) == getShortestDistance(node)
                             + getDistance(node, target)) {
         	   
         	   Node prevNode = predecessors.get(target);
         	   Node minimum = MathHelper.shortestDistanceBetweenGoal(node,prevNode,goal);
         	  // predecessors.remove(target,prevNode);
         	   //predecessors.remove(target);
         	   predecessors.put(target, minimum); 
         	   distance.put(target, getShortestDistance(node)
                                     + getDistance(node, target));
         	   
         	  distance.put(target, getShortestDistance(minimum)
                      + getDistance(minimum, target));
         	  
         	  System.out.println("target2 x " + target.getPosition().getX() + "\n");
       		  System.out.println("target2 y " + target.getPosition().getY()  + "\n");
         	   
            }
     }
        
  	}*/
  	
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
        	//return 100;
        	
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
		
		/*System.out.println("obsXpos :  "+ obsXpos + "\n");
		System.out.println("obsXposWidth :  "+ obsXposWidth + "\n");
		
		System.out.println("obsYpos :  "+ obsYpos + "\n");
		System.out.println("obsYposWidth :  "+ obsYposWidth + "\n");
		
		System.out.println("edgeGoalX :  "+ edgeGoalX + "\n");
		System.out.println("edgeGoalY :  "+ edgeGoalY + "\n");*/
		
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
	
	/*private Node getMinimumNode(Set<Node> vertexes,Node goal) {
    	Node minimum = null;
        for (Node vertex : vertexes) {
                if (minimum == null) {
                        minimum = vertex;
                } else {
                        if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                                minimum = vertex;
                        }
                        else if(getShortestDistance(vertex) == getShortestDistance(minimum)){
                        	
                        	minimum = MathHelper.shortestDistanceBetweenGoal(vertex,minimum,goal);
                        }
                }
        }
        System.out.print("Minimu x : " +minimum.getPosition().getX() + "\n");
        System.out.print("Minimu y : " +minimum.getPosition().getY() + "\n");
        System.out.print( "\n");

        return minimum;
    }*/
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
                        	
                        	//minimum = MathHelper.shortestDistanceBetweenGoal(vertex,minimum,goal);
                        	if(turning.get(vertex) < turning.get(minimum)){
                        		
                        		minimum =  vertex;
                        	}
                        	else{
                        	
                        		}
                        }
                }
        }
       /* System.out.print("Minimu x : " +minimum.getPosition().getX() + "\n");
        System.out.print("Minimu y : " +minimum.getPosition().getY() + "\n");
        System.out.print( "\n");*/

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
    
    /*public void start() {
		if (getThread() == null) {
			setThread(new Thread(this));
			getThread().start();
		}
	}*/
        
   

}
