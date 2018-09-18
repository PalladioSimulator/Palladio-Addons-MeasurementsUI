package org.palladiosimulator.measurementsui.wizard.viewer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.abstractviewer.WizardTableViewer;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;
import tableform.TableformInjectorProvider;

/**
 * Generates a table view where all MeasurementSpecifications of a monitor are shown
 * @author David Schuetz
 *
 */
public class SelectMeasurementsViewer extends WizardTableViewer {
	/**
	 * 
	 * @param parent          container where the tree viewer is placed in
	 * @param dataApplication Connection to the data binding. This is needed in
	 *                        order to get the repository of the current project.
	 */
	public SelectMeasurementsViewer(Composite parent, WizardModel wizardModel) {
		super(parent, wizardModel);
	}

	@Override
	protected void initInjector() {
		this.injector = TableformInjectorProvider.getInjector();
	}

	@Override
	protected EObject getModelRepository() {
		MetricDescriptionSelectionWizardModel model = (MetricDescriptionSelectionWizardModel) wizardModel;
		return model.getUnusedMetricsMonitor();
	}

}