package org.palladiosimulator.measurementsui.wizardpages;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.palladiosimulator.measurementsui.parsleyviewer.EmptySelectMeasurementsViewer;
import org.palladiosimulator.measurementsui.parsleyviewer.SelectMeasurementsViewer;
import org.palladiosimulator.measurementsui.wizardmain.handlers.CellModifier;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.graphics.Image;

/**
 * This class handels the GUI part of the third wizard page for selecting
 * measurements.
 * 
 * @author mehmet
 *
 */
public class SelectMeasurementsWizardPage extends WizardPage {

	/**
	 * Used for the self adapting column for unchecked value.
	 */
	public static final String CHECKBOX_UNCHECKED = "\u2610";

	/**
	 * Used for the self adapting column for checked value.
	 */
	public static final String CHECKBOX_CHECKED = "\u2611";

	/**
	 * This handles the internal model.
	 */
	private MetricDescriptionSelectionWizardModel metricDescriptionSelectionWizardModel;

	/**
	 * The constructor where basic properties are set, e. g. title, description etc.
	 * 
	 * @param metricDescriptionSelectionWizardModel This handles the internal model
	 */
	public SelectMeasurementsWizardPage(MetricDescriptionSelectionWizardModel metricDescriptionSelectionWizardModel) {
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
		FillLayout fillLayout = new FillLayout();
		fillLayout.marginHeight = 20;
		fillLayout.marginWidth = 20;
		fillLayout.spacing = 15;
		composite1.setLayoutData(new GridLayout(1, false));
		composite1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite1.setLayoutData(new GridData(500, 500));
		composite1.setLayout(fillLayout);
		SelectMeasurementsViewer viewer1 = new SelectMeasurementsViewer(composite1,
				metricDescriptionSelectionWizardModel);
		TableViewer tableViewer = (TableViewer) viewer1.getViewer();
		tableViewer.setLabelProvider(new ITableLabelProvider() {

			public void removeListener(ILabelProviderListener listener) {
			}

			public Image getColumnImage(Object element, int columnIndex) {
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
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
		});

		Composite composite2 = new Composite(container, SWT.NONE);
		FillLayout fillLayout2 = new FillLayout();
		composite2.setLayout(new FillLayout(SWT.VERTICAL));
		fillLayout2.type = SWT.VERTICAL;
		fillLayout2.marginHeight = 40;
		fillLayout2.marginWidth = 80;
		fillLayout2.spacing = 10;
		composite2.setLayout(new GridLayout(3, true));
		composite2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite2.setLayoutData(new GridData(350, 350));
		Button rightAll = new Button(composite2, SWT.PUSH);
		rightAll.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, true));
		rightAll.setBounds(500, 150, 150, 250);
		rightAll.setText("-->");
		composite2.setBounds(100, 50, 300, 40);
		composite2.setLayout(fillLayout2);
		rightAll.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				metricDescriptionSelectionWizardModel.addAllMetricDescriptions();
			}
		});

		Button rightOne = new Button(composite2, SWT.NONE);
		rightOne.setBounds(500, 150, 150, 250);
		rightOne.setText(">");
		rightOne.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				IStructuredSelection selection = tableViewer.getStructuredSelection();
				Object firstElement = selection.getFirstElement();
				MeasurementSpecification measurment = (MeasurementSpecification) firstElement;
				metricDescriptionSelectionWizardModel.addMeasurementSpecification(measurment);
			}
		});

		Composite composite3 = new Composite(container, SWT.NONE);
		composite3.setLayout(new GridLayout(2, true));
		composite3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite3.setLayout(fill);
		EmptySelectMeasurementsViewer viewer2 = new EmptySelectMeasurementsViewer(composite3,
				metricDescriptionSelectionWizardModel);
		TableViewer tableViewer1 = (TableViewer) viewer2.getViewer();
		tableViewer1.setLabelProvider(new ITableLabelProvider() {

			public void removeListener(ILabelProviderListener listener) {
			}

			public Image getColumnImage(Object element, int columnIndex) {
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
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
		});

		Button leftAllOld = new Button(composite2, SWT.NONE);
		leftAllOld.setBounds(500, 250, 150, 250);
		leftAllOld.setText("<");
		leftAllOld.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				IStructuredSelection selection = tableViewer1.getStructuredSelection();
				Object firstElement = selection.getFirstElement();
				MeasurementSpecification measurment = (MeasurementSpecification) firstElement;
				metricDescriptionSelectionWizardModel.removeMeasurementSpecification(measurment);
			}

		});

		Button leftAll = new Button(composite2, SWT.NONE);
		leftAll.setBounds(500, 200, 150, 250);
		leftAll.setText("<--");
		leftAll.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				metricDescriptionSelectionWizardModel.removeAllMetricDescriptions();
			}

		});

		Button addSuggestion = new Button(composite2, SWT.NONE);
		addSuggestion.setBounds(500, 250, 150, 250);
		addSuggestion.setText("Add Suggestions");

		CellEditor[] cellEditor = new CellEditor[2];
		cellEditor[0] = null;
		cellEditor[1] = new CheckboxCellEditor(tableViewer1.getTable());
		tableViewer1.setCellEditors(cellEditor);
		String[] columnNames = { "Metric Description", "Self Adapting" };
		tableViewer1.setColumnProperties(columnNames);
		tableViewer1.setCellModifier(new CellModifier(tableViewer1));

		setPageComplete(true);
		setControl(container);
	}
}