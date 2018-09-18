package org.palladiosimulator.simulizar.ui.measuringview.viewer;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.abstractviewer.MpTreeViewer;
import org.palladiosimulator.measurementsui.abstractviewer.listener.MeasuringpointDropListener;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;

import monitorrepositoryview.MonitorrepositoryviewInjectorProvider;

/**
 * Generates a tree view where all monitors from a selected monitorrepository in the workspace are
 * shown.
 * 
 * @author David Schuetz
 */
public class MonitorTreeViewer extends MpTreeViewer {

    /**
     * 
     * @param parent
     *            composite where the tree view is embedded
     * @param dirty
     *            state of the tree view
     * @param commandService
     *            eclipse commandservice in order to save the view
     * @param application
     *            Connection to the data binding. This is needed in order to get the repository of
     *            the current project.
     */
    public MonitorTreeViewer(Composite parent, MDirtyable dirty, ECommandService commandService,
            DataApplication application) {
        super(parent, dirty, commandService, application);
    }

    @Override
    protected void initInjector() {
        this.injector = MonitorrepositoryviewInjectorProvider.getInjector();
    }

    @Override
    protected EObject getModelRepository() {
        return dataApplication.getModelAccessor().getMonitorRepository().get(0);
    }

    @Override
    protected void initDragAndDrop() {
        int operations = DND.DROP_COPY | DND.DROP_MOVE;
        final LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
        Transfer[] transferTypes = new Transfer[] { transfer };
        treeViewer.addDropSupport(operations, transferTypes, new MeasuringpointDropListener(this));
    }

}
