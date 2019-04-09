package org.palladiosimulator.measurementsui.wizardmodel.pages;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.dataprovider.MeasuringPointModelElementProvider;
import org.palladiosimulator.measurementsui.util.CreateMeasuringPointSwitch;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.system.System;

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
    private MeasuringPointModelElementProvider measuringpointModelElementProvider = new MeasuringPointModelElementProvider();

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

    /**
     * returns the title text of the wizard page
     */
    @Override
    public String getTitleText() {
        if (this.monitor.getMeasuringPoint() != null) {
            return EDIT_MEASURINGPOINT_TITLE;
        }
        return CREATE_MEASURINGPOINT_TITLE;

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

    /**
     * checks which element from the measuringpoint wizard is selected, creates the corresponding
     * measuring point and adds it to the monitor
     * 
     * @param model
     *            the model, which indicates which measuringpoint needs to be created
     */
    public void createMeasuringPoint(Object model) {
        CreateMeasuringPointSwitch measuringSwitch = new CreateMeasuringPointSwitch();
        measuringSwitch.setSecondStageModel(currentSecondStageModel);
        measuringSwitch.setThirdStageModel(currentThirdStageModel);
        MeasuringPoint measuringPoint = measuringSwitch.doSwitch((EObject) model);
        setMeasuringPointDependingOnEditMode(measuringPoint);
    }

    /**
     * returns all objects which are needed to be presented in the first step of the creation of a
     * measuring point
     * 
     * @return Object[]
     */
    public Object[] getAllSecondPageObjects() {

        List<Object> allmodels = new LinkedList<>();
        addOnlyFilledLists(allmodels, dataApplication.getModelAccessor().getResourceEnvironmenList());
        addOnlyFilledLists(allmodels, dataApplication.getModelAccessor().getSystemList());
        addOnlyFilledLists(allmodels, dataApplication.getModelAccessor().getSubSystemList());
        addOnlyFilledLists(allmodels, measuringpointModelElementProvider.getAssemblyContexts());
        addOnlyFilledLists(allmodels, measuringpointModelElementProvider.getResourceContainer());
        addOnlyFilledLists(allmodels, measuringpointModelElementProvider.getActiveResources());
        addOnlyFilledLists(allmodels, measuringpointModelElementProvider.getLinkingResources());
        addOnlyFilledLists(allmodels, measuringpointModelElementProvider.getUsageScenarios());
        addOnlyFilledLists(allmodels, measuringpointModelElementProvider.getExternalCallActions());
        addOnlyFilledLists(allmodels, measuringpointModelElementProvider.getEntryLevelSystemCalls());

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
            List<MeasuringPoint> points = dataApplication.getModelAccessor()
                    .getUnassignedMeasuringPoints();
            if (getMonitor().getMeasuringPoint() != null) {
                points.add(getMonitor().getMeasuringPoint());

            }
            return points.toArray();
        } else {
            return dataApplication.getModelAccessor().getUnassignedMeasuringPoints().toArray();
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
     * Returns all models, which are needed for the hierarchical view in the measuring point
     * selection wizard page
     * 
     * @return list of all possible models
     */
    public List<Object> getAlternativeModels() {
        List<Object> elementList = new LinkedList<>();

        elementList.addAll(dataApplication.getModelAccessor().getRepositoryList().stream().filter(
                e -> (!e.getEntityName().equals("FailureTypes")) && (!e.getEntityName().equals("PrimitiveDataTypes")))
                .collect(Collectors.toCollection(LinkedList::new)));

        elementList.addAll(dataApplication.getModelAccessor().getResourceEnvironmenList());
        elementList.addAll(dataApplication.getModelAccessor().getSubSystemList());
        elementList.addAll(dataApplication.getModelAccessor().getSystemList());
        elementList.addAll(dataApplication.getModelAccessor().getUsageModelList());
        return elementList;
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
