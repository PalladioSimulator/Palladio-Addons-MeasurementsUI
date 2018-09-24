package org.palladiosimulator.measurementsui.wizardpages;

import java.util.LinkedList;

import javax.inject.Inject;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TreeItem;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.wizard.viewer.EmptyMeasurementsTreeViewer;
import org.palladiosimulator.measurementsui.wizardmain.handlers.MeasuringPointsContentProvider;
import org.palladiosimulator.measurementsui.wizardmain.handlers.MeasuringPointsLabelProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

/**
 * This is the wizard page for the first step of the creation of a measuring point. It creates all
 * necessary ui elements like the page itself, tabs and tree viewers. It provides function to
 * dynamically show info texts, dynamically choose the next wizard page
 * 
 * @author Domas Mikalkinas
 *
 */

public class ChooseMeasuringpointWizardPage extends WizardPage {

    private EmptyMeasurementsTreeViewer emptyMpTreeViewer;
    private TreeViewer createTreeViewer;

    private MeasuringPointSelectionWizardModel selectionWizardModel;
    private boolean selected = false;
    private TabFolder tabFolder;
    @Inject
    private MDirtyable dirty;

    @Inject
    private ECommandService commandService;

    /**
     * Constructor for the second wizard page, sets structural features like the title, the name of
     * the page and the description
     * 
     * @param selectionWizardModel
     *            the needed wizard model
     */
    public ChooseMeasuringpointWizardPage(MeasuringPointSelectionWizardModel selectionWizardModel) {
        super("Second Page");
        this.selectionWizardModel = selectionWizardModel;
        setTitle(selectionWizardModel.getTitleText());
        setDescription(selectionWizardModel.getInfoText());
    }

