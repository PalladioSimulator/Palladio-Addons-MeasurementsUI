package org.palladiosimulator.simulizar.ui.measurementsdashboard.parts;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.internal.workbench.handlers.SaveHandler;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.measurementsui.abstractviewer.MeasurementsTreeViewer;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.wizard.main.MeasurementsWizard;
import org.palladiosimulator.measurementsui.wizard.main.StandardSetWizard;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModelType;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.monitorrepository.ProcessingType;
import org.palladiosimulator.simulizar.ui.listeners.WorkspaceListener;
import org.palladiosimulator.simulizar.ui.measurementsdashboard.filter.MeasurementsFilter;
import org.palladiosimulator.simulizar.ui.measurementsdashboard.viewer.EmptyMeasuringPointsTreeViewer;
import org.palladiosimulator.simulizar.ui.measurementsdashboard.viewer.MonitorTreeViewer;

/**
 * Eclipse e4 view which gives the user an overview of all existing Monitors and MeasuringPoints in
 * a selected MonitorRepository.
 * 
 * @author David Schuetz
 * 
 */
public class MeasurementsDashboardView {

    private MeasurementsTreeViewer monitorTreeViewer;
    private MeasurementsTreeViewer measuringTreeViewer;
    private Combo projectsComboDropDown;
    private Combo monitorRepositoriesComboDropDown;
    private DataApplication dataApplication;
    private Button deleteButton;
    private Button editButton;
    private MeasurementsFilter filter;
    private Text searchText;
    
    private static final String EDITTEXT_GRAYEDOUT = "Edit...";
    private static final String DELETETEXT_GRAYEDOUT = "Delete...";
    private static final String EDITTEXT_MONITOR = "Edit Monitor";
    private static final String DELETETEXT_MONITOR = "Delete Monitor";
    private static final String EDITTEXT_MEASUREMENT = "Edit Measurement";
    private static final String DELETETEXT_MEASUREMENT = "Delete Measurement";
    private static final String EDITTEXT_PROCESSINGTYPE = "Edit ProcessingType";
    private static final String INFOTEXT_NO_PCM_MODELS = "You need to create your"
            + " palladio core models before you can create measuring points."
            + " They are used to model your systems architecture and chrakteristics."
            + " Use the buttons on the toolbar on top to start creating.";
    

    @Inject
    private MDirtyable dirty;

    @Inject
    private ECommandService commandService;

    @Inject
    private EHandlerService handlerService;

    @Inject
    private ESelectionService selectionService;

    /**
     * Creates the menu items and controls for the simulizar measuring point view
     * 
     * @param parent
     *            the composite of the empty view
     */
    @PostConstruct
    public void createPartControl(Composite parent) {
        parent.setLayout(new GridLayout(1, true));
        initializeApplication();
        createWorkspaceListener();
        createSelectionComboBoxes(parent);

        SashForm outerContainer = new SashForm(parent, SWT.FILL);
        outerContainer.setLayout(new GridLayout(1, true));
        outerContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Composite leftContainer = new Composite(outerContainer, SWT.VERTICAL);
        leftContainer.setLayout(new GridLayout(1, false));
        leftContainer.setBackground(new Color(Display.getCurrent(), 255, 255, 255));

        createFilterGadgets(leftContainer);

        Composite buttonContainer = new Composite(outerContainer, SWT.BORDER);
        buttonContainer.setLayout(new GridLayout(1, true));

        outerContainer.setWeights(new int[] { 3, 1 });

        SashForm treeContainer = new SashForm(leftContainer, SWT.VERTICAL);
        treeContainer.setLayout(new GridLayout(1, false));
        treeContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        treeContainer.setBackground(new Color(Display.getCurrent(), 255, 255, 255));

        Composite monitorContainer = createTreeComposite(treeContainer);
        Composite undefinedMeasuringContainer = createTreeComposite(treeContainer);
        treeContainer.setWeights(new int[] { 10, 5 });
        createViewButtons(buttonContainer);

        monitorTreeViewer = createMonitorTreeViewer(monitorContainer);
        measuringTreeViewer = createEmptyMeasuringPointsTreeViewer(undefinedMeasuringContainer);

        handlerService.activateHandler("org.eclipse.ui.file.save", new SaveHandler());
    }

