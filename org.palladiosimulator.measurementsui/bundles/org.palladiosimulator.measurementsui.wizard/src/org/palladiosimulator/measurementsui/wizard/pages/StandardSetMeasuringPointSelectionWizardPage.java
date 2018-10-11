package org.palladiosimulator.measurementsui.wizard.pages;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.dataprovider.StandardSetCreationProvider;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * Wizard page for the creation of standard sets. This is the second page on which you choose what
 * what monitors/measuringpoints should be created.
 * 
 * @author Domas Mikalkinas
 *
 */
public class StandardSetMeasuringPointSelectionWizardPage extends WizardPage {
    private CheckboxTableViewer viewer;
    private boolean loadMonitorAndMeasuringpoint = true;

    /**
     * getter for the flag, which indicates what should be loaded
     * 
     * @return boolean
     */
    public boolean isLoadMonitorAndMeasuringpoint() {
        return loadMonitorAndMeasuringpoint;
    }

    /**
     * setter for the flag, which indicates what should be loaded
     * 
     * @param add
     *            flag for the monitors
     */
    public void setLoadMonitorAndMeasuringpoint(boolean add) {
        this.loadMonitorAndMeasuringpoint = add;
    }

    private Composite composite;

    /**
     * getter for the table viewer
     * 
     * @return checkboxtableviewer
     */
    public CheckboxTableViewer getViewer() {
        return viewer;
    }

    /**
     * sets the viewer for this view
     * 
     * @param viewer
     *            the checkboxtableviewer
     */
    public void setViewer(CheckboxTableViewer viewer) {
        this.viewer = viewer;
    }

    private StandardSetCreationProvider set;

    /**
     * constructor for this wizard page
     * 
     * @param pageName
     *            name for the wizard page
     */
    public StandardSetMeasuringPointSelectionWizardPage(String pageName) {
        super("standardSetMeasuringPointSelectionWizardPage");
        setMessage(pageName);
        set = new StandardSetCreationProvider();
    }

    @Override
    public void createControl(Composite parent) {

        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        createStructure();
        setControl(composite);
        setPageComplete(true);

    }

    /**
     * creates the whole structure for the wizard page
     */
    private void createStructure() {
        Composite tableviewerComposite = new Composite(composite, SWT.SINGLE);
        tableviewerComposite.setLayout(new GridLayout(1, false));
        tableviewerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        viewer = CheckboxTableViewer.newCheckList(tableviewerComposite, SWT.BORDER);

        viewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Table table = viewer.getTable();

        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        viewer.setContentProvider(new ArrayContentProvider());
        Composite buttonComposite = new Composite(composite, SWT.NONE);
        buttonComposite.setLayout(new GridLayout(2, false));
        Button selectAll = new Button(buttonComposite, SWT.PUSH);
        selectAll.setText("Select all");
        selectAll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        selectAll.addListener(SWT.Selection, e -> {
            viewer.setAllChecked(true);
        });

        Button unselectAll = new Button(buttonComposite, SWT.PUSH);
        unselectAll.setText("Unselect all");
        unselectAll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        unselectAll.addListener(SWT.Selection, e -> {
            viewer.setAllChecked(false);
        });
    }

    /**
     * loads monitors and measuringpoints, if the option is selected on the previous wizard page
     */
    public void loadMonitorAndMeasuringpointInput() {

        createColumns(viewer);
        setMessage("Select all monitors which should be created.");
        loadMonitorAndMeasuringpoint = true;
        viewer.setInput(set.createMonitorForEveryResource().toArray());
        viewer.refresh();

    }

    /**
     * loads only the measuringpoints, if the option is selected on the previous wizard page
     */
    public void loadOnlyMeasuringpointInput() {
        createMeasuringpointColumns(viewer);
        setMessage("Select all measuringpoints which should be created.");
        loadMonitorAndMeasuringpoint = false;
        List<MeasuringPoint> measuringpoints = new LinkedList<>();
        List<Monitor> monitors = set.createMonitorForEveryResource();
        for (Monitor monitor : monitors) {
            measuringpoints.add(monitor.getMeasuringPoint());
        }
        viewer.setInput(measuringpoints.toArray());
        viewer.refresh();

    }

    /**
     * creates all columns for the monitor and measuringpoint table
     * 
     * @param viewer
     *            the table viewer the columns are added to
     */
    private void createColumns(final TableViewer viewer) {
        for (TableColumn tc : viewer.getTable().getColumns()) {
            tc.dispose();
        }
        TableViewerColumn col = createTableViewerColumn("Create", 50);
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                return null;
            }

        });

        col = createTableViewerColumn("Monitor", 200);
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Monitor p = (Monitor) element;
                return p.getEntityName();
            }
        });

        col = createTableViewerColumn("Measuring Point", 400);

        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Monitor p = (Monitor) element;
                return p.getMeasuringPoint().getStringRepresentation();
            }
        });

    }

    /**
     * creates all columns for the measuringpoint table
     * 
     * @param viewer
     *            the viewer the columns are added to
     */
    private void createMeasuringpointColumns(final TableViewer viewer) {
        for (TableColumn tc : viewer.getTable().getColumns()) {
            tc.dispose();
        }
        TableViewerColumn col = createTableViewerColumn("Create", 50);
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                return null;
            }

        });

        col = createTableViewerColumn("Measuring Point", 400);

        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                MeasuringPoint p = (MeasuringPoint) element;
                return p.getStringRepresentation();
            }
        });

    }

    /**
     * creates a column for a table and adds it to it
     * 
     * @param title
     *            title of the column
     * @param bound
     *            size of the columns
     * @return the column
     */
    private TableViewerColumn createTableViewerColumn(String title, int bound) {
        final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
        final TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.setWidth(bound);
        column.setResizable(true);
        column.setMoveable(true);
        return viewerColumn;
    }

}
