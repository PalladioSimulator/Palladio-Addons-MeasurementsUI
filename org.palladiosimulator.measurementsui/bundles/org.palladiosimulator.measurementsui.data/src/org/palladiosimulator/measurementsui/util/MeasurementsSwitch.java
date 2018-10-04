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

public class MeasurementsSwitch<T> {


 


	
	
	public T doSwitch(final EObject theEObject) {

		
		if (theEObject instanceof ResourceContainer){
			final ResourceContainer resourceContainer = (ResourceContainer) theEObject;
			T result = this.caseResourceContainer(resourceContainer);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof ProcessingResourceSpecification){
			final ProcessingResourceSpecification processingResourceSpecification = (ProcessingResourceSpecification) theEObject;
			T result = this.caseProcessingResourceSpecification(processingResourceSpecification);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof AssemblyContext){
			final AssemblyContext assemblyContext = (AssemblyContext) theEObject;
			T result = this.caseAssemblyContext(assemblyContext);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof PassiveResource){
			final PassiveResource passiveResource = (PassiveResource) theEObject;
			T result = this.casePassiveResource(passiveResource);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof OperationSignature) {
			final OperationSignature operationSignature = (OperationSignature) theEObject;
			T result = this.caseOperationSignature(operationSignature);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof Role){
			final Role role = (Role) theEObject;
			T result = this.caseRole(role);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof EntryLevelSystemCall){
			final EntryLevelSystemCall entryLevelSystemCall = (EntryLevelSystemCall) theEObject;
			T result = this.caseEntryLevelSystemCall(entryLevelSystemCall);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof ExternalCallAction) {
			final ExternalCallAction externalCallAction = (ExternalCallAction) theEObject;
			T result = this.caseExternalCallAction(externalCallAction);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof LinkingResource){
			final LinkingResource linkingResource = (LinkingResource) theEObject;
			T result = this.caseLinkingResource(linkingResource);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof ResourceEnvironment){
		    
			final ResourceEnvironment resourceEnvironment = (ResourceEnvironment) theEObject;
			
			T result = this.caseResourceEnvironment(resourceEnvironment);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof SubSystem){
			final SubSystem subSystem = (SubSystem) theEObject;
			T result = this.caseSubSystem(subSystem);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof System){
			final System system = (System) theEObject;
			T result = this.caseSystem(system);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof UsageScenario){
			final UsageScenario usageScenario = (UsageScenario) theEObject;
			T result = this.caseUsageScenario(usageScenario);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof Branch){
			final Branch branch = (Branch) theEObject;
			T result = this.caseBranch(branch);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof Loop){
			final Loop loop = (Loop) theEObject;
			T result = this.caseLoop(loop);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof BranchTransition){
			final BranchTransition branchTransition = (BranchTransition) theEObject;
			T result = this.caseBranchTransition(branchTransition);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof Repository){
			final Repository repository = (Repository) theEObject;
			T result = this.caseRepository(repository);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof ScenarioBehaviour){
			final ScenarioBehaviour scenarioBehaviour = (ScenarioBehaviour) theEObject;
			T result = this.caseScenarioBehaviour(scenarioBehaviour);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}else if(theEObject instanceof BasicComponent){
			final BasicComponent basicComponent = (BasicComponent) theEObject;
			T result = this.caseBasicComponent(basicComponent);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}

		
		return null;

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
