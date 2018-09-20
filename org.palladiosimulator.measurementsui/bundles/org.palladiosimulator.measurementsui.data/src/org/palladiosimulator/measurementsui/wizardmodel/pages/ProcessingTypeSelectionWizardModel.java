package org.palladiosimulator.measurementsui.wizardmodel.pages;

import java.util.List;

import org.palladiosimulator.measurementsui.dataprovider.ProcessingTypeProvider;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;

public class ProcessingTypeSelectionWizardModel implements WizardModel {

    private static final String INFORMATION_MESSAGE = "Please select a Processing Type for each Metric Description";
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

    @Override
    public boolean canFinish() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public String getInfoText() {
        return INFORMATION_MESSAGE;
    }

    @Override
    public boolean nextStep() {
        return false;

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
     * Sets the values for the modification fields of each Processing Type.
     * 
     * @param aMeasurementSpecification
     * @param floatValues
     */
    public void editProcessingTypeAttributes(MeasurementSpecification aMeasurementSpecification,
            List<Double> floatValues) {
        provider.setProcessingTypeAttributes(aMeasurementSpecification, floatValues, isEditing);

    }

}
