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
import org.palladiosimulator.measurementsui.wizard.viewer.EmptySelectMeasurementsViewer;
import org.palladiosimulator.measurementsui.wizard.viewer.SelectMeasurementsViewer;
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
 * @author mehmet, Ba
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
		//TODO: Change titles of wizard page (according to selected measuring point from previous page???)
		setTitle("HDD Monitor: Select Measurements");
		setDescription("Select desired Measurements to be used with the Monitor");
		this.metricDescriptionSelectionWizardModel = metricDescriptionSelectionWizardModel;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.FILL);
		FillLayout fillLayoutParentContainer = new FillLayout();
		container.setLayout(fillLayoutParentContainer);
		
		Composite compositeLeft = new Composite(container, SWT.NONE);
		FillLayout fillLayoutLeft = new FillLayout();
		SelectMeasurementsViewer selectMeasurementsViewerLeft = new SelectMeasurementsViewer(compositeLeft,
				metricDescriptionSelectionWizardModel);
		TableViewer tableViewerLeft = (TableViewer) selectMeasurementsViewerLeft.getViewer();
		tableViewerLeft.setLabelProvider(new ITableLabelProvider() {
		    
			public void removeListener(ILabelProviderListener listener) {
			    // not used
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
			    // not used
			}

			public void dispose() {
			    // not used
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
		});
		compositeLeft.setLayout(fillLayoutLeft);

		Composite compositeMiddle = new Composite(container, SWT.NONE);
		FillLayout fillLayoutMiddle = new FillLayout();
		fillLayoutMiddle.type = SWT.CENTER;
		fillLayoutMiddle.marginHeight = 100;
		fillLayoutMiddle.marginWidth = 40;
		fillLayoutMiddle.spacing = 10;
		compositeMiddle.setLayout(fillLayoutMiddle);
		
		Button rightAll = new Button(compositeMiddle, SWT.PUSH);
		rightAll.setText("-->");
		rightAll.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				metricDescriptionSelectionWizardModel.addAllMetricDescriptions();
				getContainer().updateButtons();
			}
		});

		Button rightOne = new Button(compositeMiddle, SWT.NONE);
		rightOne.setText(">");
		rightOne.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				IStructuredSelection selection = tableViewerLeft.getStructuredSelection();
				Object firstElement = selection.getFirstElement();
				MeasurementSpecification measurment = (MeasurementSpecification) firstElement;
				metricDescriptionSelectionWizardModel.addMeasurementSpecification(measurment);
				getContainer().updateButtons();
			}
		});

		Composite compositeRight = new Composite(container, SWT.NONE);
		FillLayout fillLayoutRight = new FillLayout();
		EmptySelectMeasurementsViewer emptySelectMeasurementsViewerRight = new EmptySelectMeasurementsViewer(compositeRight,
				metricDescriptionSelectionWizardModel);
		TableViewer tableViewerRight = (TableViewer) emptySelectMeasurementsViewerRight.getViewer();
		tableViewerRight.setLabelProvider(new ITableLabelProvider() {

			public void removeListener(ILabelProviderListener listener) {
			    // not used
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
			    // not used
			}

			public void dispose() {
			    // not used
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
		});
		compositeRight.setLayout(fillLayoutRight);
		tableViewerRight.getTable().getColumn(1).setWidth(75);

		Button leftOne = new Button(compositeMiddle, SWT.NONE);
		leftOne.setText("<");
		leftOne.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				IStructuredSelection selection = tableViewerRight.getStructuredSelection();
				Object firstElement = selection.getFirstElement();
				MeasurementSpecification measurment = (MeasurementSpecification) firstElement;
				metricDescriptionSelectionWizardModel.removeMeasurementSpecification(measurment);
				getContainer().updateButtons();
			}

		});

		Button leftAll = new Button(compositeMiddle, SWT.NONE);
		leftAll.setText("<--");
		leftAll.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				metricDescriptionSelectionWizardModel.removeAllMetricDescriptions();
				getContainer().updateButtons();
			}
		});

		Button addSuggestion = new Button(compositeMiddle, SWT.NONE);
		addSuggestion.setText("Add Suggestions");

		CellEditor[] cellEditor = new CellEditor[2];
		cellEditor[0] = null;
		cellEditor[1] = new CheckboxCellEditor(tableViewerRight.getTable());
		tableViewerRight.setCellEditors(cellEditor);
		String[] columnNames = { "Metric Description", "Self Adapting" };
		tableViewerRight.setColumnProperties(columnNames);
		tableViewerRight.setCellModifier(new CellModifier(tableViewerRight, metricDescriptionSelectionWizardModel));
		setPageComplete(true);
		setControl(container);
	}
}