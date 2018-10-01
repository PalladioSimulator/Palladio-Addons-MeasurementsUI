package org.palladiosimulator.simulizar.ui.measurementsdashboard.filter;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.monitorrepository.ProcessingType;

/**
 * Viewer Filter which filters all elements of a TreeViewer given a search text. Additionally
 * expands or collapses certain elements of the tree.
 * 
 * @author David Schuetz
 *
 */
public class MeasurementsFilter extends ViewerFilter {
    private String searchText;
    private boolean filterActiveMonitors;
    private boolean filterInactiveMonitors;

    /**
     * 
     * @param searchText
     *            a text which will be matched with all elements of a viewer. Deciding whether it is
     *            shown or not.
     */
    public void setSearchText(String searchText) {
        // ensure that the value can be used for matching
        this.searchText = (".*" + searchText + ".*").trim().replace(" ", "").toLowerCase();
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

    /**
     * 
     * @param element
     *            a monitor which is matched with the search text
     * @param treeViewer
     *            a treeviewer where the monitor is in
     * @return true if the search text matches the monitors properties
     */
    private boolean filterMonitor(Object element, TreeViewer treeViewer) {
        Monitor monitor = (Monitor) element;
        if (filterActiveMonitors && monitor.isActivated()) {
            return false;
        }
        if (filterInactiveMonitors && !monitor.isActivated()) {
            return false;
        }
        final String monitorMatch = monitor.getEntityName() + "$" + monitor.getId() + "$" + monitor.toString()
                + "$monitor$" + monitor.getMeasuringPoint().getStringRepresentation() + "$"
                + monitor.getMeasuringPoint().toString();
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

    /**
     * 
     * @param element
     *            a MeasurementSpecification which is matched with the search text
     * @param treeViewer
     *            a treeviewer where the MeasurementSpecification is in
     * @return true if the search text matches the MeasurementSpecification properties
     */
    private boolean filterMeasurementSpecification(Object element, TreeViewer treeViewer) {
        MeasurementSpecification measurement = (MeasurementSpecification) element;
        final String measurementSpecificationMatch = measurement.getName() + "$" + measurement.getId() + "$"
                + measurement.getMetricDescription().getName() + "$" + measurement.getMetricDescription().getId() + "$"
                + measurement.getMetricDescription().getTextualDescription()
                + "$MeasurementSpecification$MetricDescription";
        if (select(treeViewer, measurement, measurement.getProcessingType())) {
            treeViewer.setExpandedState(element, true);
            return true;
        }
        return measurementMatchesSearchText(measurementSpecificationMatch, searchText);
    }

    /**
     * 
     * @param element
     *            a ProcessingType which is matched with the search text
     * @param treeViewer
     *            a treeviewer where the ProcessingType is in
     * @return true if the search text matches the ProcessingType properties
     */
    private boolean filterProcessingType(Object element) {
        ProcessingType processing = (ProcessingType) element;
        String processingTypeMatch = processing.getId() + "$" + processing.toString() + "$ProcessingType";
        return measurementMatchesSearchText(processingTypeMatch, searchText);
    }

    /**
     * 
     * @param element
     *            a MeasuringPoint which is matched with the search text
     * @param treeViewer
     *            a treeviewer where the MeasuringPoint is in
     * @return true if the search text matches the MeasuringPoints properties
     */
    private boolean filterMeasuringPoint(Object element) {
        MeasuringPoint measuringPoint = (MeasuringPoint) element;
        String measuringPointMatch = measuringPoint.getStringRepresentation() + "$" + measuringPoint.toString()
                + "$MeasuringPoint";
        return measurementMatchesSearchText(measuringPointMatch, searchText);
    }

    /**
     * 
     * @param measurementMatch
     *            a string which represents a measurement which will be matched
     * @param searchText
     *            a search text which is input by the user
     * @return true if the measurementString matches with the searchText
     */
    private boolean measurementMatchesSearchText(String measurementMatch, String searchText) {
        return (measurementMatch.replace(" ", "").trim().toLowerCase().matches(searchText));
    }

    /**
     * @param filterActiveMonitors
     *            the filterActiveMonitors to set
     */
    public void setFilterActiveMonitors(boolean filterActiveMonitors) {
        this.filterActiveMonitors = filterActiveMonitors;
    }

    /**
     * @param filterInactiveMonitors
     *            the filterInactiveMonitors to set
     */
    public void setFilterInactiveMonitors(boolean filterInactiveMonitors) {
        this.filterInactiveMonitors = filterInactiveMonitors;
    }
}