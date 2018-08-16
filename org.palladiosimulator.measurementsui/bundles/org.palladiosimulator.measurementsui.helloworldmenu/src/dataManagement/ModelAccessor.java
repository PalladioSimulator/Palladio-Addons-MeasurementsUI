package dataManagement;

import java.util.ArrayList;
import java.util.List;


import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.api.session.Session;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

public class ModelAccessor {
	
	private List<ResourceEnvironment> resourceEnvironment;
	private List<org.palladiosimulator.pcm.system.System> system;
	private List<Allocation> allocation;
	private List<Repository> repository;
	private List<UsageModel> usageModel;
	
	private List<MeasuringPointRepository> measuringPointRpository;
	private List<MonitorRepository> monitorRepository;
	
	
	public ModelAccessor() {
		this.resourceEnvironment = new ArrayList<ResourceEnvironment>();
		this.system = new ArrayList<org.palladiosimulator.pcm.system.System>();
		this.allocation = new ArrayList<Allocation>();
		this.repository = new ArrayList<Repository>();
		this.usageModel = new ArrayList<UsageModel>();
		this.measuringPointRpository = new ArrayList<MeasuringPointRepository>();
		this.monitorRepository = new ArrayList<MonitorRepository>();
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


	public List<MeasuringPointRepository> getMeasuringPointRpository() {
		return measuringPointRpository;
	}


	public List<MonitorRepository> getMonitorRepository() {
		return monitorRepository;
	}
	
	/**
	 * Checks whether there exists an MonitorRepository
	 * @return boolean whether a monitorRepository exists
	 */
	public boolean monitorRepositoryExists() {
		if(this.monitorRepository!= null && !this.monitorRepository.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method returns a list of all MeasuringPoints which are not assigned to any Monitor.
	 * @return List of unassigned MeasuringPoints
	 */
	public List<MeasuringPoint> getUnassignedMeasuringPoints() {
		
		List<MeasuringPoint> unassignedMeasuringPoints = new ArrayList<MeasuringPoint>();
		
		for (MeasuringPointRepository measuringPointRepository : this.measuringPointRpository) {
			for (MeasuringPoint measuringPoint : measuringPointRepository.getMeasuringPoints()) {
				if(!checkIfMeasuringPointIsAssignedToAnyMonitor(measuringPoint)) {
					unassignedMeasuringPoints.add(measuringPoint);
				}
			}
			
		}
		return unassignedMeasuringPoints;
		
	}
	
	/**
	 * This method checks whether a MeasuringPoint is assigned to any Monitor
	 * @param measuringpoint to check
	 * @return boolean whether the measuringPoint is assigned to any Monitor
	 */
	private boolean checkIfMeasuringPointIsAssignedToAnyMonitor(MeasuringPoint measuringpoint) {
		
		for (MonitorRepository monitorRepository: this.monitorRepository) {
			for (Monitor monitor: monitorRepository.getMonitors()) {
				if(monitor.getMeasuringPoint().equals(measuringpoint)) {
					return true;
				}
				
			}
			
		}
		return false;
	}


	/**
	 * Given a sirius session this Method initializes all found pcm Models in the session.
	 * 
	 * @param session the session to which all models should be loaded
	 */
	public void initializeModels(Session session) {
		clearModels();
		
		for (Resource resource : session.getSemanticResources()) {

			for (EObject pcmModel: resource.getContents()) {	

				if(PcmModelEnum.checkName(pcmModel.eClass().getName())!= null) {

					checkPcmModels(pcmModel, PcmModelEnum.valueOf(pcmModel.eClass().getName()));
				}
			}
		}
		
	}
	
	/**
	 * This method initializes the pcm Model from the given EObject
	 * 
	 * @param pcmModel the EObject of the model
	 * @param pcmEnum the Enum of the model eClass
	 */
	private void checkPcmModels(EObject pcmModel, PcmModelEnum pcmEnum) {

		pcmEnum.createPcmInstance(this, pcmModel);
	}
	
	/**
	 * Resets the models
	 */
	private void clearModels() {
		this.allocation.clear();
		this.repository.clear();
		this.resourceEnvironment.clear();
		this.system.clear();
		this.usageModel.clear();
		
		this.measuringPointRpository.clear();
		this.monitorRepository.clear();
		
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
	
	protected void addMonitorRepository(MonitorRepository monitorRepository){
		this.monitorRepository.add(monitorRepository);
	}
	
	protected void addMeasuringPointRepository(MeasuringPointRepository measuringPointRepository){
		this.measuringPointRpository.add(measuringPointRepository);
	}
	
	

}
