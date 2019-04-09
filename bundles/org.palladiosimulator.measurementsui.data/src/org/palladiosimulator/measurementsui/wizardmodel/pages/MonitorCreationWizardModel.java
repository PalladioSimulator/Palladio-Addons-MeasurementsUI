package org.palladiosimulator.measurementsui.wizardmodel.pages;

import org.palladiosimulator.measurementsui.dataprovider.MonitorCreationProvider;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * This class provides provides all necessary data for the first
 * Wizard page, to create or Edit a Monitor. 
 * @author Lasse Merz
 *
 */
public class MonitorCreationWizardModel implements WizardModel {
	
	private static final String CREATE_MONITOR_INFO_TEXT = "A Monitor is used for specifying which metrics"
			+ " should be measured. It corresponds to a Measuring Point."
	        + "\nActivated Monitors will be simulated during a SimuLizar run, not activated ones will be ignored. ";
	private static final String EDIT_MONITOR_INFO_TEXT = CREATE_MONITOR_INFO_TEXT 
	        + "\nEdit your Monitor name and set it activated/not activated.";
	
	private static final String CREATE_MONITOR_TITEL = "Create Monitor";
	private static final String EDIT_MONITOR_TITEL = "Edit Monitor";
	
	private Monitor monitor;
	private boolean isEditing;
	private MonitorCreationProvider provider;
	
	/**
	 * Constructor
	 * @param monitor to edit
	 * @param isEditing indicates whether we are in edit mode or creation mode
	 */
	 public MonitorCreationWizardModel(Monitor monitor, boolean isEditing) {
		 this.monitor = monitor;
		 this.isEditing = isEditing;
		 this.provider = new MonitorCreationProvider();
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
		return !monitor.getEntityName().isEmpty();
	}

	@Override
	public String getInfoText() {
		if (this.monitor.getMonitorRepository() != null) {
			return EDIT_MONITOR_INFO_TEXT;
		}
		
		return CREATE_MONITOR_INFO_TEXT;
	}

	@Override
	public String getTitleText() {
		if (this.monitor.getMonitorRepository() != null) {
			return EDIT_MONITOR_TITEL;
		}
		
		return CREATE_MONITOR_TITEL;
	}

	/**
	 * Sets the name of the monitor.
	 * @param name the new given name of the monitor
	 */
	public void setMonitorName(String name) {
	    if (!this.isEditing) {
    	    this.monitor.setEntityName(name);
	    } else {    	    
    	    provider.setMonitorName(this.monitor, name);
	    }
	}

	/**
	 * Sets the activated attribute of the monitor.
	 * @param activatedOrNot the given boolean to be set for the activated attribute
	 */
	public void setMonitorActivated(boolean activatedOrNot) {
	    if (!this.isEditing) {
	        this.monitor.setActivated(activatedOrNot);
	    } else {
	        provider.setMonitorActivatedValue(this.monitor, !activatedOrNot);
	    }
	}
}
