package org.palladiosimulator.measurementsui.extension.contributions;

import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.extensionpoint.definition.IMeasuringPointMetricsWorkingCombinations;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.constants.MetricDescriptionConstants;
import org.palladiosimulator.pcmmeasuringpoint.PcmmeasuringpointFactory;

/**
 * This class is used as an example of an extension to the MeasuringPointMetricsWorkingCombinations
 * ExtensionPoint which defines, that the ResponseTime metric works with
 * a UsageScenarioMeasuringPoint, and it should also be suggested.
 * @author Lasse
 *
 */
public class ResponseTimeWithUsageScenarioMeasuringPoint implements IMeasuringPointMetricsWorkingCombinations {


    @Override
    public MetricDescription getMetricDescription() {
       return MetricDescriptionConstants.RESPONSE_TIME_METRIC;
        
    }

    @Override
    public MeasuringPoint getMeasuringPoint() {
       return PcmmeasuringpointFactory.eINSTANCE.createUsageScenarioMeasuringPoint();
      
    }

    @Override
    public boolean addtoSuggestedList() {
        return true;
    }

}
