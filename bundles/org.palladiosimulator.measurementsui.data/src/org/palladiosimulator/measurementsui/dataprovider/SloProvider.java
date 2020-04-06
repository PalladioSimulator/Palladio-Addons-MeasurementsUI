package org.palladiosimulator.measurementsui.dataprovider;

import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.servicelevelobjective.ServiceLevelObjective;
import org.palladiosimulator.servicelevelobjective.Threshold;

/**
 * Provides and saves temporary Service Level Objective data.
 * Is used by the SloWizardmodel in order to provide data for the SloWizard pages.
 * 
 * @author Jan Hofmann
 */
public class SloProvider {
	
    private static SloProvider instance;
	
	private String name;
	private String description;
	private MeasurementSpecification measurementSpecification;
	private ServiceLevelObjective serviceLevelObjective;
	private Threshold lowerThreshold;
	private Threshold upperThreshols;
	
    /**
     * Get the instance of DataApplication
     * @return instance of DataApplication
     */
    public static SloProvider getInstance() {
        if (SloProvider.instance == null) {
        	SloProvider.instance = new SloProvider();
        }
        return SloProvider.instance;
    }
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the measurementSpecification
	 */
	public MeasurementSpecification getMeasurementSpecification() {
		return measurementSpecification;
	}
	
	/**
	 * @param measurementSpecification the measurementSpecification to set
	 */
	public void setMeasurementSpecification(MeasurementSpecification measurementSpecification) {
		this.measurementSpecification = measurementSpecification;
	}
	
	/**
	 * @return the serviceLevelObjective
	 */
	public ServiceLevelObjective getServiceLevelObjective() {
		return serviceLevelObjective;
	}
	
	/**
	 * @param serviceLevelObjective the serviceLevelObjective to set
	 */
	public void setServiceLevelObjective(ServiceLevelObjective serviceLevelObjective) {
		this.serviceLevelObjective = serviceLevelObjective;
	}
	
	/**
	 * @return the lowerThreshold
	 */
	public Threshold getLowerThreshold() {
		return lowerThreshold;
	}
	
	/**
	 * @param lowerThreshold the lowerThreshold to set
	 */
	public void setLowerThreshold(Threshold lowerThreshold) {
		this.lowerThreshold = lowerThreshold;
	}
	
	/**
	 * @return the upperThreshols
	 */
	public Threshold getUpperThreshols() {
		return upperThreshols;
	}
	
	/**
	 * @param upperThreshols the upperThreshols to set
	 */
	public void setUpperThreshols(Threshold upperThreshols) {
		this.upperThreshols = upperThreshols;
	}

}
