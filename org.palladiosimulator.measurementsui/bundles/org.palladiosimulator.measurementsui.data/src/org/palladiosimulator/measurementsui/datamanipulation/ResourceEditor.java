package org.palladiosimulator.measurementsui.datamanipulation;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

/**
 * Interface for editing Resources through {@link DataEditor}.
 * 
 * @author Florian Nieuwenhuizen
 *
 */
public interface ResourceEditor {
    /**
     * Changes the EntityName of a resource through EMF Commands.
     * 
     * @param resource
     *            The resource to change the name
     * @param newName
     *            The new name of the resource
     */
    void setResourceName(EObject resource, String newName);

    /**
     * Changes the current status of "activated" to its negative through EMF Commands.
     * 
     * @param monitor
     *            the monitor to change
     * @param currentBool
     *            the value to change
     */
    void changeMonitorActive(EObject monitor, boolean currentBool);

    /**
     * Changes current bool triggers self adapting to its negative through EMF Commands.
     * 
     * @param mspec
     *            the measurement specification to change
     */
    void changeTriggersSelfAdapting(MeasurementSpecification mspec);

    /**
     * alternative method to set measuring points, if it is not possible with parsley. Current MP is
     * overwritten through new MP.
     * 
     * @param monitor
     * @param mp
     */
    void setMeasuringPointToMonitor(EObject monitor, MeasuringPoint mp);

    /**
     * alternative method to add measuring points, if it is not possible with parsley. New MP is
     * appended to MPRepository.
     * 
     * @param mpRep
     * @param mp
     */
    void addMeasuringPointToRepository(EObject mpRep, EObject mp);

    /**
     * Delete the passed EObject.
     * 
     * @param objToDelete
     */
    void deleteResource(EObject objToDelete);

    /**
     * Sets MetricDescription for a MeasurementSpecification using EMF Commands.
     * 
     * @param aMeasurementSpecification
     * @param aMetricDescription
     */
    void setMetricDescription(EObject aMeasurementSpecification, MetricDescription aMetricDescription);

    /**
     * Appends a new MeasurementSpecification to the list of MeasurementSpecifications for the
     * monitor.
     * 
     * @param monitor
     *            The Monitor to which the new specification is added
     */
    void addMeasurementSpecificationToMonitor(EObject monitor, MeasurementSpecification mspec);

    /**
     * Appends a Monitor to a Monitor Repository through AddCommands.
     * 
     * @param monitorRepository
     * @param monitor
     */
    void addMonitorToRepository(EObject monitorRepository, EObject monitor);

}