package org.palladiosimulator.measurementsui.wizard.handlers;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.parsley.composite.FormDetailComposite;
import org.eclipse.emf.parsley.composite.FormFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.palladiosimulator.monitorrepository.Monitor;
import com.google.inject.Injector;
import de.uni_stuttgart.enpro.newmonitor.NewmonitorInjectorProvider;

/**
 * This class handles the view for the form for the 1st wizard page where the new monitor is created.
 * @author Ba
 *
 */
public class MonitorFormViewer {

    @Inject
    MDirtyable dirty;

    /**
     * Constructor
     * @param parent the container which contains the view
     * @param newMonitor the new monitor object
     * @param wizardPage the wizard page where the monitor is created
     */
    public MonitorFormViewer(Composite parent, Monitor newMonitor, AddMonitor wizardPage) {
        Injector injector = NewmonitorInjectorProvider.getInjector();

        FormFactory formFactory = injector.getInstance(FormFactory.class);
        FormDetailComposite formDetailComposite = formFactory.createFormDetailComposite(parent, SWT.NONE);

        formDetailComposite.init(newMonitor);
        formDetailComposite.setBackground(new Color(Display.getCurrent(), 233, 232, 233));

        for (Control formChild : formDetailComposite.getChildren()) {
            if (formChild instanceof ScrolledForm) {
                ((ScrolledForm) formChild).setBackground(new Color(Display.getCurrent(), 233, 232, 233));
                
                for (Control scrolledFormChild : ((ScrolledForm) formChild).getBody().getChildren()) {
                    if (!(scrolledFormChild instanceof Text)) {
                        scrolledFormChild.setBackground(new Color(Display.getCurrent(), 233, 232, 233));
                    }

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
        }
    }

}
