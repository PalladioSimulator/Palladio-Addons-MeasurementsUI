package org.palladiosimulator.measurementsui.wizard.handlers;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
/**
 * @author Birasanth Pushpanathan
 *
 */
public class SelectMeasuringPoint extends WizardPage {
    private TabFolder tabFolder;
    private Composite container;

    public SelectMeasuringPoint() {
        super("Second Page");
        setTitle("Second Page");
        setDescription("Now this is the second page");
        setControl(tabFolder);
    }

    @Override
    public void createControl(Composite parent) {
        container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        // required to avoid an error in the system
        setControl(container);
        
        TabFolder tabFolder = new TabFolder(container,SWT.BORDER | SWT.SINGLE);
        tabFolder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
        tbtmNewItem.setText("Tab 1");
        
        Group group = new Group(tabFolder, SWT.BORDER | SWT.SINGLE);
        group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        tbtmNewItem.setControl(group);
        
        Label lblNewLabel = new Label(group, SWT.NONE);
        lblNewLabel.setBounds(0, 0, 59, 14);
        lblNewLabel.setText("tab1");
        
        TabItem tbtmNewItem_1 = new TabItem(tabFolder, SWT.NONE);
        tbtmNewItem_1.setText("Tab 2");
        
        Group group_1 = new Group(tabFolder, SWT.BORDER | SWT.SINGLE);
        group_1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        tbtmNewItem_1.setControl(group_1);
        
        Label lblNewLabel_1 = new Label(group_1, SWT.NONE);
        lblNewLabel_1.setBounds(0, 0, 59, 14);
        lblNewLabel_1.setText("tab2");
        setPageComplete(true);
    }

}