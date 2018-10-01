package org.palladiosimulator.measurementsui.extension.contributions;

import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.extensionpoint.definition.IMeasuringPointMetricsWorkingCombinations;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.constants.MetricDescriptionConstants;
import org.palladiosimulator.pcmmeasuringpoint.PcmmeasuringpointFactory;

public class StateOfPassiveResourceWithAssemblyPassiveResource implements IMeasuringPointMetricsWorkingCombinations {

    public StateOfPassiveResourceWithAssemblyPassiveResource() {
    }

    @Override
    public MeasuringPoint getMeasuringPoint() {
        return PcmmeasuringpointFactory.eINSTANCE.createAssemblyPassiveResourceMeasuringPoint();
      
    }

    @Override
    public MetricDescription getMetricDescription() {
        return MetricDescriptionConstants.STATE_OF_PASSIVE_RESOURCE_METRIC;
    }

    @Override
    public boolean addtoSuggestedList() {
       return true;
    }

}
