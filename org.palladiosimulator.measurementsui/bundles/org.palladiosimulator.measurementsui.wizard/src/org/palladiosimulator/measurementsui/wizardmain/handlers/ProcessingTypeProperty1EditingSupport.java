package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

public final class ProcessingTypeProperty1EditingSupport extends EditingSupport {

    private TextCellEditor cellEditor = null;
    private TableViewer tableViewer;
    
    // TODO: remove this test variable
    public static int test = 7;

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
                // TODO Auto-generated method stub

            }

            @Override
            public void cancelEditor() {
                // TODO Auto-generated method stub

            }

            @Override
            public void editorValueChanged(boolean oldValidState, boolean newValidState) {
                // TODO Auto-generated method stub

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
