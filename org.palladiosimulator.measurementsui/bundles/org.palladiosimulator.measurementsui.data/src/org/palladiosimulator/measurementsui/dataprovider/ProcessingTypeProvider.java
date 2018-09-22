package org.palladiosimulator.measurementsui.dataprovider;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.monitorrepository.MonitorRepositoryPackage;
import org.palladiosimulator.monitorrepository.ProcessingType;
import org.palladiosimulator.monitorrepository.impl.FeedThroughImpl;
import org.palladiosimulator.monitorrepository.impl.FixedSizeAggregationImpl;
import org.palladiosimulator.monitorrepository.impl.TimeDrivenAggregationImpl;
import org.palladiosimulator.monitorrepository.impl.TimeDrivenImpl;
import org.palladiosimulator.monitorrepository.impl.VariableSizeAggregationImpl;

public class ProcessingTypeProvider {

    private static final String EDIT_MODE_NOT_YET_SUPPORTERD = "Edit Mode not yet supporterd";
    MonitorRepositoryFactory monFactory = MonitorRepositoryPackage.eINSTANCE.getMonitorRepositoryFactory();

    public String[] provideBasicProcessingTypes() {
        EList<EObject> listOfProcessingTypes = new BasicEList<>();
        listOfProcessingTypes.add(monFactory.createFeedThrough());
        listOfProcessingTypes.add(monFactory.createFixedSizeAggregation());
        listOfProcessingTypes.add(monFactory.createTimeDriven());
        listOfProcessingTypes.add(monFactory.createTimeDrivenAggregation());
        listOfProcessingTypes.add(monFactory.createVariableSizeAggregation());
        String[] allProcessingTypesArray = new String[5];

        for (EObject aProcessingType : listOfProcessingTypes) {
            if (aProcessingType instanceof FeedThroughImpl) {
                allProcessingTypesArray[0] = "FeedThrough";

            } else if (aProcessingType instanceof FixedSizeAggregationImpl) {
                allProcessingTypesArray[1] = "FixedSizeAggregation";

            } else if (aProcessingType instanceof TimeDrivenImpl) {
                if (aProcessingType instanceof TimeDrivenAggregationImpl) {
                    allProcessingTypesArray[3] = "TimeDrivenAggregation";

                } else {
                    allProcessingTypesArray[2] = "TimeDriven";
                }

            } else if (aProcessingType instanceof VariableSizeAggregationImpl) {
                allProcessingTypesArray[4] = "VariableSizeAggregation";

            }

        }
        return allProcessingTypesArray;
    }

    public List<String> provideProcessingTypeProperties(String processingTypeString) {
        LinkedList<String> propertiesForProcessingType = new LinkedList<>();
        switch (processingTypeString) {
        case "FeedThrough":
            break;
        case "FixedSizeAggregation":
            propertiesForProcessingType.add("Frequency");
            propertiesForProcessingType.add("Number of Measurements");
            break;
        case "TimeDriven":
        case "TimeDrivenAggregation":
            propertiesForProcessingType.add("Window Increment");
            propertiesForProcessingType.add("Window Length");
            break;
        case "VariableSizeAggregation":
            propertiesForProcessingType.add("Frequency");
            propertiesForProcessingType.add("Retrospection Length");
            break;
        }
        return propertiesForProcessingType;
    }

    public void assignProcessingTypeToMeasurementSpecification(MeasurementSpecification measurementSpecification,
            String selectedProcessingType, boolean isEditing) {

        switch (selectedProcessingType) {
        case "FeedThrough":
            if (isEditing) {
                System.err.println(EDIT_MODE_NOT_YET_SUPPORTERD);
            } else {
                measurementSpecification.setProcessingType(monFactory.createFeedThrough());
            }

            break;
        case "FixedSizeAggregation":
            if (isEditing) {
                System.err.println(EDIT_MODE_NOT_YET_SUPPORTERD);
            } else {
                measurementSpecification.setProcessingType(monFactory.createFixedSizeAggregation());
            }
            break;
        case "TimeDriven":
            if (isEditing) {
                System.err.println(EDIT_MODE_NOT_YET_SUPPORTERD);
            } else {
                measurementSpecification.setProcessingType(monFactory.createTimeDriven());
            }
            break;
        case "TimeDrivenAggregation":
            if (isEditing) {
                System.err.println(EDIT_MODE_NOT_YET_SUPPORTERD);
            } else {
                measurementSpecification.setProcessingType(monFactory.createTimeDrivenAggregation());
            }
            break;
        case "VariableSizeAggregation":
            if (isEditing) {
                System.err.println(EDIT_MODE_NOT_YET_SUPPORTERD);
            } else {
                measurementSpecification.setProcessingType(monFactory.createVariableSizeAggregation());
            }
            break;
        default:
            System.err.println("You done fucked up boy");
        }

    }

    public void setProcessingTypeAttributes(MeasurementSpecification measurementSpecification, List<Double> values,
            boolean isEditing) {
        ProcessingType processingType = measurementSpecification.getProcessingType();
        if (processingType instanceof FeedThroughImpl) {

        } else if (processingType instanceof FixedSizeAggregationImpl) {
            if (isEditing) {

            } else {
                ((FixedSizeAggregationImpl) processingType).setFrequency(values.get(0).intValue());
                ((FixedSizeAggregationImpl) processingType).setNumberOfMeasurements(values.get(1).intValue());
            }

        } else if (processingType instanceof TimeDrivenImpl) { // works for both Time Driven and
                                                               // TimeDrivenAggregation, since
                                                               // Aggregation is child of TimeDriven
                                                               // and both require the same values
                                                               // to be set
            if (isEditing) {

            } else {
                ((TimeDrivenImpl) processingType).setWindowIncrement(values.get(0));
                ((TimeDrivenImpl) processingType).setWindowLength(values.get(1));
            }

        } else if (processingType instanceof VariableSizeAggregationImpl) {
            if (isEditing) {

            } else {
                ((VariableSizeAggregationImpl) processingType).setFrequency(values.get(0).intValue());
                ((VariableSizeAggregationImpl) processingType).setRetrospectionLength(values.get(1).intValue());
            }

        }

    }

    public void setAProcessingTypeAttribute(MeasurementSpecification measurementSpecification,
            String processingTypeProperty, Double value, boolean isEditing) {
        ProcessingType processingType = measurementSpecification.getProcessingType();
        if (processingType instanceof FixedSizeAggregationImpl) {
            if (isEditing) {

            } else {
                if (processingTypeProperty == "Frequency") {
                    ((FixedSizeAggregationImpl) processingType).setFrequency(value.intValue());
                }
                if (processingTypeProperty == "Number of Measurements") {
                    ((FixedSizeAggregationImpl) processingType).setNumberOfMeasurements(value.intValue());
                }
            }
        }

    }
}
