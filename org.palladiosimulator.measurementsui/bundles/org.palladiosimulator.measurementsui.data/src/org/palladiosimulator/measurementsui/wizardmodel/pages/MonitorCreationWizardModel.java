package org.palladiosimulator.measurementsui.wizardmodel.pages;

import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.monitorrepository.Monitor;

public class MonitorCreationWizardModel implements WizardModel{
	
	private static final String createMonitorInfoText = "A Monitor specifies which element of your models should be analyzed during a simulation run.\nIn this page "
			+ "you can give your Monitor an appropiate name and set it activated/not activated.\n Activated Monitors will be simulated during a SimuLizar run, not activated ones will be ignored. ";
	private static final String editMonitorInfoText = "Edit your Monitor name and set it activated/not activated.";
	
	private static final String createMonitorTitle = "Create Monitor";
	private static final String editMonitorTitle = "Edit Monitor";
	
	private Monitor monitor;
	
	 public MonitorCreationWizardModel(Monitor monitor) {
		 this.monitor = monitor;
		
	}
	 
	 public Monitor getMonitor() {
		 return monitor;
	 }

	@Override
	public boolean canFinish() {
		if(this.monitor.getMonitorRepository() != null && this.monitor.getMeasuringPoint() != null) {
			return true;
		}
		return false;
	}

	@Override
	public String getInfoText() {
		if(this.monitor.getMonitorRepository() != null) {
			return editMonitorInfoText;
		}
		
		return createMonitorInfoText;
	}

	@Override
	public boolean nextStep() {
		return true;
	}

	@Override
	public String getTitleText() {
		if(this.monitor.getMonitorRepository() != null) {
			return editMonitorTitle;
		}
		
		return createMonitorTitle;
		
	}

}
