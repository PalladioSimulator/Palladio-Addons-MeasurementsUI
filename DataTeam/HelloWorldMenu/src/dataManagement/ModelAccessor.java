package dataManagement;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.api.session.Session;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
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
	

	/**
	 * Given a sirius session this Method initializes all found pcm Models in the session.
	 * 
	 * @param session the session to which all models should be loaded
	 */
	public void initializeModels(Session session) {

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
	 * Enum for the different pcm Models.
	 * Depending on the existing models the method createPcmInstance creates the according
	 * pcm Model and adds it to the corresponding list.
	 * @author Lasse
	 *
	 */
	public enum PcmModelEnum { 
		ResourceEnvironment{

			@Override
			void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
				modelAccsessor.addResourceEnvironment((ResourceEnvironment) pcmModel);

			}

		}, 
		System{

			@Override
			void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
				modelAccsessor.addSystem((org.palladiosimulator.pcm.system.System) pcmModel);

			}

		}, 
		Allocation{

			@Override
			void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
				modelAccsessor.addAllocation((org.palladiosimulator.pcm.allocation.Allocation) pcmModel);

			}

		}, 
		Repository{

			@Override
			void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
				if(pcmModel.eResource().getURI().toString().split(":")[0].contains("platform")) {
					modelAccsessor.addRepository((org.palladiosimulator.pcm.repository.Repository) pcmModel);
				}

			}

		}, 
		UsageModel{

			@Override
			void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
				modelAccsessor.addUsageModel((org.palladiosimulator.pcm.usagemodel.UsageModel) pcmModel);

			}

		}, 
		MeasuringPointRepository{

			@Override
			void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
				modelAccsessor.addMeasuringPointRepository((org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository) pcmModel);

			}

		}, 
		MonitorRepository{

			@Override
			void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
				modelAccsessor.addMonitorRepository((org.palladiosimulator.monitorrepository.MonitorRepository) pcmModel);

			}

		};

		/**
		 * This method adds the pcm Model to the according List of the ModelAccsessor
		 * 
		 * @param modelAccsessor the instance in which the models should be added
		 * @param pcmModel the model which should be initialized
		 */
		abstract void createPcmInstance (ModelAccessor modelAccsessor,EObject pcmModel);

		private static final Map<String, PcmModelEnum> nameToValueMap =
				new HashMap<String, PcmModelEnum>();

		static {
			for (PcmModelEnum value : EnumSet.allOf(PcmModelEnum.class)) {
				nameToValueMap.put(value.name(), value);
			}
		}

		/**
		 * Checks in the name is one of the Enums
		 * 
		 * @param name of the Enum to check
		 * @return PcmModelEnum instance according to the name
		 */
		public static PcmModelEnum checkName(String name) {
			return nameToValueMap.get(name);
		}
	}
	
	
	private void addResourceEnvironment(ResourceEnvironment resourceEnvironment) {
		this.resourceEnvironment.add(resourceEnvironment);
	}
	
	private void addSystem(org.palladiosimulator.pcm.system.System system) {
		this.system.add(system);
	}
	
	private void addAllocation(Allocation allocation) {
		this.allocation.add(allocation);
	}
	
	private void addRepository(Repository repository) {
		this.repository.add(repository);
	}
	
	private void addUsageModel(UsageModel usageModel) {
		this.usageModel.add(usageModel);
	}
	
	private void addMonitorRepository(MonitorRepository monitorRepository){
		this.monitorRepository.add(monitorRepository);
	}
	
	private void addMeasuringPointRepository(MeasuringPointRepository measuringPointRepository){
		this.measuringPointRpository.add(measuringPointRepository);
	}
	
	

}
