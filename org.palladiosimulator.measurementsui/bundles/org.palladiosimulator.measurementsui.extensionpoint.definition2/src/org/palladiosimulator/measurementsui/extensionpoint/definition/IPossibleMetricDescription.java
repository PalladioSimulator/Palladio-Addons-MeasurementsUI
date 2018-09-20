package org.palladiosimulator.measurementsui.extensionpoint.definition;

import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.metricspec.MetricDescription;

public interface IPossibleMetricDescription {
    
    /**
     * The Measuring Point with whom the Metric Description
     * can be simulated.
     * 
     * @return MeasuringPoint
     */
    MeasuringPoint getMeasuringPoint();
    
    /**
     * The Metric Description that can be simulated
     * when associated with this Measuring Point
     * 
     * @return MetricDescription
     */
    MetricDescription getMetricDescription();
    
    /**
     * Should this Metric Description be added to the list
     * of suggested Metrics for this Measuring Point
     * 
     * @return boolean
     */
    boolean addtoSuggestedList();

}
