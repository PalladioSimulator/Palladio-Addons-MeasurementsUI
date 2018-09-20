package org.palladiosimulator.measurementsui.wizardmain.handlers;

import java.util.LinkedList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;

/**
 * 
 * @author Domas Mikalkinas
 *
 */
public class MeasuringPointsContentProvider implements ITreeContentProvider {

	Object[] models;
	DataApplication da = DataApplication.getInstance();
	MeasuringPointSelectionWizardModel measuringPointWizardModel;

    public MeasuringPointsContentProvider (MeasuringPointSelectionWizardModel measuringPointWizardModel) {
	    this.measuringPointWizardModel = measuringPointWizardModel;
	}
	
	@Override
	public Object[] getElements(Object inputElement) {
		return measuringPointWizardModel.getAllSecondPageObjects();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof LinkedList) {
			return ((LinkedList) parentElement).toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof LinkedList) {
			if (!((LinkedList) element).isEmpty()) {
				return element;
			}

		}
		return null;
		// return ((NamedElement) element).getClass();
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof LinkedList) {
			return true;
		} else {
			return false;
		}
	}

}
