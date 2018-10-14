package emptymeasuringpoints;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.palladiosimulator.measurementsui.wizard.main.StandardSetWizard;

/**
 * Parsley tree view which contains all empty measuringpoints of a palladio
 * project.
 * 
 * @author David Schuetz

 *
 */
public class EmptymeasuringpointsSaveableTreeView extends org.eclipse.emf.parsley.views.SaveableTreeView {
    
    public static void openStandardSetWizard() {
        StandardSetWizard wizard = new StandardSetWizard();
        Shell parentShell = wizard.getShell();
        WizardDialog dialog = new WizardDialog(parentShell, wizard);
        dialog.setPageSize(720, 400);
        dialog.setMinimumPageSize(720, 400);
        dialog.open();
    }
}
