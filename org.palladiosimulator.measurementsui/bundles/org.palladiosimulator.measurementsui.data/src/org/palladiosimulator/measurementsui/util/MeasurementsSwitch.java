package org.palladiosimulator.measurementsui.util;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

/**
 * An adapted version of a switch class. It behaves similar to proper switch classes, but it doesn't
 * call any other Switch-classes. It uses instanceof to differentiate between all cases, because
 * there is no other possibility to differentiate between the used models. The models come from
 * different ePackages, so they all have their own proper Switch-class. Some models don't have any
 * Switch-class at all. The common superclass of all classes combined is eObject, so there is no
 * common value you could use to implement a proper emf switch class. Alternatively you could use
 * the EObjects classifierId, but it changes at runtime, so this is can not be used as a proper
 * switch implementation, too.
 * 
 * @author Domas Mikalkinas
 *
 * @param <T>
 */
public class MeasurementsSwitch<T> {

    /**
     * the "switch"-method of this "switch". It checks, which object is passed and calls the fitting
     * caseXXX method. It can differentiate between all the models, which are used in the
     * measurementsui.
     * 
     * @param theEObject
     *            the object to determine its class to
     * @return T an object which you can specify when inheriting from this class
     */
    public T doSwitch(final EObject theEObject) {
        if (theEObject instanceof ResourceContainer) {
            final ResourceContainer resourceContainer = (ResourceContainer) theEObject;
            T result = this.caseResourceContainer(resourceContainer);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof ProcessingResourceSpecification) {
            final ProcessingResourceSpecification processingResourceSpecification = (ProcessingResourceSpecification) theEObject;
            T result = this.caseProcessingResourceSpecification(processingResourceSpecification);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof AssemblyContext) {
            final AssemblyContext assemblyContext = (AssemblyContext) theEObject;
            T result = this.caseAssemblyContext(assemblyContext);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof PassiveResource) {
            final PassiveResource passiveResource = (PassiveResource) theEObject;
            T result = this.casePassiveResource(passiveResource);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof OperationSignature) {
            final OperationSignature operationSignature = (OperationSignature) theEObject;
            T result = this.caseOperationSignature(operationSignature);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof Role) {
            final Role role = (Role) theEObject;
            T result = this.caseRole(role);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof EntryLevelSystemCall) {
            final EntryLevelSystemCall entryLevelSystemCall = (EntryLevelSystemCall) theEObject;
            T result = this.caseEntryLevelSystemCall(entryLevelSystemCall);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof ExternalCallAction) {
            final ExternalCallAction externalCallAction = (ExternalCallAction) theEObject;
            T result = this.caseExternalCallAction(externalCallAction);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof LinkingResource) {
            final LinkingResource linkingResource = (LinkingResource) theEObject;
            T result = this.caseLinkingResource(linkingResource);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof ResourceEnvironment) {

            final ResourceEnvironment resourceEnvironment = (ResourceEnvironment) theEObject;

            T result = this.caseResourceEnvironment(resourceEnvironment);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof SubSystem) {
            final SubSystem subSystem = (SubSystem) theEObject;
            T result = this.caseSubSystem(subSystem);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof System) {
            final System system = (System) theEObject;
            T result = this.caseSystem(system);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof UsageScenario) {
            final UsageScenario usageScenario = (UsageScenario) theEObject;
            T result = this.caseUsageScenario(usageScenario);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof Branch) {
            final Branch branch = (Branch) theEObject;
            T result = this.caseBranch(branch);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof Loop) {
            final Loop loop = (Loop) theEObject;
            T result = this.caseLoop(loop);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof BranchTransition) {
            final BranchTransition branchTransition = (BranchTransition) theEObject;
            T result = this.caseBranchTransition(branchTransition);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof Repository) {
            final Repository repository = (Repository) theEObject;
            T result = this.caseRepository(repository);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof ScenarioBehaviour) {
            final ScenarioBehaviour scenarioBehaviour = (ScenarioBehaviour) theEObject;
            T result = this.caseScenarioBehaviour(scenarioBehaviour);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof BasicComponent) {
            final BasicComponent basicComponent = (BasicComponent) theEObject;
            T result = this.caseBasicComponent(basicComponent);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof UsageModel) {
            final UsageModel usageModel = (UsageModel) theEObject;
            T result = this.caseUsageModel(usageModel);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof ResourceDemandingSEFF) {
            final ResourceDemandingSEFF resourceDemandingSeff = (ResourceDemandingSEFF) theEObject;
            T result = this.caseResourceDemandingSEFF(resourceDemandingSeff);
            result = useDefaultCase(theEObject, result);
            return result;
        } else if (theEObject instanceof MeasuringPoint) {
            final MeasuringPoint measuringpoint = (MeasuringPoint) theEObject;
            T result = this.caseMeasuringPoint(measuringpoint);
            result = useDefaultCase(theEObject, result);
            return result;
        }
        return null;

    }

    /**
     * The case for measuringpoints. You should override it and add your own logic before using the
     * switch.
     * 
     * @param measuringpoint
     *            the given measuringpoint
     * @return T
     */
    public T caseMeasuringPoint(MeasuringPoint measuringpoint) {
        return null;
    }

    /**
     * The case for resource demanding SEFFs. You should override it and add your own logic before
     * using the switch.
     * 
     * @param resourceDemandingSeff
     *            the given resource demanding SEFF
     * @return T
     */
    public T caseResourceDemandingSEFF(ResourceDemandingSEFF resourceDemandingSeff) {
        return null;
    }

