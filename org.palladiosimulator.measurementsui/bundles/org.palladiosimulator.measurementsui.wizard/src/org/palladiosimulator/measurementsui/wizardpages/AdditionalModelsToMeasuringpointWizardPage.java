package org.palladiosimulator.measurementsui.wizardpages;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.wizardmain.handlers.AdditionalMeasuringpointContentProvider;
import org.palladiosimulator.measurementsui.wizardmain.handlers.AdditionalMeasuringpointLabelProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.pcm.repository.PassiveResource;

/**
 * This is the wizard page for the second step of the creation of a measuring
 * point. It only needs to be shown if certain elements are selected in the
 * first step. It creates all necessary ui elements and provides functions to
 * dynamically choose the next wizard page.
 * 
 * @author Domas Mikalkinas
 *
 */
public class AdditionalModelsToMeasuringpointWizardPage extends WizardPage {
	TreeViewer secondModelTreeViewer;
	ITreeContentProvider createContentProvider;
	Composite container;
	AdditionalMeasuringpointContentProvider mp;
	boolean selected = false;
	MeasuringPointSelectionWizardModel selectionWizardModel = MeasuringPointSelectionWizardModel.getInstance();

	public AdditionalModelsToMeasuringpointWizardPage() {
		super("page2extra");
		setTitle("Select an operation role or passive resource");
		setDescription("");
	}

	/**
	 * creates the wizard page which shows additional models, which are needed
	 * depending on the chosen element from the ChooseMeasuringpointWizardpage for
	 * the creation of a new measuringpoint
	 */
	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		FillLayout layout = new FillLayout();

		layout.marginHeight = 0;
		layout.marginWidth = 0;
		container.setLayout(layout);
		setControl(container);
		mp = new AdditionalMeasuringpointContentProvider();
		createContentProvider = mp;
		secondModelTreeViewer = new TreeViewer(container);

	}

	/**
	 * delays the loading of the data, because it needs to be loaded dynamically
	 * depending on the chosen element from the ChooseMeasuringpointWizardpage
	 */
	public void loadData() {
		secondModelTreeViewer.setContentProvider(mp);
		secondModelTreeViewer.setInput(selectionWizardModel.getAllAdditionalModels());
		IStructuredSelection initialSelection = new StructuredSelection(
				selectionWizardModel.getAllAdditionalModels()[0]);
		secondModelTreeViewer.setSelection(initialSelection);
		secondModelTreeViewer.setLabelProvider(new AdditionalMeasuringpointLabelProvider());
	}

	/**
	 * overrides the getNextPage() method of the wizard page to allow a dynamic flow
	 * of wizard pages
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
			FinalModelsToMeasuringpointWizardPage page = (FinalModelsToMeasuringpointWizardPage) super.getWizard()
					.getPage("page2final");

			return page;

		} else {
			return super.getWizard().getPage("wizardPage");
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
			if (!(secondModelTreeViewer.getStructuredSelection().getFirstElement() instanceof PassiveResource)) {

				selectionWizardModel
						.setCurrentSecondStageModel(secondModelTreeViewer.getStructuredSelection().getFirstElement());
				selected = true;
			} else {
				selectionWizardModel
						.setCurrentSecondStageModel(secondModelTreeViewer.getStructuredSelection().getFirstElement());
				selectionWizardModel.createMeasuringPoint(selectionWizardModel.getCurrentSelection());
				selected = false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return validatedNextPressed;
	}
}
