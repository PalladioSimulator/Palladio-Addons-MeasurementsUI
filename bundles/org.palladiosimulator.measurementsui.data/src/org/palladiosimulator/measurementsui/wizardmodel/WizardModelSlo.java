package org.palladiosimulator.measurementsui.wizardmodel;

/**
 * @author Jan Hofmann
 *
 */
public interface WizardModelSlo {
	
    /**
     * Checks if WizardPage contains all necessary attributes.
     * 
     * @return true if all necessary attributes are set in order to finish
     */
	public boolean canFinish();
	
	/**
	 * Returns info text displayed in the WizardPage
	 * 
	 * @return an info text based on the changes made in the model.
	 */
	public String getInfoText();

	
	/**
	 * Returns title text displayed in the WizardPage
	 * 
	 * @return the title of the wizard page
	 */
	public String getTitleText();

}
