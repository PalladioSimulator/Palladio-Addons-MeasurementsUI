package org.palladiosimulator.measurementsui.abstractviewer;

import org.eclipse.emf.parsley.viewers.ViewerFactory;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;

/**
 * Generates a Eclipse SWT TreeViewer based on an Eclipse Parsley TreeViewer
 * 
 * @author David Schuetz
 *
 */
public abstract class WizardTreeViewer extends ComponentViewer {
    protected TreeViewer treeViewer;
    protected ViewerFactory treeFactory;
    protected DataApplication dataApplication;

    /**
     * 
     * @param parent
     *            the container where the tree viewer is placed in
     * @param dataApplication
     *            the connection to the data binding. This is needed in order to get the repository of
     *            the current project.
     */
    protected WizardTreeViewer(Composite parent, DataApplication dataApplication) {
        super(parent);
        this.dataApplication = dataApplication;
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

    @Override
    protected void initDragAndDrop() {
        // Do not support Drag and Drop
    }

}