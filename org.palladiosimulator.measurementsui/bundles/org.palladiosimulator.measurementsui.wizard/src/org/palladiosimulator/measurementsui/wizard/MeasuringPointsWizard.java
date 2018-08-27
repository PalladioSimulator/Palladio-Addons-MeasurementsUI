package org.palladiosimulator.measurementsui.wizard;

import org.palladiosimulator.measurementsui.wizard.handlers.AddMonitor;
import org.palladiosimulator.measurementsui.wizard.handlers.ChooseMeasuringpointWizardPage;
import org.palladiosimulator.measurementsui.wizard.handlers.MeasurementSpecification;
import org.palladiosimulator.measurementsui.wizard.handlers.SelectMeasurements;

import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;

/**
 * This class handles the wizard and its wizard pages for creating a new measuring point/monitor.
 * 
 * @author Birasanth Pushpanathan
 *
 */
public class MeasuringPointsWizard extends org.eclipse.jface.wizard.Wizard {
    
    /**
     * This is the monitor object which is created during the wizard
     */
    private Monitor newMonitor = MonitorRepositoryFactory.eINSTANCE.createMonitor();
    
    /**
     * Represents the first wizard page, where the new monitor defined (name + activated/deactivated)
     */
    private AddMonitor page1 = new AddMonitor(this.newMonitor);
    
    /**
     * Represents the 2nd wizard page, where the user selects either an existing measuring point for the new monitor
     * or creates a new one.
     */
	private ChooseMeasuringpointWizardPage page2 = new ChooseMeasuringpointWizardPage();
	
	/**
	 * Represents the 3rd wizard page, where the user selects measurements which are then assigned to the monitor
	 */
	private SelectMeasurements page3 = new SelectMeasurements();
	
	/**
	 * Represents the 4th wizard page, where the user can set properties for the selected measurements.
	 */
	private MeasurementSpecification page4 = new MeasurementSpecification();

	/**
	 * The constructor
	 */
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
