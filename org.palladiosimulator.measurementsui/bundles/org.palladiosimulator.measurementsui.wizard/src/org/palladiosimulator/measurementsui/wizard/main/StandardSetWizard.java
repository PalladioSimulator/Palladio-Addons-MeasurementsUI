package org.palladiosimulator.measurementsui.wizard.main;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.equinox.log.SynchronousLogListener;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.dataprovider.StandardSetCreationProvider;
import org.palladiosimulator.measurementsui.wizard.pages.StandardSetCreationSelectionWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.StandardSetMeasuringPointSelectionWizardPage;
import org.palladiosimulator.monitorrepository.Monitor;

public class StandardSetWizard extends org.eclipse.jface.wizard.Wizard{

	private StandardSetCreationSelectionWizardPage page1;
	private StandardSetMeasuringPointSelectionWizardPage page2;
    private ResourceEditorImpl editor;
    private DataApplication dataApp;
	
	
	public StandardSetCreationSelectionWizardPage getPage1() {
		return page1;
	}

	public void setPage1(StandardSetCreationSelectionWizardPage page1) {
		this.page1 = page1;
	}

	public StandardSetMeasuringPointSelectionWizardPage getPage2() {
		return page2;
	}

	public void setPage2(StandardSetMeasuringPointSelectionWizardPage page2) {
		this.page2 = page2;
	}

	public StandardSetWizard() {
		editor= ResourceEditorImpl.getInstance();
		dataApp = DataApplication.getInstance();
		setWindowTitle("Create a standard set");	
		page1= new StandardSetCreationSelectionWizardPage("Select which standard set should be created.");
		page2= new StandardSetMeasuringPointSelectionWizardPage("Select all monitors which should be created.");
	}
	
	@Override
	public void addPages() {
		addPage(page1);
		addPage(page2);
	}
	
	@Override
	public boolean performFinish() {
		StandardSetCreationProvider provider = new StandardSetCreationProvider();
		if(page2.isAdd()) {
			Object[] tempmonitor= page2.getViewer().getCheckedElements();
		
			
			
			Monitor[] monitors= new Monitor[tempmonitor.length];
			System.arraycopy(tempmonitor, 0, monitors, 0, tempmonitor.length);
			provider.addMetricDescriptionsToAllMonitors(monitors);
			for(Monitor monitor: monitors) {
				
				 editor.addMonitorToRepository(dataApp.getModelAccessor().getMonitorRepository().get(0), monitor);
			        editor.addMeasuringPointToRepository(dataApp.getModelAccessor().getMeasuringPointRepository().get(0),
			                monitor.getMeasuringPoint());
			        editor.setMeasuringPointToMonitor(monitor, monitor.getMeasuringPoint());
			        try {
			            dataApp.getModelAccessor().getMeasuringPointRepository().get(0).eResource().save(null);
			            dataApp.getModelAccessor().getMonitorRepository().get(0).eResource().save(null);
			        } catch (IOException e) {
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			        }
			}
			
		}else {
			for(Object tempmeasuringpoint: page2.getViewer().getCheckedElements()) {
				MeasuringPoint measuringpoint= (MeasuringPoint)tempmeasuringpoint;
			        editor.addMeasuringPointToRepository(dataApp.getModelAccessor().getMeasuringPointRepository().get(0),
			                measuringpoint);
			        try {
			            dataApp.getModelAccessor().getMeasuringPointRepository().get(0).eResource().save(null);
			        } catch (IOException e) {
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			        }
			}
		}
		return true;
	}

	
	
}
