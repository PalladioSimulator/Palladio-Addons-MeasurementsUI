package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
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
	
	/*
	 * Die Methode hier sollte aufgerufen werden, wenn im ein anderes Projekt aufgerufen wird.
	 * Leider hat bei mir aus irgendeinem Grund die Startapplication immer null zur�ckgegeben,
	 * weswegen die Methode momentan noch nicht aufgerufen wird.
	 */
	public MpTreeViewer updateInput(Composite parent) {
		StartApplication application = StartApplication.getInstance();
        application.startApplication();
        List<MonitorRepository> repository = application.getModelAccessor().getMonitorRepository();      
        initParsley(parent, repository);
		return this;
	}
	
	/**
	 * Adds a DoubleClickMouseListener which changes Attributes if an icon in the treeview is double clicked.
	 */
	public void addMouseListener() {
		mpTreeViewer.getTree().addMouseListener(new MpTreeDoubleClickListener(mpTreeViewer));
	}
	
	/*
	 * Schafft die Anbindung zu den jeweilgen Parsleyprojekten mit Hilfe der Google Guice Injection. Mehr dazu in den erbenden Klassen.
	 */
	protected abstract void initParsley(Composite parent, List<MonitorRepository> repository);
}
