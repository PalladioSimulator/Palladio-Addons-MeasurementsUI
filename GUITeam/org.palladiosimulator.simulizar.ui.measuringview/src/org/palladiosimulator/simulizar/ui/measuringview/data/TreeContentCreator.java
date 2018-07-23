package org.palladiosimulator.simulizar.ui.measuringview.data;

public class TreeContentCreator {

	public static TreeContentItem[] createTreeStructure() {
		Measurement measurement1 = new Measurement(true, "Measurement1");
		Measurement measurement2 = new Measurement(true, "Measurement2");
		Measurement measurement3 = new Measurement(true, "Measurement3");
		MeasurementList measurementList1 = new MeasurementList(new Measurement[] {measurement1,measurement2,measurement3});
		
		MeasuringPoint measuringPoint = new MeasuringPoint(false, "Measuring Point 1sdfsdfffffffffffffffffffffffffffffffffddddddddddddd");
		Monitor monitor = new Monitor(measuringPoint, measurementList1, true, "Monitor1");	
		
		Measurement measurement7 = new Measurement(true, "Measurement7");
		Measurement measurement8 = new Measurement(true, "Measurement8");
		Measurement measurement9 = new Measurement(true, "Measurement9");
		Measurement measurement10 = new Measurement(true, "Measurement10");
		MeasurementList measurementList2 = new MeasurementList(new Measurement[] {measurement7,measurement8,measurement9,measurement10});
		
		MeasuringPoint measuringPoint2 = new MeasuringPoint(false, "Measuring Point 2");
		Monitor monitor2 = new Monitor(measuringPoint2, measurementList2, true, "Monitor2");	
		
		return new Monitor[] {monitor, monitor2};
	}
	
	public static TreeContentItem[] createEmptyMeasuringsPoints() {
		MeasuringPoint measuringPoint3 = new MeasuringPoint(false, "Measuring Point 3");
		MeasuringPoint measuringPoint4 = new MeasuringPoint(false, "Measuring Point 4");
		MeasuringPoint measuringPoint5 = new MeasuringPoint(false, "Measuring Point 5");
		
		return new MeasuringPoint[] {measuringPoint3, measuringPoint4, measuringPoint5};
	}
}
