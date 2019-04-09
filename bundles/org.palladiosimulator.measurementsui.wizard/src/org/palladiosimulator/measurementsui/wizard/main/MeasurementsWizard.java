package org.palladiosimulator.measurementsui.wizard.main;

import java.beans.PropertyChangeListener;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.Bundle;
import org.palladiosimulator.measurementsui.wizard.pages.MonitorCreationWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.AdditionalModelsToMeasuringpointWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.ChooseMeasuringpointWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.FinalModelsToMeasuringpointWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.ProcessingTypeSelectionWizardPage;
import org.palladiosimulator.measurementsui.wizard.pages.MetricDescriptionSelectionWizardPage;
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
     * 1.Page
     * Represents the first wizard page, where the new monitor defined (name +
     * activated/deactivated)
     */
    private MonitorCreationWizardPage monitorCreationWizardPage1;

    /**
     * 2.Page
     * Represents the 2nd wizard page, where the user selects either an existing measuring point for
     * the new monitor or creates a new one.
     */
    private ChooseMeasuringpointWizardPage chooseMeasuringpointWizardPage2;
    
    /**
     * 2_2. Page
     * Represents the first extra page to page 2. There are some exceptions, where the user
     * has to select further models after creating a measuring point. In these cases
     * this page will be shown.
     */
    private AdditionalModelsToMeasuringpointWizardPage additionalModelsToMeasuringpointWizardPage2_2;
    
    /**
     * 2_3. Page
     * Represents the second extra page to page 2. There are rare exceptions, where the user
     * has to select further models after selecting something in the 2_2 extra page. In these cases
     * this page will be shown.
     */
    private FinalModelsToMeasuringpointWizardPage finalModelsToMeasuringpointWizardPage2_3;

    /**
     * 3.Page
     * Represents the 3rd wizard page, where the user selects metrics which are then assigned
     * to the monitor
     */
    private MetricDescriptionSelectionWizardPage metricDescriptionSelectionWizardPage3;

    /**
     * Represents the 4th wizard page, where the user can set properties for the selected
     * metrics.
     */
    private ProcessingTypeSelectionWizardPage processingTypeSelectionWizardPage4;

    

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
    * Adds a PropertyChangeListener to the wizardManager
    * @param listener the PropertyChangeListener
    */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        wizardManager.addPropertyChangeListener(listener);
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
        monitorCreationWizardPage1 = new MonitorCreationWizardPage(
                (MonitorCreationWizardModel) wizardManager.getWizardModel(WizardModelType.MONITOR_CREATION));
        
        chooseMeasuringpointWizardPage2 = new ChooseMeasuringpointWizardPage((MeasuringPointSelectionWizardModel) wizardManager
                .getWizardModel(WizardModelType.MEASURING_POINT_SELECTION));
        additionalModelsToMeasuringpointWizardPage2_2 = new AdditionalModelsToMeasuringpointWizardPage((MeasuringPointSelectionWizardModel) wizardManager
                .getWizardModel(WizardModelType.MEASURING_POINT_SELECTION));
        finalModelsToMeasuringpointWizardPage2_3 = new FinalModelsToMeasuringpointWizardPage((MeasuringPointSelectionWizardModel) wizardManager
                .getWizardModel(WizardModelType.MEASURING_POINT_SELECTION));
        
        metricDescriptionSelectionWizardPage3 = new MetricDescriptionSelectionWizardPage((MetricDescriptionSelectionWizardModel) wizardManager
                .getWizardModel(WizardModelType.METRIC_DESCRIPTION_SELECTION));
        
        processingTypeSelectionWizardPage4 = new ProcessingTypeSelectionWizardPage(
                (ProcessingTypeSelectionWizardModel) wizardManager.getWizardModel(WizardModelType.PROCESSING_TYPE));

    }

    @Override
    public IWizardPage getStartingPage() {
        switch (startingPage) {
        case MONITOR_CREATION:
            return monitorCreationWizardPage1;
        case MEASURING_POINT_SELECTION:
            return chooseMeasuringpointWizardPage2;
        case METRIC_DESCRIPTION_SELECTION:
            return metricDescriptionSelectionWizardPage3;
        case PROCESSING_TYPE:
            return processingTypeSelectionWizardPage4;
        default:
            return monitorCreationWizardPage1;
        }

    }

    @Override
    public void addPages() {
        addPage(monitorCreationWizardPage1);
        addPage(chooseMeasuringpointWizardPage2);
        addPage(metricDescriptionSelectionWizardPage3);
        addPage(processingTypeSelectionWizardPage4);
        addPage(additionalModelsToMeasuringpointWizardPage2_2);
        addPage(finalModelsToMeasuringpointWizardPage2_3);
    }

    @Override
    public boolean performCancel() {
        wizardManager.cancel();
        return true;
    }
    
    @Override
    public boolean performFinish() {
        chooseMeasuringpointWizardPage2.performAddingOperations();
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
