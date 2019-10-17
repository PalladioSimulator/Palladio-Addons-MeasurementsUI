package org.palladiosimulator.measurementsui.wizard.main;



import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.palladiosimulator.measurementsui.wizard.pages.ServiceLevelObjectiveCreationPage;
import org.palladiosimulator.measurementsui.wizard.pages.ServiceLevelObjectiveMeasurementSelectionPage;
import org.palladiosimulator.measurementsui.wizard.pages.ServiceLevelObjectiveThresholdPage;
import org.palladiosimulator.measurementsui.wizardmodel.SloWizardModelManager;
import org.palladiosimulator.measurementsui.wizardmodel.SloWizardModelType;
import org.palladiosimulator.measurementsui.wizardmodel.pages.SloCreationWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.SloMeasurementSpecSelectionWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.SloThresholdWizardModel;
import org.palladiosimulator.servicelevelobjective.ServiceLevelObjective;


/**
 * This is the wizard for the service level objective creation.
 * 
 * @author Jan Hofmann 
 * @author Manuel Marroquin
 *
 */
public class ServiceLevelObjectiveWizard extends org.eclipse.jface.wizard.Wizard {

    private ServiceLevelObjectiveCreationPage sloCreationWizardPage;
    private ServiceLevelObjectiveMeasurementSelectionPage sloMspSelectionWizardPage;
    private ServiceLevelObjectiveThresholdPage sloThresholdWizardPage;
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
        
    private SloWizardModelManager wizardManager;
    
    private static final String WINDOW_TITLE = "Create a new Service Level Objective";

    /**
     * returns the first wizard page of the service level objective wizard
     * 
     * @return first wizardpage
     */
    public ServiceLevelObjectiveCreationPage getPage1() {
    	 return sloCreationWizardPage;
    }

    /**
     * sets the first wizard page of service level objective wizard
     * 
     * @param page1
     *            first wizard page
     */
    public void setPage1(ServiceLevelObjectiveCreationPage page1) {
        this.sloCreationWizardPage = page1;
    }

    /**
     * returns the second wizard page of the standard set wizard
     * 
     * @return second wizardpage
     */
    public ServiceLevelObjectiveMeasurementSelectionPage getPage2() {
        return sloMspSelectionWizardPage;
    }

    /**
     * sets the second wizard page of the standard set wizard
     * 
     * @param page2
     *            second wizard page
     */
    public void setPage2(ServiceLevelObjectiveMeasurementSelectionPage page2) {
        this.sloMspSelectionWizardPage = page2;
    }
    /**
     * returns the third wizard page of the standard set wizard
     * 
     * @return third wizardpage
     */
    public ServiceLevelObjectiveThresholdPage getPage3() {
        return sloThresholdWizardPage;
    }

    /**
     * sets the third wizard page of the standard set wizard
     * 
     * @param page3
     *            third wizard page
     */
    public void setPage3(ServiceLevelObjectiveThresholdPage page3) {
        this.sloThresholdWizardPage = page3;
    }

    /**
     * constructor for the wizard
     * @param createNewSlo 
     * @param selection 
     */
    public ServiceLevelObjectiveWizard(boolean createNewSlo, ServiceLevelObjective slo) {
        wizardManager = new SloWizardModelManager(createNewSlo, slo);
        setWindowTitle(WINDOW_TITLE);
        
        sloCreationWizardPage = new ServiceLevelObjectiveCreationPage((SloCreationWizardModel) wizardManager.getWizardModel(SloWizardModelType.SLO_CREATION));
        sloMspSelectionWizardPage = new ServiceLevelObjectiveMeasurementSelectionPage((SloMeasurementSpecSelectionWizardModel) wizardManager.getWizardModel(SloWizardModelType.SLO_MEASUREMENT_SPEC));
        sloThresholdWizardPage = new ServiceLevelObjectiveThresholdPage((SloThresholdWizardModel) wizardManager.getWizardModel(SloWizardModelType.SLO_THRESHOLDING));
    }
    /**
     * Adds PropertyChangeListener to the propertyChangeSupport
     * @param listener the PropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        wizardManager.addPropertyChangeListener(listener);
    }

    @Override
    public void addPages() {
        addPage(sloCreationWizardPage);
        addPage(sloMspSelectionWizardPage);
        addPage(sloThresholdWizardPage);
    }
  
	@Override
	public boolean performFinish() {
		sloThresholdWizardPage.finishTresholds();
		wizardManager.finish();
		changes.firePropertyChange("save", 1, 2);
		return true;
	}

    @Override
    public boolean canFinish() {
//   	 // Prevents user from clicking finish if measurement specification was changed
//   	 // which is not valid with current threshold settings
        return wizardManager.canFinish() && getContainer().getCurrentPage() != sloMspSelectionWizardPage;
    } 
}
