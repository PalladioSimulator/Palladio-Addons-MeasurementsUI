package org.palladiosimulator.measurementsui.wizard.util;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.measurementsui.util.MeasurementsSwitch;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

public class TextProviderSwitch extends MeasurementsSwitch<String> {

    private static final String FROM_THE = " from the ";
    private static final String TRANSIENT = "[TRANSIENT]";
    private static final String LOCATED_IN = " located in ";

    @Override
    public String caseProcessingResourceSpecification(ProcessingResourceSpecification processingResourceSpecification) {
        return (processingResourceSpecification).getActiveResourceType_ActiveResourceSpecification().getEntityName()
                + LOCATED_IN + (processingResourceSpecification).getResourceContainer_ProcessingResourceSpecification()
                        .getEntityName();
    }

    @Override
    public String caseAssemblyContext(AssemblyContext assemblyContext) {
        return (assemblyContext).getEntityName() + LOCATED_IN
                + (assemblyContext).getParentStructure__AssemblyContext().getEntityName();
    }

    @Override
    public String caseResourceContainer(ResourceContainer resourceContainer) {
        return (resourceContainer).getEntityName() + LOCATED_IN
                + (resourceContainer).getResourceEnvironment_ResourceContainer().getEntityName();
    }

    @Override
    public String caseLinkingResource(LinkingResource linkingResource) {
        return (linkingResource).getEntityName() + LOCATED_IN
                + (linkingResource).getResourceEnvironment_LinkingResource().getEntityName();
    }

    @Override
    public String caseExternalCallAction(ExternalCallAction externalCallAction) {
        NamedElement resourceDemandingBehaviour = (NamedElement) (externalCallAction)
                .getResourceDemandingBehaviour_AbstractAction().eContainer();
        ResourceDemandingSEFF resourceDemandingSEFF = (ResourceDemandingSEFF) (externalCallAction).eContainer();
        return (externalCallAction).getEntityName() + FROM_THE + resourceDemandingSEFF.toString().replace(TRANSIENT, "")
                + LOCATED_IN + resourceDemandingBehaviour.getEntityName();
    }

    @Override
    public String caseEntryLevelSystemCall(EntryLevelSystemCall entryLevelSystemCall) {
        if ((entryLevelSystemCall).getScenarioBehaviour_AbstractUserAction().eContainer() instanceof BranchTransition) {
            BranchTransition branchTransition = (BranchTransition) (entryLevelSystemCall)
                    .getScenarioBehaviour_AbstractUserAction().eContainer();
            ScenarioBehaviour scenarioBehaviour = (entryLevelSystemCall).getScenarioBehaviour_AbstractUserAction();
            return (entryLevelSystemCall).getEntityName() + FROM_THE + scenarioBehaviour.getEntityName() + LOCATED_IN
                    + branchTransition.toString().replace(TRANSIENT, "");
        } else {
            NamedElement scenarioBehaviourContainer = (NamedElement) (entryLevelSystemCall)
                    .getScenarioBehaviour_AbstractUserAction().eContainer();
            ScenarioBehaviour scenarioBehaviour = (entryLevelSystemCall).getScenarioBehaviour_AbstractUserAction();
            return (entryLevelSystemCall).getEntityName() + FROM_THE + scenarioBehaviour.getEntityName() + LOCATED_IN
                    + scenarioBehaviourContainer.getEntityName();
        }
    }

    @Override
    public String caseUsageScenario(UsageScenario usageScenario) {
        return (usageScenario).getEntityName() + LOCATED_IN
                + (usageScenario).getUsageModel_UsageScenario().toString().replace(TRANSIENT, "");
    }

    @Override
    public String defaultCase(EObject eObject) {
        return ((NamedElement) eObject).getEntityName();
    }
}
