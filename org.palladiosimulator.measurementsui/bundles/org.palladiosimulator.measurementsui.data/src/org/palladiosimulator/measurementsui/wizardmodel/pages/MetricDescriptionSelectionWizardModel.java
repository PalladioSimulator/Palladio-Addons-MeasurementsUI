package org.palladiosimulator.measurementsui.wizardmodel.pages;

import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.UnselectedMetricSpecificationsProvider;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
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
	private boolean isEditing;

	public MetricDescriptionSelectionWizardModel(Monitor monitor, boolean isEditing) {
		this.provider = new UnselectedMetricSpecificationsProvider();
		this.usedMetricsMonitor = monitor;
		this.isEditing = isEditing;
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

	public void addMeasurementSpecification(MeasurementSpecification selectedMeasurementSpecification) {
		provider.moveMeasurementSpecificationsBetweenMonitors(selectedMeasurementSpecification, usedMetricsMonitor,
				isEditing);
	}

	public void removeMeasurementSpecification(MeasurementSpecification selectedMeasurementSpecification) {
		provider.removeMeasurementSpecificationBetweenMonitors(selectedMeasurementSpecification, unusedMetricsMonitor,
				isEditing);
	}

	public void addAllMetricDescriptions() {
		provider.moveAllMeasurementSpecificationsBetweenMonitors(unusedMetricsMonitor, usedMetricsMonitor, isEditing);
	}

	public void removeAllMetricDescriptions() {
		provider.moveAllMeasurementSpecificationsBetweenMonitors(usedMetricsMonitor, unusedMetricsMonitor, isEditing);
	}

	public void switchTriggerSelfAdapting(MeasurementSpecification mspec) {
	    if(isEditing) {
	        ResourceEditorImpl.getInstance().changeTriggersSelfAdapting(mspec);
	    }else {
	        mspec.setTriggersSelfAdaptations(!mspec.isTriggersSelfAdaptations());
	    }
	}
	
	public void addSuggestions() {
		// TODO: Not implemented yet.
	}

	private boolean metricListIsEmpty() {
		return usedMetricsMonitor.getMeasurementSpecifications().isEmpty();
	}

	@Override
	public String getTitleText() {
		return METRIC_SELECTION_TITEL;

	}

}