package org.palladiosimulator.measurementsui.wizard.handlers;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Birasanth Pushpanathan
 *
 */

public class AddMonitor extends WizardPage {
    private Text text1;
    private Composite container;
    private Button btnCheckButton;

    public AddMonitor() {
        super("First Page");
        setTitle("Add Monitor to Measuring Point");
        setDescription("description");
    }

    @Override
    public void createControl(Composite parent) {
        container = new Composite(parent, SWT.NONE);

        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 2;
        Label label1 = new Label(container, SWT.NONE);
        label1.setText("Name");

        text1 = new Text(container, SWT.BORDER | SWT.SINGLE);
        text1.setMessage("Placeholder");
        text1.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!text1.getText().isEmpty()) {
                    setPageComplete(true);
                } else {
                    setPageComplete(false);
                }
            }

        });
        text1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        // required to avoid an error in the system
        setControl(container);
        new Label(container, SWT.NONE);
        new Label(container, SWT.NONE);
        new Label(container, SWT.NONE);
        new Label(container, SWT.NONE);

        btnCheckButton = new Button(container, SWT.CHECK);
        btnCheckButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        btnCheckButton.setText("activate");
        new Label(container, SWT.NONE);
        setPageComplete(false);
    }

    public String getText1() {
        return text1.getText();
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
