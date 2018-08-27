package org.palladiosimulator.measurementsui.wizard.handlers;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;

public class SelectMeasurements extends WizardPage {
    private Table table;
    private Table table_1;

    public SelectMeasurements() {
        super("wizardPage");
        setTitle("HDD Monitor: Select Measurements");
        setDescription("Select desired Measurements to be used with the Monitor");
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);

        setControl(container);
        container.setLayout(new BorderLayout(0, 0));
        
        SashForm sashForm = new SashForm(container, SWT.NONE);
        sashForm.setLayoutData(BorderLayout.CENTER);
        
        Composite composite = new Composite(sashForm, SWT.NONE);
        composite.setLayout(new BorderLayout(0, 0));
        
        Label lblNewLabel = new Label(composite, SWT.NONE);
        lblNewLabel.setLayoutData(BorderLayout.NORTH);
        lblNewLabel.setText("Available Measurements");
        
        TableLayout tableLayout = new TableLayout();
        tableLayout.addColumnData(new ColumnWeightData(1));
        
        table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
        table.setLayoutData(BorderLayout.CENTER);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setLayout(tableLayout);
        
        TableViewer tableViewer = new TableViewer(table);
        tableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        TableViewerColumn availableColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        availableColumn.getColumn().setText("Available Measurements");
        
        tableViewer.setContentProvider(new ArrayContentProvider());
        tableViewer.setLabelProvider(new MeasurementSpecLabelProvider());
        
        Measurement[] input = new Measurement[]{new Measurement(), 
                new Measurement(),
                new Measurement()};
        tableViewer.setInput(input);
        
        Composite composite_1 = new Composite(sashForm, SWT.NONE);
        composite_1.setLayout(null);
        
        Button button = new Button(composite_1, SWT.NONE);
        button.setBounds(43, 144, 57, 28);
        button.setText("<");
        
        Button btnNewButton = new Button(composite_1, SWT.NONE);
        btnNewButton.setBounds(43, 178, 57, 28);
        btnNewButton.setText(">");
        
        Button btnNewButton_1 = new Button(composite_1, SWT.NONE);
        btnNewButton_1.setBounds(43, 76, 57, 28);
        btnNewButton_1.setText("<--");
        
        Button btnNewButton_2 = new Button(composite_1, SWT.NONE);
        btnNewButton_2.setBounds(43, 246, 57, 28);
        btnNewButton_2.setText("-->");
        
        Button btnAddSuggestions = new Button(composite_1, SWT.NONE);
        btnAddSuggestions.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        btnAddSuggestions.setText("Add Suggestions");
        btnAddSuggestions.setBounds(0, 320, 136, 28);
        
        Composite composite_2 = new Composite(sashForm, SWT.NONE);
        composite_2.setLayout(new BorderLayout(0, 0));
        
        TableLayout tableLayout2 = new TableLayout();
        tableLayout2.addColumnData(new ColumnWeightData(3));
        tableLayout2.addColumnData(new ColumnWeightData(2));
        
        table_1 = new Table(composite_2, SWT.BORDER | SWT.FULL_SELECTION);
        table_1.setLayoutData(BorderLayout.CENTER);
        table_1.setHeaderVisible(true);
        table_1.setLinesVisible(true);
        table_1.setLayout(tableLayout2);
        
        TableViewer tableViewer2 = new TableViewer(table_1);
        tableViewer2.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        TableViewerColumn selectedColumn = new TableViewerColumn(tableViewer2, SWT.NONE);
        selectedColumn.getColumn().setText("Selected Measurements");
        
        TableViewerColumn selfAdaptColumn = new TableViewerColumn(tableViewer2, SWT.NONE);
        selfAdaptColumn.getColumn().setText("Trigger Self Adapting");
        
        tableViewer2.setContentProvider(new ArrayContentProvider());
        tableViewer2.setLabelProvider(new SelectedMeasurementsLabelProvider());
        
        Measurement[] input2 = new Measurement[]{new Measurement(), 
                new Measurement(),
                new Measurement()};
        tableViewer2.setInput(input2);
        
        EditingSupport selfAdaptEditingSupport = new SelfAdaptEditingSupport(selfAdaptColumn.getViewer(), tableViewer2);
        selfAdaptColumn.setEditingSupport(selfAdaptEditingSupport);
        
        Label lblSelectedMeasurements = new Label(composite_2, SWT.NONE);
        lblSelectedMeasurements.setLayoutData(BorderLayout.NORTH);
        lblSelectedMeasurements.setText("Selected Measurements");
        sashForm.setWeights(new int[] {217, 143, 224});
    }
}
