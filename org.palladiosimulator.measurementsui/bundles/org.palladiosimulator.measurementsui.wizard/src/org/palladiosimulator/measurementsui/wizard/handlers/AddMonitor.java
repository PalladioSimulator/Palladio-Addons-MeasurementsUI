package org.palladiosimulator.measurementsui.wizard.handlers;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * This class handles the first wizard page for creating a new monitor.
 * @author Birasanth Pushpanathan
 *
 */
public class AddMonitor extends WizardPage {

    /**
     * This contains the form for the newly created monitor
     */
    private Composite container;

    /**
     * the newly created monitor object
     */
    private Monitor newMonitor;

    /**
     * Constructor
     * @param newMonitor the newly created monitor object
     */
    public AddMonitor(Monitor newMonitor) {
        super("First Page");
        setTitle("Create new Monitor");
        setDescription("Firstly a new Monitor is needed, please define its name. (De-)Activate it if necessary.");
        this.newMonitor = newMonitor;
    }

    @Override
    public void createControl(Composite parent) {
        container = new Composite(parent, SWT.NONE);

        FillLayout layout = new FillLayout();
        container.setLayout(layout);

        setControl(container);
        setPageComplete(true);

        createMonitorFormViewer(container, this.newMonitor, this);
    }

    private MonitorFormViewer createMonitorFormViewer(Composite parent, Monitor newMonitor, AddMonitor wizardPage) {
        MonitorFormViewer monitorFormViewer = new MonitorFormViewer(parent, newMonitor, wizardPage);
        return monitorFormViewer;
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
