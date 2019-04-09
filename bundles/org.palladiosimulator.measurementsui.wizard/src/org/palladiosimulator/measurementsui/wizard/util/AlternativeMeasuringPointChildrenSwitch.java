package org.palladiosimulator.measurementsui.wizard.util;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.measurementsui.util.MeasurementsSwitch;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

/**
 * This is a switch to get all children of the alternative (hierarchical) measuringpoint view
 * 
 * @author Domas Mikalkinas
 *
 */
public class AlternativeMeasuringPointChildrenSwitch extends MeasurementsSwitch<Object[]> {

    @Override
    public Object[] caseSystem(System system) {
        return system.getAssemblyContexts__ComposedStructure().toArray();
    }

    @Override
    public Object[] caseResourceEnvironment(ResourceEnvironment resourceEnvironment) {
        List<Object> elements = new LinkedList<>();
        elements.addAll(resourceEnvironment.getLinkingResources__ResourceEnvironment());
        elements.addAll(resourceEnvironment.getResourceContainer_ResourceEnvironment());
        return elements.toArray();
    }

    @Override
    public Object[] caseResourceContainer(ResourceContainer resourceContainer) {
        return resourceContainer.getActiveResourceSpecifications_ResourceContainer().toArray();
    }

    @Override
    public Object[] caseUsageModel(UsageModel usageModel) {
        return usageModel.getUsageScenario_UsageModel().toArray();
    }

    @Override
    public Object[] caseUsageScenario(UsageScenario usageScenario) {
        return new Object[] { usageScenario.getScenarioBehaviour_UsageScenario() };
    }

    @Override
    public Object[] caseScenarioBehaviour(ScenarioBehaviour scenarioBehaviour) {
        return scenarioBehaviour
                .getActions_ScenarioBehaviour().stream().filter(e -> e instanceof EntryLevelSystemCall
                        || e instanceof Branch || e instanceof BranchTransition || e instanceof Loop)
                .collect(Collectors.toCollection(LinkedList::new)).toArray();
    }

    @Override
    public Object[] caseRepository(Repository resourceContainer) {
        return resourceContainer.eContents().stream().filter(e -> e instanceof BasicComponent)
                .collect(Collectors.toCollection(LinkedList::new)).toArray();
    }

    @Override
    public Object[] caseBasicComponent(BasicComponent basicComponent) {
        List<Object> appendingBasicComponentObjects = new LinkedList<>();
        appendingBasicComponentObjects.addAll(basicComponent.getServiceEffectSpecifications__BasicComponent());
        return appendingBasicComponentObjects.toArray();
    }

    @Override
    public Object[] caseResourceDemandingSEFF(ResourceDemandingSEFF resourceDemandingSeff) {
        return resourceDemandingSeff.getSteps_Behaviour().stream().filter(e -> e instanceof ExternalCallAction)
                .collect(Collectors.toCollection(LinkedList::new)).toArray();
    }

    @Override
    public Object[] caseBranch(Branch branch) {
        return branch.getBranchTransitions_Branch().toArray();
    }

    @Override
    public Object[] caseBranchTransition(BranchTransition branchTransition) {
        return new Object[] { branchTransition.getBranchedBehaviour_BranchTransition() };

    }

    @Override
    public Object[] caseLoop(Loop loop) {
        return loop.getBodyBehaviour_Loop().getActions_ScenarioBehaviour().stream()
                .filter(e -> e instanceof EntryLevelSystemCall || e instanceof Branch || e instanceof BranchTransition
                        || e instanceof Loop)
                .collect(Collectors.toCollection(LinkedList::new)).toArray();
    }

    @Override
    public Object[] defaultCase(EObject eObject) {
        return new Object[0];
    }

}
