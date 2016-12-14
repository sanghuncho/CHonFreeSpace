package model.dijkstra;

import java.util.ArrayList;
import java.util.List;

import model.node.Node;
import model.node.NodeMap;
import util.math.Vector;

public class DijkstraAlgorithm {
	
	private List<Node> nodes;
    private List<Edge> edges;
    
    public DijkstraAlgorithm(Vector start,Vector goal,CostMap costMap){
    	
    	nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        
        for (Node node : costMap.getNodeMap().getNodes()) {
        	
        	nodes.add(node);
        	
        	
        }
        
        
    	
    	
    	
    }

}
