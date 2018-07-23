package org.palladiosimulator.simulizar.ui.measuringview.data;

public class MeasuringPoint extends TreeContentItem{

	private enum Status implements TreeItemStatus {
		EMPTY, NOT_EMPTY;
	}
	
	public MeasuringPoint(boolean empty, String name) {
		this.children = null;
		this.status = empty ? Status.EMPTY : Status.NOT_EMPTY;
		
		this.name = name;
		this.parent = null;
	}
	
	@Override
	public String toString() {
		return "Measuring Point: " + this.name;
	}
	
	@Override
	public boolean hasChildren() {
		return false;
	}

}
