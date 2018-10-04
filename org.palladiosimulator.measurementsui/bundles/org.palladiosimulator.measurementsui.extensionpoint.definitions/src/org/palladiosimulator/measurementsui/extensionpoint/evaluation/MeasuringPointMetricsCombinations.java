package org.palladiosimulator.measurementsui.extensionpoint.evaluation;

import java.util.HashMap;
import java.util.Map;

import org.palladiosimulator.measurementsui.extensionpoint.definition.IMeasuringPointMetricsWorkingCombinations;
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
	 * Adds a working MetricDescription to the UsageScenarioMeasuringPoint
	 * @param possibleMetricDescription
	 */
	public void addUsageScenarioMeasuringPointMetric(IMeasuringPointMetricsWorkingCombinations possibleMetricDescription) {
		this.usageScenarioMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	/**
	 * Adds a working MetricDescription to the ResourceMeasuringPoint
	 * @param possibleMetricDescription
	 */
	public void addActiveResourceMeasuringPointMetric(IMeasuringPointMetricsWorkingCombinations possibleMetricDescription) {
		this.activeResourceMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	/**
	 * Adds a working MetricDescription to the AssemblyOperationMeasuringPoint
	 * @param possibleMetricDescription
	 */
	public void addAssemblyOperationMeasuringPointMetric(IMeasuringPointMetricsWorkingCombinations possibleMetricDescription) {
		this.assemblyOperationMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	/**
	 * Adds a working MetricDescription to the AssemblyPassiveResourceMeasuringPoint
	 * @param possibleMetricDescription
	 */
	public void addAssemblyPassiveResourceMeasuringPointMetric(IMeasuringPointMetricsWorkingCombinations possibleMetricDescription) {
		this.assemblyPassiveResourceMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	/**
	 * Adds a working MetricDescription to the EntryLevelSystemCallMeasuringPoint
	 * @param possibleMetricDescription
	 */
	public void addEntryLevelSystemCallMeasuringPointMetric(IMeasuringPointMetricsWorkingCombinations possibleMetricDescription) {
		this.entryLevelSystemCallMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	/**
	 * Adds a working MetricDescription to the ExternalCallActionMeasuringPoint
	 * @param possibleMetricDescription
	 */
	public void addExternalCallActionMeasuringPointMetric(IMeasuringPointMetricsWorkingCombinations possibleMetricDescription) {
		this.externalCallActionMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	/**
	 * Adds a working MetricDescription to the LinkingResourceMeasuringPoint
	 * @param possibleMetricDescription
	 */
	public void addLinkingResourceMeasuringPointMetric(IMeasuringPointMetricsWorkingCombinations possibleMetricDescription) {
		this.linkingResourceMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	/**
	 * Adds a working MetricDescription to the ResourceContainerMeasuringPoint
	 * @param possibleMetricDescription
	 */
	public void addResourceContainerMeasuringPointMetric(IMeasuringPointMetricsWorkingCombinations possibleMetricDescription) {
		this.resourceContainerMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	/**
	 * Adds a working MetricDescription to the ResourceEnvironmentMeasuringPoint
	 * @param possibleMetricDescription
	 */
	public void addResourceEnvironmentMeasuringPointMetric(IMeasuringPointMetricsWorkingCombinations possibleMetricDescription) {
		this.resourceEnvironmentMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	/**
	 * Adds a working MetricDescription to the SubSystemOperationMeasuringPoint
	 * @param possibleMetricDescription
	 */
	public void addSubSystemOperationMeasuringPointMetric(IMeasuringPointMetricsWorkingCombinations possibleMetricDescription) {
		this.subSystemOperationMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	/**
	 * Adds a working MetricDescription to the SystemOperationMeasuringPoint
	 * @param possibleMetricDescription
	 */
	public void addSystemOperationMeasuringPointMetric(IMeasuringPointMetricsWorkingCombinations possibleMetricDescription) {
		this.systemOperationMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
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
