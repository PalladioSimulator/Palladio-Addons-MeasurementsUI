package org.palladiosimulator.simulizar.ui.measuringview.data;

public class MeasurementList extends TreeContentItem {
	private enum Status implements TreeItemStatus {
		UNDEFINED;
	}
	
	public MeasurementList(Measurement[] measurements) {
		this.children = measurements;
		this.name = "Measurements:";
		this.parent = parent;
	}
	
	@Override
	public TreeItemStatus getStatus() {
		return Status.UNDEFINED;
	}
}
