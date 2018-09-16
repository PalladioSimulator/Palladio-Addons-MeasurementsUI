package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.palladiosimulator.pcm.core.entity.NamedElement;

/**
 * 
 * @author Domas Mikalkinas
 *
 */
public class AdditionalMeasuringpointLabelProvider implements ILabelProvider {

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
		EObject object = (EObject) element;
		EcoreItemProviderAdapterFactory factory = new EcoreItemProviderAdapterFactory();
		if (factory.isFactoryForType(IItemLabelProvider.class)) {
			IItemLabelProvider labelProvider = (IItemLabelProvider) factory.adapt(object, IItemLabelProvider.class);
			if (labelProvider != null) {
				URI test = (URI) labelProvider.getImage(object);

				return ExtendedImageRegistry.getInstance().getImage(test);
			}
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		return ((NamedElement) element).getEntityName() + " ["
				+ element.getClass().getSimpleName().replaceAll("Impl", "") + "]";
	}

}
