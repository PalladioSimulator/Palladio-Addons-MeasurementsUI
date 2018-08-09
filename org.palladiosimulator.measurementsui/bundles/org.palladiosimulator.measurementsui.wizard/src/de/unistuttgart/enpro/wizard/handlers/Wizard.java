package de.unistuttgart.enpro.wizard.handlers;

/**
 * @author Birasanth Pushpanathan
 *
 */
public class Wizard extends org.eclipse.jface.wizard.Wizard {

    private AddMonitor page1 = new AddMonitor();
    private SelectMeasuringPoint page2 = new SelectMeasuringPoint();
    private SelectMeasurements page3 = new SelectMeasurements();
    private MeasurementSpecification page4 = new MeasurementSpecification();

    public Wizard() {
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
