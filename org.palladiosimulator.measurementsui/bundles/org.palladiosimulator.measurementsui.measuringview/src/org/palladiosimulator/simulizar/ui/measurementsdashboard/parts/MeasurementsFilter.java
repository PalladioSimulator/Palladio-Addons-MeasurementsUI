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

    private String searchText;
    private boolean filterActiveMonitors;
    private boolean filterInactiveMonitors;

    public void setSearchText(String s) {
        // ensure that the value can be used for matching
        this.searchText = (".*" + s + ".*").trim().replace(" ", "").toLowerCase();
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        TreeViewer treeViewer = (TreeViewer) viewer;
        if (searchText == null || searchText.length() == 0 || element instanceof MonitorRepository
                || element instanceof MeasuringPointRepository) {
            return true;
        }

        if (element instanceof Monitor) {
             return filterMonitor(element, treeViewer);
        }

        if (element instanceof MeasurementSpecification) {
            return filterMeasurementSpecification(element, treeViewer);
        }

        if (element instanceof ProcessingType) {
            return filterProcessingType(element);
        }

        if (element instanceof MeasuringPoint) {
            return filterMeasuringPoint(element);
        }

        return false;
    }
    
    private boolean filterMonitor(Object element, TreeViewer treeViewer) {
        Monitor monitor = (Monitor) element;
        if (filterActiveMonitors && monitor.isActivated()) {
            return false;
        }
        if (filterInactiveMonitors && !monitor.isActivated()) {
            return false;
        }
        final String monitorMatch = monitor.getEntityName() + "$" + monitor.getId() + "$" + monitor.toString()
                + "$monitor$" + monitor.getMeasuringPoint().getStringRepresentation();
        if (monitorMatch.replace(" ", "").trim().toLowerCase().matches(searchText)) {
            return true;
        }
        
        for (MeasurementSpecification measurement : monitor.getMeasurementSpecifications()) {
            if (select(treeViewer, monitor, measurement)) {
                treeViewer.setExpandedState(element, true);
                return true;
            }
        }
        return false;
    }
 
    private boolean filterMeasurementSpecification(Object element, TreeViewer treeViewer) {
        MeasurementSpecification measurement = (MeasurementSpecification) element;
        final String measurementMatch = measurement.getName() + "$" + measurement.getId() + "$"
                + measurement.getMetricDescription().getName() + "$" + measurement.getMetricDescription().getId()
                + "$" + measurement.getMetricDescription().getTextualDescription()
                + "$MeasurementSpecification$MetricDescription";
        
        if (select(treeViewer, measurement, measurement.getProcessingType())) {
            treeViewer.setExpandedState(element, true);
            return true;
        }
        return measurementMatchesSearchText(measurementMatch, searchText);
    }

    private boolean filterProcessingType(Object element) {
        ProcessingType processing = (ProcessingType) element;
        String processingTypeMatch = processing.getId()+"$"+processing.toString()+"$ProcessingType";
        return processingTypeMatch.replace(" ", "").trim().toLowerCase().matches(searchText);
    }

    private boolean filterMeasuringPoint(Object element) {
        MeasuringPoint measuringPoint = (MeasuringPoint) element;
        String measuringPointMatch = measuringPoint.getStringRepresentation()+"$"+measuringPoint.toString()+"$MeasuringPoint";
        return measuringPointMatch.replace(" ", "").trim().toLowerCase().matches(searchText);
    }

    private boolean measurementMatchesSearchText(String measurementMatch, String searchText) {
        return (measurementMatch.replace(" ", "").trim().toLowerCase().matches(searchText));
    }
    
    /**
     * @param filterActiveMonitors the filterActiveMonitors to set
     */
    public void setFilterActiveMonitors(boolean filterActiveMonitors) {
        this.filterActiveMonitors = filterActiveMonitors;
    }

    /**
     * @param filterInactiveMonitors the filterInactiveMonitors to set
     */
    public void setFilterInactiveMonitors(boolean filterInactiveMonitors) {
        this.filterInactiveMonitors = filterInactiveMonitors;
    }
}