package org.palladiosimulator.measurementsui.wizardmodel.pages;

import org.palladiosimulator.measurementsui.dataprovider.SloProvider;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModelSlo;
import org.palladiosimulator.servicelevelobjective.ServiceLevelObjective;

/**
 * WizardModel for the creating and editing a Service Level Objective. 
 * Model for the first SloWizardPage.
 * 
 * @author Manuel Marroquin
 * @author Jan Hofmann
 *
 */
public class SloCreationWizardModel implements WizardModelSlo {
	
	private static final String CREATE_SLO_INFO_TEXT = "A Service Level Objective is used for specifying"
			+ " the goals the measurements should achieve."
			+ "\n\nYou can set a name and a description for your Service Level Objective here.";
	private static final String CREATE_SLO_TITEL = "Create Service Level Objective";
	private static final String EDIT_SLO_TITEL = "Edit Service Level Objective";

    private SloProvider sloProvider = SloProvider.getInstance();
	private boolean isEditing;
	
    /**
	 * Constructor
	 * @param slo the service level objective to edit
	 * @param isEditing indicates whether we are in edit mode or creation mode
	 */ 
    public SloCreationWizardModel(SloProvider dataprovider, boolean isEditing) {
    	this.isEditing = isEditing;
    	this.sloProvider = dataprovider;
    }
    
    /**
	 * Gets the Service Level Objective from the SLO-Provider.
	 * Is used by the wizard pages
	 * @return the service level objective
	 */
    public ServiceLevelObjective getSlo() {
		 return sloProvider.getServiceLevelObjective();
	 }
    
    /**
	 * Sets the Service Level Objective in the SLO-Provider.
	 * Is used by the wizard pages
	 */
    public void setServiceLevelObjective(ServiceLevelObjective slo) {
    	sloProvider.setServiceLevelObjective(slo);
    }
    
    /**
	 * Sets the name of the slo.
	 * @param name the new name of the slo
	 */
	public void setName(String name) {
		sloProvider.setName(name);
	}
	
    /**
	 * Sets the description of the slo.
	 * @param description the new description of the slo
	 */
	public void setDescription(String description) {
		sloProvider.setDescription(description);
	}   

    /**
	 * Returns true if this wizard page contains all necessary information
	 */
	@Override
	public boolean canFinish() {
		boolean canFinish;
		try {
			canFinish = !this.sloProvider.getName().isEmpty();
		} catch (Exception e) {
			canFinish = false;
		}
		return canFinish;
	}

	public String getName() {
		return sloProvider.getName();
	}

	public String getDescription() {
		return sloProvider.getDescription();
	}

	@Override
	public String getInfoText() {
		return CREATE_SLO_INFO_TEXT;
	}

	@Override
	public String getTitleText() {
		if (isEditing) {
			return EDIT_SLO_TITEL;
		}
		return CREATE_SLO_TITEL;
	}
}
