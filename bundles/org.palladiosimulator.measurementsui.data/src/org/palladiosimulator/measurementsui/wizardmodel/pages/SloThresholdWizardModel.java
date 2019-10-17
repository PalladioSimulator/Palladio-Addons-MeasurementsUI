package org.palladiosimulator.measurementsui.wizardmodel.pages;

import javax.measure.Measure;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;

import org.palladiosimulator.measurementsui.dataprovider.SloProvider;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModelSlo;
import org.palladiosimulator.metricspec.NumericalBaseMetricDescription;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.servicelevelobjective.HardThreshold;
import org.palladiosimulator.servicelevelobjective.SoftThreshold;
import org.palladiosimulator.servicelevelobjective.ServiceLevelObjective;
import org.palladiosimulator.servicelevelobjective.ServicelevelObjectiveFactory;
import org.palladiosimulator.servicelevelobjective.ServicelevelObjectivePackage;

/**
 * WizardModel for the creating and editing thresholds for service level objectives
 * 
 * @author Manuel Marroquin
 * @author Jan Hofmann
 *
 */
public class SloThresholdWizardModel implements WizardModelSlo {

	private boolean isFinishable = false;
	private ServicelevelObjectivePackage serviceLevelObjectivePackage;
	private ServicelevelObjectiveFactory servicelevelObjectiveFactory;
	
    private SloProvider sloProvider;
    
	private static final String PAGE_TITLE = "Create Thresholds";
	private static final String PAGE_DESCRIPTION = "On this page thresholds "
			+ "for Service Level Objectives can be created and edited. \n\n"
			+ "Some thresholds need a specific unit. Time driven measurement "
			+ "specifications for example can be set with seconds (s) or milliseconds (ms)";

    /**
	 * Constructor
	 * @param slo the service level objective to edit
	 * @param isEditing indicates whether we are in edit mode or creation mode
	 */ 
	public SloThresholdWizardModel(SloProvider dataProvider, boolean isEditing) {
		this.serviceLevelObjectivePackage = ServicelevelObjectivePackage.eINSTANCE;
		this.servicelevelObjectiveFactory = serviceLevelObjectivePackage.getServicelevelObjectiveFactory();
		this.sloProvider = dataProvider;
	}

	public enum fuzzyThresholdType {
		LINEAR, QUADRATIC, NEGATIVE_QUADRATIC
	}

	/**
	 * This method returns true if the Threshold Selection Page contains
	 * all necessary information.
	 * 
	 * @return true if user has chosen a valid threshold
	 */
	@Override
	public boolean canFinish() {
		return isFinishable;
	}

	/**
	 * Sets Wizard finishable. Used by the SloThresholdPage if threshold is valid or not
	 */
	public void setFinishable(boolean isFinishable) {
		this.isFinishable = isFinishable;
	}

	@Override
	public String getInfoText() {
		return PAGE_DESCRIPTION;
	}

	@Override
	public String getTitleText() {
		return PAGE_TITLE;
	}

	/**
	 * Returns the current Service Level Objective from the SloProvider
	 */
	public ServiceLevelObjective getSlo() {
		return sloProvider.getServiceLevelObjective();
	}

	/**
	 * Creates and sets a lower fuzzy Threshold to a Service Level Objective.
	 *
	 * @param hard		the value for the hard threshold
	 * @param hardUnit	the unit for the hard threshold
	 * @param fuzzy		the value for the fuzzy threshold
	 * @param fuzzyUnit the unit for the fuzzy threshold
	 * @param type		the type of the fuzzy threshold
	 */
	public void setLowerThreshold(Float hard, String hardUnit, Float fuzzy, String fuzzyUnit, fuzzyThresholdType type) {

		Unit<? extends Quantity> f = Unit.valueOf(fuzzyUnit);
		Unit<? extends Quantity> h = Unit.valueOf(hardUnit);
		try {
			f = Unit.valueOf(fuzzyUnit);
			h = Unit.valueOf(hardUnit);
		} catch (IllegalArgumentException e) {
			f = null;
			h = null;
		}
		Measure<Float, ?> fValue = Measure.valueOf(fuzzy, f);
		Measure<Float, ?> hValue = Measure.valueOf(hard, h);
		SoftThreshold lower;
		switch (type) {
		case LINEAR:
			lower = servicelevelObjectiveFactory.createLinearFuzzyThreshold();
			break;
		case QUADRATIC:
			lower = servicelevelObjectiveFactory.createQuadraticFuzzyThreshold();
			break;
		case NEGATIVE_QUADRATIC:
			lower = servicelevelObjectiveFactory.createNegativeQuadraticFuzzyThreshold();
			break;
		default:
			lower = servicelevelObjectiveFactory.createLinearFuzzyThreshold();
		}
		lower.setSoftLimit(fValue);
		lower.setThresholdLimit(hValue);
		sloProvider.getServiceLevelObjective().setLowerThreshold(lower);
	}

