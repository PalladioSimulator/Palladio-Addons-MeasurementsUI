package org.palladiosimulator.measurementsui.wizardmain.handlers;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.parsleyviewer.SelectMeasurementsViewer;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;

import com.sun.javafx.collections.SetListenerHelper;

import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.custom.SashForm;

public class SelectMeasurements extends WizardPage {
	public static final String CHECKBOX_UNCHECKED = "\u2610";
	public static final String CHECKBOX_CHECKED = "\u2611";
	private DataApplication dataApplication;
	// private Table table;
	// private final FormToolkit formToolkit = new
	// FormToolkit(Display.getDefault());
	// private CheckboxCellEditor cellEditor;

	public SelectMeasurements(MetricDescriptionSelectionWizardModel metricDescriptionSelectionWizardModel) {
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
		Composite container = new Composite(parent, SWT.FILL);
		FillLayout fill = new FillLayout();
		fill.marginHeight = 20;
		fill.marginWidth = 20;
		fill.spacing = 15;

        
        FillLayout layout = new FillLayout();
        container.setLayout(layout);
        //SelectMeasurementsViewer measurementsViewer = new SelectMeasurementsViewer(container, dataApplication);
        setControl(container);
    }
}