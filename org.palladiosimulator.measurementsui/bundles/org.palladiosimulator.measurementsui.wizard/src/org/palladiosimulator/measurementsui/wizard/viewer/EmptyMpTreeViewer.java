package org.palladiosimulator.measurementsui.wizard.viewer;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.abstractviewer.ComponentViewer;
import org.palladiosimulator.measurementsui.abstractviewer.MpTreeViewer;
import org.palladiosimulator.measurementsui.abstractviewer.WizardTreeViewer;
import org.palladiosimulator.measurementsui.abstractviewer.listener.MeasuringpointDragListener;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;

import emptymeasuringpointswizard.EmptymeasuringpointsInjectorProvider;

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