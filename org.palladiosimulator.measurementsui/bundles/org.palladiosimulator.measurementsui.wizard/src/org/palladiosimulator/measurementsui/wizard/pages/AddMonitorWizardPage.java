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
 *
 */
public class AddMonitorWizardPage extends WizardPage {

    /**
     * the newly created monitor object
     */
	private MonitorCreationWizardModel model;

    /**
     * Constructor, sets basic attributes for the wizard page like title, description etc.
     * @param model the model object which manages the internal model for this wizard page
     */
    public AddMonitorWizardPage(MonitorCreationWizardModel model) {
        super("First Page");
        this.model = model;
        setTitle(model.getTitleText());
        setDescription(model.getInfoText());
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        container.setLayout(gridLayout);

        Label nameLabel = new Label(container, SWT.NONE);
        nameLabel.setText("Name: ");
        
        Text nameText = new Text(container, SWT.BORDER);
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
        
        Label activatedLabel = new Label(container, SWT.NONE);
        activatedLabel.setText("Activated: ");
        
        Button activatedCheckbox = new Button(container, SWT.CHECK);
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
        
        setControl(container);
        setPageComplete(true);
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
