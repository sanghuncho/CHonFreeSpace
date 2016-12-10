package model.node;

import model.CHmodel;
import util.enums.Directions;
import util.math.Vector;

public class NodeMapHandler {
	
	NodeMap nodeMap;
	Vector size;
	
	/*public NodeMapHandler(NodeMap nodeMap){
		
		this.nodeMap = nodeMap;
		
		defineNeighboringNodes();
		
		 apply obstacle values to the node map 
		nodeMap = new ObstacleGenerator(nodeMap)

	}*/
	
	public NodeMapHandler(Vector size){
		
		
		this.size  = size;
		nodeMap = new NodeMap(size);
		CHmodel.setNodeMap(nodeMap);
		defineNeighboringNodes();
		
		/* apply obstacle values to the node map */
		/*nodeMap = new ObstacleGenerator(nodeMap)*/

	}
	
	private void defineNeighboringNodes(){
		
		size = CHmodel.getSizeVector2D();
		
		for (Node node : nodeMap.getNodes()) {
			
			/* define the position of the nodes */
			int x = node.getPosition().getX();
			int y = node.getPosition().getY();

			/* clear any previous defined neighbors */
			node.getNeighborList().clear();

			/* define the neighboring nodes */

			/* the north node */
			if (!(y == 0)) {
				node.addNeighbor(nodeMap.get(x, y - 1), Directions.NORTH);
			}

			/* the north east node */
			if (!(y == 0) && !(x == size.getX())) {
				node.addNeighbor(nodeMap.get(x + 1, y - 1),
						Directions.NORTH_EAST);
			}

			/* the east node */
			if (!(x == size.getX())) {
				node.addNeighbor(nodeMap.get(x + 1, y), Directions.EAST);
			}

			/* the south east node */
			if (!(x == size.getX()) && !(y == size.getY())) {
				node.addNeighbor(nodeMap.get(x + 1, y + 1),
						Directions.SOUTH_EAST);
			}

			/* the south node */
			if (!(y == size.getY())) {
				node.addNeighbor(nodeMap.get(x, y + 1), Directions.SOUTH);
			}

			/* the south west node */
			if (!(x == 0) && !(y == size.getY())) {
				node.addNeighbor(nodeMap.get(x - 1, y + 1),
						Directions.SOUTH_WEST);
			}

			/* the west node */
			if (!(x == 0)) {
				node.addNeighbor(nodeMap.get(x - 1, y), Directions.WEST);
			}

			/* the north west node */
			if (!(x == 0) && !(y == 0)) {
				node.addNeighbor(nodeMap.get(x - 1, y - 1),
						Directions.NORTH_WEST);
			}
		}
		
	}
	
	public NodeMap getNodeMap() {
		return nodeMap;
	}
	
	
	
	

}
