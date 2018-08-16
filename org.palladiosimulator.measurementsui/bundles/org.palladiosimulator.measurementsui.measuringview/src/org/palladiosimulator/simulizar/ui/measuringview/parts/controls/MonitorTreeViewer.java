package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
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
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;

import dataManagement.DataGathering;

import com.google.inject.Injector;

import mpview.MpviewInjectorProvider;

/**
 * 
 * @author David Schütz
 *
 */
public class MonitorTreeViewer extends MpTreeViewer {

	TreeFormComposite treeFormComposite;
	
	/**
	 * 
	 * @param parent
	 * @param dirty
	 * @param commandService
	 */
	public MonitorTreeViewer(Composite parent, MDirtyable dirty,ECommandService commandService) {
		super(parent, dirty, commandService);
	}

	@Override
	protected void initParsley(Composite parent, int selectionIndex) {
		// Siehe Eclipse 4.x in der Parsley Doku. Je nach Darstellungsart(Tree, Form,
		// Table, TreeForm,...) des Parsleyprojektes muss der Code hier
		// leicht modifiziert werden.

		// Guice injector
		Injector injector = MpviewInjectorProvider.getInjector();

		// Get the Path of MonitorRepository file of first project in Workspace that
		// also has an .aird file
		
		EditingDomain editingDomain = getEditingDomain(injector);
		resource = getResource(selectionIndex, editingDomain, injector);

		TreeFormFactory treeFormFactory = injector.getInstance(TreeFormFactory.class);
		// create the tree-form composite
		treeFormComposite = treeFormFactory.createTreeFormComposite(parent, SWT.BORDER);

		// Guice injected viewer context menu helper
		ViewerContextMenuHelper contextMenuHelper = injector.getInstance(ViewerContextMenuHelper.class);
		// Guice injected viewer drag and drop helper
		ViewerDragAndDropHelper dragAndDropHelper = injector.getInstance(ViewerDragAndDropHelper.class);

		// set context menu and drag and drop
		contextMenuHelper.addViewerContextMenu(treeFormComposite.getViewer(), editingDomain);

		// Leider ist das Drag and Drop in Parsley f�r unser Projekt nicht so
		// geeignet, da es lediglich auf EMF.Edit basiert.
		// Wahrscheinlich m�ssen wir eine eigene DragandDrop Funktion programmieren.
		// Oder besser aber auf unserem eigenen ECoreModell arbeiten, wo das dann alles
		// funktioniert :)
		dragAndDropHelper.addDragAndDrop(treeFormComposite.getViewer(), editingDomain);

		// update the composite
		treeFormComposite.update(resource);

		this.treeViewer = (TreeViewer) treeFormComposite.getViewer();
		

	}

	@Override
	protected void updateTree(Object resource) {
		treeFormComposite.update(resource);
		Monitor m = MonitorRepositoryFactory.eINSTANCE.createMonitor();
		m.eResource().getURI();
	}

	public Resource getResource() {
		return resource;
	}

	@Override
	public void dispose() {
		this.treeFormComposite.dispose();
	}

	@Override
	protected String getURIPath(int selectionIndex) {
		DataGathering gatherer = new DataGathering();
		if (selectionIndex == -1) {
			return gatherer.getChosenFile(gatherer.getAllProjectAirdfiles().get(0), "monitorrepository");
		} else {
			return gatherer.getChosenFile(gatherer.getAllProjectAirdfiles().get(selectionIndex), "monitorrepository");
		}
	}
}
