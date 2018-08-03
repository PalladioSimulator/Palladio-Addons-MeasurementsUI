package mpview;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.parsley.composite.FormFactory;
import org.eclipse.emf.parsley.composite.TreeFormComposite;
import org.eclipse.emf.parsley.composite.TreeFormFactory;
import org.eclipse.emf.parsley.resource.ResourceManager;
import org.eclipse.emf.parsley.viewers.ViewerFactory;
import org.eclipse.emf.parsley.views.SaveableTreeFormView;
import org.eclipse.emf.parsley.views.SaveableTreeView;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.palladiosimulator.monitorrepository.*;

import mpview.config.MpviewConfigurator;

public class MpviewSaveableTreeFormView extends SaveableTreeFormView {
	@Inject ViewerFactory  viewerFactory;
	@Inject ResourceManager manager;
	
	/**
	 * Creates the control objects of the simulizar measuring point view
	 * @param parent
	 */
	
	@Inject MpviewConfigurator conf;
	@PostConstruct
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);  
	}

}
