package org.palladiosimulator.measurementsui.datamanipulation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

public class ResourceEditor {

    private final DataEditor editor = new DataEditor();

    public void setResourceName(EObject resource, String newName) {
        editor.editResourceName(resource, "entityName", newName);
    }

    /**
     * Changes the current status of "activated" to its negative
     * 
     * @param monitor
     *            the monitor to change
     * @param currentBool
     *            the value to change
     */
    public void changeMonitorActive(EObject monitor, boolean currentBool) {
        editor.editResourceActivated(monitor, "activated", !currentBool);
    }

    /**
     * Changes current bool triggersselfadapting to its negative
     * 
     * @param mspec
     *            the measurement specification to change
     * @param currentBool
     *            current value of triggersSelfAdaptions
     */
    public void changeTriggersSelfAdapting(MeasurementSpecification mspec, boolean currentBool) {
        editor.editResourceActivated(mspec, "triggersSelfAdaptations", !currentBool);
    }

    public void setMeasuringPoint(EObject monitor, MeasuringPoint mp) {
        editor.editMeasuringPoint(monitor, "measuringPoint", mp);
    }

    public void addMeasuringPoint(EObject mpRep, EObject mp) {
        editor.addResource(mpRep, "measuringPoints", mp);
    }

}
