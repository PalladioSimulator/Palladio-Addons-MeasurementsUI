package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.parsley.edit.ui.dnd.ViewerDragAndDropHelper;
import org.eclipse.emf.parsley.menus.ViewerContextMenuHelper;
import org.eclipse.emf.parsley.viewers.ViewerFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import dataManagement.DataGathering;
import emptymeasuringpoints.EmptymeasuringpointsInjectorProvider;
import init.DataApplication;

import com.google.inject.Injector;

import mpview.MpviewInjectorProvider;


import emptymeasuringpoints.EmptymeasuringpointsInjectorProvider;

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


	@Override
	public void update() {
		
		
	}

}
