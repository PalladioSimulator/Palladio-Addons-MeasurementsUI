package org.palladiosimulator.measurementsui.wizard.handlers.editingsupport;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

/**
 * This class enables editing support for the 2nd column on the 4th wizard page (for
 * ProcessingTypes).
 * 
 * @author Mehmet Ali Tepeli, Ba-Anh Vu
 *
 */
public final class ProcessingTypeDropDownEditingSupport extends ProcessingTypeSelectionEditingSupport {

    /**
     * Constructor, where basic attributes are set for further use, e. g. the according
     * ColumnViewer, TableViewer.
     * 
     * @param columnViewer the given ColumnViewer
     * @param tableViewer the given TableViewer
     * @param processingTypeSelectionWizardModel the internal model handler
     */
    public ProcessingTypeDropDownEditingSupport(ColumnViewer columnViewer, TableViewer tableViewer,
            ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel) {     
        super(columnViewer, tableViewer, processingTypeSelectionWizardModel);
        
        cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
        ((ComboBoxViewerCellEditor) cellEditor).setContenProvider(new ArrayContentProvider());
        String[] possibleProcessingTypes = processingTypeSelectionWizardModel.providePossibleProcessingTypes();
        ((ComboBoxViewerCellEditor) cellEditor).setInput(possibleProcessingTypes);
        
        enableTableRowUpdateOnValueChange();
    }

    /**
     * This work-around causes the combo box to update the table row immediately after clicking a
     * new selection from the drop down list. Otherwise you would have to additionally hit enter to
     * update the table row (since combo boxes are also text boxes).
     */
    private void enableTableRowUpdateOnValueChange() {
        CCombo comboBox = (CCombo) cellEditor.getControl();
        comboBox.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                comboBox.setEnabled(false);
                comboBox.setEnabled(true);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // not used here, but the existence of this method is required
            }
        });
    }

    @Override
    protected boolean canEdit(Object element) {
        return true;
    }

    @Override
    protected Object getValue(Object element) {
        MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
        return super.processingTypeSelectionWizardModel.getStringOfProcessingType(measurementSpecification.getProcessingType());
    }

    @Override
    protected void setValue(Object element, Object value) {
        MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
        String selectedProcessingTypeString = (String) value;
        
        processingTypeSelectionWizardModel.assignProcessingType(measurementSpecification, selectedProcessingTypeString);
        tableViewer.refresh();
    }

}