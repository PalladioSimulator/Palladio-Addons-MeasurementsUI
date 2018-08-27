package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.init.DataApplication;

import emptymeasuringpoints.EmptymeasuringpointsInjectorProvider;

/**
 * 
 * @author David Schuetz
 * Generates a tree view where all empty measuring points from all measuringpoint repositories in the workspace are shown.
 * Creates a connection between the parsley emptymeasuringpoints view and an eclipse e4 plugin.
 */
public class EmptyMpTreeViewer extends MpTreeViewer {
	

	/**
	 * 
	 * @param parent container where the tree viewer is placed in
	 * @param dirty state of the e4 view
	 * @param commandService of the eclipse application in order to make the tree view saveable
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