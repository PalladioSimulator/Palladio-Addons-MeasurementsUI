package org.palladiosimulator.measurementsui.wizardmodel;

import org.eclipse.emf.ecore.util.EcoreUtil;
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
 * 
 * @author David Schuetz
 *
 */
public class WizardModelManager {

	private Monitor monitor;
	private MeasuringPoint measuringPoint;
	private ResourceEditorImpl editor;
	private DataApplication dataApp;

	public WizardModelManager() {
		monitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
		this.dataApp = DataApplication.getInstance();
		this.editor = new ResourceEditorImpl();

	}

	public WizardModelManager(Monitor monitor) {
		this.monitor = monitor;
	}

	public void cancel() {

	}

	public void finish() {
		measuringPoint = monitor.getMeasuringPoint();

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

	public WizardModel getWizardModel(WizardModelType wizardModel) {
		switch (wizardModel) {
		case MONITOR_CREATION:
			return new MonitorCreationWizardModel(monitor);
		case MEASURING_POINT_SELECTION:
			MeasuringPointSelectionWizardModel model = new MeasuringPointSelectionWizardModel(monitor);
			model.setInstance(model);
			return model;

		case METRIC_DESCRIPTION_SELECTION:
			return new MetricDescriptionSelectionWizardModel(monitor);
		case PROCESSING_TYPE:
			return new ProcessingTypeSelectionWizardModel();
		default:
			return null;
		}
	}
}
