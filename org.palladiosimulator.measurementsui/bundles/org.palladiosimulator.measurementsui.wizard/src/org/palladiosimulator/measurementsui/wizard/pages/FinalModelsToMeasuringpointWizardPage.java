package org.palladiosimulator.measurementsui.wizard.pages;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.wizard.handlers.contentprovider.FinalMeasuringpointContentProvider;
import org.palladiosimulator.measurementsui.wizard.handlers.labelprovider.AdditionalMeasuringpointLabelProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;

/**
 * 
 * This is the wizard page for the third and final step of the creation of a wizard page. It needs
 * to be shown if and only if certain elements are selected in the first and second step of the
 * measuring point creation workflow. It creates all necessary ui elements.
 * 
 * @author Domas Mikalkinas
 *
 */
public class FinalModelsToMeasuringpointWizardPage extends WizardPage {
    private TreeViewer finalSelectionTreeViewer;
    private MeasuringPointSelectionWizardModel selectionWizardModel;

    /**
     * the constructor with the needed wizard model
     * 
     * @param selectionWizardModel
     *            the needed wizard model
     */
    public FinalModelsToMeasuringpointWizardPage(MeasuringPointSelectionWizardModel selectionWizardModel) {
        super("page2final");
        this.selectionWizardModel = selectionWizardModel;
        setTitle("Select an operation signature");
    }

    /**
     * creates the wizard page to choose the operation signature
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        FillLayout layout = new FillLayout();

        layout.marginHeight = 0;
        layout.marginWidth = 0;
        container.setLayout(layout);
        setControl(container);
        FinalMeasuringpointContentProvider finalMeasuringpointContentProvider = new FinalMeasuringpointContentProvider(selectionWizardModel);
        finalSelectionTreeViewer = new TreeViewer(container);
        finalSelectionTreeViewer.setContentProvider(finalMeasuringpointContentProvider);

    }

    /**
     * loads the data for the tree. It's a separate method because it needs to be delayed, since the
     * loaded data depends on a selection in a previous wizard page
     */
    public void loadData() {
        finalSelectionTreeViewer.setInput(selectionWizardModel.getSignatures().toArray());
        ISelection initialSelection = new StructuredSelection(selectionWizardModel.getSignatures().get(0));
        finalSelectionTreeViewer.setSelection(initialSelection);
        finalSelectionTreeViewer.setLabelProvider(new AdditionalMeasuringpointLabelProvider());
    }

    /**
     * overrides the getNextPage() method of the wizard page to allow a dynamic flow of the wizard
     * pages
     */
    @Override
    public org.eclipse.jface.wizard.IWizardPage getNextPage() {
        boolean isNextPressed = "nextPressed"
                .equalsIgnoreCase(Thread.currentThread().getStackTrace()[2].getMethodName());
        if (isNextPressed) {
            boolean validatedNextPress = this.nextPressed();
            if (!validatedNextPress) {
                return this;
            }
        }

        return super.getWizard().getPage("wizardPage");

        //
    }

    /**
     * @see WizardDialog#nextPressed()
     * @see WizardPage#getNextPage()
     * @return boolean validates whether the next button is pressed or not
     */
    protected boolean nextPressed() {
        boolean validatedNextPressed = true;
        try {
            selectionWizardModel
                    .setCurrentThirdStageModel(finalSelectionTreeViewer.getStructuredSelection().getFirstElement());
            selectionWizardModel.createMeasuringPoint(selectionWizardModel.getCurrentSelection());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return validatedNextPressed;
    }
}