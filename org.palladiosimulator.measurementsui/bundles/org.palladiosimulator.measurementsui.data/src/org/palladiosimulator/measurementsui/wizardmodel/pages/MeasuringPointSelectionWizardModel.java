package org.palladiosimulator.measurementsui.wizardmodel.pages;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.monitorrepository.impl.MonitorRepositoryFactoryImpl;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
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

public class MeasuringPointSelectionWizardModel implements WizardModel {

    private final String createMeasuringPointInfoText = "Select the element of your Models which should be monitored during a simulation run. ";
    private final String editMeasuringPointInfoText = "Select a different measuring Point.";

    private final String createMeasuringPointTitel = "Create Measuring Point";
    private final String editMeasuringPointTitel = "Edit Measuring Point";

    private Monitor monitor;

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    private static MeasuringPointSelectionWizardModel instance;
    MonitorRepositoryFactory mf= new MonitorRepositoryFactoryImpl();
    Object currentSelectionFirstMeasuringModel;
    Object currentSecondStageModel;
    public Object getCurrentSecondStageModel() {
        return currentSecondStageModel;
    }

    public void setCurrentSecondStageModel(Object currentSecondStageModel) {
        this.currentSecondStageModel = currentSecondStageModel;
    }

    public Object getCurrentThirdStageModel() {
        return currentThirdStageModel;
    }

    public void setCurrentThirdStageModel(Object currentThirdStageModel) {
        this.currentThirdStageModel = currentThirdStageModel;
    }

    Object currentThirdStageModel;

    DataApplication da = DataApplication.getInstance();

    public static MeasuringPointSelectionWizardModel getInstance() {
        if (instance == null) {
            instance = new MeasuringPointSelectionWizardModel();
            return instance;
        }
        return instance;
    }

    public MeasuringPointSelectionWizardModel() {
        

    }

    public MeasuringPointSelectionWizardModel(Monitor monitor) {
        this.monitor = monitor;
    }

    public void createMeasuringPoint(Object model) {
        monitor= mf.createMonitor();
        monitor.setEntityName("lol");
        PcmmeasuringpointPackage pcmMeasuringPointPackage = PcmmeasuringpointPackage.eINSTANCE;
        PcmmeasuringpointFactory pcmMeasuringPointFactory = pcmMeasuringPointPackage.getPcmmeasuringpointFactory();

        if (model instanceof ResourceContainer) {
            ResourceContainerMeasuringPoint mp = pcmMeasuringPointFactory.createResourceContainerMeasuringPoint();
            mp.setResourceContainer((ResourceContainer) model);
            monitor.setMeasuringPoint(mp);
        } else if (model instanceof ProcessingResourceSpecification) {

            ActiveResourceMeasuringPoint mp = pcmMeasuringPointFactory.createActiveResourceMeasuringPoint();
            mp.setActiveResource((ProcessingResourceSpecification) model);
            monitor.setMeasuringPoint(mp);

        } else if (model instanceof AssemblyContext && currentSecondStageModel instanceof PassiveResource) {
            AssemblyPassiveResourceMeasuringPoint mp = pcmMeasuringPointFactory
                    .createAssemblyPassiveResourceMeasuringPoint();
            mp.setAssembly((AssemblyContext) model);
            mp.setPassiveResource((PassiveResource) currentSecondStageModel);
            monitor.setMeasuringPoint(mp);

        } else if (model instanceof AssemblyContext) {

            AssemblyOperationMeasuringPoint mp = pcmMeasuringPointFactory.createAssemblyOperationMeasuringPoint();
            mp.setAssembly((AssemblyContext) model);
            mp.setOperationSignature((OperationSignature) currentThirdStageModel);
            mp.setRole((Role) currentSecondStageModel);
            monitor.setMeasuringPoint(mp);

        } else if (model instanceof EntryLevelSystemCall) {

            EntryLevelSystemCallMeasuringPoint mp = pcmMeasuringPointFactory.createEntryLevelSystemCallMeasuringPoint();
            mp.setEntryLevelSystemCall((EntryLevelSystemCall) model);
            monitor.setMeasuringPoint(mp);

        } else if (model instanceof ExternalCallAction) {

            ExternalCallActionMeasuringPoint mp = pcmMeasuringPointFactory.createExternalCallActionMeasuringPoint();
            mp.setExternalCall((ExternalCallAction) model);
            monitor.setMeasuringPoint(mp);
        } else if (model instanceof LinkingResource) {

            LinkingResourceMeasuringPoint mp = pcmMeasuringPointFactory.createLinkingResourceMeasuringPoint();
            mp.setLinkingResource((LinkingResource) model);
            monitor.setMeasuringPoint(mp);
        } else if (model instanceof ResourceEnvironment) {

            ResourceEnvironmentMeasuringPoint mp = pcmMeasuringPointFactory.createResourceEnvironmentMeasuringPoint();
            mp.setResourceEnvironment((ResourceEnvironment) model);
            monitor.setMeasuringPoint(mp);
        } else if (model instanceof SubSystem) {

            SubSystemOperationMeasuringPoint mp = pcmMeasuringPointFactory.createSubSystemOperationMeasuringPoint();
            mp.setSubsystem((SubSystem) model);
            mp.setOperationSignature((OperationSignature) currentThirdStageModel);
            mp.setRole((Role) currentSecondStageModel);
            monitor.setMeasuringPoint(mp);

        } else if (model instanceof System) {

            SystemOperationMeasuringPoint mp = pcmMeasuringPointFactory.createSystemOperationMeasuringPoint();
            mp.setSystem((System) model);
            mp.setOperationSignature((OperationSignature) currentThirdStageModel);
            mp.setRole((Role) currentSecondStageModel);
            monitor.setMeasuringPoint(mp);

        } else if (model instanceof UsageScenario) {

            UsageScenarioMeasuringPoint mp = pcmMeasuringPointFactory.createUsageScenarioMeasuringPoint();
            mp.setUsageScenario((UsageScenario) model);
            monitor.setMeasuringPoint(mp);

        }

    }

