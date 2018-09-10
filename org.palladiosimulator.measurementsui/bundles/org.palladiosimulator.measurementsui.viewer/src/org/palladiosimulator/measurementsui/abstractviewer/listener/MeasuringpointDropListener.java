package org.palladiosimulator.measurementsui.abstractviewer.listener;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;
import org.palladiosimulator.monitorrepository.Monitor;

public class MeasuringpointDropListener extends ViewerDropAdapter{
	private final TreeViewer viewer;
	
	public MeasuringpointDropListener(TreeViewer viewer) {
		super(viewer);
        this.viewer = viewer;
	}

	@Override
	public boolean performDrop(Object data) {
		System.out.println("Funktioniert");
		return false;
	}

	@Override
	public boolean validateDrop(Object target, int operation, TransferData transferType) {
		return target == null || target instanceof Monitor;
	}
}
