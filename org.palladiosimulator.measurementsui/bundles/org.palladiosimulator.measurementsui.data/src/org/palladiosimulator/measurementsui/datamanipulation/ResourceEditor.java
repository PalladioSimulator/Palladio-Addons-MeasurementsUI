package org.palladiosimulator.measurementsui.datamanipulation;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

public interface ResourceEditor {
	/**
	 * Changes the EntityName of a resource
	 * 
	 * @param resource The resource to change the name
	 * @param newName  The new name of the resource
	 */
	void setResourceName(EObject resource, String newName);

	/**
	 * Changes the current status of "activated" to its negative
	 * 
	 * @param monitor     the monitor to change
	 * @param currentBool the value to change
	 */
	void changeMonitorActive(EObject monitor, boolean currentBool);

	/**
	 * Changes current bool triggers self adapting to its negative
	 * 
	 * @param mspec       the measurement specification to change
	 * @param currentBool current value of triggersSelfAdaptions
	 */
	void changeTriggersSelfAdapting(MeasurementSpecification mspec, boolean currentBool);

	/**
	 * alternative method to set measuring points, if it is not possible with
	 * parsley
	 * 
	 * @param monitor
	 * @param mp
	 */
	void setMeasuringPointToMonitor(EObject monitor, MeasuringPoint mp);

	/**
	 * alternative method to add measuring points, if it is not possible with
	 * parsley
	 * 
	 * @param mpRep
	 * @param mp
	 */
	void addMeasuringPointToRepository(EObject mpRep, EObject mp);

	/**
	 * Delete the passed EObject
	 * 
	 * @param objToDelete
	 */
	void deleteResource(EObject objToDelete);

	/**
	 * Sets MetricDescription for a MeasurementSpecification. A List of all Metric
	 * Descriptions can be gotten through:
	 * aMSpec.getMetricDescription().getRepository().getMetricDescriptions()
	 * 
	 * @param aMeasurementSpecification
	 * @param aMetricDescription
	 */
	void setMetricDescription(EObject aMeasurementSpecification, MetricDescription aMetricDescription);

	/**
	 * Adds a new MeasurementSpecification to the list of MeasurementSpecifications
	 * for the monitor
	 * 
	 * @param monitor The Monitor to which the new specification is added
	 */
	void addMeasurementSpecification(EObject monitor);

	/**
	 * adds a Monitor to a Monitor Repository through AddCommands
	 * 
	 * @param monitorRepository
	 * @param monitor
	 */
	void addMonitorToRepository(EObject monitorRepository, EObject monitor);
}