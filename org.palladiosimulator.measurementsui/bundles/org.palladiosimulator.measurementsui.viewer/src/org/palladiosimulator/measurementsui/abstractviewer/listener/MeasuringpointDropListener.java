package org.palladiosimulator.measurementsui.abstractviewer.listener;

import java.io.IOException;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.abstractviewer.MpTreeViewer;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * DropListener which specifies what happens if a MeasuringPoint is dropped into a container.
 * 
 * @author zss3
 *
 */
public class MeasuringpointDropListener extends ViewerDropAdapter {
    private MpTreeViewer measurementTreeViewer;

    /**
     * 
     * @param measurementTreeViewer
     *            MeasurementTreeViewer where the MeasuringPoint is dropped in.
     */
    public MeasuringpointDropListener(MpTreeViewer measurementTreeViewer) {
        super(measurementTreeViewer.getViewer());
        this.measurementTreeViewer = measurementTreeViewer;
    }

    @Override
    public boolean performDrop(Object data) {
        IStructuredSelection selection = (IStructuredSelection) data;
        ResourceEditor editor = new ResourceEditorImpl();
        editor.setMeasuringPointToMonitor((Monitor) getCurrentTarget(), (MeasuringPoint) selection.getFirstElement());
        try {
            measurementTreeViewer.save();
        } catch (IOException e) {
            // TODO Use a logger here
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean validateDrop(Object target, int operation, TransferData transferType) {
        return target instanceof Monitor;
    }
}