    public void addMeasuringPointToMonitor(MeasuringPoint measuringPoint) {
        monitor= mf.createMonitor();
        monitor.setEntityName("lol");
        monitor.setMeasuringPoint(measuringPoint);

    }
    
    public void addMeasuringPointToRepository(MeasuringPoint measuringPoint) {
        MeasuringPointRepository measuringPointRepository = DataApplication.getInstance().getModelAccessor()
                .getMeasuringPointRepository().get(0);
        ResourceEditorImpl.getInstance().addMeasuringPoint(measuringPointRepository, measuringPoint);
    }

    public void setMeasuringPointToMonitor(MeasuringPoint measuringPoint) {
        ResourceEditorImpl.getInstance().setMeasuringPoint(this.monitor, measuringPoint);

    }

    @Override
    public boolean canFinish() {
        if (this.monitor.getMonitorRepository() != null && this.monitor.getMeasuringPoint() != null) {
            return true;
        }
        return false;
    }

    @Override
    public String getInfoText() {
        if (this.monitor.getMeasuringPoint() != null) {
            return editMeasuringPointInfoText;
        }
        return createMeasuringPointInfoText;
    }

    @Override
    public void nextStep() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getTitleText() {
        if (this.monitor.getMeasuringPoint() != null) {
            return createMeasuringPointTitel;
        }
        return editMeasuringPointTitel;

    }

    public List<AssemblyContext> getAssemblyContexts() {
        List<AssemblyContext> assemblyContexts = da.getModelAccessor().getSystem().stream()
                .flatMap(e -> e.getAssemblyContexts__ComposedStructure().stream()).collect(Collectors.toList());

        return assemblyContexts;
    }

    public List<ResourceContainer> getResourceContainer() {
        List<ResourceContainer> resourceContainer = da.getModelAccessor().getResourceEnvironment().stream()
                .flatMap(e -> e.getResourceContainer_ResourceEnvironment().stream()).collect(Collectors.toList());

        return resourceContainer;
    }

    public List<ProcessingResourceSpecification> getActiveResources() {

        List<ProcessingResourceSpecification> activeresources = getResourceContainer().stream()
                .flatMap(e -> e.getActiveResourceSpecifications_ResourceContainer().stream())
                .collect(Collectors.toList());

        return activeresources;
    }

    public List<LinkingResource> getLinkingResources() {

        List<LinkingResource> activeresources = da.getModelAccessor().getResourceEnvironment().stream()
                .flatMap(e -> e.getLinkingResources__ResourceEnvironment().stream()).collect(Collectors.toList());

        return activeresources;
    }

    public List<UsageScenario> getUsageScenarios() {

        List<UsageScenario> activeresources = da.getModelAccessor().getUsageModel().stream()
                .flatMap(e -> e.getUsageScenario_UsageModel().stream()).collect(Collectors.toList());

        return activeresources;
    }

