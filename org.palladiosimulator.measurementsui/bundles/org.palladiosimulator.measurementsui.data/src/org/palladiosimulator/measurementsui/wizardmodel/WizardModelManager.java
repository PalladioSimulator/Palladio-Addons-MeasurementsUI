package org.palladiosimulator.measurementsui.wizardmodel;

import java.io.IOException;
import java.util.EnumMap;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MonitorCreationWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class manages all WizardModels used in the wizard
 * 
 * @author David Schuetz
 *
 */
public class WizardModelManager {

    private Monitor monitor;

    private ResourceEditorImpl editor;
    private DataApplication dataApp;
    private boolean isEditing;

    private EnumMap<WizardModelType, WizardModel> wizardModels = new EnumMap<WizardModelType, WizardModel>(
            WizardModelType.class);
    
    final Logger logger = LoggerFactory.getLogger(WizardModelManager.class);

    /**
     * Creates a new empty monitor which will be edited in the wizard pages
     */
    public WizardModelManager() {
        monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
        this.dataApp = DataApplication.getInstance();
        this.editor = ResourceEditorImpl.getInstance();
    }

    /**
     * @param monitor
     *            an existing monitor which will be edited in the wizard pages
     */
    public WizardModelManager(Monitor monitor) {
        this.monitor = monitor;
        this.dataApp = DataApplication.getInstance();
        this.editor = ResourceEditorImpl.getInstance();
        isEditing = true;
    }

    /**
     * Discards all changes made
     */
    public void cancel() {
        if (isEditing) {
        EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(monitor);
        CommandStack commandStack = editingDomain.getCommandStack();
        
        while (commandStack.canUndo()) {
            editingDomain.getCommandStack().undo();
            }
        } else {
            monitor = null;
        }
    }

    /**
     * Saves the changes made in the wizard pages in the Monitor and MeasuringPoint.
     */
    public void finish() {
        MeasuringPoint measuringPoint = monitor.getMeasuringPoint();

        if (!isEditing) {
            editor.addMonitorToRepository(dataApp.getMonitorRepository(), monitor);
          editor.addMeasuringPointToRepository(dataApp.getModelAccessor().getMeasuringPointRepository().get(0),
          measuringPoint);
          editor.setMeasuringPointToMonitor(monitor, measuringPoint);
        }

        
        try {
            dataApp.getModelAccessor().getMeasuringPointRepository().get(0).eResource().save(null);
            dataApp.getMonitorRepository().eResource().save(null);
        } catch (IOException e) {
            logger.warn("IOException when attempting to save changes made in Wizard. Stacktrace : {}", e.getMessage());
        }

    }

    /**
     * 
     * @return true if all necessary attributes of the monitor are set and the wizard can finish
     */
    public boolean canFinish() {
        return getWizardModel(WizardModelType.MONITOR_CREATION).canFinish()
                && getWizardModel(WizardModelType.MEASURING_POINT_SELECTION).canFinish()
                && getWizardModel(WizardModelType.METRIC_DESCRIPTION_SELECTION).canFinish()
                && getWizardModel(WizardModelType.PROCESSING_TYPE).canFinish();
    }

    /**
     * 
     * @param wizardModelType
     *            an enum which indicates which wizardmodel should be returned
     * @return a wizardmodel which allows to use data methods for a specific wizardpage
     */
    public WizardModel getWizardModel(WizardModelType wizardModelType) {

        if (wizardModels.containsKey(wizardModelType)) {
            return wizardModels.get(wizardModelType);
        }

        WizardModel newWizardModel;
        switch (wizardModelType) {
        case MONITOR_CREATION:
            newWizardModel = new MonitorCreationWizardModel(monitor, isEditing);
            break;
        case MEASURING_POINT_SELECTION:
            newWizardModel = new MeasuringPointSelectionWizardModel(monitor, isEditing);
            break;
        case METRIC_DESCRIPTION_SELECTION:
            newWizardModel = new MetricDescriptionSelectionWizardModel(monitor, isEditing);
            break;
        case PROCESSING_TYPE:
            newWizardModel = new ProcessingTypeSelectionWizardModel(monitor, isEditing);
            break;
        default:
            return null;
        }

        wizardModels.put(wizardModelType, newWizardModel);
        return newWizardModel;
    }
}
