package org.palladiosimulator.simulizar.ui.measuringview.parts.controls.listener;

import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.impl.MonitorImpl;

/**
 * 
 * @author David Schuetz
 *
 */
public class MpTreeDoubleClickListener implements MouseListener{

	private Tree mpTree;
	private TreeViewer mpTreeViewer;
	
	/**
	 * 
	 * @param mpTreeViewer
	 */
	public MpTreeDoubleClickListener(TreeViewer mpTreeViewer) {
		this.mpTreeViewer = mpTreeViewer;
		this.mpTree = mpTreeViewer.getTree();
	}
	
	@Override
	public void mouseDoubleClick(MouseEvent e) {
		for (TreeItem item : mpTree.getSelection()) {
			if (item.getImage() != null && (e.x > item.getImageBounds(0).x)
					&& (e.x < (item.getImageBounds(0).x + item.getImage().getBounds().width))
					&& (e.y > item.getImageBounds(0).y)
					&& (e.y < (item.getImageBounds(0).y + item.getImage().getBounds().height))) {
				setChecked(item);

			}
		}
	}

	/**
	 * 
	 * @param item
	 */
	private void setChecked(TreeItem item) {
		Object data = item.getData();
		ResourceEditor edit = new ResourceEditor();
		
		if (data instanceof Monitor) {
			MonitorImpl monitor = (MonitorImpl) data;
			edit.changeMonitorActive(monitor, monitor.isActivated());
		}

		if (data instanceof MeasurementSpecification) {
			MeasurementSpecification spec = (MeasurementSpecification) data;
			edit.changeTriggersSelfAdapting(spec, spec.isTriggersSelfAdaptations());

		}
	}

	@Override
	public void mouseDown(MouseEvent e) {
		//Do nothing on mouseDown
	}

	@Override
	public void mouseUp(MouseEvent e) {
		//Do nothing on mouseUp
	}

}
