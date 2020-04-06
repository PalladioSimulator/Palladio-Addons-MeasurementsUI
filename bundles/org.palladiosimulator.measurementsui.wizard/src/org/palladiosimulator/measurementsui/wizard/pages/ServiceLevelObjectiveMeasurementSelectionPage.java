package org.palladiosimulator.measurementsui.wizard.pages;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.Bundle;
import org.palladiosimulator.measurementsui.wizardmodel.pages.SloMeasurementSpecSelectionWizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * Wizard page for editing the measurement specification of a Service Level Objective
 * 
 * @author Jan Hofmann
 * @author Manuel Marroquin
 *
 */
@SuppressWarnings("restriction")
public class ServiceLevelObjectiveMeasurementSelectionPage extends WizardPage {
	private SloMeasurementSpecSelectionWizardModel pageModel;
    private String[] titles = { "Monitor", "Name", "Metric Description", "Processing Type", "ID" };

	private static final String HELP_URL = "https://sdqweb.ipd.kit.edu/wiki/SimuLizar_Usability_Extension#User_Guide";
    
    private Composite composite;
    private Table table;
    
    /**
     * Constructor for the measurement selection Service Level Objective wizard page
     * @wbp.parser.constructor
     */
    public ServiceLevelObjectiveMeasurementSelectionPage (SloMeasurementSpecSelectionWizardModel model) {
        super("Second Page");
        this.pageModel = model;
        setTitle(model.getTitleText());
        setMessage(model.getInfoText(), INFORMATION);
        setImage();
        setPageComplete(false);
    }

    /**
     * This method initializes and manages SWT-UI elements for the wizard page.
     */
    @Override
    public void createControl(Composite parent) {
        composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        setControl(composite);
        composite.setLayout(new FillLayout(SWT.HORIZONTAL));
        
        table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
        table.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		TableItem[] items = table.getSelection();
        		MeasurementSpecification measurementSpec = (MeasurementSpecification) items[0].getData();
        		pageModel.setMeasurementSpecificationTmp(measurementSpec);
        		setPageComplete(true);
        	}
        });
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        createColumns();
        fillTable();
        
    	MeasurementSpecification msp = pageModel.getSlo().getMeasurementSpecification();
        // If SLO is edited. (A measurement specification is already assigned to SLO)
    	if (msp != null) {
        	// Search for measurement specification by its id
        	int index = 0;
        	String selectedID = msp.getId();  	
        	for (int i = 0; i < table.getItemCount(); i++) {
        		if (selectedID.equals(table.getItem(i).getText(4))) {
        			index = i;
        		}
        	}
        	table.setSelection(index);
        	setPageComplete(true);
        } else {
        	// No msp is selected (A new slo is being created)
        	setPageComplete(false);
        }
    }

    /**
     * Creates the Columns of the measurement specification table
     */
    private void createColumns() {
        for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
          TableColumn column = new TableColumn(table, SWT.NONE);
          column.setText(titles[loopIndex]);
          
        }
    }
    
    /**
     * Fills the measurement specification table with 
     * information regarding the Measurement Specifications
     */
    private void fillTable() {
    	for (Monitor monitor : pageModel.getMonitors()) {
    		for (MeasurementSpecification measurementSpec : pageModel.getMeasurementSpecs(monitor)) {
    			TableItem item = new TableItem(table, SWT.NULL);
    			item.setText(0, monitor.getEntityName());
    			item.setText(1, measurementSpec.getName());
    			item.setText(2, measurementSpec.getMetricDescription().toString());
    			item.setText(3, measurementSpec.getProcessingType().toString());
    			item.setText(4, measurementSpec.getId().toString());
    			item.setData(measurementSpec);
    		}
    	}
    	for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
    	      table.getColumn(loopIndex).pack();
    	    }
    }

    /**
     * Returns the scaling of the OS with a value of 1.0 if set to 100%, 1.5 if set to 150% etc.
     * @return the scaling of the OS with a value of 1.0 if set to 100%, 1.5 if set to 150% etc.
     */
    private float getDPIScale() {
        int currentDPI = Display.getDefault().getDPI().x;
        float defaultDPI = 96.0f;
        return currentDPI / defaultDPI;
    }
    
	 /**
     * Sets the image of the wizard which is shown in the upper right corner of the wizard pages.
     * 
     * Also scales the image according to the scaling of the OS.
     * The original size of the image is used for a scaling of 150%, that means when the scaling of the OS is below
     * 150%, the image is shrunk.
     */
    @SuppressWarnings("deprecation")
    private void setImage() {
        Bundle bundle = Platform.getBundle("org.palladiosimulator.measurementsui.wizard");
        URL fullPathString = BundleUtility.find(bundle, "icons/wizardImage.png");
        ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(fullPathString);
        ImageData imageData = imageDescriptor.getImageData();
        int scaledWidth = Math.round(imageData.width / (1.5f / getDPIScale()));
        int scaledHeight = Math.round(imageData.height / (1.5f / getDPIScale()));
        if( scaledWidth < 100) {
            scaledWidth = 100;
        }
        if( scaledHeight < 100) {
            scaledHeight = 100;
        }
        ImageData scaledImageData = imageData.scaledTo(scaledWidth, scaledHeight);
        ImageDescriptor resizedImageDescriptor = ImageDescriptor.createFromImageData(scaledImageData);
        setImageDescriptor(resizedImageDescriptor);
    }
    
    @Override
    public void performHelp() {
        Program.launch(HELP_URL);
    }
    
}
