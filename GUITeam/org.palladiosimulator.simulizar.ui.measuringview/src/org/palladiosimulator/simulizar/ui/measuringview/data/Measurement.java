package org.palladiosimulator.simulizar.ui.measuringview.data;

public class Measurement extends TreeContentItem {

	private enum Status implements TreeItemStatus {
		SELF_ADAPTING, NON_SELF_ADAPTING;
	}
	
	public Measurement(boolean selfAdapting, String name) {
		this.children = null;
		this.status = selfAdapting ? Status.SELF_ADAPTING : Status.NON_SELF_ADAPTING;
		
		this.name = name;
		this.parent = parent;
	}
	
	@Override
	public String toString() {
		return "Measurement: " + this.name;
	}
	
	@Override
	public boolean hasChildren() {
		return false;
	}
}
