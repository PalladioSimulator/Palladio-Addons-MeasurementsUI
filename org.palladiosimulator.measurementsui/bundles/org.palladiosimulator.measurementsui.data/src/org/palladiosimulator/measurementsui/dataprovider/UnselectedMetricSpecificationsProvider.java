package org.palladiosimulator.measurementsui.dataprovider;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.MetricSetDescription;
import org.palladiosimulator.metricspec.constants.MetricDescriptionConstants;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.monitorrepository.MonitorRepositoryPackage;

/**
 * This class creates and manages the Monitors used for the 3rd Wizard Page
 * 
 * @TODO: adapt moveMetricSpecificationBetweenMonitors to work with an array of selected Metric
 *        Descriptions, to allow moving multiple selected
 * @author Florian
 *
 */
public class UnselectedMetricSpecificationsProvider {

    private ResourceEditorImpl editor = new ResourceEditorImpl();

    /**
     * Creates a Monitor with all of the Metric Description <-> Measurement Specification pairs that
     * do not already exist in the passedMonitor needed for the left half of the 3rd Wizard Page
     * 
     * @param passedMonitor
     *            The Monitor that gets passed from the previous Wizard Page
     * @return the Monitor with the missing Metric Description<->Measurement Specification pairs
     */
    public Monitor createMonitorWithMissingMetricDescriptions(Monitor passedMonitor) {
        MonitorRepositoryFactory monFactory = MonitorRepositoryPackage.eINSTANCE.getMonitorRepositoryFactory();
        // Only way to get all Metric Descriptions from scratch
        MetricSetDescription dummyMetricDesc = MetricDescriptionConstants.COST_OVER_TIME;
        EList<MetricDescription> allMetricDescriptions = dummyMetricDesc.getRepository().getMetricDescriptions();

        EList<MeasurementSpecification> mSpecsOfPassedMonitor = passedMonitor.getMeasurementSpecifications();

        if (!mSpecsOfPassedMonitor.isEmpty()) {
            mSpecsOfPassedMonitor = passedMonitor.getMeasurementSpecifications();
            EList<MetricDescription> metricDescriptionsInPassedMonitor = new BasicEList<>();

            for (MeasurementSpecification aMSpec : mSpecsOfPassedMonitor) {
                metricDescriptionsInPassedMonitor.add(aMSpec.getMetricDescription());
            }
            EList<MetricDescription> nonMatchingMetricDesciptions = new BasicEList<>();

            findNonMatchingMetricDescriptions(metricDescriptionsInPassedMonitor, allMetricDescriptions,
                    nonMatchingMetricDesciptions);

            return createMonitorWithMissingDescriptions(monFactory, nonMatchingMetricDesciptions);

        } else {
            return createMonitorWithMissingDescriptions(monFactory, allMetricDescriptions);
        }

    }

    /**
     * Creates a Monitor with all MetricDesc<->MeasurementSpec pairs for every MetricDesc from the
     * listOfMetricDescriptions
     * 
     * @param monFactory
     * @param listOfMetricDescriptions
     * @return
     */
    private Monitor createMonitorWithMissingDescriptions(MonitorRepositoryFactory monFactory,
            EList<MetricDescription> listOfMetricDescriptions) {
        Monitor tempMon = monFactory.createMonitor();
        EList<MeasurementSpecification> mSpecList = new BasicEList<>();
        createMeasurementSpecificationsForEveryMetricDescription(listOfMetricDescriptions, monFactory, mSpecList);
        setMetricDescriptionForEveryMeasurementSpecification(listOfMetricDescriptions, mSpecList);
        for (MeasurementSpecification aMSpec : mSpecList) {
            // has to be adapted once the 4th screen is implemented
            aMSpec.setProcessingType(MonitorRepositoryFactory.eINSTANCE.createFeedThrough());
        }
        tempMon.eSet(tempMon.eClass().getEStructuralFeature("measurementSpecifications"), mSpecList);
        return tempMon;
    }

