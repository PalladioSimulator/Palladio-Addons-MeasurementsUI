package org.palladiosimulator.measurementsui.wizardmain.handlers;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.ProcessingType;
import org.palladiosimulator.monitorrepository.impl.FeedThroughImpl;
import org.palladiosimulator.monitorrepository.impl.FixedSizeAggregationImpl;
import org.palladiosimulator.monitorrepository.impl.TimeDrivenAggregationImpl;
import org.palladiosimulator.monitorrepository.impl.TimeDrivenImpl;
import org.palladiosimulator.monitorrepository.impl.VariableSizeAggregationImpl;

public class MeasurementSpecificationLabelProvider implements ITableLabelProvider {
    
    /**
     * This handles the internal model.
     */
    private ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel;
    
    public MeasurementSpecificationLabelProvider(ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel) {
        this.processingTypeSelectionWizardModel = processingTypeSelectionWizardModel;
    }

    public void removeListener(ILabelProviderListener listener) {
        // not used
    }

    public void addListener(ILabelProviderListener listener) {
        // not used
    }

    public void dispose() {
        // not used
    }

    public boolean isLabelProperty(Object element, String property) {
        return false;
    }
    
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    public String getColumnText(Object element, int columnIndex) {
        String result = "";
        MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
        if (columnIndex == 0) {
            result = measurementSpecification.getMetricDescription().getName();
        } else if (columnIndex == 1) {
            result = getProcessingTypeString(measurementSpecification.getProcessingType());
        } else if (columnIndex == 2) {
            result = getLabelProperty1(result, measurementSpecification);
        } else if (columnIndex == 3) {
            result = getLabelProperty2(result, measurementSpecification);
        }
        return result;
    }

    private String getLabelProperty1(String result, MeasurementSpecification measurementSpecification) {
        ProcessingType selectedProcessingType = measurementSpecification.getProcessingType();
        String selectedProcessingTypeString = getProcessingTypeString(selectedProcessingType);
    
        List<String> processingTypeProperties = processingTypeSelectionWizardModel
                .fieldsForThisProcessingType(selectedProcessingTypeString);
        if (!processingTypeProperties.isEmpty()) {
            result += processingTypeProperties.get(0) + ": ";
            
            if (selectedProcessingType instanceof FixedSizeAggregationImpl) {
                result += ((FixedSizeAggregationImpl) selectedProcessingType).getFrequency();
    
            } else if (selectedProcessingType instanceof TimeDrivenImpl) {
                result += ((TimeDrivenImpl) selectedProcessingType).getWindowIncrement();
    
            } else if (selectedProcessingType instanceof VariableSizeAggregationImpl) {
                result += ((VariableSizeAggregationImpl) selectedProcessingType).getFrequency();
            }
        } else {
            result += "-";
        }
        return result;
    }

    private String getLabelProperty2(String result, MeasurementSpecification measurementSpecification) {
        ProcessingType selectedProcessingType = measurementSpecification.getProcessingType();
        String selectedProcessingTypeString = getProcessingTypeString(selectedProcessingType);
    
        List<String> processingTypeProperties = processingTypeSelectionWizardModel
                .fieldsForThisProcessingType(selectedProcessingTypeString);
        if (processingTypeProperties.size() > 1) {
            result += processingTypeProperties.get(1) + ": ";
            
            if (selectedProcessingType instanceof FixedSizeAggregationImpl) {
                result += ((FixedSizeAggregationImpl) selectedProcessingType).getNumberOfMeasurements();
    
            } else if (selectedProcessingType instanceof TimeDrivenImpl) {
                result += ((TimeDrivenImpl) selectedProcessingType).getWindowLength();
    
            } else if (selectedProcessingType instanceof VariableSizeAggregationImpl) {
                result += ((VariableSizeAggregationImpl) selectedProcessingType).getRetrospectionLength();
            }
        } else {
            result += "-";
        }
        return result;
    }

    /**
     * Returns the correct name of a given ProcessingType.
     * @param aProcessingType the given ProcessingType
     * @return the correct name of a given ProcessingType
     */
    public static String getProcessingTypeString(EObject aProcessingType) {
        String result;
        if (aProcessingType instanceof FeedThroughImpl) {
            result = "FeedThrough";
            
        } else if (aProcessingType instanceof FixedSizeAggregationImpl) {
            result = "FixedSizeAggregation";

        } else if (aProcessingType instanceof TimeDrivenImpl) {
            if (aProcessingType instanceof TimeDrivenAggregationImpl) {
                result = "TimeDrivenAggregation";

            } else {
                result = "TimeDriven";
            }

        } else if (aProcessingType instanceof VariableSizeAggregationImpl) {
            result = "VariableSizeAggregation";
        } else {
            throw new IllegalArgumentException();
        }
        return result;
    }

}
