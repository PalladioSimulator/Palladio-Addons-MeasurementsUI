package org.palladiosimulator.measurementsui.wizard.handlers.editingsupport;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;

/**
 * This class models the editing support for the columns on the 4th wizard page (for
 * specification of selected measurements).
 * 
 * @author Ba
 */
public abstract class MeasurementSpecificationEditingSupport extends EditingSupport {

    /**
     * The according TableViewer object.
     */
    protected TableViewer tableViewer;

    /**
     * This handles the internal model.
     */
    protected ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel;

    /**
     * The Editor object for the table cells.
     */
    protected CellEditor cellEditor;
    
    /**
     * Constructor, where basic attributes are set for further use, e. g. the according
     * ColumnViewer, TableViewer.
     * @param columnViewer the given ColumnViewer
     * @param tableViewer the given TableViewer
     * @param processingTypeSelectionWizardModel the internal model handler
     */
    public MeasurementSpecificationEditingSupport(ColumnViewer columnViewer, TableViewer tableViewer,
            ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel) {
        super(columnViewer);
        this.tableViewer = tableViewer;
        this.processingTypeSelectionWizardModel = processingTypeSelectionWizardModel;
    }
    
    @Override
    protected CellEditor getCellEditor(Object element) {
        return cellEditor;
    }

}
