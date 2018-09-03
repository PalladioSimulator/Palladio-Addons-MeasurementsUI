package org.palladiosimulator.simulizar.ui.measuringview.parts.controls;

import java.io.IOException;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.init.DataApplication;

import com.google.inject.Injector;

/**
 * A common saveable view based on a parsley view.
 * 
 * @author David Schuetz
 */
public abstract class SaveableComponentViewer extends ComponentViewer {

	protected MDirtyable dirty;
	protected ECommandService commandService;

	/**
	 * 
	 * @param parent         container where the view is embedded
	 * @param dirty          describes whether the view was edited
	 * @param commandService eclipse command
	 * @param application    Connection to the data binding. This is needed in order
	 *                       to get the repository of the current project.
	 */
	protected SaveableComponentViewer(Composite parent, MDirtyable dirty, ECommandService commandService,
			DataApplication application) {
		super(parent, application);
		this.dirty = dirty;
		this.commandService = commandService;
	}

	/**
	 * Connects the current selected item in the view with the eclipse
	 * selectionservice
	 * 
	 * @param selectionService of the eclipse project
	 */
	public abstract void addSelectionListener(ESelectionService selectionService);

	@Override
	protected Resource getResource(EObject model, EditingDomain editingDomain, Injector injector) {
		Resource resource = super.getResource(model, editingDomain, injector);

		editingDomain.getCommandStack().addCommandStackListener(e -> {
			if (dirty != null) {
				dirty.setDirty(true);
				commandService.getCommand("org.eclipse.ui.file.save").isEnabled();
			}
		});
		
		return resource;
	}

	/**
	 * Saves the current state of the view
	 * 
	 * @param dirty
	 * @throws IOException
	 */
	public void save(MDirtyable dirty) throws IOException {
		resource.save(null);
		if (dirty != null) {
			dirty.setDirty(false);
			commandService.getCommand("org.eclipse.ui.file.save").isEnabled();
		}
	}
}