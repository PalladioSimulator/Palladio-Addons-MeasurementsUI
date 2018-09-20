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

public final class ProcessingTypeEditingSupport extends EditingSupport {
	
	enum MeasurementType {
		type1, type2, type3, type4
	}

    private ComboBoxViewerCellEditor cellEditor;
    private TableViewer tableViewer;

    public ProcessingTypeEditingSupport(ColumnViewer columnViewer, TableViewer tableViewer) {
        super(columnViewer);
        this.cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
        this.cellEditor.setLabelProvider(new LabelProvider());
        this.cellEditor.setContenProvider(new ArrayContentProvider());
        this.cellEditor.setInput(MeasurementType.values());
        
        this.tableViewer = tableViewer;
        
        this.cellEditor.addListener(new ICellEditorListener() {

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