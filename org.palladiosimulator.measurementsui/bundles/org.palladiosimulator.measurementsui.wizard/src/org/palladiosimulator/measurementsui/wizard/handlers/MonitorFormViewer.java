package org.palladiosimulator.measurementsui.wizard.handlers;

import java.util.EventObject;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.parsley.composite.FormDetailComposite;
import org.eclipse.emf.parsley.composite.FormFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;
import com.google.inject.Injector;
import de.uni_stuttgart.enpro.newmonitor.NewmonitorInjectorProvider;

public class MonitorFormViewer {

    @Inject
    MDirtyable dirty;

    public MonitorFormViewer(Composite parent, Monitor newMonitor, AddMonitor wizardPage) {
        Injector injector = NewmonitorInjectorProvider.getInjector();

        EditingDomain editingDomain = injector.getInstance(EditingDomain.class);

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
