package org.palladiosimulator.measurementsui.wizard.handlers.contentprovider;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;

/**
 * A content provider for the second step of the measuringpoint creation wizard pages
 * 
 * @author Domas Mikalkinas
 *
 */
public class AdditionalMeasuringpointContentProvider implements ITreeContentProvider {
    private MeasuringPointSelectionWizardModel measuringPointWizardModel;

    /**
     * constructor with arguments, which sets the needed wizard model
     * 
     * @param measuringPointWizardModel
     *            the needed wizard model
     */
    public AdditionalMeasuringpointContentProvider(MeasuringPointSelectionWizardModel measuringPointWizardModel) {
        this.measuringPointWizardModel = measuringPointWizardModel;
    }

    @Override
    public Object[] getElements(Object inputElement) {

        return measuringPointWizardModel.getAllAdditionalModels();
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        return new Object[0];
    }

    @Override
    public Object getParent(Object element) {
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        return false;
    }

}
