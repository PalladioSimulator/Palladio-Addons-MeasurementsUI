package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

public class CellModifier implements ICellModifier {

	private TableViewer tableViewer;

	public CellModifier(TableViewer tableViewer) {
		super();
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
		if (specification.isTriggersSelfAdaptations()) {
			specification.setTriggersSelfAdaptations(false);
		} else {
			specification.setTriggersSelfAdaptations(true);
		}
	}
}
