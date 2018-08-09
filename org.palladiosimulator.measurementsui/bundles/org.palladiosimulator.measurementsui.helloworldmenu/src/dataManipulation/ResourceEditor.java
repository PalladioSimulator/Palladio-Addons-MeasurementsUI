package dataManipulation;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;

public class ResourceEditor {

	private final DataEditor editor = new DataEditor();
	
	public void setResourceName(EObject resource ,String newName) {
		editor.editResourceName(resource, "entityName", newName);
	}
	
	public void setMonitorActive(EObject monitor) {
		editor.editResourceActivated(monitor, "activated", true);
	}
	public void setMonitorUnactive(EObject monitor) {
		editor.editResourceActivated(monitor, "activated", false);
	}
		
	public void setMeasuringPoint(EObject monitor, MeasuringPoint mp) {
		editor.editMeasuringPoint(monitor, "measuringPoint", mp);
	}
	
	public void addMeasuringPoint(EObject mpRep ,EObject mp) {
		editor.addResource(mpRep, "measuringPoints", mp);
	}
	
//	public void setMeasurementSpecification(EObject monitor, MeasurementSpecification newSpec) {
//		editor.editMeasurementSpecification(monitor, "measurementSpecifications", newSpec);
//	}

	
}
