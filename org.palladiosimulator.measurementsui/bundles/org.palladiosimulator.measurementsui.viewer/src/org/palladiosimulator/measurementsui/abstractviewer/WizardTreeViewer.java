package org.palladiosimulator.measurementsui.abstractviewer;

import org.eclipse.emf.parsley.viewers.ViewerFactory;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
public abstract class WizardTreeViewer extends ComponentViewer {
	protected TreeViewer treeViewer;
	protected ViewerFactory treeFactory;
	protected DataApplication application;
	/**
	 * 
	 * @param parent          container where the tree viewer is placed in
	 * @param dataApplication Connection to the data binding. This is needed in
	 *                        order to get the repository of the current project.
	 */
	protected WizardTreeViewer(Composite parent, DataApplication application) {
		super(parent, false);
		this.application = application;
		initEditingDomain();
		initParsley(parent);
		treeViewer.expandAll();
	}

	@Override
	protected void initParsley(Composite parent) {

		treeViewer = new TreeViewer(parent);
		treeFactory = injector.getInstance(ViewerFactory.class);
		update();
	}

	@Override
	public void update() {
		Object[] expandedElements = treeViewer.getExpandedElements();
		initEditingDomain();
		resource = updateResource(getModelRepository());
		treeFactory.initialize(treeViewer, resource);
		treeViewer.setExpandedElements(expandedElements);
		treeViewer.refresh();
	}

	@Override
	public StructuredViewer getViewer() {
		return treeViewer;
	}

}