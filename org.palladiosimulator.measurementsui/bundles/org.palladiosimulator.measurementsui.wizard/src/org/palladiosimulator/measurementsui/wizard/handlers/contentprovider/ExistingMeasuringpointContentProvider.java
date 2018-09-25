package org.palladiosimulator.measurementsui.wizard.handlers.contentprovider;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
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
