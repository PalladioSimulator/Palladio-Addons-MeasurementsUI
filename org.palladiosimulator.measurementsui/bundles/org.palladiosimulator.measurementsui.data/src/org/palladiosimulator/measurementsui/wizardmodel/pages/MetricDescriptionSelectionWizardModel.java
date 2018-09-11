package org.palladiosimulator.measurementsui.wizardmodel.pages;

import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * 
 * @author David Schuetz
 *
 */
public class MetricDescriptionSelectionWizardModel implements WizardModel {
	private Monitor monitor;
	private static final String STANDARD_INFORMATION_MESSAGE = "Please select all Metrics which should be measured.";
	private static final String NO_METRIC_SELECTED_MEASSAGE = "There is currently no Metric selected. "
			+ "In order to get Simulation results you have select at least one Metric.";

	public MetricDescriptionSelectionWizardModel(Monitor monitor) {
		this.monitor = monitor;
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
	
	public Monitor get
	
	private boolean metricListIsEmpty() {
		return monitor.getMeasurementSpecifications().isEmpty();
	}

}
