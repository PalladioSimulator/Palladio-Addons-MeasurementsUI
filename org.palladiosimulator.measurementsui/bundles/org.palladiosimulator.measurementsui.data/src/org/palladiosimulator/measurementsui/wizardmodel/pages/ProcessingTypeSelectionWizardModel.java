package org.palladiosimulator.measurementsui.wizardmodel.pages;

import java.util.List;

import org.palladiosimulator.measurementsui.dataprovider.ProcessingTypeProvider;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.ProcessingType;

/**
 * Provides all necessary methods for the fourth wizard page.
 * 
 * @author Florian Nieuwenhuizen
 *
 */
public class ProcessingTypeSelectionWizardModel implements WizardModel {

    private static final String INFORMATION_MESSAGE = "Please select a Processing Type for each Metric Description."
    		+ "\nA Processing Type defines how the measurements are computed during a simulation. "
    		+ "Choose a different type than FeedThrough, if the measurements should "
    		+ "be aggregated for the simulation results.";
    private static final String PROCESSING_TYPE_TITLE = "Select Processing Types";

    private Monitor usedMetricsMonitor;
    private boolean isEditing;
    private ProcessingTypeProvider provider;

    /**
     * 
     * @param monitor
     *            the monitor containing the selected MeasurementSpecifications from Page 3
     * @param isEditing
     *            Boolean containing information if we are in edit mode or not
     */
    public ProcessingTypeSelectionWizardModel(Monitor monitor, boolean isEditing) {
        this.usedMetricsMonitor = monitor;
        this.isEditing = isEditing;
        this.provider = new ProcessingTypeProvider();
    }

    /**
     * Returns the monitor that is sued for the metrics
     * @return monitor
     */
    public Monitor getUsedMetricsMonitor() {
        return usedMetricsMonitor;
    }

    @Override
    public boolean canFinish() {
        return true;
    }

    @Override
    public String getInfoText() {
        return INFORMATION_MESSAGE;
    }

    @Override
    public String getTitleText() {
        return PROCESSING_TYPE_TITLE;
    }

    /**
     * Provides all possible Processing Types in a String Array, used to fill the dropdown in the
     * 4th page.
     * 
     * @return A String Array containing all possible processsing types
     */
    public String[] providePossibleProcessingTypes() {
        return provider.provideBasicProcessingTypes();
    }

    /**
     * assigns the Processing Type depending on which was selected in the dropdown.
     * 
     * @param aMeasurementSpecification
     * @param selectedProcessingType
     */
    public void assignProcessingType(MeasurementSpecification aMeasurementSpecification,
            String selectedProcessingType) {
        provider.assignProcessingTypeToMeasurementSpecification(aMeasurementSpecification, selectedProcessingType,
                isEditing);
    }

    /**
     * Returns a list of String for the modification fields of the corresponding Processing Type.
     * Used for the 3rd and 4th columns in the 4th page.
     * 
     * @param processingTypeString
     * @return
     */
    public List<String> fieldsForThisProcessingType(String processingTypeString) {
        return provider.provideProcessingTypeProperties(processingTypeString);
    }

    /**
     * Sets the value for a single ProcessingTypeAttribute.
     * 
     * @param aMeasurementSpecification
     * @param aProcessingType
     * @param value
     */
    public void editAProcessingTypeAttribute(MeasurementSpecification aMeasurementSpecification,
            String aProcessingTypeAttribute, double value) {
        provider.setAProcessingTypeAttribute(aMeasurementSpecification, aProcessingTypeAttribute, value, isEditing);
    }

    /**
     * The getter for a PT Attribute from a selected Measurement Specification.
     * 
     * @param aMeasurementSpecification
     * @param aProcessingTypeAttribute
     * @return
     */
    public Number getAProccesingTypeAttribute(MeasurementSpecification aMeasurementSpecification,
            String aProcessingTypeAttribute) {
        return provider.getAProcessingType(aMeasurementSpecification, aProcessingTypeAttribute);
    }

    public String getStringOfProcessingType(ProcessingType processingType) {
        return provider.getProcessingTypeString(processingType);
    }
}
