package model.node;

import java.util.ArrayList;
import java.util.List;

import util.math.Vector;
import util.math.Vector2D;

public class NodeMap {
	
	private List<ArrayList<Node>> structure;
	private Vector size;
	
	
	public NodeMap(Vector size) {

		this.size = size;

		structure = new ArrayList<ArrayList<Node>>();

		for (int x = 0; x < size.getX(); x++) {

			structure.add(new ArrayList<Node>());

			for (int y = 0; y < size.getY(); y++) {

				Node node = new Node(new Vector2D(x, y));

				structure.get(x).add(node);
			}
		}
		
		System.out.println("NodeMap complete!!  \n");
	}
	
	public List<Node> getNodes() {

		/* create a new list which will contain all nodes */
		List<Node> list = new ArrayList<>();

		/* Loop through all sublists */
		for (ArrayList<Node> sublist : structure) {

			/* loop through all nodes */
			for (Node node : sublist) {

				/* add the node to the list */
				list.add(node);
			}
		}

		return list;
	}
	
	public Node get(int x, int y) {

		if (x < 0 || y < 0 || x >= size.getX() || y >= size.getY()) {

			/* request out of bounds */
			return null;
		}
		
		return structure.get(x).get(y);
	}
	
	public Vector getDimensions() {
		return size;
	}
}
