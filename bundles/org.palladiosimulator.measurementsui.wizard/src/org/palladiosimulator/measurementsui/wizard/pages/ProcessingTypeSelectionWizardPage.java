package org.palladiosimulator.measurementsui.wizard.pages;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.palladiosimulator.measurementsui.wizard.handlers.editingsupport.ProcessingTypeDropDownEditingSupport;
import org.palladiosimulator.measurementsui.wizard.handlers.editingsupport.ProcessingTypePropertyEditingSupport;
import org.palladiosimulator.measurementsui.wizard.handlers.labelprovider.ProcessingTypeSelectionLabelProvider;
import org.palladiosimulator.measurementsui.wizard.viewer.ProcessingTypeSelectionViewer;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;

import org.eclipse.jface.util.Policy;

/**
 * This class handles the GUI part of the fourth wizard page for measurement specification.
 * 
 * @author Mehmet Ali Tepeli, Ba-Anh Vu
 *
 */
public class ProcessingTypeSelectionWizardPage extends WizardPage {

    /**
     * The text for the column of the metric description
     */
    private static final String COLUMN_TEXT_METRIC_DESCRIPTION = "Metric Description";
    /**
     * The text for the column of the selected ProcessingType
     */
    private static final String COLUMN_TEXT_PROCESSING_TYPE = "Processing Type";
    /**
     * The text for the column for the 1st property of the selected ProcessingType
     */
    private static final String COLUMN_TEXT_PROPERTY1 = "Property Value 1";
    /**
     * The text for the column for the 2nd property of the selected ProcessingType
     */
    private static final String COLUMN_TEXT_PROPERTY2 = "Property Value 2";
    
    /**
	 * This handles the internal model.
	 */
	private ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel;

	/**
	 * The constructor where basic properties are set, e. g. title, description etc.
	 * 
	 * @param processingTypeSelectionWizardModel This handles the internal model
	 */
	public ProcessingTypeSelectionWizardPage(ProcessingTypeSelectionWizardModel processingTypeSelectionWizardModel) {
		super("wizardPage");
		this.processingTypeSelectionWizardModel = processingTypeSelectionWizardModel;
		setTitle(processingTypeSelectionWizardModel.getTitleText());
		setDescription(processingTypeSelectionWizardModel.getInfoText());
		
		
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.FILL);
		final FillLayout fillLayoutParentContainer = new FillLayout();
		container.setLayout(fillLayoutParentContainer);

		setControl(container);

		final ProcessingTypeSelectionViewer measurementSpecificationViewer = new ProcessingTypeSelectionViewer(container,
				processingTypeSelectionWizardModel.getUsedMetricsMonitor());
		final TableViewer tableViewer = (TableViewer) measurementSpecificationViewer.getViewer();
		tableViewer.setLabelProvider(new ProcessingTypeSelectionLabelProvider(this.processingTypeSelectionWizardModel));
		
		setColumnsText(tableViewer);
		setEditingSupports(tableViewer);
	}

    /**
     * Sets the EditingSupport objects the table of the 4th wizard page.
     * This is used to edit the values of the measurements.
     * @param tableViewer the given TableViewer
     */
    private void setEditingSupports(TableViewer tableViewer) {
        final TableViewerColumn[] tableViewerColumns = getTableViewerColumns(tableViewer);
		tableViewerColumns[1].setEditingSupport(new ProcessingTypeDropDownEditingSupport(tableViewerColumns[1].getViewer(), 
		        tableViewer, this.processingTypeSelectionWizardModel));
		tableViewerColumns[2].setEditingSupport(new ProcessingTypePropertyEditingSupport(tableViewerColumns[2].getViewer(), 
		        tableViewer, this.processingTypeSelectionWizardModel, 0));
		tableViewerColumns[3].setEditingSupport(new ProcessingTypePropertyEditingSupport(tableViewerColumns[3].getViewer(), 
                tableViewer, this.processingTypeSelectionWizardModel, 1));
    }

    /**
     * Sets the text of the table columns for the 4th wizard page.
     * @param tableViewer the given TableViewer
     */
    private void setColumnsText(TableViewer tableViewer) {
        tableViewer.getTable().getColumn(0).setText(COLUMN_TEXT_METRIC_DESCRIPTION);
		tableViewer.getTable().getColumn(1).setText(COLUMN_TEXT_PROCESSING_TYPE);
		tableViewer.getTable().getColumn(2).setText(COLUMN_TEXT_PROPERTY1);
		tableViewer.getTable().getColumn(3).setText(COLUMN_TEXT_PROPERTY2);
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
	    final TableColumn[] columns = tableViewer.getTable().getColumns();
	    final TableViewerColumn[] viewerColumns = new TableViewerColumn[columns.length];
		for (int i = 0; i < columns.length; i++) {
			TableColumn tableColumn = columns[i];
			viewerColumns[i] = (TableViewerColumn) tableColumn.getData(Policy.JFACE + ".columnViewer");
		}
		return viewerColumns;
	}

	@Override
	public boolean canFlipToNextPage() {
		return false;
	}

	@Override
    public void performHelp() {
        Program.launch("https://sdqweb.ipd.kit.edu/wiki/SimuLizar_Usability_Extension#Processing_Type_Selection_Page");
    }
}
