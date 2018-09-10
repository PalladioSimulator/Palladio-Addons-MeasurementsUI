package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreEditPlugin;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.EMFEditUIPlugin;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.core.provider.PalladioComponentModelEditPlugin;
import org.palladiosimulator.pcm.core.provider.PalladioComponentModelEditPlugin.Implementation;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

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

		EObject object = (EObject) element;
		EcoreItemProviderAdapterFactory factory = new EcoreItemProviderAdapterFactory();
		if (factory.isFactoryForType(IItemLabelProvider.class)) {
			IItemLabelProvider labelProvider = (IItemLabelProvider) factory.adapt(object, IItemLabelProvider.class);
			if (labelProvider != null) {
				System.out.println(labelProvider.getImage(object));
				URI test = (URI) labelProvider.getImage(object);
				System.out.println(test.path());
				System.out.println(test.scheme());
				// PalladioComponentModelEditPlugin.getPlugin().;
				// EMFEditUIPlugin.getPlugin().getImage(key);
				// return PlatformUI.getWorkbench().getSharedImages().getImage(test.fragment());
				// ImageDescriptor id = ExtendedI
				// EMFEditUIPlugin.getPlugin().
//				 return (Image)	 PalladioComponentModelEditPlugin.getPlugin().getImage(labelProvider.getImage(object).toString());
				System.out.println("wuutwuut "
					+ExtendedImageRegistry.getInstance().getImage(labelProvider.getImage(object)));
				
				return ExtendedImageRegistry.getInstance().getImage(labelProvider.getImage(object));

			}
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		// if (element instanceof Allocation) {
		// return ((Allocation) element).getEntityName();
		// } else if (element instanceof ResourceEnvironment) {
		// return ((ResourceEnvironment)element).getEntityName();
		//// EObject object = (EObject) element;
		//// EcoreItemProviderAdapterFactory factory = new
		// EcoreItemProviderAdapterFactory();
		//// if (factory.isFactoryForType(IItemLabelProvider.class)) {
		//// IItemLabelProvider labelProvider = (IItemLabelProvider)
		// factory.adapt(object, IItemLabelProvider.class);
		//// if (labelProvider != null) {
		//// return labelProvider.getText(object);
		//// }
		//// }
		//// return ((ResourceEnvironment) element).getEntityName();
		// } else if (element instanceof org.palladiosimulator.pcm.system.System) {
		// return ((org.palladiosimulator.pcm.system.System) element).getEntityName();
		// } else if (element instanceof Repository) {
		// return ((Repository) element).getEntityName();
		// } else if (element instanceof UsageModel) {
		// return ((UsageModel)
		// element).getUsageScenario_UsageModel().get(0).getEntityName();
		// } else if (element instanceof AssemblyContext) {
		// return ((AssemblyContext)element).getEntityName();
		// }
		// return null;
		if (element instanceof ProcessingResourceSpecification) {
			return ((ProcessingResourceSpecification) element).getActiveResourceType_ActiveResourceSpecification()
					.getEntityName() + " [" + element.getClass().getSimpleName().replaceAll("Impl", "") + "]";
		} else {
			return ((NamedElement) element).getEntityName() + " ["
					+ element.getClass().getSimpleName().replaceAll("Impl", "") + "]";
		}

	}

}
