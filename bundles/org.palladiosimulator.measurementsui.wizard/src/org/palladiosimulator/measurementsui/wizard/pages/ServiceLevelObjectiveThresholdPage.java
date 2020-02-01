package org.palladiosimulator.measurementsui.wizard.pages;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.measure.Measure;
import javax.measure.converter.ConversionException;
import javax.measure.converter.UnitConverter;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtchart.Chart;
import org.eclipse.swtchart.ILineSeries;
import org.eclipse.swtchart.ILineSeries.PlotSymbolType;
import org.eclipse.swtchart.ISeries.SeriesType;
import org.eclipse.swtchart.ISeriesSet;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.Bundle;
import org.palladiosimulator.measurementsui.wizardmodel.pages.SloThresholdWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.SloThresholdWizardModel.fuzzyThresholdType;
import org.palladiosimulator.servicelevelobjective.HardThreshold;
import org.palladiosimulator.servicelevelobjective.LinearFuzzyThreshold;
import org.palladiosimulator.servicelevelobjective.NegativeQuadraticFuzzyThreshold;
import org.palladiosimulator.servicelevelobjective.QuadraticFuzzyThreshold;
import org.palladiosimulator.servicelevelobjective.SoftThreshold;
import org.palladiosimulator.servicelevelobjective.Threshold;

/**
 * Wizard page for creating and editing the thresholds of a Service Level Objective
 * 
 * @author Jan Hofmann
 * @author Manuel Marroquin
 *
 */
@SuppressWarnings("restriction")
public class ServiceLevelObjectiveThresholdPage extends WizardPage {
	private SloThresholdWizardModel pageModel;
	private Text upperThresholdLimit;
	private Text upperSoftLimit;
	private Text lowerThresholdLimit;
	private Text lowerSoftLimit;
	private Combo comboDropDownUpper;
	private Combo comboDropDownLower;

	private Chart chart;
	private Double[] input = { null, null, null, null };

	private static final String DROPDOWN_ENTRY_NOTHRESHOLD = "None";
	private static final String DROPDOWN_ENTRY_HARDTHRESHOLD = "Hard Threshold";
	private static final String DROPDOWN_ENTRY_FUZZYTHRESHOLD = "Linear Fuzzy Threshold";
	private static final String DROPDOWN_ENTRY_QUADRATICTHRESHOLD = "Quadratic Fuzzy Threshold";
	private static final String DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD = "Negative Quadratic Fuzzy Threshold";

	private static final String ERROR_UPPER_THRESHOLD_LOWER_THAN_LOWER_THRESHOLD = "The lower threshold limit can not be higher than the upper threshold limit!";
	private static final String ERROR_UPPER_THRESHOLD_EQUAL_LOWER_THRESHOLD = "The lower threshold limit and the upper threshold limit can not be equal!";
	private static final String ERROR_LOWER_SOFT_LOWER_THAN_LOWER_HARD_THRESHOLD = "Lower soft limit can not be lower than the lower hard limit!";
	private static final String ERROR_LOWER_SOFT_AND_HARD_EQUAL = "Lower soft limit and lower hard limit can not be equal!";
	private static final String ERROR_UPPER_SOFT_HIGHER_THAN_LOWER_HARD_THRESHOLD = "Upper soft limit can not be higher than the upper hard limit!";
	private static final String ERROR_UPPER_SOFT_AND_HARD_EQUAL = "Upper soft limit and upper hard limit can not be equal!";
	private static final String ERROR_LOWER_SOFT_HIGHER_UPPER_SOFT = "Lower soft limit can not be higher than the upper soft limit!";
	private static final String ERROR_UPPER_SOFT_HIGHER_UPPER_HARD = "Upper soft limit can not be higher than the upper hard limit!";
	private static final String ERROR_LOWER_SOFT_HIGHER_UPPER_HARD = "LOWER soft limit can not be higher than the upper hard limit!";
	
	private static final String HELP_URL = "https://sdqweb.ipd.kit.edu/wiki/SimuLizar_Usability_Extension#User_Guide";
	
	private Composite contentContainer;
	private Composite graphContainer;
	private Label validInputLabelUpper;
	private Label validInputLabelLower;

	private Label lblSoftLimitLower;
	private Label lblSoftLimitUpper;
	private Label lblThresholdLimitLower;
	private Label lblThresholdLimitUpper;
	
	private ControlDecoration upperHardLimitDecorator;
	private ControlDecoration lowerSoftDecorator;
	private ControlDecoration lowerHardLimitDecorator;
	private ControlDecoration upperSoftDecorator;
	
	// Point limit for the threshold visualization graph
	private int pointLimit = 100000;


	/**
	 * Constructor for the threshold selection page
	 * 
	 * @param model the model of the Service Level Objective Threshold page
	 */
	public ServiceLevelObjectiveThresholdPage(SloThresholdWizardModel model) {
		super("Page 3");
		this.pageModel = model;
		setPageComplete(false);
		setTitle(model.getTitleText());
		setMessage(model.getInfoText(), INFORMATION);
		setImage();
		
		pageModel.setFinishable(false);	
	}
	
	/**
	 * Is used to prevent bugs (force updates) in the graph if user 
	 * switches back and forth between the wizard pages.
	 * This method is called if threshold page gets visible in the wizard.
	 */
	@Override
	public void setVisible(boolean visible) {
		if(gatherInput()) {
			drawGraph(input, chart);
			pageModel.setFinishable(true);
			chart.setVisible(true);
		} else {
			pageModel.setFinishable(false);
			chart.setVisible(false);
		}
		// Update buttons
		getWizard().getContainer().updateButtons();
		super.setVisible(visible);
	}

