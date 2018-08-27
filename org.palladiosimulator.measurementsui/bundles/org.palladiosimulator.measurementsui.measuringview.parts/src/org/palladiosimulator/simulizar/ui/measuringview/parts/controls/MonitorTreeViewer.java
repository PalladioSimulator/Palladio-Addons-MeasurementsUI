package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.init.DataApplication;

import mpview.MpviewInjectorProvider;

/**
 * 
 * @author David Schütz Generates a tree view where all monitors from a
 *         selected monitorrepository in the workspace are shown. Creates a
 *         connection between the parsley mpview view and an eclipse e4 plugin.
 */
public class MonitorTreeViewer extends MpTreeViewer {

	/**
	 * 
	 * @param parent         composite where the tree view is embedded
	 * @param dirty          state of the tree view
	 * @param commandService eclipse commandservice in order to save the view
	 * @param application    Connection to the data binding. This is needed in order
	 *                       to get the repository of the current project.
	 */
	public MonitorTreeViewer(Composite parent, MDirtyable dirty, ECommandService commandService,
			DataApplication application) {
		super(parent, dirty, commandService, application);
	}

	@Override
	protected void initInjector() {
		this.injector = MpviewInjectorProvider.getInjector();
	}

	@Override
	protected EObject getModelRepository() {
		return dataApplication.getModelAccessor().getMonitorRepository().get(0);
	}

}