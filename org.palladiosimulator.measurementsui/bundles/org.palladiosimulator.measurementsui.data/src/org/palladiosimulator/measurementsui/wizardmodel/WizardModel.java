package org.palladiosimulator.measurementsui.wizardmodel;

/**
 * 
 * @author David Schuetz
 *
 */
public interface WizardModel {
	
    /**
     * @return true if all necessary attributes are set in order to finish
     */
	public boolean canFinish();
	
	/**
	 * @return an info text based on the changes made in the model.
	 */
	public String getInfoText();

	/**
	 * 
	 * @return true if all necessary attributes in these model are set and the user can click next
	 */
	public boolean nextStep();
	
	/**
	 * @return the title of the wizard page
	 */
	public String getTitleText();
}
