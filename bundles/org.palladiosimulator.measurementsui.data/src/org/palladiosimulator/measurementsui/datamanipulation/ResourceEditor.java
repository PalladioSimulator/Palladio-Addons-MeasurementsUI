package org.palladiosimulator.measurementsui.datamanipulation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

/**
 * Interface for editing Resources through {@link DataEditor}.
 * 
 * @author Florian Nieuwenhuizen
 * @author Jan Hofmann
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
     * Delete a List of MeasurementSpecifications, specifically for deleting mspecs out of the
     * wizard monitor in edit mode in the 3rd page.
     * 
     * @param objsToDelete
     */
    void deleteMultipleResources(EList<MeasurementSpecification> objsToDelete);

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
     * 
     * @param monitor
     * @param MSpecList
     */
    void addMeasurementSpecificationsToMonitor(EObject monitor, EList<MeasurementSpecification> mSpecList);

    /**
     * Appends a Monitor to a Monitor Repository through AddCommands.
     * 
     * @param monitorRepository
     * @param monitor
     */
    void addMonitorToRepository(EObject monitorRepository, EObject monitor);

    /**
     * Sets the Processing Type for a Measurement Specification using EMF Set Command. Used in 4th
     * page of Wizard.
     * 
     * @param measurementSpecification
     * @param processingType
     */
    public void setProcessingType(EObject measurementSpecification, EObject processingType);

    /**
     * Sets the PT attribute using EMF Set Command. Value should be either double or int, depending
     * on the attribute.
     * 
     * @param processingType
     * @param processingTypeAttributeName
     *            The String name of the PT Attribute that we want to set
     * @param value
     */
    public void setAProcessingTypeAttribute(EObject processingType, String processingTypeAttributeName, Object value);

    /**
     * Binds a Measuring Point to a Monitor using EMF Command.
     * 
     * @param monitor 
     * @param mp
     */
	void setMeasuringPointToMonitor(EObject monitor, EObject mp);

    /**
     * Sets the name of the Service Level Objective using EMF Set Command.
     * 
     * @param resource	the Slo to set the name
     * @param newName	the new name to set
     */
	void addServiceLevelObjectiveToRepository(EObject sloRep, EObject slo);
	
    /**
     * Sets the name of the Service Level Objective using EMF Set Command.
     * 
     * @param resource	the Slo to set the name
     * @param newName	the new name to set
     */
	void setServiceLevelObjectiveName(EObject slo, String name);

    /**
     * Sets the description of the Service Level Objective using EMF Set Command.
     * 
     * @param resource	the Slo to set the name
     * @param newName	the new name to set
     */
	void setServiceLevelObjectiveDescription(EObject slo, String newDescription);

    /**
     * Binds a Measurement Specification the Service Level Objective using EMF Set Command.
     * 
     * @param resource	the Slo to set the name
     * @param newName	the new name to set
     */
	void setMeasurementSpecificationToServiceLevelObjective(EObject slo,
			EObject measurementSpec);

}