package org.palladiosimulator.measurementsui.util;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcmmeasuringpoint.ActiveResourceMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.AssemblyOperationMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.AssemblyPassiveResourceMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.EntryLevelSystemCallMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.ExternalCallActionMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.LinkingResourceMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.PcmmeasuringpointFactory;
import org.palladiosimulator.pcmmeasuringpoint.PcmmeasuringpointPackage;
import org.palladiosimulator.pcmmeasuringpoint.ResourceContainerMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.ResourceEnvironmentMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.SubSystemOperationMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.SystemOperationMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.UsageScenarioMeasuringPoint;

public class CreateMeasuringPointSwitch extends MeasurementsSwitch<MeasuringPoint>{
    PcmmeasuringpointPackage pcmMeasuringPointPackage = PcmmeasuringpointPackage.eINSTANCE;
    PcmmeasuringpointFactory pcmMeasuringPointFactory = pcmMeasuringPointPackage.getPcmmeasuringpointFactory();
    
    private Object secondStageModel;
    private Object thirdStageModel;

    public Object getSecondStageModel() {
        return secondStageModel;
    }

    public void setSecondStageModel(Object secondStageModel) {
        this.secondStageModel = secondStageModel;
    }

    public Object getThirdStageModel() {
        return thirdStageModel;
    }

    public void setThirdStageModel(Object thirdStageModel) {
        this.thirdStageModel = thirdStageModel;
    }


    @Override
    public MeasuringPoint caseAssemblyContext(AssemblyContext assemblyContext) {
       
        if(secondStageModel instanceof PassiveResource) {
            PassiveResource passiveResource = (PassiveResource) secondStageModel;
            AssemblyPassiveResourceMeasuringPoint mp = (AssemblyPassiveResourceMeasuringPoint) pcmMeasuringPointFactory
                    .create(PcmmeasuringpointPackage.eINSTANCE.getAssemblyPassiveResourceMeasuringPoint());
            mp.setAssembly(assemblyContext);
            mp.setPassiveResource(passiveResource);
            mp.setStringRepresentation(assemblyContext.getEntityName() + "_" + passiveResource.getEntityName());
            mp.setResourceURIRepresentation(
                    assemblyContext.eResource().getURI().toString() + "#" + assemblyContext.getId());
            return mp;
        }else {
            OperationSignature operationSignature = (OperationSignature) thirdStageModel;
            Role role = (Role) secondStageModel;
            AssemblyOperationMeasuringPoint mp = (AssemblyOperationMeasuringPoint) pcmMeasuringPointFactory
                    .create(PcmmeasuringpointPackage.eINSTANCE.getAssemblyOperationMeasuringPoint());

            mp.setAssembly(assemblyContext);
            mp.setOperationSignature(operationSignature);
            mp.setRole(role);
            mp.setStringRepresentation(assemblyContext.getEntityName());
            mp.setResourceURIRepresentation(
                    assemblyContext.eResource().getURI().toString() + "#" + (assemblyContext).getId());
            return mp;
        }
    }
    
    @Override
    public MeasuringPoint caseEntryLevelSystemCall(EntryLevelSystemCall entryLevelSystemCall) {
        EntryLevelSystemCallMeasuringPoint mp = (EntryLevelSystemCallMeasuringPoint) pcmMeasuringPointFactory
                .create(PcmmeasuringpointPackage.eINSTANCE.getEntryLevelSystemCallMeasuringPoint());

        mp.setEntryLevelSystemCall(entryLevelSystemCall);
        mp.setStringRepresentation(entryLevelSystemCall.getEntityName());
        mp.setResourceURIRepresentation(
                entryLevelSystemCall.eResource().getURI().toString() + "#" + entryLevelSystemCall.getId());
        return mp;
    }
    
    @Override
    public MeasuringPoint caseExternalCallAction(ExternalCallAction externalCallAction) {
        ExternalCallActionMeasuringPoint mp = (ExternalCallActionMeasuringPoint) pcmMeasuringPointFactory
                .create(PcmmeasuringpointPackage.eINSTANCE.getExternalCallActionMeasuringPoint());

        mp.setExternalCall(externalCallAction);
        mp.setStringRepresentation(externalCallAction.getEntityName());
        mp.setResourceURIRepresentation(
                externalCallAction.eResource().getURI().toString() + "#" + externalCallAction.getId());
        return mp;
    }
    
    
    @Override
    public MeasuringPoint caseLinkingResource(LinkingResource linkingResource) {
        LinkingResourceMeasuringPoint mp = (LinkingResourceMeasuringPoint) pcmMeasuringPointFactory
                .create(PcmmeasuringpointPackage.eINSTANCE.getLinkingResourceMeasuringPoint());

        mp.setLinkingResource(linkingResource);
        mp.setStringRepresentation(linkingResource.getEntityName());
        mp.setResourceURIRepresentation(
                linkingResource.eResource().getURI().toString() + "#" + linkingResource.getId());
        return mp;
    }
    
