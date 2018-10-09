package org.palladiosimulator.measurementsui.wizard.viewer;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.palladio.simulator.measurementsui.selectmeasurements.emptyview.EmptyviewInjectorProvider;
import org.palladiosimulator.measurementsui.abstractviewer.WizardTableViewer;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;

public class SelectedMetricDescriptionSelectionViewer extends WizardTableViewer {

	/**
	 * 
	 * @param parent          container where the tree viewer is placed in
	 * @param dataApplication Connection to the data binding. This is needed in
	 *                        order to get the repository of the current project.
	 */
	public SelectedMetricDescriptionSelectionViewer(Composite parent, WizardModel wizardModel) {
		super(parent, wizardModel);
	}

	@Override
	protected void initInjector() {
		this.injector = EmptyviewInjectorProvider.getInjector();
	}

	@Override
	protected Optional<EObject> getModelRepository() {
		MetricDescriptionSelectionWizardModel model = (MetricDescriptionSelectionWizardModel) wizardModel;
		return Optional.of(model.getUsedMetricsMonitor());
	}

}
