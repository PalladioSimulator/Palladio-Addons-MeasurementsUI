package org.palladiosimulator.measurementsui.wizard.handlers.contentprovider;

import java.util.LinkedList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;

/**
 * 
 * @author Domas Mikalkinas
 *
 */
public class MeasuringPointsContentProvider implements ITreeContentProvider {

    private MeasuringPointSelectionWizardModel measuringPointWizardModel;

    /**
     * constructor which sets the needed wizard model
     * 
     * @param measuringPointWizardModel
     *            the needed wizard model
     */
    public MeasuringPointsContentProvider(MeasuringPointSelectionWizardModel measuringPointWizardModel) {
        this.measuringPointWizardModel = measuringPointWizardModel;
    }

    @Override
    public Object[] getElements(Object inputElement) {
        return measuringPointWizardModel.getAllSecondPageObjects();
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof LinkedList) {
            return ((LinkedList<?>) parentElement).toArray();
        }
        return new Object[0];
    }

    @Override
    public Object getParent(Object element) {
        if (element instanceof LinkedList && !((LinkedList<?>) element).isEmpty()) {

            return element;

        }
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        return element instanceof LinkedList;
    }

}
