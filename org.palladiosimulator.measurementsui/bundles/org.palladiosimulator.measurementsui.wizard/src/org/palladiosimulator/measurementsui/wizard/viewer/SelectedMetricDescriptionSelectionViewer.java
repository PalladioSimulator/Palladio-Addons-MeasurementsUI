package org.palladiosimulator.measurementsui.wizard.viewer;


import org.eclipse.swt.widgets.Composite;
import org.palladio.simulator.measurementsui.selectmeasurements.emptyview.EmptyviewInjectorProvider;
import org.palladiosimulator.measurementsui.abstractviewer.WizardTableViewer;
import org.palladiosimulator.monitorrepository.Monitor;

public class SelectedMetricDescriptionSelectionViewer extends WizardTableViewer {

	/**
	 * 
	 * @param parent          container where the tree viewer is placed in
	 * @param dataApplication Connection to the data binding. This is needed in
	 *                        order to get the repository of the current project.
	 */
	public SelectedMetricDescriptionSelectionViewer(Composite parent, Monitor usedMetricsMonitor) {
		super(parent, usedMetricsMonitor);
	}

	@Override
	protected void initInjector() {
		this.injector = EmptyviewInjectorProvider.getInjector();
	}

}
