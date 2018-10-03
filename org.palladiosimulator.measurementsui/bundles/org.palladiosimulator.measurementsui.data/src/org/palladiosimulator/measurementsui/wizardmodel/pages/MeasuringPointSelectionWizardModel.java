package org.palladiosimulator.measurementsui.wizardmodel.pages;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.util.MeasurementsPackage;
import org.palladiosimulator.measurementsui.util.MeasurementsSwitch;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
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
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.Loop;
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
 * 
 * @author Domas Mikalkinas
 *
 */
public class MeasuringPointSelectionWizardModel implements WizardModel {

    private static final String CREATE_MEASURINGPOINT_INFO_TEXT = "Select the element of your Models which should be "
            + "monitored during a simulation run. Models for which a measuring point "
            + "can be created are highlighted with a bold font.";
    private static final String EDIT_MEASURINGPOINT_INFO_TEXT = "Select a different measuring Point.";

    private static final String CREATE_MEASURINGPOINT_TITLE = "Create Measuring Point";
    private static final String EDIT_MEASURINGPOINT_TITLE = "Edit Measuring Point";

    private Monitor monitor;
    private boolean isEditing;
    private boolean finishable = true;

    private ResourceEditorImpl editor = ResourceEditorImpl.getInstance();
    private Object currentSelectionFirstMeasuringModel;
    private Object currentSecondStageModel;
    private Object currentThirdStageModel;

    private DataApplication dataApplication = DataApplication.getInstance();

    /**
     * 
     * @param monitor
     *            the monitor with which the model should be initialized
     * @param isEditing
     *            indicates, whether it is in edit mode or not
     */
    public MeasuringPointSelectionWizardModel(Monitor monitor, boolean isEditing) {
        this.monitor = monitor;
        this.isEditing = isEditing;

    }

    /**
     * helper method to add the measuringpoint to the monitor
     * 
     * @param measuringPoint
     *            the measuringpoint to be added to the monitor
     */
    public void addMeasuringPointToMonitor(MeasuringPoint measuringPoint) {
        monitor.setMeasuringPoint(measuringPoint);

    }

    /**
     * adds a measuringpoint to the given repository
     * 
     * @param measuringPoint
     *            the measuringpoint to be added to the measuringpoint repository
     */
    public void addMeasuringPointToRepository(MeasuringPoint measuringPoint) {
        MeasuringPointRepository measuringPointRepository = DataApplication.getInstance().getModelAccessor()
                .getMeasuringPointRepository().get(0);
        ResourceEditorImpl.getInstance().addMeasuringPointToRepository(measuringPointRepository, measuringPoint);
    }

    /**
     * checks whether the monitor repository and measuringpoint of a monitor is set. If this is the
     * case the wizard can be finished
     */
    @Override
    public boolean canFinish() {
        return (this.monitor.getMeasuringPoint() != null) && finishable;
    }

    /**
     * returns the info text of the wizard page
     */
    @Override
    public String getInfoText() {
        if (this.monitor.getMeasuringPoint() != null) {
            return EDIT_MEASURINGPOINT_INFO_TEXT;
        }
        return CREATE_MEASURINGPOINT_INFO_TEXT;
    }

    @Override
    public boolean nextStep() {
        return false;
    }

    /**
     * returns the title text of the wizard page
     */
    @Override
    public String getTitleText() {
        if (this.monitor.getMeasuringPoint() != null) {
            return CREATE_MEASURINGPOINT_TITLE;
        }
        return EDIT_MEASURINGPOINT_TITLE;

    }

