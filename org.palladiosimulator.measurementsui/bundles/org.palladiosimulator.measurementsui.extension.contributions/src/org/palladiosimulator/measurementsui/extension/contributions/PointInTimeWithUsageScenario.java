package org.palladiosimulator.measurementsui.extension.contributions;

import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.extensionpoint.definition.IMeasuringPointMetricsWorkingCombinations;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.constants.MetricDescriptionConstants;
import org.palladiosimulator.pcmmeasuringpoint.PcmmeasuringpointFactory;

public class PointInTimeWithUsageScenario implements IMeasuringPointMetricsWorkingCombinations {

    public PointInTimeWithUsageScenario() {
    }

    @Override
    public MeasuringPoint getMeasuringPoint() {
        return PcmmeasuringpointFactory.eINSTANCE.createUsageScenarioMeasuringPoint();
    }

    @Override
    public MetricDescription getMetricDescription() {
        return MetricDescriptionConstants.POINT_IN_TIME_METRIC;
    }

    @Override
    public boolean addtoSuggestedList() {
        return true;
    }

}
