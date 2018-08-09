package de.unistuttgart.enpro.wizard.handlers;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

public final class Property2EditingSupport extends EditingSupport {

    private TextCellEditor cellEditor = null;
    private TableViewer tableViewer;

    Property2EditingSupport(ColumnViewer columnViewer, TableViewer tableViewer) {
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
        if (element instanceof Measurement) {
            Measurement data = (Measurement) element;
            return data.getProperty2();
        }
        return null;
    }

    @Override
    protected void setValue(Object element, Object value) {
        if (element instanceof Measurement && value instanceof Integer) {
            Measurement data = (Measurement) element;
            int newValue = (int) value;
            /* only set new value if it differs from old one */
            if (data.getProperty2() != newValue) {
                data.setProperty2(newValue);
                this.tableViewer.refresh();
            }
        }
    }
    
}
