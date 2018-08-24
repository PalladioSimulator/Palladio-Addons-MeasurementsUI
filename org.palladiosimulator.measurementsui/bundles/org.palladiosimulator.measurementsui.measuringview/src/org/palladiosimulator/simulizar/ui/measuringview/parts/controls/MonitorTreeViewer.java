package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.parsley.composite.TreeFormComposite;
import org.eclipse.emf.parsley.composite.TreeFormFactory;
import org.eclipse.emf.parsley.edit.ui.dnd.ViewerDragAndDropHelper;
import org.eclipse.emf.parsley.menus.ViewerContextMenuHelper;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;

import dataManagement.DataGathering;
import init.DataApplication;

import com.google.inject.Injector;

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
