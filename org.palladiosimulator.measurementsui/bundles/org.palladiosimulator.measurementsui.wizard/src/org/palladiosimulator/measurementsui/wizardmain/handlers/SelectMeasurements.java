package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.parsleyviewer.EmptySelectMeasurementsViewer;
import org.palladiosimulator.measurementsui.parsleyviewer.SelectMeasurementsViewer;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.custom.SashForm;

public class SelectMeasurements extends WizardPage {
	public static final String CHECKBOX_UNCHECKED = "\u2610";
	public static final String CHECKBOX_CHECKED = "\u2611";
	private MetricDescriptionSelectionWizardModel metricDescriptionSelectionWizardModel;


	public SelectMeasurements(MetricDescriptionSelectionWizardModel metricDescriptionSelectionWizardModel) {
		super("wizardPage");
		setTitle("HDD Monitor: Select Measurements");
		setDescription("Select desired Measurements to be used with the Monitor");
		this.metricDescriptionSelectionWizardModel = metricDescriptionSelectionWizardModel;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.FILL);
		FillLayout fill = new FillLayout();
		fill.marginHeight = 20;
		fill.marginWidth = 20;
		fill.spacing = 15;

		container.setLayout(fill);
		setControl(container);

		container.setLayout(new GridLayout(3, true));
		container.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));
		Composite composite1 = new Composite(container, SWT.NONE);

		composite1.setLayoutData(new GridLayout(1, false));
		composite1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite1.setLayoutData(new GridData(400, 400));
		composite1.setLayout(fill);
		SelectMeasurementsViewer viewer1 = new SelectMeasurementsViewer(composite1,
				metricDescriptionSelectionWizardModel);
		TableViewer tableViewer = (TableViewer) viewer1.getViewer();
		tableViewer.setLabelProvider(new ITableLabelProvider() {

			public void removeListener(ILabelProviderListener listener) {
				// TODO Auto-generated method stub

			}

			public Image getColumnImage(Object element, int columnIndex) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				String result = "";
				MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
				if (columnIndex == 1) {
					if (measurementSpecification.isTriggersSelfAdaptations()) {
						result = CHECKBOX_CHECKED;
					} else {
						result = CHECKBOX_UNCHECKED;
					}
					return result;
				} else {
					result = measurementSpecification.getMetricDescription().getName();
					return result;
				}
			}

			public void addListener(ILabelProviderListener listener) {
				// TODO Auto-generated method stub

			}

			public void dispose() {
				// TODO Auto-generated method stub

			}

			public boolean isLabelProperty(Object element, String property) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				String sel = event.getSource().toString();
				Object e = event.getSource().getClass();
			}

		});
		Composite composite2 = new Composite(container, SWT.NONE);
		composite2.setLayout(new GridLayout(1, true));
		composite2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Button rightOne = new Button(composite2, SWT.NONE);
		rightOne.setBounds(500, 100, 150, 250);
		rightOne.setText("-->");
		rightOne.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				metricDescriptionSelectionWizardModel.addAllMetricDescriptions();
			}
		});
		Button rightAll = new Button(composite2, SWT.NONE);
		rightAll.setBounds(500, 150, 150, 250);
		rightAll.setText(">");
		rightAll.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				IStructuredSelection selection = tableViewer.getStructuredSelection();
				Object firstElement = selection.getFirstElement();
				MeasurementSpecification measurment = (MeasurementSpecification) firstElement;
				metricDescriptionSelectionWizardModel.addMetricDescription(measurment.getMetricDescription());
			}
		});
		
		Button leftAll = new Button(composite2, SWT.NONE);
		leftAll.setBounds(500, 250, 150, 250);
		leftAll.setText("<");
		
		Button leftOne = new Button(composite2, SWT.NONE);
		leftOne.setBounds(500, 200, 150, 250);
		leftOne.setText("<--");
		leftOne.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				metricDescriptionSelectionWizardModel.removeAllMetricDescriptions();
			}

		});
		
		Button addSuggestion = new Button(composite2, SWT.NONE);
		addSuggestion.setBounds(500, 250, 150, 250);
		addSuggestion.setText("Add Suggestions");	
		
		Composite composite3 = new Composite(container, SWT.NONE);
		composite3.setLayout(new GridLayout(2, true));
		composite3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite3.setLayout(fill);
		EmptySelectMeasurementsViewer viewer2 = new EmptySelectMeasurementsViewer(composite3,
				metricDescriptionSelectionWizardModel);
		// SelectMeasurementsViewer viewer2 = new SelectMeasurementsViewer(composite3,
		// metricDescriptionSelectionWizardModel, false);
		TableViewer tableViewer1 = (TableViewer) viewer2.getViewer();
		tableViewer1.setLabelProvider(new ITableLabelProvider() {

			public void removeListener(ILabelProviderListener listener) {
				// TODO Auto-generated method stub

			}

			public Image getColumnImage(Object element, int columnIndex) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				String result = "";
				MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
				if (columnIndex == 1) {
					if (measurementSpecification.isTriggersSelfAdaptations()) {
						result = CHECKBOX_CHECKED;
					} else {
						result = CHECKBOX_UNCHECKED;
					}
					return result;
				} else {
					result = measurementSpecification.getMetricDescription().getName();
					return result;
				}
			}

			public void addListener(ILabelProviderListener listener) {

			}

			public void dispose() {
				// TODO Auto-generated method stub

			}

			public boolean isLabelProperty(Object element, String property) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		leftAll.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				IStructuredSelection selection = tableViewer1.getStructuredSelection();
				Object firstElement = selection.getFirstElement();
				MeasurementSpecification measurment = (MeasurementSpecification) firstElement;
				metricDescriptionSelectionWizardModel.removeMetricDescription(measurment.getMetricDescription());
			}

		});
		
		CellEditor[] cellEditor = new CellEditor[2];
		cellEditor[0] = null;
		cellEditor[1] = new CheckboxCellEditor(tableViewer1.getTable());
		tableViewer1.setCellEditors(cellEditor);
		String[] columnNames = {"Metric Description", "Self Adapting"};
		tableViewer1.setColumnProperties(columnNames);
		tableViewer1.setCellModifier(new CellModifier(tableViewer1));

		setPageComplete(true);
		setControl(container);

	}
}