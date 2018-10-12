package org.palladiosimulator.measurementsui.wizard.pages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Wizard page for the creation of standard sets. This is the first page on which you choose what
 * kind of standard set should be created.
 * 
 * @author Domas Mikalkinas
 *
 */
public class StandardSetCreationSelectionWizardPage extends WizardPage {
    private Button[] radios = new Button[2];

    /**
     * constructor for the first wizardpage
     * 
     * @param pageName
     *            name of the wizardpage
     */
    public StandardSetCreationSelectionWizardPage(String pageName) {

        super("standardSetCreationSelectionWizardPage");
        setMessage(pageName);

    }

    @Override
    public void createControl(Composite parent) {

        Composite radioButtonContainer = new Composite(parent, SWT.BORDER);
        radios[0] = new Button(radioButtonContainer, SWT.RADIO);
        radios[0].setSelection(true);
        radios[0].setText("Create monitors and measuring points.");
        radios[0].setBounds(10, 5, 700, 30);

        radios[1] = new Button(radioButtonContainer, SWT.RADIO);
        radios[1].setText("Create only measuring points.");
        radios[1].setBounds(10, 30, 700, 30);

        setControl(radioButtonContainer);

        setPageComplete(true);

    }

    /**
     * overrides the getNextPage() method of the wizard page to allow a dynamic flow of the wizard
     * pages
     */
    @Override
    public org.eclipse.jface.wizard.IWizardPage getNextPage() {

        StandardSetMeasuringPointSelectionWizardPage page2 = (StandardSetMeasuringPointSelectionWizardPage) super.getNextPage();
        if (radios[0].getSelection()) {
            page2.loadMonitorAndMeasuringpointInput();
            return page2;

        } else {
            page2.loadOnlyMeasuringpointInput();
            return page2;
        }

    }
    
    @Override
    public void performHelp() {
        Program.launch("https://sdqweb.ipd.kit.edu/wiki/SimuLizar_Usability_Extension#Standard_Set_Functionality");
    }

}
