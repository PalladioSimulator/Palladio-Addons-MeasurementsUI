package org.palladiosimulator.simulizar.ui.measuringview.parts;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.EmptyMpTreeViewer;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.MonitorTreeViewer;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.MpTreeViewer;

import dataManagement.DataGathering;
import init.DataApplication;

/**
 * 
 * @author David Schuetz
 *
 */
public class MeasuringpointView {
	
	private MpTreeViewer monitorTreeViewer;
	private MpTreeViewer emptyMpTreeViewer;
	private DataApplication dataApplication;
	
	@Inject
	MDirtyable dirty;
	
	@Inject
	ECommandService commandService;
	
	@Inject
	EHandlerService handlerService;
	
	@Inject
	private ESelectionService selectionService;
	 
	/**
	 * Creates the control objects of the simulizar measuring point view
	 * @param parent
	 */
	@PostConstruct
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, true));
		
		createRepositorySelectionCBox(parent);
		SashForm outerContainer = new SashForm(parent, SWT.FILL);
        outerContainer.setLayout(new GridLayout(1,true));
        outerContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        SashForm treeContainer = new SashForm(outerContainer, SWT.VERTICAL);
        treeContainer.setLayout(new GridLayout(1,true));
        
        Composite buttonContainer = new Composite(outerContainer, SWT.BORDER);
        buttonContainer.setLayout(new GridLayout(1, true));
        
        outerContainer.setWeights(new int[]{3, 1});
        
        Composite monitorContainer = createTreeComposite(treeContainer);
        Composite undefinedMeasuringContainer = createTreeComposite(treeContainer); 
        
        createViewButtons(buttonContainer); 
        
        initializeApplication();
        
        monitorTreeViewer = createMonitorTreeViewer(monitorContainer);
        emptyMpTreeViewer = createEmptyMpTreeViewer(undefinedMeasuringContainer);
           
       
        
        
        handlerService.activateHandler("org.eclipse.ui.file.save", new org.eclipse.e4.ui.internal.workbench.handlers.SaveHandler());
	}
	
	private void initializeApplication() {
		this.dataApplication = DataApplication.getInstance();
		dataApplication.loadData(0);	
	}

	
	/**
	 * 
	 * @param parent
	 * @return
	 */
	private MpTreeViewer createMonitorTreeViewer(Composite parent) {
		MpTreeViewer mpTreeViewer = new MonitorTreeViewer(parent,dirty,commandService, dataApplication);
		mpTreeViewer.addMouseListener();
		mpTreeViewer.addSelectionListener(selectionService);
		return mpTreeViewer;
	}
	
	/**
	 * 
	 * @param parent
	 * @return
	 */
	private MpTreeViewer createEmptyMpTreeViewer(Composite parent) {
		return new EmptyMpTreeViewer(parent,dirty,commandService, dataApplication);
	}
	
	/**
	 * 
	 * @param parent
	 * @return
	 */
	private Composite createTreeComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		return composite;
	}
	
	/**
	 * 
	 * @param buttonContainer
	 */
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
	
	private void createRepositorySelectionCBox(Composite parent) {
		Combo comboDropDown = new Combo(parent, SWT.DROP_DOWN);
		comboDropDown.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
        List<IProject> allProjects = dataApplication.getDataGathering().getAllProjectAirdfiles();     
        for(IProject project : allProjects) {
        	comboDropDown.add(project.toString());
        }
        comboDropDown.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = comboDropDown.getSelectionIndex();
				dataApplication.loadData(selectionIndex);
				monitorTreeViewer.updateTree();
				emptyMpTreeViewer.updateTree();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				comboDropDown.select(0);
			}
		});
        comboDropDown.select(0);
	}
	
	/**
	 * 
	 * @param dirty
	 * @throws IOException
	 */
	@Persist
	public void save(MDirtyable dirty) throws IOException {
		monitorTreeViewer.save(dirty);
	}
	
	
}
