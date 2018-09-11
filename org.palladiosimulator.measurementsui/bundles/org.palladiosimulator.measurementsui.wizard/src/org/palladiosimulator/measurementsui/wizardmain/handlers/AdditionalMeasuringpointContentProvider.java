package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.measurementsui.fileaccess.SecondPageWizardModel;

public class AdditionalMeasuringpointContentProvider implements ITreeContentProvider {
	SecondPageWizardModel sq=SecondPageWizardModel.getInstance();
	Object[] objects;
	
	@Override
	public Object[] getElements(Object inputElement) {

			objects=sq.getAllAdditionalModels();
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

	public Object[] getObjects() {
		return objects;
	}
}
