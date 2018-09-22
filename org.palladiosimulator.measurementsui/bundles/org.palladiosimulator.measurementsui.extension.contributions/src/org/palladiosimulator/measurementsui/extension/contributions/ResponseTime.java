package org.palladiosimulator.measurementsui.extension.contributions;

import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointFactory;
import org.palladiosimulator.measurementsui.extensionpoint.definition.IPossibleMetricDescription;
import org.palladiosimulator.metricspec.BaseMetricDescription;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.MetricSetDescription;
import org.palladiosimulator.metricspec.MetricSpecFactory;
import org.palladiosimulator.metricspec.constants.MetricDescriptionConstants;
import org.palladiosimulator.pcmmeasuringpoint.PcmmeasuringpointFactory;


public class ResponseTime implements IPossibleMetricDescription {

    public ResponseTime() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public MetricDescription getMetricDescription() {
        BaseMetricDescription dummyMetricDesc = MetricDescriptionConstants.RESPONSE_TIME_METRIC;
       return dummyMetricDesc;
        
    }

    @Override
    public MeasuringPoint getMeasuringPoint() {
       return PcmmeasuringpointFactory.eINSTANCE.createUsageScenarioMeasuringPoint();
      
    }

    @Override
    public boolean addtoSuggestedList() {
       
        return false;
    }

}
