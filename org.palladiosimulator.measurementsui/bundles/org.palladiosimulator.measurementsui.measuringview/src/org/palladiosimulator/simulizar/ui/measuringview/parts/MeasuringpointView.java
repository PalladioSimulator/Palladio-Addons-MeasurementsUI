package org.palladiosimulator.simulizar.ui.measuringview.parts;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.internal.workbench.handlers.SaveHandler;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.ecore.EObject;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.palladiosimulator.measurementsui.abstractviewer.MpTreeViewer;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.parsleyviewer.EmptyMpTreeViewer;
import org.palladiosimulator.measurementsui.parsleyviewer.MonitorTreeViewer;
import org.palladiosimulator.measurementsui.wizardmain.MeasuringPointsWizard;

/**
 * Eclipse e4 view in which the user gets an overview of all existing monitors
 * and measuringpoints in a selected monitorrepository.
 * 
 * @author David Schuetz
 * 
 */
public class MeasuringpointView {

	private MpTreeViewer monitorTreeViewer;
	private MpTreeViewer measuringTreeViewer;
	private Combo projectsComboDropDown;
	private DataApplication dataApplication;

	@Inject
	private MDirtyable dirty;

	@Inject
	private ECommandService commandService;

	@Inject
	private EHandlerService handlerService;

	@Inject
	private ESelectionService selectionService;

