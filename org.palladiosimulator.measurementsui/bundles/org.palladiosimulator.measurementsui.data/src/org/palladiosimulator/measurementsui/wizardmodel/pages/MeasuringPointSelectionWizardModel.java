package org.palladiosimulator.measurementsui.wizardmodel.pages;

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

/**
 * 
 * @author Domas Mikalkinas
 *
 */
public class MeasuringPointSelectionWizardModel implements WizardModel {

	private final String createMeasuringPointInfoText = "Select the element of your Models which should be monitored during a simulation run. ";
	private final String editMeasuringPointInfoText = "Select a different measuring Point.";

	private final String createMeasuringPointTitel = "Create Measuring Point";
	private final String editMeasuringPointTitel = "Edit Measuring Point";

	private Monitor monitor;
	private boolean isEditing;
	private static MeasuringPointSelectionWizardModel instance;
	MonitorRepositoryFactory mf = new MonitorRepositoryFactoryImpl();
	ResourceEditorImpl editor = new ResourceEditorImpl();
	Object currentSelectionFirstMeasuringModel;
	Object currentSecondStageModel;
	Object currentThirdStageModel;

	DataApplication da = DataApplication.getInstance();

	public MeasuringPointSelectionWizardModel(Monitor monitor, boolean isEditing) {
		this.monitor = monitor;
		this.isEditing = isEditing;

	}

	private void setMeasuringPointDependingOnEditMode(Monitor monitor, MeasuringPoint measuringPoint, boolean isEditing) {
		if (isEditing) {
			editor.setMeasuringPointToMonitor(monitor, measuringPoint);
		}else {
			monitor.setMeasuringPoint(measuringPoint);
		}
	}
	
