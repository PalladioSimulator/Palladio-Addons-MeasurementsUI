package org.palladiosimulator.measurementsui.extensionpoint.definition;

import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.metricspec.MetricDescription;

/**
 * This Interface is used as Extension Point to define which Metric Descriptions
 * actually lead to a working simulation run in combination
 * with which Measuring Points.
 * Extensions have to implement this Interface to provide these working pairs.
 * (e.g Response_Time with UsageScenarioMeasuringPoint)
 * 
 * @author Lasse
 *
 */
public interface IMeasuringPointMetricsWorkingCombinations {
    
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
