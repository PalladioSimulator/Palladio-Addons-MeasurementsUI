package org.palladiosimulator.measurementsui.fileaccess;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.business.api.session.Session;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointPackage;
import org.palladiosimulator.measurementsui.datamanipulation.RepositoryCreator;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.monitorrepository.MonitorRepositoryPackage;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.allocation.AllocationPackage;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.subsystem.SubsystemPackage;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.SystemPackage;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;
import org.palladiosimulator.servicelevelobjective.*;
/**
 * Class for accessing all Palladio models of a Modelling Project (.aird file available)
 * Offers access to each model.
 * 
 * @author Lasse Merz
 * 
 * @author Jan Hofmann: Added SLO-Support
 * @author Mario Maser: Added SLO-Support
 *
 */
public class ModelAccessor {

    private List<ResourceEnvironment> resourceEnvironmentList;
    private List<org.palladiosimulator.pcm.system.System> systemList;
    private List<Allocation> allocationList;
    private List<Repository> repositoryList;
    private List<UsageModel> usageModelList;
    private List<SubSystem> subsystemList;

    private List<MeasuringPointRepository> measuringPointRepositoryList;
    private List<MonitorRepository> monitorRepositoryList;
    private List<ServiceLevelObjectiveRepository> sloRepositoryList;
    private List<String> sloRepositoryNameList;

    /**
     * Constructor
     * initiliazes the list for all models
     */
    public ModelAccessor() {
        this.resourceEnvironmentList = new LinkedList<>();
        this.systemList = new LinkedList<>();
        this.allocationList = new LinkedList<>();
        this.repositoryList = new LinkedList<>();
        this.usageModelList = new LinkedList<>();
        this.subsystemList = new LinkedList<>();
        this.measuringPointRepositoryList = new LinkedList<>();
        this.monitorRepositoryList = new LinkedList<>();
        this.sloRepositoryList = new LinkedList<>();
        this.sloRepositoryNameList = new LinkedList<>();
    }
    
    /**
     * 
     * Method to negate a given predicate
     * 
     * NOTE: with java 11 this will be implemented in the java.util.function package
     * so Predicate.not(Predicate<T> t) will be usable from there
     * 
     * @param t predicate to negate
     * @param <T> 
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
     
        List<MeasuringPoint> measuringPointsFromAllRepositories = this.measuringPointRepositoryList.stream()
                .flatMap(e -> e.getMeasuringPoints().stream())
                .collect(Collectors.toList());
        
        List<MeasuringPoint> measuringPointsFromMonitors = this.monitorRepositoryList.stream()
                .flatMap(e -> e.getMonitors().stream().map(Monitor::getMeasuringPoint))
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

        List<Resource> allModelResourcesInSession = session.getSemanticResources().stream().collect(Collectors.toList());
        List<EObject> allModelObjectsInSession = allModelResourcesInSession.stream().flatMap(e -> e.getContents().stream())
                .collect(Collectors.toList());

        
        Collection<MeasuringPointRepository> measuringPointRepositories = EcoreUtil.getObjectsByType(allModelObjectsInSession,
                MeasuringpointPackage.eINSTANCE.getMeasuringPointRepository());
        this.measuringPointRepositoryList.addAll(measuringPointRepositories);

        Collection<MonitorRepository> monitorRepositories = EcoreUtil.getObjectsByType(allModelObjectsInSession,
                MonitorRepositoryPackage.eINSTANCE.getMonitorRepository());
        this.monitorRepositoryList.addAll(monitorRepositories);
        
        Collection<ServiceLevelObjectiveRepository> sloRepositories = EcoreUtil.getObjectsByType(allModelObjectsInSession,
                ServicelevelObjectivePackage.eINSTANCE.getServiceLevelObjectiveRepository());
        this.sloRepositoryList.addAll(sloRepositories);

        Collection<ResourceEnvironment> resourceEnvironments = EcoreUtil.getObjectsByType(allModelObjectsInSession,
                ResourceenvironmentPackage.eINSTANCE.getResourceEnvironment());
        this.resourceEnvironmentList.addAll(resourceEnvironments);

        Collection<System> systems = EcoreUtil.getObjectsByType(allModelObjectsInSession, SystemPackage.eINSTANCE.getSystem());
        this.systemList.addAll(systems);

        Collection<Allocation> allocations = EcoreUtil.getObjectsByType(allModelObjectsInSession,
                AllocationPackage.eINSTANCE.getAllocation());
        this.allocationList.addAll(allocations);

        Collection<Repository> repositories = EcoreUtil.getObjectsByType(allModelObjectsInSession,
                RepositoryPackage.eINSTANCE.getRepository());
        this.repositoryList.addAll(repositories);
        

        Collection<UsageModel> usageModels = EcoreUtil.getObjectsByType(allModelObjectsInSession,
                UsagemodelPackage.eINSTANCE.getUsageModel());
        this.usageModelList.addAll(usageModels);

        Collection<SubSystem> subSystems = EcoreUtil.getObjectsByType(allModelObjectsInSession,
                SubsystemPackage.eINSTANCE.getSubSystem());
        this.subsystemList.addAll(subSystems);
        
        
        // Dirty way of getting slo file names
        for (ServiceLevelObjectiveRepository repo : sloRepositoryList) {
            String uri = EcoreUtil.getURI(repo).toString();
            String name = new File(uri).getName();
            this.sloRepositoryNameList.add(name);
        }
    }
    
    /**
     * This method checks if the monitor- and measuringPointRepository
     * exists and if not it creates default versions of them.
     * 
     * @param project current Project
     */
    public void checkIfRepositoriesExist(IProject project) {
    	
    	if (!monitorRepositoryExists()) {
    		addMonitorRepository(RepositoryCreator.getInstance().createMonitorRepository(project));
    	}
    	
    	if (!measuringPointRepositoryExists()) {
    		addMeasuringPointRepository(RepositoryCreator.getInstance().createMeasuringPointRepository(project));
    	}	

    	if(!sloRepositoryExists()){
    		addSloRepository(RepositoryCreator.getInstance().createSLORepository(project));
    	}
    }

