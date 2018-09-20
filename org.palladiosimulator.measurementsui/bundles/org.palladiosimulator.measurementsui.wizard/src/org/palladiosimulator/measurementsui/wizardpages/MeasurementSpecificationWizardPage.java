package org.palladiosimulator.measurementsui.wizardpages;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.palladiosimulator.measurementsui.wizard.viewer.MeasurementSpecificationViewer;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.eclipse.jface.util.Policy;

public class MeasurementSpecificationWizardPage extends WizardPage {

	private ProcessingTypeSelectionWizardModel processingTypeWizardModel;

	public MeasurementSpecificationWizardPage(ProcessingTypeSelectionWizardModel processingTypeWizardModel) {
		super("wizardPage");
		setTitle("Measurement Specification");
		setDescription("Specify properties of measurements");
		this.processingTypeWizardModel = processingTypeWizardModel;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.FILL);
		FillLayout fillLayoutParentContainer = new FillLayout();
		container.setLayout(fillLayoutParentContainer);

		setControl(container);

		MeasurementSpecificationViewer measurementSpecificationViewer = new MeasurementSpecificationViewer(container,
				this.processingTypeWizardModel);
		TableViewer tableViewer = (TableViewer) measurementSpecificationViewer.getViewer();
		tableViewer.setLabelProvider(new ITableLabelProvider() {

			public void removeListener(ILabelProviderListener listener) {
				// not used
			}

			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				String result = "";
				MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
				if (columnIndex == 0) {
					result = measurementSpecification.getMetricDescription().getName();
				} else if (columnIndex == 1) {

				} else if (columnIndex == 2) {

				} else if (columnIndex == 3) {

				}
				return result;
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
		TableViewerColumn[] tableViewerColumns = getTableViewerColumns(tableViewer);
		tableViewerColumns[1].setEditingSupport(new EditingSupport(tableViewer) {
			@Override
			protected boolean canEdit(Object element) {
				if (element instanceof MeasurementSpecification) {
					return true;
				} else {
					return false;
				}
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				final ComboBoxCellEditor editor = new ComboBoxCellEditor(tableViewer.getTable(),
						getPossibleProcessingTypeNames(), SWT.READ_ONLY);
				((CCombo) editor.getControl()).addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						IStructuredSelection sel = (IStructuredSelection) tableViewer.getSelection();
						MeasurementSpecification filterValue = (MeasurementSpecification) sel.getFirstElement();
						// .. update the filter on your TableViewer
					}
				});
				return editor;
			}

			private String[] getPossibleProcessingTypeNames() {
				return null;
			}

			@Override
			protected Object getValue(Object element) {
				if (element instanceof MeasurementSpecification) {
					return null;
				} else {
					return null;
				}
			}

			@Override
			protected void setValue(Object element, Object value) {
				if (element instanceof MeasurementSpecification) {
					// update your FilterDataObject
				}
			}
		});
	}

	/**
	 * Returns the TableViewerColumn objects as an array for a given TableViewer
	 * object.
	 * 
	 * @param tableViewer
	 *            the given TableViewer object
	 * @return the TableViewerColumn objects as an array for a given TableViewer
	 *         object
	 */
	private TableViewerColumn[] getTableViewerColumns(TableViewer tableViewer) {
		TableColumn[] columns = tableViewer.getTable().getColumns();
		TableViewerColumn[] viewerColumns = new TableViewerColumn[columns.length];
		for (int i = 0; i < columns.length; i++) {
			TableColumn tableColumn = columns[i];
			viewerColumns[i] = (TableViewerColumn) tableColumn.getData(Policy.JFACE + ".columnViewer");
		}
		return viewerColumns;
	}

	/** @override */
	public boolean canFlipToNextPage() {
		return false;
	}

}
