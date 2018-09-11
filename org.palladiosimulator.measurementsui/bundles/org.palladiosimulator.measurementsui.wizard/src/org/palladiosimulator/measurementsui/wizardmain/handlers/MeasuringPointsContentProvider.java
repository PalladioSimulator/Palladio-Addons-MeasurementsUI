package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.fileaccess.SecondPageWizardModel;

public class MeasuringPointsContentProvider implements ITreeContentProvider{

    Object[] models;
    DataApplication da = DataApplication.getInstance();
    SecondPageWizardModel spwm = SecondPageWizardModel.getInstance();
    @Override
    public Object[] getElements(Object inputElement) {

        return spwm.getAllSecondPageObjects();
        
        
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