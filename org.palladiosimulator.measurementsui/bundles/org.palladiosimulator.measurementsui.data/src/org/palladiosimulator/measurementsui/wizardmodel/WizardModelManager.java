package org.palladiosimulator.measurementsui.wizardmodel;

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
	
	public WizardModel getWizardModel(WizardModelType wizardModelType) {
		return null;
	}
}
