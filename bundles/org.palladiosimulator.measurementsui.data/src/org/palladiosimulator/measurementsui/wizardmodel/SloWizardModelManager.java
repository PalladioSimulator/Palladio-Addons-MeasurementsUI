package org.palladiosimulator.measurementsui.wizardmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EnumMap;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.datamanipulation.SloEditor;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.dataprovider.SloProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.SloCreationWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.SloMeasurementSpecSelectionWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.SloThresholdWizardModel;
import org.palladiosimulator.servicelevelobjective.ServiceLevelObjective;

/**
 * This class manages the additional Service Level Objective WizardModels used in
 * the SloWizard. Handles finishing the Wizard and saving the created or edited Slo's 
 * 
 * @author Jan Hofmann
 * @author Manu
 *
 */
public class SloWizardModelManager {
	private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private SloProvider sloProvider = SloProvider.getInstance();

	private SloEditor sloEditor = new SloEditor();
	private ResourceEditorImpl editor;
	private DataApplication dataApp;
	private boolean isEditing;
	
	private WizardModelSlo newWizardModel;
	private EnumMap<SloWizardModelType, WizardModelSlo> wizardModels = new EnumMap<>(SloWizardModelType.class);

	
	/**
	 * Constructor
	 * 
     * Creates a new empty SLO which will be edited in the wizard pages
	 * @param createNewSlo 
     */
    public SloWizardModelManager(boolean isNewSlo, ServiceLevelObjective slo) {
    	this.isEditing = !isNewSlo;
    	if (isNewSlo) {
           ServiceLevelObjective serviceLevelObjective = sloEditor.createServiceLevelObjective("", "", null, null, null);    
           sloProvider.setServiceLevelObjective(serviceLevelObjective);
           sloProvider.setName("");
           sloProvider.setDescription("");
    	} else {
            sloProvider.setServiceLevelObjective(slo);
            sloProvider.setMeasurementSpecification(slo.getMeasurementSpecification());
            sloProvider.setLowerThreshold(slo.getLowerThreshold());
            sloProvider.setUpperThreshols(slo.getUpperThreshold());
            sloProvider.setName(slo.getName());
            sloProvider.setDescription(slo.getDescription());
    	}
        this.dataApp = DataApplication.getInstance();
        this.editor = ResourceEditorImpl.getInstance();     
    }
    
    /**
     * Adds the propertyChangeListener to the PropertyChangeSupport
     * @param listener the PropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    /**
     * Discards all changes made
     */
    public void cancel() {
        if (isEditing) {
        	// Undo editing
            EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(sloProvider.getServiceLevelObjective());
            CommandStack commandStack = editingDomain.getCommandStack();

            while (commandStack.canUndo()) {
                editingDomain.getCommandStack().undo();
            }
        } 
    }

    /**
     * This method saves the changes made in the wizard pages to the Service Level Objective.
     */
    public void finish() {
		ServiceLevelObjective serviceLevelObjective = sloProvider.getServiceLevelObjective();
		if (isEditing) {
			// Edit with parsley resource editor
			sloEditor.setSLOName(serviceLevelObjective, sloProvider.getName());
			sloEditor.setSLODescription(serviceLevelObjective, sloProvider.getDescription());
			sloEditor.setMeasurementSpecification(serviceLevelObjective, sloProvider.getMeasurementSpecification());		
		} else {
			// If new Slo is being created edit Slo-Object directly
			serviceLevelObjective.setName(sloProvider.getName());
			serviceLevelObjective.setDescription(sloProvider.getDescription());
			serviceLevelObjective.setMeasurementSpecification(sloProvider.getMeasurementSpecification());
    		// Add new Slo to repository
			editor.addServiceLevelObjectiveToRepository(dataApp.getSLORepository(), serviceLevelObjective);
		}
		
        changes.firePropertyChange("save", 1, 2);
    }

  /**
  * Checks if Wizard contains all necessary informations and can be finished by the user
  * 
  * @return true if all necessary attributes of the monitor are set and the wizard can finish
  */
    public boolean canFinish() {
    	return getWizardModel(SloWizardModelType.SLO_CREATION).canFinish()
    			&& getWizardModel(SloWizardModelType.SLO_MEASUREMENT_SPEC).canFinish()
    			&& getWizardModel(SloWizardModelType.SLO_THRESHOLDING).canFinish();
    }
    
    /**
     * Creates and returns the WizardModels for the SloWizard-Pages
     * 
     * @param wizardModelType
     *            an enum which indicates which wizardmodel should be returned
     * @return a wizardmodel which allows to use data methods for a specific wizardpage
     */
    public WizardModelSlo getWizardModel(SloWizardModelType sloWizardModelType) {

        if (wizardModels.containsKey(sloWizardModelType)) {
            return wizardModels.get(sloWizardModelType);
        }
        
        switch (sloWizardModelType) {
        case SLO_CREATION:
            newWizardModel = new SloCreationWizardModel(sloProvider, isEditing);
            break;
        case SLO_MEASUREMENT_SPEC:
            newWizardModel = new SloMeasurementSpecSelectionWizardModel(sloProvider, isEditing);
            break;
        case SLO_THRESHOLDING:
            newWizardModel = new SloThresholdWizardModel(sloProvider, isEditing);
            break;
        case SLO_EDITING:
        	newWizardModel = new SloCreationWizardModel(sloProvider, isEditing);
        default:
            return null;
        }

        wizardModels.put(sloWizardModelType, newWizardModel);
        return newWizardModel;
    }
    
}