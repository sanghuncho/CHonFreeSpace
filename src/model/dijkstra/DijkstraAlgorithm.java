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
    private Map<Node, Integer> distance;
    private ArrayList<Obstacle> obstacles;
    private Node goal;
    private Node source;
    private Lobby lobbyView;
    private LinkedList<Node> path;
    
    
    public DijkstraAlgorithm(Graph graph, ArrayList<Obstacle> obstacles,Node source,Lobby lobbyView){
    	
    	nodes = new ArrayList<Node>(graph.getNodes());
        edges = new ArrayList<Edge>(graph.getEdges());
        this.obstacles = obstacles;
        this.source = source;
        this.lobbyView = lobbyView;
        
    }
   
   /* public DijkstraAlgorithm(Graph graph, ArrayList<Obstacle> obstacles,Node source, Node goal,Lobby lobbyView){
    	
    	nodes = new ArrayList<Node>(graph.getNodes());
        edges = new ArrayList<Edge>(graph.getEdges());
        this.obstacles = obstacles;
        this.source = source;
        this.goal = goal;
        this.lobbyView = lobbyView;
        
    }*/
    
    
	/*public void run() {
		execute();
	}
    */
     
    /*public void run() {
 		execute();
 	}
    */ 
     
     public void execute() {
     	
         System.out.println("execute \n");

             settledNodes = new HashSet<Node>();
             unSettledNodes = new HashSet<Node>();
             distance = new HashMap<Node, Integer>();
             predecessors = new HashMap<Node, Node>();
             distance.put(source, 0);
             unSettledNodes.add(source);
             
             while (unSettledNodes.size() > 0) {
         
         		
             		Node node = getMinimumNode(unSettledNodes,source);
             		
             		/*
             		System.out.println("node x " + node.getPosition().getX() + "\n");
             		System.out.println("node y " + node.getPosition().getY()  + "\n");*/
             		
                     settledNodes.add(node);

                     unSettledNodes.remove(node);
                     
                     findMinimalDistances(node,source);
                     
             }
 	
             //setPath();
             
            /* Platform.runLater(new Runnable() {
                 @Override public void run() {
                 	lobbyView.createLane(getPath());      
                 }
             });*/
     } 
     
    
    /*public void execute() {
    	
        System.out.println("execute \n");

            settledNodes = new HashSet<Node>();
            unSettledNodes = new HashSet<Node>();
            distance = new HashMap<Node, Integer>();
            predecessors = new HashMap<Node, Node>();
            distance.put(source, 0);
            unSettledNodes.add(source);
            
            while (unSettledNodes.size() > 0) {
        
        		
            		Node node = getMinimumNode(unSettledNodes,goal);
            		
            		
            		System.out.println("node x " + node.getPosition().getX() + "\n");
            		System.out.println("node y " + node.getPosition().getY()  + "\n");
            		
                    settledNodes.add(node);

                    unSettledNodes.remove(node);
                    
                    findMinimalDistances(node,goal);
                    
            }
	
            setPath();
            
            Platform.runLater(new Runnable() {
                @Override public void run() {
                	lobbyView.createLane(getPath());      
                }
            });
    } 
    */
     
     
     
     /*private void setPath() {
    	   
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
         
     }*/
     
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
    	 
    	System.out.println("node x " + node.getPosition().getX() + "\n");
  		 System.out.println("node y " + node.getPosition().getY()  + "\n");
    	 
     	
             if (getShortestDistance(target) > getShortestDistance(node)
                             + getDistance(node, target)) { 
                     distance.put(target, getShortestDistance(node)+getDistance(node, target));
                     predecessors.put(target, node);
                     unSettledNodes.add(target);
                     System.out.println("target x " + target.getPosition().getX() + "\n");
             		System.out.println("target y " + target.getPosition().getY()  + "\n");
                     
             }
             
             else if(getShortestDistance(target) == getShortestDistance(node)
                     + getDistance(node, target)) {
 	   
			 	   Node prevNode = predecessors.get(target);
			 	   Node prevPrevNode = predecessors.get(node);
			 	   Node minimum = null;
			 	    if(MathHelper.shortestDistanceBetweenGoal(prevPrevNode,prevNode,goal).equals(prevPrevNode)){
			 	    	minimum = node;
			 	    }
			 	    else{
			 	    	minimum = prevNode;
			 	    }
			 	   predecessors.remove(target,prevNode);
			 	   predecessors.put(target, minimum); 
			 	   distance.put(target, getShortestDistance(node)
			                             + getDistance(node, target));
			 	  System.out.println("target2 x " + target.getPosition().getX() + "\n");
			 	  System.out.println("target2 y " + target.getPosition().getY()  + "\n");
 	   
             }
          /* else if(getShortestDistance(target) == getShortestDistance(node)
                             + getDistance(node, target)) {
         	   
         	   Node prevNode = predecessors.get(target);
         	   Node minimum = MathHelper.shortestDistanceBetweenGoal(node,prevNode,goal);
         	   predecessors.remove(target,prevNode);
         	   predecessors.put(target, minimum); 
         	   distance.put(target, getShortestDistance(node)
                                     + getDistance(node, target));
         	  System.out.println("target2 x " + target.getPosition().getX() + "\n");
       		  System.out.println("target2 y " + target.getPosition().getY()  + "\n");
         	   
            }*/
     }
        
 }


    private int getShortestDistance(Node destination) {
    	
        Integer d = distance.get(destination);
        
        if (d == null) {
        	
                //return Integer.MAX_VALUE;
        	return 100;
        	
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
                        	
                        	minimum = MathHelper.shortestDistanceBetweenGoal(vertex,minimum,goal);
                        }
                }
        }
        /*System.out.print("Minimu x : " +minimum.getPosition().getX() + "\n");
        System.out.print("Minimu y : " +minimum.getPosition().getY() + "\n");
        System.out.print( "\n");
*/
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
