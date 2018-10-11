package org.palladiosimulator.measurementsui.wizard.viewer;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.abstractviewer.WizardTableViewer;
import org.palladiosimulator.measurementsui.processingtype.ProcessingtypeInjectorProvider;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * Generates a table view where all selected metrics from the 3rd wizard page are shown 
 * and where further properties can be specified.
 * 
 * @author Mehmet, Ba
 *
 */
public class ProcessingTypeSelectionViewer extends WizardTableViewer {
	
	/**
	 * 
	 * @param parent          container where the table viewer is placed in
	 * @param dataApplication Connection to the data binding. This is needed in
	 *                        order to get the repository of the current project.
	 */
	public ProcessingTypeSelectionViewer(Composite parent, Monitor usedMetricsMonitor) {
		super(parent, usedMetricsMonitor);
		
	}

	@Override
	protected void initInjector() {
		this.injector = ProcessingtypeInjectorProvider.getInjector();
		
	}
}
