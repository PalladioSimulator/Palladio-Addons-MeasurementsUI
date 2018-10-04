package org.palladiosimulator.measurementsui.wizard.main;

import java.io.IOException;

import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.dataprovider.StandardSetCreationProvider;
import org.palladiosimulator.measurementsui.wizard.pages.StandardSetCreationSelectionWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.StandardSetMeasuringPointSelectionWizardPage;
import org.palladiosimulator.monitorrepository.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private final Logger logger = LoggerFactory.getLogger(StandardSetWizard.class);

	/**
	 * returns the first wizard page of the standard set wizard
	 * @return first wizardpage
	 */
	public StandardSetCreationSelectionWizardPage getPage1() {
		return standardSetChoiceWizardPage;
	}
/**
 * sets the first wizard page of the standard set wizard
 * @param page1 first wizard page
 */
	public void setPage1(StandardSetCreationSelectionWizardPage page1) {
		this.standardSetChoiceWizardPage = page1;
	}
    /**
     * returns the second wizard page of the standard set wizard
     * @return second wizardpage
     */
	public StandardSetMeasuringPointSelectionWizardPage getPage2() {
		return measuringPointSelectionWizardPage;
	}
/**
 * sets the second wizard page of the standard set wizard
 * @param page2 second wizard page
 */
	public void setPage2(StandardSetMeasuringPointSelectionWizardPage page2) {
		this.measuringPointSelectionWizardPage = page2;
	}

	/**
	 * constructor for the wizard 
	 */
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
		if (measuringPointSelectionWizardPage.isLoadMonitorAndMeasuringpoint()) {
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
				    logger.warn("IOException when attempting to save changes from standard set wizard. Stacktrace: {}", e.getMessage());
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
				    logger.warn("IOException when attempting to save changes from standard set wizard. Stacktrace: {}", e.getMessage());
				}
			}
		}
		return true;
	}

}
