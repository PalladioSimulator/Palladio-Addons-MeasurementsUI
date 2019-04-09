package org.palladiosimulator.measurementsui.extensionpoint.evaluation;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.metricspec.MetricDescription;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.palladiosimulator.pcmmeasuringpoint.util.PcmmeasuringpointSwitch;

/**
 * This class is used to evaluate our Extensionpoint measuringPointMetricsWorkingCombinations. It
 * loads all Extensions connected to this Extensionpoint and adds them to the
 * MeasuringPointMetricsCombinations object accordingly.
 * 
 * @author Lasse Merz
 *
 */
public class EvaluateExtensions {

    private static final String EXTENSION_POINT_ID = "org.palladiosimulator.measurementsui.extensionpoint.definition.measuringPointMetricsWorkingCombinations";

    private Resource metricDescriptionConstants;
	private static final String PATHMAP_METRIC_SPEC_MODELS_COMMON_METRICS_METRICSPEC = "pathmap://METRIC_SPEC_MODELS/commonMetrics.metricspec";
    private static final Map<?, ?> OPTIONS = Collections.emptyMap();
    private final MeasuringPointMetricsCombinations measuringPointMetricsCombinations;
    private static final Logger logger = LoggerFactory.getLogger(EvaluateExtensions.class);

	/**
	 * Constructor which creates an object of MeasuringPointMetricsCombinations
	 * and loads the MetricDescriptionConstants
	 */
	public EvaluateExtensions() {
		this.measuringPointMetricsCombinations = new MeasuringPointMetricsCombinations();
		loadMetricDescriptionConstants();
	}
	
	/**
	 * Returns the instance of MeasuringPointMetricsCombinations
	 * @return MeasuringPointMetricsCombinations
	 */
	public MeasuringPointMetricsCombinations getMeasuringPointmetricsCombinations() {
		return this.measuringPointMetricsCombinations;
	}
	
	/**
	 * Loads the MetricDescriptionConstants from their file according to the
	 * filePath "pathmap://METRIC_SPEC_MODELS/commonMetrics.metricspec"
	 */
	private void loadMetricDescriptionConstants() {
	    final ResourceSet resourceSet = new ResourceSetImpl();
	    metricDescriptionConstants = resourceSet
                .createResource(URI.createURI(PATHMAP_METRIC_SPEC_MODELS_COMMON_METRICS_METRICSPEC, true));
        try {
            metricDescriptionConstants.load(OPTIONS);
        } catch (final IOException e) {
            logger.warn("IOException when loading metric description constants from {0}. Stacktrace: {1}",PATHMAP_METRIC_SPEC_MODELS_COMMON_METRICS_METRICSPEC, e.getMessage());
           
        }
	}
	
	/**
	 * Creates an instance of a MeasuringPoint depending on a given
	 * class name and returns it
	 * 
	 * @param measuringPointClassName class name of the MeasuringPoint
	 * @return MeasuringPoint instance
	 */
	private Optional<MeasuringPoint> getMeasuringPoint(String measuringPointClassName) {
	    EClassifier classifier = PcmmeasuringpointPackage.eINSTANCE.getEClassifier(measuringPointClassName);
        if (classifier instanceof EClass) {
          EObject measuringPointInstance = PcmmeasuringpointFactory.eINSTANCE.create((EClass)classifier);
          if(measuringPointInstance instanceof MeasuringPoint) {
              return Optional.of((MeasuringPoint) measuringPointInstance);
          }
        }
       return Optional.empty();
	}
	
	/**
	 * Creates an instance of a MetricDescription based on its id and
	 * returns it
	 * @param id of the MetricDescription
	 * @return MetricDesciption instance
	 */
	private Optional<MetricDescription> getMetricDescription(String id) {
	  EObject metricDescriptionInstance = metricDescriptionConstants.getEObject(id);
	  if(metricDescriptionInstance instanceof MetricDescription) {
	      return Optional.of((MetricDescription) metricDescriptionInstance);
	  }
	  return Optional.empty();
	}


    /**
     * Loads all Extensions to the Extensionpoint from the ExtensionRegistry and adds their content
     * accordingly to the MeasuringPointMetricsCombinations obejct
     */
    public void loadExtensions() {

        IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] configurationElements = registry.getConfigurationElementsFor(EXTENSION_POINT_ID);

