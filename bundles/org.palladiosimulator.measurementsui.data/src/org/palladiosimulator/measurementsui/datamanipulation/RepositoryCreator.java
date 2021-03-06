package org.palladiosimulator.measurementsui.datamanipulation;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointFactory;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointPackage;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import org.palladiosimulator.monitorrepository.MonitorRepositoryPackage;
import org.palladiosimulator.servicelevelobjective.ServiceLevelObjectiveRepository;
import org.palladiosimulator.servicelevelobjective.ServicelevelObjectiveFactory;
import org.palladiosimulator.servicelevelobjective.ServicelevelObjectivePackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class can be used to create monitor- and measuringPointRepositories in a given project. They
 * will be created as default emf model instances.
 * 
 * @author Lasse Merz
 * @author Jan Hofmann
 *
 */
public final class RepositoryCreator {

    private static final String MEASURINGPOINT_REPOSITORY_FILE_ENDING = "/default.measuringpoint";
    private MeasuringpointPackage measuringpointPackage;
    private MeasuringpointFactory measuringpointFactory;

    private static final String MONITORREPOSITORY_FILE_ENDING = "/default.monitorrepository";
    private MonitorRepositoryFactory monitorFactory;
    private MonitorRepositoryPackage monitorPackage;
    
    private static final String SLO_REPOSITORY_FILE_ENDING = "/default.slo";
	protected ServicelevelObjectivePackage serviceLevelObjectivePackage;
    protected ServicelevelObjectiveFactory servicelevelObjectiveFactory;
	
    private static RepositoryCreator instance;
    private final Logger logger = LoggerFactory.getLogger(RepositoryCreator.class);

    /**
     * private Constructor for singleton pattern
     */
    private RepositoryCreator() {
        this.measuringpointPackage = MeasuringpointPackage.eINSTANCE;
        this.measuringpointFactory = this.measuringpointPackage.getMeasuringpointFactory();

        this.monitorPackage = MonitorRepositoryPackage.eINSTANCE;
        this.monitorFactory = this.monitorPackage.getMonitorRepositoryFactory();
        
        this.serviceLevelObjectivePackage = ServicelevelObjectivePackage.eINSTANCE;
        this.servicelevelObjectiveFactory = this.serviceLevelObjectivePackage.getServicelevelObjectiveFactory();
    }

    /**
     * Returns instance of DataRepositoryCreator
     * 
     * @return instance of DataRepositoryCreator
     */
    public static RepositoryCreator getInstance() {
        if (RepositoryCreator.instance == null) {
            RepositoryCreator.instance = new RepositoryCreator();
        }
        return RepositoryCreator.instance;
    }

    /**
     * Creates a measuringPointRepository named "default.measuringPoint" in a given project.
     * 
     * @param project
     *            to create the measuringPointRepository in
     * @return created MeasuringPointRepository
     */
    public MeasuringPointRepository createMeasuringPointRepository(IProject project) {
        String measuringPointRepositoryfileName = project.getFullPath() + MEASURINGPOINT_REPOSITORY_FILE_ENDING;
        final URI measuringPointRepositoryfileURI = URI.createPlatformResourceURI(measuringPointRepositoryfileName,
                true);

        List<AdapterFactory> factories = new LinkedList<>();
        factories.add(new ResourceItemProviderAdapterFactory());
        ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory();
        BasicCommandStack commandStack = new BasicCommandStack();
        AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack);

        final Resource resource = editingDomain.getResourceSet().createResource(measuringPointRepositoryfileURI);

        EClass measuringPointRepository = this.measuringpointPackage.getMeasuringPointRepository();

        final EObject measuringPointRepositoryRootObject = measuringpointFactory.create(measuringPointRepository);

        if (measuringPointRepositoryRootObject != null) {
            resource.getContents().add(measuringPointRepositoryRootObject);
        }

        final Map<Object, Object> options = new HashMap<>();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
            resource.save(options);
        } catch (IOException e) {
            logger.warn("IOException when attempting to create Measuring Point Repository. Stacktrace: {}", e.getMessage());
        }
        return (MeasuringPointRepository) measuringPointRepositoryRootObject;
    }

    /**
     * Creates a monitorRepository named "default.monitorrepository" in the given project.
     * 
     * @param project
     *            to create monitorRepository in
     * @return created MonitorRepository
     */
    public MonitorRepository createMonitorRepository(IProject project) {
        String monitorRepositoryfileName = project.getFullPath() + MONITORREPOSITORY_FILE_ENDING;
        final URI monitorRepositoryfileURI = URI.createPlatformResourceURI(monitorRepositoryfileName, true);

        List<AdapterFactory> factories = new LinkedList<>();
        factories.add(new ResourceItemProviderAdapterFactory());
        ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory();
        BasicCommandStack commandStack = new BasicCommandStack();
        AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack);

        final Resource resource = editingDomain.getResourceSet().createResource(monitorRepositoryfileURI);

        EClass monitorRepository = this.monitorPackage.getMonitorRepository();
        final EObject monitorRepositoryRootObject = monitorFactory.create(monitorRepository);

        if (monitorRepositoryRootObject != null) {
            resource.getContents().add(monitorRepositoryRootObject);
        }

        final Map<Object, Object> options = new HashMap<>();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
            resource.save(options);
        } catch (IOException e) {
            logger.warn("IOException when attempting to create Monitor Repository. Stacktrace: {}", e.getMessage());
        }

        return (MonitorRepository) monitorRepositoryRootObject;
    }
    
    /**
     * Creates a ServiceLevelObjective Repository named "default.slo" in the given project.
     * 
     * @param project to create ServiceLevelObjective Repository in
     * @return created ServiceLevelObjective Repository
     */
    public ServiceLevelObjectiveRepository createSLORepository(IProject project) {
        String servicelevelRepositoryfileName = project.getFullPath() + SLO_REPOSITORY_FILE_ENDING;
        final URI servicelevelRepositoryfileURI = URI.createPlatformResourceURI(servicelevelRepositoryfileName, true);

        List<AdapterFactory> factories = new LinkedList<>();
        factories.add(new ResourceItemProviderAdapterFactory());
        ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory();
        BasicCommandStack commandStack = new BasicCommandStack();
        AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack);

        final Resource resource = editingDomain.getResourceSet().createResource(servicelevelRepositoryfileURI);

        EClass servicelevelRepository = this.serviceLevelObjectivePackage.getServiceLevelObjectiveRepository();
        final EObject servicelevelRootObject = servicelevelObjectiveFactory.create(servicelevelRepository);

        if (servicelevelRootObject != null) {
            resource.getContents().add(servicelevelRootObject);
        }

        final Map<Object, Object> options = new HashMap<>();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
            resource.save(options);
        } catch (IOException e) {
            logger.warn("IOException when attempting to create ServiceLevelObjectiv Repository. Stacktrace: {}", e.getMessage());
        }
        
        return (ServiceLevelObjectiveRepository) servicelevelRootObject;
    }
    
}
