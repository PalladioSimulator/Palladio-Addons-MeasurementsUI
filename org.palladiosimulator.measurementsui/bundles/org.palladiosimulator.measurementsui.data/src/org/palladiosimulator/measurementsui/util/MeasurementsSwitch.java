package org.palladiosimulator.measurementsui.util;

import org.eclipse.emf.ecore.EObject;
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
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
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
        }

        return null;

    }

    private T useDefaultCase(final EObject theEObject, T result) {
        if (result == null) {
            result = this.defaultCase(theEObject);
        }
        return result;
    }

    public T caseBasicComponent(BasicComponent basicComponent) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseScenarioBehaviour(ScenarioBehaviour scenarioBehaviour) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseRepository(Repository resourceContainer) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseBranchTransition(BranchTransition branchTransition) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseLoop(Loop loop) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseBranch(Branch branch) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseUsageScenario(UsageScenario usageScenario) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseSystem(System resourceContainer) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseSubSystem(SubSystem subSystem) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseResourceEnvironment(ResourceEnvironment resourceEnvironment) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseLinkingResource(LinkingResource linkingResource) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseExternalCallAction(ExternalCallAction externalCallAction) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseEntryLevelSystemCall(EntryLevelSystemCall entryLevelSystemCall) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseRole(Role role) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseOperationSignature(OperationSignature operationSignature) {
        // TODO Auto-generated method stub
        return null;
    }

    public T casePassiveResource(PassiveResource passiveResource) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseAssemblyContext(AssemblyContext assemblyContext) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseProcessingResourceSpecification(ProcessingResourceSpecification processingResourceSpecification) {
        // TODO Auto-generated method stub
        return null;
    }

    public T caseResourceContainer(ResourceContainer resourceContainer) {
        return null;
    }

    public T defaultCase(EObject eObject) {
        return null;

    }

}
