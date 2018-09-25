package org.palladiosimulator.measurementsui.wizard.viewer;

import org.eclipse.emf.parsley.composite.FormDetailComposite;
import org.eclipse.emf.parsley.composite.FormFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.palladiosimulator.monitorrepository.Monitor;
import com.google.inject.Injector;
import org.palladiosimulator.measurementsui.newMonitor.NewMonitorInjectorProvider;
import org.palladiosimulator.measurementsui.wizard.pages.AddMonitorWizardPage;

/**
 * This class handles the view for the form for the 1st wizard page where the new monitor is created.
 * 
 * @author Ba
 *
 */
public class MonitorFormViewer {

    /**
     * Constructor with injection and creation of the form view of the wizard page. 
     * Also sets various properties of the page such as background color etc.
     * 
     * @param parent the container which contains the view
     * @param newMonitor the new monitor object
     * @param wizardPage the wizard page where the monitor is created
     */
    public MonitorFormViewer(Composite parent, Monitor newMonitor, AddMonitorWizardPage wizardPage) {
        Injector injector = NewMonitorInjectorProvider.getInjector();

        FormFactory formFactory = injector.getInstance(FormFactory.class);
        FormDetailComposite formDetailComposite = formFactory.createFormDetailComposite(parent, SWT.NONE);

        formDetailComposite.init(newMonitor);

        for (Control formChild : formDetailComposite.getChildren()) {
            if (formChild instanceof ScrolledForm) {
                for (Control scrolledFormChild : ((ScrolledForm) formChild).getBody().getChildren()) {
					checkAndSetPageComplete(wizardPage, scrolledFormChild);
                }
            }
        }
    }

    /**
     * Checks if the given control contains text.
     * If yes, set the page complete,
     * otherweise, set the page incomplete.
     * 
     * @param wizardPage the given wizard page which contains the given text
     * @param scrolledFormChild the element whose text is checked
     */
    private void checkAndSetPageComplete(AddMonitorWizardPage wizardPage, Control scrolledFormChild) {
        if (scrolledFormChild instanceof Text) {
        	((Text) scrolledFormChild).addModifyListener(e -> {
        		if (((Text) scrolledFormChild).getText().length() > 0) {
        			wizardPage.setPageComplete(true);
        		} else {
        			wizardPage.setPageComplete(false);
        		}

        	});
        }
    }

}
