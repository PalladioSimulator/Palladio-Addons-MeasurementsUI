package org.palladiosimulator.measurementsui.fileaccess;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.api.session.Session;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.measurementsui.datamanipulation.DataRepositoryCreator;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
/**
 * Class for accessing all Palladio models of a Modelling Project (.aird file available)
 * Offers access to each model.
 * 
 * @author Lasse
 *
 */
public class ModelAccessor {

    private List<ResourceEnvironment> resourceEnvironment;
    private List<org.palladiosimulator.pcm.system.System> system;
    private List<Allocation> allocation;
    private List<Repository> repository;
    private List<UsageModel> usageModel;
    private List<SubSystem> subsystem;

    private List<MeasuringPointRepository> measuringPointRepository;
    private List<MonitorRepository> monitorRepository;

    public ModelAccessor() {
        this.resourceEnvironment = new LinkedList<>();
        this.system = new LinkedList<>();
        this.allocation = new LinkedList<>();
        this.repository = new LinkedList<>();
        this.usageModel = new LinkedList<>();
        this.subsystem = new LinkedList<>();
        this.measuringPointRepository = new LinkedList<>();
        this.monitorRepository = new LinkedList<>();
    }
    
    /**
     * 
     * Method to negate a given predicate
     * 
     * NOTE: with java 11 this will be implemented in the java.util.function package
     * so Predicate.not(Predicate<T> t) will be usable from there
     * 
     * @param t Predicate to negate
     * @return negated Predicate
     */
    public static <T> Predicate<T> not(Predicate<T> t) {
        return t.negate();
    }


    /**
     * This method returns a list of all MeasuringPoints which are not assigned to any Monitor.
     * 
     * @return EList of unassigned MeasuringPoints
     */
    public EList<MeasuringPoint> getUnassignedMeasuringPoints() {
     
        List<MeasuringPoint> measuringPointsFromAllRepositories = this.measuringPointRepository.stream()
                .flatMap(e->e.getMeasuringPoints().stream())
                .collect(Collectors.toList());
        
        List<MeasuringPoint> measuringPointsFromMonitors = this.monitorRepository.stream()
                .flatMap(e->e.getMonitors().stream().map(Monitor::getMeasuringPoint))
                .collect(Collectors.toList());
        
        List<MeasuringPoint> intersectionOfMeasuringPoints = measuringPointsFromAllRepositories.stream()
                .filter(not(measuringPointsFromMonitors::contains))
                .collect(Collectors.toList());
        
        return new BasicEList<MeasuringPoint>(intersectionOfMeasuringPoints);
        

    }
    


    /**
     * Given a sirius session this Method initializes all five palladio component
     * models(+SubSystems), monitor- and measuringPoint-Repositories
     * that are found in this session.
     * 
     * @param session
     *            the session to which all models should be loaded
     */
    public void initializeModels(Session session) {

        clearModelAccess();

        for (Resource modelResource : session.getSemanticResources()) {

            for (EObject model : modelResource.getContents()) {

                if (PcmModelEnum.checkName(model.eClass().getName()) != null) {

                    checkPcmModels(model, PcmModelEnum.valueOf(model.eClass().getName()));
                }
            }
        }

    }
    
    /**
     * This method checks if the monitor- and measuringPointRepository
     * exists and if not it creates default versions of them.
     * 
     * @param project current Project
     */
    public void checkIfRepositoriesExist(IProject project){
    	
    	if(!monitorRepositoryExists()) {
    		addMonitorRepository(DataRepositoryCreator.getInstance().createMonitorRepository(project));
    	}
    	
    	if(!measuringPointRepositoryExists()) {
    		addMeasuringPointRepository(DataRepositoryCreator.getInstance().createMeasuringPointRepository(project));
    	}
    		
    }

    /**
     * This method initializes the pcm Model from the given EObject
     * 
     * @param pcmModel
     *            the EObject of the model
     * @param pcmEnum
     *            the Enum of the model eClass
     */
    private void checkPcmModels(EObject pcmModel, PcmModelEnum pcmEnum) {

        pcmEnum.createPcmInstance(this, pcmModel);
    }

    /**
     * Clears and resets all lists of models
     */
    private void clearModelAccess() {
        this.allocation.clear();
        this.repository.clear();
        this.system.clear();
        this.resourceEnvironment.clear();
        this.usageModel.clear();
        this.subsystem.clear();
        this.monitorRepository.clear();
        this.measuringPointRepository.clear();
    }
    
    /**
     * Checks whether there exists a MonitorRepository
     * 
     * @return boolean whether a monitorRepository exists
     */
    public boolean monitorRepositoryExists() {
        if (this.monitorRepository != null && !this.monitorRepository.isEmpty()) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks whether there exists a MonitorRepository
     * 
     * @return boolean whether a monitorRepository exists
     */
    public boolean measuringPointRepositoryExists() {
        if (this.measuringPointRepository != null && !this.measuringPointRepository.isEmpty()) {
            return true;
        }
        return false;
    }

    protected void addResourceEnvironment(ResourceEnvironment resourceEnvironment) {
        this.resourceEnvironment.add(resourceEnvironment);
    }

    protected void addSystem(org.palladiosimulator.pcm.system.System system) {
        this.system.add(system);
    }

    protected void addAllocation(Allocation allocation) {
        this.allocation.add(allocation);
    }

    protected void addRepository(Repository repository) {
        this.repository.add(repository);
    }

    protected void addUsageModel(UsageModel usageModel) {
        this.usageModel.add(usageModel);
    }

    protected void addSubSystem(SubSystem subsystem) {
        this.subsystem.add(subsystem);
    }
    
    protected void addMonitorRepository(MonitorRepository monitorRepository) {
        this.monitorRepository.add(monitorRepository);
    }

    protected void addMeasuringPointRepository(MeasuringPointRepository measuringPointRepository) {
        this.measuringPointRepository.add(measuringPointRepository);
    }
    
    public List<ResourceEnvironment> getResourceEnvironment() {
        return resourceEnvironment;
    }

    public List<org.palladiosimulator.pcm.system.System> getSystem() {
        return system;
    }

    public List<Allocation> getAllocation() {
        return allocation;
    }

    public List<Repository> getRepository() {
        return repository;
    }

    public List<UsageModel> getUsageModel() {
        return usageModel;
    }
    public List<SubSystem> getSubSystem() {
        return subsystem;
    }

    public List<MeasuringPointRepository> getMeasuringPointRepository() {
        return measuringPointRepository;
    }

    public List<MonitorRepository> getMonitorRepository() {
        return monitorRepository;
    }

}
