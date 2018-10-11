package org.palladiosimulator.measurementsui.wizard.pages;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.palladiosimulator.measurementsui.wizard.handlers.MetricDescriptionSelectionCheckboxCellModifier;
import org.palladiosimulator.measurementsui.wizard.viewer.SelectedMetricDescriptionSelectionViewer;
import org.palladiosimulator.measurementsui.wizard.viewer.AvailableMetricDescriptionSelectionViewer;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * This class handles the GUI part of the third wizard page for selecting measurements.
 * 
 * @author Mehmet Ali Tepeli, Ba-Anh Vu
 * @author Florian Nieuwenhuizen: Added Infotext depending on selected Metric Description (Methods
 *         showMessage & updateMessageAccordingToSelectedMeasuringPoint)
 *
 */
public class MetricDescriptionSelectionWizardPage extends WizardPage {

    /**
     * Used for the self adapting column for unchecked value.
     */
    public static final String CHECKBOX_UNCHECKED = "\u2610";

    /**
     * Used for the self adapting column for checked value.
     */
    public static final String CHECKBOX_CHECKED = "\u2611";

    /**
     * This handles the internal model.
     */
    private MetricDescriptionSelectionWizardModel metricDescriptionSelectionWizardModel;

    /**
     * The constructor where basic properties are set, e. g. title, description etc.
     * 
     * @param metricDescriptionSelectionWizardModel
     *            This handles the internal model
     */
    public MetricDescriptionSelectionWizardPage(MetricDescriptionSelectionWizardModel metricDescriptionSelectionWizardModel) {
        super("wizardPage");
        setTitle("Select Measurements");
        setDescription("Select desired Measurements to be used with the Monitor.");
        this.metricDescriptionSelectionWizardModel = metricDescriptionSelectionWizardModel;

    }

    @Override
    public void createControl(Composite parent) {
        final Composite container = new Composite(parent, SWT.FILL);
        final GridLayout layoutParentContainer = new GridLayout();
        layoutParentContainer.numColumns = 3;
        layoutParentContainer.makeColumnsEqualWidth = false;
        container.setLayout(layoutParentContainer);

        final TableViewer tableViewerLeft = initLeftTableViewer(container);
        final Composite compositeMiddle = initMiddleSubComposite(container);
        final TableViewer tableViewerRight = initRightTableViewer(container);

        addButtons(tableViewerLeft, compositeMiddle, tableViewerRight);

        setPageComplete(true);
        setControl(container);
    }

    /**
     * Initializes the left TableViewer which contains available measurements.
     * 
     * @param container
     *            the parent container which contains the TableViewer
     * @return the TableViewer that is used for further user interactions
     */
    private TableViewer initLeftTableViewer(Composite container) {
        final Composite compositeLeft = new Composite(container, SWT.NONE);
        final FillLayout fillLayoutLeft = new FillLayout();
        final AvailableMetricDescriptionSelectionViewer selectMeasurementsViewerLeft = new AvailableMetricDescriptionSelectionViewer(compositeLeft,
                metricDescriptionSelectionWizardModel.getUnusedMetricsMonitor());
        final TableViewer tableViewerLeft = (TableViewer) selectMeasurementsViewerLeft.getViewer();
        tableViewerLeft.getTable().setHeaderBackground(new Color(Display.getCurrent(), 210, 210, 210));
        setLabelProvider(tableViewerLeft);

        updateMessageAccordingToSelectedMeasuringPoint(tableViewerLeft);
        compositeLeft.setLayout(fillLayoutLeft);
        compositeLeft.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        final LocalSelectionTransfer transfer = addDrag(tableViewerLeft);

        boolean dragAndDropfromLeftToRight = false;
        addDrop(tableViewerLeft, transfer, dragAndDropfromLeftToRight);

        return tableViewerLeft;
    }

    /**
     * Initializes the middle sub composite, where later buttons are added for moving selecting
     * measurements.
     * 
     * @param container
     *            the parent container
     * @return the Composite object
     */
    private Composite initMiddleSubComposite(Composite container) {
        final Composite compositeMiddle = new Composite(container, SWT.NONE);
        final FillLayout fillLayoutMiddle = new FillLayout();
        fillLayoutMiddle.type = SWT.CENTER;
        fillLayoutMiddle.marginWidth = 40;
        fillLayoutMiddle.spacing = 10;
        compositeMiddle.setLayout(fillLayoutMiddle);
        return compositeMiddle;
    }

