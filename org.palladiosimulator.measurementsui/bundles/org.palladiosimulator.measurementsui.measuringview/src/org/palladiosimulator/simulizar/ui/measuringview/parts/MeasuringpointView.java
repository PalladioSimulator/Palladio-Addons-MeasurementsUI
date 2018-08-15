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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.EmptyMpTreeViewer;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.MonitorTreeViewer;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.MpTreeViewer;

import dataManagement.DataGathering;

/**
 * 
 * @author David Schuetz
 *
 */
public class MeasuringpointView {
	
	private MpTreeViewer monitorTreeViewer;
	private MpTreeViewer emptyMpTreeViewer;
	
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
		SashForm outerContainer = new SashForm(parent, SWT.HORIZONTAL);
        outerContainer.setLayout(new FillLayout());
        
        SashForm treeContainer = new SashForm(outerContainer, SWT.VERTICAL);
        treeContainer.setLayout(new FillLayout());
        
        Composite buttonContainer = new Composite(outerContainer, SWT.BORDER);
        buttonContainer.setLayout(new GridLayout(1, true));
        
        outerContainer.setWeights(new int[]{3, 1});
        
        Composite monitorContainer = createTreeComposite(treeContainer);
        Composite undefinedMeasuringContainer = createTreeComposite(treeContainer); 
        
        monitorTreeViewer = createMonitorTreeViewer(monitorContainer);
        emptyMpTreeViewer = createEmptyMpTreeViewer(undefinedMeasuringContainer);
           
        createViewButtons(buttonContainer); 
        
        handlerService.activateHandler("org.eclipse.ui.file.save", new org.eclipse.e4.ui.internal.workbench.handlers.SaveHandler());
	}
	

	
	/**
	 * 
	 * @param parent
	 * @return
	 */
	private MpTreeViewer createMonitorTreeViewer(Composite parent) {
		MpTreeViewer mpTreeViewer = new MonitorTreeViewer(parent,dirty, commandService);
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
		return new EmptyMpTreeViewer(parent,dirty, commandService);
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
        
        Combo comboDropDown = new Combo(buttonContainer, SWT.DROP_DOWN);
        DataGathering gatherer = new DataGathering();
        List<IProject> allProjects = gatherer.getAllProjectAirdfiles();     
        for(IProject project : allProjects) {
        	comboDropDown.add(project.toString());
        }
        comboDropDown.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = comboDropDown.getSelectionIndex();
				monitorTreeViewer.updateInput(selectionIndex);
				emptyMpTreeViewer.updateInput(selectionIndex);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				comboDropDown.select(0);
			}
		});
        comboDropDown.select(0);
        
        Button deleteMpButton = new Button(buttonContainer, SWT.PUSH);
        deleteMpButton.setText("Delete...");
        Button assignMonitorButton = new Button(buttonContainer, SWT.PUSH);
        assignMonitorButton.setText("Assign to Monitor");
        Button createStandardButton = new Button(buttonContainer, SWT.PUSH);
        createStandardButton.setText("Create Standard Set");     
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
