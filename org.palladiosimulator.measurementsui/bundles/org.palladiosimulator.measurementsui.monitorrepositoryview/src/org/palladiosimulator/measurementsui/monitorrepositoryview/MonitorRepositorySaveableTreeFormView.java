package org.palladiosimulator.measurementsui.monitorrepositoryview;

import org.eclipse.emf.parsley.views.SaveableTreeView;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.palladiosimulator.measurementsui.wizard.main.MeasurementsWizard;
import org.palladiosimulator.measurementsui.wizardmodel.WizardModelType;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * Parsley view which shows all monitors and their respective childs and
 * measuringpoints in a tree view
 * 
 * @author David Schuetz
 *
 */
public class MonitorRepositorySaveableTreeFormView extends SaveableTreeView {
	
	public static void openWizard() {
		MeasurementsWizard wizard = new MeasurementsWizard();
		Shell parentShell = wizard.getShell();
		WizardDialog dialog = new WizardDialog(parentShell, wizard);
		dialog.setPageSize(1180, 580);
        dialog.setMinimumPageSize(1180, 580);
		dialog.open();
	}
	
	public static void openWizard(WizardModelType type, Monitor monitor) {
		MeasurementsWizard wizard = new MeasurementsWizard(type, monitor);
		Shell parentShell = wizard.getShell();
		WizardDialog dialog = new WizardDialog(parentShell, wizard);
		dialog.setPageSize(1180, 580);
        dialog.setMinimumPageSize(1180, 580);
		dialog.open();
	}
}
