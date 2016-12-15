package model.dijkstra;

import java.util.List;

import model.node.Node;

public class Graph {

	
	private final List<Node> nodes;
    private final List<Edge> edges;

    public Graph(List<Node> nodes, List<Edge> edges) {
            this.nodes = nodes;
            this.edges = edges;
    }

    public List<Node> getNodes() {
            return nodes;
    }

    public List<Edge> getEdges() {
            return edges;
    }


}
