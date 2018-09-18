package org.palladiosimulator.measurementsui.wizard.viewer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.abstractviewer.WizardTreeViewer;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;

import emptymeasuringpoints.EmptymeasuringpointsInjectorProvider;

/**
 * Generates a tree view where all empty measuring points from all measuringpoint repositories in
 * the workspace are shown.
 * 
 * @author David Schuetz
 */
public class EmptyMpTreeViewer extends WizardTreeViewer {

    /**
     * 
     * @param parent
     *            the container where the tree viewer is placed in
     * @param application
     *            the connection to the data binding. This is needed in order to get the repository
     *            of the current project.
     */
    public EmptyMpTreeViewer(Composite parent, DataApplication application) {
        super(parent, application);
    }

    @Override
    protected EObject getModelRepository() {
        return dataApplication.getModelAccessor().getMeasuringPointRepository().get(0);
    }

    @Override
    protected void initInjector() {
        injector = EmptymeasuringpointsInjectorProvider.getInjector();
    }

}