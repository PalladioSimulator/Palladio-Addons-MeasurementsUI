package org.palladiosimulator.measurementsui.datamanagement;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;

/**
 * Enum for the different pcm Models. Depending on the existing models the method createPcmInstance
 * creates the according pcm Model and adds it to the corresponding list.
 * 
 * @author Lasse
 *
 */
public enum PcmModelEnum {
    ResourceEnvironment {

        @Override
        void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
            modelAccsessor.addResourceEnvironment((ResourceEnvironment) pcmModel);

        }

    },
    System {

        @Override
        void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
            modelAccsessor.addSystem((org.palladiosimulator.pcm.system.System) pcmModel);

        }

    },
    Allocation {

        @Override
        void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
            modelAccsessor.addAllocation((org.palladiosimulator.pcm.allocation.Allocation) pcmModel);

        }

    },
    Repository {

        @Override
        void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
            if (pcmModel.eResource().getURI().toString().split(":")[0].contains("platform")) {
                modelAccsessor.addRepository((org.palladiosimulator.pcm.repository.Repository) pcmModel);
            }

        }

    },
    UsageModel {

        @Override
        void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
            modelAccsessor.addUsageModel((org.palladiosimulator.pcm.usagemodel.UsageModel) pcmModel);

        }

    },
    MeasuringPointRepository {

        @Override
        void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
            modelAccsessor.addMeasuringPointRepository(
                    (org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository) pcmModel);

        }

    },
    MonitorRepository {

        @Override
        void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel) {
            modelAccsessor.addMonitorRepository((org.palladiosimulator.monitorrepository.MonitorRepository) pcmModel);

        }

    };
    /**
     * This method adds the pcm Model to the according List of the ModelAccsessor
     * 
     * @param modelAccsessor
     *            the instance in which the models should be added
     * @param pcmModel
     *            the model which should be initialized
     */
    abstract void createPcmInstance(ModelAccessor modelAccsessor, EObject pcmModel);

    private static final Map<String, PcmModelEnum> nameToValueMap = new HashMap<String, PcmModelEnum>();

    static {
        for (PcmModelEnum value : EnumSet.allOf(PcmModelEnum.class)) {
            nameToValueMap.put(value.name(), value);
        }
    }

    /**
     * Checks in the name is one of the Enums
     * 
     * @param name
     *            of the Enum to check
     * @return PcmModelEnum instance according to the name
     */
    public static PcmModelEnum checkName(String name) {
        return nameToValueMap.get(name);
    }
}