package org.palladiosimulator.measurementsui.extensionpoint.evaluation;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.palladiosimulator.measurementsui.extensionpoint.definition.IMeasuringPointMetricsWorkingCombinations;
import org.palladiosimulator.pcmmeasuringpoint.ActiveResourceMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.AssemblyOperationMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.AssemblyPassiveResourceMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.EntryLevelSystemCallMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.ExternalCallActionMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.LinkingResourceMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.ResourceContainerMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.ResourceEnvironmentMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.SubSystemOperationMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.SystemOperationMeasuringPoint;
import org.palladiosimulator.pcmmeasuringpoint.UsageScenarioMeasuringPoint;

public class EvaluateExtensions {

	private static final String ID = "org.palladiosimulator.measurementsui.extensionpoint.definition.MeasuringPointMetricsWorkingCombinations";
	private final MeasuringPointMetricsCombinations measuringPointMetricsCombinations;

	public EvaluateExtensions() {
		this.measuringPointMetricsCombinations = new MeasuringPointMetricsCombinations();
	}
	
	public MeasuringPointMetricsCombinations getMeasuringPointmetricsCombinations() {
		return this.measuringPointMetricsCombinations;
	}

	public void loadExtensions() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();

		IConfigurationElement[] configurationElements = registry.getConfigurationElementsFor(ID);

		List<IMeasuringPointMetricsWorkingCombinations> possibleMetricDescriptionExtensions = new LinkedList<>();

		try {
			for (IConfigurationElement configurationElement : configurationElements) {
				System.out.println("Evaluating extension");
				final Object possibleMetricDescription = configurationElement.createExecutableExtension("class");
				if (possibleMetricDescription instanceof IMeasuringPointMetricsWorkingCombinations) {
					possibleMetricDescriptionExtensions.add((IMeasuringPointMetricsWorkingCombinations) possibleMetricDescription);
				}
			}

		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
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