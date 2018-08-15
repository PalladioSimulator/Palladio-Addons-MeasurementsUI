package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import java.io.IOException;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.parsley.resource.ResourceLoader;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.google.inject.Injector;
import mpview.MpviewInjectorProvider;

/**
 * Creates a eclipse.swt TreeView based on a parsley TreeView project.
 * @author David Schuetz
 *
 */
public abstract class MpTreeViewer {
	protected MDirtyable dirty;
	protected TreeViewer treeViewer;
	protected ECommandService commandService;
	protected Resource resource;
	/**
	 * 
	 * @param parent composite container
	 */
	protected MpTreeViewer(Composite parent, MDirtyable dirty, ECommandService commandService) {
		this.dirty = dirty;
		this.commandService = commandService;
		initParsley(parent, -1);
	}

	/**
	 * Return the TreeViewer
	 * 
	 * @return The current TreeViewer
	 */
	public TreeViewer getTreeViewer() {
		return treeViewer;
	}

	/**
	 * Updates the tree to a resource with a certain index
	 * @param selectionIndex Index of the resource which should be shown in the treeview
	 */
	public void updateInput(int selectionIndex) {
		Injector injector = MpviewInjectorProvider.getInjector();
		EditingDomain editingDomain = getEditingDomain(injector);
		resource = getResource(selectionIndex, editingDomain, injector);
		updateTree(resource);
	}

	/**
	 * Adds a DoubleClickMouseListener which changes Attributes if an icon in the
	 * treeview is double clicked.
	 */
	public void addMouseListener() {
		treeViewer.getTree().addMouseListener(new MpTreeDoubleClickListener(treeViewer));
	}
	
	/**
	 * Adds a listener which connects the selected tree item to the ESelectionService.
	 * @param selectionService
	 */
	public void addSelectionListener(ESelectionService selectionService) {
		treeViewer.addSelectionChangedListener(event -> {
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			// set the selection to the service
			selectionService
					.setSelection(selection.size() == 1 ? selection.getFirstElement() : selection.toArray());
		});
	}

	/**
	 * Initialize the connection between the e4 plugin and the Parsley TreeView
	 * @param parent composite container
	 * @param selectionIndex of a respository which should be shown in the TreeView
	 */
	protected abstract void initParsley(Composite parent, int selectionIndex);

	/**
	 * Disposes of the operating system resources associated with the tree and all
	 * its descendants
	 */
	public abstract void dispose();

	/**
	 * Updates the underlying resources of the tree and redraws the component
	 * 
	 * @param resource which will be shown in the updated tree
	 */
	protected abstract void updateTree(Object resource);

	/**
	 * Returns the URI path to the EMF-model of the tree
	 * 
	 * @param selectionIndex of a respository which should be shown in the tree
	 * @return the URI path to the EMF-model of the tree
	 */
	protected abstract String getURIPath(int selectionIndex);

	/**
	 * Returns the parsley EditingDomain
	 * 
	 * @param injector Google Juice injector of the parsley project
	 * @return the current Editing Domain
	 */
	protected EditingDomain getEditingDomain(Injector injector) {
		// The EditingDomain is needed for context menu and drag and drop
		return injector.getInstance(EditingDomain.class);
	}

	/**
	 * Return the resource of an eobject at a certain index
	 * 
	 * @param selectionIndex index of the resource
	 * @param editingDomain editingdomain of the treeview
	 * @param injector Google guice injector to the parsley project
	 * @return the resource at the chosen selection index
	 */
	protected Resource getResource(int selectionIndex, EditingDomain editingDomain, Injector injector) {
		String measuringPointPath = getURIPath(selectionIndex);
		URI uri = URI.createFileURI(measuringPointPath);
		ResourceLoader resourceLoader = injector.getInstance(ResourceLoader.class);
		// load the resource
		resource = resourceLoader.getResource(editingDomain, uri).getResource();
		
		editingDomain.getCommandStack().addCommandStackListener(e -> {
			if (dirty != null) {
				dirty.setDirty(true);
				commandService.getCommand("org.eclipse.ui.file.save").isEnabled();
			}
		});

		return resource;
	}
	
	
	
	/**
	 * Save the current state of the view
	 * @param dirty
	 * @throws IOException
	 */
	public void save(MDirtyable dirty) throws IOException {
		resource.save(null);
		if (dirty != null) {
			dirty.setDirty(false);
			commandService.getCommand("org.eclipse.ui.file.save").isEnabled();
		}
	}
}
