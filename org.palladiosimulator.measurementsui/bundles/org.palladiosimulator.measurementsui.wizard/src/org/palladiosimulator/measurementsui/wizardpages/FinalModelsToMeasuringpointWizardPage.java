package org.palladiosimulator.measurementsui.wizardpages;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.fileaccess.SecondPageWizardModel;
import org.palladiosimulator.measurementsui.wizardmain.handlers.AdditionalMeasuringpointLabelProvider;
import org.palladiosimulator.measurementsui.wizardmain.handlers.FinalMeasuringpointContentProvider;

public class FinalModelsToMeasuringpointWizardPage extends WizardPage {
	TreeViewer createTreeViewer;
	ITreeContentProvider createContentProvider;
	Composite container;
	FinalMeasuringpointContentProvider mp;
	boolean selected;
	SecondPageWizardModel sq = SecondPageWizardModel.getInstance();

	public FinalModelsToMeasuringpointWizardPage() {
		super("page2final");
		setTitle("Select final models");
		setDescription("description");
	}

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
		createTreeViewer = new TreeViewer(container);
		createTreeViewer.setContentProvider(mp);
		createTreeViewer.setInput(sq.getSignatures().toArray());
		createTreeViewer.setLabelProvider(new AdditionalMeasuringpointLabelProvider());

	}

	/** @override */
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

		} catch (Exception ex) {
			System.out.println("Error validation when pressing Next: " + ex);
		}
		return validatedNextPressed;
	}
}
