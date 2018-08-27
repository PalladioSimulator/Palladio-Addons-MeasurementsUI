package org.palladiosimulator.measurementsui.datamanipulation;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

/**
 * Class for editing resources without use of parsley
 * 
 * @author Florian
 *
 */
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
     * Changes current bool triggers self adapting to its negative
     * 
     * @param mspec
     *            the measurement specification to change
     * @param currentBool
     *            current value of triggersSelfAdaptions
     */
    public void changeTriggersSelfAdapting(MeasurementSpecification mspec, boolean currentBool) {
        editor.editResourceActivated(mspec, "triggersSelfAdaptations", !currentBool);
    }

    /**
     * alternative method to set measuring points, if it is not possible with parsley
     * 
     * @param monitor
     * @param mp
     */
    public void setMeasuringPoint(EObject monitor, MeasuringPoint mp) {
        editor.editMeasuringPoint(monitor, "measuringPoint", mp);
    }

    /**
     * alternative method to add measuring points, if it is not possible with parsley
     * 
     * @param mpRep
     * @param mp
     */
    public void addMeasuringPoint(EObject mpRep, EObject mp) {
        editor.addResource(mpRep, "measuringPoints", mp);
    }

}
