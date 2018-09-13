package org.palladiosimulator.measurementsui.wizardpages;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.wizardmain.handlers.AdditionalMeasuringpointContentProvider;
import org.palladiosimulator.measurementsui.wizardmain.handlers.AdditionalMeasuringpointLabelProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.pcm.repository.PassiveResource;

public class AdditionalModelsToMeasuringpointWizardPage extends WizardPage {
    TreeViewer createTreeViewer;
    ITreeContentProvider createContentProvider;
    Composite container;
    AdditionalMeasuringpointContentProvider mp;
    boolean selected = false;
    MeasuringPointSelectionWizardModel selectionWizardModel = MeasuringPointSelectionWizardModel.getInstance();

    public AdditionalModelsToMeasuringpointWizardPage() {
        super("page2extra");
        setTitle("Select an operation role");
        setDescription("description");
    }

    @Override
    public void createControl(Composite parent) {
        container = new Composite(parent, SWT.NONE);
        FillLayout layout = new FillLayout();

        layout.marginHeight = 0;
        layout.marginWidth = 0;
        container.setLayout(layout);
        setControl(container);
        mp = new AdditionalMeasuringpointContentProvider();
        createContentProvider = mp;
        createTreeViewer = new TreeViewer(container);

    }

    public void loadData() {
        createTreeViewer.setContentProvider(mp);
        createTreeViewer.setInput(selectionWizardModel.getAllAdditionalModels());
        // }
        createTreeViewer.setLabelProvider(new AdditionalMeasuringpointLabelProvider());
    }

    /** @override */
    public org.eclipse.jface.wizard.IWizardPage getNextPage() {
        // kind of hack to detect without overriding WizardDialog#nextPressed()
        boolean isNextPressed = "nextPressed"
                .equalsIgnoreCase(Thread.currentThread().getStackTrace()[2].getMethodName());
        if (isNextPressed) {
            boolean validatedNextPress = this.nextPressed();
            if (!validatedNextPress) {
                return this;
            }
        }
        if (selected) {
            FinalModelsToMeasuringpointWizardPage page = (FinalModelsToMeasuringpointWizardPage) super.getWizard()
                    .getPage("page2final");

            return page;

        } else {
            return super.getWizard().getPage("wizardPage");
        }
        //
    }

    /**
     * @see WizardDialog#nextPressed()
     * @see WizardPage#getNextPage()
     */
    protected boolean nextPressed() {
        boolean validatedNextPressed = true;
        try {
            if (!(createTreeViewer.getStructuredSelection().getFirstElement() instanceof PassiveResource)) {

                selectionWizardModel
                        .setCurrentSecondStageModel(createTreeViewer.getStructuredSelection().getFirstElement());
                selected = true;
            } else {
                selectionWizardModel
                        .setCurrentSecondStageModel(createTreeViewer.getStructuredSelection().getFirstElement());
                selectionWizardModel.createMeasuringPoint(selectionWizardModel.getCurrentSelection());
                selected = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return validatedNextPressed;
    }
}
