package model.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.node.Node;
import model.node.NodeMap;
import util.math.Vector;

public class DijkstraAlgorithm {
	
	private List<Node> nodes;
    private List<Edge> edges;
    
    private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distance;
    
    public DijkstraAlgorithm(Graph graph){
    	
    	nodes = new ArrayList<Node>(graph.getNodes());
        edges = new ArrayList<Edge>(graph.getEdges());
    }
        
    public void execute(Node source) {
    	
    	
        System.out.println("execute \n");

            settledNodes = new HashSet<Node>();
            unSettledNodes = new HashSet<Node>();
            distance = new HashMap<Node, Integer>();
            predecessors = new HashMap<Node, Node>();
            distance.put(source, 0);//Integer d = distance.get(destination);
            unSettledNodes.add(source);
            
            while (unSettledNodes.size() > 0) {
            	

            		Node node = getMinimum(unSettledNodes);
            		
                    settledNodes.add(node);

                    unSettledNodes.remove(node);
                    
                    findMinimalDistances(node);
            }
    }  
    private Node getMinimum(Set<Node> vertexes) {
    	Node minimum = null;
        for (Node vertex : vertexes) {
                if (minimum == null) {
                        minimum = vertex;
                } else {
                        if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                                minimum = vertex;
                        }
                }
        }
        return minimum;
    }
    
    
    private int getShortestDistance(Node destination) {
    	
        Integer d = distance.get(destination);
        
        if (d == null) {
                return Integer.MAX_VALUE;
        } else {
                return d;
        }
    }
    
    private void findMinimalDistances(Node node) {
    	
        List<Node> adjacentNodes = getNeighbors(node);
        
        for (Node target : adjacentNodes) {
                if (getShortestDistance(target) > getShortestDistance(node)
                                + getDistance(node, target)) {
                        distance.put(target, getShortestDistance(node)
                                        + getDistance(node, target));
                        predecessors.put(target, node); //HashMap<Node, Node>();
                        unSettledNodes.add(target);
                }
        }

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
    
    private List<Node> getNeighbors(Node node) {
    	
        List<Node> neighbors = new ArrayList<Node>();
        
   
        for (Edge edge : edges) {
        	
                if (edge.getSource().equals(node)
                                && !isSettled(edge.getDestination())) {
                        neighbors.add(edge.getDestination());
                }
        }
        
        return neighbors;
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
