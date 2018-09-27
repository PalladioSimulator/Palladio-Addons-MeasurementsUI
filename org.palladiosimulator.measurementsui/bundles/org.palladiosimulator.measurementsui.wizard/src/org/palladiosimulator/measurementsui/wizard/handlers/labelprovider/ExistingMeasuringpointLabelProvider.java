package org.palladiosimulator.measurementsui.wizard.handlers.labelprovider;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;

public class ExistingMeasuringpointLabelProvider implements ILabelProvider{

    @Override
    public void addListener(ILabelProviderListener listener) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Image getImage(Object element) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getText(Object element) {
        return ((MeasuringPoint)element).getStringRepresentation();
    }

}
