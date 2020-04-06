package org.palladiosimulator.measurementsui.wizard.pages;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.Bundle;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.KeyAdapter;
import org.palladiosimulator.measurementsui.wizardmodel.pages.SloCreationWizardModel;

/**
 * Wizard page for the creation of service level objectives. 
 * This is the first page on which you choose the name and the description
 * for the new Service Level Objective.
 * 
 * @author Jan Hofmann
 * @author Manuel Marroquin
 *
 */
@SuppressWarnings("restriction")
public class ServiceLevelObjectiveCreationPage extends WizardPage {
	private SloCreationWizardModel pageModel;
    private Text text_name;
    private Text text_description;
    private ControlDecoration nameTextDecorator;
    
    private static final String ERROR_SLO_NAME_EMPTY = "The name of the Service Level Objective can not be empty.";
	private static final String HELP_URL = "https://sdqweb.ipd.kit.edu/wiki/SimuLizar_Usability_Extension#User_Guide";
    
    /**
     * constructor for the first service level objective wizardpage
     * @param pageName
     *            name of the wizardpage
     */
    public ServiceLevelObjectiveCreationPage (SloCreationWizardModel model) {
        super("First Page");
        this.pageModel = model;
        setTitle(model.getTitleText());
        setMessage(model.getInfoText(), INFORMATION);
    }
    
    /**
     * This method initializes and manages SWT-UI elements for the wizard page.
     */
    @Override
    public void createControl(Composite parent) {
    	setImage();
        Composite contentContainer = new Composite(parent, SWT.BORDER);
        setControl(contentContainer);
        contentContainer.setLayout(new GridLayout(2, false));
        
        Label lblNewLabel = new Label(contentContainer, SWT.NONE);
        lblNewLabel.setBounds(0, 0, 55, 15);
        lblNewLabel.setText("Name:");
        
        text_name = new Text(contentContainer, SWT.BORDER);
        text_name.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) {
        		pageModel.setName(text_name.getText());	
        		if (pageModel.getName().isEmpty()) {
                    setPageComplete(false);
                    setErrorMessage(ERROR_SLO_NAME_EMPTY);
                    nameTextDecorator.show();
                } else {
                	setPageComplete(true);
                	setErrorMessage(null);
                	nameTextDecorator.hide();
                }
        	}
        });
        
        GridData gd_text_name = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_text_name.widthHint = 495;
        text_name.setLayoutData(gd_text_name);
        text_name.setBounds(0, 0, 76, 21);
              
        if (pageModel.getSlo().getName() != null) {
        	// Get the description if editing an slo
        	String name = pageModel.getSlo().getName();
        	text_name.setText(name);
            // Set the current name to the page model
        	pageModel.setName(name);
        	setPageComplete(true);

		} else {
			text_name.setText("");
		}
        
        Label lblDescription = new Label(contentContainer, SWT.NONE);
        lblDescription.setBounds(0, 0, 55, 15);
        lblDescription.setText("Description:");
        
        text_description = new Text(contentContainer, SWT.BORDER);
        text_description.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) {
        		pageModel.setDescription(text_description.getText());
        	}
        });
        GridData gd_text_description = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_text_description.widthHint = 495;
        text_description.setLayoutData(gd_text_description);
        text_description.setBounds(0, 0, 76, 21);
        
        try {
        	// Get the description if editing an slo
        	String description = pageModel.getSlo().getDescription();
            text_description.setText(description);
            // Set the current name to the page model
            pageModel.setDescription(description);

		} catch (Exception e) {
	        text_description.setText("");
		}
        
        // Configure error decorations
        org.eclipse.swt.graphics.Image image = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage();
        nameTextDecorator = new ControlDecoration(text_name, SWT.CENTER);
        nameTextDecorator.setImage(image);
        nameTextDecorator.setMarginWidth(1);
        nameTextDecorator.hide();
        
        if (text_name.getText().isEmpty()) {
        	setPageComplete(false);
        }
    }
   
    /**
     * Returns the scaling of the OS with a value of 1.0 if set to 100%, 1.5 if set to 150% etc.
     *
     * @return the scaling of the OS with a value of 1.0 if set to 100%, 1.5 if set to 150% etc.
     */
    private float getDPIScale() {
        int currentDPI = Display.getDefault().getDPI().x;
        float defaultDPI = 96.0f;
        return currentDPI / defaultDPI;
    } 
    
    /**
     * Sets the image of the wizard which is shown in the upper right corner of the wizard pages.
     * 
     * Also scales the image according to the scaling of the OS.
     * The original size of the image is used for a scaling of 150%, that means when the scaling of the OS is below
     * 150%, the image is shrunk.
     */
    @SuppressWarnings("deprecation")
    private void setImage() {
        Bundle bundle = Platform.getBundle("org.palladiosimulator.measurementsui.wizard");
        URL fullPathString = BundleUtility.find(bundle, "icons/wizardImage.png");
        ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(fullPathString);
        ImageData imageData = imageDescriptor.getImageData();
        int scaledWidth = Math.round(imageData.width / (1.5f / getDPIScale()));
        int scaledHeight = Math.round(imageData.height / (1.5f / getDPIScale()));
        if( scaledWidth < 100) {
            scaledWidth = 100;
        }
        if( scaledHeight < 100) {
            scaledHeight = 100;
        }
        ImageData scaledImageData = imageData.scaledTo(scaledWidth, scaledHeight);
        ImageDescriptor resizedImageDescriptor = ImageDescriptor.createFromImageData(scaledImageData);
        setImageDescriptor(resizedImageDescriptor);
    }
    
    @Override
    public void performHelp() {
        Program.launch(HELP_URL);
    }
 
}
