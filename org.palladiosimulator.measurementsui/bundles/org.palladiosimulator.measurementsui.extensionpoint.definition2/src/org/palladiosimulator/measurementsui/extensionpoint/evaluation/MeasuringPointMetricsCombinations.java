package org.palladiosimulator.measurementsui.extensionpoint.evaluation;

import java.util.HashMap;
import java.util.Map;

import org.palladiosimulator.measurementsui.extensionpoint.definition.IPossibleMetricDescription;
import org.palladiosimulator.metricspec.MetricDescription;

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
	
	public void addUsageScenarioMeasuringPointMetric(IPossibleMetricDescription possibleMetricDescription) {
		this.usageScenarioMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	
	public void addActiveResourceMeasuringPointMetric(IPossibleMetricDescription possibleMetricDescription) {
		this.usageScenarioMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	
	public void addAssemblyOperationMeasuringPointMetric(IPossibleMetricDescription possibleMetricDescription) {
		this.usageScenarioMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	
	public void addAssemblyPassiveResourceMeasuringPointMetric(IPossibleMetricDescription possibleMetricDescription) {
		this.usageScenarioMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	
	public void addEntryLevelSystemCallMeasuringPointMetric(IPossibleMetricDescription possibleMetricDescription) {
		this.usageScenarioMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	
	public void addExternalCallActionMeasuringPointMetric(IPossibleMetricDescription possibleMetricDescription) {
		this.usageScenarioMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	
	public void addLinkingResourceMeasuringPointMetric(IPossibleMetricDescription possibleMetricDescription) {
		this.usageScenarioMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	
	public void addResourceContainerMeasuringPointMetric(IPossibleMetricDescription possibleMetricDescription) {
		this.usageScenarioMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	
	public void addResourceEnvironmentMeasuringPointMetric(IPossibleMetricDescription possibleMetricDescription) {
		this.usageScenarioMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	
	public void addSubSystemOperationMeasuringPointMetric(IPossibleMetricDescription possibleMetricDescription) {
		this.usageScenarioMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	
	public void addSystemOperationMeasuringPointMetric(IPossibleMetricDescription possibleMetricDescription) {
		this.usageScenarioMeasuringPointMetrics.put(possibleMetricDescription.getMetricDescription(),
				possibleMetricDescription.addtoSuggestedList());
	}
	
	public Map<MetricDescription, Boolean> getUsageScenarioMeasuringPointMetrics() {
		return usageScenarioMeasuringPointMetrics;
	}


	public Map<MetricDescription, Boolean> getActiveResourceMeasuringPointMetrics() {
		return activeResourceMeasuringPointMetrics;
	}


	public Map<MetricDescription, Boolean> getAssemblyOperationMeasuringPointMetrics() {
		return assemblyOperationMeasuringPointMetrics;
	}


	public Map<MetricDescription, Boolean> getAssemblyPassiveResourceMeasuringPointMetrics() {
		return assemblyPassiveResourceMeasuringPointMetrics;
	}


	public Map<MetricDescription, Boolean> getEntryLevelSystemCallMeasuringPointMetrics() {
		return entryLevelSystemCallMeasuringPointMetrics;
	}


	public Map<MetricDescription, Boolean> getExternalCallActionMeasuringPointMetrics() {
		return externalCallActionMeasuringPointMetrics;
	}


	public Map<MetricDescription, Boolean> getLinkingResourceMeasuringPointMetrics() {
		return linkingResourceMeasuringPointMetrics;
	}


	public Map<MetricDescription, Boolean> getResourceContainerMeasuringPointMetrics() {
		return resourceContainerMeasuringPointMetrics;
	}


	public Map<MetricDescription, Boolean> getResourceEnvironmentMeasuringPointMetrics() {
		return resourceEnvironmentMeasuringPointMetrics;
	}


	public Map<MetricDescription, Boolean> getSubSystemOperationMeasuringPointMetrics() {
		return subSystemOperationMeasuringPointMetrics;
	}


	public Map<MetricDescription, Boolean> getSystemOperationMeasuringPointMetrics() {
		return systemOperationMeasuringPointMetrics;
	}
	
	
}
