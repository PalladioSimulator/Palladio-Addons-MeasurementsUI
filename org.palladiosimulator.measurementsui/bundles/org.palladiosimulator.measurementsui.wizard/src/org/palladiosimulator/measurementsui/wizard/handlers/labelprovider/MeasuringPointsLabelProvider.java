package org.palladiosimulator.measurementsui.wizard.handlers.labelprovider;

import java.util.LinkedList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.provider.AssemblyContextItemProvider;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.provider.PcmItemProviderAdapterFactory;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourceenvironment.provider.LinkingResourceItemProvider;
import org.palladiosimulator.pcm.resourceenvironment.provider.ResourceContainerItemProvider;
import org.palladiosimulator.pcm.resourceenvironment.provider.ResourceEnvironmentItemProvider;
import org.palladiosimulator.pcm.resourcetype.provider.ProcessingResourceTypeItemProvider;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.provider.ExternalCallActionItemProvider;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.subsystem.provider.SubSystemItemProvider;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.provider.SystemItemProvider;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.provider.EntryLevelSystemCallItemProvider;
import org.palladiosimulator.pcm.usagemodel.provider.UsageModelItemProvider;

/**
 * label provider for the first wizard page of the measuringpoint creation process
 * 
 * @author Domas Mikalkinas
 *
 */
public class MeasuringPointsLabelProvider implements ILabelProvider {

    private static final String LOCATED_IN = " located in ";

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
        PcmItemProviderAdapterFactory factory = new PcmItemProviderAdapterFactory();

        if (element instanceof LinkedList) {
            return null;
        } else {
            EObject object = (EObject) element;
            if (object instanceof UsageScenario) {
                UsageModelItemProvider mod = new UsageModelItemProvider(factory);
                return ExtendedImageRegistry.getInstance().getImage(mod.getImage(object));
            } else if (object instanceof ResourceEnvironment) {
                ResourceEnvironmentItemProvider mod = new ResourceEnvironmentItemProvider(factory);
                return ExtendedImageRegistry.getInstance().getImage(mod.getImage(object));

            } else if (object instanceof System) {
                SystemItemProvider mod = new SystemItemProvider(factory);
                return ExtendedImageRegistry.getInstance().getImage(mod.getImage(object));
            } else if (object instanceof AssemblyContext) {
                AssemblyContextItemProvider mod = new AssemblyContextItemProvider(factory);
                return ExtendedImageRegistry.getInstance().getImage(mod.getImage(object));
            } else if (object instanceof ResourceContainer) {
                ResourceContainerItemProvider mod = new ResourceContainerItemProvider(factory);
                return ExtendedImageRegistry.getInstance().getImage(mod.getImage(object));
            } else if (object instanceof ProcessingResourceSpecification) {
                ProcessingResourceTypeItemProvider mod = new ProcessingResourceTypeItemProvider(factory);
                return ExtendedImageRegistry.getInstance().getImage(mod.getImage(object));
            } else if (object instanceof LinkingResource) {
                LinkingResourceItemProvider mod = new LinkingResourceItemProvider(factory);
                return ExtendedImageRegistry.getInstance().getImage(mod.getImage(object));
            } else if (object instanceof ExternalCallAction) {
                ExternalCallActionItemProvider mod = new ExternalCallActionItemProvider(factory);
                return ExtendedImageRegistry.getInstance().getImage(mod.getImage(object));
            } else if (object instanceof EntryLevelSystemCall) {
                EntryLevelSystemCallItemProvider mod = new EntryLevelSystemCallItemProvider(factory);
                return ExtendedImageRegistry.getInstance().getImage(mod.getImage(object));
            } else if (object instanceof SubSystem) {
                SubSystemItemProvider mod = new SubSystemItemProvider(factory);
                return ExtendedImageRegistry.getInstance().getImage(mod.getImage(object));
            }

        }

        return null;
    }

    @Override
    public String getText(Object element) {
        if (element instanceof LinkedList) {
            if (!((LinkedList<?>) element).isEmpty()) {
                return (((LinkedList<?>) element).get(0).getClass().getSimpleName().replaceAll("Impl", "")
                        .replaceAll("([A-Z])", " $1"));
            } else {
                return null;
            }

        } else {
            if (element instanceof ProcessingResourceSpecification) {
                return ((ProcessingResourceSpecification) element).getActiveResourceType_ActiveResourceSpecification()
                        .getEntityName() + LOCATED_IN
                        + ((ProcessingResourceSpecification) element)
                                .getResourceContainer_ProcessingResourceSpecification().getEntityName();

            } else if (element instanceof AssemblyContext) {
                return ((AssemblyContext) element).getEntityName() + LOCATED_IN
                        + ((AssemblyContext) element).getParentStructure__AssemblyContext().getEntityName();

            } else if (element instanceof ResourceContainer) {
                return ((ResourceContainer) element).getEntityName() + LOCATED_IN
                        + ((ResourceContainer) element).getResourceEnvironment_ResourceContainer().getEntityName();

            } else if (element instanceof LinkingResource) {
                return ((LinkingResource) element).getEntityName() + LOCATED_IN
                        + ((LinkingResource) element).getResourceEnvironment_LinkingResource().getEntityName();

            } else if (element instanceof ExternalCallAction) {
                NamedElement externalCallAction = (NamedElement) ((ExternalCallAction) element)
                        .getResourceDemandingBehaviour_AbstractAction().eContainer();
                ResourceDemandingSEFF resourceDemandingSEFF = (ResourceDemandingSEFF) ((ExternalCallAction) element)
                        .eContainer();
                return ((ExternalCallAction) element).getEntityName() + " from the "
                        + resourceDemandingSEFF.toString().replace("[TRANSIENT]", "") + LOCATED_IN
                        + externalCallAction.getEntityName();

            } else if (element instanceof EntryLevelSystemCall) {
                NamedElement entryLevelSystemCall = (NamedElement) ((EntryLevelSystemCall) element)
                        .getScenarioBehaviour_AbstractUserAction().eContainer();
                ScenarioBehaviour scenarioBehaviour = ((EntryLevelSystemCall) element)
                        .getScenarioBehaviour_AbstractUserAction();
                return ((EntryLevelSystemCall) element).getEntityName() + " from the "
                        + scenarioBehaviour.getEntityName() + LOCATED_IN + entryLevelSystemCall.getEntityName();

            } else if (element instanceof UsageScenario) {
                return ((UsageScenario) element).getEntityName() + LOCATED_IN
                        + ((UsageScenario) element).getUsageModel_UsageScenario().toString().replace("[TRANSIENT]", "");

            }

            return ((NamedElement) element).getEntityName();

        }

    }

}
