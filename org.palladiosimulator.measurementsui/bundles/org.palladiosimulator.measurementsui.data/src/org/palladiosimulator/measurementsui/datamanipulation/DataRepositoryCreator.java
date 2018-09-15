package org.palladiosimulator.measurementsui.datamanipulation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointFactory;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointPackage;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.monitorrepository.MonitorRepositoryPackage;


/**
 * This class can be used to create monitor- and measuringPointRepositories in a given project.
 * They will be created as default emf model instances.
 * 
 * @author Lasse
 *
 */
public class DataRepositoryCreator {

	private final String MeasuringPointRepositoryfileEnding = "/default.measuringpoint";
	private MeasuringpointPackage measuringpointPackage;
	private MeasuringpointFactory measuringpointFactory;
	
	private final String MonitorRepositoryfileEnding = "/default.monitorrepository";
	private MonitorRepositoryFactory monitorFactory;
	private MonitorRepositoryPackage monitorPackage;
	
	private static DataRepositoryCreator instance;

	
	public DataRepositoryCreator() {
		 this.measuringpointPackage = MeasuringpointPackage.eINSTANCE;
		 this.measuringpointFactory = this.measuringpointPackage.getMeasuringpointFactory();	
		 
		 this.monitorPackage = MonitorRepositoryPackage.eINSTANCE;
		 this.monitorFactory = this.monitorPackage.getMonitorRepositoryFactory();
	}

	public static DataRepositoryCreator getInstance () {
		if (DataRepositoryCreator.instance == null) {
			DataRepositoryCreator.instance = new DataRepositoryCreator();
		}
		return DataRepositoryCreator.instance;
	}
	
	/**
	 * Creates a measuringPointRepository named "default.measuringPoint" in a given project.
	 * 
	 * @param project to create the measuringPointRepository in
	 */
	public MeasuringPointRepository createMeasuringPointRepository(IProject project) {
		String measuringPointRepositoryfileName = project.getFullPath() + MeasuringPointRepositoryfileEnding;	
		final URI measuringPointRepositoryfileURI = URI.createPlatformResourceURI(measuringPointRepositoryfileName, true);

		final ResourceSet resourceSet = new ResourceSetImpl();
		final Resource resource = resourceSet.createResource(measuringPointRepositoryfileURI);

		EClass measuringPointRepository = this.measuringpointPackage.getMeasuringPointRepository();

		final EObject measuringPointRepositoryRootObject = measuringpointFactory.create(measuringPointRepository);

		if (measuringPointRepositoryRootObject != null) {
			resource.getContents().add(measuringPointRepositoryRootObject);
		}
		
		final Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		try {
			resource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (MeasuringPointRepository) measuringPointRepositoryRootObject;
	}
	
	/**
	 * Creates a monitorRepository named "default.monitorrepository" in the given project.
	 * 
	 * @param project to create monitorRepository in
	 */
	public MonitorRepository createMonitorRepository(IProject project) {
		String monitorRepositoryfileName = project.getFullPath() + MonitorRepositoryfileEnding;	
		final URI monitorRepositoryfileURI = URI.createPlatformResourceURI(monitorRepositoryfileName, true);

		final ResourceSet resourceSet = new ResourceSetImpl();
		final Resource resource = resourceSet.createResource(monitorRepositoryfileURI);

		EClass monitorRepository = this.monitorPackage.getMonitorRepository();
		final EObject monitorRepositoryRootObject = monitorFactory.create(monitorRepository);

		if (monitorRepositoryRootObject != null) {
			resource.getContents().add(monitorRepositoryRootObject);
		}
		
		final Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		try {
			resource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return (MonitorRepository) monitorRepositoryRootObject;
	}

}
