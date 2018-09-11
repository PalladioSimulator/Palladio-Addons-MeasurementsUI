package org.palladiosimulator.measurementsui.fileaccess;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

public class SecondPageWizardModel {
	private static SecondPageWizardModel instance;
	Object currentSelection;
	DataApplication da = DataApplication.getInstance();

	public static SecondPageWizardModel getInstance() {
		if (SecondPageWizardModel.instance == null) {
			SecondPageWizardModel.instance = new SecondPageWizardModel();
		}
		return SecondPageWizardModel.instance;
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
		if (currentSelection instanceof AssemblyContext) {
			AssemblyContext context = (AssemblyContext) currentSelection;
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
		} else if (currentSelection instanceof org.palladiosimulator.pcm.system.System) {
			org.palladiosimulator.pcm.system.System context = (org.palladiosimulator.pcm.system.System) currentSelection;
			elements.addAll(context.getProvidedRoles_InterfaceProvidingEntity());
			elements.addAll(context.getRequiredRoles_InterfaceRequiringEntity());

			return elements.toArray();
		} else if (currentSelection instanceof SubSystem) {
			SubSystem context = (SubSystem) currentSelection;
			elements.addAll(context.getProvidedRoles_InterfaceProvidingEntity());
			elements.addAll(context.getRequiredRoles_InterfaceRequiringEntity());
			return elements.toArray();
		}
		return null;

	}

	public Object getCurrentSelection() {
		return this.currentSelection;
	}

	public void setCurrentSelection(Object current) {
		this.currentSelection = current;
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
