package org.palladiosimulator.measurementsui.wizard.main;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.Bundle;
import org.palladiosimulator.measurementsui.wizard.pages.AddMonitorWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.AdditionalModelsToMeasuringpointWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.ChooseMeasuringpointWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.FinalModelsToMeasuringpointWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.MeasurementSpecificationWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.SelectMeasurementsWizardPage;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModelManager;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModelType;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MetricDescriptionSelectionWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MonitorCreationWizardModel;
import org.palladiosimulator.measurementsui.wizardmodel.pages.ProcessingTypeSelectionWizardModel;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * This class handles the wizard and its wizard pages for creating a new measuring point/monitor.
 * 
 * @author Birasanth Pushpanathan David Schuetz, Lasse Merz Added WizardModel support
 * @author Ba-Anh Vu, resize of wizard window and image in upper right corner according to scaling of OS
 *
 */
public class MeasurementsWizard extends org.eclipse.jface.wizard.Wizard {

    private WizardModelManager wizardManager;

    private WizardModelType startingPage;
    /**
     * Represents the first wizard page, where the new monitor defined (name +
     * activated/deactivated)
     */
    private AddMonitorWizardPage page1;

    /**
     * Represents the 2nd wizard page, where the user selects either an existing measuring point for
     * the new monitor or creates a new one.
     */
    private ChooseMeasuringpointWizardPage page2;

    /**
     * Represents the 3rd wizard page, where the user selects measurements which are then assigned
     * to the monitor
     */
    private SelectMeasurementsWizardPage page3;
    
    /**
     * Represents the 4th wizard page, where the user can set properties for the selected
     * measurements.
     */
    private MeasurementSpecificationWizardPage page4;

    private AdditionalModelsToMeasuringpointWizardPage page2extra;

    private FinalModelsToMeasuringpointWizardPage page2final;

    /**
     * The width used for the wizard window
     */
    private int windowWidth = 800;

    /**
     * The height used for the wizard window
     */
    private int windowHeight = 400;

    public MeasurementsWizard() {
        wizardManager = new WizardModelManager();
        this.startingPage = WizardModelType.MONITOR_CREATION;
        setImage();
        createPages();
    }

    /**
     * The constructor
     */
    public MeasurementsWizard(WizardModelType startingPage) {
        setWindowTitle("Add new Measuring Point");
        wizardManager = new WizardModelManager();
        this.startingPage = startingPage;
        setImage();
        createPages();
    }

    /**
     * The constructor
     */
    public MeasurementsWizard(WizardModelType startingPage, Monitor monitor) {
        setWindowTitle("Add new Measuring Point");
        wizardManager = new WizardModelManager(monitor);
        this.startingPage = startingPage;
        setImage();
        createPages();
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
        setDefaultPageImageDescriptor(resizedImageDescriptor);
    }

    private void createPages() {
        page1 = new AddMonitorWizardPage(
                (MonitorCreationWizardModel) wizardManager.getWizardModel(WizardModelType.MONITOR_CREATION));
        
        page2 = new ChooseMeasuringpointWizardPage((MeasuringPointSelectionWizardModel) wizardManager
                .getWizardModel(WizardModelType.MEASURING_POINT_SELECTION));
        page2extra = new AdditionalModelsToMeasuringpointWizardPage((MeasuringPointSelectionWizardModel) wizardManager
                .getWizardModel(WizardModelType.MEASURING_POINT_SELECTION));
        page2final = new FinalModelsToMeasuringpointWizardPage((MeasuringPointSelectionWizardModel) wizardManager
                .getWizardModel(WizardModelType.MEASURING_POINT_SELECTION));
        
        page3 = new SelectMeasurementsWizardPage((MetricDescriptionSelectionWizardModel) wizardManager
                .getWizardModel(WizardModelType.METRIC_DESCRIPTION_SELECTION));
        
        page4 = new MeasurementSpecificationWizardPage(
                (ProcessingTypeSelectionWizardModel) wizardManager.getWizardModel(WizardModelType.PROCESSING_TYPE));

    }

    @Override
    public IWizardPage getStartingPage() {
        switch (startingPage) {
        case MONITOR_CREATION:
            return page1;
        case MEASURING_POINT_SELECTION:
            return page2;
        case METRIC_DESCRIPTION_SELECTION:
            return page3;
        case PROCESSING_TYPE:
            return page4;
        default:
            return page1;
        }

    }

    @Override
    public void addPages() {
        addPage(page1);
        addPage(page2);
        addPage(page3);
        addPage(page4);
        addPage(page2extra);
        addPage(page2final);
    }

    @Override
    public boolean performCancel() {
        wizardManager.cancel();
        return true;
    }
    
    @Override
    public boolean performFinish() {
        page2.performAddingOperations();
        wizardManager.finish();
        return true;
    }

    @Override
    public boolean canFinish() {
        return wizardManager.canFinish();
    }

    /**
     * Returns the width used for the wizard window, also considers the scaling of the OS
     * @return the width used for the wizard window, also considers the scaling of the OS
     */
    public int getWindowWidth() {
        return Math.round(windowWidth * getDPIScale());
    }

    /**
     * Returns the height used for the wizard window, also considers the scaling of the OS
     * @return the height used for the wizard window, also considers the scaling of the OS
     */
    public int getWindowHeight() {
        return Math.round(windowHeight * getDPIScale());
    }
    
    /**
     * Returns the scaling of the OS with a value of 1.0 if set to 100%, 1.5 if set to 150% etc.
     * @return the scaling of the OS with a value of 1.0 if set to 100%, 1.5 if set to 150% etc.
     */
    private float getDPIScale() {
        int currentDPI = Display.getDefault().getDPI().x;
        float defaultDPI = 96.0f;
        return currentDPI / defaultDPI;
    }
    
}
