package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class MeasurementSpecification extends WizardPage {
    private Table table;

    public MeasurementSpecification() {
        super("wizardPage");
        setTitle("Measurement Specification");
        setDescription("Specify properties of measurements");
    }

    @Override
    public void createControl(Composite parent) {
        TableLayout tableLayout = new TableLayout();
        tableLayout.addColumnData(new ColumnWeightData(1));
        tableLayout.addColumnData(new ColumnWeightData(1));
        tableLayout.addColumnData(new ColumnWeightData(1));
        tableLayout.addColumnData(new ColumnWeightData(1));

        Table table = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        table.setLayout(tableLayout);
        
        setControl(table);

        TableViewer tableViewer = new TableViewer(table);
        tableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        TableViewerColumn labelColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        labelColumn.getColumn().setText("Label");
        TableViewerColumn typeColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        typeColumn.getColumn().setText("Value");
        TableViewerColumn property1Column = new TableViewerColumn(tableViewer, SWT.NONE);
        property1Column.getColumn().setText("Property 1");
        TableViewerColumn property2Column = new TableViewerColumn(tableViewer, SWT.NONE);
        property2Column.getColumn().setText("Property 2");

        tableViewer.setContentProvider(new ArrayContentProvider());
        tableViewer.setLabelProvider(new MeasurementSpecLabelProvider());

        Measurement[] input = new Measurement[]{new Measurement(), 
                                                new Measurement(),
                                                new Measurement()};
        tableViewer.setInput(input);
        
        EditingSupport typeEditingSupport = new TypeEditingSupport(typeColumn.getViewer(), tableViewer);
        typeColumn.setEditingSupport(typeEditingSupport);
        
        EditingSupport property1EditingSupport = new Property1EditingSupport(property1Column.getViewer(), tableViewer);
        property1Column.setEditingSupport(property1EditingSupport);
        
        EditingSupport property2EditingSupport = new Property2EditingSupport(property2Column.getViewer(), tableViewer);
        property2Column.setEditingSupport(property2EditingSupport);
    }

}
