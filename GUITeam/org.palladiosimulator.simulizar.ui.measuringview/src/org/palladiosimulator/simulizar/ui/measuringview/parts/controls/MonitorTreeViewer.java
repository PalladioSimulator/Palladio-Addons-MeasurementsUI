package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import java.io.IOException;
import java.util.EventObject;
import java.util.List;

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

import com.google.inject.Inject;
import com.google.inject.Injector;

import mpview.MpviewInjectorProvider;

public class MonitorTreeViewer extends MpTreeViewer{
	@Inject
	MDirtyable dirty;
	Resource resource;
	public MonitorTreeViewer(Composite parent) {
		super(parent);
	}
	
	@Override
	protected void initParsley(Composite parent, List<MonitorRepository> repository) {
		//Siehe Eclipse 4.x in der Parsley Doku. Je nach Darstellungsart(Tree, Form, Table, TreeForm,...) des Parsleyprojektes muss der Code hier
		//leicht modifiziert werden.
		
		// Guice injector
     	Injector injector = MpviewInjectorProvider.getInjector();
     			
     	// The EditingDomain is needed for context menu and drag and drop
     	EditingDomain editingDomain = injector.getInstance(EditingDomain.class);
     	URI uri = URI.createFileURI("/Users/enpro2/Downloads/Pets.com/PetsMonitor.monitorrepository");
	
		ResourceLoader resourceLoader = injector.getInstance(ResourceLoader.class);
		//load the resource
		resource = resourceLoader.getResource(editingDomain, uri).getResource();
	
		TreeFormFactory treeFormFactory = injector.getInstance(TreeFormFactory.class);
		//create the tree-form composite
		TreeFormComposite treeFormComposite = treeFormFactory.createTreeFormComposite(parent, SWT.BORDER);
	
		// Guice injected viewer context menu helper
		ViewerContextMenuHelper contextMenuHelper = injector.getInstance(ViewerContextMenuHelper.class);
		// Guice injected viewer drag and drop helper
		ViewerDragAndDropHelper dragAndDropHelper = injector.getInstance(ViewerDragAndDropHelper.class);
	
		// set context menu and drag and drop
		contextMenuHelper.addViewerContextMenu(treeFormComposite.getViewer(), editingDomain);
		
		//Leider ist das Drag and Drop in Parsley f�r unser Projekt nicht so geeignet, da es lediglich auf EMF.Edit basiert.
		//Wahrscheinlich m�ssen wir eine eigene DragandDrop Funktion programmieren. 
		//Oder besser aber auf unserem eigenen ECoreModell arbeiten, wo das dann alles funktioniert :)
		dragAndDropHelper.addDragAndDrop(treeFormComposite.getViewer(), editingDomain);
	
		//update the composite
		treeFormComposite.update(resource);
		
		this.mpTreeViewer = (TreeViewer) treeFormComposite.getViewer();
		
		//Speichern der �nderungen. Funktioniert gerade leider noch nicht siehe SaveHandler.java
		editingDomain.getCommandStack().addCommandStackListener(
				new CommandStackListener() {
					public void commandStackChanged(EventObject event) {
						if (dirty != null)
							dirty.setDirty(true);
					}
				});
		
	}
	
	@Persist
	public void save(MDirtyable dirty) throws IOException {
		resource.save(null);
		if (dirty != null) {
			dirty.setDirty(false);
		}
	}
}
