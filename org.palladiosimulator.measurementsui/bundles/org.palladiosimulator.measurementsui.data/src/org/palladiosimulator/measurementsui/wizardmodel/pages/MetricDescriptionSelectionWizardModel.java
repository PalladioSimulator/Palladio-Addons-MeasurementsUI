package org.palladiosimulator.measurementsui.wizardmodel.pages;

import org.palladiosimulator.measurementsui.dataprovider.UnselectedMetricSpecificationsProvider;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * 
 * @author David Schuetz
 *
 */
public class MetricDescriptionSelectionWizardModel implements WizardModel {
	private static final String STANDARD_INFORMATION_MESSAGE = "Please select all Metrics which should be measured.";
	private static final String NO_METRIC_SELECTED_MEASSAGE = "There is currently no Metric selected. "
			+ "In order to get Simulation results you have select at least one Metric.";
	
	private static final String METRIC_SELECTION_TITEL = "Select Metrics";
	
	private Monitor usedMetricsMonitor;
	private Monitor unusedMetricsMonitor;
	private UnselectedMetricSpecificationsProvider provider;

	public MetricDescriptionSelectionWizardModel(Monitor monitor) {
		this.provider = new UnselectedMetricSpecificationsProvider();
		this.usedMetricsMonitor = monitor;
		this.unusedMetricsMonitor = provider.createMonitorWithMissingMetricDescriptions(usedMetricsMonitor);
	}

	@Override
	public boolean canFinish() {
		return !metricListIsEmpty();
	}

	@Override
	public String getInfoText() {
		if (metricListIsEmpty()) {
			return NO_METRIC_SELECTED_MEASSAGE;
		}
		return STANDARD_INFORMATION_MESSAGE;
	}

	@Override
	public void nextStep() {
		// TODO Auto-generated method stub
	}
	
	public Monitor getUnusedMetricsMonitor() {
		return unusedMetricsMonitor;
	}
	
	public Monitor getUsedMetricsMonitor() {
		return usedMetricsMonitor;
	}
	
	public void addMetricDescription(MetricDescription selectedMetricDescription) {
		provider.moveMetricSpecificationBetweenMonitors(selectedMetricDescription, unusedMetricsMonitor, usedMetricsMonitor);
	}
	
	public void removeMetricDescription(MetricDescription selectedMetricDescription) {
		provider.moveMetricSpecificationBetweenMonitors(selectedMetricDescription, usedMetricsMonitor, unusedMetricsMonitor);
	}
	
	public void addAllMetricDescriptions() {
		provider.moveAllMetricSpecificationsBetweenMonitors(unusedMetricsMonitor, usedMetricsMonitor);
	}
	
	public void removeAllMetricDescriptions() {
		provider.moveAllMetricSpecificationsBetweenMonitors(usedMetricsMonitor, unusedMetricsMonitor);
	}
	
	public void addSuggestions() {
		//TODO: Not implemented yet.
	}
	
	private boolean metricListIsEmpty() {
		return usedMetricsMonitor.getMeasurementSpecifications().isEmpty();
	}

	@Override
	public String getTitleText() {
		return METRIC_SELECTION_TITEL;
		
	}

}