    /**
     * The case for usage models. You should override it and add your own logic before using the
     * switch.
     * 
     * @param usageModel
     *            the given usage model
     * @return T
     */
    public T caseUsageModel(UsageModel usageModel) {
        return null;
    }

    /**
     * The case for basic components. You should override it and add your own logic before using the
     * switch.
     * 
     * @param basicComponent
     *            the given basic component
     * @return T
     */
    public T caseBasicComponent(BasicComponent basicComponent) {
        return null;
    }

    /**
     * The case for scenario behaviours. You should override it and add your own logic before using
     * the switch.
     * 
     * @param scenarioBehaviour
     *            the given scenario behaviour
     * @return T
     */
    public T caseScenarioBehaviour(ScenarioBehaviour scenarioBehaviour) {
        return null;
    }

    /**
     * The case for repositories. You should override it and add your own logic before using the
     * switch.
     * 
     * @param repository
     *            the given repository
     * @return T
     */
    public T caseRepository(Repository repository) {
        return null;
    }

    /**
     * The case for branch transitions. You should override it and add your own logic before using
     * the switch.
     * 
     * @param branchTransition
     *            the given branch transition
     * @return T 
     */
    public T caseBranchTransition(BranchTransition branchTransition) {

        return null;
    }

    /**
     * The case for loops. You should override it and add your own logic before using the switch.
     * 
     * @param loop
     *            the given loop
     * @return T
     */
    public T caseLoop(Loop loop) {
        return null;
    }

    /**
     * The case for branches. You should override it and add your own logic before using the switch.
     * 
     * @param branch
     *            the given branch
     * @return T
     */
    public T caseBranch(Branch branch) {
        return null;
    }

    /**
     * The case for usage scenarios. You should override it and add your own logic before using the
     * switch.
     * 
     * @param usageScenario
     *            the given usage scenario
     * @return T
     */
    public T caseUsageScenario(UsageScenario usageScenario) {
        return null;
    }

    /**
     * The case for systems. You should override it and add your own logic before using the switch.
     * 
     * @param system
     *            the given system
     * @return T
     */
    public T caseSystem(System system) {
        return null;
    }

    /**
     * The case for subsystems. You should override it and add your own logic before using the
     * switch.
     * 
     * @param subSystem
     *            the given subsystem
     * @return T
     */
    public T caseSubSystem(SubSystem subSystem) {
        return null;
    }

    /**
     * The case for resource environments. You should override it and add your own logic before
     * using the switch.
     * 
     * @param resourceEnvironment
     *            the given resource environment
     * @return T
     */
    public T caseResourceEnvironment(ResourceEnvironment resourceEnvironment) {
        return null;
    }

    /**
     * The case for linking resources. You should override it and add your own logic before using
     * the switch.
     * 
     * @param linkingResource
     *            the given linking resource
     * @return T
     */
    public T caseLinkingResource(LinkingResource linkingResource) {
        return null;
    }

    /**
     * The case for external call actions. You should override it and add your own logic before
     * using the switch.
     * 
     * @param externalCallAction
     *            the given external call action
     * @return T
     */
    public T caseExternalCallAction(ExternalCallAction externalCallAction) {
        return null;
    }

    /**
     * The case for entry level system calls. You should override it and add your own logic before
     * using the switch.
     * 
     * @param entryLevelSystemCall
     *            the given entry level system call
     * @return T
     */
    public T caseEntryLevelSystemCall(EntryLevelSystemCall entryLevelSystemCall) {
        return null;
    }

    /**
     * The case for roles. You should override it and add your own logic before using the switch.
     * 
     * @param role
     *            the given role
     * @return T
     */
    public T caseRole(Role role) {
        return null;
    }

    /**
     * The case for operation signatures. You should override it and add your own logic before using
     * the switch.
     * 
     * @param operationSignature
     *            the given operation signature
     * @return T
     */
    public T caseOperationSignature(OperationSignature operationSignature) {
        return null;
    }

    /**
     * The case for passive resources. You should override it and add your own logic before using
     * the switch.
     * 
     * @param passiveResource
     *            the given passive resource
     * @return T
     */
    public T casePassiveResource(PassiveResource passiveResource) {
        return null;
    }

    /**
     * The case for assembly contexts. You should override it and add your own logic before using
     * the switch.
     * 
     * @param assemblyContext
     *            the given assembly context
     * @return T
     */
    public T caseAssemblyContext(AssemblyContext assemblyContext) {
        return null;
    }

    /**
     * The case for processing resource specifications. You should override it and add your own
     * logic before using the switch.
     * 
     * @param processingResourceSpecification
     *            the given processing resource specification
     * @return T
     */
    public T caseProcessingResourceSpecification(ProcessingResourceSpecification processingResourceSpecification) {
        return null;
    }

    /**
     * The case for resource containers. You should override it and add your own logic before using
     * the switch.
     * 
     * @param resourceContainer
     *            the given resource container
     * @return T
     */
    public T caseResourceContainer(ResourceContainer resourceContainer) {
        return null;
    }

    /**
     * logic for the default case within the switch method
     * 
     * @param theEObject
     *            the given EObject
     * @param result
     *            the result which was set in the switch. If it didn't apply for any of the cases,
     *            it is null
     * @return T
     */
    private T useDefaultCase(final EObject theEObject, T result) {
        if (result == null) {
            result = this.defaultCase(theEObject);
        }
        return result;
    }

    /**
     * The default case. You should override it and add your own logic before using the switch.
     * 
     * @param eObject the given EObject
     * @return T
     */
    public T defaultCase(EObject eObject) {
        return null;

    }

}
