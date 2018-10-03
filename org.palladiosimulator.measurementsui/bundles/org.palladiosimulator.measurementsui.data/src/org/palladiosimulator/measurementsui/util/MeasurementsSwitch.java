package org.palladiosimulator.measurementsui.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointPackage;
import org.palladiosimulator.edp2.models.measuringpoint.util.MeasuringpointSwitch;
import org.palladiosimulator.monitorrepository.MonitorRepositoryPackage;
import org.palladiosimulator.pcm.PcmPackage;
import org.palladiosimulator.pcm.allocation.AllocationPackage;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.ImplementationComponentType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.subsystem.SubsystemPackage;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.SystemPackage;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;
import org.palladiosimulator.pcmmeasuringpoint.PcmmeasuringpointPackage;

public class MeasurementsSwitch<T> extends Switch<T> {

	MeasurementsPackage pcmMeasuringPointPackage;


	public MeasurementsSwitch() {

	}

	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == pcmMeasuringPointPackage;

	}

	@Override
	public T doSwitch(final int classifierID, final EObject theEObject) {
		switch (classifierID) {
		case MeasurementsPackage.RESOURCE_CONTAINER: {
			final ResourceContainer resourceContainer = (ResourceContainer) theEObject;
			T result = this.caseResourceContainer(resourceContainer);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.PROCESSING_RESOURCE_SPECIFICATION: {
			final ProcessingResourceSpecification processingResourceSpecification = (ProcessingResourceSpecification) theEObject;
			T result = this.caseProcessingResourceSpecification(processingResourceSpecification);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.ASSEMBLY_CONTEXT: {
			final AssemblyContext assemblyContext = (AssemblyContext) theEObject;
			T result = this.caseAssemblyContext(assemblyContext);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.PASSIVE_RESOURCE: {
			final PassiveResource passiveResource = (PassiveResource) theEObject;
			T result = this.casePassiveResource(passiveResource);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.OPERATION_SIGNATURE: {
			final OperationSignature operationSignature = (OperationSignature) theEObject;
			T result = this.caseOperationSignature(operationSignature);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.ROLE: {
			final Role role = (Role) theEObject;
			T result = this.caseRole(role);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.ENTRY_LEVEL_SYSTEM_CALL: {
			final EntryLevelSystemCall entryLevelSystemCall = (EntryLevelSystemCall) theEObject;
			T result = this.caseEntryLevelSystemCall(entryLevelSystemCall);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.EXTERNAL_CALL_ACTION: {
			final ExternalCallAction externalCallAction = (ExternalCallAction) theEObject;
			T result = this.caseExternalCallAction(externalCallAction);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.LINKING_RESOURCE: {
			final LinkingResource linkingResource = (LinkingResource) theEObject;
			T result = this.caseLinkingResource(linkingResource);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.RESOURCE_ENVIRONMENT: {
			final ResourceEnvironment resourceEnvironment = (ResourceEnvironment) theEObject;
			T result = this.caseResourceEnvironment(resourceEnvironment);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.SUB_SYSTEM: {
			final SubSystem subSystem = (SubSystem) theEObject;
			T result = this.caseSubSystem(subSystem);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.SYSTEM: {
			final System system = (System) theEObject;
			T result = this.caseSystem(system);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.USAGE_SCENARIO: {
			final UsageScenario usageScenario = (UsageScenario) theEObject;
			T result = this.caseUsageScenario(usageScenario);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.BRANCH: {
			final Branch branch = (Branch) theEObject;
			T result = this.caseBranch(branch);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.LOOP: {
			final Loop loop = (Loop) theEObject;
			T result = this.caseLoop(loop);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.BRANCH_TRANSITION: {
			final BranchTransition branchTransition = (BranchTransition) theEObject;
			T result = this.caseBranchTransition(branchTransition);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.REPOSITORY: {
			final Repository repository = (Repository) theEObject;
			T result = this.caseRepository(repository);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.SCENARIO_BEHAVIOUR: {
			final ScenarioBehaviour scenarioBehaviour = (ScenarioBehaviour) theEObject;
			T result = this.caseScenarioBehaviour(scenarioBehaviour);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}
		case MeasurementsPackage.BASIC_COMPONENT: {
			final BasicComponent basicComponent = (BasicComponent) theEObject;
			T result = this.caseBasicComponent(basicComponent);
			if (result == null) {
				result = this.defaultCase(theEObject);
			}
			return result;
		}

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

	public T caseResourceContainer(final ResourceContainer resourceContainer) {
		return null;
	}

}
