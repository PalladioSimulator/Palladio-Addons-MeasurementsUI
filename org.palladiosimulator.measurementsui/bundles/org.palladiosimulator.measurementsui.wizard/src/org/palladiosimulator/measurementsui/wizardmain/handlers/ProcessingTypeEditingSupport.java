package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

/**
 * This class enables editing support for the 2nd column on the 4th wizard page (for ProcessingTypes).
 * @author Mehmet, Ba
 *
 */
public final class ProcessingTypeEditingSupport extends EditingSupport {
	
	//TODO: only used for testing
	enum MeasurementType {
		type1, type2, type3, type4
	}

	/**
	 * The Editor object for the table cells.
	 */
    private ComboBoxViewerCellEditor cellEditor;
    
    /**
     * The according TableViewer object.
     */
    private TableViewer tableViewer;

    /**
     * Constructor, where basic attributes are set for further use, e. g. the according ColumnViewer, TableViewer.
     * @param columnViewer the given ColumnViewer
     * @param tableViewer the given TableViewer
     */
    public ProcessingTypeEditingSupport(ColumnViewer columnViewer, TableViewer tableViewer) {
        super(columnViewer);
        this.cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
        this.cellEditor.setContenProvider(new ArrayContentProvider());
        this.cellEditor.setInput(MeasurementType.values());
        
        this.tableViewer = tableViewer;
        
        this.cellEditor.addListener(new ICellEditorListener() {
            @Override
            public void applyEditorValue() {
                //not used here
            }

            @Override
            public void cancelEditor() {
            	//not used here
            }

            @Override
            public void editorValueChanged(boolean oldValidState, boolean newValidState) {
            	//not used here
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
        if (element instanceof MeasurementSpecification) {
        	MeasurementSpecification data = (MeasurementSpecification) element;
            return MeasurementType.type2;
        }
        return null;
    }

    @Override
    protected void setValue(Object element, Object value) {
        if (element instanceof MeasurementSpecification && value instanceof MeasurementType) {
        	MeasurementSpecification data = (MeasurementSpecification) element;
            MeasurementType newValue = (MeasurementType) value;
            /* only set new value if it differs from old one */
//            if (data.getType() != newValue) {
//                data.setType(newValue);
//                this.tableViewer.refresh();
//            }
        }
    }

}