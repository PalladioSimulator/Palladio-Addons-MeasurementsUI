package org.palladiosimulator.simulizar.ui.measurementsdashboard.viewer;

import java.util.Optional;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.measurementsui.abstractviewer.MeasurementsTreeViewer;
import org.palladiosimulator.measurementsui.abstractviewer.listener.MeasuringPointDragListener;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;

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
     * @param application
     *            the connection to the data binding. This is needed in order to get the repository of
     *            the current project.
     */
    public EmptyMeasuringPointsTreeViewer(Composite parent, MDirtyable dirty, ECommandService commandService,
            DataApplication application) {
        super(parent, dirty, commandService, application);
    }

    @Override
    protected Optional<EObject> getModelRepository() {
        if(dataApplication.getModelAccessor().measuringPointRepositoryExists()) {
            return Optional.of((EObject)dataApplication.getModelAccessor().getMeasuringPointRepository().get(0));
        }
        return Optional.empty();
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