    /**
     * 
     * @param measuringPoint
     *            the measuringpoint which needs to be added to the monitor
     */
    public void setMeasuringPointDependingOnEditMode(MeasuringPoint measuringPoint) {
        if (isEditing) {
            editor.setMeasuringPointToMonitor(monitor, measuringPoint);
        } else {
            monitor.setMeasuringPoint(measuringPoint);
        }
    }
    MeasurementsSwitch<MeasuringPoint> measuringSwitch = new MeasurementsSwitch<MeasuringPoint>() {
    	PcmmeasuringpointPackage pcmMeasuringPointPackage = PcmmeasuringpointPackage.eINSTANCE;
        PcmmeasuringpointFactory pcmMeasuringPointFactory = pcmMeasuringPointPackage.getPcmmeasuringpointFactory();
        
    
        @Override
        public MeasuringPoint caseProcessingResourceSpecification(ProcessingResourceSpecification processingResourceSpecification) {
			return null;
			}
    
        @Override
        public MeasuringPoint caseUsageScenario(UsageScenario usageScenario) {
        	java.lang.System.out.println("Test"+ usageScenario.getEntityName());
        	return (UsageScenarioMeasuringPoint) pcmMeasuringPointFactory
            .create(PcmmeasuringpointPackage.eINSTANCE.getUsageScenarioMeasuringPoint());
        }
        
    	@Override
    	public MeasuringPoint caseResourceContainer(ResourceContainer resourceContainer) {
    		// TODO Auto-generated method stub
    		return (ResourceContainerMeasuringPoint) pcmMeasuringPointFactory
                    .create(PcmmeasuringpointPackage.eINSTANCE.getResourceContainerMeasuringPoint());
    	}
    	
    	
    };
    /**
     * checks which element from the measuringpoint wizard is selected, creates the corresponding
     * measuring point and adds it to the monitor
     * 
     * @param model
     *            the model, which indicates which measuringpoint needs to be created
     */
    public void createMeasuringPoint(Object model) {

        PcmmeasuringpointPackage pcmMeasuringPointPackage = PcmmeasuringpointPackage.eINSTANCE;
        PcmmeasuringpointFactory pcmMeasuringPointFactory = pcmMeasuringPointPackage.getPcmmeasuringpointFactory();

   
        if (model instanceof ResourceContainer) {
        
            ResourceContainer container = (ResourceContainer) model;
            
            ResourceContainerMeasuringPoint mp = (ResourceContainerMeasuringPoint) measuringSwitch.doSwitch((EObject) model);
//            		(ResourceContainerMeasuringPoint) pcmMeasuringPointFactory
//                    .create(PcmmeasuringpointPackage.eINSTANCE.getResourceContainerMeasuringPoint());

            mp.setResourceContainer(container);
            mp.setStringRepresentation(container.getEntityName());
            mp.setResourceURIRepresentation(container.eResource().getURI().toString() + "#" + container.getId());
            setMeasuringPointDependingOnEditMode(mp);
        } 
        else if (model instanceof ProcessingResourceSpecification) {
            ProcessingResourceSpecification processingResourceSpecification = (ProcessingResourceSpecification) model;
            ActiveResourceMeasuringPoint mp = (ActiveResourceMeasuringPoint) pcmMeasuringPointFactory
                    .create(PcmmeasuringpointPackage.eINSTANCE.getActiveResourceMeasuringPoint());

            mp.setActiveResource(processingResourceSpecification);
            mp.setStringRepresentation(processingResourceSpecification
                    .getActiveResourceType_ActiveResourceSpecification().getEntityName());
            mp.setResourceURIRepresentation(processingResourceSpecification.eResource().getURI().toString() + "#"
                    + processingResourceSpecification.getId());
            setMeasuringPointDependingOnEditMode(mp);

        } 
        else if (model instanceof AssemblyContext && currentSecondStageModel instanceof PassiveResource) {
            AssemblyContext assemblyContext = (AssemblyContext) model;
            PassiveResource passiveResource = (PassiveResource) currentSecondStageModel;

            AssemblyPassiveResourceMeasuringPoint mp = (AssemblyPassiveResourceMeasuringPoint) pcmMeasuringPointFactory
                    .create(PcmmeasuringpointPackage.eINSTANCE.getAssemblyPassiveResourceMeasuringPoint());

            mp.setAssembly(assemblyContext);
            mp.setPassiveResource(passiveResource);
            mp.setStringRepresentation(assemblyContext.getEntityName() + "_" + passiveResource.getEntityName());
            mp.setResourceURIRepresentation(
                    assemblyContext.eResource().getURI().toString() + "#" + assemblyContext.getId());
            setMeasuringPointDependingOnEditMode(mp);

        } else if (model instanceof AssemblyContext) {
            AssemblyContext assemblyContext = (AssemblyContext) model;
            OperationSignature operationSignature = (OperationSignature) currentThirdStageModel;
            Role role = (Role) currentSecondStageModel;
            AssemblyOperationMeasuringPoint mp = (AssemblyOperationMeasuringPoint) pcmMeasuringPointFactory
                    .create(PcmmeasuringpointPackage.eINSTANCE.getAssemblyOperationMeasuringPoint());

            mp.setAssembly(assemblyContext);
            mp.setOperationSignature(operationSignature);
            mp.setRole(role);
            mp.setStringRepresentation(assemblyContext.getEntityName());
            mp.setResourceURIRepresentation(
                    assemblyContext.eResource().getURI().toString() + "#" + (assemblyContext).getId());
            setMeasuringPointDependingOnEditMode(mp);

        } else if (model instanceof EntryLevelSystemCall) {
            EntryLevelSystemCall entryLevelSystemCall = (EntryLevelSystemCall) model;
            EntryLevelSystemCallMeasuringPoint mp = (EntryLevelSystemCallMeasuringPoint) pcmMeasuringPointFactory
                    .create(PcmmeasuringpointPackage.eINSTANCE.getEntryLevelSystemCallMeasuringPoint());

            mp.setEntryLevelSystemCall(entryLevelSystemCall);
            mp.setStringRepresentation(entryLevelSystemCall.getEntityName());
            mp.setResourceURIRepresentation(
                    entryLevelSystemCall.eResource().getURI().toString() + "#" + entryLevelSystemCall.getId());
            setMeasuringPointDependingOnEditMode(mp);

        } else if (model instanceof ExternalCallAction) {
            ExternalCallAction externalCallAction = (ExternalCallAction) model;
            ExternalCallActionMeasuringPoint mp = (ExternalCallActionMeasuringPoint) pcmMeasuringPointFactory
                    .create(PcmmeasuringpointPackage.eINSTANCE.getExternalCallActionMeasuringPoint());

            mp.setExternalCall(externalCallAction);
            mp.setStringRepresentation(externalCallAction.getEntityName());
            mp.setResourceURIRepresentation(
                    externalCallAction.eResource().getURI().toString() + "#" + externalCallAction.getId());
            setMeasuringPointDependingOnEditMode(mp);
        } else if (model instanceof LinkingResource) {
            LinkingResource linkingResource = (LinkingResource) model;
            LinkingResourceMeasuringPoint mp = (LinkingResourceMeasuringPoint) pcmMeasuringPointFactory
                    .create(PcmmeasuringpointPackage.eINSTANCE.getLinkingResourceMeasuringPoint());

            mp.setLinkingResource(linkingResource);
            mp.setStringRepresentation(linkingResource.getEntityName());
            mp.setResourceURIRepresentation(
                    linkingResource.eResource().getURI().toString() + "#" + linkingResource.getId());
            setMeasuringPointDependingOnEditMode(mp);
        } else if (model instanceof ResourceEnvironment) {
            ResourceEnvironment resourceEnvironment = (ResourceEnvironment) model;
            ResourceEnvironmentMeasuringPoint mp = (ResourceEnvironmentMeasuringPoint) pcmMeasuringPointFactory
                    .create(PcmmeasuringpointPackage.eINSTANCE.getResourceEnvironmentMeasuringPoint());

            mp.setResourceEnvironment(resourceEnvironment);
            mp.setStringRepresentation(resourceEnvironment.getEntityName());
            mp.setResourceURIRepresentation(resourceEnvironment.eResource().getURI().toString() + "#/0");
            setMeasuringPointDependingOnEditMode(mp);
        } else if (model instanceof SubSystem) {
            SubSystem subSystem = (SubSystem) model;
            OperationSignature operationSignature = (OperationSignature) currentThirdStageModel;
            Role role = (Role) currentSecondStageModel;
            SubSystemOperationMeasuringPoint mp = (SubSystemOperationMeasuringPoint) pcmMeasuringPointFactory
                    .create(PcmmeasuringpointPackage.eINSTANCE.getSubSystemOperationMeasuringPoint());

            mp.setSubsystem(subSystem);
            mp.setOperationSignature(operationSignature);
            mp.setRole(role);
            mp.setStringRepresentation(subSystem.getEntityName());
            mp.setResourceURIRepresentation((subSystem.eResource().getURI().toString() + "#" + subSystem.getId()));
            setMeasuringPointDependingOnEditMode(mp);

        } else if (model instanceof System) {
            System system = (System) model;
            OperationSignature operationSignature = (OperationSignature) currentThirdStageModel;
            Role role = (Role) currentSecondStageModel;
            SystemOperationMeasuringPoint mp = (SystemOperationMeasuringPoint) pcmMeasuringPointFactory
                    .create(PcmmeasuringpointPackage.eINSTANCE.getSystemOperationMeasuringPoint());

            mp.setSystem(system);
            mp.setOperationSignature(operationSignature);
            mp.setRole(role);
            mp.setStringRepresentation(system.getEntityName());
            mp.setResourceURIRepresentation(system.eResource().getURI().toString() + "#" + system.getId());
            setMeasuringPointDependingOnEditMode(mp);

        } else if (model instanceof UsageScenario) {
            UsageScenario usageScenario = (UsageScenario) model;
            UsageScenarioMeasuringPoint mp = (UsageScenarioMeasuringPoint) measuringSwitch.doSwitch(MeasurementsPackage.USAGE_SCENARIO,(EObject) model);
            		
            mp.setUsageScenario(usageScenario);
            mp.setStringRepresentation(usageScenario.getEntityName());
            mp.setResourceURIRepresentation(
                    usageScenario.eResource().getURI().toString() + "#" + usageScenario.getId());
            setMeasuringPointDependingOnEditMode(mp);

        }

    }

