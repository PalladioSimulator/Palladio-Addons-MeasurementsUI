package org.palladiosimulator.simulizar.ui.measurementsdashboard.viewer;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.abstractviewer.MeasurementsTreeViewer;
import org.palladiosimulator.monitorrepository.MonitorRepository;

import monitorrepositoryview.MonitorrepositoryviewInjectorProvider;

/**
 * Generates a tree view where all monitors from a selected monitorrepository in the workspace are
 * shown.
 * 
 * @author David Schuetz
 */
public class MonitorTreeViewer extends MeasurementsTreeViewer {

    /**
     * 
     * @param parent
     *            composite where the tree view is embedded
     * @param dirty
     *            the dirty state which indicates whether there were changes made
     * @param commandService
     *            the eclipse commandservice in order to save the view
     * @param monitorRepository
     *            a monitor repository which is displayed with all its monitors,
     *            measurementspecifications and processing types in the tree view
     */
    public MonitorTreeViewer(Composite parent, MDirtyable dirty, ECommandService commandService,
            MonitorRepository monitorRepository) {
        super(parent, dirty, commandService, monitorRepository);
    }

    @Override
    protected void initInjector() {
        this.injector = MonitorrepositoryviewInjectorProvider.getInjector();
    }

    @Override
    protected void initDragAndDrop() {
        //Override Parsley Drag and Drop
    }
    
    public void setDropAdapter(ViewerDropAdapter adapter) {
        int operations = DND.DROP_COPY | DND.DROP_MOVE;
        final LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
        Transfer[] transferTypes = new Transfer[] { transfer };
        treeViewer.addDropSupport(operations, transferTypes, adapter);
    }

}
