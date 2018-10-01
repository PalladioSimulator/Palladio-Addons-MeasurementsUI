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
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.palladiosimulator.measurementsui.wizard.handlers.SelectMeasurementCheckboxCellModifier;
import org.palladiosimulator.measurementsui.wizard.viewer.EmptySelectMeasurementsViewer;
import org.palladiosimulator.measurementsui.wizard.viewer.SelectMeasurementsViewer;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Image;

/**
 * This class handles the GUI part of the third wizard page for selecting
 * measurements.
 * 
 * @author mehmet, Ba
 *
 */
public class SelectMeasurementsWizardPage extends WizardPage {

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
	 * @param metricDescriptionSelectionWizardModel This handles the internal model
	 */
	public SelectMeasurementsWizardPage(MetricDescriptionSelectionWizardModel metricDescriptionSelectionWizardModel) {
		super("wizardPage");
		setTitle("Select Measurements");
		setDescription("Select desired Measurements to be used with the Monitor.");
		this.metricDescriptionSelectionWizardModel = metricDescriptionSelectionWizardModel;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.FILL);
		GridLayout layoutParentContainer = new GridLayout();
		layoutParentContainer.numColumns = 3;
		layoutParentContainer.makeColumnsEqualWidth = false;
		container.setLayout(layoutParentContainer);
		
		TableViewer tableViewerLeft = initLeftTableViewer(container);
		Composite compositeMiddle = initMiddleSubComposite(container);
		TableViewer tableViewerRight = initRightTableViewer(container);

		addButtons(tableViewerLeft, compositeMiddle, tableViewerRight);
		
