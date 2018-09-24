package org.palladiosimulator.simulizar.ui.measurementsdashboard.parts;


import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.monitorrepository.ProcessingType;

public class MeasurementsFilter extends ViewerFilter {

    private String searchString;

    public void setSearchText(String s) {
        // ensure that the value can be used for matching
        this.searchString = (".*" + s + ".*").trim().replace(" ", "").toLowerCase();
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        TreeViewer treeViewer = (TreeViewer) viewer;
        if (searchString == null || searchString.length() == 0 || element instanceof MonitorRepository
                || element instanceof MeasuringPointRepository) {
            return true;
        }

        if (element instanceof Monitor) {
            Monitor monitor = (Monitor) element;
            String monitorMatch = monitor.getEntityName() + "$" + monitor.getId() + "$" + monitor.toString()
                    + "$monitor$" + monitor.getMeasuringPoint().getStringRepresentation();
            if (monitorMatch.replace(" ", "").trim().toLowerCase().matches(searchString)) {
                return true;
            }
            
            for (MeasurementSpecification measurement : monitor.getMeasurementSpecifications()) {
                if (select(viewer, monitor, measurement)) {
                    treeViewer.setExpandedState(element, true);
                    return true;
                }
            }
            
        }

        if (element instanceof MeasurementSpecification) {
            MeasurementSpecification measurement = (MeasurementSpecification) element;
            String measurementMatch = measurement.getName() + "$" + measurement.getId() + "$"
                    + measurement.getMetricDescription().getName() + "$" + measurement.getMetricDescription().getId()
                    + "$" + measurement.getMetricDescription().getTextualDescription()
                    + "$MeasurementSpecification$MetricDescription";
            if (select(viewer, measurement, measurement.getProcessingType())) {
                treeViewer.setExpandedState(element, true);
                return true;
            }
            return (measurementMatch.replace(" ", "").trim().toLowerCase().matches(searchString));
        }

        if (element instanceof ProcessingType) {
            ProcessingType processing = (ProcessingType) element;
            String processingTypeMatch = processing.getId()+"$"+processing.toString()+"$ProcessingType";
            return processingTypeMatch.replace(" ", "").trim().toLowerCase().matches(searchString);
        }

        if (element instanceof MeasuringPoint) {
            MeasuringPoint measuringPoint = (MeasuringPoint) element;
            String measuringPointMatch = measuringPoint.getStringRepresentation()+"$"+measuringPoint.toString()+"$MeasuringPoint";
            return measuringPointMatch.replace(" ", "").trim().toLowerCase().matches(searchString);
        }

        return false;
    }
    
}