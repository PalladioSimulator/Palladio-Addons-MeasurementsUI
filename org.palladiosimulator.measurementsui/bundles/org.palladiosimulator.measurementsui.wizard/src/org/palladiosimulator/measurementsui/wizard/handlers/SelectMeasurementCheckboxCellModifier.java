package org.palladiosimulator.measurementsui.wizard.handlers;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

/**
 * This handles the checkbox action for the self adapting column in the 3rd wizard page (measurement selection).
 * @author Mehmet Ali Tepeli
 *
 */
public class SelectMeasurementCheckboxCellModifier implements ICellModifier {

	/**
	 * The table viewer of the according table
	 */
	private TableViewer tableViewer;
    private MetricDescriptionSelectionWizardModel model;

	/**
	 * The constructor where basic attributes are set, e. g. the tableViewer
	 * @param tableViewer the given tableViewer of the according table
	 */
	public SelectMeasurementCheckboxCellModifier(TableViewer tableViewer, MetricDescriptionSelectionWizardModel model) {
		super();
		this.model = model;
		this.tableViewer = tableViewer;
	}


    @Override
	public boolean canModify(Object element, String property) {
		return true;
	}

	@Override
	public Object getValue(Object element, String property) {
		MeasurementSpecification ms = (MeasurementSpecification) element;
		return ms.isTriggersSelfAdaptations();
	}

	@Override
	public void modify(Object element, String property, Object value) {
		IStructuredSelection selection = tableViewer.getStructuredSelection();
		MeasurementSpecification specification = (MeasurementSpecification) selection.getFirstElement();
		model.switchTriggerSelfAdapting(specification);
	}
}