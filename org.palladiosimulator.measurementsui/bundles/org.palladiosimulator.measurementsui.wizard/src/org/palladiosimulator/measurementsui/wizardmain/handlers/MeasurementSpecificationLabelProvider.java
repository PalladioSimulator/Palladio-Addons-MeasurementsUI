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

/**
 * This class handles the correct display of labels in the table of the 4th wizard page which is for measurement specification.
 * 
 * @author Ba
 *
 */
public class MeasurementSpecificationLabelProvider implements ITableLabelProvider {
    
    /**
     * This handles the internal model.
     */
    private ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel;
    
    /**
     * The constructor which sets the internal model.
     * @param processingTypeSelectionWizardModel the internal model
     */
    public MeasurementSpecificationLabelProvider(ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel) {
        this.processingTypeSelectionWizardModel = processingTypeSelectionWizardModel;
    }

    /**
     * not used, but the existence of this method is required
     * @param listener not used
     */
    public void removeListener(ILabelProviderListener listener) {
        // not used
    }

    /**
     * not used, but the existence of this method is required
     * @param listener not used
     */
    public void addListener(ILabelProviderListener listener) {
        // not used
    }

    /**
     * not used, but the existence of this method is required
     */
    public void dispose() {
        // not used
    }

    /**
     * not used, but the existence of this method is required
     * @param element not used
     * @param property not used
     * @return not used
     */
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }
    
    /**
     * not used, but the existence of this method is required
     * @param element not used
     * @param columnIndex not used
     * @return not used
     */
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    /**
     * Sets the label that is displayed in the table for a given element and column.
     * @param element the given element of the table
     * @param columnIndex the index of the given column
     * @return the label that is displayed
     */
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

    /**
     * Returns the label for the 3rd column (first property column)
     * @param result the label as a String
     * @param measurementSpecification the given MeasurementSpecification
     * @return the label for the 3rd column (first property column)
     */
    private String getLabelProperty1(String result, MeasurementSpecification measurementSpecification) {
        ProcessingType selectedProcessingType = measurementSpecification.getProcessingType();
        String selectedProcessingTypeString = getProcessingTypeString(selectedProcessingType);
    
        List<String> processingTypeProperties = processingTypeSelectionWizardModel
                .fieldsForThisProcessingType(selectedProcessingTypeString);
        if (!processingTypeProperties.isEmpty()) {
            result += processingTypeProperties.get(0) + ": ";
            
            result += this.processingTypeSelectionWizardModel.getAProccesingTypeAttribute(
                    measurementSpecification, processingTypeProperties.get(0));
        } else {
            result += "-";
        }
        return result;
    }

    /**
     * Returns the label for the 4th column (second property column)
     * @param result the label as a String
     * @param measurementSpecification the given MeasurementSpecification
     * @return the label for the 4th column (second property column)
     */
    private String getLabelProperty2(String result, MeasurementSpecification measurementSpecification) {
        ProcessingType selectedProcessingType = measurementSpecification.getProcessingType();
        String selectedProcessingTypeString = getProcessingTypeString(selectedProcessingType);
    
        List<String> processingTypeProperties = processingTypeSelectionWizardModel
                .fieldsForThisProcessingType(selectedProcessingTypeString);
        if (processingTypeProperties.size() > 1) {
            result += processingTypeProperties.get(1) + ": ";
            
            result += this.processingTypeSelectionWizardModel.getAProccesingTypeAttribute(
                    measurementSpecification, processingTypeProperties.get(1));
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
