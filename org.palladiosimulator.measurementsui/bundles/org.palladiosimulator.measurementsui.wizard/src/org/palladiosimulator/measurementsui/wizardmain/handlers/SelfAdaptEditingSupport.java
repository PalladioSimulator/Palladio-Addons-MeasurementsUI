package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public final class SelfAdaptEditingSupport extends EditingSupport {

    private CheckboxCellEditor cellEditor;
    private TableViewer tableViewer;
    
    public SelfAdaptEditingSupport(ColumnViewer columnViewer, TableViewer tableViewer) {
        super(columnViewer);
        this.tableViewer = tableViewer;
        
        this.cellEditor = new CheckboxCellEditor(((TableViewer)columnViewer).getTable());
//        {
//            @Override
//            protected void doSetValue(Object value) {
//                super.doSetValue(value);
//            }
//        };
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
        return ((Measurement) element).isTriggerSelfAdapting();
    }

    @Override
    protected void setValue(Object element, Object value) {
        ((Measurement) element).setTriggerSelfAdapting((Boolean.valueOf((boolean) value)));
        getViewer().update(element, null);
    }

}
