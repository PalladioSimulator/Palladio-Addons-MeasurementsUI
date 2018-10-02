package org.palladiosimulator.measurementsui.wizard.handlers.labelprovider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.provider.MeasuringPointItemProvider;
import org.palladiosimulator.pcm.provider.PcmItemProviderAdapterFactory;

/**
 * label provider for the first wizard page of the measuringpoint creation process
 * 
 * @author Domas Mikalkinas
 *
 */
public class ExistingMeasuringpointLabelProvider implements ILabelProvider {

    @Override
    public void addListener(ILabelProviderListener listener) {
        // not needed
    }

    @Override
    public void dispose() {
        // not needed
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        // not needed
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
        // not needed

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
        return ((MeasuringPoint) element).getStringRepresentation();
    }

}
