package org.palladiosimulator.measurementsui.util;

import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
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

/**
 * The switch for the MeasuringPointSelectionWizardModel, which takes care of the creation of the
 * correct measuringpoint
 * 
 * @author Domas Mikalkinas
 *
 */
public class CreateMeasuringPointSwitch extends MeasurementsSwitch<MeasuringPoint> {
    private PcmmeasuringpointPackage pcmMeasuringPointPackage = PcmmeasuringpointPackage.eINSTANCE;
    private PcmmeasuringpointFactory pcmMeasuringPointFactory = pcmMeasuringPointPackage.getPcmmeasuringpointFactory();

    private Object secondStageModel;
    private Object thirdStageModel;

    /**
     * getter for the second stage model
     * 
     * @return Object
     */
    public Object getSecondStageModel() {
        return secondStageModel;
    }

    /**
     * setter for the second stage model
     * 
     * @param secondStageModel
     *            the model which was set in the second stage of the measuring point wizard pages
     */
    public void setSecondStageModel(Object secondStageModel) {
        this.secondStageModel = secondStageModel;
    }

    /**
     * getter for the third stage model
     * 
     * @return Object
     */
    public Object getThirdStageModel() {
        return thirdStageModel;
    }

    /**
     * setter for the third stage model
     * 
     * @param thirdStageModel
     *            the model which was set in the third stage of the measuring point wizard pages
     */
    public void setThirdStageModel(Object thirdStageModel) {
        this.thirdStageModel = thirdStageModel;
    }

    @Override
    public MeasuringPoint caseAssemblyContext(AssemblyContext assemblyContext) {

        if (secondStageModel instanceof PassiveResource) {
            PassiveResource passiveResource = (PassiveResource) secondStageModel;
            AssemblyPassiveResourceMeasuringPoint assemblyPassiveResourceMeasuringPoint = (AssemblyPassiveResourceMeasuringPoint) pcmMeasuringPointFactory
                    .create(PcmmeasuringpointPackage.eINSTANCE.getAssemblyPassiveResourceMeasuringPoint());
            assemblyPassiveResourceMeasuringPoint.setAssembly(assemblyContext);
            assemblyPassiveResourceMeasuringPoint.setPassiveResource(passiveResource);
            assemblyPassiveResourceMeasuringPoint
                    .setStringRepresentation(assemblyContext.getEntityName() + "_" + passiveResource.getEntityName());
            assemblyPassiveResourceMeasuringPoint.setResourceURIRepresentation(
                    assemblyContext.eResource().getURI().toString() + "#" + assemblyContext.getId());
            return assemblyPassiveResourceMeasuringPoint;
        } else {
            OperationSignature operationSignature = (OperationSignature) thirdStageModel;
            Role role = (Role) secondStageModel;
            AssemblyOperationMeasuringPoint assemblyOperationMeasuringPoint = pcmMeasuringPointFactory
                    .createAssemblyOperationMeasuringPoint();

            assemblyOperationMeasuringPoint.setAssembly(assemblyContext);
            assemblyOperationMeasuringPoint.setOperationSignature(operationSignature);
            assemblyOperationMeasuringPoint.setRole(role);
            assemblyOperationMeasuringPoint.setStringRepresentation(assemblyContext.getEntityName());
            assemblyOperationMeasuringPoint.setResourceURIRepresentation(
                    assemblyContext.eResource().getURI().toString() + "#" + (assemblyContext).getId());
            return assemblyOperationMeasuringPoint;
        }
    }

    @Override
    public MeasuringPoint caseEntryLevelSystemCall(EntryLevelSystemCall entryLevelSystemCall) {
        EntryLevelSystemCallMeasuringPoint entryLevelSystemCallMeasuringPoint = (EntryLevelSystemCallMeasuringPoint) pcmMeasuringPointFactory
                .create(PcmmeasuringpointPackage.eINSTANCE.getEntryLevelSystemCallMeasuringPoint());

        entryLevelSystemCallMeasuringPoint.setEntryLevelSystemCall(entryLevelSystemCall);
        entryLevelSystemCallMeasuringPoint.setStringRepresentation(entryLevelSystemCall.getEntityName());
        entryLevelSystemCallMeasuringPoint.setResourceURIRepresentation(
                entryLevelSystemCall.eResource().getURI().toString() + "#" + entryLevelSystemCall.getId());
        return entryLevelSystemCallMeasuringPoint;
    }

    @Override
    public MeasuringPoint caseExternalCallAction(ExternalCallAction externalCallAction) {
        ExternalCallActionMeasuringPoint externalCallActionMeasuringPoint = (ExternalCallActionMeasuringPoint) pcmMeasuringPointFactory
                .create(PcmmeasuringpointPackage.eINSTANCE.getExternalCallActionMeasuringPoint());

        externalCallActionMeasuringPoint.setExternalCall(externalCallAction);
        externalCallActionMeasuringPoint.setStringRepresentation(externalCallAction.getEntityName());
        externalCallActionMeasuringPoint.setResourceURIRepresentation(
                externalCallAction.eResource().getURI().toString() + "#" + externalCallAction.getId());
        return externalCallActionMeasuringPoint;
    }

