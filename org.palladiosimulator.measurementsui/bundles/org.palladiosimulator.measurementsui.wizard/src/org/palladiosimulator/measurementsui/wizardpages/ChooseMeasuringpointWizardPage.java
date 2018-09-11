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
import org.palladiosimulator.measurementsui.abstractviewer.MpTreeViewer;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.fileaccess.SecondPageWizardModel;
import org.palladiosimulator.measurementsui.parsleyviewer.EmptyMpTreeViewer;
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
	SecondPageWizardModel spwm = SecondPageWizardModel.getInstance();
	public static Object currentSelection;
	boolean selected = false;
	ITreeContentProvider createContentProvider;
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
	 * Creates the second page of the wizard, which shows a tabbed table of
	 * measuringpoints, either existing or those which can be created
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
		MeasuringPointsContentProvider mp = new MeasuringPointsContentProvider();
		createContentProvider = mp;
		createTreeViewer = new TreeViewer(createMPcomposite);
		createTreeViewer.setContentProvider(createContentProvider);
		createTreeViewer.setInput(spwm.getAllSecondPageObjects());
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
		boolean validatedNextPressed = true;
		try {

			if (createTreeViewer.getStructuredSelection().getFirstElement() instanceof AssemblyContext
					| createTreeViewer.getStructuredSelection().getFirstElement() instanceof SubSystem
					| createTreeViewer.getStructuredSelection()
							.getFirstElement() instanceof org.palladiosimulator.pcm.system.System) {
				spwm.setCurrentSelection(createTreeViewer.getStructuredSelection().getFirstElement());
				selected = true;
			} else {
				selected = false;
			}
		} catch (Exception ex) {
			System.out.println("Error validation when pressing Next: " + ex);
		}
		return validatedNextPressed;
	}
}
