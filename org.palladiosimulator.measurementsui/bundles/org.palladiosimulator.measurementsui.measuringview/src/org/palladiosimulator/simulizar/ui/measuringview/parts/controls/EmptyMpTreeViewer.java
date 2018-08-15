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

import com.google.inject.Injector;

import mpview.MpviewInjectorProvider;

/**
 * 
 * @author David Schütz
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
	public EmptyMpTreeViewer(Composite parent,MDirtyable dirty,ECommandService commandService) {
		super(parent, dirty, commandService);
	}

	@Override
	protected void initParsley(Composite parent, int selectionIndex) {
		this.treeViewer = new TreeViewer(parent);
		// Guice injector
		Injector injector = MpviewInjectorProvider.getInjector();

		treeFormFactory = injector.getInstance(ViewerFactory.class);
		EditingDomain editingDomain = getEditingDomain(injector);

		Object resource = getResource(selectionIndex, editingDomain, injector);
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
	protected void updateTree(Object resource) {
		treeFormFactory.initialize(treeViewer, resource);
	}

	@Override
	public void dispose() {
		this.treeViewer.getTree().dispose();

	}

	@Override
	protected String getURIPath(int selectionIndex) {
		DataGathering gatherer = new DataGathering();
		if (selectionIndex == -1) {
			return gatherer.getChosenFile(gatherer.getAllProjectAirdfiles().get(0), "measuringpoint");
		} else {
			return gatherer.getChosenFile(gatherer.getAllProjectAirdfiles().get(selectionIndex), "measuringpoint");
		}
	}
}
