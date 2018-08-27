package org.palladiosimulator.simulizar.ui.measuringview.parts;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.internal.workbench.handlers.SaveHandler;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.palladiosimulator.measurementsui.init.DataApplication;
import org.palladiosimulator.measurementsui.wizard.MeasuringPointsWizard;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.EmptyMpTreeViewer;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.MonitorTreeViewer;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.MpTreeViewer;



/**
 * 
 * @author David Schuetz
 * Eclipse e4 view in which the user gets an overview of all existing monitors and measuringpoints in a selected monitorrepository.
 * 
 */
public class MeasuringpointView {
	
	private MpTreeViewer monitorTreeViewer;
	private DataApplication dataApplication;
	
	@Inject
	MDirtyable dirty;
	
	@Inject
	ECommandService commandService;
	
	@Inject
	EHandlerService handlerService;
	
	@Inject
	private ESelectionService selectionService;
	 
	/**
	 * Creates the meu items and controls for the simulizar measuring point view
	 * @param parent composite of the empty view
	 */
	@PostConstruct
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, true));
		initializeApplication();
		createRepositorySelectionCBox(parent);
		SashForm outerContainer = new SashForm(parent, SWT.FILL);
        outerContainer.setLayout(new GridLayout(1,true));
        outerContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        SashForm treeContainer = new SashForm(outerContainer, SWT.VERTICAL);
        treeContainer.setLayout(new GridLayout(1,true));
        
        Composite buttonContainer = new Composite(outerContainer, SWT.BORDER);
        buttonContainer.setLayout(new GridLayout(1, true));
        
        outerContainer.setWeights(new int[]{3, 1});
        
        Composite monitorContainer = createTreeComposite(treeContainer);
        Composite undefinedMeasuringContainer = createTreeComposite(treeContainer); 
        
        createViewButtons(buttonContainer); 
        

        monitorTreeViewer = createMonitorTreeViewer(monitorContainer);
        createEmptyMpTreeViewer(undefinedMeasuringContainer);
        
        handlerService.activateHandler("org.eclipse.ui.file.save", new SaveHandler());
	}
	
	/**
	 * Initializes the connecton to the data management and manipulation packages
	 */
	private void initializeApplication() {
		this.dataApplication = DataApplication.getInstance();
		dataApplication.loadData(0);	
	}

	
	/**
	 * Creates a tree view which shows all existing monitors and their childs in the selected projected
	 * @param parent composite where the tree view will be placed
	 * @return
	 */
	private MpTreeViewer createMonitorTreeViewer(Composite parent) {
		MpTreeViewer mpTreeViewer = new MonitorTreeViewer(parent,dirty,commandService, dataApplication);
		mpTreeViewer.addMouseListener();
		mpTreeViewer.addSelectionListener(selectionService);
		return mpTreeViewer;
	}
	
	/**
	 * Creates a tree view which shows all empty measuring points of all projects in the workspace
	 * @param parent composite where the tree view will be placed
	 * @return
	 */
	private MpTreeViewer createEmptyMpTreeViewer(Composite parent) {
		EmptyMpTreeViewer emptyMpTreeViewer = new EmptyMpTreeViewer(parent,dirty,commandService, dataApplication);
		emptyMpTreeViewer.addSelectionListener(selectionService);
		return emptyMpTreeViewer;
	}
	
	/**
	 * Creates the composite in which the tree view is later embedded
	 * @param parent composite where the tree composite will be placed
	 * @return
	 */
	private Composite createTreeComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		return composite;
	}
	
	/**
	 * Create all buttons of the view which provide different functionalities like 
	 * editing, deleting, assigning measuringpoints to monitor or creating a standard measuring point set
	 * @param buttonContainer
	 */
	private void createViewButtons(Composite buttonContainer) {
		Button newMpButton = new Button(buttonContainer, SWT.PUSH);
        newMpButton.setText("Add new Measuring Point");

		newMpButton.addListener(SWT.Selection, e -> {
			MeasuringPointsWizard test = new MeasuringPointsWizard();
			Shell parentShell = test.getShell();
			WizardDialog dialog = new WizardDialog(parentShell, test);
			dialog.open();
		});

        Button editMpButton = new Button(buttonContainer, SWT.PUSH);
        editMpButton.setText("Edit...");
        Button deleteMpButton = new Button(buttonContainer, SWT.PUSH);
        deleteMpButton.setText("Delete...");
        Button assignMonitorButton = new Button(buttonContainer, SWT.PUSH);
        assignMonitorButton.setText("Assign to Monitor");
        Button createStandardButton = new Button(buttonContainer, SWT.PUSH);
        createStandardButton.setText("Create Standard Set");     
        

	}
	
	/**
	 * Creates a combobox at the top of the view where the user can select the monitorrepository
	 * @param parent composite where the combobox is placed in
	 */
	private void createRepositorySelectionCBox(Composite parent) {
		Combo comboDropDown = new Combo(parent, SWT.DROP_DOWN);
		comboDropDown.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
        List<IProject> allProjects = dataApplication.getDataGathering().getAllProjectAirdfiles();     
        for(IProject project : allProjects) {
        	comboDropDown.add(project.toString());
        }
        comboDropDown.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = comboDropDown.getSelectionIndex();
				dataApplication.loadData(selectionIndex);
				monitorTreeViewer.update();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				comboDropDown.select(0);

				
			}
		});
        comboDropDown.select(0);
    }

	
	/**
	 * Saves the current data in the tree view
	 * @param dirty states whether there were changes made
	 * @throws IOException
	 */
	@Persist
	public void save(MDirtyable dirty) throws IOException {
		monitorTreeViewer.save(dirty);
	}
}
	
	
