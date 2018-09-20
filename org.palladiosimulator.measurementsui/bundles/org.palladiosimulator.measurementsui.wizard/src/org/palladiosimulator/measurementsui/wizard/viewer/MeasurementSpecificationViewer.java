package org.palladiosimulator.measurementsui.wizard.viewer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.abstractviewer.WizardTableViewer;
import org.palladiosimulator.measurementsui.wizardmain.handlers.MeasurementSpecification;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;
import org.palladiosimulator.monitorrepository.impl.MeasurementSpecificationImpl;

 /**
  * autor mehmet
  */
import tableform.TableformInjectorProvider;

public class MeasurementSpecificationViewer extends WizardTableViewer {
	/**
	 * 
	 * @param parent          container where the table viewer is placed in
	 * @param dataApplication Connection to the data binding. This is needed in
	 *                        order to get the repository of the current project.
	 */
	protected MeasurementSpecificationViewer(Composite parent, WizardModel wizardModel) {
		super(parent, wizardModel);
		
	}

	@Override
	protected void initInjector() {
		this.injector = TableformInjectorProvider.getInjector();
		
	}

	@Override
	protected EObject getModelRepository() {
		MetricDescriptionSelectionWizardModel model = (MetricDescriptionSelectionWizardModel) wizardModel;
		return model.getUsedMetricsMonitor();
	}
	
	

}