    @Override
    public MeasuringPoint caseResourceEnvironment(ResourceEnvironment resourceEnvironment) {
        ResourceEnvironmentMeasuringPoint mp = (ResourceEnvironmentMeasuringPoint) pcmMeasuringPointFactory
                .create(PcmmeasuringpointPackage.eINSTANCE.getResourceEnvironmentMeasuringPoint());

        mp.setResourceEnvironment(resourceEnvironment);
        mp.setStringRepresentation(resourceEnvironment.getEntityName());
        mp.setResourceURIRepresentation(resourceEnvironment.eResource().getURI().toString() + "#/0");
        return mp;
    }
    
    @Override
    public MeasuringPoint caseSubSystem(SubSystem subSystem) {
        OperationSignature operationSignature = (OperationSignature) thirdStageModel;
        Role role = (Role) secondStageModel;
        SubSystemOperationMeasuringPoint mp = pcmMeasuringPointFactory.createSubSystemOperationMeasuringPoint();

        mp.setSubsystem(subSystem);
        mp.setOperationSignature(operationSignature);
        mp.setRole(role);
        mp.setStringRepresentation(subSystem.getEntityName());
        mp.setResourceURIRepresentation((subSystem.eResource().getURI().toString() + "#" + subSystem.getId()));
        return mp;
    }
    
    @Override
    public MeasuringPoint caseSystem(System system) {
        OperationSignature operationSignature = (OperationSignature) thirdStageModel;
        Role role = (Role) secondStageModel;
        SystemOperationMeasuringPoint mp =  pcmMeasuringPointFactory.createSystemOperationMeasuringPoint();

        mp.setSystem(system);
        mp.setOperationSignature(operationSignature);
        mp.setRole(role);
        mp.setStringRepresentation(system.getEntityName());
        mp.setResourceURIRepresentation(system.eResource().getURI().toString() + "#" + system.getId());
        return mp;
    }
    
    @Override
    public MeasuringPoint caseUsageScenario(UsageScenario usageScenario) {
        UsageScenarioMeasuringPoint mp = pcmMeasuringPointFactory.createUsageScenarioMeasuringPoint();
        
        mp.setUsageScenario(usageScenario);
        mp.setStringRepresentation(usageScenario.getEntityName());
        mp.setResourceURIRepresentation(
                usageScenario.eResource().getURI().toString() + "#" + usageScenario.getId());
        return super.caseUsageScenario(usageScenario);
    }
    
    @Override
    public MeasuringPoint caseResourceContainer(ResourceContainer resourceContainer) {
        ResourceContainerMeasuringPoint mp = 
                (ResourceContainerMeasuringPoint) pcmMeasuringPointFactory
                .create(PcmmeasuringpointPackage.eINSTANCE.getResourceContainerMeasuringPoint());
        mp.setResourceContainer(resourceContainer);
        mp.setStringRepresentation(resourceContainer.getEntityName());
        mp.setResourceURIRepresentation(resourceContainer.eResource().getURI().toString() + "#" + resourceContainer.getId());
        return mp;
    }
    
    @Override
    public MeasuringPoint caseProcessingResourceSpecification(ProcessingResourceSpecification processingResourceSpecification) {
        ActiveResourceMeasuringPoint mp = (ActiveResourceMeasuringPoint) pcmMeasuringPointFactory
                .create(PcmmeasuringpointPackage.eINSTANCE.getActiveResourceMeasuringPoint());

        mp.setActiveResource(processingResourceSpecification);
        mp.setStringRepresentation(processingResourceSpecification
                .getActiveResourceType_ActiveResourceSpecification().getEntityName());
        mp.setResourceURIRepresentation(processingResourceSpecification.eResource().getURI().toString() + "#"
                + processingResourceSpecification.getId());
        return mp;
        }
}


