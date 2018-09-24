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
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.ProcessingType;
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
     * @param processingTypeSelectionWizardModel
     *            the internal model handler
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
        String selectedProcessingTypeString = MeasurementSpecificationLabelProvider
                .getProcessingTypeString(selectedProcessingType);

        List<String> processingTypeProperties = this.processingTypeSelectionWizardModel
                .fieldsForThisProcessingType(selectedProcessingTypeString);
        return (processingTypeProperties.size() > 1);
    }

    @Override
    protected Object getValue(Object element) {
        MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
        ProcessingType selectedProcessingType = measurementSpecification.getProcessingType();
        String selectedProcessingTypeString = MeasurementSpecificationLabelProvider
                .getProcessingTypeString(selectedProcessingType);

        List<String> processingTypeProperties = this.processingTypeSelectionWizardModel
                .fieldsForThisProcessingType(selectedProcessingTypeString);
        if (processingTypeProperties.size() > 1) {
            return this.processingTypeSelectionWizardModel.getAProccesingTypeAttribute(
                    measurementSpecification, processingTypeProperties.get(1));
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
        if (processingTypeProperties.size() > 1) {
            this.processingTypeSelectionWizardModel.editAProcessingTypeAttribute(
                    measurementSpecification, processingTypeProperties.get(1), Double.valueOf(valueString));
        }
        
        this.tableViewer.refresh();
    }

}
