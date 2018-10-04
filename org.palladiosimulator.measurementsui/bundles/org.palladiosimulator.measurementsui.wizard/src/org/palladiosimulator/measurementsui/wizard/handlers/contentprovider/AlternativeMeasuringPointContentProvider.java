package org.palladiosimulator.measurementsui.wizard.handlers.contentprovider;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.util.AlternativeMeasuringPointChildrenSwitch;
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
 * An alternative content provider for a hierarchical view of the measuringpoints
 * 
 * @author Domas Mikalkinas
 *
 */
public class AlternativeMeasuringPointContentProvider implements ITreeContentProvider {
    private DataApplication dataApplication = DataApplication.getInstance();

    @Override
    public Object[] getElements(Object inputElement) {

        List<Object> elementList = new LinkedList<>();

        elementList.addAll(dataApplication.getModelAccessor().getRepository().stream().filter(
                e -> (!e.getEntityName().equals("FailureTypes")) && (!e.getEntityName().equals("PrimitiveDataTypes")))
                .collect(Collectors.toCollection(LinkedList::new)));

        elementList.addAll(dataApplication.getModelAccessor().getResourceEnvironment());
        elementList.addAll(dataApplication.getModelAccessor().getSubSystem());
        elementList.addAll(dataApplication.getModelAccessor().getSystem());
        elementList.addAll(dataApplication.getModelAccessor().getUsageModel());
        return elementList.toArray();
    }

    /**
     * evaluates which model is the parent and retrieves all children
     */
    @Override
    public Object[] getChildren(Object parentElement) {
AlternativeMeasuringPointChildrenSwitch childrenSwitch = new AlternativeMeasuringPointChildrenSwitch();
return childrenSwitch.doSwitch((EObject) parentElement);

        
    }

    @Override
    public Object getParent(Object element) {
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof System) {
            return !((System) element).getAssemblyContexts__ComposedStructure().isEmpty();
        } else if (element instanceof ResourceEnvironment) {
            return !((ResourceEnvironment) element).getLinkingResources__ResourceEnvironment().isEmpty()
                    || !((ResourceEnvironment) element).getResourceContainer_ResourceEnvironment().isEmpty();
        } else if (element instanceof ResourceContainer) {
            return !((ResourceContainer) element).getActiveResourceSpecifications_ResourceContainer().isEmpty();
        } else if (element instanceof UsageModel) {
            return !((UsageModel) element).getUsageScenario_UsageModel().isEmpty();
        } else if (element instanceof UsageScenario) {
            return ((UsageScenario) element).getScenarioBehaviour_UsageScenario() != null;
        } else if (element instanceof ScenarioBehaviour) {
            return !((ScenarioBehaviour) element).getActions_ScenarioBehaviour().stream()
                    .filter(e -> e instanceof EntryLevelSystemCall).collect(Collectors.toCollection(LinkedList::new))
                    .isEmpty();
        } else if (element instanceof Repository) {
            return !((Repository) element).eContents().stream().filter(e -> e instanceof BasicComponent)
                    .collect(Collectors.toCollection(LinkedList::new)).isEmpty();
        } else if (element instanceof BasicComponent) {
            return !((BasicComponent) element).getServiceEffectSpecifications__BasicComponent().isEmpty()
                    || !((BasicComponent) element).getPassiveResource_BasicComponent().isEmpty();
        } else if (element instanceof ResourceDemandingSEFF) {
            return !((ResourceDemandingSEFF) element).getSteps_Behaviour().stream()
                    .filter(e -> e instanceof ExternalCallAction).collect(Collectors.toCollection(LinkedList::new))
                    .isEmpty();
        } else if (element instanceof Branch) {
            return !((Branch) element).getBranchTransitions_Branch().isEmpty();
        } else if (element instanceof BranchTransition) {
            return !((BranchTransition) element).getBranchedBehaviour_BranchTransition().getActions_ScenarioBehaviour()
                    .isEmpty();
        } else if (element instanceof Loop) {
            return !((Loop) element).getBodyBehaviour_Loop().getActions_ScenarioBehaviour().isEmpty();
        }
        return false;
    }

}
