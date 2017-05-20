package model.dijkstra;

import model.node.Node;

public class Edge {
	
	private final int id;
    private final Node source;
    private final Node destination;
    private final int weight;
    
    /**
     * The edge connects the source node and the destination node.
     * The edge hat the weight value and this is needed to calculate the shortest path.
    */ 
    public Edge(int id, Node source, Node destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
    
	public int getId() {
        return id;
    }
    public Node getDestination() {
        return destination;
	}

	public Node getSource() {
        return source;
	}
	public int getWeight() {
        return weight;
	}
	

}
