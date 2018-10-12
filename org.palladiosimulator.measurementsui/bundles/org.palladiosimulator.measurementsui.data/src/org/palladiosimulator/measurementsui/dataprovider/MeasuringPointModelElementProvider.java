package org.palladiosimulator.measurementsui.dataprovider;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

/**
 * Provider for all model elements which are needed in the measuring point wizard pages
 * 
 * @author Domas Mikalkinas
 *
 */
public class MeasuringPointModelElementProvider {
    private DataApplication dataApplication = DataApplication.getInstance();

    /**
     * iterates over all system elements and returns all assembly contexts
     * 
     * @return List<AssemblyContext>
     */
    public List<AssemblyContext> getAssemblyContexts() {

        return dataApplication.getModelAccessor().getSystem().stream()
                .flatMap(e -> e.getAssemblyContexts__ComposedStructure().stream())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all resource environments and returns all resource containers
     * 
     * @return List<ResourceContainer>
     */
    public List<ResourceContainer> getResourceContainer() {

        return dataApplication.getModelAccessor().getResourceEnvironment().stream()
                .flatMap(e -> e.getResourceContainer_ResourceEnvironment().stream())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all resource containers and returns all active resource specifications
     * 
     * @return List<ProcessingResourceSpecification>
     */
    public List<ProcessingResourceSpecification> getActiveResources() {

        return getResourceContainer().stream()
                .flatMap(e -> e.getActiveResourceSpecifications_ResourceContainer().stream())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all resource environments and returns all linking resources
     * 
     * @return List<LinkingResource>
     */
    public List<LinkingResource> getLinkingResources() {

        return dataApplication.getModelAccessor().getResourceEnvironment().stream()
                .flatMap(e -> e.getLinkingResources__ResourceEnvironment().stream())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all usage models and returns all usage scenarios
     * 
     * @return List<UsageScenario>
     */
    public List<UsageScenario> getUsageScenarios() {

        return dataApplication.getModelAccessor().getUsageModel().stream()
                .flatMap(e -> e.getUsageScenario_UsageModel().stream())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all usage scenarios and returns all entry level system calls
     * 
     * @return List<AbstractUserAction>
     */
    public List<AbstractUserAction> getEntryLevelSystemCalls() {

        List<AbstractUserAction> allActions = new LinkedList<>();
        List<AbstractUserAction> onlyEntryLevelSystemCalls = new LinkedList<>();
        allActions
                .addAll(getUsageScenarios().stream()
                        .flatMap(e -> e.getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour().stream())
                        .filter(e -> e instanceof EntryLevelSystemCall || e instanceof Branch
                                || e instanceof BranchTransition || e instanceof Loop)
                        .collect(Collectors.toCollection(LinkedList::new)));

        for (AbstractUserAction action : allActions) {
            if (action instanceof EntryLevelSystemCall) {
                onlyEntryLevelSystemCalls.add(action);
            } else if (action instanceof Branch) {

                onlyEntryLevelSystemCalls.addAll(getBranchNestedEntryLevelSystemCalls((Branch) action));
            } else if (action instanceof Loop) {
                onlyEntryLevelSystemCalls.addAll(((Loop) action).getBodyBehaviour_Loop().getActions_ScenarioBehaviour()
                        .stream().filter(e -> e instanceof EntryLevelSystemCall)
                        .collect(Collectors.toCollection(LinkedList::new)));
            } else if (action instanceof BranchTransition) {
                onlyEntryLevelSystemCalls.addAll(((BranchTransition) action).getBranchedBehaviour_BranchTransition()
                        .getActions_ScenarioBehaviour().stream().filter(e -> e instanceof EntryLevelSystemCall)
                        .collect(Collectors.toCollection(LinkedList::new)));
            }
        }
        return onlyEntryLevelSystemCalls;

    }

    /**
     * gets nested branch elements
     * 
     * @param branch
     *            the branch to investigate
     * @return list of deeper nested Abstract user actions
     */
    private List<AbstractUserAction> getBranchNestedEntryLevelSystemCalls(Branch branch) {
        List<AbstractUserAction> actions = new LinkedList<>();
        for (BranchTransition trans : branch.getBranchTransitions_Branch()) {
            actions.addAll(trans.getBranchedBehaviour_BranchTransition().getActions_ScenarioBehaviour().stream()
                    .filter(e -> e instanceof EntryLevelSystemCall).collect(Collectors.toCollection(LinkedList::new)));
        }

        return actions;

    }

    /**
     * iterates over all seffs and returns all external call action
     * 
     * @return List<EObject>
     */
    public List<EObject> getExternalCallActions() {

        return getSeffs().stream().flatMap(e -> e.eContents().stream()).filter(e -> e instanceof ExternalCallAction)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all repositories and returns all basic components
     * 
     * @return List<EObject>
     */
    public List<EObject> getComponents() {

        return dataApplication.getModelAccessor().getRepository().stream().flatMap(e -> e.eContents().stream())
                .filter(e -> e instanceof BasicComponent).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all basic components and returns all seffs
     * 
     * @return List<EObject>
     */
    public List<EObject> getSeffs() {

        return getComponents().stream().flatMap(e -> e.eContents().stream())
                .filter(e -> e instanceof ResourceDemandingSEFF).collect(Collectors.toCollection(LinkedList::new));
    }

}
