package org.palladiosimulator.measurementsui.wizard.pages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MonitorCreationWizardModel;

/**
 * This class handles the first wizard page for creating a new monitor.
 * @author Birasanth Pushpanathan
 * @author David Schuetz Added WizardModel integration
 * @author Lasse Merz Added WizardModel integration
 * @author Ba-Anh Vu reworked first page, now working without Parsley, due to complication with GUI Design
 *
 */
public class MonitorCreationWizardPage extends WizardPage {

    /**
     * The model object which manages the internal model for this wizard page.
     */
	private MonitorCreationWizardModel model;

    /**
     * Constructor, sets basic attributes for the wizard page like title, description etc.
     * @param model the model object which manages the internal model for this wizard page
     */
    public MonitorCreationWizardPage(MonitorCreationWizardModel model) {
        super("First Page");
        this.model = model;
        setTitle(model.getTitleText());
        setDescription(model.getInfoText());
    }

    @Override
    public void createControl(Composite parent) {
        final Composite container = new Composite(parent, SWT.NONE);
        
        setGridLayout(container);

        addLabel(container, "Name: ");
        addTextBox(container);
        addLabel(container, "Activated: ");
        addCheckbox(container);
        
        setControl(container);
        setPageComplete(true);
    }

    /**
     * Sets a GridLayout as the layout for the given composite.
     * @param container the given composite
     */
    private void setGridLayout(Composite container) {
        final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        container.setLayout(gridLayout);
    }

    /**
     * Adds a label with a given string to the given composite.
     * @param container the given composite
     * @param labelText the given text of the label
     */
    private void addLabel(Composite container, String labelText) {
        final Label nameLabel = new Label(container, SWT.NONE);
        nameLabel.setText(labelText);
    }

    /**
     * Adds the text box for setting the name of the monitor.
     * @param container the given composite
     */
    private void addTextBox(Composite container) {
        final Text nameText = new Text(container, SWT.BORDER);
        nameText.setText(this.model.getMonitor().getEntityName());
        nameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        nameText.addModifyListener(e -> {
            this.model.setMonitorName(nameText.getText());
            if (nameText.getText().length() > 0) {
                this.setPageComplete(true);
            } else {
                this.setPageComplete(false);
            }
        });
    }

    /**
     * Adds the check box to a given composite which is for setting the 'activated' attribute of the monitor.
     * @param container the given composite
     */
    private void addCheckbox(Composite container) {
        final Button activatedCheckbox = new Button(container, SWT.CHECK);
        activatedCheckbox.setSelection(this.model.getMonitor().isActivated());
        activatedCheckbox.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                model.setMonitorActivated(activatedCheckbox.getSelection());                
            }
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // not used here, but the existence of this method is required
            }
        });
    }

    @Override
    public void performHelp() {
        Shell shell = new Shell(getShell());
        shell.setText("My Custom Help !!");
        shell.setLayout(new GridLayout());
        shell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Browser browser = new Browser(shell, SWT.NONE);
        browser.setUrl("http://stackoverflow.com/questions/7322489/cant-put-content-behind-swt-wizard-help-button");
        browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        shell.open();
    }

}