    /**
     * Moves the selected Measurement Specification from one Monitor to the other. Needed for
     * implementation of Left/Right Buttons in Wizard Page 3
     * 
     * @param selectedMetricDescription
     * @param sendingMonitor
     * @param receivingMonitor
     */
    public void moveMeasurementSpecificationsBetweenMonitors(MeasurementSpecification selectedMeasurementSpecification,
            Monitor receivingMonitor, boolean isInEditMode) {
        if (isInEditMode) {
            editor.addMeasurementSpecificationToMonitor(receivingMonitor, selectedMeasurementSpecification);
        } else {
            receivingMonitor.getMeasurementSpecifications().add(selectedMeasurementSpecification);
        }
    }

    /**
     * Removes the measurement specification from the receiving monitor. If in edit mode the
     * resource needs to be deleted through emf command to avoid write transaction errors
     * 
     * @param selectedMeasurementSpecification
     * @param receivingMonitor
     * @param isInEditMode
     */
    public void removeMeasurementSpecificationBetweenMonitors(MeasurementSpecification selectedMeasurementSpecification,
            Monitor receivingMonitor, boolean isInEditMode) {
        if (isInEditMode) {

            editor.deleteResource(selectedMeasurementSpecification);
            receivingMonitor.getMeasurementSpecifications().add(selectedMeasurementSpecification);
        } else {
            receivingMonitor.getMeasurementSpecifications().add(selectedMeasurementSpecification);
        }
    }

    /**
     * Moves all Measurement Specifications from one Monitor to another. Needed for the double Arrow
     * (move All) in the 3rd Wizard Page
     * 
     * @param sendingMonitor
     * @param receivingMonitor
     */
    public void moveAllMeasurementSpecificationsBetweenMonitors(Monitor sendingMonitor, Monitor receivingMonitor,
            boolean isInEditMode) {
        if (isInEditMode) {
            for (MeasurementSpecification aMSpec : sendingMonitor.getMeasurementSpecifications()) {
                editor.addMeasurementSpecificationToMonitor(receivingMonitor, aMSpec);
            }
        } else {

            receivingMonitor.getMeasurementSpecifications().addAll(sendingMonitor.getMeasurementSpecifications());
        }

    }

    /**
     * Sets a Metric Description for every Measurement Specification
     * 
     * @param nonMatchingMetricDesciptions
     * @param mSpecList
     */
    private void setMetricDescriptionForEveryMeasurementSpecification(
            EList<MetricDescription> nonMatchingMetricDesciptions, EList<MeasurementSpecification> mSpecList) {
        for (int i = 0; i < mSpecList.size(); i++) {
            mSpecList.get(i).setMetricDescription(nonMatchingMetricDesciptions.get(i));
        }
    }

    /**
     * Create a MSpec for every Metric Description
     * 
     * @param nonMatchingMetricDesciptions
     * @param monFactory
     * @param mSpecList
     */
    private void createMeasurementSpecificationsForEveryMetricDescription(
            EList<MetricDescription> nonMatchingMetricDesciptions, MonitorRepositoryFactory monFactory,
            EList<MeasurementSpecification> mSpecList) {
        for (int i = 0; i < nonMatchingMetricDesciptions.size(); i++) {
            mSpecList.add(monFactory.createMeasurementSpecification());
        }
    }

    /**
     * Finds the Metric Descriptions that are not in the passedMonitor
     * 
     * @param metricDescInPassedMonitor
     * @param allMetricDescriptions
     * @param nonMatchingMetricDesciptions
     * @return
     */
    private EList<MetricDescription> findNonMatchingMetricDescriptions(
            EList<MetricDescription> metricDescInPassedMonitor, EList<MetricDescription> allMetricDescriptions,
            EList<MetricDescription> nonMatchingMetricDesciptions) {
        for (MetricDescription allMetricDesc : allMetricDescriptions) {
            boolean nonMatching = true;
            for (MetricDescription aMetricDesc : metricDescInPassedMonitor) {
                if (allMetricDesc.getName().equals(aMetricDesc.getName())) {
                    nonMatching = false;
                    break;
                }
            }
            if (nonMatching) {
                nonMatchingMetricDesciptions.add(allMetricDesc);
            }

        }
        return nonMatchingMetricDesciptions;
    }
}
