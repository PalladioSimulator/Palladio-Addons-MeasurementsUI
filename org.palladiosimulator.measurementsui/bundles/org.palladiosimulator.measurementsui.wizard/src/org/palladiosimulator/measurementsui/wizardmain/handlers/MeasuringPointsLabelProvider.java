package org.palladiosimulator.measurementsui.wizardmain.handlers;

import java.util.LinkedList;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreEditPlugin;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;

/**
 * 
 * @author Domas Mikalkinas
 *
 */
public class MeasuringPointsLabelProvider implements ILabelProvider {
	EMFPlugin plug = new EcoreEditPlugin();

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
		if (element instanceof LinkedList) {
			return null;
		} else {
			EObject object = (EObject) element;
			EcoreItemProviderAdapterFactory factory = new EcoreItemProviderAdapterFactory();
			if (factory.isFactoryForType(IItemLabelProvider.class)) {
				IItemLabelProvider labelProvider = (IItemLabelProvider) factory.adapt(object, IItemLabelProvider.class);
				if (labelProvider != null) {
					URI test = (URI) labelProvider.getImage(object);

					return ExtendedImageRegistry.getInstance().getImage(test);
				}
			}
		}

		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof LinkedList) {
			if (!((LinkedList) element).isEmpty()) {
				return (((LinkedList) element).get(0).getClass().getSimpleName().replaceAll("Impl", "")
						.replaceAll("([A-Z])", " $1"));
			} else {
				return null;
			}

			// return "Liste";
		} else {
			if (element instanceof ProcessingResourceSpecification) {
				return ((ProcessingResourceSpecification) element).getActiveResourceType_ActiveResourceSpecification()
						.getEntityName();

			} else {
				return ((NamedElement) element).getEntityName();
			}
		}

	}

}