    /**
     * 
     * Creates the second page of the wizard, which shows a tabbed table of measuringpoints, either
     * existing or those which can be created
     * 
     * @param parent
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        FillLayout layout = new FillLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        container.setLayout(layout);
        setControl(container);

        tabFolder = new TabFolder(container, SWT.SINGLE);

        createNewMeasuringPointTab(layout);

        createExistingMeasuringPointTab(layout);

        this.setPageComplete(true);
    }

    /**
     * Creates the tab for showing all models from which you can derive and create a new measuring
     * point
     * 
     * @param layout
     *            the layout for the tab
     * 
     */
    private void createNewMeasuringPointTab(FillLayout layout) {
        TabItem createMeasuringTabbedItem = new TabItem(tabFolder, SWT.NONE);
        createMeasuringTabbedItem.setText("Create new measuring point");

        Composite createMPcomposite = new Composite(tabFolder, SWT.SINGLE);
        createMPcomposite.setLayout(layout);
        MeasuringPointsContentProvider mp = new MeasuringPointsContentProvider(selectionWizardModel);
        ITreeContentProvider createContentProvider = mp;
        createTreeViewer = new TreeViewer(createMPcomposite, SWT.SINGLE);
        createTreeViewer.setContentProvider(createContentProvider);
        createTreeViewer.setInput(selectionWizardModel.getAllSecondPageObjects());
        createTreeViewer.setLabelProvider(new MeasuringPointsLabelProvider());
        //createTreeViewer.getTree().getItem(0).setExpanded(true);
        createTreeViewer.refresh();

        if (selectionWizardModel.getAllSecondPageObjects().length != 0) {
            LinkedList<?> temp = (LinkedList<?>) selectionWizardModel.getAllSecondPageObjects()[0];
            ISelection selection = new StructuredSelection(temp.get(0));
            createTreeViewer.setSelection(selection);
        }

        createTreeViewer.getTree().addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                TreeItem item = (TreeItem) e.item;

                showMessage(item);

            }
        });
        createMeasuringTabbedItem.setControl(createMPcomposite);
    }

    /**
     * Creates the tab which shows all existing measuring points.
     * 
     * @param layout
     *            the layout for the tab
     */
    private void createExistingMeasuringPointTab(FillLayout layout) {
        TabItem existingMeasuringTabbedItem = new TabItem(tabFolder, SWT.SINGLE);
        existingMeasuringTabbedItem.setText("Select existing measuring point");

        Composite existingMPcomposite = new Composite(tabFolder, SWT.SINGLE);
        existingMPcomposite.setLayout(layout);

        emptyMpTreeViewer = createEmptyMpTreeViewer(existingMPcomposite);

        existingMeasuringTabbedItem.setControl(existingMPcomposite);
    }

    /**
     * calls the tree viewer which is being build by parsley
     * 
     * @param parent
     *            the parent composite of the tree viewer
     * @return EmptyMPTreeViewer the created tree viewer
     */
    private EmptyMeasurementsTreeViewer createEmptyMpTreeViewer(Composite parent) {
        return new EmptyMeasurementsTreeViewer(parent, DataApplication.getInstance());
    }

    /**
     * overrides the getNextPage() method of the wizard page to allow a dynamic flow of wizard
     * pages, depending on the selected item
     */
    @Override
    public org.eclipse.jface.wizard.IWizardPage getNextPage() {
        boolean isNextPressed = "nextPressed"
                .equalsIgnoreCase(Thread.currentThread().getStackTrace()[2].getMethodName());
        if (isNextPressed) {
            boolean validatedNextPress = this.nextPressed();
            if (!validatedNextPress) {
                return this;
            }
        }
        if (selected) {
            AdditionalModelsToMeasuringpointWizardPage page = (AdditionalModelsToMeasuringpointWizardPage) super.getWizard()
                    .getPage("page2extra");
            page.loadData();
            return page;

        } else {
            return super.getNextPage();
        }

    }

    /**
     * shows an informative message on the top, depending on the chosen element.
     * 
     * @param item
     *            the selected item
     */
    public void showMessage(TreeItem item) {
        this.setErrorMessage(null);
        if (item.getData() instanceof ResourceEnvironment) {
            this.setMessage("This will create a resource environment measuring point.");
        } else if (item.getData() instanceof ResourceContainer) {
            this.setMessage("This will create a resource container measuring point.");
        } else if (item.getData() instanceof ProcessingResourceSpecification) {
            this.setMessage("This will create an active resource measuring point.");
        } else if (item.getData() instanceof AssemblyContext) {
            this.setMessage(
                    "This will create either an assembly operation or an assembly passive resource measuring point. In the next step you will need to specify further components.");
        } else if (item.getData() instanceof EntryLevelSystemCall) {
            this.setMessage("This will create an entry level system call measuring point.");
        } else if (item.getData() instanceof ExternalCallAction) {
            this.setMessage("This will create an external call action measuring point.");
        } else if (item.getData() instanceof LinkingResource) {
            this.setMessage("This will create a linking resource measuring point.");
        } else if (item.getData() instanceof SubSystem) {
            this.setMessage(
                    "This will create a subsystem measuring point. In the next step you will need to specify further components.");
        } else if (item.getData() instanceof org.palladiosimulator.pcm.system.System) {
            this.setMessage(
                    "This will create a system measuring point. In the next step you will need to specify further components.");
        } else if (item.getData() instanceof UsageScenario) {
            this.setMessage("This will create a usage scenario measuring point.");
        } else {
            this.setMessage("");
        }

    }

    /**
     * @see WizardDialog#nextPressed()
     * @see WizardPage#getNextPage()
     * @return boolean validates whether the next button is pressed or not
     */
    protected boolean nextPressed() {
        boolean validatedNextPressed = true;
        try {
            if (emptyMpTreeViewer.getViewer().getStructuredSelection()
                    .getFirstElement() instanceof MeasuringPointRepository
                    || createTreeViewer.getStructuredSelection().getFirstElement() instanceof LinkedList) {
                this.setErrorMessage("Choose a model for which a measuring point will be created.");
                validatedNextPressed = false;

            }
            if (tabFolder.getSelectionIndex() == 1) {
                if (emptyMpTreeViewer.getViewer().getStructuredSelection()
                        .getFirstElement() instanceof MeasuringPoint) {
                    selectionWizardModel.addMeasuringPointToMonitor(
                            (MeasuringPoint) emptyMpTreeViewer.getViewer().getStructuredSelection().getFirstElement());
                }
            } else {
                if (createTreeViewer.getStructuredSelection().getFirstElement() instanceof AssemblyContext
                        || createTreeViewer.getStructuredSelection().getFirstElement() instanceof SubSystem
                        || createTreeViewer.getStructuredSelection()
                                .getFirstElement() instanceof org.palladiosimulator.pcm.system.System) {
                    selectionWizardModel
                            .setCurrentSelection(createTreeViewer.getStructuredSelection().getFirstElement());
                    selected = true;
                } else {
                    selectionWizardModel
                            .setCurrentSelection(createTreeViewer.getStructuredSelection().getFirstElement());
                    selectionWizardModel
                            .createMeasuringPoint(createTreeViewer.getStructuredSelection().getFirstElement());
                    selected = false;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return validatedNextPressed;
    }

}
