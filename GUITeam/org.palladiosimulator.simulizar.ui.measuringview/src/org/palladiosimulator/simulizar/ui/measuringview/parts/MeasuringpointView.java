package org.palladiosimulator.simulizar.ui.measuringview.parts;

import java.io.File;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.palladiosimulator.simulizar.ui.measuringview.data.TreeContentCreator;



public class MeasuringpointView {
	
	@PostConstruct
	public void createPartControl(Composite parent) {
		
        SashForm outerContainer = new SashForm(parent, SWT.HORIZONTAL);
        outerContainer.setLayout(new FillLayout());
        
        SashForm treeContainer = new SashForm(outerContainer, SWT.VERTICAL);
        treeContainer.setLayout(new FillLayout());
        
        Composite buttonContainer = new Composite(outerContainer, SWT.BORDER);
        buttonContainer.setLayout(new GridLayout(1,true));
        
        outerContainer.setWeights(new int[]{3,1});
        
        
        Composite definedMeasuringContainer = new Composite(treeContainer, SWT.NONE);
        definedMeasuringContainer.setLayout(new FillLayout());
        definedMeasuringContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        Composite undefinedMeasuringContainer = new Composite(treeContainer, SWT.NONE); 
        undefinedMeasuringContainer.setLayout(new FillLayout());
        undefinedMeasuringContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        treeContainer.setWeights(new int[]{3,1});
        
        TreeViewer topTreeViewer = createTreeViewer(definedMeasuringContainer);
        topTreeViewer.setInput(TreeContentCreator.createTreeStructure());
        
        TreeViewer bottomTreeViewer = createTreeViewer(undefinedMeasuringContainer);
        bottomTreeViewer.setInput(TreeContentCreator.createEmptyMeasuringsPoints());
        
        Tree topTree = topTreeViewer.getTree();
        TreeItem[] treeItems = topTree.getItems();
        LocalResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());
        Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
        URL url = FileLocator.find(bundle, new Path("icons/ActiveIcon.png"), null);
        ImageDescriptor i = ImageDescriptor.createFromURL(url);
        for (TreeItem treeItem: treeItems) {
        	treeItem.setImage(resourceManager.createImage(i));
        }
        
        createViewButtons(buttonContainer);
	}
	
	private TreeViewer createTreeViewer(Composite parent) {
		TreeViewer mpTreeViewer = new TreeViewer(parent);
		mpTreeViewer.setContentProvider(new MpTreeContentProvider());
		mpTreeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(
				new ViewLabelProvider()));
		
		TreeColumnLayout layout = new TreeColumnLayout();
		parent.setLayout(layout);
		
		createTreeColumn(mpTreeViewer, layout);
		return mpTreeViewer;
	}

	private void createTreeColumn(TreeViewer mpTreeViewer, TreeColumnLayout layout) {
		TreeViewerColumn topViewerColumn = new TreeViewerColumn(mpTreeViewer, SWT.NONE);
		layout.setColumnData(topViewerColumn.getColumn(), new ColumnWeightData(100,10,true));
		topViewerColumn.getColumn().setText("Names");
		topViewerColumn.setLabelProvider(new ColumnLabelProvider());
		topViewerColumn.setLabelProvider(new DelegatingStyledCellLabelProvider(
                new ViewLabelProvider()));
	}
	
	private void createViewButtons(Composite buttonContainer) {
		Button newMpButton = new Button(buttonContainer,SWT.PUSH);
        newMpButton.setText("Add new Measuring Point");
        Button editMpButton = new Button(buttonContainer,SWT.PUSH);
        editMpButton.setText("Edit...");
        Button deleteMpButton = new Button(buttonContainer,SWT.PUSH);
        deleteMpButton.setText("Delete...");
        Button assignMonitorButton = new Button(buttonContainer,SWT.PUSH);
        assignMonitorButton.setText("Assign to Monitor");
        Button createStandardButton = new Button(buttonContainer,SWT.PUSH);
        createStandardButton.setText("Create Standard Set");     
	}

	@Focus
	public void setFocus() {

	}

	/**
	 * This method is kept for E3 compatiblity. You can remove it if you do not
	 * mix E3 and E4 code. <br/>
	 * With E4 code you will set directly the selection in ESelectionService and
	 * you do not receive a ISelection
	 * 
	 * @param s
	 *            the selection received from JFace (E3 mode)
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection s) {
		if (s == null || s.isEmpty())
			return;

		if (s instanceof IStructuredSelection) {
			IStructuredSelection iss = (IStructuredSelection) s;
			if (iss.size() == 1) {
				setSelection(iss.getFirstElement());
			} else {
				setSelection(iss.toArray());
			}
		}
	}

	/**
	 * This method manages the selection of your current object. In this example
	 * we listen to a single Object (even the ISelection already captured in E3
	 * mode). <br/>
	 * You should change the parameter type of your received Object to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current object received
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object o) {

		// Remove the 2 following lines in pure E4 mode, keep them in mixed mode
		if (o instanceof ISelection) // Already captured
			return;

		// Test if label exists (inject methods are called before PostConstruct)

	}

	/**
	 * This method manages the multiple selection of your current objects. <br/>
	 * You should change the parameter type of your array of Objects to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current array of objects received in case of multiple selection
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object[] selectedObjects) {

	}
}
