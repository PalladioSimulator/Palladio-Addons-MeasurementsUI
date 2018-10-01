package org.palladiosimulator.measurementsui.wizard.main;

import java.io.IOException;

import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.dataprovider.StandardSetCreationProvider;
import org.palladiosimulator.measurementsui.wizard.pages.StandardSetCreationSelectionWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.StandardSetMeasuringPointSelectionWizardPage;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * This is the wizard for the standard set creation.
 * 
 * @author Domas Mikalkinas
 *
 */
public class StandardSetWizard extends org.eclipse.jface.wizard.Wizard {

	private StandardSetCreationSelectionWizardPage standardSetChoiceWizardPage;
	private StandardSetMeasuringPointSelectionWizardPage measuringPointSelectionWizardPage;
	private ResourceEditorImpl editor;
	private DataApplication dataApplication;

	public StandardSetCreationSelectionWizardPage getPage1() {
		return standardSetChoiceWizardPage;
	}

	public void setPage1(StandardSetCreationSelectionWizardPage page1) {
		this.standardSetChoiceWizardPage = page1;
	}

	public StandardSetMeasuringPointSelectionWizardPage getPage2() {
		return measuringPointSelectionWizardPage;
	}

	public void setPage2(StandardSetMeasuringPointSelectionWizardPage page2) {
		this.measuringPointSelectionWizardPage = page2;
	}

	public StandardSetWizard() {
		editor = ResourceEditorImpl.getInstance();
		dataApplication = DataApplication.getInstance();
		setWindowTitle("Create a standard set");
		standardSetChoiceWizardPage = new StandardSetCreationSelectionWizardPage(
				"Select which standard set should be created.");
		measuringPointSelectionWizardPage = new StandardSetMeasuringPointSelectionWizardPage(
				"Select all monitors which should be created.");
	}

	@Override
	public void addPages() {
		addPage(standardSetChoiceWizardPage);
		addPage(measuringPointSelectionWizardPage);
	}

	/**
	 * Retrieves all elements from the list, adds metric descriptions to it and
	 * saves it to the respective files
	 */
	@Override
	public boolean performFinish() {
		StandardSetCreationProvider provider = new StandardSetCreationProvider();
		if (measuringPointSelectionWizardPage.isAdd()) {
			Object[] tempmonitor = measuringPointSelectionWizardPage.getViewer().getCheckedElements();

			Monitor[] monitors = new Monitor[tempmonitor.length];
			System.arraycopy(tempmonitor, 0, monitors, 0, tempmonitor.length);
			provider.addMetricDescriptionsToAllMonitors(monitors);
			for (Monitor monitor : monitors) {

				editor.addMonitorToRepository(dataApplication.getModelAccessor().getMonitorRepository().get(0),
						monitor);
				editor.addMeasuringPointToRepository(
						dataApplication.getModelAccessor().getMeasuringPointRepository().get(0),
						monitor.getMeasuringPoint());
				editor.setMeasuringPointToMonitor(monitor, monitor.getMeasuringPoint());
				try {
					dataApplication.getModelAccessor().getMeasuringPointRepository().get(0).eResource().save(null);
					dataApplication.getModelAccessor().getMonitorRepository().get(0).eResource().save(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else {
			for (Object tempmeasuringpoint : measuringPointSelectionWizardPage.getViewer().getCheckedElements()) {
				MeasuringPoint measuringpoint = (MeasuringPoint) tempmeasuringpoint;
				editor.addMeasuringPointToRepository(
						dataApplication.getModelAccessor().getMeasuringPointRepository().get(0), measuringpoint);
				try {
					dataApplication.getModelAccessor().getMeasuringPointRepository().get(0).eResource().save(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

}
