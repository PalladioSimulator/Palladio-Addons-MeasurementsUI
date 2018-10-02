package org.palladiosimulator.measurementsui.extensionpoint.evaluation;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.extensionpoint.definition.IMeasuringPointMetricsWorkingCombinations;
import org.palladiosimulator.metricspec.MetricSpecPackage;
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
 * This class is used to evaluate our Extensionpoint measuringPointMetricsWorkingCombinations.
 * It loads all Extensions connected to this Extensionpoint and adds them
 * to the MeasuringPointMetricsCombinations object accordingly.
 * 
 * @author Lasse
 *
 */
public class EvaluateExtensions {

	private static final String ID = "org.palladiosimulator.measurementsui.extensionpoint.definition.measuringPointMetricsWorkingCombinations";
	private final MeasuringPointMetricsCombinations measuringPointMetricsCombinations;

	/**
	 * Constructor which creates an object of MeasuringPointMetricsCombinations
	 */
	public EvaluateExtensions() {
		this.measuringPointMetricsCombinations = new MeasuringPointMetricsCombinations();
	}
	
	/**
	 * Returns the instance of MeasuringPointMetricsCombinations
	 * @return MeasuringPointMetricsCombinations
	 */
	public MeasuringPointMetricsCombinations getMeasuringPointmetricsCombinations() {
		return this.measuringPointMetricsCombinations;
	}

	/**
	 * Loads all Extensions to the Extensionpoint from the ExtensionRegistry
	 * and adds their content accordingly to the MeasuringPointMetricsCombinations obejct
	 */
	public void loadExtensions() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();

		IConfigurationElement[] configurationElements = registry.getConfigurationElementsFor(ID);

		List<IMeasuringPointMetricsWorkingCombinations> possibleMetricDescriptionExtensions = new LinkedList<>();

		try {
			for (IConfigurationElement configurationElement : configurationElements) {
				final Object possibleMetricDescription = configurationElement.createExecutableExtension("class");
				if (possibleMetricDescription instanceof IMeasuringPointMetricsWorkingCombinations) {
					possibleMetricDescriptionExtensions.add((IMeasuringPointMetricsWorkingCombinations) possibleMetricDescription);
				}
			}

		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
		}
		
	

		EClassifier classif = PcmmeasuringpointPackage.eINSTANCE.getEClassifier("UsageScenarioMeasuringPoint");
		if (classif != null && classif instanceof EClass) {
		  // At this point, you have the right EClass, 
		  // you can now create an instance using the factory
		  EObject myinstance = PcmmeasuringpointFactory.eINSTANCE.create((EClass)classif);
		}
		
		for (IMeasuringPointMetricsWorkingCombinations iPossibleMetricDescription : possibleMetricDescriptionExtensions) {

			if (iPossibleMetricDescription.getMeasuringPoint() instanceof UsageScenarioMeasuringPoint) {
				this.measuringPointMetricsCombinations.addUsageScenarioMeasuringPointMetric(iPossibleMetricDescription);

			} else if (iPossibleMetricDescription.getMeasuringPoint() instanceof AssemblyOperationMeasuringPoint) {
				this.measuringPointMetricsCombinations
						.addAssemblyOperationMeasuringPointMetric(iPossibleMetricDescription);

			} else if (iPossibleMetricDescription
					.getMeasuringPoint() instanceof AssemblyPassiveResourceMeasuringPoint) {
				this.measuringPointMetricsCombinations
						.addAssemblyPassiveResourceMeasuringPointMetric(iPossibleMetricDescription);

			} else if (iPossibleMetricDescription.getMeasuringPoint() instanceof ResourceContainerMeasuringPoint) {
				this.measuringPointMetricsCombinations
						.addResourceContainerMeasuringPointMetric(iPossibleMetricDescription);

			} else if (iPossibleMetricDescription.getMeasuringPoint() instanceof ResourceEnvironmentMeasuringPoint) {
				this.measuringPointMetricsCombinations
						.addResourceEnvironmentMeasuringPointMetric(iPossibleMetricDescription);

			} else if (iPossibleMetricDescription.getMeasuringPoint() instanceof SubSystemOperationMeasuringPoint) {
				this.measuringPointMetricsCombinations
						.addSubSystemOperationMeasuringPointMetric(iPossibleMetricDescription);

			} else if (iPossibleMetricDescription.getMeasuringPoint() instanceof SystemOperationMeasuringPoint) {
				this.measuringPointMetricsCombinations
						.addSystemOperationMeasuringPointMetric(iPossibleMetricDescription);

			} else if (iPossibleMetricDescription.getMeasuringPoint() instanceof EntryLevelSystemCallMeasuringPoint) {
				this.measuringPointMetricsCombinations
						.addEntryLevelSystemCallMeasuringPointMetric(iPossibleMetricDescription);

			} else if (iPossibleMetricDescription.getMeasuringPoint() instanceof LinkingResourceMeasuringPoint) {
				this.measuringPointMetricsCombinations
						.addLinkingResourceMeasuringPointMetric(iPossibleMetricDescription);

			} else if (iPossibleMetricDescription.getMeasuringPoint() instanceof ExternalCallActionMeasuringPoint) {
				this.measuringPointMetricsCombinations
						.addExternalCallActionMeasuringPointMetric(iPossibleMetricDescription);

			} else if (iPossibleMetricDescription.getMeasuringPoint() instanceof ActiveResourceMeasuringPoint) {
				this.measuringPointMetricsCombinations
						.addActiveResourceMeasuringPointMetric(iPossibleMetricDescription);
			}

		}
	}
}