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

/**
 * An alternative content provider for a hierarchical view of the measuringpoints
 * 
 * @author Domas Mikalkinas
 *
 */
public class AlternativeMeasuringPointContentProvider implements ITreeContentProvider {
    private DataApplication da = DataApplication.getInstance();

    @Override
    public Object[] getElements(Object inputElement) {

        List<Object> elementList = new LinkedList<>();
        elementList.addAll(da.getModelAccessor().getAllocation().stream().filter(e -> e instanceof Repository)
                .collect(Collectors.toCollection(LinkedList::new)));
        elementList.addAll(da.getModelAccessor().getRepository());
        elementList.addAll(da.getModelAccessor().getResourceEnvironment());
        elementList.addAll(da.getModelAccessor().getSubSystem());
        elementList.addAll(da.getModelAccessor().getSystem());
        elementList.addAll(da.getModelAccessor().getUsageModel());
        return elementList.toArray();
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof Allocation) {
            return ((Allocation) parentElement).getAllocationContexts_Allocation().toArray();

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
            Object[] elements = { ((UsageScenario) parentElement).getScenarioBehaviour_UsageScenario() };
            return elements;
        } else if (parentElement instanceof ScenarioBehaviour) {
            return ((ScenarioBehaviour) parentElement).getActions_ScenarioBehaviour().stream()
                    .filter(e -> e instanceof EntryLevelSystemCall).collect(Collectors.toCollection(LinkedList::new))
                    .toArray();
        } else if (parentElement instanceof Repository) {

            return ((Repository) parentElement).eContents().stream().filter(e -> e instanceof BasicComponent)
                    .collect(Collectors.toCollection(LinkedList::new)).toArray();
        } else if (parentElement instanceof BasicComponent) {
            return ((BasicComponent) parentElement).getServiceEffectSpecifications__BasicComponent().toArray();
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
        return true;
    }

}