	/**
	 * checks which element from the measuringpoint wizard is selected, creates the
	 * corresponding measuring point and adds it to the monitor
	 * 
	 * @param model
	 */
	public void createMeasuringPoint(Object model) {

		PcmmeasuringpointPackage pcmMeasuringPointPackage = PcmmeasuringpointPackage.eINSTANCE;
		PcmmeasuringpointFactory pcmMeasuringPointFactory = pcmMeasuringPointPackage.getPcmmeasuringpointFactory();

		if (model instanceof ResourceContainer) {
			ResourceContainerMeasuringPoint mp = (ResourceContainerMeasuringPoint) pcmMeasuringPointFactory
					.create(PcmmeasuringpointPackage.eINSTANCE.getResourceContainerMeasuringPoint());

			mp.setResourceContainer((ResourceContainer) model);
			mp.setStringRepresentation(((ResourceContainer) model).getEntityName());
			mp.setResourceURIRepresentation(((ResourceContainer) model).eResource().getURI().toString() + "#"
					+ ((ResourceContainer) model).getId());
			setMeasuringPointDependingOnEditMode(monitor, mp, isEditing);
		} else if (model instanceof ProcessingResourceSpecification) {
			ActiveResourceMeasuringPoint mp = (ActiveResourceMeasuringPoint) pcmMeasuringPointFactory
					.create(PcmmeasuringpointPackage.eINSTANCE.getActiveResourceMeasuringPoint());

			mp.setActiveResource((ProcessingResourceSpecification) model);
			mp.setStringRepresentation(((ProcessingResourceSpecification) model)
					.getActiveResourceType_ActiveResourceSpecification().getEntityName());
			mp.setResourceURIRepresentation(((ProcessingResourceSpecification) model).eResource().getURI().toString()
					+ "#" + ((ProcessingResourceSpecification) model).getId());
			setMeasuringPointDependingOnEditMode(monitor, mp, isEditing);

		} else if (model instanceof AssemblyContext && currentSecondStageModel instanceof PassiveResource) {
			AssemblyPassiveResourceMeasuringPoint mp = (AssemblyPassiveResourceMeasuringPoint) pcmMeasuringPointFactory
					.create(PcmmeasuringpointPackage.eINSTANCE.getAssemblyPassiveResourceMeasuringPoint());

			mp.setAssembly((AssemblyContext) model);
			mp.setPassiveResource((PassiveResource) currentSecondStageModel);
			mp.setStringRepresentation(((PassiveResource) model).getEntityName());
			mp.setResourceURIRepresentation(((PassiveResource) model).eResource().getURI().toString() + "#"
					+ ((PassiveResource) model).getId());
			setMeasuringPointDependingOnEditMode(monitor, mp, isEditing);

		} else if (model instanceof AssemblyContext) {
			AssemblyOperationMeasuringPoint mp = (AssemblyOperationMeasuringPoint) pcmMeasuringPointFactory
					.create(PcmmeasuringpointPackage.eINSTANCE.getAssemblyOperationMeasuringPoint());

			mp.setAssembly((AssemblyContext) model);
			mp.setOperationSignature((OperationSignature) currentThirdStageModel);
			mp.setRole((Role) currentSecondStageModel);
			mp.setStringRepresentation(((AssemblyContext) model).getEntityName());
			mp.setResourceURIRepresentation(((AssemblyContext) model).eResource().getURI().toString() + "#"
					+ ((AssemblyContext) model).getId());
			setMeasuringPointDependingOnEditMode(monitor, mp, isEditing);

		} else if (model instanceof EntryLevelSystemCall) {
			EntryLevelSystemCallMeasuringPoint mp = (EntryLevelSystemCallMeasuringPoint) pcmMeasuringPointFactory
					.create(PcmmeasuringpointPackage.eINSTANCE.getEntryLevelSystemCallMeasuringPoint());

			mp.setEntryLevelSystemCall((EntryLevelSystemCall) model);
			mp.setStringRepresentation(((EntryLevelSystemCall) model).getEntityName());
			mp.setResourceURIRepresentation(((EntryLevelSystemCall) model).eResource().getURI().toString() + "#"
					+ ((EntryLevelSystemCall) model).getId());
			setMeasuringPointDependingOnEditMode(monitor, mp, isEditing);

		} else if (model instanceof ExternalCallAction) {
			ExternalCallActionMeasuringPoint mp = (ExternalCallActionMeasuringPoint) pcmMeasuringPointFactory
					.create(PcmmeasuringpointPackage.eINSTANCE.getExternalCallActionMeasuringPoint());
			// ExternalCallActionMeasuringPoint mp =
			// pcmMeasuringPointFactory.createExternalCallActionMeasuringPoint();
			mp.setExternalCall((ExternalCallAction) model);
			mp.setStringRepresentation(((ExternalCallAction) model).getEntityName());
			mp.setResourceURIRepresentation(((ExternalCallAction) model).eResource().getURI().toString() + "#"
					+ ((ExternalCallAction) model).getId());
			setMeasuringPointDependingOnEditMode(monitor, mp, isEditing);
		} else if (model instanceof LinkingResource) {
			LinkingResourceMeasuringPoint mp = (LinkingResourceMeasuringPoint) pcmMeasuringPointFactory
					.create(PcmmeasuringpointPackage.eINSTANCE.getLinkingResourceMeasuringPoint());

			mp.setLinkingResource((LinkingResource) model);
			mp.setStringRepresentation(((LinkingResource) model).getEntityName());
			mp.setResourceURIRepresentation(((LinkingResource) model).eResource().getURI().toString() + "#"
					+ ((LinkingResource) model).getId());
			setMeasuringPointDependingOnEditMode(monitor, mp, isEditing);
		} else if (model instanceof ResourceEnvironment) {
			ResourceEnvironmentMeasuringPoint mp = (ResourceEnvironmentMeasuringPoint) pcmMeasuringPointFactory
					.create(PcmmeasuringpointPackage.eINSTANCE.getResourceEnvironmentMeasuringPoint());

			mp.setResourceEnvironment((ResourceEnvironment) model);
			mp.setStringRepresentation(((ResourceEnvironment) model).getEntityName());
			mp.setResourceURIRepresentation(((ResourceEnvironment) model).eResource().getURI().toString() + "#/0");
			setMeasuringPointDependingOnEditMode(monitor, mp, isEditing);
		} else if (model instanceof SubSystem) {
			SubSystemOperationMeasuringPoint mp = (SubSystemOperationMeasuringPoint) pcmMeasuringPointFactory
					.create(PcmmeasuringpointPackage.eINSTANCE.getSubSystemOperationMeasuringPoint());

			mp.setSubsystem((SubSystem) model);
			mp.setOperationSignature((OperationSignature) currentThirdStageModel);
			mp.setRole((Role) currentSecondStageModel);
			mp.setStringRepresentation(((SubSystem) model).getEntityName());
			mp.setResourceURIRepresentation(
					((SubSystem) model).eResource().getURI().toString() + "#" + ((SubSystem) model).getId());
			setMeasuringPointDependingOnEditMode(monitor, mp, isEditing);

		} else if (model instanceof System) {
			SystemOperationMeasuringPoint mp = (SystemOperationMeasuringPoint) pcmMeasuringPointFactory
					.create(PcmmeasuringpointPackage.eINSTANCE.getSystemOperationMeasuringPoint());

			mp.setSystem((System) model);
			mp.setOperationSignature((OperationSignature) currentThirdStageModel);
			mp.setRole((Role) currentSecondStageModel);
			mp.setStringRepresentation(((System) model).getEntityName());
			mp.setResourceURIRepresentation(
					((System) model).eResource().getURI().toString() + "#" + ((System) model).getId());
			setMeasuringPointDependingOnEditMode(monitor, mp, isEditing);

		} else if (model instanceof UsageScenario) {
			UsageScenarioMeasuringPoint mp = (UsageScenarioMeasuringPoint) pcmMeasuringPointFactory
					.create(PcmmeasuringpointPackage.eINSTANCE.getUsageScenarioMeasuringPoint());
			mp.setUsageScenario((UsageScenario) model);
			mp.setStringRepresentation(((UsageScenario) model).getEntityName());
			mp.setResourceURIRepresentation(
					((UsageScenario) model).eResource().getURI().toString() + "#" + ((UsageScenario) model).getId());
			setMeasuringPointDependingOnEditMode(monitor, mp, isEditing);

		}

	}

