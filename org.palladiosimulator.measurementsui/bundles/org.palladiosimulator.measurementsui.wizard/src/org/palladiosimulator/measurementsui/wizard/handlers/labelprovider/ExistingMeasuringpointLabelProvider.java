package org.palladiosimulator.measurementsui.wizard.handlers.labelprovider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.provider.MeasuringPointItemProvider;
import org.palladiosimulator.pcm.provider.PcmItemProviderAdapterFactory;
import org.palladiosimulator.pcmmeasuringpoint.ResourceContainerMeasuringPoint;

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
        		PcmItemProviderAdapterFactory factory = new PcmItemProviderAdapterFactory();
        		EObject object = (EObject) element;
        			MeasuringPointItemProvider provider = new MeasuringPointItemProvider(factory);
        		

        return ExtendedImageRegistry.getInstance().getImage(provider.getImage(object));
    }

    @Override
    public String getText(Object element) {
        return ((MeasuringPoint)element).getStringRepresentation();
    }

}
