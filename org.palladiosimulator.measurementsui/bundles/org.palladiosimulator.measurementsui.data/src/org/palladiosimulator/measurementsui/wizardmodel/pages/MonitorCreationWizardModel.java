package org.palladiosimulator.measurementsui.wizardmodel.pages;

import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * This class provides provides all necessary data for the first
 * Wizard page, to create or Edit a Monitor. 
 * @author Lasse Merz
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
	private boolean isEditing;
	
	/**
	 * Constructor
	 * @param monitor to edit
	 * @param isEditing indicates whether we are in edit mode or creation mode
	 */
	 public MonitorCreationWizardModel(Monitor monitor, boolean isEditing) {
		 this.monitor = monitor;
		 this.isEditing = isEditing;
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
	public boolean nextStep() {
		return true;
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
    	    ResourceEditorImpl editor = ResourceEditorImpl.getInstance();
    	    editor.setResourceName(this.monitor, name);
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
	        ResourceEditorImpl editor = ResourceEditorImpl.getInstance();
	        editor.changeMonitorActive(this.monitor, !activatedOrNot);
	    }
	}
}
