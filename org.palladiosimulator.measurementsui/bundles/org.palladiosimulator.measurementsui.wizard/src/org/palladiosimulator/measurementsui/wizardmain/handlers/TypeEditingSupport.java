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
import org.palladiosimulator.measurementsui.wizardmain.handlers.Measurement.MeasurementType;

public final class TypeEditingSupport extends EditingSupport {

    private ComboBoxViewerCellEditor cellEditor = null;
    private TableViewer tableViewer;

    TypeEditingSupport(ColumnViewer columnViewer, TableViewer tableViewer) {
        super(columnViewer);
        cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
        cellEditor.setLabelProvider(new LabelProvider());
        cellEditor.setContenProvider(new ArrayContentProvider());
        cellEditor.setInput(MeasurementType.values());
        
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
            return data.getType();
        }
        return null;
    }

    @Override
    protected void setValue(Object element, Object value) {
        if (element instanceof Measurement && value instanceof MeasurementType) {
            Measurement data = (Measurement) element;
            MeasurementType newValue = (MeasurementType) value;
            /* only set new value if it differs from old one */
            if (data.getType() != newValue) {
                data.setType(newValue);
                this.tableViewer.refresh();
            }
        }
    }

}
