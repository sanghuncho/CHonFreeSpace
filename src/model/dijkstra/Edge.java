package model.dijkstra;

import model.node.Node;
import util.math.Vector;

public class Edge{
	
	private final int id;
    private final Node source;
    private final Node destination;
    private final int weight;
    
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
