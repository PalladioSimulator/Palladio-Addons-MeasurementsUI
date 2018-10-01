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
 * Wizard page for the creation of standard sets. This is the second page on which you choose what what monitors/measuringpoints should be created.
 * @author Domas Mikalkinas
 *
 */
public class StandardSetMeasuringPointSelectionWizardPage extends WizardPage {
	CheckboxTableViewer viewer;
	boolean add = true;
	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	Composite composite;
	Composite tableviewerComposite;

	public CheckboxTableViewer getViewer() {
		return viewer;
	}

	public void setViewer(CheckboxTableViewer viewer) {
		this.viewer = viewer;
	}

	StandardSetCreationProvider set;

	public StandardSetMeasuringPointSelectionWizardPage(String pageName) {
		super("wizardpage");
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

	private void createStructure() {
		tableviewerComposite = new Composite(composite, SWT.SINGLE);
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

	public void loadMonitorAndMeasuringpointInput() {

		createColumns(tableviewerComposite, viewer);

		add = true;
		viewer.setInput(set.createMonitorForEveryResource().toArray());
		viewer.refresh();

	}

	public void loadOnlyMeasuringpointInput() {
		createMonitorColumns(tableviewerComposite, viewer);
		add = false;
		List<MeasuringPoint> measuringpoints = new LinkedList<>();
		List<Monitor> monitors =set.createMonitorForEveryResource();
		for(Monitor monitor: monitors) {
			measuringpoints.add(monitor.getMeasuringPoint());
		}
		viewer.setInput(measuringpoints.toArray());
		viewer.refresh();

	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		for (TableColumn tc : viewer.getTable().getColumns()) {
			tc.dispose();
		}
		TableViewerColumn col = createTableViewerColumn("Create", 50, 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}

		});

		col = createTableViewerColumn("Monitor", 200, 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Monitor p = (Monitor) element;
				return p.getEntityName();
			}
		});

		col = createTableViewerColumn("Measuring Point", 400, 2);

		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Monitor p = (Monitor) element;
				return p.getMeasuringPoint().getStringRepresentation();
			}
		});

	}

	private void createMonitorColumns(final Composite parent, final TableViewer viewer) {
		for (TableColumn tc : viewer.getTable().getColumns()) {
			tc.dispose();
		}
		TableViewerColumn col = createTableViewerColumn("Create", 50, 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}

		});

		col = createTableViewerColumn("Measuring Point", 400, 1);

		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				MeasuringPoint p = (MeasuringPoint) element;
				return p.getStringRepresentation();
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

}