    /**
     * This method initializes and manages SWT-UI elements for the threshold wizard page.
     */
	@Override
	public void createControl(Composite parent) {
		contentContainer = new Composite(parent, SWT.BORDER);
		contentContainer.setLayout(new GridLayout(1, false));
		Group upperGroup = new Group(contentContainer, SWT.NONE);
		GridData gd_upperGroup = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_upperGroup.widthHint = 564;
		upperGroup.setLayoutData(gd_upperGroup);
		upperGroup.setText("Upper Threshold:");
		upperGroup.setLayout(new GridLayout(5, false));

		comboDropDownUpper = new Combo(upperGroup, SWT.READ_ONLY);
		comboDropDownUpper.add(DROPDOWN_ENTRY_HARDTHRESHOLD);
		comboDropDownUpper.add(DROPDOWN_ENTRY_FUZZYTHRESHOLD);
		comboDropDownUpper.add(DROPDOWN_ENTRY_QUADRATICTHRESHOLD);
		comboDropDownUpper.add(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD);
		comboDropDownUpper.add(DROPDOWN_ENTRY_NOTHRESHOLD);
		comboDropDownUpper.select(4);

		lblThresholdLimitUpper = new Label(upperGroup, SWT.NONE);
		lblThresholdLimitUpper.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblThresholdLimitUpper.setText("Threshold Limit:  ");
		lblThresholdLimitUpper.setVisible(false);

		upperThresholdLimit = new Text(upperGroup, SWT.BORDER);
		upperThresholdLimit.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(gatherInput()) {
					drawGraph(input, chart);
					pageModel.setFinishable(true);
					chart.setVisible(true);
				} else {
					pageModel.setFinishable(false);
					chart.setVisible(false);
				}
				getWizard().getContainer().updateButtons();
			}
			
		});

		upperThresholdLimit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		upperThresholdLimit.setVisible(false);

		new Label(upperGroup, SWT.NONE);
		new Label(upperGroup, SWT.NONE);
		new Label(upperGroup, SWT.NONE);

		lblSoftLimitUpper = new Label(upperGroup, SWT.NONE);
		lblSoftLimitUpper.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSoftLimitUpper.setText("Soft Limit:  ");
		lblSoftLimitUpper.setVisible(false);

		upperSoftLimit = new Text(upperGroup, SWT.BORDER);
		upperSoftLimit.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(gatherInput()) {
					drawGraph(input, chart);
					pageModel.setFinishable(true);
					chart.setVisible(true);
				} else {
					pageModel.setFinishable(false);
					chart.setVisible(false);
				}
				getWizard().getContainer().updateButtons();
			}
			
		});
		
		upperSoftLimit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		upperSoftLimit.setVisible(false);

		new Label(upperGroup, SWT.NONE);
		new Label(upperGroup, SWT.NONE);

		validInputLabelUpper = new Label(contentContainer, SWT.WRAP);
		validInputLabelUpper.setForeground(new org.eclipse.swt.graphics.Color(upperGroup.getDisplay(), 255, 0, 0));
		// Somehow label wont be displayed correctly if no placeholder space is used
		validInputLabelUpper.setText("                                               "
				+ "                                                                  "
				+ "                                                                  "
				+ "                                                                  "
				+ "                                                                  ");

		comboDropDownUpper.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_NOTHRESHOLD)) {
					upperThresholdLimit.setEnabled(false);
					upperThresholdLimit.setVisible(false);
					upperSoftLimit.setEnabled(false);
					upperSoftLimit.setVisible(false);
					lblThresholdLimitUpper.setVisible(false);
					lblSoftLimitUpper.setVisible(false);
					upperSoftLimit.setText("");
					upperThresholdLimit.setText("");
				}

				else if (comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_HARDTHRESHOLD)) {
					upperThresholdLimit.setEnabled(true);
					upperThresholdLimit.setVisible(true);
					upperSoftLimit.setEnabled(false);
					upperSoftLimit.setVisible(false);
					lblThresholdLimitUpper.setVisible(true);
					lblSoftLimitUpper.setVisible(false);

				}

				else if (comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_FUZZYTHRESHOLD)
						|| comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_QUADRATICTHRESHOLD)
						|| comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD)) {
					upperThresholdLimit.setEnabled(true);
					upperThresholdLimit.setVisible(true);
					upperSoftLimit.setEnabled(true);
					upperSoftLimit.setVisible(true);
					lblThresholdLimitUpper.setVisible(true);
					lblSoftLimitUpper.setVisible(true);

				}

				if(gatherInput()) {
					drawGraph(input, chart);
					pageModel.setFinishable(true);
					chart.setVisible(true);
				} else {
					pageModel.setFinishable(false);
					chart.setVisible(false);
				}
				
				if (comboDropDownLower.getText().equals(DROPDOWN_ENTRY_NOTHRESHOLD)
						&& comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_NOTHRESHOLD)) {
					chart.setVisible(false);
				}	
				getWizard().getContainer().updateButtons();
			}
		});

		Group lowerGroup = new Group(contentContainer, SWT.NONE);
		GridData gd_lowerGroup = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lowerGroup.widthHint = 564;
		lowerGroup.setLayoutData(gd_lowerGroup);
		lowerGroup.setText("Lower Threshold:");
		lowerGroup.setLayout(new GridLayout(5, false));

		comboDropDownLower = new Combo(lowerGroup, SWT.READ_ONLY);
		comboDropDownLower.add(DROPDOWN_ENTRY_HARDTHRESHOLD);
		comboDropDownLower.add(DROPDOWN_ENTRY_FUZZYTHRESHOLD);
		comboDropDownLower.add(DROPDOWN_ENTRY_QUADRATICTHRESHOLD);
		comboDropDownLower.add(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD);
		comboDropDownLower.add(DROPDOWN_ENTRY_NOTHRESHOLD);
		comboDropDownLower.select(4);

		lblThresholdLimitLower = new Label(lowerGroup, SWT.NONE);
		lblThresholdLimitLower.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblThresholdLimitLower.setText("Threshold Limit:  ");
		lblThresholdLimitLower.setVisible(false);

		lowerThresholdLimit = new Text(lowerGroup, SWT.BORDER);
		lowerThresholdLimit.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(gatherInput()) {
					drawGraph(input, chart);
					pageModel.setFinishable(true);
					chart.setVisible(true);
				} else {
					pageModel.setFinishable(false);
					chart.setVisible(false);
				}
				getWizard().getContainer().updateButtons();
			}

		});

		lowerThresholdLimit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lowerThresholdLimit.setVisible(false);

		new Label(lowerGroup, SWT.NONE);
		new Label(lowerGroup, SWT.NONE);
		new Label(lowerGroup, SWT.NONE);

		lblSoftLimitLower = new Label(lowerGroup, SWT.NONE);
		lblSoftLimitLower.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSoftLimitLower.setText("Soft Limit:  ");
		lblSoftLimitLower.setVisible(false);

		lowerSoftLimit = new Text(lowerGroup, SWT.BORDER);
		lowerSoftLimit.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(gatherInput()) {
					drawGraph(input, chart);
					pageModel.setFinishable(true);
					chart.setVisible(true);
				} else {
					pageModel.setFinishable(false);
					chart.setVisible(false);
				}
				getWizard().getContainer().updateButtons();
			}
			
		});
		lowerSoftLimit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lowerSoftLimit.setVisible(false);

		comboDropDownLower.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboDropDownLower.getText().equals(DROPDOWN_ENTRY_NOTHRESHOLD)) {
					lowerThresholdLimit.setEnabled(false);
					lowerThresholdLimit.setVisible(false);
					lowerSoftLimit.setEnabled(false);
					lowerSoftLimit.setVisible(false);
					lblThresholdLimitLower.setVisible(false);
					lblSoftLimitLower.setVisible(false);
					lowerSoftLimit.setText("");
					lowerThresholdLimit.setText("");
				}

				else if (comboDropDownLower.getText().equals(DROPDOWN_ENTRY_HARDTHRESHOLD)) {
					lowerThresholdLimit.setEnabled(true);
					lowerThresholdLimit.setVisible(true);
					lowerSoftLimit.setEnabled(false);
					lowerSoftLimit.setVisible(false);
					lblThresholdLimitLower.setVisible(true);
					lblSoftLimitLower.setVisible(false);
				}

				else if (comboDropDownLower.getText().equals(DROPDOWN_ENTRY_FUZZYTHRESHOLD)
						|| comboDropDownLower.getText().equals(DROPDOWN_ENTRY_QUADRATICTHRESHOLD)
						|| comboDropDownLower.getText().equals(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD)) {
					lowerThresholdLimit.setEnabled(true);
					lowerThresholdLimit.setVisible(true);
					lowerSoftLimit.setEnabled(true);
					lowerSoftLimit.setVisible(true);
					lblThresholdLimitLower.setVisible(true);
					lblSoftLimitLower.setVisible(true);
				}
				
				if(gatherInput()) {
					drawGraph(input, chart);
					pageModel.setFinishable(true);
					chart.setVisible(true);
				} else {
					pageModel.setFinishable(false);
					chart.setVisible(false);
				}
				
				if (comboDropDownLower.getText().equals(DROPDOWN_ENTRY_NOTHRESHOLD)
						&& comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_NOTHRESHOLD)) {
					chart.setVisible(false);
				}	
				getWizard().getContainer().updateButtons();
			}		
		});

		// Manage editing an already existing SLO
		Threshold upperThreshold = pageModel.getSlo().getUpperThreshold();
		if (upperThreshold != null) {
			// Upper threshold already exists
			if (upperThreshold instanceof HardThreshold) {
				Measure<?, ?> upperHardThresholdMeasure = ((HardThreshold) upperThreshold).getThresholdLimit();
				upperThresholdLimit.setText(upperHardThresholdMeasure.getValue().toString() + " "
						+ upperHardThresholdMeasure.getUnit().toString());
				upperThresholdLimit.setEnabled(true);
				upperThresholdLimit.setVisible(true);
				lblThresholdLimitUpper.setVisible(true);
				comboDropDownUpper.select(0);
			} else if (upperThreshold instanceof SoftThreshold) {
				Measure<?, ?> upperThresholdMeasure = upperThreshold.getThresholdLimit();
				upperThresholdLimit.setText(
						upperThresholdMeasure.getValue().toString() + " " + upperThresholdMeasure.getUnit().toString());
				upperThresholdLimit.setEnabled(true);
				upperThresholdLimit.setVisible(true);
				lblThresholdLimitUpper.setVisible(true);

				Measure<?, ?> upperSoftThresholdMeasure = ((SoftThreshold) upperThreshold).getSoftLimit();
				upperSoftLimit.setText(upperSoftThresholdMeasure.getValue().toString() + " "
						+ upperSoftThresholdMeasure.getUnit().toString());
				lblSoftLimitUpper.setVisible(true);
				upperSoftLimit.setVisible(true);
				upperSoftLimit.setEnabled(true);
				if (upperThreshold instanceof LinearFuzzyThreshold) {
					comboDropDownUpper.select(1);
				} else if (upperThreshold instanceof QuadraticFuzzyThreshold) {
					comboDropDownUpper.select(2);
				} else if (upperThreshold instanceof NegativeQuadraticFuzzyThreshold) {
					comboDropDownUpper.select(3);
				}
			}
		}

		Threshold lowerThreshold = pageModel.getSlo().getLowerThreshold();
		if (lowerThreshold != null) {
			// Lower threshold already exists
			if (lowerThreshold instanceof HardThreshold) {
				Measure<?, ?> lowerHardThresholdMeasure = ((HardThreshold) lowerThreshold).getThresholdLimit();
				lowerThresholdLimit.setText(lowerHardThresholdMeasure.getValue().toString() + " "
						+ lowerHardThresholdMeasure.getUnit().toString());
				lowerThresholdLimit.setEnabled(true);
				lowerThresholdLimit.setVisible(true);
				lblThresholdLimitLower.setVisible(true);
				comboDropDownLower.select(0);
			} else if (lowerThreshold instanceof SoftThreshold) {
				Measure<?, ?> lowerThresholdMeasure = lowerThreshold.getThresholdLimit();
				lowerThresholdLimit.setText(
						lowerThresholdMeasure.getValue().toString() + " " + lowerThresholdMeasure.getUnit().toString());
				lowerThresholdLimit.setEnabled(true);
				lowerThresholdLimit.setVisible(true);
				lblThresholdLimitLower.setVisible(true);

				Measure<?, ?> lowerSoftThresholdMeasure = ((SoftThreshold) lowerThreshold).getSoftLimit();
				lowerSoftLimit.setText(lowerSoftThresholdMeasure.getValue().toString() + " "
						+ lowerSoftThresholdMeasure.getUnit().toString());
				lblSoftLimitLower.setVisible(true);
				lowerSoftLimit.setVisible(true);
				lowerSoftLimit.setEnabled(true);
				if (lowerThreshold instanceof LinearFuzzyThreshold) {
					comboDropDownLower.select(1);
				} else if (lowerThreshold instanceof QuadraticFuzzyThreshold) {
					comboDropDownLower.select(2);
				} else if (lowerThreshold instanceof NegativeQuadraticFuzzyThreshold) {
					comboDropDownLower.select(3);
				}
			}
		}

		new Label(lowerGroup, SWT.NONE);
		new Label(lowerGroup, SWT.NONE);
		validInputLabelLower = new Label(contentContainer, SWT.LEFT);
		validInputLabelLower.setForeground(new org.eclipse.swt.graphics.Color(lowerGroup.getDisplay(), 255, 0, 0));
		// TODO Somehow it wont be displayed if no placeholder space is used
		validInputLabelLower.setText("                                               "
				+ "                                                                  "
				+ "                                                                  "
				+ "                                                                  "
				+ "                                                                  ");

		graphContainer = new Composite(contentContainer, SWT.NONE);
		graphContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		graphContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		// Initialize text decorations for UI warnings
        org.eclipse.swt.graphics.Image image = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage();

        upperSoftDecorator = new ControlDecoration(upperSoftLimit, SWT.CENTER);
        upperSoftDecorator.setImage(image);
        upperSoftDecorator.setMarginWidth(1);
        
        upperHardLimitDecorator = new ControlDecoration(upperThresholdLimit, SWT.CENTER);
        upperHardLimitDecorator.setImage(image);
        upperHardLimitDecorator.setMarginWidth(1);
        
        lowerSoftDecorator = new ControlDecoration(lowerSoftLimit, SWT.CENTER);
        lowerSoftDecorator.setImage(image);
        lowerSoftDecorator.setMarginWidth(1);
        
        lowerHardLimitDecorator = new ControlDecoration(lowerThresholdLimit, SWT.CENTER);
        lowerHardLimitDecorator.setImage(image); 
        lowerHardLimitDecorator.setMarginWidth(1);
        
		initGraph();
		if(pageModel.getSlo().getMeasurementSpecification() != null) {
			if(gatherInput()) {
				drawGraph(input, chart);
				pageModel.setFinishable(true);
				chart.setVisible(true);
			} else {
				pageModel.setFinishable(false);
				chart.setVisible(false);
			}
		}
	
		setPageComplete(false);
		setControl(contentContainer);
		getWizard().getContainer().updateButtons();
        
	}

    /**
     * This method prepares the upper threshold for saving
     * by extracting the units and the values from the text field.
     */
	private void setUpperHardTreshold() {
		String[] data = upperThresholdLimit.getText().split("\\s+");
		if (data.length == 2 && !data[0].isEmpty() && !data[1].isEmpty()) {
			pageModel.setUpperThreshold(Float.parseFloat(data[0]), data[1]);
		} else if (data.length == 1 && !data[0].isEmpty()) {
			pageModel.setUpperThreshold(Float.parseFloat(data[0]), "");
		}
	}

	
    /**
     * This method prepares the upper fuzzy threshold for saving
     * by extracting the units and the values from the text field.
     */
	private void setUpperLinearFuzzyTreshold(fuzzyThresholdType type) {
		String[] limitData = upperThresholdLimit.getText().split("\\s+");
		String[] softData = upperSoftLimit.getText().split("\\s+");

		if (limitData.length == 2 && !limitData[0].isEmpty() && !limitData[1].isEmpty() && softData.length == 2
				&& !softData[0].isEmpty() && !softData[1].isEmpty()) {
			pageModel.setUpperThreshold(Float.parseFloat(limitData[0]), limitData[1], Float.parseFloat(softData[0]),
					softData[1], type);
		} else if (limitData.length == 1 && !limitData[0].isEmpty() && softData.length == 2 && !softData[0].isEmpty()
				&& !softData[1].isEmpty()) {
			pageModel.setUpperThreshold(Float.parseFloat(limitData[0]), "", Float.parseFloat(softData[0]), softData[1],
					type);
		} else if (limitData.length == 2 && !limitData[0].isEmpty() && !limitData[1].isEmpty() && softData.length == 1
				&& !softData[0].isEmpty()) {
			pageModel.setUpperThreshold(Float.parseFloat(limitData[0]), limitData[1], Float.parseFloat(softData[0]), "",
					type);
		} else if (limitData.length == 1 && !limitData[0].isEmpty() && softData.length == 1 && !softData[0].isEmpty()) {
			pageModel.setUpperThreshold(Float.parseFloat(limitData[0]), "", Float.parseFloat(softData[0]), "", type);
		}
	}

    /**
     * This method prepares the lower hard threshold for saving
     * by extracting the units and the values from the text field.
     */
	private void setLowerHardTreshold() {
		String[] data = lowerThresholdLimit.getText().split("\\s+");
		if (data.length == 2 && !data[0].isEmpty() && !data[1].isEmpty()) {
			pageModel.setLowerThreshold(Float.parseFloat(data[0]), data[1]);
		} else if (data.length == 1 && !data[0].isEmpty()) {
			pageModel.setLowerThreshold(Float.parseFloat(data[0]), "");
		}
	}

    /**
     * This method prepares the lower fuzzy threshold for saving
     * by extracting the units and the values from the text field.
     */
	private void setLowerLinearFuzzyTreshold(fuzzyThresholdType type) {
		String[] limitData = lowerThresholdLimit.getText().split("\\s+");
		String[] softData = lowerSoftLimit.getText().split("\\s+");

		if (limitData.length == 2 && !limitData[0].isEmpty() && !limitData[1].isEmpty() && softData.length == 2
				&& !softData[0].isEmpty() && !softData[1].isEmpty()) {
			pageModel.setLowerThreshold(Float.parseFloat(limitData[0]), limitData[1], Float.parseFloat(softData[0]),
					softData[1], type);
		} else if (limitData.length == 1 && !limitData[0].isEmpty() && softData.length == 2 && !softData[0].isEmpty()
				&& !softData[1].isEmpty()) {
			pageModel.setLowerThreshold(Float.parseFloat(limitData[0]), "", Float.parseFloat(softData[0]), softData[1],
					type);
		} else if (limitData.length == 2 && !limitData[0].isEmpty() && !limitData[1].isEmpty() && softData.length == 1
				&& !softData[0].isEmpty()) {
			pageModel.setLowerThreshold(Float.parseFloat(limitData[0]), limitData[1], Float.parseFloat(softData[0]), "",
					type);
		} else if (limitData.length == 1 && !limitData[0].isEmpty() && softData.length == 1 && !softData[0].isEmpty()) {
			pageModel.setLowerThreshold(Float.parseFloat(limitData[0]), "", Float.parseFloat(softData[0]), "", type);
		}
	}

    /**
     * This method calls preparation methods for saving thresholds depending on the 
     * thresholds selected by the user.
     */
	public void finishTresholds() {
		// Index 0: Hard-Threshold
		// Index 1: LinearFuzzy-Threshold
		// Index 2: No threshold

		try {
			if (comboDropDownUpper.getSelectionIndex() == 0) {
				setUpperHardTreshold();
			} else if (comboDropDownUpper.getSelectionIndex() == 1) {
				setUpperLinearFuzzyTreshold(fuzzyThresholdType.LINEAR);
			} else if (comboDropDownUpper.getSelectionIndex() == 2) {
				setUpperLinearFuzzyTreshold(fuzzyThresholdType.QUADRATIC);
			} else if (comboDropDownUpper.getSelectionIndex() == 3) {
				setUpperLinearFuzzyTreshold(fuzzyThresholdType.NEGATIVE_QUADRATIC);
			} else if (comboDropDownUpper.getSelectionIndex() == 4) {
				pageModel.deleteUpperThreshold();
			}

		} catch (Exception e) {
			// TODO: handle exception
			// java.lang.IllegalStateException: Cannot modify resource set without a write
			// transaction
			// Setting and deleting a threshold will throw an exception but works somehow
			// e.printStackTrace();
			// Its working although this is throwing an exception 
			// Could be resolved in future work
		}
		try {
			if (comboDropDownLower.getSelectionIndex() == 0) {
				setLowerHardTreshold();
			} else if (comboDropDownLower.getSelectionIndex() == 1) {
				setLowerLinearFuzzyTreshold(fuzzyThresholdType.LINEAR);
			} else if (comboDropDownLower.getSelectionIndex() == 2) {
				setLowerLinearFuzzyTreshold(fuzzyThresholdType.QUADRATIC);
			} else if (comboDropDownLower.getSelectionIndex() == 3) {
				setLowerLinearFuzzyTreshold(fuzzyThresholdType.NEGATIVE_QUADRATIC);
			} else if (comboDropDownLower.getSelectionIndex() == 4) {
				pageModel.deleteLowerThreshold();
			}

		} catch (Exception e) {
			// Its working although this is throwing an exception 
			// Could be resolved in future work
		}

	}

	/**
	 * Draws a graph according to the input
	 * 
	 * @param input Array of 4 Doubles as Thresholds, in order: lower hard, lower
	 *              soft, upper soft, upper hard
	 */
	private void drawGraph(Double[] input, Chart chart) {
		String unitString = pageModel.getDefaultUnit().toString();
		chart.setSize(672, 200);
		chart.getTitle().setText("Threshold Visualization");
		chart.getAxisSet().getXAxis(0).getTitle().setText(unitString);
		chart.getAxisSet().getYAxis(0).getTitle().setText("SLO Fulfillment");
		double rangeStart = 0;
		double rangeEnd = 0;
		
		if (input[1] == null) {
			input[1] = input[0];
		}

		if (input[2] == null) {
			input[2] = input[3];
		}

		if (input[0] != null && input[3] == null) {
			input[3] = input[2] = input[1] * 2; // calculation from the soft limit is intentional
			rangeEnd = input[1] * 1.1;
		} else if (input[3] != null) {
			rangeEnd = input[3] * 1.1;
		}

		if (input[3] != null && input[0] == null) {
			input[0] = input[1] = 0.0; // calculation from the soft limit is intentional
		}
		
		if (input[0] != null && input[3] != null && input[1] != null && input[2] != null) {

			int digitLength[] = { String.valueOf(input[0]).split("\\.")[1].length(),
					String.valueOf(input[1]).split("\\.")[1].length(),
					String.valueOf(input[2]).split("\\.")[1].length(),
					String.valueOf(input[3]).split("\\.")[1].length() };
			Arrays.sort(digitLength);
			double step = Math.pow(0.1, digitLength[3] + 2);
			ArrayList<Double> ySeries = new ArrayList<>();
			ArrayList<Double> xSeries = new ArrayList<>();
			
			//limits number of points to avoid heap size problems
			while ( (rangeEnd - rangeStart)/step > pointLimit) {
				step = step * 10;
			}
			
			for (double i = rangeStart; i <= rangeEnd; i = i + step) {
				double toAdd = chartSeriesHelper(input, i);
				ySeries.add(toAdd);
				xSeries.add(i);
			}

			double[] yArray = ySeries.stream().mapToDouble(d -> d).toArray();
			double[] xArray = xSeries.stream().mapToDouble(d -> d).toArray();
			ISeriesSet seriesSet = chart.getSeriesSet();
			ILineSeries series = (ILineSeries) seriesSet.createSeries(SeriesType.LINE, "Threshold");
			series.setYSeries(yArray);
			series.setXSeries(xArray);
			series.setSymbolType(PlotSymbolType.NONE);
			// adjust the axis range
			chart.getAxisSet().adjustRange();
			chart.setVisible(true);
		}
		chart.redraw();

	}

	public double chartSeriesHelper(Double[] input, double i) {
		double result = 0;
		if (input[1] <= i && i <= input[2]) {
			result = 1;
		}

		if (input[0] <= i && i < input[1]) {
			if (comboDropDownLower.getSelectionIndex() == 1) {
				result = (i - input[0]) / (input[1] - input[0]);
			} else if (comboDropDownLower.getSelectionIndex() == 2) {
				result = 1 / Math.pow((input[1] - input[0]), 2) * Math.pow((i - input[0]), 2);
			} else if (comboDropDownLower.getSelectionIndex() == 3) {
				result = 1 - 1 / Math.pow((input[1] - input[0]), 2) * Math.pow((i - input[1]), 2);
			} else if (comboDropDownLower.getSelectionIndex() == 4) {

			}
		}

		if (input[2] < i && i <= input[3]) {
			if (comboDropDownUpper.getSelectionIndex() == 1) {
				result = -(i - input[3]) / (input[3] - input[2]);
			} else if (comboDropDownUpper.getSelectionIndex() == 2) {
				result = 1 / Math.pow((input[3] - input[2]), 2) * Math.pow((i - input[3]), 2);
			} else if (comboDropDownUpper.getSelectionIndex() == 3) {
				result = 1 - 1 / Math.pow((input[3] - input[2]), 2) * Math.pow((i - input[2]), 2);
			} else if (comboDropDownUpper.getSelectionIndex() == 4) {

			}
		}
		return result;
	}

    /**
     * Initializes chart for visualizing thresholds.
     */
	private void initGraph() {
		chart = new Chart(graphContainer, SWT.NONE);
		chart.setSize(672, 200);
		chart.setVisible(false);
	}

    /**
     * This method gathers threshold measure from a string 
     * by splitting it into unit and value.
     * 
     * @param text 		the input text containing value and unit
     * @return measure  the converted measure containing value and unit
     */
	private Measure<?, ?> getInputFromText(String text) {
		Unit<? extends Quantity> unit;
		Measure<Float, ?> measure;
		try {
			unit = Unit.valueOf(text.split("\\s+")[1]);
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			unit = Unit.valueOf("bit");
		}
		try {
			measure = Measure.valueOf(Float.parseFloat(text.split("\\s+")[0]), unit);
		} catch (NumberFormatException e) {
			measure = Measure.valueOf(Float.parseFloat("0"), unit);
		}
		return measure;
	}

    /**
     * This method validates the input for the thresholds and manages error
     * messages accordingly.
     * 
     * @return 		returns true if input is valid else false
     */
	private boolean validateInput() {
		Unit<?> defaultUnit = pageModel.getDefaultUnit();
		String mspType = pageModel.getSpecificationType().replace("[TRANSIENT]", ""); 
		// Reset text colors
		lowerThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 0, 0, 0));
		upperThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 0, 0, 0));
		lowerSoftLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 0, 0, 0));
		upperSoftLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 0, 0, 0));
		
		lowerHardLimitDecorator.hide();
		lowerSoftDecorator.hide();
		upperHardLimitDecorator.hide();
		upperSoftDecorator.hide();
		
		setErrorMessage(null);
		
		boolean hasError = false;
		int errorCount = 0;
		int emptyCount = 0;
		
		// No thresholds should be possible
		if ((comboDropDownLower.getText().equals(DROPDOWN_ENTRY_NOTHRESHOLD)) 
				&& comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_NOTHRESHOLD)) {
			return true;
		}
		
		// Check if lower hard limit has correct unit
		if (comboDropDownLower.getText().equals(DROPDOWN_ENTRY_HARDTHRESHOLD)
				&& !defaultUnit.isCompatible(getInputFromText(lowerThresholdLimit.getText()).getUnit())) {
			hasError = true;
			if (!lowerThresholdLimit.getText().isEmpty()) {
				errorCount += 1;

				lowerHardLimitDecorator.setDescriptionText("Unit is invalid for selected specification type");
				lowerHardLimitDecorator.show();

				if (defaultUnit.toString().equals("")) {
					setErrorMessage("Lower hard threshold unit is wrong! You did choose a " + pageModel.getSpecificationType()
					+ " specification type. \n The default unit is NONE. A vaild input for example would be '10' without any units");
				} else if (defaultUnit.toString().equals("s")) {
					setErrorMessage("Lower hard threshold unit is wrong! You did choose a " + mspType 
					+ " specification type. \n The default unit is 's'. A vaild input for example would be '10 s' or '10 ms'");
				} else {
					setErrorMessage("Lower hard threshold unit is wrong! You did choose a " + mspType 
							+ " specification type.");
				}
			} else {
				lowerHardLimitDecorator.setDescriptionText("Threshold can not be empty");
				lowerHardLimitDecorator.show();
				emptyCount += 1;
				setErrorMessage("Lower hard threshold is empty.");
			}	
		} 
		
		// Check if lower hard limit has correct unit
		if ((comboDropDownLower.getText().equals(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD)
				|| comboDropDownLower.getText().equals(DROPDOWN_ENTRY_FUZZYTHRESHOLD)
				|| comboDropDownLower.getText().equals(DROPDOWN_ENTRY_QUADRATICTHRESHOLD))
				&& !defaultUnit.isCompatible(getInputFromText(lowerThresholdLimit.getText()).getUnit())) {
			
			hasError = true;
			if (!lowerThresholdLimit.getText().isEmpty()) {
				errorCount += 1;

				lowerHardLimitDecorator.setDescriptionText("Unit is invalid for selected specification type");
				lowerHardLimitDecorator.show();
				
				if (defaultUnit.toString().equals("")) {
					setErrorMessage("Lower hard threshold unit is wrong! You did choose a " + pageModel.getSpecificationType()
					+ " specification type. \n The default unit is NONE. A vaild input for example would be '10' without any units");
				} else if (defaultUnit.toString().equals("s")) {
					setErrorMessage("Lower hard threshold unit is wrong! You did choose a " + mspType 
					+ " specification type. \n The default unit is 's'. A vaild input for example would be '10 s' or '10 ms'");
				} else {
					setErrorMessage("Lower hard threshold unit is wrong! You did choose a " + mspType 
							+ " specification type.");
				}
			} else {
				lowerHardLimitDecorator.setDescriptionText("Threshold can not be empty");
				lowerHardLimitDecorator.show();
				emptyCount += 1;
				setErrorMessage("Lower hard threshold is empty.");
			}		
		} 
		
		// Check if lower soft limit has correct unit
		if ((comboDropDownLower.getText().equals(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD)
				|| comboDropDownLower.getText().equals(DROPDOWN_ENTRY_FUZZYTHRESHOLD)
				|| comboDropDownLower.getText().equals(DROPDOWN_ENTRY_QUADRATICTHRESHOLD))
				&& !defaultUnit.isCompatible(getInputFromText(lowerSoftLimit.getText()).getUnit())) {

			hasError = true;
			if (!lowerSoftLimit.getText().isEmpty()) {
				errorCount += 1;

				lowerSoftDecorator.setDescriptionText("Unit is invalid for selected specification type");
				lowerSoftDecorator.show();
				
				if (defaultUnit.toString().equals("")) {
					setErrorMessage("Lower soft threshold unit is wrong! You did choose a " + pageModel.getSpecificationType()
					+ " specification type. \n The default unit is NONE. A vaild input for example would be '10' without any units");
				} else if (defaultUnit.toString().equals("s")) {
					setErrorMessage("Lower soft threshold unit is wrong! You did choose a " + mspType 
					+ " specification type. \n The default unit is 's'. A vaild input for example would be '10 s' or '10 ms'");
				} else {
					setErrorMessage("Lower soft threshold unit is wrong! You did choose a " + mspType 
							+ " specification type.");
				}
			} else {
				lowerSoftDecorator.setDescriptionText("Threshold can not be empty");
				lowerSoftDecorator.show();
				setErrorMessage("Lower soft threshold is empty.");
			}
		}
		
		// Check if upper hard limit has correct unit
		if (comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_HARDTHRESHOLD)
				&& !defaultUnit.isCompatible(getInputFromText(upperThresholdLimit.getText()).getUnit())) {

			hasError = true;
			if (!upperThresholdLimit.getText().isEmpty()) {
				errorCount += 1;

				upperHardLimitDecorator.setDescriptionText("Unit is invalid for selected specification type");
				upperHardLimitDecorator.show();
				
				if (defaultUnit.toString().equals("")) {
					setErrorMessage("Upper hard threshold unit is wrong! You did choose a " + pageModel.getSpecificationType()
					+ " specification type. \n The default unit is NONE. A vaild input for example would be '10' without any units");
				} else if (defaultUnit.toString().equals("s")) {
					setErrorMessage("Upper hard threshold unit is wrong! You did choose a " + mspType 
					+ " specification type. \n The default unit is 's'. A vaild input for example would be '10 s' or '10 ms'");
				} else {
					setErrorMessage("Upper hard threshold unit is wrong! You did choose a " + mspType 
							+ " specification type.");
				}
			} else {
				upperHardLimitDecorator.setDescriptionText("Threshold can not be empty");
				upperHardLimitDecorator.show();
				emptyCount += 1;
				setErrorMessage("Upper hard threshold is empty.");
			}
		} 
		
		// Check if upper hard limit has correct unit
		if ((comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD)
				|| comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_FUZZYTHRESHOLD)
				|| comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_QUADRATICTHRESHOLD))
				&& !defaultUnit.isCompatible(getInputFromText(upperThresholdLimit.getText()).getUnit())) {

			hasError = true;
			if (!upperThresholdLimit.getText().isEmpty()) {
				errorCount += 1;

				upperHardLimitDecorator.setDescriptionText("Unit is invalid for selected specification type");
				upperHardLimitDecorator.show();
				
				if (defaultUnit.toString().equals("")) {
					setErrorMessage("Upper hard threshold unit is wrong! You did choose a " + pageModel.getSpecificationType()
					+ " specification type. \n The default unit is NONE. A vaild input for example would be '10' without any units");
				} else if (defaultUnit.toString().equals("s")) {
					setErrorMessage("Upper hard threshold unit is wrong! You did choose a " + mspType 
					+ " specification type. \n The default unit is 's'. A vaild input for example would be '10 s' or '10 ms'");
				} else {
					setErrorMessage("Upper hard threshold unit is wrong! You did choose a " + mspType 
							+ " specification type.");
				}
			} else {
				upperHardLimitDecorator.setDescriptionText("Threshold can not be empty");
				upperHardLimitDecorator.show();
				emptyCount += 1;
				setErrorMessage("Upper hard threshold is empty.");
			}
		} 
		
		// Check if upper soft limit has correct unit
		if ((comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD)
				|| comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_FUZZYTHRESHOLD)
				|| comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_QUADRATICTHRESHOLD))
				&& !defaultUnit.isCompatible(getInputFromText(upperSoftLimit.getText()).getUnit())) {

			hasError = true;
			if (!upperSoftLimit.getText().isEmpty()) {
				errorCount += 1;

				upperSoftDecorator.setDescriptionText("Unit is invalid for selected specification type");
				upperSoftDecorator.show();
				
				if (defaultUnit.toString().equals("")) {
					setErrorMessage("Upper soft threshold unit is wrong! You did choose a " + pageModel.getSpecificationType()
					+ " specification type. \n The default unit is NONE. A vaild input for example would be '10' without any units");
				} else if (defaultUnit.toString().equals("s")) {
					setErrorMessage("Upper soft threshold unit is wrong! You did choose a " + mspType 
					+ " specification type. \n The default unit is 's'. A vaild input for example would be '10 s' or '10 ms'");
				} else {
					setErrorMessage("Upper soft threshold unit is wrong! You did choose a " + mspType 
							+ " specification type.");
				}
			} else {
				upperSoftDecorator.setDescriptionText("Threshold can not be empty");
				upperSoftDecorator.show();
				emptyCount += 1;
				setErrorMessage("Upper soft threshold is empty.");
			}	
		}
		
		if (hasError) {
			// If multiple errors show a more generall error message
			if (errorCount > 1) {
				if (defaultUnit.toString().equals("")) {
					setErrorMessage("Threshold units are wrong! You did choose a " + pageModel.getSpecificationType()
					+ " specification type. \n The default unit is NONE. A vaild input for example would be '10' without any units");
				} else if (defaultUnit.toString().equals("s")) {
					setErrorMessage("Threshold units are wrong! You did choose a " + mspType 
					+ " specification type. \n The default unit is 's'. A vaild input for example would be '10 s' or '10 ms'");
				} else {
					setErrorMessage("Threshold units are wrong! You did choose a " + mspType 
							+ " specification type.");
				}	
			} 
			if (emptyCount > 1) {
				setErrorMessage("Thresholds are empty.");
			} 
			return false;
		}
		
		// Special case for msp without units
		// which are not detected by the compatible method
		if (defaultUnit.toString().equals("")) {
			boolean error_special = false;
			if (comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_HARDTHRESHOLD)) {
				
				if (upperThresholdLimit.getText().isEmpty()) {
					upperHardLimitDecorator.show();
					upperHardLimitDecorator.setDescriptionText("Threshold can not be empty");
					error_special = true;
				} else if (!upperThresholdLimit.getText().matches("[0-9]+")) {
					error_special = true;	
					upperHardLimitDecorator.show();
					upperHardLimitDecorator.setDescriptionText("Threshold contains wrong characters");
					setErrorMessage("Threshold units are wrong! You did choose a " + pageModel.getSpecificationType()
					+ " specification type. \n The default unit is NONE. A vaild input for example would be '10' without any units");
				}
				
			} else if (comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD)
					|| comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_FUZZYTHRESHOLD)
					|| comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_QUADRATICTHRESHOLD)) {
				
				if (upperThresholdLimit.getText().isEmpty()) {
					upperHardLimitDecorator.show();
					upperHardLimitDecorator.setDescriptionText("Threshold can not be empty");
					error_special = true;
				} else if (!upperThresholdLimit.getText().matches("[0-9]+")) {
					error_special = true;	
					upperHardLimitDecorator.show();
					upperHardLimitDecorator.setDescriptionText("Threshold contains wrong characters");
					setErrorMessage("Threshold units are wrong! You did choose a " + pageModel.getSpecificationType()
					+ " specification type. \n The default unit is NONE. A vaild input for example would be '10' without any units");
				}
				
				if (upperSoftLimit.getText().isEmpty()) {
					upperSoftDecorator.show();
					upperSoftDecorator.setDescriptionText("Threshold can not be empty");
					error_special = true;
				} else if (!upperSoftLimit.getText().matches("[0-9]+")) {
					error_special = true;		
					upperSoftDecorator.show();
					upperSoftDecorator.setDescriptionText("Threshold contains wrong characters");
					setErrorMessage("Threshold units are wrong! You did choose a " + pageModel.getSpecificationType()
					+ " specification type. \n The default unit is NONE. A vaild input for example would be '10' without any units");
				}			
				
			}
			if (comboDropDownLower.getText().equals(DROPDOWN_ENTRY_HARDTHRESHOLD)) {
				
				if (lowerThresholdLimit.getText().isEmpty()) {
					lowerHardLimitDecorator.show();
					lowerHardLimitDecorator.setDescriptionText("Threshold can not be empty");
					error_special = true;
				} else if (!lowerThresholdLimit.getText().matches("[0-9]+")) {
					error_special = true;		
					lowerHardLimitDecorator.show();
					lowerHardLimitDecorator.setDescriptionText("Threshold contains wrong characters");
					setErrorMessage("Threshold units are wrong! You did choose a " + pageModel.getSpecificationType()
					+ " specification type. \n The default unit is NONE. A vaild input for example would be '10' without any units");
				}
				
			} else if (comboDropDownLower.getText().equals(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD)
					|| comboDropDownLower.getText().equals(DROPDOWN_ENTRY_FUZZYTHRESHOLD)
					|| comboDropDownLower.getText().equals(DROPDOWN_ENTRY_QUADRATICTHRESHOLD)) {
				
				if (lowerThresholdLimit.getText().isEmpty()) {
					lowerHardLimitDecorator.show();
					lowerHardLimitDecorator.setDescriptionText("Threshold can not be empty");
					error_special = true;
				} else if (!lowerThresholdLimit.getText().matches("[0-9]+")) {
					error_special = true;		
					lowerHardLimitDecorator.show();
					lowerHardLimitDecorator.setDescriptionText("Threshold contains wrong characters");
					setErrorMessage("Threshold units are wrong! You did choose a " + pageModel.getSpecificationType()
					+ " specification type. \n The default unit is NONE. A vaild input for example would be '10' without any units");
				}
				
				if (lowerSoftLimit.getText().isEmpty()) {
					lowerSoftDecorator.show();
					lowerSoftDecorator.setDescriptionText("Threshold can not be empty");
					error_special = true;
				} else if (!lowerSoftLimit.getText().matches("[0-9]+")) {
					error_special = true;		
					lowerSoftDecorator.show();
					lowerSoftDecorator.setDescriptionText("Threshold contains wrong characters");
					setErrorMessage("Threshold units are wrong! You did choose a " + pageModel.getSpecificationType()
					+ " specification type. \n The default unit is NONE. A vaild input for example would be '10' without any units.");
				}			
			}				
			if (error_special) {
				return false;
			}
			
		}
		// Handle unit matches but there is no number
		boolean numberMissing = false;
		if (!defaultUnit.toString().equals("")) {
			
			if (comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_HARDTHRESHOLD)) {
				// REGEX: digits followed by a space followed by characters
				if (!upperThresholdLimit.getText().matches("\\d+([\\.]*\\d+)?\\s\\w+")) {
					setErrorMessage("Threshold empty.");
					numberMissing = true;
					upperHardLimitDecorator.show();
				}
			} else if (comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD)
					|| comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_FUZZYTHRESHOLD)
					|| comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_QUADRATICTHRESHOLD)) {
				if (!upperThresholdLimit.getText().matches("\\d+([\\.]*\\d+)?\\s\\w+")) {
					setErrorMessage("Threshold empty.");
					numberMissing = true;
					upperHardLimitDecorator.show();
				}
				if (!upperSoftLimit.getText().matches("\\d+([\\.]*\\d+)?\\s\\w+")) {
					setErrorMessage("Threshold empty.");
					numberMissing = true;
					upperSoftDecorator.show();
				}
			}
			if (comboDropDownLower.getText().equals(DROPDOWN_ENTRY_HARDTHRESHOLD)) {
				// REGEX: digits followed by a space followed by characters
				if (!lowerThresholdLimit.getText().matches("\\d+([\\.]*\\d+)?\\s\\w+")) {
					setErrorMessage("Threshold empty.");
					numberMissing = true;
					lowerHardLimitDecorator.show();
				}
			} else if (comboDropDownLower.getText().equals(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD)
					|| comboDropDownLower.getText().equals(DROPDOWN_ENTRY_FUZZYTHRESHOLD)
					|| comboDropDownLower.getText().equals(DROPDOWN_ENTRY_QUADRATICTHRESHOLD)) {
				if (!lowerThresholdLimit.getText().matches("\\d+([\\.]*\\d+)?\\s\\w+")) {
					setErrorMessage("Threshold empty.");
					numberMissing = true;
					lowerHardLimitDecorator.show();
				}
				if (!lowerSoftLimit.getText().matches("\\d+([\\.]*\\d+)?\\s\\w+")) {
					setErrorMessage("Threshold empty.");
					numberMissing = true;
					lowerSoftDecorator.show();
				}
			}
			if (numberMissing) {
				return false;
			}
		}
		
		return true;
	}
	
    /**
     * This method gathers inputs, checks for errors and manages error messages accordingly
     * 
     * @return returns true if input is valid
     */
	private boolean gatherInput() {
		input[0] = input[1] = input[2] = input[3] = null;
		if (validateInput()) {
			Unit<?> defaultUnit = pageModel.getDefaultUnit();
			try {
				if (comboDropDownLower.getText().equals(DROPDOWN_ENTRY_NOTHRESHOLD)) {
					input[0] = null;
					input[1] = null;
				} else if (comboDropDownLower.getText().equals(DROPDOWN_ENTRY_HARDTHRESHOLD)) {
					UnitConverter hardConverter = getInputFromText(lowerThresholdLimit.getText()).getUnit()
							.getConverterTo(defaultUnit);

					input[0] = hardConverter.convert(
							Double.parseDouble(getInputFromText(lowerThresholdLimit.getText()).getValue().toString()));
					input[1] = null;
				} else if (comboDropDownLower.getText().equals(DROPDOWN_ENTRY_FUZZYTHRESHOLD)
						|| comboDropDownLower.getText().equals(DROPDOWN_ENTRY_QUADRATICTHRESHOLD)
						|| comboDropDownLower.getText().equals(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD)) {

					UnitConverter hardConverter = getInputFromText(lowerThresholdLimit.getText()).getUnit()
							.getConverterTo(defaultUnit);
					UnitConverter softConverter = getInputFromText(lowerSoftLimit.getText()).getUnit()
							.getConverterTo(defaultUnit);

					input[0] = hardConverter.convert(
							Double.parseDouble(getInputFromText(lowerThresholdLimit.getText()).getValue().toString()));
					input[1] = softConverter.convert(
							Double.parseDouble(getInputFromText(lowerSoftLimit.getText()).getValue().toString()));

				}

				if (comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_NOTHRESHOLD)) {
					input[3] = null;
					input[2] = null;
				} else if (comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_HARDTHRESHOLD)) {
					UnitConverter hardConverter = getInputFromText(upperThresholdLimit.getText()).getUnit()
							.getConverterTo(defaultUnit);

					input[3] = hardConverter.convert(
							Double.parseDouble(getInputFromText(upperThresholdLimit.getText()).getValue().toString()));
					input[2] = null;
				} else if (comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_FUZZYTHRESHOLD)
						|| comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_QUADRATICTHRESHOLD)
						|| comboDropDownUpper.getText().equals(DROPDOWN_ENTRY_NEGATIVQUADRATICTHRESHOLD)) {

					UnitConverter hardConverter = getInputFromText(upperThresholdLimit.getText()).getUnit()
							.getConverterTo(defaultUnit);
					UnitConverter softConverter = getInputFromText(upperSoftLimit.getText()).getUnit()
							.getConverterTo(defaultUnit);

					input[3] = hardConverter.convert(
							Double.parseDouble(getInputFromText(upperThresholdLimit.getText()).getValue().toString()));
					input[2] = softConverter.convert(
							Double.parseDouble(getInputFromText(upperSoftLimit.getText()).getValue().toString()));
				}
											
			} catch (ConversionException e) {
				System.out.println("conversion error");
				return false;
			}
			
			// Validate input (e.g. check if lower threshold is bigger than upper threshold)
			// Reset decorations, colors and error messages
			lowerHardLimitDecorator.hide();
			lowerSoftDecorator.hide();
			upperHardLimitDecorator.hide();
			upperSoftDecorator.hide();
			
			lowerThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 0, 0, 0));
			upperThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 0, 0, 0));
			lowerSoftLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 0, 0, 0));
			upperSoftLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 0, 0, 0));
			
			setErrorMessage(null);
			
			if (input[0] != null && input[3] != null) {
				double upperThresholdHard = input[3];
				double lowerThresholdHard = input[0];
				
				if (lowerThresholdHard > upperThresholdHard) {
					lowerThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					upperThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					
					lowerHardLimitDecorator.setDescriptionText(ERROR_UPPER_THRESHOLD_LOWER_THAN_LOWER_THRESHOLD);
					lowerHardLimitDecorator.show();
					upperHardLimitDecorator.setDescriptionText(ERROR_UPPER_THRESHOLD_LOWER_THAN_LOWER_THRESHOLD);
					upperHardLimitDecorator.show();

					setErrorMessage(ERROR_UPPER_THRESHOLD_LOWER_THAN_LOWER_THRESHOLD);

					return false;
				}
				else if (lowerThresholdHard == upperThresholdHard) {
					lowerThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					upperThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
				
					lowerHardLimitDecorator.setDescriptionText(ERROR_UPPER_THRESHOLD_EQUAL_LOWER_THRESHOLD);
					lowerHardLimitDecorator.show();
					upperHardLimitDecorator.setDescriptionText(ERROR_UPPER_THRESHOLD_EQUAL_LOWER_THRESHOLD);
					upperHardLimitDecorator.show();
					
					setErrorMessage(ERROR_UPPER_THRESHOLD_EQUAL_LOWER_THRESHOLD);

					return false;	
				}
			}
			
			// Check if lower-soft-limit is lower than lower-hard-limit
			if (input[1] != null && input[0] != null) {
				double lowerSoftThreshold = input[1];
				double lowerThresholdHard = input[0];

				if (lowerSoftThreshold < lowerThresholdHard) {
					lowerSoftLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					lowerThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					
					lowerSoftDecorator.setDescriptionText(ERROR_LOWER_SOFT_LOWER_THAN_LOWER_HARD_THRESHOLD);
					lowerSoftDecorator.show();
					lowerHardLimitDecorator.setDescriptionText(ERROR_LOWER_SOFT_LOWER_THAN_LOWER_HARD_THRESHOLD);
					lowerHardLimitDecorator.show();
					
					setErrorMessage(ERROR_LOWER_SOFT_LOWER_THAN_LOWER_HARD_THRESHOLD);

					return false;
					
				} else if (lowerSoftThreshold == lowerThresholdHard) {
					lowerSoftLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					lowerThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					
					lowerSoftDecorator.setDescriptionText(ERROR_LOWER_SOFT_AND_HARD_EQUAL);
					lowerSoftDecorator.show();
					lowerHardLimitDecorator.setDescriptionText(ERROR_LOWER_SOFT_AND_HARD_EQUAL);
					lowerHardLimitDecorator.show();
					
					setErrorMessage(ERROR_LOWER_SOFT_AND_HARD_EQUAL);

					return false;
				}
			}
			
			// Check if upper-soft-limit is higher than upper-hard-limit
			if (input[3] != null && input[2] != null) {
				double upperSoftThreshold = input[2];
				double upperThresholdHard = input[3];

				if (upperSoftThreshold > upperThresholdHard) {
					upperSoftLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					upperThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					
					upperSoftDecorator.setDescriptionText(ERROR_UPPER_SOFT_HIGHER_UPPER_HARD);
					upperSoftDecorator.show();
					upperHardLimitDecorator.setDescriptionText(ERROR_UPPER_SOFT_HIGHER_UPPER_HARD);
					upperHardLimitDecorator.show();
					
					setErrorMessage(ERROR_UPPER_SOFT_HIGHER_UPPER_HARD);

					return false;
					
				}  else if (upperSoftThreshold == upperThresholdHard) {
					upperSoftLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					upperThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					
					upperSoftDecorator.setDescriptionText(ERROR_UPPER_SOFT_AND_HARD_EQUAL);
					upperSoftDecorator.show();
					upperHardLimitDecorator.setDescriptionText(ERROR_UPPER_SOFT_AND_HARD_EQUAL);
					upperHardLimitDecorator.show();
					
					setErrorMessage(ERROR_UPPER_SOFT_AND_HARD_EQUAL);

					return false;
				}
			}
			
			// Check if upper-soft-limit is lower than lower-hard-limit (they can be equal)
			if (input[0] != null && input[2] != null) {
				double upperSoftThreshold = input[2];
				double lowerThresholdHard = input[0];
				
				if (upperSoftThreshold < lowerThresholdHard) {
					upperSoftLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					lowerThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					
					upperSoftDecorator.setDescriptionText(ERROR_UPPER_SOFT_HIGHER_THAN_LOWER_HARD_THRESHOLD);
					upperSoftDecorator.show();
					lowerHardLimitDecorator.setDescriptionText(ERROR_UPPER_SOFT_HIGHER_THAN_LOWER_HARD_THRESHOLD);
					lowerHardLimitDecorator.show();
					
					setErrorMessage(ERROR_UPPER_SOFT_HIGHER_THAN_LOWER_HARD_THRESHOLD);

					return false;			
				} 
			}
			
			// Check if upper-soft is lower than lower-soft-limit (they can be equal)
			if (input[1] != null && input[2] != null) {
				double lowerSoftThreshold = input[1];
				double upperSoftThreshold = input[2];
				
				if (lowerSoftThreshold > upperSoftThreshold) {
					lowerSoftLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					upperSoftLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					
					// Setup decorations
					lowerSoftDecorator.setDescriptionText(ERROR_LOWER_SOFT_HIGHER_UPPER_SOFT);
					upperSoftDecorator.setDescriptionText(ERROR_LOWER_SOFT_HIGHER_UPPER_SOFT);
					lowerSoftDecorator.show();
					upperSoftDecorator.show();
					
					setErrorMessage(ERROR_LOWER_SOFT_HIGHER_UPPER_SOFT);

					return false;
				} 
			}
			
			// Check if lower-soft is higher than upper-hard-limit (they can be equal)
			if (input[1] != null && input[3] != null) {
				double lowerSoftThreshold = input[1];
				double upperThreshold = input[3];
				
				if (lowerSoftThreshold > upperThreshold) {

					lowerSoftLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					upperThresholdLimit.setForeground(new org.eclipse.swt.graphics.Color(null, 255, 0, 0));
					
					// Setup decorations
					lowerSoftDecorator.setDescriptionText(ERROR_LOWER_SOFT_HIGHER_UPPER_HARD);
					upperHardLimitDecorator.setDescriptionText(ERROR_LOWER_SOFT_HIGHER_UPPER_HARD);
					lowerSoftDecorator.show();
					upperHardLimitDecorator.show();
					
					setErrorMessage(ERROR_LOWER_SOFT_HIGHER_UPPER_HARD);
					return false;
				} 
			} 
		} else {
			// Input is not valid
			return false;
		}
			
		// Return true if every possible input is valid
		return true;
	}
	
	/**
	 * Returns the scaling of the OS with a value of 1.0 if set to 100%, 1.5 if set
	 * to 150% etc.
	 * 
	 * @return the scaling of the OS with a value of 1.0 if set to 100%, 1.5 if set
	 *         to 150% etc.
	 */
	private float getDPIScale() {
		int currentDPI = Display.getDefault().getDPI().x;
		float defaultDPI = 96.0f;
		return currentDPI / defaultDPI;
	}

	/**
	 * Sets the image of the wizard which is shown in the upper right corner of the
	 * wizard pages.
	 * 
	 * Also scales the image according to the scaling of the OS. The original size
	 * of the image is used for a scaling of 150%, that means when the scaling of
	 * the OS is below 150%, the image is shrunk.
	 */
	@SuppressWarnings("deprecation")
	private void setImage() {
		Bundle bundle = Platform.getBundle("org.palladiosimulator.measurementsui.wizard");
		URL fullPathString = BundleUtility.find(bundle, "icons/wizardImage.png");
		ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(fullPathString);
		ImageData imageData = imageDescriptor.getImageData();
		int scaledWidth = Math.round(imageData.width / (1.5f / getDPIScale()));
		int scaledHeight = Math.round(imageData.height / (1.5f / getDPIScale()));
		if (scaledWidth < 100) {
			scaledWidth = 100;
		}
		if (scaledHeight < 100) {
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
