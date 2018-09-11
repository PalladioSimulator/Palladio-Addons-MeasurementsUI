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
 * @author Florian
 *
 */
public class UnselectedMetricSpecificationsProvider {

    /**
     * Creates a Monitor with all of the Metric Description <-> Measurement Specification pairs that
     * do not already exist in the passedMonitor Needed for the Left half of the 3rd Wizard Page
     * 
     * @param passedMonitor
     *            The Monitor that gets passed from the previous Wizard Page
     */
    public void createMonitorWithMissingMetricSpecifications(Monitor passedMonitor) {
        EList<MeasurementSpecification> mSpecsOfPassedMonitor = passedMonitor.getMeasurementSpecifications();
        if (!mSpecsOfPassedMonitor.isEmpty()) {
            EList<MetricDescription> metricDescInPassedMonitor = new BasicEList<MetricDescription>();
            EList<MetricDescription> allMetricDescriptions = mSpecsOfPassedMonitor.get(0).getMetricDescription()
                    .getRepository().getMetricDescriptions();

            for (MeasurementSpecification aMSpec : mSpecsOfPassedMonitor) {
                metricDescInPassedMonitor.add(aMSpec.getMetricDescription());
            }

            EList<MetricDescription> nonMatchingMetricDesciptions = new BasicEList<MetricDescription>();

            for (MetricDescription allMetricDesc : allMetricDescriptions) {
                for (MetricDescription aMetricDesc : metricDescInPassedMonitor) {
                    if (!allMetricDesc.getName().equals(aMetricDesc.getName())) {
                        nonMatchingMetricDesciptions.add(allMetricDesc);
                    }
                }
            }

            MonitorRepositoryFactory monFactory = MonitorRepositoryPackage.eINSTANCE.getMonitorRepositoryFactory();
            Monitor tempMon = monFactory.createMonitor();
            EList<MeasurementSpecification> mSpecList = new BasicEList<MeasurementSpecification>();
            for (MetricDescription desc : nonMatchingMetricDesciptions) {
                // create a MSpec for every Metric Description
                mSpecList.add(monFactory.createMeasurementSpecification());
            }

            for (int i = 0; i < mSpecList.size(); i++) {
                // sets a Metric Description for every Measurement Specification
                mSpecList.get(i).setMetricDescription(nonMatchingMetricDesciptions.get(i));
            }

            tempMon.eSet(tempMon.eClass().getEStructuralFeature("measurementSpecifications"), mSpecList);

        }
    }
}
