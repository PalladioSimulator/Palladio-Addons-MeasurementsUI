package org.palladiosimulator.measurementsui.datamanipulation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

/**
 * Class for editing resources without use of parsley
 * 
 * @author Florian Nieuwenhuizen
 * @author Jan Hofmann
 *
 */
public final class ResourceEditorImpl implements ResourceEditor {

    private final DataEditor editor = new DataEditor();

    private static ResourceEditorImpl instance;

    /**
     * Private constructor for singelton pattern
     */
    private ResourceEditorImpl() {
    }

    /**
     * Returns the instance of the ResourceEditorImpl
     * 
     * @return instance of the ResourceEditorImpl
     */
    public static ResourceEditorImpl getInstance() {
        if (ResourceEditorImpl.instance == null) {
            ResourceEditorImpl.instance = new ResourceEditorImpl();
        }
        return ResourceEditorImpl.instance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#setResourceName(org.
     * eclipse.emf.ecore.EObject, java.lang.String)
     */
    @Override
    public void setResourceName(EObject resource, String newName) {
        editor.editResource(resource, "entityName", newName);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#changeMonitorActive(org.
     * eclipse.emf.ecore.EObject, boolean)
     */
    @Override
    public void changeMonitorActive(EObject monitor, boolean currentBool) {
        editor.editResource(monitor, "activated", !currentBool);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#
     * changeTriggersSelfAdapting(org.palladiosimulator.monitorrepository.MeasurementSpecification,
     * boolean)
     */
    @Override
    public void changeTriggersSelfAdapting(MeasurementSpecification mspec) {
        editor.editResource(mspec, "triggersSelfAdaptations", !mspec.isTriggersSelfAdaptations());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#setMeasuringPoint(org.
     * eclipse.emf.ecore.EObject, org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint)
     */
    @Override
    public void setMeasuringPointToMonitor(EObject monitor, MeasuringPoint mp) {
        editor.editResource(monitor, "measuringPoint", mp);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#setMeasuringPoint(org.
     * eclipse.emf.ecore.EObject, org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint)
     */
    @Override
    public void setMeasuringPointToMonitor(EObject monitor, EObject mp) {
        editor.editResource(monitor, "measuringPoint", mp);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#addMeasuringPoint(org.
     * eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
     */
    @Override
    public void addMeasuringPointToRepository(EObject mpRep, EObject mp) {
        editor.addResource(mpRep, "measuringPoints", mp);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#addMeasuringPoint(org.
     * eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
     */
   
    @Override
    public void addServiceLevelObjectiveToRepository(EObject sloRep, EObject slo) {
    	editor.addResource(sloRep, "servicelevelobjectives", slo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#deleteResource(org.
     * eclipse.emf.ecore.EObject)
     */
    @Override
    public void deleteResource(EObject objToDelete) {
        editor.deleteResource(objToDelete);

    }

    /* (non-Javadoc)
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#deleteMultipleResources(org.eclipse.emf.common.util.EList)
     */
    @Override
    public void deleteMultipleResources(EList<MeasurementSpecification> objsToDelete) {
        editor.deleteMultipleResources(objsToDelete);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#setMetricDescription(org
     * .eclipse.emf.ecore.EObject, org.palladiosimulator.metricspec.MetricDescription)
     */
    @Override
    public void setMetricDescription(EObject aMeasurementSpecification, MetricDescription aMetricDescription) {
        editor.editResource(aMeasurementSpecification, "metricDescription", aMetricDescription);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#
     * addMeasurementSpecificationToMonitor(org.eclipse.emf.ecore.EObject,
     * org.palladiosimulator.monitorrepository.MeasurementSpecification)
     */
    @Override
    public void addMeasurementSpecificationToMonitor(EObject monitor, MeasurementSpecification mspec) {
        editor.addResource(monitor, "measurementSpecifications", mspec);

    }

    /* (non-Javadoc)
     * 
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#addMeasurementSpecificationsToMonitor(org.eclipse.emf.ecore.EObject, org.eclipse.emf.common.util.EList)
     */
    @Override
    public void addMeasurementSpecificationsToMonitor(EObject monitor, EList<MeasurementSpecification> mSpecList) {
        editor.addListOfResources(monitor, "measurementSpecifications", mSpecList);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#addMonitorToRepository(
     * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
     */
    @Override
    public void addMonitorToRepository(EObject monitorRepository, EObject monitor) {
        editor.addResource(monitorRepository, "monitors", monitor);

    }
    
    /* (non-Javadoc)
     * 
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#setProcessingType(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
     */
    @Override
    public void setProcessingType(EObject measurementSpecification, EObject processingType) {
        editor.editResource(measurementSpecification, "processingType", processingType);
    }

    /* (non-Javadoc)
     * 
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#setAProcessingTypeAttribute(org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.Object)
     */
    @Override
    public void setAProcessingTypeAttribute(EObject processingType, String processingTypeAttributeName, Object value) {
        editor.editResource(processingType, processingTypeAttributeName, value);
        
    }

    /* (non-Javadoc)
     * 
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#setServiceLevelObjectivename(org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.Object)
     */
	@Override
	public void setServiceLevelObjectiveName(EObject slo, String name) {
        editor.editResource(slo, "name", name);
		
	}
	
    /* (non-Javadoc)
     * 
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#setServiceLevelObjectiveDescription(org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.Object)
     */
	@Override
	public void setServiceLevelObjectiveDescription(EObject slo, String description) {
        editor.editResource(slo, "description", description);
        
	}

    /* (non-Javadoc)
     * 
     * @see org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor#setMeasurementSpecificationToServiceLevelObjective(org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.Object)
     */
	@Override
	public void setMeasurementSpecificationToServiceLevelObjective(EObject slo, EObject measurementSpec) {
		editor.editResource(slo, "measurementSpecification", measurementSpec);
		
	}
	
}
