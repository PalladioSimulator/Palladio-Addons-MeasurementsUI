package org.palladiosimulator.measurementsui.wizardmain.handlers;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.parsleyviewer.SelectMeasurementsViewer;

public class SelectMeasurements extends WizardPage {
    private Table table;
    private Table table_1;
    private DataApplication dataApplication;
    public SelectMeasurements() {
    	super("wizardPage");
    	initializeApplication();
        setTitle("HDD Monitor: Select Measurements");
        setDescription("Select desired Measurements to be used with the Monitor");
    }
    
    /**
	 * Initializes the connection to the data management and manipulation packages
	 */
	private void initializeApplication() {
		this.dataApplication = DataApplication.getInstance();
		dataApplication.loadData(0);
	}

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);

        
        FillLayout layout = new FillLayout();
        container.setLayout(layout);
        SelectMeasurementsViewer measurementsViewer = new SelectMeasurementsViewer(container, dataApplication);
        setControl(container);
    }
}