	/**
	 * Creates the meu items and controls for the simulizar measuring point view
	 * 
	 * @param parent composite of the empty view
	 */
	@PostConstruct
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, true));
		initializeApplication();
		createWorkspaceListener();
		createProjectsSelectionComboBox(parent);
		SashForm outerContainer = new SashForm(parent, SWT.FILL);
		outerContainer.setLayout(new GridLayout(1, true));
		outerContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		SashForm treeContainer = new SashForm(outerContainer, SWT.VERTICAL);
		treeContainer.setLayout(new GridLayout(1, true));

		Composite buttonContainer = new Composite(outerContainer, SWT.BORDER);
		buttonContainer.setLayout(new GridLayout(1, true));

		outerContainer.setWeights(new int[] { 3, 1 });

		Composite monitorContainer = createTreeComposite(treeContainer);
		Composite undefinedMeasuringContainer = createTreeComposite(treeContainer);

		createViewButtons(buttonContainer);
		
		
		

		monitorTreeViewer = createMonitorTreeViewer(monitorContainer);
		measuringTreeViewer = createEmptyMpTreeViewer(undefinedMeasuringContainer);

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
	 * Adds a changeListener to the eclispe workspace
	 * to listen to changes in it and update
	 * our GUI accordingly
	 */
	private void createWorkspaceListener() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IResourceChangeListener listener = new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				
				if(event.getType() != IResourceChangeEvent.POST_CHANGE) {
					return;
				}
							
				Display.getDefault().asyncExec(new Runnable() {

					   public void run() {

						   updateMeasuringPointView();
					   }

					  });
			}	
		};
		workspace.addResourceChangeListener(listener, 1);

	}
	

	

	/**
	 * Creates a tree view which shows all existing monitors and their childs in the
	 * selected projected
	 * 
	 * @param parent composite where the tree view will be placed
	 * @return TreeViewer which includes all existing monitors
	 */
	private MpTreeViewer createMonitorTreeViewer(Composite parent) {
		MpTreeViewer mpTreeViewer = new MonitorTreeViewer(parent, dirty, commandService, dataApplication);
		mpTreeViewer.addMouseListener();
		mpTreeViewer.addSelectionListener(selectionService);
		return mpTreeViewer;
	}

	/**
	 * Creates a tree view which shows all empty measuring points of all projects in
	 * the workspace
	 * 
	 * @param parent composite where the tree view will be placed
	 * @return TreeViewer which includes all measuring points without a monitor
	 */
	private MpTreeViewer createEmptyMpTreeViewer(Composite parent) {
		EmptyMpTreeViewer emptyMpTreeViewer = new EmptyMpTreeViewer(parent, dirty, commandService, dataApplication);
		emptyMpTreeViewer.addSelectionListener(selectionService);
		return emptyMpTreeViewer;
	}

	/**
	 * Creates the composite in which the tree view is later embedded
	 * 
	 * @param parent composite where the tree composite will be placed
	 * @return Composite where the TreeViewers can be placed
	 */
	private Composite createTreeComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		return composite;
	}

	/**
	 * Create all buttons of the view which provide different functionalities like
	 * editing, deleting, assigning measuringpoints to monitor or creating a
	 * standard measuring point set
	 * 
	 * @param buttonContainer composite where the buttons will be placed
	 */
	private void createViewButtons(Composite buttonContainer) {
		createNewMeasuringpointButton(buttonContainer);
		createDeleteButton(buttonContainer);

		Button editMpButton = new Button(buttonContainer, SWT.PUSH);
		editMpButton.setText("Edit...");
		
		Button assignMonitorButton = new Button(buttonContainer, SWT.PUSH);
		assignMonitorButton.setText("Assign to Monitor");
		Button createStandardButton = new Button(buttonContainer, SWT.PUSH);
		createStandardButton.setText("Create Standard Set");

	}
	
	/**
	 * Creates a Button which opens the Wizard in order to create a measuring point 
	 * @param parent composite where the button will be placed
	 */
	private void createNewMeasuringpointButton(Composite parent) {
		Button newMpButton = new Button(parent, SWT.PUSH);
		newMpButton.setText("Add new Measuring Point");

		newMpButton.addListener(SWT.Selection, e -> {
			MeasuringPointsWizard test = new MeasuringPointsWizard();
			Shell parentShell = test.getShell();
			WizardDialog dialog = new WizardDialog(parentShell, test);
			dialog.open();
		});
	}
	
	/**
	 * Creates a Button which deletes selected EObjects
	 * @param parent composite where the button will be placed
	 */
	private void createDeleteButton(Composite parent) {
		Button deleteMpButton = new Button(parent, SWT.PUSH);
		deleteMpButton.setText("Delete...");
		
		deleteMpButton.addListener(SWT.Selection, e -> {
			ResourceEditor resourceEditor = new ResourceEditorImpl();
			Object selection = selectionService.getSelection();
			if (selection instanceof EObject) {
				resourceEditor.deleteResource((EObject) selection);
				measuringTreeViewer.update();
			}
		});
	}
	
	/**
	 * Updates the Monitor and Measuringpoint Tree Viewer
	 */
	private void updateTreeViewer() {
		monitorTreeViewer.update();
		measuringTreeViewer.update();
	}

	/**
	 * Creates a combobox at the top of the view where the user can select the
	 * project
	 * 
	 * @param parent composite where the combobox is placed in
	 */
	private void createProjectsSelectionComboBox(Composite parent) {
		projectsComboDropDown = new Combo(parent, SWT.DROP_DOWN);
		projectsComboDropDown.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		updateProjectComboBo();
		projectsComboDropDown.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = projectsComboDropDown.getSelectionIndex();
				dataApplication.loadData(selectionIndex);
				updateTreeViewer();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				projectsComboDropDown.select(0);

			}
		});
		projectsComboDropDown.select(0);
	}
	
	/**
	 * Adds every project in the workspace, that has an
	 * .aird file to the projectsComboBox
	 */
	private void updateProjectComboBo() {
		
		int selectionIndex = -1;
		projectsComboDropDown.removeAll();
		List<IProject> allProjects = dataApplication.getDataGathering().getAllProjectAirdfiles();
		for (int i =0; i< allProjects.size();i++) {
			IProject project = allProjects.get(i);
			
			if(project.equals(dataApplication.getProject())){
				selectionIndex = i;
			}
			
			projectsComboDropDown.add(project.toString());
		}
		projectsComboDropDown.select(selectionIndex);
		
	}
	
	/**
	 * Reloads the dashboard view and updates it, if
	 * something changed
	 */
	private void updateMeasuringPointView() {
		updateProjectComboBo();
		monitorTreeViewer.update();
		measuringTreeViewer.update();
	}

	/**
	 * Saves the current data in the tree view
	 * 
	 * @param dirty states whether there were changes made
	 * @throws IOException
	 */
	@Persist
	public void save(MDirtyable dirty) throws IOException {
		monitorTreeViewer.save(dirty);
	}
}