    /**
     * Initializes the right TableViewer which contains selected measurements.
     * 
     * @param container
     *            the parent container which contains the TableViewer
     * @return the TableViewer that is used for further user interactions
     */
    private TableViewer initRightTableViewer(Composite container) {
        final Composite compositeRight = new Composite(container, SWT.NONE);
        final FillLayout fillLayoutRight = new FillLayout();
        final SelectedMetricDescriptionSelectionViewer emptySelectMeasurementsViewerRight = new SelectedMetricDescriptionSelectionViewer(
                compositeRight, metricDescriptionSelectionWizardModel.getUsedMetricsMonitor());
        final TableViewer tableViewerRight = (TableViewer) emptySelectMeasurementsViewerRight.getViewer();
        tableViewerRight.getTable().setHeaderBackground(new Color(Display.getCurrent(), 210, 210, 210));
        setLabelProvider(tableViewerRight);

        updateMessageAccordingToSelectedMeasuringPoint(tableViewerRight);
        compositeRight.setLayout(fillLayoutRight);
        compositeRight.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        tableViewerRight.getTable().getColumn(1).setWidth(125);

        setCellEditor(tableViewerRight);

        final LocalSelectionTransfer transfer = addDrag(tableViewerRight);

        boolean dragAndDropfromLeftToRight = true;
        addDrop(tableViewerRight, transfer, dragAndDropfromLeftToRight);

        return tableViewerRight;
    }

