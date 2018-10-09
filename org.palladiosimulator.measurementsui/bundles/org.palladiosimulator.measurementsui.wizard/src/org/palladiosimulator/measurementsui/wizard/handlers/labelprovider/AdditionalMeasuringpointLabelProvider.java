package org.palladiosimulator.measurementsui.wizard.handlers.labelprovider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.palladiosimulator.measurementsui.wizard.util.ImageProviderSwitch;
import org.palladiosimulator.pcm.core.entity.NamedElement;

/**
 * A label provider for the second step of the measuringpoint creation wizard pages
 * 
 * @author Domas Mikalkinas
 *
 */
public class AdditionalMeasuringpointLabelProvider implements ILabelProvider {

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
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
        // not needed

    }

    @Override
    public Image getImage(Object element) {
        ImageProviderSwitch imageSwitch = new ImageProviderSwitch();
        return  imageSwitch.doSwitch((EObject) element);
    }

    @Override
    public String getText(Object element) {
        return ((NamedElement) element).getEntityName() + " ["
                + element.getClass().getSimpleName().replaceAll("Impl", "") + "]";
    }

}
