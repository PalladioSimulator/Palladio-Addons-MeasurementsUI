package org.palladiosimulator.measurementsui.wizard;

import org.palladiosimulator.measurementsui.wizard.handlers.AddMonitor;
import org.palladiosimulator.measurementsui.wizard.handlers.ChooseMeasuringpointWizardPage;
import org.palladiosimulator.measurementsui.wizard.handlers.MeasurementSpecification;
import org.palladiosimulator.measurementsui.wizard.handlers.SelectMeasurements;

/**
 * @author Birasanth Pushpanathan
 *
 */
public class MeasuringPointsWizard extends org.eclipse.jface.wizard.Wizard {

	private AddMonitor page1 = new AddMonitor();
	private ChooseMeasuringpointWizardPage page2 = new ChooseMeasuringpointWizardPage();
	private SelectMeasurements page3 = new SelectMeasurements();
	private MeasurementSpecification page4 = new MeasurementSpecification();

	public MeasuringPointsWizard() {
		setWindowTitle("Enpro Wizard");

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
}