    /**
     * iterates over all system elements and returns all assembly contexts
     * 
     * @return List<AssemblyContext>
     */
    public List<AssemblyContext> getAssemblyContexts() {

        return dataApplication.getModelAccessor().getSystem().stream()
                .flatMap(e -> e.getAssemblyContexts__ComposedStructure().stream())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all resource environments and returns all resource containers
     * 
     * @return List<ResourceContainer>
     */
    public List<ResourceContainer> getResourceContainer() {

        return dataApplication.getModelAccessor().getResourceEnvironment().stream()
                .flatMap(e -> e.getResourceContainer_ResourceEnvironment().stream())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all resource containers and returns all active resource specifications
     * 
     * @return List<ProcessingResourceSpecification>
     */
    public List<ProcessingResourceSpecification> getActiveResources() {

        return getResourceContainer().stream()
                .flatMap(e -> e.getActiveResourceSpecifications_ResourceContainer().stream())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all resource environments and returns all linking resources
     * 
     * @return List<LinkingResource>
     */
    public List<LinkingResource> getLinkingResources() {

        return dataApplication.getModelAccessor().getResourceEnvironment().stream()
                .flatMap(e -> e.getLinkingResources__ResourceEnvironment().stream())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all usage models and returns all usage scenarios
     * 
     * @return List<UsageScenario>
     */
    public List<UsageScenario> getUsageScenarios() {

        return dataApplication.getModelAccessor().getUsageModel().stream().flatMap(e -> e.getUsageScenario_UsageModel().stream())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all usage scenarios and returns all entry level system calls
     * 
     * @return List<AbstractUserAction>
     */
    public List<AbstractUserAction> getEntryLevelSystemCalls() {

        List<AbstractUserAction> allActions = new LinkedList<>();
        List<AbstractUserAction> onlyEntryLevelSystemCalls = new LinkedList<>();
        allActions
                .addAll(getUsageScenarios().stream()
                        .flatMap(e -> e.getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour().stream())
                        .filter(e -> e instanceof EntryLevelSystemCall || e instanceof Branch
                                || e instanceof BranchTransition || e instanceof Loop)
                        .collect(Collectors.toCollection(LinkedList::new)));

        for (AbstractUserAction action : allActions) {
            if (action instanceof EntryLevelSystemCall) {
                onlyEntryLevelSystemCalls.add(action);
            } else if (action instanceof Branch) {

                onlyEntryLevelSystemCalls.addAll(getBranchNestedEntryLevelSystemCalls((Branch) action));
            } else if (action instanceof Loop) {
                onlyEntryLevelSystemCalls.addAll(((Loop) action).getBodyBehaviour_Loop().getActions_ScenarioBehaviour()
                        .stream().filter(e -> e instanceof EntryLevelSystemCall)
                        .collect(Collectors.toCollection(LinkedList::new)));
            } else if (action instanceof BranchTransition) {
                onlyEntryLevelSystemCalls.addAll(((BranchTransition) action).getBranchedBehaviour_BranchTransition()
                        .getActions_ScenarioBehaviour().stream().filter(e -> e instanceof EntryLevelSystemCall)
                        .collect(Collectors.toCollection(LinkedList::new)));
            }
        }
        return onlyEntryLevelSystemCalls;

    }

    /**
     * gets nested branch elements
     * 
     * @param branch
     *            the branch to investigate
     * @return list of deeper nested Abstract user actions
     */
    private List<AbstractUserAction> getBranchNestedEntryLevelSystemCalls(Branch branch) {
        List<AbstractUserAction> actions = new LinkedList<>();
        for (BranchTransition trans : branch.getBranchTransitions_Branch()) {
            actions.addAll(trans.getBranchedBehaviour_BranchTransition().getActions_ScenarioBehaviour().stream()
                    .filter(e -> e instanceof EntryLevelSystemCall).collect(Collectors.toCollection(LinkedList::new)));
        }

        return actions;

    }

    /**
     * iterates over all seffs and returns all external call action
     * 
     * @return List<EObject>
     */
    public List<EObject> getExternalCallActions() {

        return getSeffs().stream().flatMap(e -> e.eContents().stream()).filter(e -> e instanceof ExternalCallAction)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all repositories and returns all basic components
     * 
     * @return List<EObject>
     */
    public List<EObject> getComponents() {

        return dataApplication.getModelAccessor().getRepository().stream().flatMap(e -> e.eContents().stream())
                .filter(e -> e instanceof BasicComponent).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all basic components and returns all seffs
     * 
     * @return List<EObject>
     */
    public List<EObject> getSeffs() {

        return getComponents().stream().flatMap(e -> e.eContents().stream())
                .filter(e -> e instanceof ResourceDemandingSEFF).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * iterates over all repositories and returns all operation signatures
     * 
     * @return List<EObject>
     */
    public List<EObject> getSignatures() {

        if (currentSecondStageModel instanceof OperationProvidedRole) {
            OperationProvidedRole model = (OperationProvidedRole) currentSecondStageModel;
            List<EObject> list = new LinkedList<>();
            list.addAll(model.getProvidedInterface__OperationProvidedRole().getSignatures__OperationInterface());
            return list;

        } else if (currentSecondStageModel instanceof OperationRequiredRole) {
            OperationRequiredRole model = (OperationRequiredRole) currentSecondStageModel;
            List<EObject> list = new LinkedList<>();
            list.addAll(model.getRequiredInterface__OperationRequiredRole().getSignatures__OperationInterface());
            return list;
        }
        return new LinkedList<>();

    }

    /**
     * returns all objects which are needed to be presented in the first step of the creation of a
     * measuring point
     * 
     * @return Object[]
     */
    public Object[] getAllSecondPageObjects() {

        List<Object> allmodels = new LinkedList<>();
        addOnlyFilledLists(allmodels, dataApplication.getModelAccessor().getResourceEnvironment());
        addOnlyFilledLists(allmodels, dataApplication.getModelAccessor().getSystem());
        addOnlyFilledLists(allmodels, dataApplication.getModelAccessor().getSubSystem());
        addOnlyFilledLists(allmodels, getAssemblyContexts());
        addOnlyFilledLists(allmodels, getResourceContainer());
        addOnlyFilledLists(allmodels, getActiveResources());
        addOnlyFilledLists(allmodels, getLinkingResources());
        addOnlyFilledLists(allmodels, getUsageScenarios());
        addOnlyFilledLists(allmodels, getExternalCallActions());
        addOnlyFilledLists(allmodels, getEntryLevelSystemCalls());

        return allmodels.toArray();
    }

    /**
     * retrieves all measuring points, which exist and casts them to an object array for the
     * treeviewer
     * 
     * @return array of all existing measuring points
     */
    public Object[] getExistingMeasuringPoints() {
        if (isEditing()) {
            List<MeasuringPoint> points = DataApplication.getInstance().getModelAccessor()
                    .getUnassignedMeasuringPoints();
            points.add(getMonitor().getMeasuringPoint());
            return points.toArray();
        } else {
            return DataApplication.getInstance().getModelAccessor().getUnassignedMeasuringPoints().toArray();
        }
    }

    /**
     * helper class to filter out empty model lists
     * 
     * @param fillerList
     *            the list of all the lists of models required for the second wizard page
     * @param modelList
     *            the list with all the models of a type
     * @return List
     */
    public List<Object> addOnlyFilledLists(List<Object> fillerList, List<?> modelList) {

        if (!modelList.isEmpty()) {
            fillerList.add(modelList);
            return fillerList;
        }
        return fillerList;
    }

    /**
     * returns all objects which are needed in the second step of the creation of a measuring point.
     * It differentiates between the different models of the first step to return the corresponding
     * models
     * 
     * @return Object[]
     */
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
        } else if (currentSelectionFirstMeasuringModel instanceof System) {
            System context = (System) currentSelectionFirstMeasuringModel;
            elements.addAll(context.getProvidedRoles_InterfaceProvidingEntity());
            elements.addAll(context.getRequiredRoles_InterfaceRequiringEntity());

            return elements.toArray();
        } else if (currentSelectionFirstMeasuringModel instanceof SubSystem) {
            SubSystem context = (SubSystem) currentSelectionFirstMeasuringModel;
            elements.addAll(context.getProvidedRoles_InterfaceProvidingEntity());
            elements.addAll(context.getRequiredRoles_InterfaceRequiringEntity());
            return elements.toArray();
        }
        return elements.toArray();

    }

    /**
     * getter for the selection of the first step in the creation of a measuring point
     * 
     * @return Object
     */
    public Object getCurrentSelection() {
        return this.currentSelectionFirstMeasuringModel;
    }

    /**
     * setter for the selection of the first step in the creation of a measuring point
     * 
     * @param current
     *            the current object selected in the tree
     */
    public void setCurrentSelection(Object current) {
        this.currentSelectionFirstMeasuringModel = current;
    }

    /**
     * getter for the monitor the measuring point needs to be added to
     * 
     * @return Monitor
     */
    public Monitor getMonitor() {
        return monitor;
    }

    /**
     * setter for the monitor the measuring point needs to be added to
     * 
     * @param monitor
     *            the monitor the wizard operates on
     */
    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    /**
     * getter for the selection of the second step in the creation of a measuring point
     * 
     * @return Object
     */
    public Object getCurrentSecondStageModel() {
        return currentSecondStageModel;
    }

    /**
     * setter for the selection of the second step in the creation of a measuring point
     * 
     * @param currentSecondStageModel
     *            the current secondary model selected in the measuring point wizard
     */
    public void setCurrentSecondStageModel(Object currentSecondStageModel) {
        this.currentSecondStageModel = currentSecondStageModel;
    }

    /**
     * getter for the selection of the third step in the creation of a measuring point
     * 
     * @return Object
     */
    public Object getCurrentThirdStageModel() {
        return currentThirdStageModel;
    }

    /**
     * setter for the selection of the third step in the creation of a measuring point
     * 
     * @param currentThirdStageModel
     *            the current third model selected in the measuring point wizard
     */
    public void setCurrentThirdStageModel(Object currentThirdStageModel) {
        this.currentThirdStageModel = currentThirdStageModel;
    }

    /**
     * indicates whether the wizard model is complete, so the wizard can finish
     * 
     * @return boolean
     */
    public boolean isFinishable() {
        return finishable;
    }

    /**
     * sets the wizard model to finishable, if true, the wizard can perform the finish operations
     * 
     * @param finishable
     *            boolean indicating whether finish operations of wizard can be performed or not
     */
    public void setFinishable(boolean finishable) {
        this.finishable = finishable;
    }

    /**
     * indicates whether the wizard is in editing mode or not
     * 
     * @return boolean
     */
    public boolean isEditing() {
        return isEditing;
    }

    /**
     * sets the wizard models editing flag, true means it is in editing mode
     * 
     * @param isEditing
     *            flag for editing mode
     */
    public void setEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }
}
