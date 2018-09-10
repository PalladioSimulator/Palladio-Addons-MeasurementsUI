package org.palladiosimulator.measurementsui.abstractviewer.listener;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;

public class MeasuringpointDragListener implements DragSourceListener{
	private TreeViewer viewer;
	
	public MeasuringpointDragListener(TreeViewer viewer) {
		this.viewer = viewer;
	}
	@Override
	public void dragStart(DragSourceEvent event) {
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		IStructuredSelection selection = viewer.getStructuredSelection();
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof EObject && !(firstElement instanceof MeasuringPointRepository)) {
			event.data = firstElement;
			if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
	            event.data = firstElement.toString();
	        }
		}
		
	}

	@Override
	public void dragFinished(DragSourceEvent event) {
	}

}
