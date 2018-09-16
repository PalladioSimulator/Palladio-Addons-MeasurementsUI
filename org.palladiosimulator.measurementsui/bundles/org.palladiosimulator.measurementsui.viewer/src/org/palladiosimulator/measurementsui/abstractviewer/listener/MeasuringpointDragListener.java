package org.palladiosimulator.measurementsui.abstractviewer.listener;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;

public class MeasuringpointDragListener extends DragSourceAdapter {
	private TreeViewer viewer;
	private LocalSelectionTransfer transfer;
	public MeasuringpointDragListener(TreeViewer viewer, LocalSelectionTransfer transfer) {
		this.viewer = viewer;
		this.transfer = transfer;
	}
	@Override
	public void dragSetData(DragSourceEvent event) {
		IStructuredSelection selection = viewer.getStructuredSelection();
		transfer.setSelection(selection);
	}

}
