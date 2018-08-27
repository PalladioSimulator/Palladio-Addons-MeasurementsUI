package org.palladiosimulator.measurementsui.wizard;

import org.palladiosimulator.measurementsui.wizard.handlers.AddMonitor;
import org.palladiosimulator.measurementsui.wizard.handlers.ChooseMeasuringpointWizardPage;
import org.palladiosimulator.measurementsui.wizard.handlers.MeasurementSpecification;
import org.palladiosimulator.measurementsui.wizard.handlers.SelectMeasurements;

import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;

/**
 * @author Birasanth Pushpanathan
 *
 */
public class MeasuringPointsWizard extends org.eclipse.jface.wizard.Wizard {
    
    private Monitor newMonitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
            
    private AddMonitor page1 = new AddMonitor(this.newMonitor);
	private ChooseMeasuringpointWizardPage page2 = new ChooseMeasuringpointWizardPage();
	private SelectMeasurements page3 = new SelectMeasurements();
	private MeasurementSpecification page4 = new MeasurementSpecification();

	public MeasuringPointsWizard() {
        setWindowTitle("Add new Measuring Point");

    }

	@Override
    public void addPages() {
        addPage(page1);
        addPage(page2);
        addPage(page3);
        addPage(page4);
    }

	@Override
    public boolean performFinish() {
        return false;
    }
	
	@Override
    public boolean canFinish() {
        if (getContainer().getCurrentPage() == page1 
                || getContainer().getCurrentPage() == page2) {
            return false;
        } else {
            return true;
        }
    }
}
