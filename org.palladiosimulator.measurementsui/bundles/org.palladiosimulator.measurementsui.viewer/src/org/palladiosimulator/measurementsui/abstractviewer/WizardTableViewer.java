package org.palladiosimulator.measurementsui.abstractviewer;

import org.eclipse.emf.parsley.viewers.ViewerFactory;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.monitorrepository.MonitorRepositoryPackage;

/**
 * Generates a eclipse swt table viewer based on an eclipse parlsey tableview
 * 
 * @author David Schuetz
 *
 */
public abstract class WizardTableViewer extends ComponentViewer {
	protected TableViewer tableViewer;
	protected ViewerFactory tableFactory;
	protected WizardModel wizardModel;
	/**
	 * 
	 * @param parent          container where the tree viewer is placed in
	 * @param dataApplication Connection to the data binding. This is needed in
	 *                        order to get the repository of the current project.
	 */
	protected WizardTableViewer(Composite parent, WizardModel wizardModel) {
		super(parent, false);
		this.wizardModel = wizardModel;
		initEditingDomain();
		initParsley(parent);
	}

	@Override
	protected void initParsley(Composite parent) {

		tableFactory = injector.getInstance(ViewerFactory.class);
		tableViewer = tableFactory.createTableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION,
				MonitorRepositoryPackage.eINSTANCE.getMeasurementSpecification());
		tableViewer.setInput(getModelRepository());
	}

	@Override
	public void update() {

	}

	@Override
	public StructuredViewer getViewer() {
		return tableViewer;
	}

}