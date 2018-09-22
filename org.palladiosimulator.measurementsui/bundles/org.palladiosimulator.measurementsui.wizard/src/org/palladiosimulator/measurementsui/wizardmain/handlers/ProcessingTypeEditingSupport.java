package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

/**
 * This class enables editing support for the 2nd column on the 4th wizard page (for
 * ProcessingTypes).
 * 
 * @author Mehmet, Ba
 *
 */
public final class ProcessingTypeEditingSupport extends EditingSupport {

    /**
     * This handles the internal model.
     */
    private ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel;

    /**
     * The Editor object for the table cells.
     */
    private ComboBoxViewerCellEditor cellEditor;

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
    public ProcessingTypeEditingSupport(ColumnViewer columnViewer, TableViewer tableViewer,
            ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel) {
        super(columnViewer);
        this.processingTypeSelectionWizardModel = processingTypeSelectionWizardModel;
        this.cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
        this.cellEditor.setContenProvider(new ArrayContentProvider());

        String[] possibleProcessingTypes = this.processingTypeSelectionWizardModel.providePossibleProcessingTypes();
        this.cellEditor.setInput(possibleProcessingTypes);

        this.tableViewer = tableViewer;

        this.cellEditor.addListener(new ICellEditorListener() {
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
        return this.cellEditor;
    }

    @Override
    protected boolean canEdit(Object element) {
        return true;
    }

    @Override
    protected Object getValue(Object element) {
        MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
        return MeasurementSpecificationLabelProvider.getProcessingTypeString(measurementSpecification.getProcessingType());
    }

    @Override
    protected void setValue(Object element, Object value) {
        MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
        String selectedProcessingTypeString = (String) value;
        
        this.processingTypeSelectionWizardModel.assignProcessingType(measurementSpecification, selectedProcessingTypeString);
        this.tableViewer.refresh();
    }

}