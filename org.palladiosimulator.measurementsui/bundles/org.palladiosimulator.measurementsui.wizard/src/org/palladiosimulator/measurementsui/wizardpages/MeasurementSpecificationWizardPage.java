package org.palladiosimulator.measurementsui.wizardpages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.wizard.viewer.MeasurementSpecificationViewer;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;

public class MeasurementSpecificationWizardPage extends WizardPage {
	
	private ProcessingTypeSelectionWizardModel processingTypeWizardModel;

    public MeasurementSpecificationWizardPage(ProcessingTypeSelectionWizardModel processingTypeWizardModel) {
        super("wizardPage");
        setTitle("Measurement Specification");
        setDescription("Specify properties of measurements");
        this.processingTypeWizardModel = processingTypeWizardModel;
    }

    @Override
    public void createControl(Composite parent) {
    	Composite container = new Composite(parent, SWT.FILL);
		FillLayout fillLayoutParentContainer = new FillLayout();
		container.setLayout(fillLayoutParentContainer);
		
    	setControl(container);
        
    	MeasurementSpecificationViewer measurementSpecificationViewer = new MeasurementSpecificationViewer(container,
    			this.processingTypeWizardModel);
    }
    
    /** @override */
    public boolean canFlipToNextPage() {
        return false;
    }

}
