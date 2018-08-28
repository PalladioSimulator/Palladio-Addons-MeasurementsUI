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
import org.palladiosimulator.measurementsui.init.DataApplication;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.listener.MpTreeDoubleClickListener;

/**
 * @author David Schuetz Creates a eclipse.swt TreeView based on a parsley TreeView project.
 */
public abstract class MpTreeViewer extends MpComponentViewer{
	protected TreeViewer treeViewer;
	protected ViewerFactory treeFactory;
	
	/**
	 * 
	 * @param parent container where the view is embedded
	 * @param dirty describes whether the view was edited
	 * @param commandService eclipse command
	 * @param application    Connection to the data binding. This is needed in order
	 *                       to get the repository of the current project.
	 */
	public MpTreeViewer(Composite parent, MDirtyable dirty, ECommandService commandService, DataApplication application) {
		super(parent,dirty,commandService,application);
	}

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