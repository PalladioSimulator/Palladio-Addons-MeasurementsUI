package org.palladiosimulator.measurementsui.wizardmodel.pages;


import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.dataprovider.SloProvider;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModelSlo;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.servicelevelobjective.ServiceLevelObjective;

/**
 * WizardModel for the selecting a measurement specification for a Service Level Objective
 * 
 * @author Manuel Marroquin
 * @author Jan Hofmann
 *
 */
public class SloMeasurementSpecSelectionWizardModel implements WizardModelSlo {

	private static final String SET_MSPEC_INFO_TEXT = "A Service Level Objective always needs a measurement specification.";
	private static final String SET_MSPEC_TITEL = "Bind a measurement specification";

	private DataApplication dataApp;
	private EList<Monitor> monitorList;
    private SloProvider sloProvider;
   
    /**
	 * Constructor
	 * @param slo the service level objective to edit
	 * @param isEditing indicates whether we are in edit mode or creation mode
	 */ 
    public SloMeasurementSpecSelectionWizardModel(SloProvider dataProvider, boolean isEditing) {
        this.dataApp = DataApplication.getInstance();
        this.monitorList = this.dataApp.getMonitorRepository().getMonitors();
        this.sloProvider = dataProvider;
    }
    
	/**
	 * This method returns true if the MeasurementSpec Selection Page contains
	 * all necessary information.
	 * 
	 * @return true if the user has chosen a Measurement Specification
	 */
	@Override
	public boolean canFinish() {
		return sloProvider.getMeasurementSpecification() != null;
	}
	
	/**
	 * This method retrieves the current Slo from the Slo-Provider
	 * 
	 * @return ServiceLevelObjective slo
	 */
	public ServiceLevelObjective getSlo() {
		return sloProvider.getServiceLevelObjective();
	}
	
	/**
	 * This method returns all available Monitors.
	 * 
	 * @return	returns all available Monitors
	 */
	public EList<Monitor> getMonitors(){
		return monitorList;
	}
	
	/**
	 * This method returns all Measurement Specifications of a Monitor.
	 * 
	 * @param monitor
	 * @return
	 */
	public EList<MeasurementSpecification> getMeasurementSpecs(Monitor monitor){
		return monitor.getMeasurementSpecifications();
	}
	
	/**
	 * This method sets the Measurement Specification temporary.
	 * Avoids changing Measurement Specification if user cancels editing.
	 * 
	 * @param measurementSpec
	 */
	public void setMeasurementSpecificationTmp(MeasurementSpecification measurementSpec) {
		this.sloProvider.setMeasurementSpecification(measurementSpec);
	}

	/**
	 * This method retrieves the Measurement Specification from the current Slo.
	 * 
	 * @return returns the Measurement Specification currently set to the Slo.
	 */
	public MeasurementSpecification getMeasurement() {
		return sloProvider.getMeasurementSpecification();
	}
	
	@Override
	public String getInfoText() {
		return SET_MSPEC_INFO_TEXT;
	}

	@Override
	public String getTitleText() {
		return SET_MSPEC_TITEL;
	}

}
