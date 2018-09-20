package org.palladiosimulator.measurementsui.wizardpages;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.wizard.viewer.MeasurementSpecificationViewer;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

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
    	TableViewer tableViewer = (TableViewer) measurementSpecificationViewer.getViewer();
		tableViewer.setLabelProvider(new ITableLabelProvider() {
		    
			public void removeListener(ILabelProviderListener listener) {
			    // not used
			}

			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				String result = "";
				MeasurementSpecification measurementSpecification = (MeasurementSpecification) element;
				if (columnIndex == 0) {
					result = measurementSpecification.getMetricDescription().getName();
				} else if (columnIndex == 1){
					
				} else if (columnIndex == 2){
					
				} else if (columnIndex == 3){
					
				}
				return result;
			}

			public void addListener(ILabelProviderListener listener) {
			    // not used
			}

			public void dispose() {
			    // not used
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
		});
    }
    
    /** @override */
    public boolean canFlipToNextPage() {
        return false;
    }

}
