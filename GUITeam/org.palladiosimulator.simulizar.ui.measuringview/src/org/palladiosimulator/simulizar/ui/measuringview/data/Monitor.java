package org.palladiosimulator.simulizar.ui.measuringview.data;

public class Monitor extends TreeContentItem {
	public static enum Status implements TreeItemStatus {
		ACTIVE, INACTIVE;
	}
	
	public Monitor(MeasuringPoint p, MeasurementList measurements, boolean active, String name) {
		this.children = new TreeContentItem[] {p,measurements};
		this.status = active ? Status.ACTIVE : Status.INACTIVE;
		
		this.name = name;
		this.parent = null;
	}
	
	@Override
	public String toString() {
		return "Monitor: " + this.name;
	}

}
