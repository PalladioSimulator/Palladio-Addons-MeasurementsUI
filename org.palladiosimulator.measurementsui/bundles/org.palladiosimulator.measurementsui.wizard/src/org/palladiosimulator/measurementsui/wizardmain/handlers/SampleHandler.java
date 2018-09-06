package org.palladiosimulator.measurementsui.wizard.handlers;

import org.eclipse.core.commands.AbstractHandler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.palladiosimulator.measurementsui.wizard.MeasuringPointsWizard;

/**
 * @author Birasanth Pushpanathan
 *
 */
public class SampleHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        MeasuringPointsWizard test = new MeasuringPointsWizard();
        Shell parentShell = test.getShell();
        WizardDialog dialog = new WizardDialog(parentShell, test);
        dialog.open();
        return null;
    }

}
