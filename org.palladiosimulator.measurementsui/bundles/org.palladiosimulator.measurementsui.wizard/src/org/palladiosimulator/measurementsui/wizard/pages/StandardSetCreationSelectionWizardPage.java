package org.palladiosimulator.measurementsui.wizard.pages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;


/**
 * Wizard page for the creation of standard sets. This is the first page on which you choose what kind of standard set should be created.
 * @author Domas Mikalkinas
 *
 */
public class StandardSetCreationSelectionWizardPage extends WizardPage {
	Button[] radios = new Button[2];

	public StandardSetCreationSelectionWizardPage(String pageName) {

		super("wizardpage");
		setMessage(pageName);

	}

	@Override
	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.BORDER);
		radios[0] = new Button(container, SWT.RADIO);
		radios[0].setSelection(true);
		radios[0].setText("Create monitors and measuring points.");
		radios[0].setBounds(10, 5, 300, 30);

		radios[1] = new Button(container, SWT.RADIO);
		radios[1].setText("Create only measuring points.");
		radios[1].setBounds(10, 30, 300, 30);

		setControl(container);
		setPageComplete(true);

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
		StandardSetMeasuringPointSelectionWizardPage page2 = (StandardSetMeasuringPointSelectionWizardPage) super.getNextPage();
		if (radios[0].getSelection()) {
			page2.loadMonitorAndMeasuringpointInput();
			return page2;

		} else {
			page2.loadOnlyMeasuringpointInput();
			return page2;
		}


	}

	/**
	 * @see WizardDialog#nextPressed()
	 * @see WizardPage#getNextPage()
	 * @return boolean validates whether the next button is pressed or not
	 */
	protected boolean nextPressed() {

		return true;
	}

}
