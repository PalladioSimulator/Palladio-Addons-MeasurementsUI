package org.palladiosimulator.measurementsui.wizardmodel;

/**
 * 
 * @author David Schuetz
 *
 */
public interface WizardModel {
	
	public boolean canFinish();
	
	public String getInfoText();
	
	public void nextStep();
}
