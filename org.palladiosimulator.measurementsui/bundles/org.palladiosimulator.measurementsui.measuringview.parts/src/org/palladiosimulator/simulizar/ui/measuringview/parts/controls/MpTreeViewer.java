package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.parsley.edit.ui.dnd.ViewerDragAndDropHelper;
import org.eclipse.emf.parsley.menus.ViewerContextMenuHelper;
import org.eclipse.emf.parsley.viewers.ViewerFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.listener.MpTreeDoubleClickListener;

import init.DataApplication;


/**
 * @author David Schuetz
 * Creates a eclipse.swt TreeView based on a parsley TreeView project.
 */
public abstract class MpTreeViewer extends MpComponentViewer{
	protected TreeViewer treeViewer;
	protected ViewerFactory treeFactory;
	
	/**
	 * 
	 * @param parent
	 * @param dirty
	 * @param commandService
	 * @param application
	 */
	public MpTreeViewer(Composite parent, MDirtyable dirty, ECommandService commandService, DataApplication application) {
		super(parent,dirty,commandService,application);
	}

	/**
	 * Adds a DoubleClickMouseListener which changes Attributes if an icon in the
	 * treeview is double clicked.
	 */
	public void addMouseListener() {
		treeViewer.getTree().addMouseListener(new MpTreeDoubleClickListener(treeViewer));
	}
	
	/**
	 * Return the TreeViewer
	 * 
	 * @return The current TreeViewer
	 */
	@Override
	public Viewer getViewer() {
		return treeViewer;
	}
	
	/**
	 * Adds a listener which connects the selected tree item to the ESelectionService.
	 * @param selectionService
	 */
	@Override
	public void addSelectionListener(ESelectionService selectionService) {
		treeViewer.addSelectionChangedListener(event -> {
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			// set the selection to the service
			selectionService
					.setSelection(selection.size() == 1 ? selection.getFirstElement() : selection.toArray());
		});
	}
	
	@Override
	protected void initParsley(Composite parent) {
		treeViewer = new TreeViewer(parent);
		
		treeFactory = injector.getInstance(ViewerFactory.class);
		EditingDomain editingDomain = getEditingDomain(injector);

		Object resource = getResource( getModelRepository(), editingDomain, injector);
		// create the tree-form composite
		treeFactory.initialize(treeViewer, resource);

		// Guice injected viewer context menu helper
		ViewerContextMenuHelper contextMenuHelper = injector.getInstance(ViewerContextMenuHelper.class);
		// Guice injected viewer drag and drop helper
		ViewerDragAndDropHelper dragAndDropHelper = injector.getInstance(ViewerDragAndDropHelper.class);
		// set context menu and drag and drop
		contextMenuHelper.addViewerContextMenu(treeViewer, editingDomain);
		dragAndDropHelper.addDragAndDrop(treeViewer, editingDomain);
	}
	
	@Override
	public void dispose() {
		this.treeViewer.getTree().dispose();

	}
	
	@Override
	public void update() {
		EObject repository = getModelRepository();
		resource = getResource(repository, getEditingDomain(injector), injector);
		treeFactory.initialize(treeViewer, resource);
	}
}