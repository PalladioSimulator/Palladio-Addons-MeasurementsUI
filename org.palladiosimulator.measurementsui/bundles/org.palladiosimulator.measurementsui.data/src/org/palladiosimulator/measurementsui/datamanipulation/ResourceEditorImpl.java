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
public class ResourceEditorImpl implements ResourceEditor {

    private final DataEditor editor = new DataEditor();

    /* (non-Javadoc)
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#setResourceName(org.eclipse.emf.ecore.EObject, java.lang.String)
     */
    @Override
    public void setResourceName(EObject resource, String newName) {
        editor.editResource(resource, "entityName", newName);
    }

    /* (non-Javadoc)
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#changeMonitorActive(org.eclipse.emf.ecore.EObject, boolean)
     */
    @Override
    public void changeMonitorActive(EObject monitor, boolean currentBool) {
        editor.editResource(monitor, "activated", !currentBool);
    }

    /* (non-Javadoc)
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#changeTriggersSelfAdapting(org.palladiosimulator.monitorrepository.MeasurementSpecification, boolean)
     */
    @Override
    public void changeTriggersSelfAdapting(MeasurementSpecification mspec, boolean currentBool) {
        editor.editResource(mspec, "triggersSelfAdaptations", !currentBool);
    }

    /* (non-Javadoc)
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#setMeasuringPoint(org.eclipse.emf.ecore.EObject, org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint)
     */
    @Override
    public void setMeasuringPoint(EObject monitor, MeasuringPoint mp) {
        editor.editResource(monitor, "measuringPoint", mp);
    }

    /* (non-Javadoc)
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#addMeasuringPoint(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
     */
    @Override
    public void addMeasuringPoint(EObject mpRep, EObject mp) {
        editor.editResource(mpRep, "measuringPoints", mp);
    }

}