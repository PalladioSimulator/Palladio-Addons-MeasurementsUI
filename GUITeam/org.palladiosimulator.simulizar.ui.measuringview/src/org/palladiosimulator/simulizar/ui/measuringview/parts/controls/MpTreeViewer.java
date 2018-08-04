package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import java.util.List;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.monitorrepository.MonitorRepository;

import init.StartApplication;

public abstract class MpTreeViewer {
	protected TreeViewer mpTreeViewer;
	protected  MpTreeViewer (Composite parent) {
		initParsley(parent, null);
	}
	
	
	public TreeViewer getTreeViewer() {
		return mpTreeViewer;
	}
	
	public MpTreeViewer updateInput(Composite parent) {
		StartApplication application = StartApplication.getInstance();
        application.startApplication();
        List<MonitorRepository> repository = application.getModelAccessor().getMonitorRepository();      
        initParsley(parent, repository);
		return this;
	}
	
	public void addMouseListener() {
		mpTreeViewer.getTree().addMouseListener(new MpTreeDoubleClickListener(mpTreeViewer));
	}
	
	
	protected abstract void initParsley(Composite parent, List<MonitorRepository> repository);
}
