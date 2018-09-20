package org.palladiosimulator.measurementsui.wizardpages;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.wizardmain.handlers.AdditionalMeasuringpointLabelProvider;
import org.palladiosimulator.measurementsui.wizardmain.handlers.FinalMeasuringpointContentProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;

/**
 * 
 * This is the wizard page for the third and final step of the creation of a
 * wizard page. It needs to be shown if and only if certain elements are
 * selected in the first and second step of the measuring point creation
 * workflow. It creates all necessary ui elements.
 * 
 * @author Domas Mikalkinas
 *
 */
public class FinalModelsToMeasuringpointWizardPage extends WizardPage {
	TreeViewer finalSelectionTreeViewer;
	ITreeContentProvider createContentProvider;
	Composite container;
	FinalMeasuringpointContentProvider mp;
	boolean selected;
	MeasuringPointSelectionWizardModel selectionWizardModel;

	public FinalModelsToMeasuringpointWizardPage(MeasuringPointSelectionWizardModel selectionWizardModel) {
		super("page2final");
		this.selectionWizardModel = selectionWizardModel;
		setTitle("Select an operation signature");
		// setDescription("description");
	}

	/**
	 * creates the wizard page to choose the operation signature
	 */
	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		FillLayout layout = new FillLayout();

		layout.marginHeight = 0;
		layout.marginWidth = 0;
		container.setLayout(layout);
		setControl(container);
		mp = new FinalMeasuringpointContentProvider();
		createContentProvider = mp;
		finalSelectionTreeViewer = new TreeViewer(container);
		finalSelectionTreeViewer.setContentProvider(mp);
		

	}

	public void loadData() {
		finalSelectionTreeViewer.setInput(selectionWizardModel.getSignatures().toArray());
		ISelection initialSelection = new StructuredSelection(selectionWizardModel.getSignatures().get(0));
		finalSelectionTreeViewer.setSelection(initialSelection);
		finalSelectionTreeViewer.setLabelProvider(new AdditionalMeasuringpointLabelProvider());
	}
	/**
	 * overrides the getNextPage() method of the wizard page to allow a dynamic flow
	 * of the wizard pages
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

		return super.getWizard().getPage("wizardPage");

		//
	}

	/**
	 * @see WizardDialog#nextPressed()
	 * @see WizardPage#getNextPage()
	 */
	protected boolean nextPressed() {
		boolean validatedNextPressed = true;
		try {
			selectionWizardModel
					.setCurrentThirdStageModel(finalSelectionTreeViewer.getStructuredSelection().getFirstElement());
			selectionWizardModel.createMeasuringPoint(selectionWizardModel.getCurrentSelection());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return validatedNextPressed;
	}
}