	/**
	 * helper method to add the measuringpoint to the monitor
	 * 
	 * @param measuringPoint
	 */
	public void addMeasuringPointToMonitor(MeasuringPoint measuringPoint) {
		monitor.setMeasuringPoint(measuringPoint);

	}

	/**
	 * adds a measuringpoint to the given repository
	 * 
	 * @param measuringPoint
	 */
	public void addMeasuringPointToRepository(MeasuringPoint measuringPoint) {
		MeasuringPointRepository measuringPointRepository = DataApplication.getInstance().getModelAccessor()
				.getMeasuringPointRepository().get(0);
		ResourceEditorImpl.getInstance().addMeasuringPointToRepository(measuringPointRepository, measuringPoint);
	}

	// /**
	// * sets the measuringpoint to the monitor
	// *
	// * @param measuringPoint
	// */
	// public void setMeasuringPointToMonitor(MeasuringPoint measuringPoint) {
	// ResourceEditorImpl.getInstance().setMeasuringPointToMonitor(this.monitor,
	// measuringPoint);
	//
	// }

	/**
	 * checks whether the monitor repository and measuringpoint of a monitor is set.
	 * If this is the case the wizard can be finished
	 */
	@Override
	public boolean canFinish() {
		return monitor.getMeasuringPoint() != null;
	}

	/**
	 * returns the info text of the wizard page
	 */
	@Override
	public String getInfoText() {
		if (this.monitor.getMeasuringPoint() != null) {
			return editMeasuringPointInfoText;
		}
		return createMeasuringPointInfoText;
	}

	@Override
	public boolean nextStep() {
		return true;
	}

	/**
	 * returns the title text of the wizard page
	 */
	@Override
	public String getTitleText() {
		if (this.monitor.getMeasuringPoint() != null) {
			return createMeasuringPointTitel;
		}
		return editMeasuringPointTitel;

	}

	/**
	 * iterates over all system elements and returns all assembly contexts
	 * 
	 * @return List<AssemblyContext>
	 */
	public List<AssemblyContext> getAssemblyContexts() {
		List<AssemblyContext> assemblyContexts = da.getModelAccessor().getSystem().stream()
				.flatMap(e -> e.getAssemblyContexts__ComposedStructure().stream())
				.collect(Collectors.toCollection(LinkedList::new));

		return assemblyContexts;
	}

	/**
	 * iterates over all resource environments and returns all resource containers
	 * 
	 * @return List<ResourceContainer>
	 */
	public List<ResourceContainer> getResourceContainer() {
		List<ResourceContainer> resourceContainer = da.getModelAccessor().getResourceEnvironment().stream()
				.flatMap(e -> e.getResourceContainer_ResourceEnvironment().stream())
				.collect(Collectors.toCollection(LinkedList::new));

		return resourceContainer;
	}

	/**
	 * iterates over all resource containers and returns all active resource
	 * specifications
	 * 
	 * @return List<ProcessingResourceSpecification>
	 */
	public List<ProcessingResourceSpecification> getActiveResources() {

		List<ProcessingResourceSpecification> activeresources = getResourceContainer().stream()
				.flatMap(e -> e.getActiveResourceSpecifications_ResourceContainer().stream())
				.collect(Collectors.toCollection(LinkedList::new));

		return activeresources;
	}

	/**
	 * iterates over all resource environments and returns all linking resources
	 * 
	 * @return List<LinkingResource>
	 */
	public List<LinkingResource> getLinkingResources() {

		List<LinkingResource> activeresources = da.getModelAccessor().getResourceEnvironment().stream()
				.flatMap(e -> e.getLinkingResources__ResourceEnvironment().stream())
				.collect(Collectors.toCollection(LinkedList::new));

		return activeresources;
	}

	/**
	 * iterates over all usage models and returns all usage scenarios
	 * 
	 * @return List<UsageScenario>
	 */
	public List<UsageScenario> getUsageScenarios() {

		List<UsageScenario> activeresources = da.getModelAccessor().getUsageModel().stream()
				.flatMap(e -> e.getUsageScenario_UsageModel().stream())
				.collect(Collectors.toCollection(LinkedList::new));

		return activeresources;
	}

