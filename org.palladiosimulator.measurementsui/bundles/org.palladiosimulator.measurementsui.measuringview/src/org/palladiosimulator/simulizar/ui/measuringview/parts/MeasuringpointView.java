package org.palladiosimulator.simulizar.ui.measuringview.parts;

import java.awt.Event;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;
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
import org.eclipse.swt.widgets.Listener;
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
	private ExtendedPropertySheetPage propertyPage;
	
	public int selectionIndex;
	
	public int getSelectionIndex() {
		return selectionIndex;
	}

	MpTreeViewer monitorTreeViewer;
	MonitorTreeViewer mon;
	SashForm treeContainer;
	
	@Inject
	MDirtyable dirty;
	/**
	 * Creates the control objects of the simulizar measuring point view
	 * @param parent
	 */
	@PostConstruct
	public void createPartControl(Composite parent) {
        SashForm outerContainer = new SashForm(parent, SWT.HORIZONTAL);
        outerContainer.setLayout(new FillLayout());
        
        treeContainer = new SashForm(outerContainer, SWT.VERTICAL);
        treeContainer.setLayout(new FillLayout());
        
        Composite buttonContainer = new Composite(outerContainer, SWT.BORDER);
        buttonContainer.setLayout(new GridLayout(1, true));
        
        outerContainer.setWeights(new int[]{3, 1});
        
        Composite monitorContainer = createTreeComposite(treeContainer);
        Composite undefinedMeasuringContainer = createTreeComposite(treeContainer); 
        
        monitorTreeViewer = createMonitorTreeViewer(monitorContainer);
        MpTreeViewer emptyMpTreeViewer = createEmptyMpTreeViewer(undefinedMeasuringContainer);
           
        createViewButtons(buttonContainer);  
	}

	
	/**
	 * 
	 * @param parent
	 * @return
	 */
	private MpTreeViewer createMonitorTreeViewer(Composite parent) {
		MpTreeViewer mpTreeViewer = new MonitorTreeViewer(parent,dirty);
		mon = (MonitorTreeViewer) mpTreeViewer;
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
//        newMpButton.addListener(SWT.Selection, new Listener() {
//			@Override
//			public void handleEvent(org.eclipse.swt.widgets.Event event) {
//				System.out.println("Add new Measuring Point Button pressed");
//				
//			}
//        });
//
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
				// TODO Auto-generated method stub
				System.out.println("Selection is:" + comboDropDown.getSelectionIndex());
				selectionIndex = comboDropDown.getSelectionIndex();
				monitorTreeViewer.updateInput(treeContainer, selectionIndex);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        Button deleteMpButton = new Button(buttonContainer, SWT.PUSH);
        deleteMpButton.setText("Delete...");
        Button assignMonitorButton = new Button(buttonContainer, SWT.PUSH);
        assignMonitorButton.setText("Assign to Monitor");
        Button createStandardButton = new Button(buttonContainer, SWT.PUSH);
        createStandardButton.setText("Create Standard Set");     
	}
	
	@Persist
	public void save(MDirtyable dirty) throws IOException {
		mon.getResource().save(null);
		if (dirty != null) {
			dirty.setDirty(false);
		}
	}
}