    @Override
    public MeasuringPoint caseLinkingResource(LinkingResource linkingResource) {
        LinkingResourceMeasuringPoint linkingResourceMeasuringPoint = (LinkingResourceMeasuringPoint) pcmMeasuringPointFactory
                .create(PcmmeasuringpointPackage.eINSTANCE.getLinkingResourceMeasuringPoint());

        linkingResourceMeasuringPoint.setLinkingResource(linkingResource);
        linkingResourceMeasuringPoint.setStringRepresentation(linkingResource.getEntityName());
        linkingResourceMeasuringPoint.setResourceURIRepresentation(
                linkingResource.eResource().getURI().toString() + "#" + linkingResource.getId());
        return linkingResourceMeasuringPoint;
    }

    @Override
    public MeasuringPoint caseResourceEnvironment(ResourceEnvironment resourceEnvironment) {
        ResourceEnvironmentMeasuringPoint resourceEnvironmentMeasuringPoint = (ResourceEnvironmentMeasuringPoint) pcmMeasuringPointFactory
                .create(PcmmeasuringpointPackage.eINSTANCE.getResourceEnvironmentMeasuringPoint());

        resourceEnvironmentMeasuringPoint.setResourceEnvironment(resourceEnvironment);
        resourceEnvironmentMeasuringPoint.setStringRepresentation(resourceEnvironment.getEntityName());
        resourceEnvironmentMeasuringPoint
                .setResourceURIRepresentation(resourceEnvironment.eResource().getURI().toString() + "#/0");
        return resourceEnvironmentMeasuringPoint;
    }

    @Override
    public MeasuringPoint caseSubSystem(SubSystem subSystem) {
        OperationSignature operationSignature = (OperationSignature) thirdStageModel;
        Role role = (Role) secondStageModel;
        SubSystemOperationMeasuringPoint subSystemOperationMeasuringPoint = pcmMeasuringPointFactory
                .createSubSystemOperationMeasuringPoint();

        subSystemOperationMeasuringPoint.setSubsystem(subSystem);
        subSystemOperationMeasuringPoint.setOperationSignature(operationSignature);
        subSystemOperationMeasuringPoint.setRole(role);
        subSystemOperationMeasuringPoint.setStringRepresentation(subSystem.getEntityName());
        subSystemOperationMeasuringPoint
                .setResourceURIRepresentation((subSystem.eResource().getURI().toString() + "#" + subSystem.getId()));
        return subSystemOperationMeasuringPoint;
    }

    @Override
    public MeasuringPoint caseSystem(System system) {
        OperationSignature operationSignature = (OperationSignature) thirdStageModel;
        Role role = (Role) secondStageModel;
        SystemOperationMeasuringPoint systemOperationMeasuringPoint = pcmMeasuringPointFactory
                .createSystemOperationMeasuringPoint();

        systemOperationMeasuringPoint.setSystem(system);
        systemOperationMeasuringPoint.setOperationSignature(operationSignature);
        systemOperationMeasuringPoint.setRole(role);
        systemOperationMeasuringPoint.setStringRepresentation(system.getEntityName());
        systemOperationMeasuringPoint
                .setResourceURIRepresentation(system.eResource().getURI().toString() + "#" + system.getId());
        return systemOperationMeasuringPoint;
    }

    @Override
    public MeasuringPoint caseUsageScenario(UsageScenario usageScenario) {
        UsageScenarioMeasuringPoint usageScenarioMeasuringPoint = (UsageScenarioMeasuringPoint) pcmMeasuringPointFactory
                .create(PcmmeasuringpointPackage.eINSTANCE.getUsageScenarioMeasuringPoint());

        usageScenarioMeasuringPoint.setUsageScenario(usageScenario);
        usageScenarioMeasuringPoint.setStringRepresentation(usageScenario.getEntityName());
        usageScenarioMeasuringPoint.setResourceURIRepresentation(
                usageScenario.eResource().getURI().toString() + "#" + usageScenario.getId());
        return usageScenarioMeasuringPoint;
    }

    @Override
    public MeasuringPoint caseResourceContainer(ResourceContainer resourceContainer) {
        ResourceContainerMeasuringPoint resourceContainerMeasuringPoint = (ResourceContainerMeasuringPoint) pcmMeasuringPointFactory
                .create(PcmmeasuringpointPackage.eINSTANCE.getResourceContainerMeasuringPoint());
        resourceContainerMeasuringPoint.setResourceContainer(resourceContainer);
        resourceContainerMeasuringPoint.setStringRepresentation(resourceContainer.getEntityName());
        resourceContainerMeasuringPoint.setResourceURIRepresentation(
                resourceContainer.eResource().getURI().toString() + "#" + resourceContainer.getId());
        return resourceContainerMeasuringPoint;
    }

    @Override
    public MeasuringPoint caseProcessingResourceSpecification(
            ProcessingResourceSpecification processingResourceSpecification) {
        ActiveResourceMeasuringPoint activeResourceMeasuringPoint = (ActiveResourceMeasuringPoint) pcmMeasuringPointFactory
                .create(PcmmeasuringpointPackage.eINSTANCE.getActiveResourceMeasuringPoint());

        activeResourceMeasuringPoint.setActiveResource(processingResourceSpecification);
        activeResourceMeasuringPoint.setStringRepresentation(
                processingResourceSpecification.getActiveResourceType_ActiveResourceSpecification().getEntityName());
        activeResourceMeasuringPoint
                .setResourceURIRepresentation(processingResourceSpecification.eResource().getURI().toString() + "#"
                        + processingResourceSpecification.getId());
        return activeResourceMeasuringPoint;
    }
}
