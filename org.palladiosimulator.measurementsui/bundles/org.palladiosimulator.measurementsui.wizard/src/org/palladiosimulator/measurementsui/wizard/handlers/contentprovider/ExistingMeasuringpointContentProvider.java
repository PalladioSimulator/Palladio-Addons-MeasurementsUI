package org.palladiosimulator.measurementsui.wizard.handlers.contentprovider;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;

/**
 * content provider for existing measuring points
 * 
 * @author Domas Mikalkinas
 *
 */
public class ExistingMeasuringpointContentProvider implements ITreeContentProvider {
    private MeasuringPointSelectionWizardModel wizardModel;

    /**
     * constructor for the content provider
     * 
     * @param wizardModel
     *            wizard model for the second wizard page
     */
    public ExistingMeasuringpointContentProvider(MeasuringPointSelectionWizardModel wizardModel) {
        this.wizardModel = wizardModel;
    }

    @Override
    public Object[] getElements(Object inputElement) {
        return wizardModel.getExistingMeasuringPoints();

    }

    @Override
    public Object[] getChildren(Object parentElement) {
        // not needed
        return new Object[0];
    }

    @Override
    public Object getParent(Object element) {
        // not needed
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        // not needed
        return false;
    }

}
