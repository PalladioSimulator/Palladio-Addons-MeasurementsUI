package org.palladiosimulator.measurementsui.dataprovider;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
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

    private static final String RETROSPECTION_LENGTH = "Retrospection Length";
    private static final String WINDOW_LENGTH = "Window Length";
    private static final String WINDOW_INCREMENT = "Window Increment";
    private static final String NUMBER_OF_MEASUREMENTS = "Number of Measurements";
    private static final String FREQUENCY = "Frequency";
    private static final String VARIABLE_SIZE_AGGREGATION = "VariableSizeAggregation";
    private static final String TIME_DRIVEN = "TimeDriven";
    private static final String TIME_DRIVEN_AGGREGATION = "TimeDrivenAggregation";
    private static final String FIXED_SIZE_AGGREGATION = "FixedSizeAggregation";
    private static final String FEED_THROUGH = "FeedThrough";
    private MonitorRepositoryFactory monFactory = MonitorRepositoryPackage.eINSTANCE.getMonitorRepositoryFactory();
    private ResourceEditorImpl editor = ResourceEditorImpl.getInstance();

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
                allProcessingTypesArray[0] = FEED_THROUGH;

            } else if (aProcessingType instanceof FixedSizeAggregationImpl) {
                allProcessingTypesArray[1] = FIXED_SIZE_AGGREGATION;

            } else if (aProcessingType instanceof TimeDrivenImpl) {
                if (aProcessingType instanceof TimeDrivenAggregationImpl) {
                    allProcessingTypesArray[3] = TIME_DRIVEN_AGGREGATION;

                } else {
                    allProcessingTypesArray[2] = TIME_DRIVEN;
                }

            } else if (aProcessingType instanceof VariableSizeAggregationImpl) {
                allProcessingTypesArray[4] = VARIABLE_SIZE_AGGREGATION;

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
        case FEED_THROUGH:
            break;
        case FIXED_SIZE_AGGREGATION:
            propertiesForProcessingType.add(FREQUENCY);
            propertiesForProcessingType.add(NUMBER_OF_MEASUREMENTS);
            break;
        case TIME_DRIVEN:
        case TIME_DRIVEN_AGGREGATION:
            propertiesForProcessingType.add(WINDOW_INCREMENT);
            propertiesForProcessingType.add(WINDOW_LENGTH);
            break;
        case VARIABLE_SIZE_AGGREGATION:
            propertiesForProcessingType.add(FREQUENCY);
            propertiesForProcessingType.add(RETROSPECTION_LENGTH);
            break;
        default:
            break;
        }
        return propertiesForProcessingType;
    }

    /**
     * Sets the PT to the selected Measurement Specification. Uses EMF SetCommand during edit mode.
     * 
     * @param measurementSpecification
     * @param selectedProcessingType
     * @param isEditing
     */
    public void assignProcessingTypeToMeasurementSpecification(MeasurementSpecification measurementSpecification,
            String selectedProcessingType, boolean isEditing) {

        switch (selectedProcessingType) {
        case FEED_THROUGH:
            if (isEditing) {
                editor.setProcessingType(measurementSpecification, monFactory.createFeedThrough());
            } else {
                measurementSpecification.setProcessingType(monFactory.createFeedThrough());
            }

            break;
        case FIXED_SIZE_AGGREGATION:
            if (isEditing) {
                editor.setProcessingType(measurementSpecification, monFactory.createFixedSizeAggregation());
            } else {
                measurementSpecification.setProcessingType(monFactory.createFixedSizeAggregation());
            }
            break;
        case TIME_DRIVEN:
            if (isEditing) {
                editor.setProcessingType(measurementSpecification, monFactory.createTimeDriven());
            } else {
                measurementSpecification.setProcessingType(monFactory.createTimeDriven());
            }
            break;
        case TIME_DRIVEN_AGGREGATION:
            if (isEditing) {
                editor.setProcessingType(measurementSpecification, monFactory.createTimeDrivenAggregation());
            } else {
                measurementSpecification.setProcessingType(monFactory.createTimeDrivenAggregation());
            }
            break;
        case VARIABLE_SIZE_AGGREGATION:
            if (isEditing) {
                editor.setProcessingType(measurementSpecification, monFactory.createVariableSizeAggregation());
            } else {
                measurementSpecification.setProcessingType(monFactory.createVariableSizeAggregation());
            }
            break;
        default:
            break;
        }

    }

    /**
     * Sets a single PT attribute.
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
            setFixedSizeAggregation(processingTypeProperty, value, isEditing, processingType);
        } else if (processingType instanceof TimeDrivenImpl) {
            setTimeDriven(processingTypeProperty, value, isEditing, processingType);
        } else if (processingType instanceof VariableSizeAggregationImpl) {
            setVariableSizeAggregation(processingTypeProperty, value, isEditing, processingType);
        }
    }

    /**
     * Setter method for the variableSizeAggregation Processing Type. Uses EMF Commands in Edit
     * Mode.
     * 
     * @param processingTypeProperty
     * @param value
     * @param isEditing
     * @param processingType
     */
    private void setVariableSizeAggregation(String processingTypeProperty, Double value, boolean isEditing,
            ProcessingType processingType) {
        if (isEditing) {
            if (processingTypeProperty.equals(FREQUENCY)) {
                editor.setAProcessingTypeAttribute((VariableSizeAggregationImpl) processingType, "frequency",
                        value.intValue());
            }

            if (processingTypeProperty.equals(RETROSPECTION_LENGTH)) {
                editor.setAProcessingTypeAttribute((VariableSizeAggregationImpl) processingType, "retrospectionLength",
                        value);
            }
        } else {
            if (processingTypeProperty.equals(FREQUENCY)) {
                ((VariableSizeAggregationImpl) processingType).setFrequency(value.intValue());
            }

            if (processingTypeProperty.equals(RETROSPECTION_LENGTH)) {
                ((VariableSizeAggregationImpl) processingType).setRetrospectionLength(value);
            }

        }
    }

    /**
     * Setter method for the TimeDriven and TimeDrivenAggregation Processing Type. Uses EMF Commands
     * in Edit Mode.
     * 
     * @param processingTypeProperty
     * @param value
     * @param isEditing
     * @param processingType
     */
    private void setTimeDriven(String processingTypeProperty, Double value, boolean isEditing,
            ProcessingType processingType) {
        if (isEditing) {
            if (processingTypeProperty.equals(WINDOW_INCREMENT)) {
                editor.setAProcessingTypeAttribute((TimeDrivenImpl) processingType, "windowIncrement", value);
            }
            if (processingTypeProperty.equals(WINDOW_LENGTH)) {
                editor.setAProcessingTypeAttribute((TimeDrivenImpl) processingType, "windowLength", value);
            }
        } else {
            if (processingTypeProperty.equals(WINDOW_INCREMENT)) {
                ((TimeDrivenImpl) processingType).setWindowIncrement(value);
            }

            if (processingTypeProperty.equals(WINDOW_LENGTH)) {
                ((TimeDrivenImpl) processingType).setWindowLength(value);
            }

        }
    }

    /**
     * Setter method for the fixedSizeAggregation Processing Type. Uses EMF Commands in Edit Mode.
     * 
     * @param processingTypeProperty
     * @param value
     * @param isEditing
     * @param processingType
     */
    private void setFixedSizeAggregation(String processingTypeProperty, Double value, boolean isEditing,
            ProcessingType processingType) {
        if (isEditing) {
            if (processingTypeProperty.equals(FREQUENCY)) {
                editor.setAProcessingTypeAttribute((FixedSizeAggregationImpl) processingType, "frequency",
                        value.intValue());
            }
            if (processingTypeProperty.equals(NUMBER_OF_MEASUREMENTS)) {
                editor.setAProcessingTypeAttribute((FixedSizeAggregationImpl) processingType, "numberOfMeasurements",
                        value.intValue());
            }
        } else {
            if (processingTypeProperty.equals(FREQUENCY)) {
                ((FixedSizeAggregationImpl) processingType).setFrequency(value.intValue());
            }

            if (processingTypeProperty.equals(NUMBER_OF_MEASUREMENTS)) {
                ((FixedSizeAggregationImpl) processingType).setNumberOfMeasurements(value.intValue());
            }

        }
    }

    /**
     * returns the value of a selected PT attribute of a selected Measurement Specification.
     * 
     * @param measurementSpecification
     * @param processingTypeProperty
     * @return Either a double or int depending on the type of attribute.
     */
    public Number getAProcessingType(MeasurementSpecification measurementSpecification, String processingTypeProperty) {
        ProcessingType processingType = measurementSpecification.getProcessingType();
        if (processingType instanceof FixedSizeAggregationImpl) {

            if (processingTypeProperty.equals(FREQUENCY)) {
                return ((FixedSizeAggregationImpl) processingType).getFrequency();
            }
            if (processingTypeProperty.equals(NUMBER_OF_MEASUREMENTS)) {
                return ((FixedSizeAggregationImpl) processingType).getNumberOfMeasurements();
            }

        } else if (processingType instanceof TimeDrivenImpl) {

            if (processingTypeProperty.equals(WINDOW_INCREMENT)) {
                return ((TimeDrivenImpl) processingType).getWindowIncrement();
            }

            if (processingTypeProperty.equals(WINDOW_LENGTH)) {
                return ((TimeDrivenImpl) processingType).getWindowLength();
            }

        } else if (processingType instanceof VariableSizeAggregationImpl) {
            if (processingTypeProperty.equals(FREQUENCY)) {
                return ((VariableSizeAggregationImpl) processingType).getFrequency();
            }

            if (processingTypeProperty.equals(RETROSPECTION_LENGTH)) {
                return ((VariableSizeAggregationImpl) processingType).getRetrospectionLength();
            }

        }
        return null;
    }

    /**
     * Returns the correct name of a given ProcessingType.
     * 
     * @param aProcessingType
     *            the given ProcessingType
     * @return the correct name of a given ProcessingType
     */
    public String getProcessingTypeString(ProcessingType aProcessingType) {
        String result;
        if (aProcessingType instanceof FeedThroughImpl) {
            result = FEED_THROUGH;

        } else if (aProcessingType instanceof FixedSizeAggregationImpl) {
            result = FIXED_SIZE_AGGREGATION;

        } else if (aProcessingType instanceof TimeDrivenImpl) {
            if (aProcessingType instanceof TimeDrivenAggregationImpl) {
                result = TIME_DRIVEN_AGGREGATION;

            } else {
                result = TIME_DRIVEN;
            }

        } else if (aProcessingType instanceof VariableSizeAggregationImpl) {
            result = VARIABLE_SIZE_AGGREGATION;
        } else {
            throw new IllegalArgumentException();
        }
        return result;
    }
}
