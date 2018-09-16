package org.palladiosimulator.measurementsui.abstractviewer.listener;

import java.io.IOException;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.abstractviewer.MpTreeViewer;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.monitorrepository.Monitor;

public class MeasuringpointDropListener extends ViewerDropAdapter{
	MpTreeViewer viewer;
	public MeasuringpointDropListener(MpTreeViewer mpTreeViewer) {
		super(mpTreeViewer.getViewer());
		this.viewer = mpTreeViewer;
	}

	@Override
	public boolean performDrop(Object data) {
		IStructuredSelection selection = (IStructuredSelection) data;
		ResourceEditor editor = new ResourceEditorImpl();
		editor.setMeasuringPointToMonitor((Monitor) getCurrentTarget(), (MeasuringPoint) selection.getFirstElement());
		try {
			viewer.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean validateDrop(Object target, int operation, TransferData transferType) {
		return target instanceof Monitor;
	}
}
