package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.init.DataApplication;

import mpview.MpviewInjectorProvider;

/**
 * 
 * @author David Schütz
 *
 */
public class MonitorTreeViewer extends MpTreeViewer {
	/**
	 * 
	 * @param parent
	 * @param dirty
	 * @param commandService
	 */
	public MonitorTreeViewer(Composite parent, MDirtyable dirty,ECommandService commandService, DataApplication application) {
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