    /**
     * Clears and resets all lists of models
     */
    private void clearModelAccess() {
        this.allocationList.clear();
        this.repositoryList.clear();
        this.systemList.clear();
        this.resourceEnvironmentList.clear();
        this.usageModelList.clear();
        this.subsystemList.clear();
        this.monitorRepositoryList.clear();
        this.measuringPointRepositoryList.clear();
        this.sloRepositoryList.clear();
        this.sloRepositoryNameList.clear();
    }
    
    /**
     * Checks whether any Palladio core models exist
     * @return boolean whether models exist or not
     */
    public boolean modelsExist() {
        return !(this.allocationList.isEmpty() && this.systemList.isEmpty() && this.subsystemList.isEmpty()
                && this.repositoryList.isEmpty() && this.resourceEnvironmentList.isEmpty() 
                && this.usageModelList.isEmpty());
    }
    
    /**
     * Checks whether there exists a MonitorRepository
     * 
     * @return boolean whether a monitorRepository exists
     */
    public boolean monitorRepositoryExists() {
       return (this.monitorRepositoryList != null && !this.monitorRepositoryList.isEmpty());         
    }
    
    /**
     * Checks whether there exists a MonitorRepository
     * 
     * @return boolean whether a monitorRepository exists
     */
    public boolean measuringPointRepositoryExists() { 
        return (this.measuringPointRepositoryList != null && !this.measuringPointRepositoryList.isEmpty());
    }
    
    /**
     * Checks whether there exists a SLO Repository
     * @return
     */
    public boolean sloRepositoryExists() {
    	return (this.sloRepositoryList != null && !this.sloRepositoryList.isEmpty());
    }
  
    /**
     * Adds a MonitorRepository to the MonitorRepository list
     * @param monitorRepository to add
     */
    protected void addMonitorRepository(MonitorRepository monitorRepository) {
        this.monitorRepositoryList.add(monitorRepository);
    }

    /**
     * Adds a MeasuringPointRepository to the MeasuringPointRepository list
     * @param measuringPointRepository to add
     */
    protected void addMeasuringPointRepository(MeasuringPointRepository measuringPointRepository) {
        this.measuringPointRepositoryList.add(measuringPointRepository);
    }
    
    /**
     * Adds a ServiceLevelObjectiveRepository to the sloRepository list
     * @param sloRepository
     */
    protected void addSloRepository(ServiceLevelObjectiveRepository sloRepository) {
    	this.sloRepositoryList.add(sloRepository);
    	
    	// Extract and save name
        String uri = EcoreUtil.getURI(sloRepository).toString();
        String name = new File(uri).getName();
        this.sloRepositoryNameList.add(name);
    }
    
    /**
     * Returns the list of ResourceEnvironments
     * @return list of ResourceEnvironments
     */
    public List<ResourceEnvironment> getResourceEnvironmenList() {
        return resourceEnvironmentList;
    }

    /**
     * Returns the list of Systems
     * @return list of Systems
     */
    public List<org.palladiosimulator.pcm.system.System> getSystemList() {
        return systemList;
    }

    /**
     * Returns the list of Allocations
     * @return list of Allocations
     */
    public List<Allocation> getAllocationList() {
        return allocationList;
    }

    /**
     * Returns the list Repositories
     * @return list of Repositories
     */
    public List<Repository> getRepositoryList() {
        return repositoryList;
    }

    /**
     * Returns the list of UsageModels
     * @return list of UsageModels
     */
    public List<UsageModel> getUsageModelList() {
        return usageModelList;
    }
    
    /**
     * Returns the list of SubSystems
     * @return list of SubSystems
     */
    public List<SubSystem> getSubSystemList() {
        return subsystemList;
    }

    /**
     * Returns the list of MeasuringPointRepositorys
     * @return list of MeasuringPointRepositorys
     */
    public List<MeasuringPointRepository> getMeasuringPointRepositoryList() {
        return measuringPointRepositoryList;
    }

    /**
     * Returns the list of MonitorRepositorys
     * @return list of MonitorRepositorys
     */
    public List<MonitorRepository> getMonitorRepositoryList() {
        return monitorRepositoryList;
    }
    
    /**
     * Returns the list of SloRepositorys
     * @return list of SloRepositorys
     */
    public List<ServiceLevelObjectiveRepository> getSLORepositoryList(){
    	return sloRepositoryList;
    }
    
    /**
     * Returns the list of SloRepositoryNames
     * @return list of SloRepositoryNames
     */
    public List<String> getSLORepositoryNameList(){
    	return sloRepositoryNameList;
    }

}
