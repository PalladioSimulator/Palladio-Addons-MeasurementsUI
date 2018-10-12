package org.palladiosimulator.measurementsui.wizard.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.palladiosimulator.measurementsui.util.MeasurementsSwitch;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.provider.AssemblyContextItemProvider;
import org.palladiosimulator.pcm.provider.PcmItemProviderAdapterFactory;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.repository.provider.BasicComponentItemProvider;
import org.palladiosimulator.pcm.repository.provider.OperationProvidedRoleItemProvider;
import org.palladiosimulator.pcm.repository.provider.OperationRequiredRoleItemProvider;
import org.palladiosimulator.pcm.repository.provider.OperationSignatureItemProvider;
import org.palladiosimulator.pcm.repository.provider.PassiveResourceItemProvider;
import org.palladiosimulator.pcm.repository.provider.RepositoryItemProvider;
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
import org.palladiosimulator.pcm.seff.provider.ResourceDemandingSEFFItemProvider;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.subsystem.provider.SubSystemItemProvider;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.provider.SystemItemProvider;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.provider.BranchItemProvider;
import org.palladiosimulator.pcm.usagemodel.provider.BranchTransitionItemProvider;
import org.palladiosimulator.pcm.usagemodel.provider.EntryLevelSystemCallItemProvider;
import org.palladiosimulator.pcm.usagemodel.provider.LoopItemProvider;
import org.palladiosimulator.pcm.usagemodel.provider.ScenarioBehaviourItemProvider;
import org.palladiosimulator.pcm.usagemodel.provider.UsageModelItemProvider;

public class ImageProviderSwitch extends MeasurementsSwitch<Image> {

    PcmItemProviderAdapterFactory factory = new PcmItemProviderAdapterFactory();

    @Override
    public Image caseUsageScenario(UsageScenario usageScenario) {
        UsageModelItemProvider usageModelItemProvider = new UsageModelItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(usageModelItemProvider.getImage(usageScenario));
    }

    @Override
    public Image caseResourceEnvironment(ResourceEnvironment resourceEnvironment) {
        ResourceEnvironmentItemProvider resourceEnvironmentItemProvider = new ResourceEnvironmentItemProvider(factory);
        return ExtendedImageRegistry.getInstance()
                .getImage(resourceEnvironmentItemProvider.getImage(resourceEnvironment));
    }

    @Override
    public Image caseSystem(System system) {
        SystemItemProvider systemItemProvider = new SystemItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(systemItemProvider.getImage(system));
    }

    @Override
    public Image caseAssemblyContext(AssemblyContext assemblyContext) {
        AssemblyContextItemProvider assemblyContextItemProvider = new AssemblyContextItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(assemblyContextItemProvider.getImage(assemblyContext));
    }

    @Override
    public Image caseResourceContainer(ResourceContainer resourceContainer) {
        ResourceContainerItemProvider resourceContainerItemProvider = new ResourceContainerItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(resourceContainerItemProvider.getImage(resourceContainer));
    }

    @Override
    public Image caseProcessingResourceSpecification(ProcessingResourceSpecification processingResourceSpecification) {
        ProcessingResourceTypeItemProvider processingResourceTypeItemProvider = new ProcessingResourceTypeItemProvider(
                factory);
        return ExtendedImageRegistry.getInstance()
                .getImage(processingResourceTypeItemProvider.getImage(processingResourceSpecification));
    }

    @Override
    public Image caseLinkingResource(LinkingResource linkingResource) {
        LinkingResourceItemProvider linkingResourceItemProvider = new LinkingResourceItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(linkingResourceItemProvider.getImage(linkingResource));
    }

    @Override
    public Image caseExternalCallAction(ExternalCallAction externalCallAction) {
        ExternalCallActionItemProvider externalCallActionItemProvider = new ExternalCallActionItemProvider(factory);
        return ExtendedImageRegistry.getInstance()
                .getImage(externalCallActionItemProvider.getImage(externalCallAction));
    }

