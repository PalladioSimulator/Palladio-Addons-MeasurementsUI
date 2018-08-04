package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import java.util.List;

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

import com.google.inject.Injector;

import mpview.MpviewInjectorProvider;

public class EmptyMpTreeViewer extends MpTreeViewer{
	public EmptyMpTreeViewer(Composite parent) {
		super(parent);
	}
	
	@Override
	protected void initParsley(Composite parent, List<MonitorRepository> repository) {
		this.mpTreeViewer = new TreeViewer(parent);
		// Guice injector
     	Injector injector = MpviewInjectorProvider.getInjector();
     			
     	// The EditingDomain is needed for context menu and drag and drop
     	EditingDomain editingDomain = injector.getInstance(EditingDomain.class);
     	URI uri = URI.createFileURI("D:/Benutzer/David/Dokumente/Uni/EnPro/runtime-EclipseApplication/Pets.com/PetsMeasuringPoint.measuringpoint");
     	ResourceLoader resourceLoader = injector.getInstance(ResourceLoader.class);
     	//load the resource
     	Object resource = resourceLoader.getResource(editingDomain, uri).getResource();
     	
     	ViewerFactory treeFormFactory = injector.getInstance(ViewerFactory.class);
		//create the tree-form composite
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
}