    public List<AbstractUserAction> getEntryLevelSystemCalls() {

        List<AbstractUserAction> activeresources = getUsageScenarios().stream()
                .flatMap(e -> e.getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour().stream())
                .filter(e -> e instanceof EntryLevelSystemCall).collect(Collectors.toList());

        return activeresources;
    }

    public List<EObject> getExternalCallActions() {

        List<EObject> activeresources = getSeffs().stream().flatMap(e -> e.eContents().stream())
                .filter(e -> e instanceof ExternalCallAction).collect(Collectors.toList());

        return activeresources;
    }

    public List<EObject> getComponents() {

        List<EObject> activeresources = da.getModelAccessor().getRepository().stream()
                .flatMap(e -> e.eContents().stream()).filter(e -> e instanceof BasicComponent)
                .collect(Collectors.toList());

        return activeresources;
    }

    public List<EObject> getSeffs() {

        List<EObject> activeresources = getComponents().stream().flatMap(e -> e.eContents().stream())
                .filter(e -> e instanceof ResourceDemandingSEFF).collect(Collectors.toList());

        return activeresources;
    }

    public Object[] getAllSecondPageObjects() {

        List<Object> allmodels = new ArrayList<>();
        allmodels.addAll(da.getModelAccessor().getResourceEnvironment());
        allmodels.addAll(da.getModelAccessor().getSystem());
        allmodels.addAll(da.getModelAccessor().getSubSystem());

        allmodels.addAll(getAssemblyContexts());
        allmodels.addAll(getResourceContainer());
        allmodels.addAll(getActiveResources());
        allmodels.addAll(getLinkingResources());
        allmodels.addAll(getUsageScenarios());
        allmodels.addAll(getEntryLevelSystemCalls());
        allmodels.addAll(getExternalCallActions());

        return allmodels.toArray();
    }

    public Object[] getAllAdditionalModels() {
        List<Object> elements = new LinkedList<>();
        if (currentSelectionFirstMeasuringModel instanceof AssemblyContext) {
            AssemblyContext context = (AssemblyContext) currentSelectionFirstMeasuringModel;
            elements.addAll(
                    context.getEncapsulatedComponent__AssemblyContext().getProvidedRoles_InterfaceProvidingEntity());
            elements.addAll(
                    context.getEncapsulatedComponent__AssemblyContext().getRequiredRoles_InterfaceRequiringEntity());
            for (int i = 0; i < context.getEncapsulatedComponent__AssemblyContext().eContents().size(); i++) {

                if (context.getEncapsulatedComponent__AssemblyContext().eContents().get(i) instanceof PassiveResource) {
                    elements.add(context.getEncapsulatedComponent__AssemblyContext().eContents().get(i));
                }

            }

            return elements.toArray();
        } else if (currentSelectionFirstMeasuringModel instanceof org.palladiosimulator.pcm.system.System) {
            org.palladiosimulator.pcm.system.System context = (org.palladiosimulator.pcm.system.System) currentSelectionFirstMeasuringModel;
            elements.addAll(context.getProvidedRoles_InterfaceProvidingEntity());
            elements.addAll(context.getRequiredRoles_InterfaceRequiringEntity());

            return elements.toArray();
        } else if (currentSelectionFirstMeasuringModel instanceof SubSystem) {
            SubSystem context = (SubSystem) currentSelectionFirstMeasuringModel;
            elements.addAll(context.getProvidedRoles_InterfaceProvidingEntity());
            elements.addAll(context.getRequiredRoles_InterfaceRequiringEntity());
            return elements.toArray();
        }
        return null;

    }

    public Object getCurrentSelection() {
        return this.currentSelectionFirstMeasuringModel;
    }

    public void setCurrentSelection(Object current) {
        this.currentSelectionFirstMeasuringModel = current;
    }

    public List<EObject> getSignatures() {

        List<EObject> activeresources = da.getModelAccessor().getRepository().stream()
                .flatMap(e -> e.eContents().stream()).filter(e -> e instanceof OperationInterface)
                .collect(Collectors.toList());

        List<EObject> activeresources2 = activeresources.stream().flatMap(e -> e.eContents().stream())
                .filter(e -> e instanceof OperationSignature).collect(Collectors.toList());

        return activeresources2;

    }

}
