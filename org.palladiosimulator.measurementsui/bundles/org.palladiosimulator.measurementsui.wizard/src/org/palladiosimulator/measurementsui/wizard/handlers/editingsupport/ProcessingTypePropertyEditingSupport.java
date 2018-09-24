package org.palladiosimulator.measurementsui.wizard.handlers.editingsupport;

import java.util.List;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.wizard.handlers.labelprovider.MeasurementSpecificationLabelProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.ProcessingType;

/**
 * This class enables editing support for the ProcessingType property columns on the 4th wizard page 
 * (for specification of measurements).
 * 
 * @author Mehmet, Ba
 *
 */
public final class ProcessingTypePropertyEditingSupport extends MeasurementSpecificationEditingSupport {

    /**
     * This indicates the index of the property column of the ProcessingType,
     * e. g. if the ProcessingType has 2 property values, then the index goes from 0 to 1.
     */
    private int propertyColumnIndex;
    
    /**
     * Constructor, where basic attributes are set for further use, e. g. the according
     * ColumnViewer, TableViewer.
     * 
     * @param columnViewer the given ColumnViewer
     * @param tableViewer the given TableViewer
     * @param processingTypeSelectionWizardModel the internal model handler
     * @param propertyColumn indicates the index of the property column of the ProcessingType
     */
    public ProcessingTypePropertyEditingSupport(ColumnViewer columnViewer, TableViewer tableViewer,
            ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel, int propertyColumn) {
        super(columnViewer, tableViewer, processingTypeSelectionWizardModel);
        
        if (propertyColumn < 0) {
            throw new IllegalArgumentException();
        }
        this.propertyColumnIndex = propertyColumn;
        
        cellEditor = new TextCellEditor((Composite) getViewer().getControl()) {
            @Override
            protected void doSetValue(Object value) {
                super.doSetValue(value.toString());
            }
        };
    }

    @Override
    protected boolean canEdit(Object element) {
        MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
        ProcessingType selectedProcessingType = measurementSpecification.getProcessingType();
        String selectedProcessingTypeString = MeasurementSpecificationLabelProvider
                .getProcessingTypeString(selectedProcessingType);

        List<String> processingTypeProperties = this.processingTypeSelectionWizardModel
                .fieldsForThisProcessingType(selectedProcessingTypeString);
        return (processingTypeProperties.size() > this.propertyColumnIndex);
    }

    @Override
    protected Object getValue(Object element) {
        MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
        ProcessingType selectedProcessingType = measurementSpecification.getProcessingType();
        String selectedProcessingTypeString = MeasurementSpecificationLabelProvider
                .getProcessingTypeString(selectedProcessingType);

        List<String> processingTypeProperties = this.processingTypeSelectionWizardModel
                .fieldsForThisProcessingType(selectedProcessingTypeString);
        if (processingTypeProperties.size() > this.propertyColumnIndex) {
            return this.processingTypeSelectionWizardModel.getAProccesingTypeAttribute(
                    measurementSpecification, processingTypeProperties.get(this.propertyColumnIndex));
        } else {
            return null;
        }
    }

    @Override
    protected void setValue(Object element, Object value) {
        String valueString = (String) value;
        MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
        ProcessingType selectedProcessingType = measurementSpecification.getProcessingType();
        String selectedProcessingTypeString = MeasurementSpecificationLabelProvider
                .getProcessingTypeString(selectedProcessingType);

        List<String> processingTypeProperties = this.processingTypeSelectionWizardModel
                .fieldsForThisProcessingType(selectedProcessingTypeString);
        if (processingTypeProperties.size() > this.propertyColumnIndex) {
            this.processingTypeSelectionWizardModel.editAProcessingTypeAttribute(
                    measurementSpecification, processingTypeProperties.get(this.propertyColumnIndex), 
                    Double.valueOf(valueString));
        }
        
        this.tableViewer.refresh();
    }

}
