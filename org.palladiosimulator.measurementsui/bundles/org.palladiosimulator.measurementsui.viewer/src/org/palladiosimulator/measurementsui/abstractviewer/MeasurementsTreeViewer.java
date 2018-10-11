package org.palladiosimulator.measurementsui.abstractviewer;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.parsley.viewers.ViewerFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.abstractviewer.listener.MeasurementTreeDoubleClickListener;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;

/**
 * Creates a eclipse.swt TreeView based on a parsley TreeView project.
 * 
 * @author David Schuetz
 */
public abstract class MeasurementsTreeViewer extends SaveableComponentViewer {
	protected TreeViewer treeViewer;
	protected ViewerFactory treeFactory;

	/**
	 * 
	 * @param parent          a container where the view is embedded
	 * @param dirty           the dirty state which indicates whether there were
	 *                        changes made in the viewer
	 * @param commandService  a service of the eclipse application in order to make
	 *                        the tree view saveable
	 * @param dataApplication the connection to the data binding. This is needed in
	 *                        order to get the repository of the current project.
	 */
	public MeasurementsTreeViewer(Composite parent, MDirtyable dirty, ECommandService commandService,
			EObject modelRepository) {
		super(parent, dirty, commandService, modelRepository);
		treeViewer.expandToLevel(2);
	}

	/**
	 * Adds a DoubleClickMouseListener which changes Attributes if an icon in the
	 * treeview is double clicked.
	 */
	public void addMouseListener() {
		treeViewer.getTree().addMouseListener(new MeasurementTreeDoubleClickListener(treeViewer));
	}

	/**
	 * Return the TreeViewer
	 * 
	 * @return the current TreeViewer
	 */
	@Override
	public StructuredViewer getViewer() {
		return treeViewer;
	}

	/**
	 * Adds a listener which connects the selected tree item to the
	 * ESelectionService.
	 * 
	 * @param selectionService the Eclipse SelectionService to which the selected
	 *                         element is passed on
	 */
	@Override
	public void addSelectionListener(ESelectionService selectionService) {
		treeViewer.addSelectionChangedListener(event -> {
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			selectionService.setSelection(selection.size() == 1 ? selection.getFirstElement() : selection.toArray());
		});
	}

	@Override
	protected void initParsley(Composite parent) {
		treeViewer = new TreeViewer(parent);
		treeFactory = injector.getInstance(ViewerFactory.class);
		update();
	}

	@Override
	public void update() {
		Object[] expandedElements = treeViewer.getExpandedElements();
		initEditingDomain();
		resource = updateResource(getModelRepository());
		treeFactory.initialize(treeViewer, resource);
		treeViewer.setAutoExpandLevel(1);
		treeViewer.setExpandedElements(expandedElements);
		if (treeViewer.getExpandedElements().length == 0) {
			treeViewer.expandToLevel(2);
		}
		treeViewer.refresh();
	}
}