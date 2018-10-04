package org.palladiosimulator.measurementsui.abstractviewer.listener;

import java.io.IOException;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.abstractviewer.MeasurementsTreeViewer;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.monitorrepository.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DropListener which specifies what happens if a MeasuringPoint is dropped into a container.
 * 
 * @author David Schuetz
 *
 */
public class MeasuringpointDropListener extends ViewerDropAdapter {
    private MeasurementsTreeViewer measurementTreeViewer;

    private final Logger logger = LoggerFactory.getLogger(MeasuringpointDropListener.class);
    
    /**
     * 
     * @param measurementTreeViewer
     *            MeasurementTreeViewer where the MeasuringPoint is dropped in.
     */
    public MeasuringpointDropListener(MeasurementsTreeViewer measurementTreeViewer) {
        super(measurementTreeViewer.getViewer());
        this.measurementTreeViewer = measurementTreeViewer;
    }

    @Override
    public boolean performDrop(Object data) {
        IStructuredSelection selection = (IStructuredSelection) data;
        ResourceEditor editor = ResourceEditorImpl.getInstance();
        editor.setMeasuringPointToMonitor((Monitor) getCurrentTarget(), (MeasuringPoint) selection.getFirstElement());
        try {
            measurementTreeViewer.save();
        } catch (IOException e) {
            logger.warn("IOException when attempting to perform Drop. Stacktrace: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean validateDrop(Object target, int operation, TransferData transferType) {
        return target instanceof Monitor;
    }
}
