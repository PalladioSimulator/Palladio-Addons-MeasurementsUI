package org.palladiosimulator.measurementsui.abstractviewer.listener;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;

/**
 * DragListener which specifies what happens if a MeasuringPoint in a viewer is dragged.
 * 
 * @author David Schuetz
 *
 */
public class MeasuringPointDragListener extends DragSourceAdapter {
    private TreeViewer viewer;
    private LocalSelectionTransfer transfer;

    /**
     * 
     * @param viewer
     *            TreeViewer where the MeasuringPoint is dragged
     * @param transfer
     *            LocalSelectionTransfer which saves what has been dragged
     */
    public MeasuringPointDragListener(TreeViewer viewer, LocalSelectionTransfer transfer) {
        this.viewer = viewer;
        this.transfer = transfer;
    }

    @Override
    public void dragSetData(DragSourceEvent event) {
        IStructuredSelection selection = viewer.getStructuredSelection();
        transfer.setSelection(selection);
    }

}
