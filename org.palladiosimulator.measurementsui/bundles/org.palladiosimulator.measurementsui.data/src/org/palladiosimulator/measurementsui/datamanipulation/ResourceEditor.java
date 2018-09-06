package org.palladiosimulator.measurementsui.datamanipulation;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

public interface ResourceEditor {
    /**
     * Changes the EntityName of a resource
     * 
     * @param resource
     *            The resource to change the name
     * @param newName
     *            The new name of the resource
     */
    void setResourceName(EObject resource, String newName);

    /**
     * Changes the current status of "activated" to its negative
     * 
     * @param monitor
     *            the monitor to change
     * @param currentBool
     *            the value to change
     */
    void changeMonitorActive(EObject monitor, boolean currentBool);

    /**
     * Changes current bool triggers self adapting to its negative
     * 
     * @param mspec
     *            the measurement specification to change
     * @param currentBool
     *            current value of triggersSelfAdaptions
     */
    void changeTriggersSelfAdapting(MeasurementSpecification mspec, boolean currentBool);

    /**
     * alternative method to set measuring points, if it is not possible with parsley
     * 
     * @param monitor
     * @param mp
     */
    void setMeasuringPoint(EObject monitor, MeasuringPoint mp);

    /**
     * alternative method to add measuring points, if it is not possible with parsley
     * 
     * @param mpRep
     * @param mp
     */
    void addMeasuringPoint(EObject mpRep, EObject mp);

}