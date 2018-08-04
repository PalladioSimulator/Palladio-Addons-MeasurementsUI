package org.palladiosimulator.simulizar.ui.measuringview.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import mpview.MpviewInjectorProvider;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;
import org.eclipse.emf.parsley.composite.TreeFormComposite;
import org.eclipse.emf.parsley.composite.TreeFormFactory;
import org.eclipse.emf.parsley.edit.ui.dnd.ViewerDragAndDropHelper;
import org.eclipse.emf.parsley.menus.ViewerContextMenuHelper;
import org.eclipse.emf.parsley.resource.ResourceLoader;
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
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.MpTreeViewer;
import org.palladiosimulator.monitorrepository.util.MonitorRepositoryAdapterFactory;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.EmptyMpTreeViewer;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.MonitorTreeViewer;

/**
 * 
 * @author David Schuetz
 *
 */
public class MeasuringpointView {
	private ExtendedPropertySheetPage propertyPage;
	
	/**
	 * Creates the control objects of the simulizar measuring point view
	 * @param parent
	 */
	@PostConstruct
	public void createPartControl(Composite parent) {
        SashForm outerContainer = new SashForm(parent, SWT.HORIZONTAL);
        outerContainer.setLayout(new FillLayout());
        
        SashForm treeContainer = new SashForm(outerContainer, SWT.VERTICAL);
        treeContainer.setLayout(new FillLayout());
        
        Composite buttonContainer = new Composite(outerContainer, SWT.BORDER);
        buttonContainer.setLayout(new GridLayout(1, true));
        
        outerContainer.setWeights(new int[]{3, 1});
        
        Composite monitorContainer = createTreeComposite(treeContainer);
        Composite undefinedMeasuringContainer = createTreeComposite(treeContainer); 
        
        MpTreeViewer monitorTreeViewer = createMonitorTreeViewer(monitorContainer);
        MpTreeViewer emptyMpTreeViewer = createEmptyMpTreeViewer(undefinedMeasuringContainer);
           
        createViewButtons(buttonContainer);  
	}

	
	/**
	 * 
	 * @param parent
	 * @return
	 */
	private MpTreeViewer createMonitorTreeViewer(Composite parent) {
		MpTreeViewer mpTreeViewer = new MonitorTreeViewer(parent);
		return mpTreeViewer;
	}
	
	private MpTreeViewer createEmptyMpTreeViewer(Composite parent) {
		MpTreeViewer mpTreeViewer = new EmptyMpTreeViewer(parent);
		return mpTreeViewer;
	}
	
	private Composite createTreeComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		return composite;
	}
	
	private void createViewButtons(Composite buttonContainer) {
		Button newMpButton = new Button(buttonContainer, SWT.PUSH);
        newMpButton.setText("Add new Measuring Point");
        Button editMpButton = new Button(buttonContainer, SWT.PUSH);
        editMpButton.setText("Edit...");
        Button deleteMpButton = new Button(buttonContainer, SWT.PUSH);
        deleteMpButton.setText("Delete...");
        Button assignMonitorButton = new Button(buttonContainer, SWT.PUSH);
        assignMonitorButton.setText("Assign to Monitor");
        Button createStandardButton = new Button(buttonContainer, SWT.PUSH);
        createStandardButton.setText("Create Standard Set");     
	}
}
