package model.node;

import util.enums.Property;

public class NodeProperty {
	
	/** The agent id. */
	private int nodeID;

	/** The property. */
	private Property property;
	
	public NodeProperty(int nodeID, Property property) {
		this.nodeID = nodeID;
		this.property = property;
	}

}
