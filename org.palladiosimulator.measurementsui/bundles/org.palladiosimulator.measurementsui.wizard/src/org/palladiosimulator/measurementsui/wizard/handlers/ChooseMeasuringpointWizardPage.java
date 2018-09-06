package org.palladiosimulator.measurementsui.wizard.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.palladiosimulator.measurementsui.abstractviewer.MpTreeViewer;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.parsleyviewer.EmptyMpTreeViewer;

/**
 * @author Domas Mikalkinas
 *
 */

public class ChooseMeasuringpointWizardPage extends WizardPage {
    MpTreeViewer emptyMpTreeViewer;

    @Inject
    MDirtyable dirty;

    @Inject
    ECommandService commandService;

    public ChooseMeasuringpointWizardPage() {
        super("Second Page");
        setTitle("Select Measuring Point");
        setDescription("description");
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

        TabFolder tabFolder = new TabFolder(container, SWT.SINGLE);

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

        createMeasuringTabbedItem.setControl(createMPcomposite);

        setPageComplete(true);
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

}
