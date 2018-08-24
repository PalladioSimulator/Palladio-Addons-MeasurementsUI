package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import emptymeasuringpoints.EmptymeasuringpointsInjectorProvider;

import init.DataApplication;

/**
 * 
 * @author David Sch�tz
 *
 */
public class EmptyMpTreeViewer extends MpTreeViewer {
	

	/**
	 * 
	 * @param parent
	 * @param dirty
	 * @param commandService
	 */
	public EmptyMpTreeViewer(Composite parent,MDirtyable dirty,ECommandService commandService, DataApplication application) {
		super(parent, dirty, commandService, application);
	}
	
	
	@Override
	protected EObject getModelRepository() {
		return dataApplication.getModelAccessor().getMeasuringPointRpository().get(0);
	}

	@Override
	protected void initInjector() {
		injector = EmptymeasuringpointsInjectorProvider.getInjector();
	}
}
