package org.palladiosimulator.measurementsui.wizard.handlers.contentprovider;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.system.System;

/**
 * An alternative content provider for a hierarchical view of the
 * measuringpoints
 * 
 * @author Domas Mikalkinas
 *
 */
public class AlternativeMeasuringPointContentProvider implements ITreeContentProvider {
	private DataApplication da = DataApplication.getInstance();

	@Override
	public Object[] getElements(Object inputElement) {

		List<Object> elementList = new LinkedList<>();

		// elementList.addAll(da.getModelAccessor().getAllocation());

		elementList.addAll(da.getModelAccessor().getRepository().stream().filter(
				e -> (!e.getEntityName().equals("FailureTypes")) && (!e.getEntityName().equals("PrimitiveDataTypes")))
				.collect(Collectors.toCollection(LinkedList::new)));

		elementList.addAll(da.getModelAccessor().getResourceEnvironment());
		elementList.addAll(da.getModelAccessor().getSubSystem());
		elementList.addAll(da.getModelAccessor().getSystem());
		elementList.addAll(da.getModelAccessor().getUsageModel());
		return elementList.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof System) {
			return ((System) parentElement).getAssemblyContexts__ComposedStructure().toArray();

		} else if (parentElement instanceof ResourceEnvironment) {
			List<Object> elements = new LinkedList<>();
			elements.addAll(((ResourceEnvironment) parentElement).getLinkingResources__ResourceEnvironment());
			elements.addAll(((ResourceEnvironment) parentElement).getResourceContainer_ResourceEnvironment());
			return elements.toArray();

		} else if (parentElement instanceof ResourceContainer) {
			return ((ResourceContainer) parentElement).getActiveResourceSpecifications_ResourceContainer().toArray();

		} else if (parentElement instanceof UsageModel) {
			return ((UsageModel) parentElement).getUsageScenario_UsageModel().toArray();
		} else if (parentElement instanceof UsageScenario) {
			return new Object[] { ((UsageScenario) parentElement).getScenarioBehaviour_UsageScenario() };
		} else if (parentElement instanceof ScenarioBehaviour) {
			return ((ScenarioBehaviour) parentElement).getActions_ScenarioBehaviour().stream()
					.filter(e -> e instanceof EntryLevelSystemCall).collect(Collectors.toCollection(LinkedList::new))
					.toArray();
		} else if (parentElement instanceof Repository) {
			return ((Repository) parentElement).eContents().stream().filter(e -> e instanceof BasicComponent)
					.collect(Collectors.toCollection(LinkedList::new)).toArray();
		} else if (parentElement instanceof BasicComponent) {
			List<Object> appendingBasicComponentObjects = new LinkedList<>();
			appendingBasicComponentObjects
					.addAll(((BasicComponent) parentElement).getServiceEffectSpecifications__BasicComponent());
			appendingBasicComponentObjects.addAll(((BasicComponent) parentElement).getPassiveResource_BasicComponent());
			return appendingBasicComponentObjects.toArray();
		} else if (parentElement instanceof ResourceDemandingSEFF) {
			return ((ResourceDemandingSEFF) parentElement).getSteps_Behaviour().stream()
					.filter(e -> e instanceof ExternalCallAction).collect(Collectors.toCollection(LinkedList::new))
					.toArray();
		}

		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof System) {
return !((System) element).getAssemblyContexts__ComposedStructure().isEmpty();
		} else if (element instanceof ResourceEnvironment) {
return !((ResourceEnvironment) element).getLinkingResources__ResourceEnvironment().isEmpty() || !((ResourceEnvironment) element).getResourceContainer_ResourceEnvironment().isEmpty();
		} else if (element instanceof ResourceContainer) {
return !((ResourceContainer) element).getActiveResourceSpecifications_ResourceContainer().isEmpty();
		} else if (element instanceof UsageModel) {
		return	!((UsageModel) element).getUsageScenario_UsageModel().isEmpty();
		} else if (element instanceof UsageScenario) {
		return	((UsageScenario) element).getScenarioBehaviour_UsageScenario()!=null;
		} else if (element instanceof ScenarioBehaviour) {
			return !((ScenarioBehaviour) element).getActions_ScenarioBehaviour().stream()
					.filter(e -> e instanceof EntryLevelSystemCall).collect(Collectors.toCollection(LinkedList::new)).isEmpty();
		} else if (element instanceof Repository) {
			return !((Repository) element).eContents().stream().filter(e -> e instanceof BasicComponent)
			.collect(Collectors.toCollection(LinkedList::new)).isEmpty();
		} else if (element instanceof BasicComponent) {
			return !((BasicComponent) element).getServiceEffectSpecifications__BasicComponent().isEmpty()|| !((BasicComponent) element).getPassiveResource_BasicComponent().isEmpty();
		} else if (element instanceof ResourceDemandingSEFF) {
return !((ResourceDemandingSEFF) element).getSteps_Behaviour().stream()
		.filter(e -> e instanceof ExternalCallAction).collect(Collectors.toCollection(LinkedList::new)).isEmpty();
		}
		return false;
	}

}
