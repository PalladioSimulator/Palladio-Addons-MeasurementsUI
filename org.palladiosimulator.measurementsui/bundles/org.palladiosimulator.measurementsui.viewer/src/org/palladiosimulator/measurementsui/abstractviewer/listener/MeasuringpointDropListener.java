package org.palladiosimulator.measurementsui.abstractviewer.listener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.abstractviewer.MeasurementsTreeViewer;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * DropListener which specifies what happens if a MeasuringPoint is dropped into a container.
 * 
 * @author David Schuetz
 *
 */
public class MeasuringpointDropListener extends ViewerDropAdapter {
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     * 
     * @param measurementTreeViewer
     *            MeasurementTreeViewer where the MeasuringPoint is dropped in.
     */
    public MeasuringpointDropListener(MeasurementsTreeViewer measurementTreeViewer, PropertyChangeListener listener) {
        super(measurementTreeViewer.getViewer());
        this.changes.addPropertyChangeListener(listener);
    }

    @Override
    public boolean performDrop(Object data) {
        IStructuredSelection selection = (IStructuredSelection) data;
        if (selection.getFirstElement() instanceof MeasuringPoint) {
            ResourceEditor editor = ResourceEditorImpl.getInstance();
            editor.setMeasuringPointToMonitor((Monitor) getCurrentTarget(),
                    (MeasuringPoint) selection.getFirstElement());
            changes.firePropertyChange("save", 1,2);
           
        }
        return false;
    }

    @Override
    public boolean validateDrop(Object target, int operation, TransferData transferType) {
        return target instanceof Monitor;
    }
}
