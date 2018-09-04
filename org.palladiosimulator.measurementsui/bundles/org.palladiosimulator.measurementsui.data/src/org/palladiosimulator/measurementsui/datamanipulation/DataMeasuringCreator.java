package org.palladiosimulator.measurementsui.datamanipulation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointFactory;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointPackage;
import org.palladiosimulator.edp2.models.measuringpoint.ResourceURIMeasuringPoint;

/**
 * 
 * @author Lasse
 *
 */
public class DataMeasuringCreator {

    private final String measuringPointRepositoryfileEnding = "/default.measuringpoint";
    private MeasuringpointPackage measuringpointPackage;
    private MeasuringpointFactory measuringpointFactory;

    public DataMeasuringCreator() {
        this.measuringpointPackage = MeasuringpointPackage.eINSTANCE;
        this.measuringpointFactory = this.measuringpointPackage.getMeasuringpointFactory();
    }

    /**
     * If no measuring point repository exists, this can be used to create a new one
     * 
     * @param projectPath
     */
    public void createMeasuringPointRepository(String projectPath) {

        String measuringPointRepositoryfileName = "";

        if (!projectPath.startsWith("/")) {
            measuringPointRepositoryfileName = "/";
        }
        measuringPointRepositoryfileName = measuringPointRepositoryfileName + projectPath
                + measuringPointRepositoryfileEnding;
        final URI measuringPointRepositoryfileURI = createURI(measuringPointRepositoryfileName);

        // Create a resource for this file
        final ResourceSet resourceSet = new ResourceSetImpl();
        final Resource resource = createResource(resourceSet, measuringPointRepositoryfileURI);

        // Add the initial model object to the contents.
        EClass measuringPointRepository = this.measuringpointPackage.getMeasuringPointRepository();

        final EObject measuringPointRepositoryRootObject = createEObject(measuringpointFactory,
                measuringPointRepository);

        if (measuringPointRepositoryRootObject != null) {
            resource.getContents().add(measuringPointRepositoryRootObject);
        }

        final Map<Object, Object> options = new HashMap<>();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
            resource.save(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @return a new measuring point
     */
    public MeasuringPoint createMeasuringPoint() {

        EClass measuringPoint = this.measuringpointPackage.getMeasuringPoint();

        return (MeasuringPoint) createEObject(measuringpointFactory, measuringPoint);

    }

    /**
     * creates a new measuring point, depending on the given URI
     * @param resourceURI
     * @return ResourceURIMeasuringPoint
     */
    public ResourceURIMeasuringPoint createResourceURIMeasuringPoint(String resourceURI) {

        EClass resourceURIMeasuringPoint = this.measuringpointPackage.getResourceURIMeasuringPoint();
        final ResourceURIMeasuringPoint resourceURIMeasuringPointObject = (ResourceURIMeasuringPoint) createEObject(
                measuringpointFactory, resourceURIMeasuringPoint);

        EAttribute resourceURIAttribute = measuringpointPackage.getResourceURIMeasuringPoint_ResourceURI();
        resourceURIMeasuringPointObject.eSet(resourceURIAttribute, resourceURI);

        EAttribute resourceStringAttribute = measuringpointPackage.getMeasuringPoint_StringRepresentation();
        resourceURIMeasuringPointObject.eSet(resourceStringAttribute, "Blubber");

        return resourceURIMeasuringPointObject;

    }

    private URI createURI(String uri) {
        return URI.createPlatformResourceURI(uri, true);
    }

    private Resource createResource(ResourceSet resourceSet, URI uri) {
        return resourceSet.createResource(uri);
    }

    private EObject createEObject(EFactory factory, EClass classToCreate) {
        return factory.create(classToCreate);
    }

}
