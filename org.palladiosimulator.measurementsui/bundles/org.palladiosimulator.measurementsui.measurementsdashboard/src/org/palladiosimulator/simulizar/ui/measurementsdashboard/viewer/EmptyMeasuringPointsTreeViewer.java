package org.palladiosimulator.simulizar.ui.measurementsdashboard.viewer;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.measurementsui.abstractviewer.MeasurementsTreeViewer;
import org.palladiosimulator.measurementsui.abstractviewer.listener.MeasuringPointDragListener;

import emptymeasuringpoints.EmptymeasuringpointsInjectorProvider;

/**
 * Generates a tree view where all empty measuring points from all measuringpoint repositories in
 * the workspace are shown.
 * 
 * @author David Schuetz
 */
public class EmptyMeasuringPointsTreeViewer extends MeasurementsTreeViewer {

    /**
     * 
     * @param parent
     *            the container where the tree viewer is placed in
     * @param dirty
     *            the dirty state which indicates whether there were changes made
     * @param commandService
     *            a service of the eclipse application in order to make the tree view saveable
     * @param repository
     *            a measuring point repository which is displayed with all his measuring points in the tree view
     */
    public EmptyMeasuringPointsTreeViewer(Composite parent, MDirtyable dirty, ECommandService commandService,
            MeasuringPointRepository repository) {
        super(parent, dirty, commandService, repository);
    }

    @Override
    protected void initInjector() {
        injector = EmptymeasuringpointsInjectorProvider.getInjector();
    }

    @Override
    protected void initDragAndDrop() {
        int operations = DND.DROP_COPY | DND.DROP_MOVE;
        final LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
        Transfer[] transferTypes = new Transfer[] { transfer };
        treeViewer.addDragSupport(operations, transferTypes,
                new MeasuringPointDragListener((TreeViewer) getViewer(), transfer));
    }
}