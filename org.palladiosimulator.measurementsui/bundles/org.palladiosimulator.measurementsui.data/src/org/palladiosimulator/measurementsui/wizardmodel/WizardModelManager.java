package org.palladiosimulator.measurementsui.wizardmodel;

import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MonitorCreationWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;

/**
 * 
 * @author David Schuetz
 *
 */
public class WizardModelManager {
	private Monitor monitor;
	
	public WizardModelManager() {
		monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
	}
	
	public WizardModelManager(Monitor monitor) {
		this.monitor = monitor;
	}
	
	public void cancel() {
		
	}
	
	public void finish() {
		
	}
	
	public WizardModel getWizardModel(WizardModelType wizardModel) {
		switch (wizardModel) {
			case MONITOR_CREATION:
				return new MonitorCreationWizardModel();
			case MEASURING_POINT_SELECTION:
				return new MeasuringPointSelectionWizardModel();
			case METRIC_DESCRIPTION_SELECTION:
				return new MetricDescriptionSelectionWizardModel(monitor);
			case PROCESSING_TYPE:
				return new ProcessingTypeSelectionWizardModel();
			default:
				return null;
		}
	}
}
