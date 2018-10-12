package org.palladiosimulator.measurementsui.wizard.handlers.labelprovider;

import java.util.LinkedList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.palladiosimulator.measurementsui.wizard.util.ImageProviderSwitch;
import org.palladiosimulator.measurementsui.wizard.util.TextProviderSwitch;

/**
 * label provider for the first wizard page of the measuringpoint creation process
 * 
 * @author Domas Mikalkinas
 *
 */
public class MeasuringPointsLabelProvider implements ILabelProvider, IFontProvider {

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

        if (element instanceof LinkedList) {
            return null;
        } else {
            return imageSwitch.doSwitch((EObject) element);
        }
    }

    @Override
    public String getText(Object element) {
        TextProviderSwitch textSwitch = new TextProviderSwitch();

        if (element instanceof LinkedList && !((LinkedList<?>) element).isEmpty()) {

            return (((LinkedList<?>) element).get(0).getClass().getSimpleName().replaceAll("Impl", "")
                    .replaceAll("([A-Z])", " $1"));

        } else {
            return textSwitch.doSwitch((EObject) element);
        }

    }

    @Override
    public Font getFont(Object element) {
        if (!(element instanceof LinkedList)) {

            return JFaceResources.getBannerFont();
        }
        return null;

    }

}
