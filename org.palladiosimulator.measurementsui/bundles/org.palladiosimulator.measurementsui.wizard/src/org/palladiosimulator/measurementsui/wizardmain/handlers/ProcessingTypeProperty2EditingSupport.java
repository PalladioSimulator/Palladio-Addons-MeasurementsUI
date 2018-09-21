package org.palladiosimulator.measurementsui.wizardmain.handlers;

import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;
import org.palladiosimulator.measurementsui.wizardpages.MeasurementSpecificationWizardPage;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.ProcessingType;
import org.palladiosimulator.monitorrepository.impl.FeedThroughImpl;
import org.palladiosimulator.monitorrepository.impl.FixedSizeAggregationImpl;
import org.palladiosimulator.monitorrepository.impl.TimeDrivenImpl;
import org.palladiosimulator.monitorrepository.impl.VariableSizeAggregationImpl;

/**
 * This class enables editing support for the 4th column on the 4th wizard page (for
 * ProcessingTypes).
 * 
 * @author Mehmet, Ba
 *
 */
public final class ProcessingTypeProperty2EditingSupport extends EditingSupport {

    /**
     * This handles the internal model.
     */
    private ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel;

    /**
     * The Editor object for the table cells.
     */
    private TextCellEditor cellEditor;

    /**
     * The according TableViewer object.
     */
    private TableViewer tableViewer;

    /**
     * Constructor, where basic attributes are set for further use, e. g. the according
     * ColumnViewer, TableViewer.
     * 
     * @param columnViewer
     *            the given ColumnViewer
     * @param tableViewer
     *            the given TableViewer
     */
    public ProcessingTypeProperty2EditingSupport(ColumnViewer columnViewer, TableViewer tableViewer,
            ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel) {
        super(columnViewer);
        this.processingTypeSelectionWizardModel = processingTypeSelectionWizardModel;
        cellEditor = new TextCellEditor((Composite) getViewer().getControl()) {
            @Override
            protected void doSetValue(Object value) {
                super.doSetValue(value.toString());
            }
        };

        this.tableViewer = tableViewer;

        cellEditor.addListener(new ICellEditorListener() {
            @Override
            public void applyEditorValue() {
                // not used here
            }

            @Override
            public void cancelEditor() {
                // not used here
            }

            @Override
            public void editorValueChanged(boolean oldValidState, boolean newValidState) {
                // not used here
            }
        });
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        return cellEditor;
    }

    @Override
    protected boolean canEdit(Object element) {
        MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
        ProcessingType selectedProcessingType = measurementSpecification.getProcessingType();
        String selectedProcessingTypeString = MeasurementSpecificationWizardPage
                .getProcessingTypeString(selectedProcessingType);

        List<String> processingTypeProperties = this.processingTypeSelectionWizardModel
                .fieldsForThisProcessingType(selectedProcessingTypeString);
        if (processingTypeProperties.size() > 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Object getValue(Object element) {
        MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
        ProcessingType selectedProcessingType = measurementSpecification.getProcessingType();
        String selectedProcessingTypeString = MeasurementSpecificationWizardPage
                .getProcessingTypeString(selectedProcessingType);

        List<String> processingTypeProperties = this.processingTypeSelectionWizardModel
                .fieldsForThisProcessingType(selectedProcessingTypeString);
        if (processingTypeProperties.size() > 1) {
            String result = "";
            
            if (selectedProcessingType instanceof FeedThroughImpl) {

            } else if (selectedProcessingType instanceof FixedSizeAggregationImpl) {
//                result += ((FixedSizeAggregationImpl) selectedProcessingType).getFrequency();
                result += ((FixedSizeAggregationImpl) selectedProcessingType).getNumberOfMeasurements();

            } else if (selectedProcessingType instanceof TimeDrivenImpl) {
//                result += ((TimeDrivenImpl) selectedProcessingType).getWindowIncrement();
                result += ((TimeDrivenImpl) selectedProcessingType).getWindowLength();

            } else if (selectedProcessingType instanceof VariableSizeAggregationImpl) {
//                result += ((VariableSizeAggregationImpl) selectedProcessingType).getFrequency();
                result += ((VariableSizeAggregationImpl) selectedProcessingType).getRetrospectionLength();
            }

            return result;
        } else {
            return null;
        }
    }

    @Override
    protected void setValue(Object element, Object value) {
        String valueString = (String) value;
        MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
        ProcessingType selectedProcessingType = measurementSpecification.getProcessingType();
        String selectedProcessingTypeString = MeasurementSpecificationWizardPage
                .getProcessingTypeString(selectedProcessingType);

        List<String> processingTypeProperties = this.processingTypeSelectionWizardModel
                .fieldsForThisProcessingType(selectedProcessingTypeString);
        if (processingTypeProperties.size() > 1) {
            
            if (selectedProcessingType instanceof FeedThroughImpl) {

            } else if (selectedProcessingType instanceof FixedSizeAggregationImpl) {
//                ((FixedSizeAggregationImpl) selectedProcessingType).setFrequency(Integer.valueOf(valueString));
                ((FixedSizeAggregationImpl) selectedProcessingType).setNumberOfMeasurements(Integer.valueOf(valueString));

            } else if (selectedProcessingType instanceof TimeDrivenImpl) {
//                ((TimeDrivenImpl) selectedProcessingType).setWindowIncrement(Double.valueOf(valueString));
                ((TimeDrivenImpl) selectedProcessingType).setWindowLength(Double.valueOf(valueString));

            } else if (selectedProcessingType instanceof VariableSizeAggregationImpl) {
//                ((VariableSizeAggregationImpl) selectedProcessingType).setFrequency(Integer.valueOf(valueString));
                ((VariableSizeAggregationImpl) selectedProcessingType).setRetrospectionLength(Double.valueOf(valueString));
            }

        }
        
        this.tableViewer.refresh();
    }

}
