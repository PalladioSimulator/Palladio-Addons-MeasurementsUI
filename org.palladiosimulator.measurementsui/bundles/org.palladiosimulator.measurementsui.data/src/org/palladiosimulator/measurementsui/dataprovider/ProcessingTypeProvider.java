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

/**
 * Provides the required methods to show and modify data in the 4th page.
 * 
 * @author Florian Nieuwenhuizen
 *
 */
public class ProcessingTypeProvider {

    private static final String EDIT_MODE_NOT_YET_SUPPORTERD = "Edit Mode not yet supporterd";
    private MonitorRepositoryFactory monFactory = MonitorRepositoryPackage.eINSTANCE.getMonitorRepositoryFactory();

    /**
     * The five Processing types the wizard will support. Sadly PCM does not provide a class
     * containing all Processing Types, meaning new Processing Types would need to be added
     * manually.
     * 
     * @return a String Array containing the names of all supported Processing Types. Needed in this
     *         format for drop-down in 4th page.
     */
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

    /**
     * Provides the properties for each Processing Type(PT) in String Form.
     * 
     * @param processingTypeString
     *            The PT to provide the properties for.
     * @return String List of the property types.
     */
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
        default:
            break;
        }
        return propertiesForProcessingType;
    }

    /**
     * Sets the PT to the selected Measurement Specification. TODO: EMF Commands for Edit Mode.
     * 
     * @param measurementSpecification
     * @param selectedProcessingType
     * @param isEditing
     */
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

    /**
     * Sets the PT attributes. TODO: EMF Commands for Edit Mode.
     * 
     * @param measurementSpecification
     * @param values
     * @param isEditing
     */
    public void setProcessingTypeAttributes(MeasurementSpecification measurementSpecification, List<Double> values,
            boolean isEditing) {
        ProcessingType processingType = measurementSpecification.getProcessingType();
        if (processingType instanceof FixedSizeAggregationImpl) {
            if (isEditing) {
                // TODO: Edit Mode
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
                // TODO: Edit Mode
            } else {
                ((TimeDrivenImpl) processingType).setWindowIncrement(values.get(0));
                ((TimeDrivenImpl) processingType).setWindowLength(values.get(1));
            }

        } else if (processingType instanceof VariableSizeAggregationImpl) {
            if (isEditing) {
                // TODO: Edit Mode
            } else {
                ((VariableSizeAggregationImpl) processingType).setFrequency(values.get(0).intValue());
                ((VariableSizeAggregationImpl) processingType).setRetrospectionLength(values.get(1).intValue());
            }

        }

    }

    /**
     * Sets a single PT attribute. TODO: EMF Commands for Edit Mode.
     * 
     * @param measurementSpecification
     * @param processingTypeProperty
     * @param value
     * @param isEditing
     */
    public void setAProcessingTypeAttribute(MeasurementSpecification measurementSpecification,
            String processingTypeProperty, Double value, boolean isEditing) {
        ProcessingType processingType = measurementSpecification.getProcessingType();
        if (processingType instanceof FixedSizeAggregationImpl) {
            if (isEditing) {
                // TODO: Edit Mode
            } else {
                if (processingTypeProperty.equals("Frequency")) {
                    ((FixedSizeAggregationImpl) processingType).setFrequency(value.intValue());
                }

                if (processingTypeProperty.equals("Number of Measurements")) {
                    ((FixedSizeAggregationImpl) processingType).setNumberOfMeasurements(value.intValue());
                }

            }
        } else if (processingType instanceof TimeDrivenImpl) {
            if (isEditing) {
                // TODO: Edit Mode
            } else {
                if (processingTypeProperty.equals("Window Increment")) {
                    ((TimeDrivenImpl) processingType).setWindowIncrement(value);
                }

                if (processingTypeProperty.equals("Window Length")) {
                    ((TimeDrivenImpl) processingType).setWindowLength(value);
                }

            }

        } else if (processingType instanceof VariableSizeAggregationImpl) {
            if (isEditing) {
                // TODO: Edit Mode
            } else {
                if (processingTypeProperty.equals("Frequency")) {
                    ((VariableSizeAggregationImpl) processingType).setFrequency(value.intValue());
                }

                if (processingTypeProperty.equals("Retrospection Length")) {
                    ((VariableSizeAggregationImpl) processingType).setRetrospectionLength(value.intValue());
                }

            }

        }
    }
}
