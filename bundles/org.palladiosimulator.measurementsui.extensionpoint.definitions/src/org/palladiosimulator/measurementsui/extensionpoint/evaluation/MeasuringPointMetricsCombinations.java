package org.palladiosimulator.measurementsui.extensionpoint.evaluation;

import java.util.HashMap;
import java.util.Map;

import org.palladiosimulator.metricspec.MetricDescription;


/**
 * This class is used to manage the working combinations of Measuring Points and
 * Metric Descriptions. There is a HashMap for each of the 11 MeasuringPoints
 * where the keys are all Metric Descriptions that work and the value is a
 * boolean whether they should be suggested or not.
 * @author Lasse Merz
 *
 */
public class MeasuringPointMetricsCombinations {
	
	private Map<MetricDescription, Boolean> usageScenarioMeasuringPointMetrics;
	private Map<MetricDescription, Boolean> activeResourceMeasuringPointMetrics;
	private Map<MetricDescription, Boolean> assemblyOperationMeasuringPointMetrics;
	private Map<MetricDescription, Boolean> assemblyPassiveResourceMeasuringPointMetrics;
	private Map<MetricDescription, Boolean> entryLevelSystemCallMeasuringPointMetrics;
	private Map<MetricDescription, Boolean> externalCallActionMeasuringPointMetrics;
	private Map<MetricDescription, Boolean> linkingResourceMeasuringPointMetrics;
	private Map<MetricDescription, Boolean> resourceContainerMeasuringPointMetrics;
	private Map<MetricDescription, Boolean> resourceEnvironmentMeasuringPointMetrics;
	private Map<MetricDescription, Boolean> subSystemOperationMeasuringPointMetrics;
	private Map<MetricDescription, Boolean> systemOperationMeasuringPointMetrics;

	/**
	 * Constructor which initializes a HasMap for each of the
	 * 11 Measuring Points
	 */
	public MeasuringPointMetricsCombinations() {
		this.usageScenarioMeasuringPointMetrics = new HashMap<>();
		this.activeResourceMeasuringPointMetrics = new HashMap<>();
		this.assemblyOperationMeasuringPointMetrics = new HashMap<>();
		this.assemblyPassiveResourceMeasuringPointMetrics = new HashMap<>();
		this.entryLevelSystemCallMeasuringPointMetrics = new HashMap<>();
		this.externalCallActionMeasuringPointMetrics = new HashMap<>();
		this.linkingResourceMeasuringPointMetrics = new HashMap<>();
		this.resourceContainerMeasuringPointMetrics = new HashMap<>();
		this.resourceEnvironmentMeasuringPointMetrics = new HashMap<>();
		this.subSystemOperationMeasuringPointMetrics = new HashMap<>();
		this.systemOperationMeasuringPointMetrics = new HashMap<>();
		
	}
	
	/**
	 * Puts a new entry in the given MeasuringPoint map depending on the MetricDescription and the 
	 * boolean suggestedMetric
	 * 
	 * @param measuringPointMap to add the entry to
	 * @param metricDescription to add to the map
	 * @param suggestedMetric to add to the map
	 */
	public void addMetricDescriptionToMap(Map<MetricDescription, Boolean> measuringPointMap, MetricDescription metricDescription, boolean suggestedMetric) {
	    measuringPointMap.put(metricDescription, suggestedMetric);
	}
	
	/**
	 * Returns the map of working MetricDescirptions of the UsageScenarioMeasuringPoint
	 * @return map of working MetricDescriptions of the UsageScenarioMeasuringPoint
	 */
	public Map<MetricDescription, Boolean> getUsageScenarioMeasuringPointMetrics() {
		return usageScenarioMeasuringPointMetrics;
	}

	/**
     * Returns the map of working MetricDescirptions of the ActiveResourceMeasuringPoint
     * @return map of working MetricDescriptions of the ActiveResourceMeasuringPoint
     */
	public Map<MetricDescription, Boolean> getActiveResourceMeasuringPointMetrics() {
		return activeResourceMeasuringPointMetrics;
	}

	/**
     * Returns the map of working MetricDescirptions of the AssemblyOperationMeasuringPoint
     * @return map of working MetricDescriptions of the AssemblyOperationMeasuringPoint
     */
	public Map<MetricDescription, Boolean> getAssemblyOperationMeasuringPointMetrics() {
		return assemblyOperationMeasuringPointMetrics;
	}

	/**
     * Returns the map of working MetricDescirptions of the AssemblyPassiveResourceMeasuringPoint
     * @return map of working MetricDescriptions of the AssemblyPassiveResourceMeasuringPoint
     */
	public Map<MetricDescription, Boolean> getAssemblyPassiveResourceMeasuringPointMetrics() {
		return assemblyPassiveResourceMeasuringPointMetrics;
	}

	/**
     * Returns the map of working MetricDescirptions of the EntryLevelSystemCallMeasuringPoint
     * @return map of working MetricDescriptions of the EntryLevelSystemCallMeasuringPoint
     */
	public Map<MetricDescription, Boolean> getEntryLevelSystemCallMeasuringPointMetrics() {
		return entryLevelSystemCallMeasuringPointMetrics;
	}

	/**
     * Returns the map of working MetricDescirptions of the ExternalCallActionMeasuringPoint
     * @return map of working MetricDescriptions of the ExternalCallActionMeasuringPoint
     */
	public Map<MetricDescription, Boolean> getExternalCallActionMeasuringPointMetrics() {
		return externalCallActionMeasuringPointMetrics;
	}

	/**
     * Returns the map of working MetricDescirptions of the LinkingResourceMeasuringPoint
     * @return map of working MetricDescriptions of the LinkingResourceMeasuringPoint
     */
	public Map<MetricDescription, Boolean> getLinkingResourceMeasuringPointMetrics() {
		return linkingResourceMeasuringPointMetrics;
	}

	/**
     * Returns the map of working MetricDescirptions of the ResourceContainerMeasuringPoint
     * @return map of working MetricDescriptions of the ResourceContainerMeasuringPoint
     */
	public Map<MetricDescription, Boolean> getResourceContainerMeasuringPointMetrics() {
		return resourceContainerMeasuringPointMetrics;
	}

	/**
     * Returns the map of working MetricDescirptions of the ResourceEnvironmentMeasuringPoint
     * @return map of working MetricDescriptions of the ResourceEnvironmentMeasuringPoint
     */
	public Map<MetricDescription, Boolean> getResourceEnvironmentMeasuringPointMetrics() {
		return resourceEnvironmentMeasuringPointMetrics;
	}

	/**
     * Returns the map of working MetricDescirptions of the SubSystemOperationMeasuringPoint
     * @return map of working MetricDescriptions of the SubSystemOperationMeasuringPoint
     */
	public Map<MetricDescription, Boolean> getSubSystemOperationMeasuringPointMetrics() {
		return subSystemOperationMeasuringPointMetrics;
	}

	/**
     * Returns the map of working MetricDescirptions of the SystemOperationMeasuringPoint
     * @return map of working MetricDescriptions of the SystemOperationMeasuringPoint
     */
	public Map<MetricDescription, Boolean> getSystemOperationMeasuringPointMetrics() {
		return systemOperationMeasuringPointMetrics;
	}
	
	
}
