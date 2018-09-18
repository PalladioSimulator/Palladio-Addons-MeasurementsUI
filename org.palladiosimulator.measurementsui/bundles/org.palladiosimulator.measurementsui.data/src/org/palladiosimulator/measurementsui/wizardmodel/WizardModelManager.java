package org.palladiosimulator.measurementsui.wizardmodel;

import java.io.IOException;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MonitorCreationWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;

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

    /**
     * Creates a new empty monitor which will be edited in the wizard pages
     */
    public WizardModelManager() {
        monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
        this.dataApp = DataApplication.getInstance();
        this.editor = new ResourceEditorImpl();
    }

    /**
     * @param monitor
     *            an existing monitor which will be edited in the wizard pages
     */
    public WizardModelManager(Monitor monitor) {
        this.monitor = monitor;
        this.dataApp = DataApplication.getInstance();
        this.editor = new ResourceEditorImpl();
        isEditing = true;
    }

    /**
     * Discards all changes made
     */
    public void cancel() {

    }

    /**
     * Saves the changes made in the wizard pages in the Monitor and MeasuringPoint.
     */
    public void finish() {
        MeasuringPoint measuringPoint = monitor.getMeasuringPoint();

        editor.addMonitorToRepository(dataApp.getModelAccessor().getMonitorRepository().get(0), monitor);
        editor.addMeasuringPointToRepository(dataApp.getModelAccessor().getMeasuringPointRepository().get(0),
                measuringPoint);
        editor.setMeasuringPointToMonitor(monitor, measuringPoint);
        try {
            dataApp.getModelAccessor().getMeasuringPointRepository().get(0).eResource().save(null);
            dataApp.getModelAccessor().getMonitorRepository().get(0).eResource().save(null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 
     * @return true if all necessary attributes of the monitor are set and the wizard can finish
     */
    public boolean canFinish() {
        return !monitor.getMeasurementSpecifications().isEmpty();
    }

    /**
     * 
     * @param wizardModel
     *            an enum which indicates which wizardmodel should be returned
     * @return a wizardmodel which allows to use data methods for a specific wizardpage
     */
    public WizardModel getWizardModel(WizardModelType wizardModel) {
        switch (wizardModel) {
        case MONITOR_CREATION:
            return new MonitorCreationWizardModel(monitor);
        case MEASURING_POINT_SELECTION:
            MeasuringPointSelectionWizardModel model = new MeasuringPointSelectionWizardModel(monitor, isEditing);
            model.setInstance(model);
            return model;
        case METRIC_DESCRIPTION_SELECTION:
            return new MetricDescriptionSelectionWizardModel(monitor, isEditing);
        case PROCESSING_TYPE:
            return new ProcessingTypeSelectionWizardModel();
        default:
            return null;
        }
    }
}