    /**
     * Sets the label provider for the given TableViewer
     * @param tableViewer the given TableViewer
     */
    private void setLabelProvider(final TableViewer tableViewer) {
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
                if (columnIndex == 1) {
                    if (measurementSpecification.isTriggersSelfAdaptations()) {
                        result = CHECKBOX_CHECKED;
                    } else {
                        result = CHECKBOX_UNCHECKED;
                    }
                    return result;
                } else {
                    result = measurementSpecification.getMetricDescription().getName();
                    return result;
                }
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

    /**
     * Sets the CellEditor for the given TableViewer
     * @param tableViewer the given TableViewer
     */
    private void setCellEditor(TableViewer tableViewer) {
        final CellEditor[] cellEditor = new CellEditor[2];
        cellEditor[0] = null;
        cellEditor[1] = new CheckboxCellEditor(tableViewer.getTable());
        tableViewer.setCellEditors(cellEditor);
        final String[] columnNames = { "Selected", "Self Adaptive" };
        tableViewer.setColumnProperties(columnNames);
        tableViewer.setCellModifier(
                new MetricDescriptionSelectionCheckboxCellModifier(tableViewer, metricDescriptionSelectionWizardModel));
    }

    /**
     * Adds the drag functionality for a given table viewer
     * @param tableViewer the given table viewer
     * @return the LocalSelectionTransfer object
     */
    private LocalSelectionTransfer addDrag(TableViewer tableViewer) {
        final LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
        final DragSourceAdapter dragAdapter = new DragSourceAdapter() {
            @Override
            public void dragSetData(final DragSourceEvent event) {
                transfer.setSelection(new StructuredSelection(tableViewer.getTable().getSelection()));
            }
        };
        final DragSource dragSource = new DragSource(tableViewer.getTable(), DND.DROP_MOVE | DND.DROP_COPY);
        dragSource.setTransfer(transfer);
        dragSource.addDragListener(dragAdapter);
        return transfer;
    }

    /**
     * Adds drop functionality to the given TableViewer
     * @param tableViewer the given TableViewer
     * @param transfer the given LocalSelectionTransfer object
     * @param dragAndDropfromLeftToRight indicates drag and drop direction, determines method to execute on drop
     */
    private void addDrop(final TableViewer tableViewer, final LocalSelectionTransfer transfer, 
            boolean dragAndDropfromLeftToRight) {
        final DropTargetAdapter dropAdapter = new DropTargetAdapter() {
            @Override
            public void drop(final DropTargetEvent event) {
                final StructuredSelection droppedSelection = (StructuredSelection) transfer.getSelection();
                for (Object currentElement : droppedSelection.toList()) {
                    TableItem tableItem = (TableItem) currentElement;
                    MeasurementSpecification measurement = (MeasurementSpecification) tableItem.getData();
                    if (dragAndDropfromLeftToRight) {
                        metricDescriptionSelectionWizardModel.addMeasurementSpecification(measurement);
                    } else {
                        metricDescriptionSelectionWizardModel.removeMeasurementSpecification(measurement);
                    }
                }
                getContainer().updateButtons();
            }
        };
        final DropTarget dropTarget = new DropTarget(tableViewer.getTable(), DND.DROP_MOVE | DND.DROP_COPY);
        dropTarget.setTransfer(transfer);
        dropTarget.addDropListener(dropAdapter);
    }

    /**
     * Add the buttons for moving measurements.
     * 
     * @param tableViewerLeft
     *            contains the available measurements
     * @param compositeMiddle
     *            the middle composite where the buttons are located
     * @param tableViewerRight
     *            contains the selected measurements
     */
    private void addButtons(TableViewer tableViewerLeft, Composite compositeMiddle, TableViewer tableViewerRight) {
        final Button rightOne = new Button(compositeMiddle, SWT.NONE);
        rightOne.setText("Add >");
        rightOne.addListener(SWT.Selection, e -> {
            IStructuredSelection selection = tableViewerLeft.getStructuredSelection();
            for (Object currentElement : selection.toList()) {
                MeasurementSpecification measurement = (MeasurementSpecification) currentElement;
                metricDescriptionSelectionWizardModel.addMeasurementSpecification(measurement);
            }
            getContainer().updateButtons();
        });

        final Button leftOne = new Button(compositeMiddle, SWT.NONE);
        leftOne.setText("< Remove");
        leftOne.addListener(SWT.Selection, e -> {
            IStructuredSelection selection = tableViewerRight.getStructuredSelection();
            for (Object currentElement : selection.toList()) {
                MeasurementSpecification measurement = (MeasurementSpecification) currentElement;
                metricDescriptionSelectionWizardModel.removeMeasurementSpecification(measurement);
            }
            getContainer().updateButtons();
        });

        addLabelForSpacingButtons(compositeMiddle);

        final Button rightAll = new Button(compositeMiddle, SWT.PUSH);
        rightAll.setText("Add All >>");
        rightAll.addListener(SWT.Selection, e -> {
            metricDescriptionSelectionWizardModel.addAllMetricDescriptions();
            getContainer().updateButtons();
        });

        final Button leftAll = new Button(compositeMiddle, SWT.NONE);
        leftAll.setText("<< Remove All");
        leftAll.addListener(SWT.Selection, e -> {
            metricDescriptionSelectionWizardModel.removeAllMetricDescriptions();
            getContainer().updateButtons();
        });

        addLabelForSpacingButtons(compositeMiddle);

        final Button addSuggestion = new Button(compositeMiddle, SWT.BOTTOM);
        addSuggestion.setText("Add Suggestions >");
        addSuggestion.addListener(SWT.Selection, e -> {
            metricDescriptionSelectionWizardModel.moveAllSuggested();
            getContainer().updateButtons();
        });
    }

    /**
     * Used for spacing the buttons in the middle composite
     * 
     * @param compositeMiddle
     *            the given middle composite
     */
    private void addLabelForSpacingButtons(Composite compositeMiddle) {
        final Label emptyLabelForSpacing = new Label(compositeMiddle, SWT.NONE);
        emptyLabelForSpacing.setText(" ");
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            metricDescriptionSelectionWizardModel
                    .initUnusedMetrics(metricDescriptionSelectionWizardModel.getUsedMetricsMonitor());
        }
        super.setVisible(visible);
    }

    /**
     * updates the Message for the page according to passed MeasurementSpecification.
     * 
     * @param aMSpec
     */
    private void showMessage(MeasurementSpecification aMSpec) {
        this.setMessage(metricDescriptionSelectionWizardModel.getTextualDescriptionForMetricDescription(aMSpec));
    }

    /**
     * SelectionListener for the tableViewers for updating the message for the page depending on
     * which Metric Description is selected in the table.
     * 
     * @param thisTableViewer
     */
    private void updateMessageAccordingToSelectedMeasuringPoint(TableViewer thisTableViewer) {
        thisTableViewer.getTable().addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (e.item != null) {
                    TableItem item = (TableItem) e.item;
                    if (item.getData() instanceof MeasurementSpecification) {
                        showMessage((MeasurementSpecification) item.getData());
                    }
                }

            }
        });
    }
    
    @Override
    public void performHelp() {
        Shell shell = new Shell(getShell());
        shell.setText("SimuLizar Usability Extension SDQ-Wiki");
        shell.setLayout(new GridLayout());
        shell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Browser browser = new Browser(shell, SWT.NONE);
        browser.setUrl("https://sdqweb.ipd.kit.edu/wiki/SimuLizar_Usability_Extension#Metric_Description_Selection_Page");
        browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        shell.open();
    }
}