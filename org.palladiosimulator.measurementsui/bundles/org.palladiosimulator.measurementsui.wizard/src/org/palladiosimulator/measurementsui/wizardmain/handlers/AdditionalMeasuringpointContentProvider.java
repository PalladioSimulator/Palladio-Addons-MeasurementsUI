package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.pcm.core.entity.NamedElement;

/**
 * 
 * @author Domas Mikalkinas
 *
 */
public class AdditionalMeasuringpointContentProvider implements ITreeContentProvider {
	MeasuringPointSelectionWizardModel measuringPointWizardModel;
	Object[] objects;
	
	public AdditionalMeasuringpointContentProvider(MeasuringPointSelectionWizardModel measuringPointWizardModel) {
	    this.measuringPointWizardModel = measuringPointWizardModel;
	}
	
	@Override
	public Object[] getElements(Object inputElement) {

		objects = measuringPointWizardModel.getAllAdditionalModels();
		return objects;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

}
