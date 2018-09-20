package org.palladiosimulator.measurementsui.wizard.viewer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.abstractviewer.WizardTableViewer;
import org.palladiosimulator.measurementsui.measurementspecification.MeasurementspecificationInjectorProvider;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;

public class MeasurementSpecificationViewer extends WizardTableViewer {
	/**
	 * 
	 * @param parent          container where the table viewer is placed in
	 * @param dataApplication Connection to the data binding. This is needed in
	 *                        order to get the repository of the current project.
	 */
	public MeasurementSpecificationViewer(Composite parent, WizardModel wizardModel) {
		super(parent, wizardModel);
		
	}

	@Override
	protected void initInjector() {
		this.injector = MeasurementspecificationInjectorProvider.getInjector();
		
	}

	@Override
	protected EObject getModelRepository() {
		//TODO: replace with correct model
		MetricDescriptionSelectionWizardModel model = (MetricDescriptionSelectionWizardModel) wizardModel;
        return model.getUsedMetricsMonitor();
	}
	
	

}
