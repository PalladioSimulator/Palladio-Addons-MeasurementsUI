package org.palladiosimulator.measurementsui.wizard.viewer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.abstractviewer.WizardTreeViewer;
import org.palladiosimulator.measurementsui.abstractviewer.listener.MeasuringpointDragListener;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;

import emptymeasuringpoints.EmptymeasuringpointsInjectorProvider;


/**
 * Generates a tree view where all empty measuring points from all
 * measuringpoint repositories in the workspace are shown.
 * 
 * @author David Schuetz
 */
public class EmptyMpTreeViewer extends WizardTreeViewer {

	/**
	 * 
	 * @param parent         container where the tree viewer is placed in
	 * @param dirty          state of the e4 view
	 * @param commandService of the eclipse application in order to make the tree
	 *                       view saveable
	 * @param application    Connection to the data binding. This is needed in order
	 *                       to get the repository of the current project.
	 */
	public EmptyMpTreeViewer(Composite parent, DataApplication application) {
		super(parent, application);
		int operations = DND.DROP_COPY| DND.DROP_MOVE;
		final LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
        Transfer[] transferTypes = new Transfer[]{transfer};
        treeViewer.addDragSupport(operations, transferTypes , new MeasuringpointDragListener((TreeViewer) getViewer(), transfer));
	}

	@Override
	protected EObject getModelRepository() {
		return application.getModelAccessor().getMeasuringPointRepository().get(0);
	}

	@Override
	protected void initInjector() {
		injector = EmptymeasuringpointsInjectorProvider.getInjector();
	}

}