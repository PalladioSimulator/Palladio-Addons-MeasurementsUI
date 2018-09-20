package org.palladiosimulator.measurementsui.extensionpoint.definition;

import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.metricspec.MetricDescription;

public interface IPossibleMetricDescription {
    
    
    MeasuringPoint getMeasuringPoint();
    MetricDescription getMetricDescription();
    boolean addtoSuggestedList();

}
