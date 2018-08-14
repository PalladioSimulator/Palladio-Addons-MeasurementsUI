package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import java.io.IOException;
import java.util.EventObject;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.parsley.composite.TreeFormComposite;
import org.eclipse.emf.parsley.composite.TreeFormFactory;
import org.eclipse.emf.parsley.edit.ui.dnd.ViewerDragAndDropHelper;
import org.eclipse.emf.parsley.menus.ViewerContextMenuHelper;
import org.eclipse.emf.parsley.resource.ResourceLoader;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.simulizar.ui.measuringview.parts.MeasuringpointView;

import dataManagement.DataGathering;

import com.google.inject.Inject;
import com.google.inject.Injector;

import mpview.MpviewInjectorProvider;

public class MonitorTreeViewer extends MpTreeViewer {

	MDirtyable dirty;
	Resource resource;
	TreeFormComposite treeFormComposite;
	ECommandService commandService;
	
	public MonitorTreeViewer(Composite parent, MDirtyable dirty,ECommandService commandService) {
		super(parent);
		this.dirty = dirty;
		this.commandService = commandService;
		
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
		// TODO: Choose which Project to use according to some sort of selection
		DataGathering gatherer = new DataGathering();
		
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

		this.mpTreeViewer = (TreeViewer) treeFormComposite.getViewer();

		// Speichern der �nderungen. Funktioniert gerade leider noch nicht siehe
		// SaveHandler.java
		editingDomain.getCommandStack().addCommandStackListener(new CommandStackListener() {
			public void commandStackChanged(EventObject event) {
				if (dirty != null) {
					dirty.setDirty(true);
					commandService.getCommand("org.eclipse.ui.file.save").isEnabled();
				}
			}
		});

	}

	@Override
	protected void updateTree(Object resource) {
		treeFormComposite.update(resource);
	}

	public void save(MDirtyable dirty) throws IOException {
		resource.save(null);
		if (dirty != null) {
			dirty.setDirty(false);
			commandService.getCommand("org.eclipse.ui.file.save").isEnabled();
		}
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
