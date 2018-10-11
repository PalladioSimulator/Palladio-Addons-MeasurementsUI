package org.palladiosimulator.measurementsui.wizard.util;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.util.MeasurementsSwitch;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

public class ChooseMeasuringPointMessageSwitch extends MeasurementsSwitch<String>{
    
    MeasuringPointSelectionWizardModel selectionWizardModel;
    @Override
    public String caseMeasuringPoint(MeasuringPoint measuringpoint) {
        return "This is an existing measuring point.";
    }
    
    @Override
    public String caseResourceEnvironment(ResourceEnvironment resourceEnvironment) {
        return "This will create a resource environment measuring point.";
    }
    
    @Override
    public String caseResourceContainer(ResourceContainer resourceContainer) {
        return "This will create a resource container measuring point.";
    }
    
    @Override
    public String caseProcessingResourceSpecification(ProcessingResourceSpecification processingResourceSpecification) {
        return "This will create an active resource measuring point.";
    }
    
    @Override
    public String caseAssemblyContext(AssemblyContext assemblyContext) {
        return "This will create either an assembly operation or an assembly passive resource measuring point. "
                + "In the next step you will need to specify further components.";
    }
    
    @Override
    public String caseEntryLevelSystemCall(EntryLevelSystemCall entryLevelSystemCall) {
        return "This will create an entry level system call measuring point.";
    }
    @Override
    public String caseExternalCallAction(ExternalCallAction externalCallAction) {
        return "This will create an external call action measuring point.";
    }
    @Override
    public String caseLinkingResource(LinkingResource linkingResource) {
        return "This will create a linking resource measuring point.";
    }
    @Override
    public String caseSubSystem(SubSystem subSystem) {
        return "This will create a subsystem measuring point. In the next step you will "
                + "need to specify further components.";
    }
    @Override
    public String caseSystem(System resourceContainer) {
        return "This will create a system measuring point. In the next step you will need to specify further components.";
    }
    
    @Override
    public String caseUsageScenario(UsageScenario usageScenario) {
        return "This will create a usage scenario measuring point.";
    }
    
    @Override
    public String defaultCase(EObject eObject) {
        return selectionWizardModel.getInfoText();
    }

    public MeasuringPointSelectionWizardModel getSelectionWizardModel() {
        return selectionWizardModel;
    }

    public void setSelectionWizardModel(MeasuringPointSelectionWizardModel selectionWizardModel) {
        this.selectionWizardModel = selectionWizardModel;
    }

}
