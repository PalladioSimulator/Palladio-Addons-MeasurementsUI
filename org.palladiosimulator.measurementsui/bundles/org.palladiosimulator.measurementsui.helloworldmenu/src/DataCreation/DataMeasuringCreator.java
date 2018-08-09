package DataCreation;

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

public class DataMeasuringCreator {
	
	private final String MeasuringPointRepositoryfileEnding = "/default.measuringpoint";
	private MeasuringpointPackage measuringpointPackage;
	private MeasuringpointFactory measuringpointFactory;
	
	
	public DataMeasuringCreator() {
		 this.measuringpointPackage = MeasuringpointPackage.eINSTANCE;
		 this.measuringpointFactory = this.measuringpointPackage.getMeasuringpointFactory();	
	}
	
	
	public void createMeasuringPointRepository(String projectPath) {

		String measuringPointRepositoryfileName ="";
		
		if(!projectPath.startsWith("/")) {
			measuringPointRepositoryfileName = "/";
		}
		measuringPointRepositoryfileName = measuringPointRepositoryfileName + projectPath + MeasuringPointRepositoryfileEnding;	
		final URI measuringPointRepositoryfileURI = createURI(measuringPointRepositoryfileName);

		// Create a resource for this file.
		//
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Resource resource = createResource(resourceSet, measuringPointRepositoryfileURI);

		// Add the initial model object to the contents.
		EClass measuringPointRepository = this.measuringpointPackage.getMeasuringPointRepository();

		final EObject measuringPointRepositoryRootObject = createEObject(measuringpointFactory, measuringPointRepository);

		if (measuringPointRepositoryRootObject != null) {
			resource.getContents().add(measuringPointRepositoryRootObject);
		}
		
		final Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		try {
			resource.save(options);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MeasuringPoint createMeasuringPoint() {
		
		EClass measuringPoint = this.measuringpointPackage.getMeasuringPoint();
		final MeasuringPoint measuringPointObject = (MeasuringPoint) createEObject(measuringpointFactory, measuringPoint);
			
		
		
		return measuringPointObject;
		
	}
	
	public ResourceURIMeasuringPoint createResourceURIMeasuringPoint(String resourceURI) {
		
		EClass resourceURIMeasuringPoint = this.measuringpointPackage.getResourceURIMeasuringPoint();
		final ResourceURIMeasuringPoint resourceURIMeasuringPointObject = (ResourceURIMeasuringPoint) createEObject(measuringpointFactory, resourceURIMeasuringPoint);
			
		
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
