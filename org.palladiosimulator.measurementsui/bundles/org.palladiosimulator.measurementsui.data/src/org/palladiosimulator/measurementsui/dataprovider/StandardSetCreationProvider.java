package org.palladiosimulator.measurementsui.dataprovider;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.subsystem.SubSystem;

/**
 * Provides the methods for creating the standard set depending on the resources in the project. A
 * new MP is created and assigned to a new Monitor. Depending on the Monitors that are selected, we
 * add a valid MeasurementSpecification <-> Metric Description pair with the Processing Type set to
 * "Feed Through".
 * 
 * @author Florian
 *
 */
public class StandardSetCreationProvider {

    /**
     * Creates a MP <-> Monitor Pair for every resource in the workspace
     * 
     * @return List of Monitors with MPs assigned for every resource
     */
    public List<Monitor> createMonitorForEveryResource() {
        List<Monitor> monitorList = new LinkedList<Monitor>();

        Monitor tempMonitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
        MeasuringPointSelectionWizardModel model = new MeasuringPointSelectionWizardModel(tempMonitor, false);

        Object[] allResources = model.getAllSecondPageObjects();

        for (Object thisResource : allResources) {
            for (Object everyResource : (List<Object>) thisResource) {
                model.setCurrentSelection(everyResource);
                Object[] additionalModels = model.getAllAdditionalModels();
                if (model.getCurrentSelection() instanceof AssemblyContext
                        || model.getCurrentSelection() instanceof org.palladiosimulator.pcm.system.System
                        || model.getCurrentSelection() instanceof SubSystem) {
                    tempMonitor = createMPsWithAdditionalModels(monitorList, tempMonitor, model, everyResource,
                            additionalModels);
                } else {
                    tempMonitor = addNewMPAndMonPairToList(monitorList, tempMonitor, model, everyResource);
                }
            }
        }
        return monitorList;
    }

    /**
     * adds suggested, valid Metric Description<->MeasurementSpecification pairs to each Monitor.
     * 
     * @param monitorArray
     */
    public void addMetricDescriptionsToAllMonitors(Monitor[] monitorArray) {
        for (Monitor aMonitor : monitorArray) {
            addSuggestedValidMetricDescriptionsToMonitor(aMonitor);
        }
    }

    /**
     * finds all suggested Metric Descriptions for the monitor, creates mspec <-> metricDesc pair
     * and adds it to the given Monitor.
     * 
     * @param monitor
     */
    public void addSuggestedValidMetricDescriptionsToMonitor(Monitor monitor) {
        EList<MetricDescription> suggestedMetricDescriptionsList = new BasicEList<>();
        UnselectedMetricSpecificationsProvider provider = new UnselectedMetricSpecificationsProvider();
        Map<MetricDescription, Boolean> validMetricDescriptions = provider
                .getAllValidMetricDescriptionsForMeasuringPoint(monitor);
        for (Map.Entry<MetricDescription, Boolean> entry : validMetricDescriptions.entrySet()) {
            if (entry.getValue()) {
                suggestedMetricDescriptionsList.add(entry.getKey());
            }
        }
        if (!suggestedMetricDescriptionsList.isEmpty()) {
            MonitorRepositoryFactory factory = MonitorRepositoryFactory.eINSTANCE;
            EList<MeasurementSpecification> mSpecList = new BasicEList<>();
            provider.createMeasurementSpecificationsForEveryMetricDescription(suggestedMetricDescriptionsList, factory,
                    mSpecList);
            provider.setMetricDescriptionForEveryMeasurementSpecification(suggestedMetricDescriptionsList, mSpecList);
            for (MeasurementSpecification aMSpec : mSpecList) {
                aMSpec.setProcessingType(MonitorRepositoryFactory.eINSTANCE.createFeedThrough());
            }
            monitor.eSet(monitor.eClass().getEStructuralFeature("measurementSpecifications"), mSpecList);
        }
    }

    /**
     * Creates the Monitor <-> MP pairs for MPs with additional models
     * 
     * @param monitorList
     * @param tempMonitor
     * @param model
     * @param everyResource
     * @param additionalModels
     * @return The new Monitor with the correct MP
     */
    private Monitor createMPsWithAdditionalModels(List<Monitor> monitorList, Monitor tempMonitor,
            MeasuringPointSelectionWizardModel model, Object everyResource, Object[] additionalModels) {
        for (Object eachAdditionalModel : additionalModels) {
            model.setCurrentSecondStageModel(eachAdditionalModel);
            List<EObject> signatures = model.getSignatures();
            if (!(model.getCurrentSecondStageModel() instanceof PassiveResource)) {
                tempMonitor = createMPsWithSignatures(monitorList, tempMonitor, model, everyResource, signatures);
            } else {
                tempMonitor = addNewMPAndMonPairToList(monitorList, tempMonitor, model, everyResource);

            }

        }
        return tempMonitor;
    }

    /**
     * Creates the Monitor <-> MP pairs for MPs that need further signatures.
     * 
     * @param monitorList
     * @param tempMonitor
     * @param model
     * @param everyResource
     * @param signatures
     * @return THe new Monitor with the correct MP
     */
    private Monitor createMPsWithSignatures(List<Monitor> monitorList, Monitor tempMonitor,
            MeasuringPointSelectionWizardModel model, Object everyResource, List<EObject> signatures) {
        for (EObject aSignature : signatures) {

            model.setCurrentThirdStageModel(aSignature);
            tempMonitor = addNewMPAndMonPairToList(monitorList, tempMonitor, model, everyResource);

        }
        return tempMonitor;
    }

    /**
     * Adds the new MP to a new Monitor and sets the name of the Monitor depending on the MP name.
     * 
     * @param monitorList
     * @param tempMonitor
     * @param model
     * @param everyResource
     * @return the new Monitor with the new MP and new name
     */
    private Monitor addNewMPAndMonPairToList(List<Monitor> monitorList, Monitor tempMonitor,
            MeasuringPointSelectionWizardModel model, Object everyResource) {
        model.createMeasuringPoint(everyResource);
        tempMonitor.setEntityName(tempMonitor.getMeasuringPoint().toString().replace("[TRANSIENT]", "")
                .replace("MeasuringPoint", "Monitor"));
        monitorList.add(tempMonitor);
        tempMonitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
        model.setMonitor(tempMonitor);
        return tempMonitor;
    }
}