	/**
	 * Creates and sets a lower hard Threshold to a Service Level Objective.
	 * 
	 * @param hard		the value for the hard threshold
	 * @param hardUnit	the unit for the hard threshold
	 */
	public void setLowerThreshold(Float hard, String hardUnit) {

		Unit<? extends Quantity> h;
		try {
			h = Unit.valueOf(hardUnit);
		} catch (IllegalArgumentException e) {
			h = null;
		}
		Measure<Float, ?> hValue = Measure.valueOf(hard, h);

		HardThreshold lower = servicelevelObjectiveFactory.createHardThreshold();
		lower.setThresholdLimit(hValue);
		sloProvider.getServiceLevelObjective().setLowerThreshold(lower);	
	}

	/**
	 * Creates and sets an upper fuzzy Threshold to a Service Level Objective.
	 *
	 * @param hard		the value for the hard threshold
	 * @param hardUnit	the unit for the hard threshold
	 * @param fuzzy		the value for the fuzzy threshold
	 * @param fuzzyUnit the unit for the fuzzy threshold
	 * @param type		the type of the fuzzy threshold
	 */
	public void setUpperThreshold(Float hard, String hardUnit, Float fuzzy, String fuzzyUnit, fuzzyThresholdType type) {

		Unit<? extends Quantity> f = Unit.valueOf(fuzzyUnit);
		Unit<? extends Quantity> h = Unit.valueOf(hardUnit);
		try {
			f = Unit.valueOf(fuzzyUnit);
			h = Unit.valueOf(hardUnit);
		} catch (IllegalArgumentException e) {
			f = null;
			h = null;
		}
		Measure<Float, ?> fValue = Measure.valueOf(fuzzy, f);
		Measure<Float, ?> hValue = Measure.valueOf(hard, h);

		SoftThreshold upper;
		switch (type) {
		case LINEAR:
			upper = servicelevelObjectiveFactory.createLinearFuzzyThreshold();
			break;
		case QUADRATIC:
			upper = servicelevelObjectiveFactory.createQuadraticFuzzyThreshold();
			break;
		case NEGATIVE_QUADRATIC:
			upper = servicelevelObjectiveFactory.createNegativeQuadraticFuzzyThreshold();
			break;
		default:
			upper = servicelevelObjectiveFactory.createLinearFuzzyThreshold();
		}

		upper.setSoftLimit(fValue);
		upper.setThresholdLimit(hValue);
		sloProvider.getServiceLevelObjective().setUpperThreshold(upper);
	}

	/**
	 * Creates and sets an upper hard Threshold to a Service Level Objective.
	 * 
	 * @param hard		the value for the hard threshold
	 * @param hardUnit	the unit for the hard threshold
	 */
	public void setUpperThreshold(Float hard, String hardUnit) {
		Unit<? extends Quantity> h;
		try {
			h = Unit.valueOf(hardUnit);
		} catch (IllegalArgumentException e) {
			h = null;
		}
		Measure<Float, ?> hValue = Measure.valueOf(hard, h);

		HardThreshold upper = servicelevelObjectiveFactory.createHardThreshold();
		
		upper.setThresholdLimit(hValue);
		sloProvider.getServiceLevelObjective().setUpperThreshold(upper);
	}

	/**
	 * This method returns the default unit for the previous selected measurement specification.
	 * 
	 * @return 	returns the default unit
	 */
	public Unit<?> getDefaultUnit() {
		try {
			
			NumericalBaseMetricDescription specification = (NumericalBaseMetricDescription) 
					sloProvider.getMeasurementSpecification().getMetricDescription();
			return specification.getDefaultUnit();
		} catch (ClassCastException e) {
			
		}
		return null;
	}

	public String getSpecificationType() {
		MeasurementSpecification msp = sloProvider.getMeasurementSpecification();
		return msp.getProcessingType().toString();
	}

	/**
	 * Deletes the lower threshold in the current Service Level Objective
	 */
	public void deleteLowerThreshold() {
		sloProvider.getServiceLevelObjective().setLowerThreshold(null);
	}

	/**
	 * Deletes the upper threshold in the current Service Level Objective
	 */
	public void deleteUpperThreshold() {
		sloProvider.getServiceLevelObjective().setUpperThreshold(null);
	}

	/**
	 * This method checks if Service Level Objective is valid
	 * 
	 * @return		returns true if Service Level Objective is null
	 */
	public boolean sloIsNull() {
		if (sloProvider.getServiceLevelObjective().equals(null)) {
			return true;
		}
		return false;
	}

}
