package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import java.io.IOException;
import java.util.EventObject;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.parsley.composite.TreeFormComposite;
import org.eclipse.emf.parsley.composite.TreeFormFactory;
import org.eclipse.emf.parsley.edit.ui.dnd.ViewerDragAndDropHelper;
import org.eclipse.emf.parsley.menus.ViewerContextMenuHelper;
import org.eclipse.emf.parsley.resource.ResourceLoader;
import org.eclipse.emf.parsley.viewers.IViewerMouseListener;
import org.eclipse.emf.parsley.viewers.ViewerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.palladiosimulator.monitorrepository.MonitorRepository;


import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import javax.inject.Named;

import init.StartApplication;
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
		// Guice injector
     	Injector injector = MpviewInjectorProvider.getInjector();
     			
     	// The EditingDomain is needed for context menu and drag and drop
     	EditingDomain editingDomain = injector.getInstance(EditingDomain.class);
     	URI uri = URI.createFileURI("D:/Benutzer/David/Dokumente/Uni/EnPro/runtime-EclipseApplication/Pets.com/PetsMonitor.monitorrepository");
	
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
		dragAndDropHelper.addDragAndDrop(treeFormComposite.getViewer(), editingDomain);
	
		//update the composite
		treeFormComposite.update(resource);
		
		this.mpTreeViewer = (TreeViewer) treeFormComposite.getViewer();
		
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
