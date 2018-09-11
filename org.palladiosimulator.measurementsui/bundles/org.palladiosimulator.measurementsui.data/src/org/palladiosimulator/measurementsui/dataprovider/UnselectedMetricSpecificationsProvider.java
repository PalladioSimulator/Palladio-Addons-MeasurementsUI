package org.palladiosimulator.measurementsui.dataprovider;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.monitorrepository.MonitorRepositoryPackage;

/**
 * This class creates and manages the Monitors used for the 3rd Wizard Page
 * 
 * @author Florian
 *
 */
public class UnselectedMetricSpecificationsProvider {

    /**
     * Creates a Monitor with all of the Metric Description <-> Measurement Specification pairs that
     * do not already exist in the passedMonitor needed for the left half of the 3rd Wizard Page
     * 
     * @param passedMonitor
     *            The Monitor that gets passed from the previous Wizard Page
     */
    public Monitor createMonitorWithMissingMetricSpecifications(Monitor passedMonitor) {
        EList<MeasurementSpecification> mSpecsOfPassedMonitor = passedMonitor.getMeasurementSpecifications();
        if (!mSpecsOfPassedMonitor.isEmpty()) {
            EList<MetricDescription> metricDescInPassedMonitor = new BasicEList<>();
            EList<MetricDescription> allMetricDescriptions = mSpecsOfPassedMonitor.get(0).getMetricDescription()
                    .getRepository().getMetricDescriptions();

            for (MeasurementSpecification aMSpec : mSpecsOfPassedMonitor) {
                metricDescInPassedMonitor.add(aMSpec.getMetricDescription());
            }

            EList<MetricDescription> nonMatchingMetricDesciptions = new BasicEList<>();

            findNonMatchingMetricDescriptions(metricDescInPassedMonitor, allMetricDescriptions,
                    nonMatchingMetricDesciptions);

            MonitorRepositoryFactory monFactory = MonitorRepositoryPackage.eINSTANCE.getMonitorRepositoryFactory();
            Monitor tempMon = monFactory.createMonitor();
            EList<MeasurementSpecification> mSpecList = new BasicEList<>();
            createMeasurementSpecificationsForEveryMetricDescription(nonMatchingMetricDesciptions, monFactory,
                    mSpecList);

            setMetricDescriptionForEveryMeasurementSpecification(nonMatchingMetricDesciptions, mSpecList);

            tempMon.eSet(tempMon.eClass().getEStructuralFeature("measurementSpecifications"), mSpecList);
            return tempMon;
        } else { // passedMonitor has no MeasurementSpecification
            return null;
        }

    }

    /**
     * Moves the selected MetricDescription from one Monitor to the other. Needed for implementation
     * of Left/Right Buttons in Wizard Page 3
     * 
     * @param selectedMetricDescription
     * @param sendingMonitor
     * @param receivingMonitor
     */
    public void moveMeasurementSpecification(MetricDescription selectedMetricDescription, Monitor sendingMonitor,
            Monitor receivingMonitor) {
        for (MeasurementSpecification mSpec : sendingMonitor.getMeasurementSpecifications()) {
            if (mSpec.getMetricDescription().equals(selectedMetricDescription)) {
                receivingMonitor.getMeasurementSpecifications().add(mSpec);
            }
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
        for (MetricDescription desc : nonMatchingMetricDesciptions) {
            mSpecList.add(monFactory.createMeasurementSpecification());
        }
    }

    /**
     * Finds the Metric Descriptions that are not in the passedMonitor
     * 
     * @param metricDescInPassedMonitor
     * @param allMetricDescriptions
     * @param nonMatchingMetricDesciptions
     */
    private void findNonMatchingMetricDescriptions(EList<MetricDescription> metricDescInPassedMonitor,
            EList<MetricDescription> allMetricDescriptions, EList<MetricDescription> nonMatchingMetricDesciptions) {
        for (MetricDescription allMetricDesc : allMetricDescriptions) {
            for (MetricDescription aMetricDesc : metricDescInPassedMonitor) {
                if (!allMetricDesc.getName().equals(aMetricDesc.getName())) {
                    nonMatchingMetricDesciptions.add(allMetricDesc);
                }
            }
        }
    }
}