	/**
	 * iterates over all usage scenarios and returns all entry level system calls
	 * 
	 * @return List<AbstractUserAction>
	 */
	public List<AbstractUserAction> getEntryLevelSystemCalls() {

		List<AbstractUserAction> activeresources = getUsageScenarios().stream()
				.flatMap(e -> e.getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour().stream())
				.filter(e -> e instanceof EntryLevelSystemCall).collect(Collectors.toCollection(LinkedList::new));

		return activeresources;
	}

	/**
	 * iterates over all seffs and returns all external call action
	 * 
	 * @return List<EObject>
	 */
	public List<EObject> getExternalCallActions() {

		List<EObject> activeresources = getSeffs().stream().flatMap(e -> e.eContents().stream())
				.filter(e -> e instanceof ExternalCallAction).collect(Collectors.toCollection(LinkedList::new));

		return activeresources;
	}

	/**
	 * iterates over all repositories and returns all basic components
	 * 
	 * @return List<EObject>
	 */
	public List<EObject> getComponents() {

		List<EObject> activeresources = da.getModelAccessor().getRepository().stream()
				.flatMap(e -> e.eContents().stream()).filter(e -> e instanceof BasicComponent)
				.collect(Collectors.toCollection(LinkedList::new));

		return activeresources;
	}

	/**
	 * iterates over all basic components and returns all seffs
	 * 
	 * @return List<EObject>
	 */
	public List<EObject> getSeffs() {

		List<EObject> activeresources = getComponents().stream().flatMap(e -> e.eContents().stream())
				.filter(e -> e instanceof ResourceDemandingSEFF).collect(Collectors.toCollection(LinkedList::new));

		return activeresources;
	}

	/**
	 * iterates over all repositories and returns all operation signatures
	 * 
	 * @return List<EObject>
	 */
	public List<EObject> getSignatures() {

		List<EObject> activeresources = da.getModelAccessor().getRepository().stream()
				.flatMap(e -> e.eContents().stream()).filter(e -> e instanceof OperationInterface)
				.collect(Collectors.toList());

		List<EObject> activeresources2 = activeresources.stream().flatMap(e -> e.eContents().stream())
				.filter(e -> e instanceof OperationSignature).collect(Collectors.toList());

		return activeresources2;

	}

	/**
	 * returns all objects which are needed to be presented in the first step of the
	 * creation of a measuring point
	 * 
	 * @return Object[]
	 */
	public Object[] getAllSecondPageObjects() {

		List<Object> allmodels = new LinkedList<>();
		addOnlyFilledLists(allmodels, da.getModelAccessor().getResourceEnvironment());
		addOnlyFilledLists(allmodels, da.getModelAccessor().getSystem());
		addOnlyFilledLists(allmodels, da.getModelAccessor().getSubSystem());

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
	 * helper class to filter out empty model lists
	 * 
	 * @param fillerList
	 * @param modelList
	 * @return List
	 */
	public List addOnlyFilledLists(List fillerList, List modelList) {

		if (!modelList.isEmpty()) {
			fillerList.add(modelList);
			return fillerList;
		}
		return fillerList;
	}

	/**
	 * returns all objects which are needed in the second step of the creation of a
	 * measuring point. It differentiates between the different models of the first
	 * step to return the corresponding models
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

	/**
	 * getter for the selection of the first step in the creation of a measuring
	 * point
	 * 
	 * @return Object
	 */
	public Object getCurrentSelection() {
		return this.currentSelectionFirstMeasuringModel;
	}

	/**
	 * setter for the selection of the first step in the creation of a measuring
	 * point
	 * 
	 * @return Object
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
	 */
	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	/**
	 * getter for the selection of the second step in the creation of a measuring
	 * point
	 * 
	 * @return Object
	 */
	public Object getCurrentSecondStageModel() {
		return currentSecondStageModel;
	}

	/**
	 * setter for the selection of the second step in the creation of a measuring
	 * point
	 * 
	 * @return Object
	 */
	public void setCurrentSecondStageModel(Object currentSecondStageModel) {
		this.currentSecondStageModel = currentSecondStageModel;
	}

	/**
	 * getter for the selection of the third step in the creation of a measuring
	 * point
	 * 
	 * @return Object
	 */
	public Object getCurrentThirdStageModel() {
		return currentThirdStageModel;
	}

	/**
	 * setter for the selection of the third step in the creation of a measuring
	 * point
	 * 
	 * @return Object
	 */
	public void setCurrentThirdStageModel(Object currentThirdStageModel) {
		this.currentThirdStageModel = currentThirdStageModel;
	}
}
