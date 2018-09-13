package org.palladiosimulator.measurementsui.wizardmodel.pages;

import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModel;
import org.palladiosimulator.monitorrepository.Monitor;

public class MeasuringPointSelectionWizardModel implements WizardModel {
	
	private final String createMeasuringPointInfoText = "Select the element of your Models which should be monitored during a simulation run. ";
	private final String editMeasuringPointInfoText = "Select a differnt measuring Point.";
	
	private final String createMeasuringPointTitel = "Create Measuring Point";
	private final String editMeasuringPointTitel = "Edit Measuring Point";
	
	private Monitor monitor;
	
	 public MeasuringPointSelectionWizardModel(Monitor monitor) {
		this.monitor = monitor;
	}
	 
	 public void getAllPalladioModels() {
		 
	 }
	 
	 public void createMeasuringPoint() {
		 
//		 MeasuringPoint p = CREATEDMeausirngPoint;
//		 setMeasuringPointToMonitor(p);
//		 addMeasuringPointToRepository(p);
		 
	 }
	 
	 public void addMeasuringPointToRepository(MeasuringPoint measuringPoint) {
		 MeasuringPointRepository measuringPointRepository = DataApplication.getInstance().getModelAccessor().getMeasuringPointRepository().get(0);
		 ResourceEditorImpl.getInstance().addMeasuringPoint(measuringPointRepository, measuringPoint);
	 }
	 
	 public void setMeasuringPointToMonitor(MeasuringPoint measuringPoint) {
		 ResourceEditorImpl.getInstance().setMeasuringPoint(this.monitor, measuringPoint);
		 
	 }

	@Override
	public boolean canFinish() {
		if(this.monitor.getMonitorRepository() != null && this.monitor.getMeasuringPoint() != null) {
			return true;
		}
		return false;
	}

	@Override
	public String getInfoText() {
		if(this.monitor.getMeasuringPoint()!= null) {
			return editMeasuringPointInfoText;
		}
		return createMeasuringPointInfoText;
	}

	@Override
	public void nextStep() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTitleText() {
		if(this.monitor.getMeasuringPoint()!= null) {
			return createMeasuringPointTitel;
		}
		return editMeasuringPointTitel;
		
	}

}