    /**
     * Initializes the connecton to the data management and manipulation package
     */
    private void initializeApplication() {
        this.dataApplication = DataApplication.getInstance();
        dataApplication.loadData(dataApplication.getDataGathering().getAllProjectAirdfiles().get(0),0);
    }

    /**
     * Adds a changeListener to the eclipse workspace to listen to changes in it and update our GUI
     * accordingly
     */
    private void createWorkspaceListener() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();

        workspace.addResourceChangeListener(new WorkspaceListener(this), 1);
    }

    /**
     * Creates a tree view which shows all existing monitors and their childs in the selected
     * projected
     * 
     * @param parent
     *            a composite where the tree view will be placed
     * @return TreeViewer which includes all existing monitors
     */
    private MeasurementsTreeViewer createMonitorTreeViewer(Composite parent) {
        MeasurementsTreeViewer measurementsTreeViewer = new MonitorTreeViewer(parent, dirty, commandService,
                dataApplication);
        measurementsTreeViewer.addMouseListener();
        measurementsTreeViewer.getViewer().addFilter(filter);
        addSelectionListener(measurementsTreeViewer);

        return measurementsTreeViewer;
    }

    /**
     * Creates a tree view which shows all empty measuring points of all projects in the workspace
     * 
     * @param parent
     *            a composite where the tree view will be placed
     * @return TreeViewer which includes all measuring points without a monitor
     */
    private MeasurementsTreeViewer createEmptyMeasuringPointsTreeViewer(Composite parent) {
        EmptyMeasuringPointsTreeViewer emptyMeasuringPointsTreeViewer = new EmptyMeasuringPointsTreeViewer(parent,
                dirty, commandService, dataApplication);
        emptyMeasuringPointsTreeViewer.getViewer().addFilter(filter);
        addSelectionListener(emptyMeasuringPointsTreeViewer);
        return emptyMeasuringPointsTreeViewer;
    }

    /**
     * Adds a SelectionListener which enables/disables Buttons based on which TreeItem is selected
     * 
     * @param treeViewer
     *            a viewer where the SelectionListener will be added to.
     */
    private void addSelectionListener(MeasurementsTreeViewer treeViewer) {
        treeViewer.getViewer().addSelectionChangedListener(event -> {
            IStructuredSelection selection = (IStructuredSelection) event.getSelection();
            selectionService.setSelection(selection.size() == 1 ? selection.getFirstElement() : selection.toArray());

            Object selectionObject = selection.getFirstElement();

            editButton.setText(EDITTEXT_GRAYEDOUT);
            deleteButton.setText(DELETETEXT_GRAYEDOUT);
            
            if (selectionObject == null || selectionObject instanceof MonitorRepository
                    || selectionObject instanceof MeasuringPointRepository) {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            } else {
                editButton.setEnabled(true);
                if (selectionObject instanceof ProcessingType) {
                    deleteButton.setEnabled(false);
                    editButton.setText(EDITTEXT_PROCESSINGTYPE);
                } else {
                    deleteButton.setEnabled(true);
                    if (selectionObject instanceof Monitor) {
                        editButton.setText(EDITTEXT_MONITOR);
                        deleteButton.setText(DELETETEXT_MONITOR);
                    } else if (selectionObject instanceof MeasurementSpecification) {
                        editButton.setText(EDITTEXT_MEASUREMENT);
                        deleteButton.setText(DELETETEXT_MEASUREMENT);
                    }
                }
            }
        });
    }

    /**
     * Creates the composite in which the tree view is later embedded
     * 
     * @param parent
     *            a composite where the tree composite will be placed
     * @return Composite where the TreeViewers can be placed
     */
    private Composite createTreeComposite(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new FillLayout());
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        return composite;
    }

    /**
     * Creates all gadgets which are used to filter the tree viewers.
     * 
     * @param parent
     */
    private void createFilterGadgets(Composite parent) {
        Composite filterContainer = new Composite(parent, SWT.NONE);
        filterContainer.setLayout(new GridLayout(6, false));
        filterContainer.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
        filterContainer.setBackground(new Color(Display.getCurrent(), 255, 255, 255));

        filter = new MeasurementsFilter();
        final Label filterLabel = new Label(filterContainer, SWT.NONE);
        filterLabel.setText("Filter:");

        searchText = new Text(filterContainer, SWT.BORDER | SWT.SEARCH);
        searchText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        searchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTreeViewer();
            }
        });

        final Label filterActiveCheckboxLabel = new Label(filterContainer, SWT.NONE);
        filterActiveCheckboxLabel.setText("Active\nMonitors");
        final Button filterActiveCheckbox = new Button(filterContainer, SWT.CHECK);
        filterActiveCheckbox.setSelection(true);
        filterActiveCheckbox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                filter.setFilterActiveMonitors(!filterActiveCheckbox.getSelection());
                filterTreeViewer();
            }
        });

        final Label filterInactiveCheckboxLabel = new Label(filterContainer, SWT.NONE);
        filterInactiveCheckboxLabel.setText("Inactive\nMonitors");
        final Button filterInactiveCheckbox = new Button(filterContainer, SWT.CHECK);
        filterInactiveCheckbox.setSelection(true);
        filterInactiveCheckbox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                filter.setFilterInactiveMonitors(!filterInactiveCheckbox.getSelection());
                filterTreeViewer();
            }
        });
    }

    /**
     * filter the monitorTreeViewer and measuringTreeViewer according to the search text entered in the textbox.
     * If there is going to be no item shown in a tree all items will be made visible again.
     */
    private void filterTreeViewer() {
        TreeViewer treeViewer = (TreeViewer) monitorTreeViewer.getViewer();
        treeViewer.collapseAll();
        treeViewer.expandToLevel(2);
        filter.setSearchText(searchText.getText());
        monitorTreeViewer.getViewer().refresh();
        measuringTreeViewer.getViewer().refresh();
        filter.setSearchText("");
        TreeViewer measuringTree = (TreeViewer) measuringTreeViewer.getViewer();
        if (measuringTree.getTree().getTopItem().getItems().length <= 1) {
            measuringTreeViewer.getViewer().refresh();
        }
        TreeViewer monitorTree = (TreeViewer) monitorTreeViewer.getViewer();
        if (monitorTree.getTree().getTopItem().getItems().length <= 1) {
            monitorTreeViewer.getViewer().refresh();
        }
    }

    /**
     * Create all buttons of the view which provide different functionalities like editing,
     * deleting, assigning measuringpoints to monitor or creating a standard measuring point set
     * 
     * @param buttonContainer
     *            a composite where the buttons will be placed
     */
    private void createViewButtons(Composite buttonContainer) {
        createNewMeasuringpointButton(buttonContainer);
        createDeleteButton(buttonContainer);
        createEditButton(buttonContainer);

//        Button assignMonitorButton = new Button(buttonContainer, SWT.PUSH);
//        assignMonitorButton.setText("Assign to Monitor");
//        assignMonitorButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        Button createStandardButton = new Button(buttonContainer, SWT.PUSH);
        createStandardButton.setText("Create Standard Set");
        createStandardButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        createStandardButton.addListener(SWT.Selection, e -> {
            StandardSetWizard wizard = new StandardSetWizard();
            Shell parentShell = wizard.getShell();
            WizardDialog dialog = new WizardDialog(parentShell, wizard);
            dialog.setPageSize(720, 400);
            dialog.setMinimumPageSize(720, 400);
            dialog.open();
        });

    }

    /**
     * Creates a Button which opens the Wizard in order to create a measuring point
     * 
     * @param parent
     *            a composite where the button will be placed
     */
    private void createNewMeasuringpointButton(Composite parent) {
        Button newMpButton = new Button(parent, SWT.PUSH);
        newMpButton.setText("Add new Measuring Point");
        newMpButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        newMpButton.addListener(SWT.Selection, e -> {
            if(!dataApplication.getModelAccessor().modelsExist()) {
                MessageDialog.openInformation(null, "No PCM Models found Info", INFOTEXT_NO_PCM_MODELS);
            } else {
                checkDirtyState();
                MeasurementsWizard wizard = new MeasurementsWizard();
                Shell parentShell = wizard.getShell();
                WizardDialog dialog = new WizardDialog(parentShell, wizard);
                dialog.setPageSize(720, 400);
                dialog.setMinimumPageSize(720, 400);
                dialog.open();

            }
        });
    }

    /**
     * Creates a Button which deletes selected EObjects
     * 
     * @param parent
     *            a composite where the button will be placed
     */
    private void createDeleteButton(Composite parent) {
        deleteButton = new Button(parent, SWT.PUSH);
        deleteButton.setText(DELETETEXT_GRAYEDOUT);
        deleteButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        deleteButton.addListener(SWT.Selection, e -> {
            ResourceEditor resourceEditor = ResourceEditorImpl.getInstance();
            Object selection = selectionService.getSelection();
            if (selection instanceof EObject) {
                resourceEditor.deleteResource((EObject) selection);
            }
        });
    }

    /**
     * Creates a Button which edits selected EObjects
     * 
     * @param parent
     *            a composite where the button will be placed
     */
    private void createEditButton(Composite parent) {
        editButton = new Button(parent, SWT.PUSH);
        editButton.setText(EDITTEXT_GRAYEDOUT);
        editButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        editButton.addListener(SWT.Selection, e -> {
            checkDirtyState();
            MeasurementsWizard wizard;
            Object selection = selectionService.getSelection();
            ITreeContentProvider provider = (ITreeContentProvider) monitorTreeViewer.getViewer().getContentProvider();
            if (selection instanceof Monitor) {
                wizard = new MeasurementsWizard(WizardModelType.MONITOR_CREATION, (Monitor) selection);
            } else if (selection instanceof MeasuringPoint) {
                wizard = new MeasurementsWizard(WizardModelType.MEASURING_POINT_SELECTION,
                        (Monitor) provider.getParent(selection));
            } else if (selection instanceof MeasurementSpecification) {
                wizard = new MeasurementsWizard(WizardModelType.METRIC_DESCRIPTION_SELECTION,
                        (Monitor) provider.getParent(selection));
            } else if (selection instanceof ProcessingType) {
                wizard = new MeasurementsWizard(WizardModelType.PROCESSING_TYPE,
                        (Monitor) provider.getParent(((ProcessingType) selection).getMeasurementSpecification()));
            } else {
                wizard = new MeasurementsWizard(WizardModelType.MONITOR_CREATION);
            }

            Shell parentShell = wizard.getShell();
            WizardDialog dialog = new WizardDialog(parentShell, wizard);
            dialog.setPageSize(720, 400);
            dialog.setMinimumPageSize(720, 400);
            dialog.open();

        });
    }

    /**
     * Checks whether the state of the view is dirty and
     * asks the user to save before continuing
     * @return boolean
     */
    private void checkDirtyState() {
        
        if(dirty.isDirty()) {
            boolean result = MessageDialog.openQuestion(null, "", "Do you want to save your changes?");
            if (result){
                try {
                    save(dirty);
                } catch (IOException e) {
                    e.printStackTrace();
                }
               } else {
                   undoChanges();
               }
        }   
    }

    /**
     * Creates a combobox at the top of the view where the user can select the project
     * 
     * @param parent
     *            a composite where the combobox is placed in
     */
    private void createProjectsSelectionComboBox(Composite parent) {
        projectsComboDropDown = new Combo(parent, SWT.DROP_DOWN);
        projectsComboDropDown.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
        updateProjectComboBox();
        projectsComboDropDown.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                int selectionIndex = projectsComboDropDown.getSelectionIndex();
                dataApplication.loadData(dataApplication.getDataGathering().getAllProjectAirdfiles().get(selectionIndex),0);
                updateTreeViewer();
                updateMonitorRepositoryComboBox();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                projectsComboDropDown.select(0);

            }
        });
        projectsComboDropDown.select(0);
    }
    
    /**
     * Creates the ComboBoxes for project and monitorRepository
     * at the top of the view
     * @param parent
     * 				a composite where the comboboxes are palced in
     */
    private void createSelectionComboBoxes(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout(2, false));
        container.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        createProjectsSelectionComboBox(container);
        createMonitorRepositorySelectionComboBox(container);
    }
    
    /**
     * Creates a ComboBox at the top of the view where
     * user can select a monitorRepository
     * @param parent 
     * 				a composite where the combobox is placed in
     */
    private void createMonitorRepositorySelectionComboBox(Composite parent) {     
            monitorRepositoriesComboDropDown = new Combo(parent, SWT.DROP_DOWN);
            monitorRepositoriesComboDropDown.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
            updateMonitorRepositoryComboBox();
            monitorRepositoriesComboDropDown.addSelectionListener(new SelectionListener() {
                 @Override
                public void widgetSelected(SelectionEvent e) {
                    int selectionIndex = monitorRepositoriesComboDropDown.getSelectionIndex();
                    dataApplication.updateMonitorRepository(selectionIndex);
                    updateTreeViewer();
                }
                 @Override
                public void widgetDefaultSelected(SelectionEvent e) {
                    monitorRepositoriesComboDropDown.select(0);
                 }
            });
            monitorRepositoriesComboDropDown.select(0);         
        
    }
    
    /**
     * If more then 1 monitorRepository exists in the current project
     * this updates the MonitorRepositoryComboBox
     */
    public void updateMonitorRepositoryComboBox() {
       
            monitorRepositoriesComboDropDown.setEnabled(true);
            int selectionIndex = 0;
            monitorRepositoriesComboDropDown.removeAll();
            List<MonitorRepository> allMonitorRepositories = dataApplication.getModelAccessor().getMonitorRepository();
            for (int i = 0; i < allMonitorRepositories.size(); i++) {
                MonitorRepository monitorRepository = allMonitorRepositories.get(i);
                 if (monitorRepository.equals(dataApplication.getMonitorRepository())) {
                    selectionIndex = i;
                }
                 monitorRepositoriesComboDropDown.add(i+1 + ". " +monitorRepository.getEntityName());
            }
            monitorRepositoriesComboDropDown.select(selectionIndex);
         
            if (dataApplication.getModelAccessor().getMonitorRepository().size()<=1) {
                monitorRepositoriesComboDropDown.setEnabled(false);
            }
            
        
    }

    /**
     * Adds every project in the workspace that has an .aird file to the projectsComboBox
     */
    public void updateProjectComboBox() {

        int selectionIndex = 0;
        projectsComboDropDown.removeAll();
        List<IProject> allProjects = dataApplication.getDataGathering().getAllProjectAirdfiles();
        for (int i = 0; i < allProjects.size(); i++) {
            IProject project = allProjects.get(i);

            if (project.equals(dataApplication.getProject())) {
                selectionIndex = i;
            }

            projectsComboDropDown.add(project.toString());
        }
        projectsComboDropDown.select(selectionIndex);

    }
    
    /**
     * Updates the dashboard by reloading the data
     * and refreshing the views
     * @param project to update
     */
    public void updateMeasurementsDashboardView(IProject project) {
    	dataApplication.loadData(project, monitorRepositoriesComboDropDown.getSelectionIndex());
    	updateTreeViewer();
    }
  
    /**
     * Undos all changes previously done on the dashboard
     */
    private void undoChanges() {
        monitorTreeViewer.undo();
        measuringTreeViewer.undo();
    }

    /**
     * Updates the Monitor and Measuringpoint Tree Viewer
     */
    private void updateTreeViewer() {
        monitorTreeViewer.update();
        measuringTreeViewer.update();
    }
    
    /**
     * Returns the instance of dataApplication
     * @return dataApplication instance
     */
    public DataApplication getDataApplication() {
        return dataApplication;
    }

    /**
     * Saves the current data in the tree view
     * 
     * @param dirty
     *            the dirty state which indicates whether there were changes made
     * @throws IOException
     *             if the save command fails
     */
    @Persist
    public void save(MDirtyable dirty) throws IOException {
        monitorTreeViewer.save(dirty);
        measuringTreeViewer.save(dirty);
    }

 
}
