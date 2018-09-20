package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

/**
 * This class enables editing support for the 3nd column on the 4th wizard page (for ProcessingTypes).
 * @author Mehmet, Ba
 *
 */
public final class ProcessingTypeProperty1EditingSupport extends EditingSupport {

	/**
	 * The Editor object for the table cells.
	 */
    private TextCellEditor cellEditor = null;
    
    /**
     * The according TableViewer object.
     */
    private TableViewer tableViewer;
    
    // TODO: remove this test variable
    public static int test = 7;

    /**
     * Constructor, where basic attributes are set for further use, e. g. the according ColumnViewer, TableViewer.
     * @param columnViewer the given ColumnViewer
     * @param tableViewer the given TableViewer
     */
    public ProcessingTypeProperty1EditingSupport(ColumnViewer columnViewer, TableViewer tableViewer) {
        super(columnViewer);
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
        return cellEditor;
    }

    @Override
    protected boolean canEdit(Object element) {
        return true;
    }

    @Override
    protected Object getValue(Object element) {
//        if (element instanceof MeasurementSpecification) {
//        	MeasurementSpecification data = (MeasurementSpecification) element;
//            return data.getProperty1();
//        }
//        return null;
    	return test;
    }

    @Override
    protected void setValue(Object element, Object value) {
//    	MeasurementSpecification data = (MeasurementSpecification) element;
//        int newValue = Integer.valueOf((String) value);
//        /* only set new value if it differs from old one */
//        if (data.getProperty1() != newValue) {
//            data.setProperty1(newValue);
//            this.tableViewer.refresh();
//        }
    	int newValue = Integer.valueOf((String) value);
    	test = newValue;
    	this.tableViewer.refresh();
    }

}