		for (IConfigurationElement configurationElement : configurationElements) {		 
		    
		    String measuringPointClassname = configurationElement.getAttribute("MeasuringPoint");
		    String metricDescriptionId = configurationElement.getAttribute("MetricDescription");
		    String suggestedMetric = configurationElement.getAttribute("SuggestedMetricDescription");

		    if(getMeasuringPoint(measuringPointClassname).isPresent() && getMetricDescription(metricDescriptionId).isPresent() &&
		            (suggestedMetric.equalsIgnoreCase("true") || suggestedMetric.equalsIgnoreCase("false"))) {
		        MeasuringPoint measuringPointObject = getMeasuringPoint(measuringPointClassname).get();
		       
		        MetricDescription metricDescriptionObject = getMetricDescription(metricDescriptionId).get();
		        boolean suggestedMetricBoolean = Boolean.parseBoolean(suggestedMetric);

		        PcmmeasuringpointSwitch<MeasuringPoint> measuringPointSwitch = getPcmMeasuringPointSwitch(metricDescriptionObject, suggestedMetricBoolean);
		        measuringPointSwitch.doSwitch(measuringPointObject);		             
		    }
		}
	}

	/**
	 * Creates a PcmmeasuringpointSwitch instance, which switches depending on the different MeasuringPoints.
	 * It adds the given MetricDescription and boolean to its corresponding map.
	 * 
	 * @param metricDescription to add to its MeasuringPoint map
	 * @param suggestedMetric to add to its MeasuringPoint map
	 * @return PcmmeasuringpointSwitch<MeasuringPoint> instance
	 */
    private  PcmmeasuringpointSwitch<MeasuringPoint> getPcmMeasuringPointSwitch(MetricDescription metricDescription, boolean suggestedMetric){

	    return new PcmmeasuringpointSwitch<MeasuringPoint>() {
	        	        
	        @Override
	        public MeasuringPoint caseActiveResourceMeasuringPoint(ActiveResourceMeasuringPoint object) {
	            measuringPointMetricsCombinations.addMetricDescriptionToMap(measuringPointMetricsCombinations.getActiveResourceMeasuringPointMetrics(), metricDescription, suggestedMetric);
	            return object;
	        }
	        @Override
	        public MeasuringPoint caseAssemblyOperationMeasuringPoint(AssemblyOperationMeasuringPoint object) {
	            measuringPointMetricsCombinations.addMetricDescriptionToMap(measuringPointMetricsCombinations.getAssemblyOperationMeasuringPointMetrics(), metricDescription, suggestedMetric);
	            return object;
	        }
	        @Override
	        public MeasuringPoint caseAssemblyPassiveResourceMeasuringPoint(AssemblyPassiveResourceMeasuringPoint object) {
	            measuringPointMetricsCombinations.addMetricDescriptionToMap(measuringPointMetricsCombinations.getAssemblyPassiveResourceMeasuringPointMetrics(), metricDescription, suggestedMetric);
	            return object;
	        }
	        @Override
	        public MeasuringPoint caseEntryLevelSystemCallMeasuringPoint(EntryLevelSystemCallMeasuringPoint object) {
	            measuringPointMetricsCombinations.addMetricDescriptionToMap(measuringPointMetricsCombinations.getEntryLevelSystemCallMeasuringPointMetrics(), metricDescription, suggestedMetric);
	            return object;
	        }
	        @Override
	        public MeasuringPoint caseExternalCallActionMeasuringPoint(ExternalCallActionMeasuringPoint object) {
	            measuringPointMetricsCombinations.addMetricDescriptionToMap(measuringPointMetricsCombinations.getExternalCallActionMeasuringPointMetrics(), metricDescription, suggestedMetric);
	            return object;
	        }
	        @Override
	        public MeasuringPoint caseLinkingResourceMeasuringPoint(LinkingResourceMeasuringPoint object) {
	            measuringPointMetricsCombinations.addMetricDescriptionToMap(measuringPointMetricsCombinations.getLinkingResourceMeasuringPointMetrics(), metricDescription, suggestedMetric);
	            return object;
	        }
	        @Override
	        public MeasuringPoint caseResourceContainerMeasuringPoint(ResourceContainerMeasuringPoint object) {
	            measuringPointMetricsCombinations.addMetricDescriptionToMap(measuringPointMetricsCombinations.getResourceContainerMeasuringPointMetrics(), metricDescription, suggestedMetric);
	            return object;
	        }
	        @Override
	        public MeasuringPoint caseResourceEnvironmentMeasuringPoint(ResourceEnvironmentMeasuringPoint object) {
	            measuringPointMetricsCombinations.addMetricDescriptionToMap(measuringPointMetricsCombinations.getResourceEnvironmentMeasuringPointMetrics(), metricDescription, suggestedMetric);
	            return object;
	        }
	        @Override
	        public MeasuringPoint caseSubSystemOperationMeasuringPoint(SubSystemOperationMeasuringPoint object) {
	            measuringPointMetricsCombinations.addMetricDescriptionToMap(measuringPointMetricsCombinations.getSubSystemOperationMeasuringPointMetrics(), metricDescription, suggestedMetric);
	            return object;
	        }
	        @Override
	        public MeasuringPoint caseSystemOperationMeasuringPoint(SystemOperationMeasuringPoint object) {
	            measuringPointMetricsCombinations.addMetricDescriptionToMap(measuringPointMetricsCombinations.getSystemOperationMeasuringPointMetrics(), metricDescription, suggestedMetric);
	            return object;
	        }
	        @Override
	        public MeasuringPoint caseUsageScenarioMeasuringPoint(UsageScenarioMeasuringPoint object) {
	            measuringPointMetricsCombinations.addMetricDescriptionToMap(measuringPointMetricsCombinations.getUsageScenarioMeasuringPointMetrics(), metricDescription, suggestedMetric);
	            return object;
	        }
	    };
    }
}