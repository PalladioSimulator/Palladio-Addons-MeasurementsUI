package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
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

/**
 * 
 * @author David Sch�tz
 *
 */
public class EmptyMpTreeViewer extends MpTreeViewer {
	ViewerFactory treeFormFactory;

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
	protected void initParsley(Composite parent) {
		this.treeViewer = new TreeViewer(parent);
		// Guice injector
		Injector injector = EmptymeasuringpointsInjectorProvider.getInjector();

		treeFormFactory = injector.getInstance(ViewerFactory.class);
		EditingDomain editingDomain = getEditingDomain(injector);


		Object resource = getResource( dataApplication.getModelAccessor().getMeasuringPointRpository().get(0), editingDomain, injector);
		// create the tree-form composite
		treeFormFactory.initialize(treeViewer, resource);

		// Guice injected viewer context menu helper
		ViewerContextMenuHelper contextMenuHelper = injector.getInstance(ViewerContextMenuHelper.class);
		// Guice injected viewer drag and drop helper
		ViewerDragAndDropHelper dragAndDropHelper = injector.getInstance(ViewerDragAndDropHelper.class);
		// set context menu and drag and drop
		contextMenuHelper.addViewerContextMenu(treeViewer, editingDomain);
		dragAndDropHelper.addDragAndDrop(treeViewer, editingDomain);
	}

	@Override
	public void updateTree() {
		treeFormFactory.initialize(treeViewer, resource);
	}

	@Override
	public void dispose() {
		this.treeViewer.getTree().dispose();

	}

}
