package org.palladiosimulator.measurementsui.wizardmodel;

public interface WizardModel {
	
	public boolean canFinish();
	
	public String getInfoText();
	
	public void nextStep();
}
