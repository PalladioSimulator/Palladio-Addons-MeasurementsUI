package org.palladiosimulator.measurementsui.wizardmodel.pages;

import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * This class provides provides all necessary data for the first
 * Wizard page, to create or Edit a Monitor. 
 * @author Lasse
 *
 */
public class MonitorCreationWizardModel implements WizardModel {
	
	private static final String CREATE_MONITOR_INFO_TEXT = "A Monitor specifies which element of your "
	        + "Models should be analyzed during a simulation run."
	        + "\n In this page you can give your Monitor an appropiate name and set it activated/not activated."
	        + "\n Activated Monitors will be simulated during a SimuLizar run, not activated ones will be ignored. ";
	private static final String EDIT_MONITOR_INFO_TEXT = "Edit your Monitor name and set him activated/not activated.";
	
	private static final String CREATE_MONITOR_TITEL = "Create Monitor";
	private static final String EDIT_MONITOR_TITEL = "Edit Monitor";
	
	private Monitor monitor;
	
	/**
	 * Constructor
	 * @param monitor to edit
	 */
	 public MonitorCreationWizardModel(Monitor monitor) {
		 this.monitor = monitor;
		
	}
	 
	 /**
	  * Get monitor
	  * @return monitor
	  */
	 public Monitor getMonitor() {
		 return monitor;
	 }

	@Override
	public boolean canFinish() {
		return (this.monitor.getMonitorRepository() != null && this.monitor.getMeasuringPoint() != null);
		
	}

	@Override
	public String getInfoText() {
		if (this.monitor.getMonitorRepository() != null) {
			return EDIT_MONITOR_INFO_TEXT;
		}
		
		return CREATE_MONITOR_INFO_TEXT;
	}

	@Override
	public void nextStep() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitleText() {
		if (this.monitor.getMonitorRepository() != null) {
			return EDIT_MONITOR_TITEL;
		}
		
		return CREATE_MONITOR_TITEL;
		
	}

}
