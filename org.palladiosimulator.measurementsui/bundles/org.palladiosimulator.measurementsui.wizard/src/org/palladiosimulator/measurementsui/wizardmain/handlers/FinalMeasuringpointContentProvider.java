package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;

/**
 * 
 * @author Domas Mikalkinas
 *
 */
public class FinalMeasuringpointContentProvider implements ITreeContentProvider {
    MeasuringPointSelectionWizardModel measuringPointWizardModel;
    
    public FinalMeasuringpointContentProvider(MeasuringPointSelectionWizardModel measuringPointWizardModel) {
        this.measuringPointWizardModel = measuringPointWizardModel;
    }
	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return measuringPointWizardModel.getSignatures().toArray();
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
