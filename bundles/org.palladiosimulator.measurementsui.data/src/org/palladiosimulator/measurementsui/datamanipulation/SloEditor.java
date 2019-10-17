package org.palladiosimulator.measurementsui.datamanipulation;


import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.servicelevelobjective.ServiceLevelObjective;
import org.palladiosimulator.servicelevelobjective.ServicelevelObjectiveFactory;
import org.palladiosimulator.servicelevelobjective.ServicelevelObjectivePackage;
import org.palladiosimulator.servicelevelobjective.Threshold;

/**
 * Used for editing and creating Service Level Objective Objects 
 * by calling methods from the ResourceEditor and the SLO-AddOn implementation. 
 * Provides methods for the SLOWizardManager.
 * 
 * @author Jan Hofmann
 *
 */
public class SloEditor {
	
	protected ServicelevelObjectivePackage serviceLevelObjectivePackage = ServicelevelObjectivePackage.eINSTANCE;
    protected ServicelevelObjectiveFactory servicelevelObjectiveFactory = serviceLevelObjectivePackage.getServicelevelObjectiveFactory();
	private ResourceEditor resourceEditor = new ResourceEditorImpl();
   
    /**
     * Creates a new ServiceLevelObjective Object
     * 
     * @param name 			 	the name of the ServiceLevelObjective
     * @param description 	 	the description
     * @param lowerThreshold	the lowerThreshold 
     * @param upperThreshold	the upperThreshold
     * @param msp				the measurement specification
     * @return returns			the created ServiceLevelObjective
     */
	public ServiceLevelObjective createServiceLevelObjective(String name, String description, Threshold lowerthreshold, Threshold upperThreshold, MeasurementSpecification msp) {	
		ServiceLevelObjective serviceLevelObjective = servicelevelObjectiveFactory.createServiceLevelObjective();
		serviceLevelObjective.setName(name);
		if (description != null) {
			serviceLevelObjective.setDescription(description);
		}
		if (lowerthreshold != null) {
			serviceLevelObjective.setLowerThreshold(lowerthreshold);
		}
		if (upperThreshold != null) {
			serviceLevelObjective.setUpperThreshold(upperThreshold);
		}
		if (msp != null) {
			serviceLevelObjective.setMeasurementSpecification(msp);
		}
        
		return serviceLevelObjective;
		
	}
	
    /**
     * 
     * Sets the name of the ServiceLevelObjective to the new name
     * 
     * @param ServiceLevelObjective 	the slo to change the name
     * @param newName 					the new ServiceLevelObjective name
     */
    public void setSLOName(ServiceLevelObjective servicelevelObjective, String newName) {
    	resourceEditor.setServiceLevelObjectiveName(servicelevelObjective, newName);
    }
    
    /**
     * 
     * Sets the description of the ServiceLevelObjective 
     * 
     * @param serviceLevelObjective		the slo to change the description
     * @param newDescription				the new ServiceLevelObjective description
     */
    public void setSLODescription(ServiceLevelObjective servicelevelObjective, String newDescription) {
    	resourceEditor.setServiceLevelObjectiveDescription(servicelevelObjective, newDescription);
    }
    
    /**
     * Sets the lower threshold of the ServiceLevelObjective 
     * 
     * @param serviceLevelObjective 	the slo to change the threshold
     * @param threshold	 				the new ServiceLevelObjective threshold
     */
    public void setSLOLowerThreshold(ServiceLevelObjective servicelevelObjective, Threshold threshold) {
        servicelevelObjective.setLowerThreshold(threshold);
    }
  
    /**
     * Sets the upper threshold of the ServiceLevelObjective 
     * 
     * @param serviceLevelObjective		the slo to change the threshold
     * @param threshold					the new ServiceLevelObjective threshold
     */
    public void setSLOUpperThreshold(ServiceLevelObjective servicelevelObjective, Threshold threshold) {
    	servicelevelObjective.setUpperThreshold(threshold);
    }

    /**
     * Sets the measurement specification of the ServiceLevelObjective 
     * 
     * @param ServiceLevelObjective		the slo to change the measurementSpecification
     * @param msp						the new measurement specification
     */
	public void setMeasurementSpecification(ServiceLevelObjective serviceLevelObjective, MeasurementSpecification measurementSpec) {
		resourceEditor.setMeasurementSpecificationToServiceLevelObjective(serviceLevelObjective, measurementSpec);	
	}
}
