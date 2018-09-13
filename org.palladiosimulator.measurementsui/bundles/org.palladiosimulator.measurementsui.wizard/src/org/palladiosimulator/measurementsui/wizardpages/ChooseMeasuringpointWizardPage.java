package org.palladiosimulator.measurementsui.wizardpages;

import javax.inject.Inject;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.measurementsui.abstractviewer.MpTreeViewer;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.parsleyviewer.EmptyMpTreeViewer;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.measurementsui.wizardmain.handlers.MeasuringPointsContentProvider;
import org.palladiosimulator.measurementsui.wizardmain.handlers.MeasuringPointsLabelProvider;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.subsystem.SubSystem;

/**
 * @author Domas Mikalkinas
 *
 */

public class ChooseMeasuringpointWizardPage extends WizardPage {

    MpTreeViewer emptyMpTreeViewer;
    TreeViewer createTreeViewer;
    MeasuringPointSelectionWizardModel selectionWizardModel = MeasuringPointSelectionWizardModel.getInstance();
    public static Object currentSelection;
    boolean selected = false;
    ITreeContentProvider createContentProvider;
    TabFolder tabFolder;
    @Inject
    MDirtyable dirty;

    @Inject
    ECommandService commandService;

    /**
     * Constructor for the second wizard page, sets structural features like the title, the name of
     * the page and the description
     */
    public ChooseMeasuringpointWizardPage() {
        super("Second Page");
        setTitle("Select Measuring Point");
        setDescription("description");
    }

    public ChooseMeasuringpointWizardPage(MeasuringPointSelectionWizardModel measuringPointWizardModel) {
        super("Second Page");
        setTitle(measuringPointWizardModel.getTitleText());
        setDescription(measuringPointWizardModel.getInfoText());
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
        currentSelection = new Object();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        container.setLayout(layout);
        setControl(container);

        tabFolder = new TabFolder(container, SWT.SINGLE);

        TabItem existingMeasuringTabbedItem = new TabItem(tabFolder, SWT.SINGLE);
        existingMeasuringTabbedItem.setText("Select existing measuring point");

        Composite existingMPcomposite = new Composite(tabFolder, SWT.SINGLE);
        existingMPcomposite.setLayout(layout);

        emptyMpTreeViewer = createEmptyMpTreeViewer(existingMPcomposite);

        existingMeasuringTabbedItem.setControl(existingMPcomposite);

        TabItem createMeasuringTabbedItem = new TabItem(tabFolder, SWT.NONE);
        createMeasuringTabbedItem.setText("Create new measuring point");

        Composite createMPcomposite = new Composite(tabFolder, SWT.SINGLE);
        createMPcomposite.setLayout(layout);
        MeasuringPointsContentProvider mp = new MeasuringPointsContentProvider();
        createContentProvider = mp;
        createTreeViewer = new TreeViewer(createMPcomposite);
        createTreeViewer.setContentProvider(createContentProvider);
        createTreeViewer.setInput(selectionWizardModel.getAllSecondPageObjects());
        createTreeViewer.setLabelProvider(new MeasuringPointsLabelProvider());
        createMeasuringTabbedItem.setControl(createMPcomposite);

        this.setPageComplete(true);
    }

    /**
     * calls the tree viewer which is being build by parsley
     * 
     * @param parent
     * @return
     */
    private MpTreeViewer createEmptyMpTreeViewer(Composite parent) {
        return new EmptyMpTreeViewer(parent, dirty, commandService, DataApplication.getInstance());
    }

    /** @override */
    public org.eclipse.jface.wizard.IWizardPage getNextPage() {
        // kind of hack to detect without overriding WizardDialog#nextPressed()
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
        //
    }

    /**
     * @see WizardDialog#nextPressed()
     * @see WizardPage#getNextPage()
     */
    protected boolean nextPressed() {
        boolean validatedNextPressed = false;
        try {
            if (tabFolder.getSelectionIndex() == 0) {
                if (emptyMpTreeViewer.getViewer().getStructuredSelection()
                        .getFirstElement() instanceof MeasuringPoint) {
                    selectionWizardModel.addMeasuringPointToMonitor(
                            (MeasuringPoint) emptyMpTreeViewer.getViewer().getStructuredSelection().getFirstElement());
                    validatedNextPressed = true;
                }
            } else {
                if (createTreeViewer.getStructuredSelection().getFirstElement() instanceof AssemblyContext
                        | createTreeViewer.getStructuredSelection().getFirstElement() instanceof SubSystem
                        | createTreeViewer.getStructuredSelection()
                                .getFirstElement() instanceof org.palladiosimulator.pcm.system.System) {
                    selectionWizardModel
                            .setCurrentSelection(createTreeViewer.getStructuredSelection().getFirstElement());
                    selected = true;
                    validatedNextPressed = true;
                } else {
                    selectionWizardModel
                            .setCurrentSelection(createTreeViewer.getStructuredSelection().getFirstElement());
                    selectionWizardModel
                            .createMeasuringPoint(createTreeViewer.getStructuredSelection().getFirstElement());
                    selected = false;
                    validatedNextPressed = true;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return validatedNextPressed;
    }

}