    @Override
    public Image caseEntryLevelSystemCall(EntryLevelSystemCall entryLevelSystemCall) {
        EntryLevelSystemCallItemProvider entryLevelSystemCallItemProvider = new EntryLevelSystemCallItemProvider(
                factory);
        return ExtendedImageRegistry.getInstance()
                .getImage(entryLevelSystemCallItemProvider.getImage(entryLevelSystemCall));
    }

    @Override
    public Image caseSubSystem(SubSystem subSystem) {
        SubSystemItemProvider subSystemItemProvider = new SubSystemItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(subSystemItemProvider.getImage(subSystem));
    }

    @Override
    public Image casePassiveResource(PassiveResource passiveResource) {
        PassiveResourceItemProvider passiveResourceItemProvider = new PassiveResourceItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(passiveResourceItemProvider.getImage(passiveResource));
    }

    @Override
    public Image caseRepository(Repository resourceContainer) {
        RepositoryItemProvider repositoryItemProvider = new RepositoryItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(repositoryItemProvider.getImage(resourceContainer));
    }

    @Override
    public Image caseBasicComponent(BasicComponent basicComponent) {
        BasicComponentItemProvider basicComponentItemProvider = new BasicComponentItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(basicComponentItemProvider.getImage(basicComponent));
    }

    @Override
    public Image caseResourceDemandingSEFF(ResourceDemandingSEFF resourceDemandingSeff) {
        ResourceDemandingSEFFItemProvider resourceDemandingSEFFItemProvider = new ResourceDemandingSEFFItemProvider(
                factory);
        return ExtendedImageRegistry.getInstance()
                .getImage(resourceDemandingSEFFItemProvider.getImage(resourceDemandingSeff));
    }

    @Override
    public Image caseScenarioBehaviour(ScenarioBehaviour scenarioBehaviour) {
        ScenarioBehaviourItemProvider scenarioBehaviourItemProvider = new ScenarioBehaviourItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(scenarioBehaviourItemProvider.getImage(scenarioBehaviour));
    }

    @Override
    public Image caseUsageModel(UsageModel usageModel) {
        UsageModelItemProvider usageModelItemProvider = new UsageModelItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(usageModelItemProvider.getImage(usageModel));
    }

    @Override
    public Image caseBranch(Branch branch) {
        BranchItemProvider branchItemProvider = new BranchItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(branchItemProvider.getImage(branch));
    }

    @Override
    public Image caseBranchTransition(BranchTransition branchTransition) {
        BranchTransitionItemProvider branchTransitionItemProvider = new BranchTransitionItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(branchTransitionItemProvider.getImage(branchTransition));
    }

    @Override
    public Image caseLoop(Loop loop) {
        LoopItemProvider loopItemProvider = new LoopItemProvider(factory);
        return ExtendedImageRegistry.getInstance().getImage(loopItemProvider.getImage(loop));
    }

    @Override
    public Image caseOperationSignature(OperationSignature operationSignature) {
        OperationSignatureItemProvider operationSignatureItemProvider = new OperationSignatureItemProvider(factory);
        return ExtendedImageRegistry.getInstance()
                .getImage(operationSignatureItemProvider.getImage(operationSignature));

    }

    @Override
    public Image caseRole(Role role) {

        if (role instanceof OperationRequiredRole) {
            OperationRequiredRoleItemProvider operationRequiredRoleItemProvider = new OperationRequiredRoleItemProvider(
                    factory);
            return ExtendedImageRegistry.getInstance().getImage(operationRequiredRoleItemProvider.getImage(role));
        } else {
            OperationProvidedRoleItemProvider operationProvidedRoleItemProvider = new OperationProvidedRoleItemProvider(
                    factory);
            return ExtendedImageRegistry.getInstance().getImage(operationProvidedRoleItemProvider.getImage(role));
        }

    }

    @Override
    public Image defaultCase(EObject eObject) {
        return null;
    }

}
