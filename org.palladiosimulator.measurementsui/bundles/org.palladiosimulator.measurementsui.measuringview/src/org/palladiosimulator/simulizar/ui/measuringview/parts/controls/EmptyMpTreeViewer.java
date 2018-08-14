package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.parsley.edit.ui.dnd.ViewerDragAndDropHelper;
import org.eclipse.emf.parsley.menus.ViewerContextMenuHelper;
import org.eclipse.emf.parsley.resource.ResourceLoader;
import org.eclipse.emf.parsley.resource.ResourceSaveStrategy;
import org.eclipse.emf.parsley.viewers.ViewerFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.simulizar.ui.measuringview.parts.MeasuringpointView;

import dataManagement.DataGathering;

import com.google.inject.Injector;

import mpview.MpviewInjectorProvider;

public class EmptyMpTreeViewer extends MpTreeViewer {
	ViewerFactory treeFormFactory;

	public EmptyMpTreeViewer(Composite parent) {
		super(parent);
	}

	@Override
	protected void initParsley(Composite parent, int selectionIndex) {
		this.mpTreeViewer = new TreeViewer(parent);
		// Guice injector
		Injector injector = MpviewInjectorProvider.getInjector();

		treeFormFactory = injector.getInstance(ViewerFactory.class);
		EditingDomain editingDomain = getEditingDomain(injector);

		Object resource = getResource(selectionIndex, editingDomain, injector);
		// create the tree-form composite
		treeFormFactory.initialize(mpTreeViewer, resource);

		// Guice injected viewer context menu helper
		ViewerContextMenuHelper contextMenuHelper = injector.getInstance(ViewerContextMenuHelper.class);
		// Guice injected viewer drag and drop helper
		ViewerDragAndDropHelper dragAndDropHelper = injector.getInstance(ViewerDragAndDropHelper.class);
		// set context menu and drag and drop
		ResourceSaveStrategy save = injector.getInstance(ResourceSaveStrategy.class);
		contextMenuHelper.addViewerContextMenu(mpTreeViewer, editingDomain);
		dragAndDropHelper.addDragAndDrop(mpTreeViewer, editingDomain);
	}

	@Override
	protected void updateTree(Object resource) {
		treeFormFactory.initialize(mpTreeViewer, resource);
	}

	@Override
	public void dispose() {
		this.mpTreeViewer.getTree().dispose();

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
