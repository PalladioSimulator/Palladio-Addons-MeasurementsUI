package org.palladiosimulator.measurementsui.wizard.handlers.contentprovider;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;

public class ExistingMeasuringpointContentProvider implements ITreeContentProvider {
    MeasuringPointSelectionWizardModel wizardModel;

    public ExistingMeasuringpointContentProvider(MeasuringPointSelectionWizardModel wizardModel) {
        this.wizardModel = wizardModel;
    }

    @Override
    public Object[] getElements(Object inputElement) {
       return wizardModel.getExistingMeasuringPoints();
        
    }

    @Override
    public Object[] getChildren(Object parentElement) {
    	// not needed
        return new Object[0];
    }

    @Override
    public Object getParent(Object element) {
    	// not needed
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
    	// not needed
        return false;
    }

}
