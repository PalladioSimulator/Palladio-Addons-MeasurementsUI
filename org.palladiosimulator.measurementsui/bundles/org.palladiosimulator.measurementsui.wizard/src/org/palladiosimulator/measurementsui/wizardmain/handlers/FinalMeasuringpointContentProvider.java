package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.measurementsui.fileaccess.SecondPageWizardModel;

public class FinalMeasuringpointContentProvider implements ITreeContentProvider {
SecondPageWizardModel sq = SecondPageWizardModel.getInstance();
	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return sq.getSignatures().toArray();
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