		setPageComplete(true);
		setControl(container);
	}

    /**
     * Initializes the left TableViewer which contains available measurements.
     * @param container the parent container which contains the TableViewer
     * @return the TableViewer that is used for further user interactions
     */
    private TableViewer initLeftTableViewer(Composite container) {
        Composite compositeLeft = new Composite(container, SWT.NONE);
		FillLayout fillLayoutLeft = new FillLayout();
		SelectMeasurementsViewer selectMeasurementsViewerLeft = new SelectMeasurementsViewer(compositeLeft,
				metricDescriptionSelectionWizardModel);
		TableViewer tableViewerLeft = (TableViewer) selectMeasurementsViewerLeft.getViewer();
		tableViewerLeft.setLabelProvider(new ITableLabelProvider() {
		    
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
		compositeLeft.setLayout(fillLayoutLeft);
		compositeLeft.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
        final LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
        final DragSourceAdapter dragAdapter = new DragSourceAdapter() {
            @Override
            public void dragSetData(final DragSourceEvent event) {
                transfer.setSelection(new StructuredSelection(tableViewerLeft.getTable().getSelection()));
            }
        };
        final DragSource dragSource = new DragSource(tableViewerLeft.getTable(), DND.DROP_MOVE | DND.DROP_COPY);
        dragSource.setTransfer(transfer);
        dragSource.addDragListener(dragAdapter);
        
        final DropTargetAdapter dropAdapter = new DropTargetAdapter() {
            @Override
            public void drop(final DropTargetEvent event) {
                final StructuredSelection droppedSelection = (StructuredSelection) transfer.getSelection();
                for (Object currentElement : droppedSelection.toList()) {
                    TableItem tableItem = (TableItem) currentElement;
                    MeasurementSpecification measurement = (MeasurementSpecification) tableItem.getData();
                    metricDescriptionSelectionWizardModel.removeMeasurementSpecification(measurement);
                }
                getContainer().updateButtons();
            }
        };
        final DropTarget dropTarget = new DropTarget(tableViewerLeft.getTable(), DND.DROP_MOVE | DND.DROP_COPY);
        dropTarget.setTransfer(transfer);
        dropTarget.addDropListener(dropAdapter);
		
        return tableViewerLeft;
    }

    /**
     * Initializes the middle sub composite, where later buttons are added for moving selecting measurements.
     * @param container the parent container
     * @return the Composite object
     */
    private Composite initMiddleSubComposite(Composite container) {
        Composite compositeMiddle = new Composite(container, SWT.NONE);
    	FillLayout fillLayoutMiddle = new FillLayout();
    	fillLayoutMiddle.type = SWT.CENTER;
    	fillLayoutMiddle.marginWidth = 40;
    	fillLayoutMiddle.spacing = 10;
    	compositeMiddle.setLayout(fillLayoutMiddle);
        return compositeMiddle;
    }

    /**
     * Initializes the right TableViewer which contains selected measurements.
     * @param container the parent container which contains the TableViewer
     * @return the TableViewer that is used for further user interactions
     */
    private TableViewer initRightTableViewer(Composite container) {
        Composite compositeRight = new Composite(container, SWT.NONE);
    	FillLayout fillLayoutRight = new FillLayout();
    	EmptySelectMeasurementsViewer emptySelectMeasurementsViewerRight = new EmptySelectMeasurementsViewer(compositeRight,
    			metricDescriptionSelectionWizardModel);
    	TableViewer tableViewerRight = (TableViewer) emptySelectMeasurementsViewerRight.getViewer();
    	tableViewerRight.setLabelProvider(new ITableLabelProvider() {
    
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
    	compositeRight.setLayout(fillLayoutRight);
    	compositeRight.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    	tableViewerRight.getTable().getColumn(1).setWidth(125);
    	
    	CellEditor[] cellEditor = new CellEditor[2];
        cellEditor[0] = null;
        cellEditor[1] = new CheckboxCellEditor(tableViewerRight.getTable());
        tableViewerRight.setCellEditors(cellEditor);
        String[] columnNames = { "Selected", "Self Adaptive" };
        tableViewerRight.setColumnProperties(columnNames);
        tableViewerRight.setCellModifier(new SelectMeasurementCheckboxCellModifier(
                tableViewerRight, metricDescriptionSelectionWizardModel));
        
        final LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
        final DragSourceAdapter dragAdapter = new DragSourceAdapter() {
            @Override
            public void dragSetData(final DragSourceEvent event) {
                transfer.setSelection(new StructuredSelection(tableViewerRight.getTable().getSelection()));
            }
        };
        final DragSource dragSource = new DragSource(tableViewerRight.getTable(), DND.DROP_MOVE | DND.DROP_COPY);
        dragSource.setTransfer(transfer);
        dragSource.addDragListener(dragAdapter);
        
        final DropTargetAdapter dropAdapter = new DropTargetAdapter() {
            @Override
            public void drop(final DropTargetEvent event) {
                final StructuredSelection droppedSelection = (StructuredSelection) transfer.getSelection();
                for (Object currentElement : droppedSelection.toList()) {
                    TableItem tableItem = (TableItem) currentElement;
                    MeasurementSpecification measurement = (MeasurementSpecification) tableItem.getData();
                    metricDescriptionSelectionWizardModel.addMeasurementSpecification(measurement);
                }
                getContainer().updateButtons();
            }
        };
        final DropTarget dropTarget = new DropTarget(tableViewerRight.getTable(), DND.DROP_MOVE | DND.DROP_COPY);
        dropTarget.setTransfer(transfer);
        dropTarget.addDropListener(dropAdapter);
    	
        return tableViewerRight;
    }

    /**
     * Add the buttons for moving measurements.
     * @param tableViewerLeft contains the available measurements
     * @param compositeMiddle the middle composite where the buttons are located
     * @param tableViewerRight contains the selected measurements
     */
    private void addButtons(TableViewer tableViewerLeft, Composite compositeMiddle, TableViewer tableViewerRight) {
        Button rightOne = new Button(compositeMiddle, SWT.NONE);
        rightOne.setText("Add >");
        rightOne.addListener(SWT.Selection, e -> {
            IStructuredSelection selection = tableViewerLeft.getStructuredSelection();
            for (Object currentElement : selection.toList()) {
                MeasurementSpecification measurement = (MeasurementSpecification) currentElement;
                metricDescriptionSelectionWizardModel.addMeasurementSpecification(measurement);
            }
            getContainer().updateButtons();
        });
		
		Button leftOne = new Button(compositeMiddle, SWT.NONE);
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
        
        Button rightAll = new Button(compositeMiddle, SWT.PUSH);
        rightAll.setText("Add All >>");
        rightAll.addListener(SWT.Selection, e -> {
            metricDescriptionSelectionWizardModel.addAllMetricDescriptions();
            getContainer().updateButtons();
        });

		Button leftAll = new Button(compositeMiddle, SWT.NONE);
		leftAll.setText("<< Remove All");
		leftAll.addListener(SWT.Selection, e -> {
			metricDescriptionSelectionWizardModel.removeAllMetricDescriptions();
			getContainer().updateButtons();
		});

		addLabelForSpacingButtons(compositeMiddle);
		
		Button addSuggestion = new Button(compositeMiddle, SWT.BOTTOM);
		addSuggestion.setText("Add Suggestions");
    }

    /**
     * Used for spacing the buttons in the middle composite
     * @param compositeMiddle the given middle composite
     */
    private void addLabelForSpacingButtons(Composite compositeMiddle) {
        Label emptyLabelForSpacing = new Label(compositeMiddle, SWT.NONE);
        emptyLabelForSpacing.setText(" ");
    }
    
	@Override
	public void setVisible(boolean visible) {
	    if (visible) {
	        metricDescriptionSelectionWizardModel.initUnusedMetrics(
	                metricDescriptionSelectionWizardModel.getUsedMetricsMonitor());
	    }
	    super.setVisible(visible);
	}
}