package org.palladiosimulator.measurementsui.wizard.main;

import org.eclipse.equinox.log.SynchronousLogListener;
import org.palladiosimulator.measurementsui.wizard.pages.StandardSetCreationSelectionWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.StandardSetMeasuringPointSelectionWizardPage;

public class StandardSetWizard extends org.eclipse.jface.wizard.Wizard{

	private StandardSetCreationSelectionWizardPage page1;
	private StandardSetMeasuringPointSelectionWizardPage page2;
	
	
	
	public StandardSetCreationSelectionWizardPage getPage1() {
		return page1;
	}

	public void setPage1(StandardSetCreationSelectionWizardPage page1) {
		this.page1 = page1;
	}

	public StandardSetMeasuringPointSelectionWizardPage getPage2() {
		return page2;
	}

	public void setPage2(StandardSetMeasuringPointSelectionWizardPage page2) {
		this.page2 = page2;
	}

	public StandardSetWizard() {
		setWindowTitle("Create a standard set");	
		page1= new StandardSetCreationSelectionWizardPage("Select which standard set should be created.");
		page2= new StandardSetMeasuringPointSelectionWizardPage("Select all monitors which should be created.");
	}
	
	@Override
	public void addPages() {
		addPage(page1);
		addPage(page2);
	}
	
	@Override
	public boolean performFinish() {
	
		System.out.println(((Object[])page2.getViewer().getCheckedElements()).length);
		return true;
	}

	
	
}
