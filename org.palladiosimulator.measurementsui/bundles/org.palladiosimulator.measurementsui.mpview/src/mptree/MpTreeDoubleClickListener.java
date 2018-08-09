package mptree;

import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.impl.MonitorImpl;

import dataManipulation.ResourceEditor;


/**
 * Mouselistener which changes an emf attribute via a double click on an icon in the treeviewer.
 * Currently the attributes are set manually and not via parsley.
 * @author David Sch�tz
 *
 */
public class MpTreeDoubleClickListener implements MouseListener{

	private Tree mpTree;
	private TreeViewer mpTreeViewer;
	public MpTreeDoubleClickListener(TreeViewer mpTreeViewer) {
		this.mpTreeViewer = mpTreeViewer;
		this.mpTree = mpTreeViewer.getTree();
	}
	
	@Override
	public void mouseDoubleClick(MouseEvent e) {
		for (TreeItem item : mpTree.getSelection()) {
			if (item.getImage() != null) {
				if ((e.x > item.getImageBounds(0).x) && (e.x < (item.getImageBounds(0).x + item.getImage().getBounds().width))) {
					if ((e.y > item.getImageBounds(0).y) 
							&& (e.y < (item.getImageBounds(0).y + item.getImage().getBounds().height))) {
						setChecked(item);
					}
				}
			}
		}
	}
	
	private void setChecked(TreeItem item) {
		Object data = item.getData();
		ResourceEditor edit = new ResourceEditor();
		if (data instanceof Monitor) {
			MonitorImpl monitor = (MonitorImpl) data;
			if (monitor.isActivated()) {
				edit.setMonitorUnactive(monitor);
			} else {
				edit.setMonitorActive(monitor);
			}
			mpTreeViewer.update(data, null);
		}
		
		if (data instanceof MeasurementSpecification) {
			MeasurementSpecification spec = (MeasurementSpecification) data;
			EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(spec);
		    domain.getCommandStack().execute(new SetCommand(domain, spec, spec.eClass().getEStructuralFeature("triggersSelfAdaptations"), !spec.isTriggersSelfAdaptations()));
		}
	}

	@Override
	public void mouseDown(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
